package config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class JpaConfig {

    @Primary
    @Bean(name = "userLocalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean userLocalEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("userLocalDataSource") DataSource dataSource
            ){
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgresDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");


        return builder.dataSource(dataSource)
                .packages("entity.localEntity")
                .properties(properties)
                .persistenceUnit("userLocal")
                .build();
    }

    @Bean(name = "sharedEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sharedEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("sharedDataSource") DataSource dataSource
    ){
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgresDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(dataSource)
                .packages("entity.sharedEntity")
                .properties(properties)
                .persistenceUnit("shared")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean keycloakEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("keycloakDataSource") DataSource dataSource
    ){
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgresDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder
                .dataSource(dataSource)
                .packages("entity.keycloakEntity")
                .properties(properties)
                .persistenceUnit("keycloak")
                .build();
    }

    @Bean(name = "userLocalTransactionManager")
    public PlatformTransactionManager userLocalTransactionManager(
            @Qualifier("userLocalEntityManagerFactory")EntityManagerFactory factory
            ){
        return new JpaTransactionManager(factory);
    }

    @Bean(name = "sharedTransactionManager")
    public PlatformTransactionManager sharedTransactionManager(
            @Qualifier("sharedEntityManagerFactory")EntityManagerFactory factory
    ){
        return new JpaTransactionManager(factory);
    }

    @Bean(name = "keycloakTransactionManager")
    public PlatformTransactionManager keycloakTransactionManager(
            @Qualifier("keycloakEntityManagerFactory")EntityManagerFactory factory
    ){
        return new JpaTransactionManager(factory);
    }
}
