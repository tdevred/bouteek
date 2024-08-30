package com.tdevred.bouteek;

import com.tdevred.bouteek.authentication.DTOs.RegisterUserDto;
import com.tdevred.bouteek.authentication.entities.User;
import com.tdevred.bouteek.authentication.services.AuthenticationService;
import com.tdevred.bouteek.authentication.services.JwtService;
import com.tdevred.bouteek.business.DTO.CategoryDTO;
import com.tdevred.bouteek.business.DTO.ProductDTO;
import com.tdevred.bouteek.business.DTO.WarehouseDTO;
import com.tdevred.bouteek.business.StockBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.TimeUnit;

@Configuration
@Profile("dev")
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    /*
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, CategoryRepository categoryRepository, StockRepository stockRepository, WarehouseRepository warehouseRepository) {
        return args -> {
            Category eau = new Category("Eau", "Bon pour être hydratée");
            log.info("Preloading " + categoryRepository.save(
                    eau
            ));



            Product crista = new Product("Cristalline", 3.5, "Un pack de bouteilles de Cristalline bien fraîches !", eau);

            Product evian = new Product("Evian", 6.0, "Bouteille d'Evian à l'unité", eau);

            log.info("Preloading " + productRepository.save(crista));
            log.info("Preloading " + productRepository.save(evian));

            Warehouse calais = new Warehouse("calais", "15 avenue de calais");
            log.info("Preloading " + warehouseRepository.save(calais));

            log.info("Preloading " + stockRepository.save(
                    new Stock(crista, 5L, calais)
            ));
        };
    }
    */

    @Bean
    @Autowired
    CommandLineRunner initDatabase(StockBusiness stockBusiness, AuthenticationService authenticationService, JwtService jwtService) {
        return args -> {
            CategoryDTO eau = stockBusiness.addCategory("Eau", "Bon pour être hydratée");
            log.info("Preloading " + eau.toShortString());

            CategoryDTO sodas = stockBusiness.addCategory("Sodas", "Bon pour être hydratée mais avec du sucre");
            log.info("Preloading " + sodas.toShortString());

            CategoryDTO gouter = stockBusiness.addCategory("Goûters", "A consommer entre 16h et le repas du soir !");
            log.info("Preloading " + gouter.toShortString());

            ProductDTO crista = stockBusiness.addProduct("Cristalline", 3.5, "Un pack de bouteilles de Cristalline bien fraîches !", eau.getId());
            log.info("Preloading " + crista.toShortString());

            ProductDTO evian = stockBusiness.addProduct("Evian", 6.0, "Bouteille d'Evian à l'unité", eau.getId());
            log.info("Preloading " + evian.toShortString());

            for(int i = 1; i < 50; i++) {
                ProductDTO biscuit = stockBusiness.addProduct(STR."Biscuit n\{i}", 6.0, STR."Biscuit\{i} à l'unité", gouter.getId());
                log.info("Preloading " + biscuit.toShortString());
            }



            WarehouseDTO calais = stockBusiness.addWarehouse("calais", "15 avenue de calais");
            log.info("Preloading " + calais.toShortString());

            stockBusiness.addStock(calais.getId(), crista.getId(), 5);
            log.info("Added 5 stock to " + crista.toShortString() + " in warehouse " + calais.toShortString());

            RegisterUserDto registerUserDto = new RegisterUserDto();
            registerUserDto.setEmail("toto@gmail.com");
            registerUserDto.setPassword("totototi");
            registerUserDto.setFullName("toto");
            User user = authenticationService.signup(registerUserDto);
            String jwtToken = jwtService.generateToken(user);
            log.info("Token is <"+jwtToken+">");
        };
    }
}
