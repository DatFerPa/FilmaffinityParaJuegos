package com.filmaffinityparajuegos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.filmaffinityparajuegos.data.Usuario;
import com.filmaffinityparajuegos.data.Videojuego;
import com.squareup.picasso.Picasso;

import java.util.List;


public class UsuarioDetalleActivity extends AppCompatActivity {

    private Usuario usuario;
    private LinearLayout layout;
    private List<Videojuego> videojuegosQueTiene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalle);
        Intent intent = getIntent();
        usuario = intent.getParcelableExtra(NAvigationDrawerActivity.NV);
        ((TextView)findViewById(R.id.NombreUsuario)).setText(usuario.getName());
        layout = findViewById(R.id.LayoutJuegosQueTiene);

        ///añadir sus jueguicos

    }


    public void addAmigoAction(View view) {
        //añadir la amistad

    }

    private void generateBotonesTiene(){
        for(int i = 0; i < videojuegosQueTiene.size();i++){
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            Picasso.get().load(Uri.parse(videojuegosQueTiene.get(i).getUri_imagen()))
                    .resize(500,500).into(buttonI);
            buttonI.setId(i);
            buttonI.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    ImageButton btn = (ImageButton) findViewById(view.getId());

                    intent.putExtra(NAvigationDrawerActivity.NV, videojuegosQueTiene.get(btn.getId()));
                    startActivity(intent);
                }
            });
            layout.addView(buttonI);
        }
    }



    
}
