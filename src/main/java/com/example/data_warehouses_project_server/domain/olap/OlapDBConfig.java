package com.example.data_warehouses_project_server.domain.olap;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "olapEntityManagerFactory",
        transactionManagerRef = "olapTransactionManager",
        basePackages = {"com.example.data_warehouses_project_server.domain.olap.repository"}
)
public class OlapDBConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.olap.datasource")
    public DataSource olapDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean olapEntityManagerFactory(
            EntityManagerFactoryBuilder builder, @Qualifier("olapDataSource") DataSource olapDataSource
    ) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "none");

        return builder.dataSource(olapDataSource)
                .properties(properties)
                .packages("com.example.data_warehouses_project_server.domain.olap.entity")
                .persistenceUnit("olap")
                .build();
    }

    @Bean
    public PlatformTransactionManager olapTransactionManager(@Qualifier("olapEntityManagerFactory") EntityManagerFactory olapEntityManagerFactory) {
        return new JpaTransactionManager(olapEntityManagerFactory);
    }
}
