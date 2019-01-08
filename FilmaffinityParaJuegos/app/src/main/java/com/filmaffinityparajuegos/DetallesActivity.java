package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.mongobase.UsuarioDatabase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

public class DetallesActivity extends AppCompatActivity {

    Videojuego videojuego;
    ImageView imagenVideojuego;
    TextView tituloVideojuego;
    TextView descripcionVideojuego;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Intent intent = getIntent();
        videojuego = intent.getParcelableExtra(MenuPrincipalActivity.NV);
        imagenVideojuego = (ImageView) findViewById(R.id.imagenJuego);
        tituloVideojuego = (TextView) findViewById(R.id.id_titulo);
        descripcionVideojuego = (TextView) findViewById(R.id.id_resumen);
        tituloVideojuego.setText(videojuego.getTitulo());
        descripcionVideojuego.setText(videojuego.getDescripcion());
        Picasso.get().load(Uri.parse(videojuego.getUri_imagen())).resize(500  ,500).into(imagenVideojuego);
    }

    public void addItems(){

    }


}
