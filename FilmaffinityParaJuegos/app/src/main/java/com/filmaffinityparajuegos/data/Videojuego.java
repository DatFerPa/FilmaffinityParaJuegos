package com.filmaffinityparajuegos.data;

import android.net.Uri;

public class Videojuego {

    public String id_juego;
    public String titulo;
    public String descripcion;
    public String uri_imagen;
    public String uri_video;
    public String id_developer;


    public String getId_juego() {
        return id_juego;
    }

    public void setId_juego(String id_juego) {
        this.id_juego = id_juego;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUri_imagen() {
        return uri_imagen;
    }

    public void setUri_imagen(String uri_imagen) {
        this.uri_imagen = uri_imagen;
    }

    public String getUri_video() {
        return uri_video;
    }

    public void setUri_video(String uri_video) {
        this.uri_video = uri_video;
    }

    public String getId_developer() {
        return id_developer;
    }

    public void setId_developer(String id_developer) {
        this.id_developer = id_developer;
    }
}
