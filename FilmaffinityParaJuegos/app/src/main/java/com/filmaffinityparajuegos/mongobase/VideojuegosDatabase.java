package com.filmaffinityparajuegos.mongobase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.filmaffinityparajuegos.data.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VideojuegosDatabase {
    String base = "https://api.mlab.com/api/1/databases/videojogos/collections/videojuegos";
    String claveConsulta = "&apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    String clave = "?apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    Usuario usuario;


    private String TAG = "SO_TEST";

    public JSONArray getVideojuego(String name, Context context) {
        String consulta = base + "?q={\"id_videojuego\":\"" + name + "\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, consulta, null, future, future);

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

    public JSONArray getLoTiene(String id_juego,String usuario, Context context) {
        String consulta = base + "?q={\"id_videojuego\":\"" + id_juego+ "\",\"usuario\":\"" + usuario + "\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, consulta, null, future, future);

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



        return response;
    }

    public JSONArray getVideojuegosUsuario(String name, Context context) {
        String consulta = base + "?q={\"usuario\":\"" + name + "\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, consulta, null, future, future);

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

    public JSONArray getVideojuegosUsuarioTiene(String name, Context context) {
        String consulta = base + "?q={\"usuario\":\"" + name + "\", \"tener_querer\" : \"0\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, consulta, null, future, future);

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

    public JSONArray getVideojuegosUsuarioQuiere(String name, Context context) {
        String consulta = base + "?q={\"usuario\":\"" + name + "\", \"tener_querer\" : \"1\"}" + claveConsulta;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JSONArray response = null;
        System.out.println(consulta);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, consulta, null, future, future);

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

    public JSONArray addVideojuego(String id_videojuego,String usuario, String comentario,Double valoracion, String tener_querer, Context context) {

        String consulta = base + clave;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONArray response = new JSONArray();
        Map<String, String> videojuego = new HashMap<String, String>();
        videojuego.put("id_videojuego", id_videojuego);
        videojuego.put("usuario", usuario);
        videojuego.put("comentario", comentario);
        videojuego.put("valoracion", valoracion.toString());
        videojuego.put("tener_querer",tener_querer);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, consulta, new JSONObject(videojuego), future, future);
        request.add(jsonObjectRequest);
        try {
            response.put(0, future.get(3, TimeUnit.SECONDS));
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

    public JSONArray actualizarVideojuegoUsuario(String id_videojuego,String usuario,  String tener_querer, Context context) {

        String consulta = base + clave + "&q={\"id_videojuego\":\"" + id_videojuego +"\"}";
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONArray response = new JSONArray();
        Map<String, String> videojuego = new HashMap<String, String>();
        videojuego.put("id_videojuego", id_videojuego);
        videojuego.put("usuario", usuario);
        videojuego.put("tener_querer",tener_querer);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, consulta, new JSONObject(videojuego), future, future);
        request.add(jsonObjectRequest);
        try {
            response.put(0, future.get(3, TimeUnit.SECONDS));
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
