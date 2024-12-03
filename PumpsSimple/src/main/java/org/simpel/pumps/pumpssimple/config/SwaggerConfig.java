package org.simpel.pumps.pumpssimple.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simpel")
                        .version("1.1")
                        .description("API"));
    }

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> {
            openApi.getPaths()
                    .keySet()
                    .removeIf(path ->
                !path.startsWith("/api/simple") // Укажите путь, который хотите оставить
            );
        };
    }
}
