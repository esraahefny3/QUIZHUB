package com.toptal.quizhub.app.datasources.properties;

import com.toptal.quizhub.app.configurations.persistence.datasources.properties.DataSourceConfiguration;
import com.toptal.quizhub.app.configurations.persistence.datasources.properties.PropertiesToHikariConfigMapper;
import com.toptal.quizhub.app.configurations.persistence.datasources.properties.PropertiesToHikariConfigMapperImpl;
import com.zaxxer.hikari.HikariConfig;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PropertiesToHikariConfigMapperTest {

    private final Faker faker = new Faker();

    private final PropertiesToHikariConfigMapper subject = new PropertiesToHikariConfigMapperImpl();

    @Test
    void map_defaultConfiguration_thenCreatesHikariConfig() {

        // Given
        final boolean autoCommit = faker.bool().bool();
        final int connectionTimeout = faker.number().numberBetween(250, 1000);
        final String driverClassName = "org.postgresql.Driver";
        final int idleTimeout = faker.number().numberBetween(1, 10);
        final int maximumPoolSize = faker.number().numberBetween(1, 10);
        final int maxLifetime = faker.number().numberBetween(1000, 99999);
        final int leakDetectionThreshold = faker.number().numberBetween(1000, 99999);
        final int minimumIdle = faker.number().numberBetween(1000, 99999);

        final DataSourceConfiguration input = new DataSourceConfiguration();
        input.setAutoCommit(autoCommit);
        input.setConnectionTimeout((long) connectionTimeout);
        input.setDriverClassName(driverClassName);
        input.setIdleTimeout((long) idleTimeout);
        input.setMaximumPoolSize(maximumPoolSize);
        input.setMaxLifetime((long) maxLifetime);
        input.setLeakDetectionThreshold(leakDetectionThreshold);
        input.setExtraConfigs(Map.of());
        input.setMinimumIdle(minimumIdle);

        // When
        final HikariConfig result = subject.map(input);

        // Then
        final HikariConfig expected = new HikariConfig();
        expected.setAutoCommit(autoCommit);
        expected.setConnectionTimeout(connectionTimeout);
        expected.setDriverClassName(driverClassName);
        expected.setIdleTimeout(idleTimeout);
        expected.setMaximumPoolSize(maximumPoolSize);
        expected.setMaxLifetime(maxLifetime);
        expected.setLeakDetectionThreshold(leakDetectionThreshold);
        expected.setMinimumIdle(minimumIdle);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void  map_existingConfiguration_overridesOnlyNotNulls() {

        // Given
        final boolean defaultAutoCommit = faker.bool().bool();
        final int defaultConnectionTimeout = faker.number().numberBetween(250, 1000);
        final String defaultDriverClassName = "org.postgresql.Driver";
        final int defaultIdleTimeout = faker.number().numberBetween(1, 10);
        final int defaultMaximumPoolSize = faker.number().numberBetween(1, 10);
        final int defaultMaxLifetime = faker.number().numberBetween(1000, 99999);
        final int defaultLeakDetectionThreshold = faker.number().numberBetween(1000, 99999);
        final int defaultMinimumIdle = faker.number().numberBetween(1000, 99999);

        final int idleTimeout = faker.number().numberBetween(1, 10);
        final int maximumPoolSize = faker.number().numberBetween(1, 10);
        final int leakDetectionThreshold = faker.number().numberBetween(1000, 99999);

        final DataSourceConfiguration input = new DataSourceConfiguration();
        input.setIdleTimeout((long) idleTimeout);
        input.setMaximumPoolSize(maximumPoolSize);
        input.setLeakDetectionThreshold(leakDetectionThreshold);
        input.setExtraConfigs(Map.of());

        final HikariConfig defaultConfiguration = new HikariConfig();
        defaultConfiguration.setAutoCommit(defaultAutoCommit);
        defaultConfiguration.setConnectionTimeout(defaultConnectionTimeout);
        defaultConfiguration.setDriverClassName(defaultDriverClassName);
        defaultConfiguration.setIdleTimeout(defaultIdleTimeout);
        defaultConfiguration.setMaximumPoolSize(defaultMaximumPoolSize);
        defaultConfiguration.setMaxLifetime(defaultMaxLifetime);
        defaultConfiguration.setLeakDetectionThreshold(defaultLeakDetectionThreshold);
        defaultConfiguration.setMinimumIdle(defaultMinimumIdle);

        // When
        final HikariConfig result = subject.map(defaultConfiguration, input);

        // Then
        final HikariConfig expected = new HikariConfig();
        expected.setAutoCommit(defaultAutoCommit);
        expected.setConnectionTimeout(defaultConnectionTimeout);
        expected.setDriverClassName(defaultDriverClassName);
        expected.setIdleTimeout(idleTimeout);
        expected.setMaximumPoolSize(maximumPoolSize);
        expected.setMaxLifetime(defaultMaxLifetime);
        expected.setLeakDetectionThreshold(leakDetectionThreshold);
        expected.setMinimumIdle(defaultMinimumIdle);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
