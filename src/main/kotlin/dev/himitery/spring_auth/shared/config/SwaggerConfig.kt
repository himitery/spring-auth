package dev.himitery.spring_auth.shared.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().apply {
            components = Components().apply {
                addSecuritySchemes("Authentication", SecurityScheme().apply {
                    type = SecurityScheme.Type.HTTP
                    scheme = "bearer"
                    bearerFormat = "JWT"
                })
            }
            info = Info().apply {
                title = "Spring Auth"
                description = "JWT(Json Web Token) 기반 인증 시스템 구축"
                version = "1.0.0"
            }
        }
    }
}