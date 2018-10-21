package com.filmaffinityparajuegos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MenuPrincipalActivity extends AppCompatActivity {

    ArrayList<ImageButton> botones = new ArrayList<ImageButton>();
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        layout = findViewById(R.id.LayoutMain);

        generateBotones();


    }
    private void generateBotones() {
        for(int i = 0; i <10; i++){
            ImageButton buttonI;
            buttonI = new ImageButton(getApplicationContext());
            buttonI.setImageResource((i%2 ==0)?R.drawable.godofwarmini :R.drawable.legendofkaymini);
            layout.addView(buttonI);
        }
    }
}
