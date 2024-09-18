package com.toptal.quizhub.app.configurations.rest;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.examples.Example;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.apache.commons.lang3.ArrayUtils;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.List;

@Configuration
@Conditional(SwaggerConfiguration.NotProdProfileCondition.class)
@ConditionalOnProperty(value = "springdoc.swagger-ui.enabled")
public class SwaggerConfiguration {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public GroupedOpenApi authenticateGroup() {

        return GroupedOpenApi.builder()
                .group("authentication")
                .pathsToMatch("/authenticate", "/logout")
                .build();
    }

    @Bean
    public GroupedOpenApi internalGroup(OperationCustomizer customizer) {

        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/api/v1/**")
                .addOperationCustomizer(customizer)
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("QUIZHUB API")
                        .description("Inventory Operations API")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("QUIZHUB Toptal Confluence")
                        .url("https://confluence.toptal.com/display/AFRLMT/OMS+Fulfillment+Operations"))
                .components(new Components()
                        .addSecuritySchemes(AUTHORIZATION_HEADER,
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .in(SecurityScheme.In.HEADER)
                                        .name(AUTHORIZATION_HEADER)))
                .addSecurityItem(new SecurityRequirement()
                        .addList(AUTHORIZATION_HEADER, List.of("read", "write")));
    }

    @Bean
    public OperationCustomizer tenantHeaderCustomizer() {

        final Example examplesItem = new Example();
        examplesItem.setValue("MA");
        return (operation, handlerMethod) -> operation.addParametersItem(
                new Parameter()
                        .in("header")
                        .required(true)
                        .description("Header that defines in which tenant the request will take place")
                        .addExample("MA", examplesItem)
                        .name("X-TenantId"));
    }

    static class NotProdProfileCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

            return !ArrayUtils.contains(context.getEnvironment().getActiveProfiles(), "prod");
        }
    }
}
