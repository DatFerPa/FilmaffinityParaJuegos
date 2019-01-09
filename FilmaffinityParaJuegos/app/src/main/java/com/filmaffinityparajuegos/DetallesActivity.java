package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.UsuarioDatabase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetallesActivity extends AppCompatActivity {

    Videojuego videojuego;
    ImageView imagenVideojuego;
    TextView tituloVideojuego;
    TextView descripcionVideojuego;
    VideojuegoBase videojuegoAdd;
    Button botonTengo, botonQuiero;

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
        botonTengo = (Button) findViewById(R.id.btnLoTengo);
        botonQuiero = (Button) findViewById(R.id.btnLoQuiero);
        Picasso.get().load(Uri.parse(videojuego.getUri_imagen())).resize(500, 500).into(imagenVideojuego);
        new MyVolleyTengo(this).execute();
    }

    public void loTengo(View view) {
        new MyVolleyTener(this).execute("0");
    }

    public void loQuiero(View view) {
        new MyVolleyTener(this).execute("1");
    }


    public void addItems() {

    }

    private class MyVolleyTener extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();


        public MyVolleyTener(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            VideojuegosDatabase videojuegosDatabase = new VideojuegosDatabase();
            SharedPreferences sharedPref = ctx.getSharedPreferences(
                    getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
            String usuario = sharedPref.getString(getString(R.string.shared_nombre_user), getString(R.string.nombre_string));

            if (videojuegoAdd == null) {
                respuesta = videojuegosDatabase.addVideojuego(videojuego.getId_juego(), usuario, "", 0.0, params[0], ctx);
            } else {

                respuesta = videojuegosDatabase.actualizarVideojuegoUsuario(videojuego.getId_juego(), usuario, params[0], ctx);

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (respuesta.length() > 0 && respuesta != null) {
                Toast.makeText(ctx,
                        "Se ha actualizado la base", Toast.LENGTH_LONG).show();
                new MyVolleyTengo(ctx).execute();
            } else {
                Toast.makeText(ctx,
                        "Ha ocurrido un error", Toast.LENGTH_LONG).show();


            }
        }


    }

    private class MyVolleyTengo extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();


        public MyVolleyTengo(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            VideojuegosDatabase videojuegosDatabase = new VideojuegosDatabase();
            SharedPreferences sharedPref = ctx.getSharedPreferences(
                    getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
            String usuario = sharedPref.getString(getString(R.string.shared_nombre_user), getString(R.string.nombre_string));
            respuesta = videojuegosDatabase.getLoTiene(videojuego.getId_juego(), usuario, ctx);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (respuesta.length() > 0) {
                try {
                    JSONObject res = respuesta.getJSONObject(0);
                    if (res.get("tener_querer").toString().equals("0")) {
                        botonTengo.setText("No tengo");
                        botonQuiero.setText("Lo quiero");
                    } else {
                        botonQuiero.setText("No quiero");
                        botonTengo.setText("Lo tengo");
                    }
                    existeJuego(res);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }


    }

    private void existeJuego(JSONObject res) {
        try {
            JSONObject id_base = res.getJSONObject("_id");
            String id = id_base.getString("$oid");
            videojuegoAdd = new VideojuegoBase(id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
