package com.filmaffinityparajuegos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.igdb.api_android_java.wrapper.IGDBWrapper;
import com.igdb.api_android_java.wrapper.Version;

import java.util.ArrayList;

public class MenuPrincipalActivity extends AppCompatActivity {

    ArrayList<ImageButton> botones = new ArrayList<ImageButton>();
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        layout = findViewById(R.id.LayoutMain);
        IGDBWrapper wrapper = new IGDBWrapper(getApplicationContext() , "cec1dc5cac50616ebc4643c7bc94647c", Version.STANDARD,false);
        generateBotones();


    }
    private void generateBotones() {

        for(int i = 0; i <10; i++){
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            buttonI.setImageResource((i%2 ==0)?R.drawable.godofwarmini :R.drawable.legendofkaymini);
            buttonI.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui hay que hacer la llama para cada uno de los juegos
                    //mas adelante tendremos que crear una clase juegoViewButton para meterlo aqui y que tenga la info del juego
                    Toast.makeText(getApplicationContext(), "Ha clicado en un juego", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DetallesActivity.class);
                    startActivity(intent);

                }
            });
            layout.addView(buttonI);
        }
    }
}
