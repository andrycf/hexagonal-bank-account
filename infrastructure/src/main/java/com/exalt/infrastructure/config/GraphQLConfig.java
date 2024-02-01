package com.exalt.infrastructure.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

import graphql.GraphQLContext;
import graphql.execution.CoercedVariables;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;

@Configuration
public class GraphQLConfig {
    
    @Bean
    GraphQLScalarType localDateTimeScalar(){
        return GraphQLScalarType.newScalar()
              .name("LocalDateTime")
              .description("A Java LocalDateTime type")
              .coercing(new Coercing<LocalDateTime,String>() {
                    @Override
                    public String serialize(Object input,
                            GraphQLContext graphQLContext, Locale locale)
                            throws CoercingSerializeException {
                        return input.toString();
                    }

                    @Override
                    public LocalDateTime parseValue(Object input, GraphQLContext graphQLContext,
                        Locale locale) throws CoercingParseValueException {
                        return LocalDateTime.parse(input.toString(), DateTimeFormatter.ISO_TIME);
                    }

                    @Override
                    public LocalDateTime parseLiteral(Value<?> input,
                        CoercedVariables variables, GraphQLContext graphQLContext,
                        Locale locale) throws CoercingParseLiteralException {
                            return LocalDateTime.parse(((StringValue) input).getValue(), DateTimeFormatter.ISO_TIME);
                    }
              })
              .build();
    }

    @Bean
    GraphQLScalarType doubleScalar(){
        return GraphQLScalarType.newScalar()
              .name("Double")
              .description("A Java Double type")
              .coercing(new Coercing<Double,String>() {
                    @Override
                    public String serialize(Object input,
                            GraphQLContext graphQLContext, Locale locale)
                            throws CoercingSerializeException {
                        return input.toString();
                    }

                    @Override
                    public Double parseValue(Object input, GraphQLContext graphQLContext,
                        Locale locale) throws CoercingParseValueException {
                        return Double.parseDouble(input.toString());
                    }

                    @Override
                    public Double parseLiteral(Value<?> input,
                        CoercedVariables variables, GraphQLContext graphQLContext,
                        Locale locale) throws CoercingParseLiteralException {
                            return Double.parseDouble(((StringValue) input).getValue());
                    }
              })
              .build();
    }

    @Bean
    RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .scalar(localDateTimeScalar())
                .scalar(doubleScalar());
    }
}
