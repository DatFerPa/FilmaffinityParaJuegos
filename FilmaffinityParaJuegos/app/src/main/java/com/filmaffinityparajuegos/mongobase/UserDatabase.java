package com.filmaffinityparajuegos.mongobase;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoDatabase;

public class UserDatabase {
    DB base = new MongoConnection().getClient().getDB("videojogos");

    public UserDatabase() {
    }

    public void addUser(){
        DBCollection usuarios =  base.getCollection("usuarios");
    }

}
