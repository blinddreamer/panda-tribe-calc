package com.example.pandatribe.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {
private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfig.class);
    @CacheEvict(cacheNames = "cacheCalculator", allEntries = true)
    public void evictCache(){
        LOGGER.info("Cache was cleared");
    }

    @Scheduled(fixedRate = 900_000) // 15 minutes = 900,000 milliseconds
    public void triggerEvictCache(){
        evictCache();
    }
}
