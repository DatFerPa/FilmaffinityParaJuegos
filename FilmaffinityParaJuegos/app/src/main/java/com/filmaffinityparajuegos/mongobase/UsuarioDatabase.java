package com.filmaffinityparajuegos.mongobase;

import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.filmaffinityparajuegos.data.Usuario;
import com.mongodb.util.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.Request;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;

public class UsuarioDatabase {

    String base = "https://api.mlab.com/api/1/databases/videojogos/collections/usuarios";
    String claveConsulta = "&apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    String clave = "?apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    Usuario usuario;


    public Usuario getUser(String name, String password, View view)  {
        String consulta = base + "?q={\"name\":\""+ name + "\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(view.getContext());



        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, consulta, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("onResponse",""+response);
                try {
                    usuario = parsearAUsuario(response);


                } catch (JSONException e) {
                    usuario = null;

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR", "Error occurred ", error);
            }
        });

        request.add(jsonObjectRequest);



        return usuario;
    }

    private Usuario parsearAUsuario(JSONArray response) throws JSONException {
        JSONObject object = response.getJSONObject(0);

        String nombre = object.getString("name");
        String password = object.getString("password");
        Usuario aux = new Usuario(nombre, password);

        return aux;
    }


    public boolean addUser(String name, String password, View view) {
        final boolean[] creado = {false};
        if (getUser(name, password, view) == null) {
            String consulta = base+ clave;
            RequestQueue request = Volley.newRequestQueue(view.getContext());
            Map<String, String> usuario = new HashMap<String,String>();
            usuario.put("name", name);
            usuario.put("password", password);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, consulta, new JSONObject(usuario), new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    creado[0] = true;
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    creado[0] = false;
                }
            });
            request.add(jsonObjectRequest);
            return creado[0];
        } else {
            return creado[0];
        }
    }
}
