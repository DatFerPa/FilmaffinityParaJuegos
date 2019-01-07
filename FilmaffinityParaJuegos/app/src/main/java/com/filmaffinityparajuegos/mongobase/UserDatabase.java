package com.filmaffinityparajuegos.mongobase;

import com.filmaffinityparajuegos.data.Usuario;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

public class UserDatabase {
    MongoDatabase base = new MongoConnection().getClient().getDatabase("videojogos");

    public UserDatabase() {
    }

    public void addUser(String name, String password){

        MongoCollection<Document> usuarios =  base.getCollection("usuarios");
        Document usuario = new Document("name", name).append("password" , password);

        usuarios.insertOne(usuario);
    }

}
