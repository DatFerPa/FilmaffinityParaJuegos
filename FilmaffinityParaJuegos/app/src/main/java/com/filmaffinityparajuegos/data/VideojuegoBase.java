package com.filmaffinityparajuegos.data;

public class VideojuegoBase {

    String id_videojuego;
    String comentario;
    int tengo_quiero;
    double valoracion;
    String nombre_usuario;


    public VideojuegoBase(String id_videojuego, String comentario, int tengo_quiero, double valoracion, String nombre_usuario) {
        this.id_videojuego = id_videojuego;
        this.comentario = comentario;
        this.tengo_quiero = tengo_quiero;
        this.valoracion = valoracion;
        this.nombre_usuario = nombre_usuario;
    }

    public String getId_videojuego() {
        return id_videojuego;
    }

    public void setId_videojuego(String id_videojuego) {
        this.id_videojuego = id_videojuego;
    }


    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getTengo_quiero() {
        return tengo_quiero;
    }

    public void setTengo_quiero(int tengo_quiero) {
        this.tengo_quiero = tengo_quiero;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }
}
