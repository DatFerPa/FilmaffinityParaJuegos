package com.filmaffinityparajuegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

public class LoginActivity extends AppCompatActivity {

    private TextView nombreUsuario;
    private TextView passWordUsuario;
    private SharedPreferences sharedPreferences;

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

    }

    public void actionSignIn(View view) {


        String nombreUser = nombreUsuario.getText().toString();

        //Codificacion de la password
        HashCode hashCode = Hashing.sha512().hashString(passWordUsuario.getText().toString(),
                Charset.defaultCharset());
        String passwordHashed = hashCode.toString();
        //Luego quitar esto
        Toast.makeText(getApplicationContext(),passwordHashed,Toast.LENGTH_LONG).show();
        //comprabación del usuario

        //shared preferences
        sharedPreferences = this.getSharedPreferences(getString(
                R.string.ID_SHARED_PREFERENCES), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.shared_nombre_user),nombreUser);
        editor.putString(getString(R.string.shared_password_user),passwordHashed);
        editor.commit();



    }


}
