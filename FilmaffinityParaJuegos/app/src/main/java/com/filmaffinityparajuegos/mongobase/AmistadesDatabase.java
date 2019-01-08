package com.filmaffinityparajuegos.mongobase;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.filmaffinityparajuegos.data.Amistad;
import com.filmaffinityparajuegos.data.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AmistadesDatabase {

    String base = "https://api.mlab.com/api/1/databases/videojogos/collections/amistades";
    String claveConsulta = "&apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    String clave = "?apiKey=CfF8SL07e7ZNkcOA1l0yWQ_j3VSKJBpZ";
    private String TAG = "SO_TEST";
    List<Amistad> amistades;

    public JSONArray getAmistades(String usuario1, Context context){
        String consulta = base + "?q={\"seguidor\":\"" + usuario1 + "\"}" + claveConsulta;
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

    public JSONArray getAmistad(String usuario1, String usuario2, Context context){
        String consulta = base + "?q={\"seguidor\":\"" + usuario1 + "\", \"seguido\":\""+ usuario2 + "\"}" + claveConsulta;
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

    public JSONArray addAmistad(String seguidor, String seguido, Context context) {

        String consulta = base + clave;
        RequestQueue request = Volley.newRequestQueue(context);
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JSONArray response = new JSONArray();
        Map<String, String> amistad = new HashMap<String, String>();
        amistad.put("seguidor", seguidor);
        amistad.put("seguido", seguido);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, consulta, new JSONObject(amistad), future, future);
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
