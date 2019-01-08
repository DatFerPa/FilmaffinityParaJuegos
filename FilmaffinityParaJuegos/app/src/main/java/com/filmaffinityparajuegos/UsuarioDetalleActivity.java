package com.filmaffinityparajuegos;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class UsuarioDetalleActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(NAvigationDrawerActivity.NV);
        ((TextView)findViewById(R.id.NombreUsuario)).setText(usuario.getName());

        ///añadir sus jueguicos

    }


    public void addAmigoAction(View view) {
        //añadir la amistad

    }


    private class MyVolleyJuegoTiene extends AsyncTask<String,String, List<Videojuego>> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();
        private List<Videojuego> videojuegos;
        public MyVolleyJuegoTiene(Context hostContext)
        {
            ctx = hostContext;
        }

        @Override
        protected List<Videojuego> doInBackground(String... params) {
            VideojuegosDatabase vdb = new VideojuegosDatabase();
            respuesta = vdb.getVideojuegosUsuarioTiene(usuario.getName(),ctx);
            JSONObject video;
            String identificador;
            if(respuesta.length() >0){
                for(int i =0; i < respuesta.length();i++){
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
            if(respuesta.length() >0){

                Toast.makeText(ctx,
                        "Usuario creado",Toast.LENGTH_LONG).show();
                usuarioP = parsearUusuario(respuesta);
                addPreferencesUserAndPassword(usuarioP.getName(), usuarioP.getPassword());
                Intent intent = new Intent(ctx, NAvigationDrawerActivity.class);
                startActivity(intent);

            }else{
                Toast.makeText(ctx,
                        "Usuario ya existente",Toast.LENGTH_LONG).show();
            }
        }
    }
    
}
