package com.exalt.infrastructure.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods=true)
public class TestContainersConfiguration {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postreSQlContainer() {
        return new PostgreSQLContainer<>("postgres:latest");
    }
}
