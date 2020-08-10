package org.nr.backendtask.security;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfigurer implements WebMvcConfigurer {
    private final BasicAuthFilter basicAuthFilter;

    @Autowired
    public SecurityConfigurer(BasicAuthFilter basicAuthFilter) {
        this.basicAuthFilter = basicAuthFilter;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().components(new Components().addSecuritySchemes("Authorization",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Basic")));
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicAuthFilter);
    }
}
