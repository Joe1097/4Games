package com.example.pantallainicio.Ahorcado;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.pantallainicio.MenuPrincipal;
import com.example.pantallainicio.R;

public class Inicio_Ahorcado extends AppCompatActivity {

    private Spinner spinner_categoria, spinner_dificultad;
    private String jugador1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_ahorcado);
        spinner_categoria = (Spinner)findViewById(R.id.spinner_categoria);
        spinner_dificultad = (Spinner)findViewById(R.id.spinner_dificultad);

        String opciones_categoria[] = {"Animales", "Transportes", "Colores", "Electrodomésticos", "Astros"};
        String opciones_dificultad[] = {"Fácil", "Moderada", "Difícil"};

        ArrayAdapter<String> adapter_categoria = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_categoria);
        spinner_categoria.setAdapter(adapter_categoria);
        ArrayAdapter<String> adapter_dificultad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, opciones_dificultad);
        spinner_dificultad.setAdapter(adapter_dificultad);

        jugador1 = getIntent().getStringExtra("jugador1");
}

    // metodo del boton Empezar Juego_Ahorcado
    public void empezarJuego(View view){
        String categoria = spinner_categoria.getSelectedItem().toString();
        String dificultad = spinner_dificultad.getSelectedItem().toString();

        Intent i = new Intent(this, Juego_Ahorcado.class);
        i.putExtra("dato_categoria", categoria);
        i.putExtra("dato_dificultad", dificultad);
        i.putExtra("dato_nickname",jugador1);
        startActivity(i);
    }

    public void regresarAlMenu(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("dato_nickname",jugador1);
        startActivity(i);
    }
}
