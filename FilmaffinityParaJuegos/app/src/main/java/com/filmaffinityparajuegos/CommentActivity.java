package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentActivity extends AppCompatActivity {
    Videojuego videojuego;
    VideojuegoBase videojuegoComentar;
    EditText textoComentar;
    Button btnComentar;
    Button btnCancelar;
    RatingBar ratingBar;
    String usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Intent intent = getIntent();
        videojuego = intent.getParcelableExtra(MenuPrincipalActivity.NV);
        textoComentar = (EditText) findViewById(R.id.txtComentario);
        btnComentar = (Button) findViewById(R.id.btnComentar);
        btnCancelar = (Button) findViewById(R.id.btnCancelar);
        ratingBar = (RatingBar) findViewById(R.id.raitingComentar);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        usuario =sharedPref.getString(getString(R.string.shared_nombre_user),getString(R.string.nombre_string));
        new CargarJuego(this).execute();
    }

    public void senComment(View view){
        new MyVolleyComentar(this).execute();
    }

    public void comeBack(View view){
        finish();
    }


    private class MyVolleyComentar extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();


        public MyVolleyComentar(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            VideojuegosDatabase videojuegosDatabase = new VideojuegosDatabase();
            String comentario_usuario = textoComentar.getText().toString();
            String raiting = String.valueOf(ratingBar.getRating());
            String tengo_quiero = String.valueOf(videojuegoComentar.getTengo_quiero());
            respuesta = videojuegosDatabase.actualizarVideojuegoUsuario(videojuego.getId_juego(),usuario, tengo_quiero,comentario_usuario,raiting,ctx);


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (respuesta.length() > 0) {
                Toast.makeText(ctx,
                        "Se ha añadido el comentario y valoración", Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(ctx,
                        "No se ha podido realizar comentario", Toast.LENGTH_LONG).show();
            }
        }


    }

    private class CargarJuego extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();


        public CargarJuego(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            VideojuegosDatabase videojuegosDatabase = new VideojuegosDatabase();

            respuesta = videojuegosDatabase.getVideojuego(videojuego.getId_juego(),ctx);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (respuesta.length() > 0) {
                JSONObject res = null;
                try {
                    res = respuesta.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                existeJuego(res);
            } else {
                Toast.makeText(ctx,
                        "No se ha podido realizar comentario", Toast.LENGTH_LONG).show();
            }
        }


    }

    private void existeJuego(JSONObject res) {
        try {
            JSONObject id_base = res.getJSONObject("_id");
            String usuario = res.getString("usuario");
            String comentario = res.getString("comentario");
            String valoracion = res.getString("valoracion");
            int tener_querer = res.getInt("tener_querer");
            String id = id_base.getString("$oid");
            videojuegoComentar = new VideojuegoBase(id,comentario,tener_querer,Double.parseDouble(valoracion),usuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
