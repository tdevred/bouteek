package com.tdevred.bouteek.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@EnableCaching
public class CacheConfig {
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("bouteeq");
    }
}
