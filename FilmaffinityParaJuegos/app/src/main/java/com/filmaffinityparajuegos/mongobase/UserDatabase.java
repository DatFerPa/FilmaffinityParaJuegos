package com.filmaffinityparajuegos.mongobase;

import com.filmaffinityparajuegos.data.Usuario;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class UserDatabase {
    MongoDatabase base;

    public UserDatabase() {
        base = new MongoConnection().getClient().getDatabase("videojogos");
    }

    public void addUser(String name, String password){

        MongoCollection<Document> usuarios =  base.getCollection("usuarios");
        Document usuario = new Document("name", name).append("password" , password);

        usuarios.insertOne(usuario);
    }

    public Usuario getUser(String name, String password){

        MongoCollection<Document> collection = base.getCollection("usuarios");
        Document usuario_base = collection.find(eq("name", name)).first();
        if(usuario_base.get("password").toString().equals(password)){
            String nombre = usuario_base.get("name").toString();
            String contraseña = usuario_base.get("password").toString();
            Usuario usuario = new Usuario(nombre,contraseña);
            return usuario;
        }
        else
            return null;

    }

}
