package com.filmaffinityparajuegos.mongobase;

import com.filmaffinityparajuegos.data.Amistad;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class AmistadDatabase {

    MongoDatabase base = new MongoConnection().getClient().getDatabase("videojogos");

    public void addAmistad(String nombre1, String nombre2){
        MongoCollection<Document> amistades =  base.getCollection("amistades");

        Document amistad = new Document("usuario1", nombre1).append("usuario2" , nombre2);


        amistades.insertOne(amistad);
    }

    public List<Amistad> getAmistades(String usuario1){
        final List<Amistad> amistades = new ArrayList<Amistad>();

        MongoCollection<Document> collection = base.getCollection("amistades");

        Block<Document> amistades_base = new Block<Document>() {
            @Override
            public void apply(Document document) {
                String usuario1 = document.get("usuario1").toString();
                String usuario2 = document.get("usuario2").toString();

                Amistad amistad = new Amistad(usuario1,usuario2);

                amistades.add(amistad);
            }
        };

        collection.find(eq("usuario1",usuario1)).forEach(amistades_base);




        return amistades;
    }


}
