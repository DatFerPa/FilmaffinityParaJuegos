package com.filmaffinityparajuegos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.filmaffinityparajuegos.data.Videojuego;
import com.igdb.api_android_java.callback.OnSuccessCallback;
import com.igdb.api_android_java.wrapper.IGDBWrapper;
import com.igdb.api_android_java.wrapper.Parameters;
import com.igdb.api_android_java.wrapper.Version;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MenuPrincipalActivity extends AppCompatActivity {

    ArrayList<ImageButton> botones = new ArrayList<ImageButton>();
    LinearLayout layout;
    ArrayList<Videojuego> videojuegosNuevos = new ArrayList<>();
    ArrayList<Videojuego> videojuegosPopulares = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        layout = findViewById(R.id.LayoutMain);
        IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext() , "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD,false);
        Parameters params = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("published_at:desc").addLimit("6");
        wrapper.games(params, new OnSuccessCallback() {
            @Override
            public void onSuccess(@NotNull JSONArray jsonArray) {
                System.out.println(jsonArray.toString());

                for(int i = 0; i < jsonArray.length();i++){
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        Videojuego juego = new Videojuego();
                        juego.setId_juego(obj.getString("id"));
                        juego.setDescripcion(obj.getString("summary"));
                        juego.setTitulo(obj.getString("name"));
                       // juego.setId_developer(obj.getString(""));
                        juego.setUri_imagen(obj.getJSONObject("cover").getString("url"));
                        videojuegosNuevos.add(juego);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onError(@NotNull VolleyError volleyError) {

            }
        });
        generateBotones();
    }
    private void generateBotones() {

        for(int i = 0; i <videojuegosNuevos.size(); i++){
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            buttonI.setImageURI( Uri.parse(videojuegosNuevos.get(i).getUri_imagen()));
            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui hay que hacer la llama para cada uno de los juegos
                    //mas adelante tendremos que crear una clase juegoViewButton para meterlo aqui y que tenga la info del juego
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    startActivity(intent);
                }
            });
            layout.addView(buttonI);
        }
    }
}