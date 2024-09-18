package com.toptal.quizhub.app.configurations.persistence.datasources;

import com.toptal.quizhub.app.configurations.persistence.datasources.properties.DataSourceConfiguration;
import com.toptal.quizhub.app.configurations.persistence.datasources.properties.PropertiesToHikariConfigMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.Closeable;

@Component
@Slf4j
@SuppressFBWarnings(value = {"EI_EXPOSE_REP"}, justification = "Temporarily suppressing. This needs to be checked asap")
public class DataSources implements Closeable {

    private final HikariDataSource dataSource;

    private final PropertiesToHikariConfigMapper mapper;

    private final MeterRegistry registry;

    public DataSources(DataSourceConfiguration dataSourceConfiguration,
                       PropertiesToHikariConfigMapper propertiesToHikariConfigMapper,
                       MeterRegistry registry) {

        this.mapper = propertiesToHikariConfigMapper;
        this.registry = registry;
        dataSource = createDataSources(dataSourceConfiguration);
    }

    private HikariDataSource createDataSources(DataSourceConfiguration dataSourceConfiguration) {

        final HikariConfig defaultHikariConfig = mapper.map(dataSourceConfiguration);
        return createDatasource(dataSourceConfiguration, defaultHikariConfig);

    }

    public DataSource get() {

        return dataSource;
    }

    @Override
    public void close() {

        dataSource.close();
    }

    private HikariDataSource createDatasource(DataSourceConfiguration properties,
                                              HikariConfig defaultHikariConfig) {

        final HikariConfig hikariConfig = mapper.map(defaultHikariConfig, properties);
        final HikariDataSource source = new HikariDataSource(hikariConfig);
        source.setMetricRegistry(registry);
        return source;
    }


}
