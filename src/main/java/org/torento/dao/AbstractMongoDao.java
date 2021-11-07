package org.torento.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public abstract class AbstractMongoDao {

    private MongoCollection<Document> collection;

    public AbstractMongoDao() {
        final MongoClient mongoClient = this.createClient();
        MongoDatabase database = mongoClient.getDatabase("assignment");
        collection = database.getCollection("assignments");
    }

    private MongoClient createClient() {
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                        .build());
        return mongoClient;
    }

    protected MongoCollection<Document> getCollection() {
       return this.collection;
    }
}
