package com.filmaffinityparajuegos.mongobase;

import com.filmaffinityparajuegos.data.Amistad;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;


public class VideojuegoDatabase {


    MongoDatabase base ;

    public VideojuegoDatabase(){
        base = new MongoConnection().getClient().getDatabase("videojogos");
    }

    public void addVideojuego(String id_videojuego, String id_usuario ,String comentario, double valoracion, int tener_querer){
        MongoCollection<Document> videojuegos =  base.getCollection("videojuegos");

        Document videojuego = new Document("id_videojuego", id_videojuego)
                .append("id_usurio", id_usuario)
                .append("comentario", comentario)
                .append("valoracion", valoracion)
                .append("tener_querer", tener_querer);

        videojuegos.insertOne(videojuego);
    }

    public List<VideojuegoBase> getVideojuego(String user_name){
        final List<VideojuegoBase> videojuegoBase = new ArrayList<VideojuegoBase>();

        MongoCollection<Document> collection = base.getCollection("amistades");

        Block<Document> videojuegos_database = new Block<Document>() {
            @Override
            public void apply(Document document) {
                String id_videojuego = document.get("id_videojuego").toString();
                String id_usuario = document.get("id_usuario").toString();
                String comentario = document.get("comentario").toString();
                int tener_querer = Integer.parseInt(document.get("tener_querer").toString());
                double valoracion = Double.parseDouble(document.get("valoracion").toString());


                VideojuegoBase videojuego = new VideojuegoBase(id_videojuego,comentario,tener_querer,valoracion,id_usuario);

                videojuegoBase.add(videojuego);
            }
        };

        collection.find(eq("id_usuario",user_name)).forEach(videojuegos_database);





        return videojuegoBase;
    }
}
