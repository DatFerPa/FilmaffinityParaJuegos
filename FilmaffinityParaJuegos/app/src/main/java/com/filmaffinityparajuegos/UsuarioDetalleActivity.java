package com.filmaffinityparajuegos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.filmaffinityparajuegos.data.Usuario;

public class UsuarioDetalleActivity extends AppCompatActivity {

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(NAvigationDrawerActivity.NV);
        ((TextView)findViewById(R.id.NombreUsuario)).setText(usuario.getName());

        ///añadir sus jueguicos

    }


    public void addAmigoAction(View view) {
        //añadir la amistad

    }
}
