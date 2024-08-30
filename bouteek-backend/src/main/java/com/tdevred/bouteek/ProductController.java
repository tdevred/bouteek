package com.tdevred.bouteek;

import com.tdevred.bouteek.business.DTO.*;
import com.tdevred.bouteek.business.exceptions.NoCategoryException;
import com.tdevred.bouteek.business.exceptions.NoProductException;
import com.tdevred.bouteek.business.StockBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:8080", "http://localhost"})
@RestController
public class ProductController {

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private final StockBusiness stockBusiness;

    public ProductController(StockBusiness stockBusiness) {
        this.stockBusiness = stockBusiness;
    }

    @GetMapping("/products")
    public List<ProductDTO> getProducts() {
        return stockBusiness.getAllProducts();
    }

    @GetMapping("/products/{productId}")
    public ProductDTO getProduct(@PathVariable long  productId) throws NoProductException {
        return stockBusiness.getProduct(productId);
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody ProductCreationDTO productCreationDTO) throws NoCategoryException {
        return stockBusiness.addProduct(productCreationDTO.getName(), productCreationDTO.getPrice(), productCreationDTO.getDescription(), productCreationDTO.getCategory());
    }

    @PatchMapping("/products/{productId}")
    public ProductDTO updateProduct(@PathVariable long productId, @RequestBody ProductModificationDTO productModificationDTO) throws NoProductException, NoCategoryException {
        return stockBusiness.updateProduct(productId, productModificationDTO);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable long productId) {
        stockBusiness.deleteProduct(productId);
    }

    @GetMapping("/categories")
    public List<CategoryDTO> getCategories() {
        return stockBusiness.getAllCategories();
    }

    @GetMapping("/categories/{categoryId}")
    public CategoryDTO getCategory(@PathVariable long  categoryId) throws NoCategoryException {
        return stockBusiness.getCategory(categoryId);
    }

    @PostMapping("/categories")
    public CategoryDTO addCategory(@RequestBody ProductCreationDTO categoryCreationDTO) {
        return stockBusiness.addCategory(categoryCreationDTO.getName(), categoryCreationDTO.getDescription());
    }

    @PatchMapping("/categories/{categoryId}")
    public CategoryDTO updateCategory(@PathVariable long categoryId, @RequestBody CategoryModificationDTO categoryModificationDTO) throws NoCategoryException {
        return stockBusiness.updateCategory(categoryId, categoryModificationDTO);
    }

    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@PathVariable long categoryId) throws NoCategoryException {
        stockBusiness.deleteCategory(categoryId);
    }
}
