package com.toptal.quizhub.app.configurations.persistence.datasources.properties;

import com.zaxxer.hikari.HikariConfig;
import org.apache.commons.collections4.MapUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertiesToHikariConfigMapper {

    @Mapping(target = "jdbcUrl", source = "url")
    @Mapping(target = "driverClassName", ignore = true)
    HikariConfig map(@MappingTarget HikariConfig defaultConfiguration,
                     DataSourceConfiguration source);

    HikariConfig map(DataSourceConfiguration defaultProperties);

    @AfterMapping
    default void addExtraConfigs(DataSourceConfiguration source,
                                 @MappingTarget HikariConfig hikariConfig) {

        setDriver(source, hikariConfig);
        MapUtils.emptyIfNull(source.getExtraConfigs())
                .forEach(hikariConfig::addDataSourceProperty);
    }

    private void setDriver(DataSourceConfiguration source, HikariConfig hikariConfig) {

        final String driverClassName = source.getDriverClassName();
        if (driverClassName != null) {
            hikariConfig.setDriverClassName(driverClassName);
        }
    }


}
