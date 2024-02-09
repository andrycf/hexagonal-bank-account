package com.exalt.infrastructure.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.exalt.domain.annotation.DomainService;
import com.exalt.domain.usecases.CreateAccountUseCaseImpl;

@Configuration
@ComponentScan(basePackageClasses = { CreateAccountUseCaseImpl.class }, includeFilters = {
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { DomainService.class }) })
public class DomainConfig {
}
