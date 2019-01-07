package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.mongobase.UserDatabase;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity {

    private TextView nombreUsuario;
    private TextView passWordUsuario;
    private SharedPreferences sharedPreferences;
    private  UserDatabase usrDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usrDB = new UserDatabase();
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
        Usuario usuario = usrDB.getUser(nombreUser,passwordHashed);
        if(usuario !=null){
            addPreferencesUserAndPassword(nombreUser,passwordHashed);


            //pasar a la activity principal
            System.out.println("Se ha realizado un LOGIN de manera correcta para " +
                    " \n Usuario: "+
                    nombreUsuario.getText().toString() + " - - Contraseña: "+
                    passWordUsuario.getText().toString()+" - - Contraseña Hasshed: "+passwordHashed);

        }else{
            Toast.makeText(getApplicationContext(),
                    "Usuario o contraseña incorrectas",Toast.LENGTH_LONG).show();
        }

    }

    public void actionSignIn(View view) {


        String nombreUser = nombreUsuario.getText().toString();

        //Codificacion de la password
        String passwordHashed = hashPassword();
        //comprabación del usuario
        //Si el getUser devuelve null, significa que el usuario no existe
        System.out.println("llegue aqui 1");
        if(usrDB.getUser(nombreUser,passwordHashed)== null) {
            System.out.println("llegue aqui 2");
            //shared preferences
            addPreferencesUserAndPassword(nombreUser,passwordHashed);
            System.out.println("llegue aqui 3");

            //añadimos al usuario a la base de datos
            usrDB.addUser(nombreUser,passwordHashed);
            System.out.println("llegue aqui 4");

            //pasamos a la activity principal
            System.out.println("Se ha realizado un SIGN IN de manera correcta para " +
                    " \n Usuario: "+
            nombreUsuario.getText().toString() + " - - Contraseña: "+
                    passWordUsuario.getText().toString()+" - - Contraseña Hasshed: "+passwordHashed);





        }else{
            Toast.makeText(getApplicationContext(),
                    "El usuario ya existe, seleccione otro nombre",Toast.LENGTH_LONG).show();
        }


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


}
