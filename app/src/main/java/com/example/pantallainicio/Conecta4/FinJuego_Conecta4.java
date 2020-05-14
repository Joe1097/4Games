package com.example.pantallainicio.Conecta4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

public class FinJuego_Conecta4 extends AppCompatActivity {

    TextView tv_nombreganador, tv_nombreperdedor, tv_puntajeganador, tv_puntajeperdedor;
    private String nombre_ganador, nombre_perdedor, puntaje_ganador, puntaje_perdedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_juego_conecta4);

        nombre_ganador=getIntent().getStringExtra("dato_ganador");
        nombre_perdedor=getIntent().getStringExtra("dato_perdedor");
        puntaje_ganador=getIntent().getStringExtra("dato_puntajeganador");
        puntaje_perdedor=getIntent().getStringExtra("dato_puntajeperdedor");

        tv_nombreganador=(TextView)findViewById(R.id.textView_NombreGanador);
        tv_nombreperdedor=(TextView)findViewById(R.id.textView_NombrePerdedor);
        tv_puntajeganador=(TextView)findViewById(R.id.textView_PuntajeGanador);
        tv_puntajeperdedor=(TextView)findViewById(R.id.textView_PuntajePerdedor);

        tv_nombreganador.setText("Ganador: "+nombre_ganador);
        tv_nombreperdedor.setText("Perdedor: "+nombre_perdedor);
        tv_puntajeganador.setText(puntaje_ganador);
        tv_puntajeperdedor.setText(puntaje_perdedor);

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, nombre_ganador,"0",puntaje_ganador,"0","0");
        (new Transaccion(this)).registrarPuntuacion(this, nombre_perdedor,"0",puntaje_perdedor,"0","0");
    }

    public void irAlMenu(View view){
        Intent i = new Intent(this, Inicio_Conecta4.class);
        startActivity(i);
    }
}
