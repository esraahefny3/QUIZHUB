package com.toptal.quizhub.app.configurations.persistence;

import com.toptal.quizhub.app.configurations.persistence.datasources.DataSources;
import com.toptal.quizhub.app.configurations.persistence.properties.PersistenceProperties;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.dialect.PostgreSQLDialect;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@EntityScan("com.toptal.quizhub.persistence.jpa.entities")
@EnableJpaRepositories(basePackages = "com.toptal.quizhub.persistence.jpa.repositories")
@EnableJpaAuditing
@RequiredArgsConstructor
public class EntityManagerConfiguration {
    private final DataSources dataSources;

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(PersistenceProperties persistenceProperties) {

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

        final HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
        vendor.setDatabase(Database.POSTGRESQL);

        entityManagerFactoryBean.setPackagesToScan("com.toptal.quizhub.persistence.jpa.entities");
        entityManagerFactoryBean.setJpaVendorAdapter(vendor);
        entityManagerFactoryBean.setPersistenceProvider(new HibernatePersistenceProvider());
        entityManagerFactoryBean.setSharedCacheMode(SharedCacheMode.NONE); // todo :: change when defining second level cache

        final Properties properties = configureProperties(persistenceProperties);

        entityManagerFactoryBean.setJpaProperties(properties);
        entityManagerFactoryBean.setPersistenceUnitName("quizhub-database");

        return entityManagerFactoryBean;
    }

    private Properties configureProperties(PersistenceProperties persistenceProperties) {

        final Properties properties = new Properties();
        configureDatasource(persistenceProperties, properties);
        configureCache(properties);
        configureDebugOptions(persistenceProperties, properties);
        configureMetrics(properties, persistenceProperties);
        return properties;
    }

    private void configureDatasource(PersistenceProperties persistenceProperties, Properties properties) {

        properties.put(AvailableSettings.CONNECTION_HANDLING,
                PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION);
        properties.put(AvailableSettings.JPA_TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

        properties.put(AvailableSettings.DIALECT, PostgreSQLDialect.class.getName());
        properties.put(AvailableSettings.AUTO_CLOSE_SESSION, persistenceProperties.isAutoCloseSession());
        properties.put(AvailableSettings.NON_CONTEXTUAL_LOB_CREATION, persistenceProperties.isNonContextualLobCreation());
        properties.put(AvailableSettings.JDBC_TIME_ZONE, persistenceProperties.getJdbcTimeZone());
        properties.put(AvailableSettings.USE_GET_GENERATED_KEYS, persistenceProperties.isUseGetGeneratedKeys());

        final ConnectionProvider connectionProvider = new ConnectionProvider() {
            @Override
            public Connection getConnection() throws SQLException {
                return dataSources.get().getConnection();
            }

            @Override
            public void closeConnection(Connection conn) throws SQLException {
                dataSources.get().getConnection().close();
            }

            @Override
            public boolean supportsAggressiveRelease() {
                return false;
            }

            @Override
            public boolean isUnwrappableAs(Class<?> unwrapType) {
                return false;
            }

            @Override
            public <T> T unwrap(Class<T> unwrapType) {
                return null;
            }
        };
        properties.put(AvailableSettings.CONNECTION_PROVIDER, connectionProvider);
    }

    private void configureCache(Properties properties) {

        properties.put(AvailableSettings.USE_SECOND_LEVEL_CACHE, "false"); // todo :: change when defining second level cache
    }

    private void configureDebugOptions(PersistenceProperties persistenceProperties, Properties properties) {

        properties.put(AvailableSettings.SHOW_SQL, persistenceProperties.getDebug().isShowSql());
        properties.put(AvailableSettings.FORMAT_SQL, persistenceProperties.getDebug().isFormatSql());
        properties.put(AvailableSettings.USE_SQL_COMMENTS, persistenceProperties.getDebug().isShowSqlComments());
        final long logQuerySlowerThan = persistenceProperties.getDebug().getLogQuerySlowerThan();
        if (logQuerySlowerThan > 0) {
            properties.put(AvailableSettings.LOG_SLOW_QUERY, logQuerySlowerThan);
        }
    }

    private void configureMetrics(Properties properties, PersistenceProperties persistenceProperties) {

        properties.put(AvailableSettings.GENERATE_STATISTICS, persistenceProperties.isGenerateStatistics());
    }

}
