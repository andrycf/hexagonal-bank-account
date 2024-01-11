package com.exalt.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Christin ANDRIANAMBININA",
                        email = "andrycf24@gmail.com",
                        url = "https://www.acf.mg"
                ),
                description = "OpenApi documentation for Application Bank Account",
                title = "KATA - Bank Account",
                version = "1.0",
                license = @License(
                        name = "Licence name",
                        url = "https://www.acf.mg"
                ),
                termsOfService = "Terms of service"
        )
)
public class OpenApiConfig {
}
