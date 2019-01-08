package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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
    private  UsuarioDatabase usrDB = new UsuarioDatabase();

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
        /*
        Usuario usuario = null;
        try {
            usuario = usrDB.getUser(nombreUser,passwordHashed,view);


        } catch (Exception e) {
            e.printStackTrace();
        }
        if(usuario !=null){
            addPreferencesUserAndPassword(nombreUser,passwordHashed);


            //pasar a la activity principal
            System.out.println("Se ha realizado un LOGIN de manera correcta para " +
                    " \n Usuario: "+
                    nombreUsuario.getText().toString() + " - - Contraseña: "+
                    passWordUsuario.getText().toString()+" - - Contraseña Hasshed: "+passwordHashed);


            accederApp();
        }else{
            Toast.makeText(getApplicationContext(),
                    "Usuario o contraseña incorrectas",Toast.LENGTH_LONG).show();
        }
        */
        addPreferencesUserAndPassword(nombreUser,passwordHashed);
        accederApp();

    }

    public void actionSignIn(View view) {


        String nombreUser = nombreUsuario.getText().toString();

        //Codificacion de la password
        String passwordHashed = hashPassword();
        //comprabación del usuario
        //Si el getUser devuelve null, significa que el usuario no existe
        /*
        if(usrDB.getUser(nombreUser,passwordHashed,view)== null) {
            //shared preferences
            addPreferencesUserAndPassword(nombreUser,passwordHashed);

            //añadimos al usuario a la base de datos
            usrDB.addUser(nombreUser,passwordHashed,view);

            //pasamos a la activity principal
            System.out.println("Se ha realizado un SIGN IN de manera correcta para " +
                    " \n Usuario: "+
            nombreUsuario.getText().toString() + " - - Contraseña: "+
                    passWordUsuario.getText().toString()+" - - Contraseña Hasshed: "+passwordHashed);



            accederApp();

        }else{
            Toast.makeText(getApplicationContext(),
                    "El usuario ya existe, seleccione otro nombre",Toast.LENGTH_LONG).show();
        }
*/
        addPreferencesUserAndPassword(nombreUser,passwordHashed);
        accederApp();
    }

    private void addPreferencesUserAndPassword(String usuario, String pass){
        SharedPreferences sharedPreferences = getSharedPreferences(getString(
                R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.shared_nombre_user), usuario);
        editor.putString(getString(R.string.shared_password_user), pass);
        editor.apply();
    }

    private String hashPassword(){
        HashCode hashCode = Hashing.sha512().hashString(passWordUsuario.getText().toString(),
                Charset.defaultCharset());
         return hashCode.toString();
    }

    private void accederApp(){
        Intent intent = new Intent(getApplicationContext(),NAvigationDrawerActivity.class);
        startActivity(intent);
    }

}
