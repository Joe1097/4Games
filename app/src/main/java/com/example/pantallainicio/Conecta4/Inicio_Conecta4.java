package com.example.pantallainicio.Conecta4;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pantallainicio.MenuPrincipal;
import com.example.pantallainicio.R;

public class Inicio_Conecta4 extends AppCompatActivity {

    private String jugador1, jugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_conecta4);
        jugador1=getIntent().getStringExtra("jugador1");
        jugador2=getIntent().getStringExtra("jugador2");
    }

    public void empezarJuego(View view){
        Intent i = new Intent(this, Juego_Conecta4.class);
        i.putExtra("jugador1", jugador1);
        i.putExtra("jugador2", jugador2);
        startActivity(i);
    }

    public void irAlMenuDeJuegos(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("dato_nickname",jugador1);
        startActivity(i);
    }
}
