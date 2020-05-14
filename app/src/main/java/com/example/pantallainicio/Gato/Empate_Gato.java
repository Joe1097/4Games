package com.example.pantallainicio.Gato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

public class Empate_Gato extends AppCompatActivity implements View.OnClickListener {

    TextView gan, nomj1, nomj2, puntajej1, puntajej2;
    String nj1, nj2, pj1, pj2;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empate_gato);
        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        nj1=extras.getString("jugador1");
        nj2=extras.getString("jugador2");
        pj1=String.valueOf(extras.getInt("puntuacion_j1"));
        pj2=String.valueOf(extras.getInt("puntuacion_j2"));
        nomj1=findViewById(R.id.nombre_j1);
        nomj2=findViewById(R.id.nombre_j2);
        puntajej1=findViewById(R.id.puntaje_j1);
        puntajej2=findViewById(R.id.puntaje_j2);
        nomj1.setText("Puntaje "+nj1+": ");
        nomj2.setText("Puntaje "+nj2+": ");
        puntajej1.setText(pj1);
        puntajej2.setText(pj2);

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, nj1,"0","0", pj1,"0");
        (new Transaccion(this)).registrarPuntuacion(this, nj2,"0","0", pj2,"0");

        boton=(Button)findViewById(R.id.continuar);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.continuar:
                Intent i=new Intent(this, AreaJuego_Gato.class);
                i.putExtra("jugador1",nj1);
                i.putExtra("jugador2",nj2);
                startActivity(i);
                break;
        }
    }
}
