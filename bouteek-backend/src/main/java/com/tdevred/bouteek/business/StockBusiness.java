package com.tdevred.bouteek.business;

import com.tdevred.bouteek.business.DTO.*;
import com.tdevred.bouteek.business.exceptions.*;
import com.tdevred.bouteek.entities.Category;
import com.tdevred.bouteek.entities.Product;
import com.tdevred.bouteek.entities.Stock;
import com.tdevred.bouteek.entities.Warehouse;
import com.tdevred.bouteek.repositories.CategoryRepository;
import com.tdevred.bouteek.repositories.ProductRepository;
import com.tdevred.bouteek.repositories.StockRepository;
import com.tdevred.bouteek.repositories.WarehouseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockBusiness {
    private final CategoryRepository categoryRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public StockBusiness(CategoryRepository categoryRepository, StockRepository stockRepository, ProductRepository productRepository, WarehouseRepository warehouseRepository) {
        this.categoryRepository = categoryRepository;
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Caching(evict = {
            @CacheEvict(value = "all_warehouses", allEntries = true),
            @CacheEvict(value = "warehouse_stocks", key = "#result.getId"),
    })
    public WarehouseDTO addWarehouse(String name, String address) {
        return WarehouseDTO.fromWarehouse(warehouseRepository.save(new Warehouse(name, address)));
    }

    @Caching(evict = {
            @CacheEvict(value = "all_products", allEntries = true),
            @CacheEvict(value = "product_stocks", key = "#result.getId"),
            @CacheEvict(value = "product_single", key = "#result.getId")
    })
    public ProductDTO addProduct(String name, double price, String description, long categoryId) throws NoCategoryException {
        Category category;
        try {
            category = categoryRepository.findById(categoryId).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoCategoryException(categoryId);
        }

        return ProductDTO.fromProduct(productRepository.save(new Product(name, price, description, category)));
    }

    @Caching(evict = {
            @CacheEvict(value = "all_categories", allEntries = true),
            @CacheEvict(value="category_single", key="#result.getId")
    })
    public CategoryDTO addCategory(String name, String description) {
        return CategoryDTO.fromCategory(categoryRepository.save(new Category(name, description)));
    }

    public long getStockForProduct(long productId) throws NoProductException {
        if (!productRepository.existsById(productId)) {
            throw new NoProductException(productId);
        }

        List<Stock> stocks = stockRepository.findAllByProductId(productId);
        return stocks.stream().map(Stock::getQuantity).reduce(Long::sum).orElse(0L);
    }

    @Caching(evict = {
            @CacheEvict(value = "all_stocks", allEntries = true),
            @CacheEvict(value = "warehouse_stocks", key = "#warehouseId"),
            @CacheEvict(value = "product_stocks", key="#productId")
    })
    public void addStock(long warehouseId, long productId, long quantity) throws NoWarehouseException, NoProductException {
        Warehouse warehouse;
        Product product;
        try {
            warehouse = warehouseRepository.findById(warehouseId).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoWarehouseException(warehouseId);
        }

        try {
            product = productRepository.findById(productId).orElseThrow();
        } catch (NoSuchElementException e) {
            throw new NoProductException(productId);
        }
        stockRepository.save(new Stock(product, quantity, warehouse));
    }

    @Cacheable(value = "product_single", key = "#productId")
    public ProductDTO getProduct(long productId) throws NoProductException {
        return ProductDTO.fromProduct(productRepository.findById(productId).orElseThrow(() -> new NoProductException(productId)));
    }

    @Cacheable("all_products")
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(ProductDTO::fromProduct).collect(Collectors.toList());
    }

    @CacheEvict(value = "product_single", key = "#productId")
    public ProductDTO updateProduct(long productId, ProductModificationDTO productModificationDTO) throws NoProductException, NoCategoryException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NoProductException(productId));

        if(productModificationDTO.getName() != null) {
            product.setName(productModificationDTO.getName());
        }
        if(productModificationDTO.getDescription() != null) {
            product.setDescription(productModificationDTO.getDescription());
        }
        if(productModificationDTO.getPrice() != null) {
            product.setPrice(productModificationDTO.getPrice());
        }

        if(productModificationDTO.getCategory() != null) {
            long cid = productModificationDTO.getCategory();
            Category category = categoryRepository.findById(cid).orElseThrow(() -> new NoCategoryException(cid));
            product.setCategory(category);
        }

        productRepository.save(product);
        return ProductDTO.fromProduct(product);
    }

    @Cacheable("all_categories")
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDTO::fromCategory).collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(value = "all_categories", allEntries = true),
            @CacheEvict(value = "category_single", key = "#categoryId")
    })
    public CategoryDTO updateCategory(long categoryId, CategoryModificationDTO categoryModificationDTO) throws NoCategoryException {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NoCategoryException(categoryId));

        if(categoryModificationDTO.getName() != null) {
            category.setName(categoryModificationDTO.getName());
        }
        if(categoryModificationDTO.getDescription() != null) {
            category.setDescription(categoryModificationDTO.getDescription());
        }

        categoryRepository.save(category);
        return CategoryDTO.fromCategory(category);
    }

    @Cacheable(value = "category_single", key = "#categoryId")
    public CategoryDTO getCategory(long categoryId) throws NoCategoryException{
        return CategoryDTO.fromCategory(categoryRepository.findById(categoryId).orElseThrow(() -> new NoCategoryException(categoryId)));
    }

    @Caching(evict = {
            @CacheEvict(value = "all_categories", allEntries = true),
            @CacheEvict(value = "category_single", key = "#categoryId")
    })
    public void deleteCategory(long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Caching(evict = {
            @CacheEvict(value = "all_products", allEntries = true),
            @CacheEvict(value = "product_single", key = "#productId"),
            @CacheEvict(value = "product_stocks", key = "#productId")
    })
    @Transactional
    public void deleteProduct(long productId) {
        // handle deletion and notification for all stocks
        stockRepository.deleteAllByProductId(productId);
        productRepository.deleteById(productId);
    }

    @Cacheable("all_stocks")
    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll().stream().map(StockDTO::fromStock).collect(Collectors.toList());
    }

    @Cacheable("warehouse_stocks")
    public List<StockDTO> getAllStocksInWarehouse(long warehouseId) {
        return stockRepository.findAllByWarehouseId(warehouseId).stream().map(StockDTO::fromStock).collect(Collectors.toList());
    }

    @Cacheable("product_stocks")
    public List<StockDTO> getAllStocksForProduct(long productId) {
        return stockRepository.findAllByProductId(productId).stream().map(StockDTO::fromStock).collect(Collectors.toList());
    }

    public List<StockDTO> getStocksForProductInWarehouse(long productId, long warehouseId) {
        return stockRepository.findByProductIdAndWarehouseId(productId, warehouseId).stream().map(StockDTO::fromStock).collect(Collectors.toList());
    }

    @Caching(evict = {
            @CacheEvict(value = "all_stocks", allEntries = true),
            @CacheEvict(value = "warehouse_stocks", key = "#warehouseId"),
            @CacheEvict(value = "product_stocks", key = "#productId")
    })
    public StockDTO addStockForProductInWarehouse(long productId, long warehouseId, long quantity) throws NoWarehouseException, NoProductException {
        Optional<Stock> stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId);

        if (stock.isPresent()) {
            Stock stockRecord = stock.get();
            stockRecord.setQuantity(stockRecord.getQuantity() + quantity);
            stockRepository.save(stockRecord);
            return StockDTO.fromStock(stockRecord);
        } else {
            Product product = productRepository.findById(productId).orElseThrow(() -> new NoProductException(productId));
            Warehouse warehouse = warehouseRepository.findById(productId).orElseThrow(() -> new NoWarehouseException(warehouseId));

            return StockDTO.fromStock(stockRepository.save(new Stock(product, quantity, warehouse)));
        }
    }

    @Caching(evict = {
            @CacheEvict(value = "all_stocks", allEntries = true),
            @CacheEvict(value = "product_stocks", key = "#productId"),
            @CacheEvict(value = "warehouse_stocks", key = "#warehouseId"),
    })
    public StockDTO removeStockForProductInWarehouse(long productId, long warehouseId, long quantity) throws NoStockException, NotEnoughQuantityForStockException {
        Stock stock = stockRepository.findByProductIdAndWarehouseId(productId, warehouseId).orElseThrow(() -> new NoStockException(productId, warehouseId));

        if(stock.getQuantity() - quantity < 0) {
            throw new NotEnoughQuantityForStockException(productId, warehouseId, stock.getQuantity(), quantity);
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stockRepository.save(stock);
        return StockDTO.fromStock(stock);
    }
}
