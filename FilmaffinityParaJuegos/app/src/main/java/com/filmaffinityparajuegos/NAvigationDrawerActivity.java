package com.filmaffinityparajuegos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.filmaffinityparajuegos.data.VideojuegoBase;
import com.filmaffinityparajuegos.mongobase.AmistadesDatabase;
import com.filmaffinityparajuegos.mongobase.UsuarioDatabase;
import com.filmaffinityparajuegos.mongobase.VideojuegosDatabase;
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

public class NAvigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public static final String NV = "com.filmaffinityparajuegos";
    public List<VideojuegoBase> videojuegosUsuario = new ArrayList<VideojuegoBase>();
    public String usuarioSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        usuarioSesion =sharedPref.getString(getString(R.string.shared_nombre_user),getString(R.string.nombre_string));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        cargarMenuActivo();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
           System.out.println("Do Nothing");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        ((TextView)findViewById(R.id.nombreUserMenu)).setText(sharedPref.getString(getString(R.string.shared_nombre_user),getString(R.string.nombre_string)));
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        int id = item.getItemId();

        if (id == R.id.novedadesMenu) {
            // Handle the camera action
            if(findViewById(R.id.menuPrincipalInclude).getVisibility()!=View.VISIBLE) {
                cargarMenuPrincipalActivity();
            }

        } else if (id == R.id.logoutMenu) {
           // findViewById(R.id.menuPrincipalInclude).setVisibility(View.VISIBLE);
            SharedPreferences preferences = this.getSharedPreferences(getString(
                    R.string.ID_SHARED_PREFERENCES),Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            finish();
        } else if(id == R.id.menuMisJuegos){
            

        }else if(id == R.id.amigosMenu){
            if(findViewById(R.id.menuAmigosInclude).getVisibility()!=View.VISIBLE) {
                cargarMenuAmigos();
            }
        }

        return true;
    }

    private void cargarMenuActivo(){
        if(findViewById(R.id.menuPrincipalInclude).getVisibility()==View.VISIBLE) {

            cargarMenuPrincipalActivity();
        }

        if(findViewById(R.id.menuAmigosInclude).getVisibility()==View.VISIBLE){
            cargarMenuAmigos();
        }
    }

    /*
        Aqui se hace todo
         lo relacionado con el menu principal
     */

    private LinearLayout layout;
    private LinearLayout layouPopu;
    private List<Videojuego> videojuegosNuevos = new ArrayList<>();
    private List<Videojuego> videojuegosPopulares = new ArrayList<>();

    private void cargarMenuPrincipalActivity(){
        findViewById(R.id.menuPrincipalInclude).setVisibility(View.VISIBLE);
        //desactivar el resto de las vistas
        findViewById(R.id.menuAmigosInclude).setVisibility(View.GONE);
        //
        layout = findViewById(R.id.LayoutMain);
        layouPopu = findViewById(R.id.LayoutPopular);
        System.out.println("");
        System.out.println("new CargarVideojuegosNuevos().execute();");
        new CargarVideojuegosNuevos().execute();
        System.out.println("new CargarVideojuegosPopulares().execute();");
        new CargarVideojuegosPopulares().execute();
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
        for (int i = 0; i < videojuegosPopulares.size(); i++) {
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

    private class CargarVideojuegosPopulares extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NAvigationDrawerActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Los juegos populares se han cargado", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            videojuegosPopulares = new ArrayList<>();
            IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(), "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
            Parameters params2 = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("popularity:desc").addLimit("6");
            wrapper.games(params2, new OnSuccessCallback() {
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
                            //System.out.println(juego.descripcion);
                            System.out.println(juego.toString());
                            videojuegosPopulares.add(juego);
                            System.out.println("=======================================");
                            System.out.println(videojuegosPopulares.size());
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

            return null;
        }
    }

    private class CargarVideojuegosNuevos extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(NAvigationDrawerActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            this.progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Los juegos nuevos se han cargado", Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            videojuegosNuevos = new ArrayList<>();
            Parameters params = new Parameters().addFields("*").addFilter("[category][eq]=0").addOrder("published_at:desc").addLimit("6");
            IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext(), "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD, false);
            wrapper.games(params, new

                    OnSuccessCallback() {
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
                                    //System.out.println(juego.descripcion);
                                    System.out.println(juego.toString());
                                    videojuegosNuevos.add(juego);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            generateBotonesNuevos();
                        }

                        @Override
                        public void onError(@NotNull VolleyError volleyError) {
                        }
                    });

            return null;
        }
    }



/*
    Aqui se hace todo
    lo relacionado con el menu de amigos
 */
    private TextView nombreUsuarioABuscar;

    private void cargarMenuAmigos(){
        findViewById(R.id.menuAmigosInclude).setVisibility(View.VISIBLE);
        //desactivar el resto de las vistas
        findViewById(R.id.menuPrincipalInclude).setVisibility(View.GONE);
        //


    }

    public void buscarAmigo(View view) {
        nombreUsuarioABuscar = findViewById(R.id.TextInputEditTextBuscarAmigo);

        //miedas de juan
        new MyVolleyUsuarioBuscadoDetalle(this).execute(nombreUsuarioABuscar
                .getText().toString());



    }



    private class MyVolleyUsuarioBuscadoDetalle extends AsyncTask<String,String, Usuario> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();
        private Usuario usuarioBuscado;


        public MyVolleyUsuarioBuscadoDetalle(Context hostContext)
        {
            ctx = hostContext;
        }

        @Override
        protected void onPostExecute(Usuario result){
            Intent intent = new Intent(ctx, UsuarioDetalleActivity.class);
            intent.putExtra(NV, usuarioBuscado);
            startActivity(intent);
        }
        @Override
        protected Usuario doInBackground(String... params) {
            UsuarioDatabase usuarioBase = new UsuarioDatabase();

            respuesta = usuarioBase.getUser(params[0],ctx);

            if(respuesta.length() == 0){
            }
            else{
                JSONObject object = null;
                try {
                    object = respuesta.getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String nombre = null;

                Usuario aux = null;
                try {
                    nombre = object.getString("name");
                    usuarioBuscado = new Usuario(nombre);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

    }


}
