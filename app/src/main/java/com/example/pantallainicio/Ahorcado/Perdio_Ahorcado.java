package com.example.pantallainicio.Ahorcado;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

public class Perdio_Ahorcado extends AppCompatActivity {

    TextView tv_palabra, tv_puntaje;
    String nickname, puntaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdio_ahorcado);

        tv_palabra = (TextView)findViewById(R.id.textView_palabra_perdio);
        tv_puntaje = (TextView)findViewById(R.id.textView_puntaje_perdio);

        nickname = getIntent().getStringExtra("dato_nickname");
        puntaje = getIntent().getStringExtra("dato_puntaje");

        tv_palabra.setText("Palabra: "+getIntent().getStringExtra("dato_palabra"));
        tv_puntaje.setText("Puntaje: "+puntaje);

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, nickname, puntaje,"0", "0","0");
    }

    public void irAlMenu(View view){
        Intent i = new Intent(this, Inicio_Ahorcado.class);
        i.putExtra("jugador1",nickname);
        startActivity(i);
    }
}
