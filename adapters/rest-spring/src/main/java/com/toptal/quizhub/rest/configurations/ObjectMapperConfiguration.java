package com.toptal.quizhub.rest.configurations;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.math.BigDecimal;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public CsvMapperProvider csvMapperProvider() {

        return CsvMapper::new;
    }

    @Bean
    @ConditionalOnMissingBean(Jackson2ObjectMapperBuilder.class)
    public Jackson2ObjectMapperBuilder objectMapper() {

        return Jackson2ObjectMapperBuilder.json()
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY) // auto-detect all member fields
                .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE) // but only public getters
                .visibility(PropertyAccessor.IS_GETTER, JsonAutoDetect.Visibility.NONE)
                .serializerByType(BigDecimal.class, NumberSerializer.bigDecimalAsStringSerializer())
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .featuresToEnable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                .failOnUnknownProperties(false);
    }

    public interface CsvMapperProvider {

        CsvMapper getCsvMapper();
    }
}
