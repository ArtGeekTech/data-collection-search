package com.artgeektech.datacollectionsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableMongoRepositories(basePackages = "com.artgeektech.datacollectionsearch.model.repository.mongo")
//@EnableElasticsearchRepositories(basePackages = "com.artgeektech.datacollectionsearch.model.repository.es")
@EnableMongoRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MongoRepository.class))
@EnableElasticsearchRepositories(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ElasticsearchRepository.class))
@SpringBootApplication
public class DataCollectionSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataCollectionSearchApplication.class, args);
    }
}
