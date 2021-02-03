//package com.artgeektech.datacollectionsearch.config;
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import org.bson.UuidRepresentation;
//import org.bson.codecs.UuidCodecProvider;
//import org.bson.codecs.configuration.CodecRegistries;
//import org.bson.codecs.configuration.CodecRegistry;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//
//@Configuration
//public class MongoConfig extends AbstractMongoClientConfiguration {
//
////    public @Bean
////    MongoClient mongoClient() {
////        return MongoClients.create("mongodb://localhost:27017");
////    }
////
////    public @Bean
////    MongoTemplate mongoTemplate() {
////        return new MongoTemplate(mongoClient(), "feedback_db");
////    }
//
//
//    private MongoClientSettings buildMongoClientSettings(String clusterUrl) {
//        return MongoClientSettings.builder()
//                .applyConnectionString(new ConnectionString(clusterUrl))
//                .build();
//    }
//
//    private CodecRegistry codecRegistries() {
//        return CodecRegistries.fromRegistries(
//                // save uuids as UUID, instead of LUUID
//                CodecRegistries.fromProviders(new UuidCodecProvider(UuidRepresentation.STANDARD)),
//                MongoClientSettings.getDefaultCodecRegistry()
//        );
//    }
//
//    @Override
//    protected String getDatabaseName() {
//        return "feedback_db";
//    }
//}
