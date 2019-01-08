package com.filmaffinityparajuegos;

<<<<<<< Updated upstream
import android.content.Intent;
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

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
import com.igdb.api_android_java.wrapper.Parameters;
import com.squareup.picasso.Picasso;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videojuegosQueTiene = new ArrayList<>();
        setContentView(R.layout.activity_usuario_detalle);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(NAvigationDrawerActivity.NV);
        ((TextView)findViewById(R.id.NombreUsuario)).setText(usuario.getName());
        layout = findViewById(R.id.LayoutJuegosQueTiene);

        ///añadir sus jueguicos
        new MyVolleyJuegoTiene(this).execute();
    }


    public void addAmigoAction(View view) {
        //añadir la amistad

    }

    private void generateBotonesTiene(){
        for(int i = 0; i < videojuegosQueTiene.size();i++){
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            Picasso.get().load(Uri.parse(videojuegosQueTiene.get(i).getUri_imagen()))
                    .resize(500,500).into(buttonI);
            buttonI.setId(i);
            buttonI.setOnClickListener(new View.OnClickListener(){
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
            //quite lo de aqui, porque estabas haciendo que la app volviese a la siguiente cosa
            int sizeParaRecorrer = (result.size()>10)?10:result.size();
            for(int i=0;i < sizeParaRecorrer;i++){
                Parameters params = new Parameters().addFields("*").addFilter();
            }
        }
    }
    
}
