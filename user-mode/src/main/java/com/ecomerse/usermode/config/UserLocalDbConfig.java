package com.ecomerse.usermode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "repository.local",
        entityManagerFactoryRef = "userLocalEntityManagerFactory",
        transactionManagerRef = "userLocalTransactionManager"
)
public class UserLocalDbConfig {
}
