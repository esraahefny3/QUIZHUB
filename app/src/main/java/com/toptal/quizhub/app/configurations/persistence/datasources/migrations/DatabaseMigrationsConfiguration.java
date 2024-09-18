package com.toptal.quizhub.app.configurations.persistence.datasources.migrations;

import com.toptal.quizhub.app.configurations.persistence.datasources.DataSources;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


@Configuration
@ConditionalOnProperty("flyway.migrations.enabled")
@RequiredArgsConstructor
public class DatabaseMigrationsConfiguration {

    private final DataSources dataSources;
    @Value("${flyway.migrations.location}")
    private String location;

    @PostConstruct
    public void migrateDataSources() {

        final DataSource dataSource = this.dataSources.get();
        migrate(dataSource);
    }

    private void migrate(DataSource dataSource) {

        final Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(location)
                .load();

        flyway.migrate();
    }
}
