package com.exalt.infrastructure.config;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;


@Configuration
@ComponentScan(
        basePackageClasses = {CreateAccountUseCaseImpl.class},
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {DomainService.class})}
)
public class DomainConfig {
}
