package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.mongobase.UsuarioDatabase;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity {

    private TextView nombreUsuario;
    private TextView passWordUsuario;
    private SharedPreferences sharedPreferences;
    private  UsuarioDatabase usrDB = new UsuarioDatabase();
    private Usuario usuarioP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        nombreUsuario = findViewById(R.id.EditTextNombreUser);
        passWordUsuario = findViewById(R.id.EditTextPassword);
    }

    public void actionLogin(View view) {
        System.out.println("Usuario ha realizado LOGIN con: \n Usuario: "+
                nombreUsuario.getText().toString() + " - - Contraseña: "+
                passWordUsuario.getText().toString());
        String nombreUser = nombreUsuario.getText().toString();
        String passwordHashed = hashPassword();
        usuarioP = null;
        try {
            new MyVolleyAsyncTask(this).execute(nombreUser,passwordHashed);
            new AddPreferences(this).execute(nombreUser,passwordHashed);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private Usuario parsearUusuario(JSONArray parserUsuario) {
        JSONObject object = null;
        try {
            object = parserUsuario.getJSONObject(0);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String nombre = null;
        String password = null;
        Usuario aux = null;
        try {
            nombre = object.getString("name");
            password = object.getString("password");
            aux = new Usuario(nombre, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return aux;
    }



    public void actionSignIn(View view) {


        String nombreUser = nombreUsuario.getText().toString();

        //Codificacion de la password
        String passwordHashed = hashPassword();
        //comprabación del usuario
        //Si el getUser devuelve null, significa que el usuario no existe
        new MyVolleyAsyncTask(this).execute(nombreUser,passwordHashed);
        new MyVolleyAsyncTaskSignIn(this).execute(nombreUser,passwordHashed);
    }

    private void addPreferencesUserAndPassword(String usuario, String pass){
        sharedPreferences = this.getSharedPreferences(getString(
                R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.shared_nombre_user), usuario);
        editor.putString(getString(R.string.shared_password_user), pass);
        editor.commit();
    }

    private String hashPassword(){
        HashCode hashCode = Hashing.sha512().hashString(passWordUsuario.getText().toString(),
                Charset.defaultCharset());
        return hashCode.toString();
    }

    public class AddPreferences extends AsyncTask<String,String, Void> {

        private Context ctx;

        public AddPreferences(Context hostContext)
        {
            ctx = hostContext;
        }

        @Override
        protected Void doInBackground(String... params) {
            if(usuarioP !=null){
                addPreferencesUserAndPassword(params[0],params[1]);

            }else{

            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(usuarioP !=null){

                Toast.makeText(ctx,
                        "USUARIO CORRECTO",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(ctx,
                        "Usuario o contraseña incorrectas",Toast.LENGTH_LONG).show();
            }
        }

    }

    public class MyVolleyAsyncTask extends AsyncTask<String,String, JSONArray> {

        private Context ctx;

        public MyVolleyAsyncTask(Context hostContext)
        {
            ctx = hostContext;
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            // Method runs on a separate thread, make all the network calls you need
            UsuarioDatabase tester = new UsuarioDatabase();
            JSONArray respuesta = tester.getUser(params[0],params[1],ctx);
            if(respuesta.length() == 0)
                usuarioP = null;
            else
                usuarioP = parsearUusuario(respuesta);
            return null;
        }


        @Override
        protected void onPostExecute(JSONArray result)
        {
            // runs on the UI thread
            // do something with the result
        }
    }

    public class MyVolleyAsyncTaskSignIn extends AsyncTask<String,String, JSONArray> {

        private Context ctx;
        private JSONArray respuesta = new JSONArray();
        public MyVolleyAsyncTaskSignIn(Context hostContext)
        {
            ctx = hostContext;
        }

        @Override
        protected JSONArray doInBackground(String... params) {

            if(usuarioP ==null){
                UsuarioDatabase base = new UsuarioDatabase();
                respuesta = base.addUser(params[0],params[1],ctx);
            }

            return null;
        }


        @Override
        protected void onPostExecute(JSONArray result)
        {
            if(respuesta.length() >0){

                Toast.makeText(ctx,
                        "Usuario creado",Toast.LENGTH_LONG).show();

            }else{
                Toast.makeText(ctx,
                        "Usuario ya existente",Toast.LENGTH_LONG).show();
            }
        }
    }


}
