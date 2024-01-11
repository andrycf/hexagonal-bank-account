package com.exalt.infrastructure;

import com.exalt.infrastructure.config.TestContainersConfiguration;
import org.springframework.boot.SpringApplication;

public class InfrastructureApplicationTests {
    public static void main(String[] args) {
        SpringApplication
                .from(InfrastructureApplication::main)
                .with(TestContainersConfiguration.class)
                .run(args);
    }
}
