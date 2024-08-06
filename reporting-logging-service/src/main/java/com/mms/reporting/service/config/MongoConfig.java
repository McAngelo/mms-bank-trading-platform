package com.mms.reporting.service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.context.annotation.ComponentScan;

@Configuration
@EnableMongoRepositories(basePackages = "com.mms.reporting.service.repositories", repositoryImplementationPostfix = "Impl")
@ComponentScan(basePackages = "com.mms.reporting.service")
public class MongoConfig {
    // Additional MongoDB configuration if necessary
}