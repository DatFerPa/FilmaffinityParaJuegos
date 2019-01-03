package com.filmaffinityparajuegos;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.filmaffinityparajuegos.data.Videojuego;
import com.igdb.api_android_java.callback.OnSuccessCallback;
import com.igdb.api_android_java.wrapper.IGDBWrapper;
import com.igdb.api_android_java.wrapper.Parameters;
import com.igdb.api_android_java.wrapper.Version;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuPrincipalActivity extends AppCompatActivity {

    ArrayList<ImageButton> botones = new ArrayList<ImageButton>();
    LinearLayout layout;
    LinearLayout layouPopu;
    List<Videojuego> videojuegosNuevos = new ArrayList<>();
    List<Videojuego> videojuegosPopulares = new ArrayList<>();
    private Videojuego videojuegoActual;
    public static final String NV = "com.filmaffinityparajuegos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        layout = findViewById(R.id.LayoutMain);
        layouPopu = findViewById(R.id.LayoutPopular);
       /* obtenerJuegosActuales();
        obtenerJuegosPopulares();*/
       //Juegos Nuevos
        Parameters params = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("published_at:desc").addLimit("6");
        try {
            videojuegosNuevos =  new CargarVidojuego().execute(params).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        generateBotonesNuevos();
        //Juegos populares
        Parameters params2 = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("popularity:desc").addLimit("6");
        try {
            videojuegosPopulares =  new CargarVidojuego().execute(params2).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        GenerateBotonesPopulares();
    }


    private void generateBotonesNuevos() {

        for (int i = 0; i < videojuegosNuevos.size(); i++) {
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            Picasso.get().load(Uri.parse(videojuegosNuevos.get(i).getUri_imagen())).resize(500, 500).into(buttonI);
            buttonI.setId(i);
            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui hay que hacer la llama para cada uno de los juegos
                    //mas adelante tendremos que crear una clase juegoViewButton para meterlo aqui y que tenga la info del juego
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    ImageButton btn = (ImageButton) findViewById(view.getId());

                    intent.putExtra(NV, videojuegosNuevos.get(btn.getId()));
                    startActivity(intent);
                }
            });
            layout.addView(buttonI);
        }
    }

    private void GenerateBotonesPopulares() {

        for (
                int i = 0; i < videojuegosPopulares.size(); i++)

        {
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            Picasso.get().load(Uri.parse(videojuegosPopulares.get(i).getUri_imagen())).resize(500, 500).into(buttonI);
            buttonI.setId(i);
            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui hay que hacer la llama para cada uno de los juegos
                    //mas adelante tendremos que crear una clase juegoViewButton para meterlo aqui y que tenga la info del juego
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    ImageButton btn = (ImageButton) findViewById(view.getId());

                    intent.putExtra(NV, videojuegosPopulares.get(btn.getId()));
                    startActivity(intent);
                }
            });
            layouPopu.addView(buttonI);
        }

    }
/*
    private void obtenerJuegosPopulares() {

        IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(), "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
        Parameters params = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("popularity:desc").addLimit("6");
        wrapper.games(params, new OnSuccessCallback() {
            @Override
            public void onSuccess(@NotNull JSONArray jsonArray) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        System.out.println(obj);
                        Videojuego juego = new Videojuego();
                        juego.setId_juego(obj.getString("id"));
                        if (obj.opt("summary") != null)
                            juego.setDescripcion(obj.getString("summary"));
                        else
                            juego.setDescripcion("No tiene descripcion");
                        juego.setTitulo(obj.getString("name"));
                        // juego.setId_developer(obj.getString(""));
                        juego.setUri_imagen("https:" + obj.getJSONObject("cover").getString("url"));
                        System.out.println(juego.descripcion);
                        videojuegosPopulares.add(juego);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                GenerateBotonesPopulares();
            }

            @Override
            public void onError(@NotNull VolleyError volleyError) {

            }
        });
    }

*/

    public void buscarJuego(View view) {
        TextView texto = (TextView) findViewById(R.id.TextoJuegoParaBuscar);
        IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(), "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
        Parameters params = new Parameters().addSearch(texto.getText().toString()).addFields("*").addFilter("[category][eq]=0").addLimit("1");
        wrapper.games(params, new OnSuccessCallback() {
            @Override
            public void onSuccess(@NotNull JSONArray jsonArray) {

                    try {
                        JSONObject obj = jsonArray.getJSONObject(0);
                        System.out.println(obj);
                        Videojuego juego = new Videojuego();
                        juego.setId_juego(obj.getString("id"));
                        if (obj.opt("summary") != null)
                            juego.setDescripcion(obj.getString("summary"));
                        else
                            juego.setDescripcion("No tiene descripcion");
                        juego.setTitulo(obj.getString("name"));
                        // juego.setId_developer(obj.getString(""));
                        juego.setUri_imagen("https:" + obj.getJSONObject("cover").getString("url"));
                        System.out.println(juego.descripcion);

                        Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                        intent.putExtra(NV, juego);
                        startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }

            @Override
            public void onError(@NotNull VolleyError volleyError) {

            }
        });

    }

    private class CargarVidojuego extends AsyncTask<Parameters,Void,List<Videojuego>>{
        List<Videojuego> juegos = new ArrayList<>();
        @Override
        protected List<Videojuego> doInBackground(Parameters... parameters) {
            IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(), "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
            wrapper.games(parameters[0], new OnSuccessCallback() {
                @Override
                public void onSuccess(@NotNull JSONArray jsonArray) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            //System.out.println(obj);
                            Videojuego juego = new Videojuego();
                            juego.setId_juego(obj.getString("id"));
                            if (obj.opt("summary") != null)
                                juego.setDescripcion(obj.getString("summary"));
                            else
                                juego.setDescripcion("No tiene descripcion");
                            juego.setTitulo(obj.getString("name"));
                            // juego.setId_developer(obj.getString(""));
                            juego.setUri_imagen("https:" + obj.getJSONObject("cover").getString("url"));
                            //System.out.println(juego.descripcion);
                            System.out.println(juego.toString());
                            juegos.add(juego);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onError(@NotNull VolleyError volleyError) {
                }
            });
            return juegos;
        }
    }


}
