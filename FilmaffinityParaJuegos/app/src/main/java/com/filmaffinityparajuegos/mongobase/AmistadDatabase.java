package com.filmaffinityparajuegos.mongobase;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class AmistadDatabase {

    DB base = new MongoConnection().getClient().getDB("videojogos");

    public void addAmistad(String nombre1, String nombre2){
        DBCollection usuarios =  base.getCollection("amistades");
        BasicDBObject usuario = new BasicDBObject();
        usuario.put("usuario1",nombre1);
        usuario.put("usuario2", nombre2);

        usuarios.insert(usuario);
    }
}
