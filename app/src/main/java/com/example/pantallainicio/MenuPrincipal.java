package com.example.pantallainicio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.pantallainicio.Ahorcado.Inicio_Ahorcado;

public class MenuPrincipal extends AppCompatActivity implements View.OnClickListener{

    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        nickname = getIntent().getStringExtra("dato_nickname");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_memorama:
                Intent i = new Intent(this, Jugador2.class);
                i.putExtra("jugador1", nickname);
                i.putExtra("juego","memorama");
                startActivity(i);
                break;
            case R.id.button_ahorcado:
                Intent i2 = new Intent(this, Inicio_Ahorcado.class);
                i2.putExtra("jugador1", nickname);
                startActivity(i2);
                break;
            case R.id.button_gato:
                Intent i3 = new Intent(this, Jugador2.class);
                i3.putExtra("jugador1", nickname);
                i3.putExtra("juego","gato");
                startActivity(i3);
                break;
            case R.id.button_conecta4:
                Intent i4 = new Intent(this, Jugador2.class);
                i4.putExtra("jugador1", nickname);
                i4.putExtra("juego","conecta4");
                startActivity(i4);
                break;
            case R.id.button_puntuaciones:
                Intent i5 = new Intent(this, Puntuaciones.class);
                i5.putExtra("jugador1", nickname);
                startActivity(i5);
                break;
        }
    }

}
