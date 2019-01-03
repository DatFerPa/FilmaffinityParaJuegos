package com.filmaffinityparajuegos.mongobase;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

    MongoClientURI uri ;
    MongoClient mongoClient ;
    MongoDatabase db;

    public MongoConnection(){
        uri = new MongoClientURI( "mongodb://admin:videojogos2019@ds111420.mlab.com:11420/videojogos" );
        mongoClient = new MongoClient(uri);
        db = mongoClient.getDatabase(uri.getDatabase());
    }

    public MongoDatabase getBase(){
        return db;
    }

    public MongoClient getClient(){
        return mongoClient;
    }
}
