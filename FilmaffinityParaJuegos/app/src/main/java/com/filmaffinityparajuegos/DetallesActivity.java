package com.filmaffinityparajuegos;

import android.animation.FloatArrayEvaluator;
import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.adapter.ComentarioAdapter;
import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.UsuarioDatabase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetallesActivity extends AppCompatActivity {

    Videojuego videojuego;
    ImageView imagenVideojuego;
    TextView tituloVideojuego;
    TextView descripcionVideojuego;
    VideojuegoBase videojuegoAdd;
    Button botonTengo, botonQuiero;
    FloatingActionButton btnComentar;
    RatingBar raitingBarJuego;

    public static final String NV = "com.filmaffinityparajuegos";



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
        btnComentar = (FloatingActionButton) findViewById(R.id.btnComentar);
        raitingBarJuego = (RatingBar) findViewById(R.id.ratingBar);
        raitingBarJuego.setEnabled(false);

        Picasso.get().load(Uri.parse(videojuego.getUri_imagen())).resize(500, 500).into(imagenVideojuego);

        new MyVolleyTengo(this).execute();

    }

    public void comentar(View view) {
        if (videojuegoAdd != null) {
            if (videojuegoAdd.getTengo_quiero() == 0) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra(NV, videojuego);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(),
                        "Se debe tener el juego", Toast.LENGTH_LONG).show();
        } else
            Toast.makeText(getApplicationContext(),
                    "Se debe tener el juego", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new MyVolleyTengo(getApplicationContext()).execute();

        cargarComentarios();
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

                respuesta = videojuegosDatabase.actualizarVideojuegoUsuario(videojuegoAdd.getId_videojuego(),videojuego.getId_juego(), usuario, params[0], videojuegoAdd.getComentario(), String.valueOf(videojuegoAdd.getValoracion()), ctx);

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

                        botonTengo.setEnabled(false);
                        botonQuiero.setEnabled(true);

                    } else {

                        botonQuiero.setEnabled(false);
                        botonTengo.setEnabled(true);
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
            String usuario = res.getString("usuario");
            String comentario = res.getString("comentario");
            String valoracion = res.getString("valoracion");
            int tener_querer = res.getInt("tener_querer");
            String id = id_base.getString("$oid");
            videojuegoAdd = new VideojuegoBase(id, comentario, tener_querer, Double.parseDouble(valoracion), usuario);
            raitingBarJuego.setRating(Float.parseFloat(valoracion));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    /*
    TODO
    Cosas de los comenetarios
     */

    private RecyclerView recyclerViewComentarios;
    private List<VideojuegoBase> comentariosDelVideojuego;
    private ComentarioAdapter comentarioAdapter;

    private void cargarComentarios() {
        comentariosDelVideojuego = new ArrayList<>();
        recyclerViewComentarios = findViewById(R.id.RecyclerViewComentario);

        RecyclerView.LayoutManager myLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewComentarios.setLayoutManager(myLayoutManager);
        recyclerViewComentarios.setItemAnimator(new DefaultItemAnimator());

        recyclerViewComentarios.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        comentarioAdapter = new ComentarioAdapter(comentariosDelVideojuego);
        recyclerViewComentarios.setAdapter(comentarioAdapter);

        new MyVolleyComentariosDeUnJuego(this).execute();

    }

    private class MyVolleyComentariosDeUnJuego extends AsyncTask<Void, Void, List<VideojuegoBase>> {
        private Context context;
        private JSONArray respuesta = new JSONArray();
        List<VideojuegoBase> videojuegosBase = new ArrayList<>();

        public MyVolleyComentariosDeUnJuego(Context hostContext) {
            context = hostContext;
        }

        @Override
        protected List<VideojuegoBase> doInBackground(Void... voids) {

            VideojuegosDatabase vgdb = new VideojuegosDatabase();
            respuesta = vgdb.getVideojuego(videojuego.getId_juego(), context);

            return videojuegosBase;
        }

        @Override
        protected void onPostExecute(List<VideojuegoBase> videojuegoBases) {
            JSONObject object = null;
            if (respuesta.length() > 0) {
                for (int i = 0; i < respuesta.length(); i++) {
                    try {
                        object = respuesta.getJSONObject(i);
                        if(!("").equals(object.getString("comentario"))&&object.getString("comentario")!= null) {
                            VideojuegoBase juegoBase = new VideojuegoBase(object.getString("id_videojuego"));
                            juegoBase.setComentario(object.getString("comentario"));
                            videojuegosBase.add(juegoBase);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            for (int i = 0; i < videojuegoBases.size(); i++) {
                comentariosDelVideojuego.add(videojuegoBases.get(i));
                comentarioAdapter.notifyDataSetChanged();
            }
        }
    }


}
