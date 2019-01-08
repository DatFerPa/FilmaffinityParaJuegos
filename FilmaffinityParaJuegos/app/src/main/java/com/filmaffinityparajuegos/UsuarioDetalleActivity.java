package com.filmaffinityparajuegos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.filmaffinityparajuegos.data.Amistad;
import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.AmistadesDatabase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
import com.igdb.api_android_java.callback.OnSuccessCallback;
import com.igdb.api_android_java.wrapper.IGDBWrapper;
import com.igdb.api_android_java.wrapper.Parameters;
import com.igdb.api_android_java.wrapper.Version;
import com.mongodb.util.JSON;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Policy;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDetalleActivity extends AppCompatActivity {

    private Usuario usuario;
    private LinearLayout layout;
    private List<Videojuego> videojuegosQueTiene;
    private Amistad amistad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videojuegosQueTiene = new ArrayList<>();
        setContentView(R.layout.activity_usuario_detalle);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(NAvigationDrawerActivity.NV);
        ((TextView) findViewById(R.id.NombreUsuario)).setText(usuario.getName());
        layout = findViewById(R.id.LayoutJuegosQueTiene);

        ///añadir sus jueguicos
        new MyVolleyJuegoTiene(this).execute();
    }


    public void addAmigoAction(View view) {
        //añadir la amistad

    }

    private void generateBotonesTiene() {
        for (int i = 0; i < videojuegosQueTiene.size(); i++) {
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            Picasso.get().load(Uri.parse(videojuegosQueTiene.get(i).getUri_imagen()))
                    .resize(500, 500).into(buttonI);
            buttonI.setId(i);
            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    ImageButton btn = (ImageButton) findViewById(view.getId());

                    intent.putExtra(NAvigationDrawerActivity.NV, videojuegosQueTiene.get(btn.getId()));
                    startActivity(intent);
                }
            });
            layout.addView(buttonI);
        }
    }

    private void asignarAmistad(JSONArray respuesta){
        JSONObject object = null;
        try {
            object = respuesta.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String usuario1 = null;
        String usuario2 = null;
        Amistad aux = null;
        try {
            usuario1 = object.getString("seguidor");
            usuario2 = object.getString("seguido");
            aux = new Amistad(usuario1, usuario2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        amistad = aux;
    }



    private class MyVolleyJuegoTiene extends AsyncTask<String, String, List<Videojuego>> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();
        private List<Videojuego> videojuegos;

        public MyVolleyJuegoTiene(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected List<Videojuego> doInBackground(String... params) {
            VideojuegosDatabase vdb = new VideojuegosDatabase();
            respuesta = vdb.getVideojuegosUsuarioTiene(usuario.getName(), ctx);
            JSONObject video;
            String identificador;
            if (respuesta.length() > 0) {
                for (int i = 0; i < respuesta.length(); i++) {
                    try {
                        video = respuesta.getJSONObject(i);
                        identificador = video.getString("id_videojuego");
                        videojuegos.add(new Videojuego(identificador));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            return videojuegos;
        }


        @Override
        protected void onPostExecute(List<Videojuego> result)
        {
            //quite lo de aqui, porque estabas haciendo que la app volviese a la siguiente cosa
            int sizeParaRecorrer = (result.size()>10)?10:result.size();
            for(int i=0;i < sizeParaRecorrer;i++){
                //comprobar esta mierda
                Parameters params = new Parameters().addFields("*").addFilter("[id][eq]="+result.get(i).getId_juego());
                IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(),"cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
                wrapper.games(params, new
                        OnSuccessCallback() {
                            @Override
                            public void onSuccess(@NotNull JSONArray jsonArray) {
                                try{
                                    JSONObject obj = jsonArray.getJSONObject(0);
                                    System.out.println("Juego de amigo: \n"+obj);
                                    Videojuego videojuego = new Videojuego();
                                    videojuego.setId_juego(obj.getString("id"));
                                    if (obj.opt("summary") != null)
                                        videojuego.setDescripcion(obj.getString("summary"));
                                    else
                                        videojuego.setDescripcion("No tiene descripcion");
                                    videojuego.setTitulo(obj.getString("name"));
                                    // juego.setId_developer(obj.getString(""));
                                    videojuego.setUri_imagen("https:" + obj.getJSONObject("cover").getString("url"));
                                    System.out.println(videojuego.toString());
                                    videojuegosQueTiene.add(videojuego);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void onError(@NotNull VolleyError volleyError) {
                            }
                        }
                );
            }
            generateBotonesTiene();

        }
    }

    private class MyVolleyFriendExist extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();

        public MyVolleyFriendExist(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            AmistadesDatabase adb = new AmistadesDatabase();
            SharedPreferences sharedPref = ctx.getSharedPreferences(
                    getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
            respuesta = adb.getAmistad(sharedPref.getString(getString(R.string.shared_nombre_user), getString(R.string.nombre_string)), usuario.getName(), ctx);

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {

            if (respuesta.length() > 0) {
                asignarAmistad( respuesta);
            }
        }
    }


    private class MyVolleyAddFriend extends AsyncTask<String, String, Void> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();

        public MyVolleyAddFriend(Context hostContext) {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            if (amistad == null) {
                AmistadesDatabase adb = new AmistadesDatabase();
                SharedPreferences sharedPref = ctx.getSharedPreferences(
                        getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
                respuesta = adb.addAmistad(sharedPref.getString(getString(R.string.shared_nombre_user), getString(R.string.nombre_string)), usuario.getName(), ctx);
            }
            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            if (respuesta != null && respuesta.length() > 0) {
                Toast.makeText(ctx,
                        "Has seguido al usuario", Toast.LENGTH_LONG).show();
                asignarAmistad( respuesta);

            } else {
                Toast.makeText(ctx,
                        "Ha ocurrido un error", Toast.LENGTH_LONG).show();
            }
        }
    }


}
