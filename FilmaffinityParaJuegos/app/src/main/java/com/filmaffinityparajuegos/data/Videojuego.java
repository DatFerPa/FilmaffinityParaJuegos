package com.filmaffinityparajuegos.data;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Videojuego implements Parcelable {

    public String id_juego;
    public String titulo;
    public String descripcion;
    public String uri_imagen;
    public String uri_video;
    public String id_developer;


    public Videojuego(Parcel in) {
        id_juego = in.readString();
        titulo = in.readString();
        descripcion = in.readString();
        uri_imagen = in.readString();
        uri_video = in.readString();
        id_developer = in.readString();
    }

    public static final Creator<Videojuego> CREATOR = new Creator<Videojuego>() {
        @Override
        public Videojuego createFromParcel(Parcel in) {
            return new Videojuego(in);
        }

        @Override
        public Videojuego[] newArray(int size) {
            return new Videojuego[size];
        }
    };

    public Videojuego() {

    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "id_juego='" + id_juego + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", uri_imagen='" + uri_imagen + '\'' +
                ", uri_video='" + uri_video + '\'' +
                ", id_developer='" + id_developer + '\'' +
                '}';
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_juego);
        parcel.writeString(titulo);
        parcel.writeString(descripcion);
        parcel.writeString(uri_imagen);
        parcel.writeString(uri_video);
        parcel.writeString(id_developer);
    }
}
