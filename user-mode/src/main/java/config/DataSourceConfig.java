package config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "localDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.local")
    public DataSource localDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sharedDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.shared")
    public DataSource sharedDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "keycloakDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.keycloak")
    public DataSource keycloakDataSource(){
        return DataSourceBuilder.create().build();
    }


}
