package com.filmaffinityparajuegos.mongobase;

import com.filmaffinityparajuegos.data.Usuario;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoDatabase;

public class UserDatabase {
    DB base = new MongoConnection().getClient().getDB("videojogos");

    public UserDatabase() {
    }

    public void addUser(String name, String password){

        DBCollection usuarios =  base.getCollection("usuarios");
        BasicDBObject usuario = new BasicDBObject();
        usuario.put("name",name);
        usuario.put("password", password);

        usuarios.insert(usuario);
    }

}
