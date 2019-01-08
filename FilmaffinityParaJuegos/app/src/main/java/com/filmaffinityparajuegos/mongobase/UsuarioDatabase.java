package com.filmaffinityparajuegos.mongobase;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request.Method;
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


    private String TAG = "SO_TEST";

    public JSONArray getUser(String name, String password, Context context) {
        String consulta = base + "?q={\"name\":\"" + name + "\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Method.GET, consulta, null, future, future);

        request.add(jsonObjectRequest);

        try {
            response = future.get(3, TimeUnit.SECONDS); // Blocks for at most 10 seconds.
        } catch (InterruptedException e) {
            Log.d(TAG, "interrupted");
        } catch (ExecutionException e) {
            Log.d(TAG, "execution");
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        Log.d(TAG, response.toString());

        return response;
    }

    private Usuario parsearAUsuario(JSONArray response) throws JSONException {
        JSONObject object = response.getJSONObject(0);

        String nombre = object.getString("name");
        String password = object.getString("password");
        Usuario aux = new Usuario(nombre, password);

        return aux;
    }


    public JSONArray addUser(String name, String password, Context context) {
        final boolean[] creado = {false};
        String consulta = base + clave;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONArray response = new JSONArray();
        Map<String, String> usuario = new HashMap<String, String>();
        usuario.put("name", name);
        usuario.put("password", password);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Method.POST, consulta, new JSONObject(usuario), future, future);
        request.add(jsonObjectRequest);
        try {
            response.put(0, future.get(3, TimeUnit.SECONDS)); // Blocks for at most 10 seconds.
        } catch (InterruptedException e) {
            Log.d(TAG, "interrupted");
        } catch (ExecutionException e) {
            Log.d(TAG, "execution");
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;

    }
}
