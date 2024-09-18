package com.toptal.quizhub.persistence.jpa.configuration;

import com.toptal.quizhub.persistence.jpa.Faker;
import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@ComponentScan(value = {"com.toptal.quizhub.persistence.jpa"})
@EntityScan("com.toptal.quizhub.persistence.jpa.entities")
@EnableJpaRepositories(basePackages = "com.toptal.quizhub.persistence.jpa.repositories")
@EnableTransactionManagement
@EnableAutoConfiguration
public class PersistenceConfiguration {

    @Bean
    Faker faker() {

        return new Faker();
    }

    @ConditionalOnMissingBean(FlywayMigrationStrategy.class)
    @Bean
    FlywayMigrationStrategy flywayMigrationStrategy() {

        return Flyway::migrate;
    }
}
