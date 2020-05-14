package com.example.pantallainicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pantallainicio.bd.Transaccion;


public class Puntuaciones extends AppCompatActivity {

    TextView tv_gato, tv_memorama, tv_conecta4, tv_ahorcado;
    EditText et_nickname;
    String jugador1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuaciones);

        tv_ahorcado=findViewById(R.id.textView_PuntajeAhorcado);
        tv_conecta4=findViewById(R.id.textView_PuntajeConecta4);
        tv_gato=findViewById(R.id.textView_PuntajeGato);
        tv_memorama=findViewById(R.id.textView_PuntajeMemorama);
        et_nickname=findViewById(R.id.editText_Nickname);

        jugador1 = getIntent().getStringExtra("jugador1");

        et_nickname.setText(jugador1);

    }

    public void verPuntuaciones(View view){
        if(!et_nickname.getText().toString().isEmpty()){
            String[] datos = (new Transaccion(this)).consultar(this, et_nickname.getText().toString());
            tv_ahorcado.setText("Ahorcado: "+datos[0]);
            tv_conecta4.setText("Conecta 4: "+datos[1]);
            tv_gato.setText("Gato: "+datos[2]);
            tv_memorama.setText("Memorama: "+datos[3]);
        }
        else {
            Toast.makeText(this, "Ingresa un nickname",Toast.LENGTH_SHORT).show();
        }

    }

    public void regresarAlMenu(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("dato_nickname",jugador1);
        startActivity(i);
    }

}
