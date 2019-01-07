package com.filmaffinityparajuegos.mongobase;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;


public class VideojuegoDatabase {


    MongoDatabase base ;

    public VideojuegoDatabase(){
        base = new MongoConnection().getClient().getDatabase("videojogos");
    }

    public void addVideojuego(int id_videojuego, String id_usuario ,String comentario, double valoracion, int tener_querer){
        MongoCollection<Document> videojuegos =  base.getCollection("videojuegos");

        Document videojuego = new Document("id_videojuego", id_videojuego)
                .append("id_usurio", id_usuario)
                .append("comentario", comentario)
                .append("valoracion", valoracion)
                .append("tener_querer", tener_querer);

        videojuegos.insertOne(videojuego);
    }
}
