package com.tdevred.bouteek;

import com.tdevred.bouteek.business.*;
import com.tdevred.bouteek.business.DTO.*;
import com.tdevred.bouteek.business.exceptions.NoProductException;
import com.tdevred.bouteek.business.exceptions.NoStockException;
import com.tdevred.bouteek.business.exceptions.NoWarehouseException;
import com.tdevred.bouteek.business.exceptions.NotEnoughQuantityForStockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    Logger logger = LoggerFactory.getLogger(StockController.class);

    @Autowired
    private final StockBusiness stockBusiness;

    public StockController(StockBusiness stockBusiness) {
        this.stockBusiness = stockBusiness;
    }

    @GetMapping("/stocks")
    public List<StockDTO> getAllStocks() {
        return stockBusiness.getAllStocks();
    }

    @GetMapping("/warehouses/{warehouseId}/stocks")
    public List<StockDTO> getAllStocksInWarehouse(@PathVariable long warehouseId) throws NoWarehouseException {
        return stockBusiness.getAllStocksInWarehouse(warehouseId);
    }

    @GetMapping("/stocks/{productId}")
    public List<StockDTO> getAllStocksForProduct(@PathVariable long productId) throws NoProductException {
        return stockBusiness.getAllStocksForProduct(productId);
    }

    @GetMapping("/stocks/{productId}/{warehouseId}")
    public List<StockDTO> getStocksForProductInWarehouse(@PathVariable long productId, @PathVariable long warehouseId) throws NoProductException {
        return stockBusiness.getStocksForProductInWarehouse(productId, warehouseId);
    }

    @PostMapping("/stocks/{productId}/{warehouseId}")
    public StockDTO addStockForProductInWarehouse(@PathVariable long productId, @PathVariable long warehouseId, @RequestBody long quantity) throws NoProductException, NoWarehouseException, NoStockException, NotEnoughQuantityForStockException {
        if(quantity == 0) {
            return stockBusiness.getStocksForProductInWarehouse(productId, warehouseId).getFirst();
        }

        if(quantity > 0) {
            return stockBusiness.addStockForProductInWarehouse(productId, warehouseId, quantity);
        } else {
            return stockBusiness.removeStockForProductInWarehouse(productId, warehouseId, -quantity);
        }
    }
}
