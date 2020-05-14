package com.example.pantallainicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pantallainicio.Ahorcado.Inicio_Ahorcado;
import com.example.pantallainicio.Conecta4.Inicio_Conecta4;
import com.example.pantallainicio.Gato.AreaJuego_Gato;
import com.example.pantallainicio.Gato.Inicio_Gato;
import com.example.pantallainicio.Memorama.Juego_Memorama;

public class Jugador2 extends AppCompatActivity {

    private String jugador1, juego;
    private EditText et_jugador2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugador2);

        et_jugador2 = findViewById(R.id.editText_nickname2);

        jugador1 = getIntent().getStringExtra("jugador1");
        juego = getIntent().getStringExtra("juego");

    }

    public void ir(View view){
        String jugador2=et_jugador2.getText().toString();
        if(!jugador2.isEmpty()){
            if(!jugador2.equals(jugador1)){
                switch (juego){
                    case "memorama":
                        Intent i1 = new Intent(this, Juego_Memorama.class);
                        i1.putExtra("jugador1", jugador1);
                        i1.putExtra("jugador2", jugador2);
                        startActivity(i1);
                        break;
                    case "gato":
                        Intent i2 = new Intent(this, AreaJuego_Gato.class);
                        i2.putExtra("jugador1", jugador1);
                        i2.putExtra("jugador2", jugador2);
                        startActivity(i2);
                        break;
                    case "conecta4":
                        Intent i3 = new Intent(this, Inicio_Conecta4.class);
                        i3.putExtra("jugador1", jugador1);
                        i3.putExtra("jugador2", jugador2);
                        startActivity(i3);
                        break;
                }
            }
            else {
                Toast.makeText(this,"Jugador 2, ingresa un nickname diferente al del jugador 1",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this,"Por favor jugador 2, ingresa tu nickname",Toast.LENGTH_SHORT).show();
        }
    }

}
