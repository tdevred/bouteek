package com.tdevred.bouteek;

import com.tdevred.bouteek.business.StockBusiness;
import com.tdevred.bouteek.repositories.WarehouseRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BouteekApplication {

	public static void main(String[] args) {
		SpringApplication.run(BouteekApplication.class, args);
	}
}
