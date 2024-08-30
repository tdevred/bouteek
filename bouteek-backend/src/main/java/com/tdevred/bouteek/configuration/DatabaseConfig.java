package com.tdevred.bouteek.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);

    @Value("${spring.datasource.url}")
    String databaseUrl;

    @Value("${spring.datasource.username}")
    String databaseUsername;

    @Value("${spring.datasource.password}")
    String databasePassword;

    @Value("${spring.datasource.driver-class-name}")
    String databaseDriverClass;
    @Bean
    public DataSource getDataSource() {
        logger.info("Starting database config with following values:");
        logger.info(databaseDriverClass);
        logger.info(databaseUrl);
        logger.info(databaseUsername);
        logger.info(databasePassword);

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        //dataSourceBuilder.driverClassName("org.h2.Driver");
        dataSourceBuilder.driverClassName(databaseDriverClass);
        dataSourceBuilder.url(databaseUrl);
        dataSourceBuilder.username(databaseUsername);
        dataSourceBuilder.password(databasePassword);
        return dataSourceBuilder.build();
    }
}
