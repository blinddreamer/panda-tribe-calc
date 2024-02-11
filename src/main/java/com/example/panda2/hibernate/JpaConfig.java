package com.example.panda2.hibernate;

import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new CustomPhysicalNamingStrategy();
    }
}
