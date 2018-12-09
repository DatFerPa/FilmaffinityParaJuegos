package com.filmaffinityparajuegos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.filmaffinityparajuegos.data.Videojuego;
import com.squareup.picasso.Picasso;

public class DetallesActivity extends AppCompatActivity {

    Videojuego videojuego;
    ImageView imagenVideojuego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent intent = getIntent();
        videojuego = intent.getParcelableExtra(MenuPrincipalActivity.NV);
        System.out.println(videojuego.getDescripcion());
        imagenVideojuego = (ImageView) findViewById(R.id.imagenJuego);
        Picasso.get().load(Uri.parse(videojuego.getUri_imagen())).resize(500  ,500).into(imagenVideojuego);

    }

    public void addItems(){

    }
}
