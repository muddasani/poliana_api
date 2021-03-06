package com.poliana.config;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackages = "com.poliana.core")
@PropertySource(value={"classpath:mongo.properties"})
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    Environment env;

    @Override
    public String getDatabaseName() {
        return env.getProperty("mongo.dbname");
    }

    @Override
    protected UserCredentials getUserCredentials() {
        return new UserCredentials(
                  env.getProperty("mongo.username")
                , env.getProperty("mongo.password")
        );
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        Mongo mongo = new MongoClient(
                  env.getProperty("mongo.host")
                , Integer.parseInt(env.getProperty("mongo.port"))
        );
        mongo.setWriteConcern(WriteConcern.SAFE);
        return mongo;
    }

    @Bean
    public DB mongoDb() throws Exception {
        DB db =  mongo().getDB(env.getProperty("mongo.dbname"));
        db.authenticate(
                  env.getProperty("mongo.username")
                , env.getProperty("mongo.password").toCharArray()
        );
        return db;
    }

    @Bean
    public Datastore mongoStore() throws Exception {
        return new Morphia().createDatastore(
                mongo()
                , env.getProperty("mongo.dbname")
                , env.getProperty("mongo.username")
                , env.getProperty("mongo.password").toCharArray()
        );
    }

    @Override
    public String getMappingBasePackage() {
        return "com.poliana";
    }
}