package com.filmaffinityparajuegos.data;

import java.util.ArrayList;
import java.util.List;

public class Amistad {

    private String usuario1,usuario2;

    public Amistad(String usuario1,String usuario2){
        this.usuario1 = usuario1;
        this.usuario2 = usuario2;
    }

    public String getUsuario1() {
        return usuario1;
    }

    public void setUsuario1(String usuario1) {
        this.usuario1 = usuario1;
    }

    public String getUsuario2() {
        return usuario2;
    }

    public void setUsuario2(String usuario2) {
        this.usuario2 = usuario2;
    }

    public List<String> getUsuarios(){
        List<String> amistad = new ArrayList<String>();
        amistad.add(usuario1);
        amistad.add(usuario2);
        return amistad;
    }
}
