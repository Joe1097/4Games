package com.example.pantallainicio.Gato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pantallainicio.R;

public class Inicio_Gato extends AppCompatActivity implements View.OnClickListener {

    EditText j1,j2;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_gato);
        boton=(Button)findViewById(R.id.sig1);
        j1=(EditText) findViewById(R.id.jugador1);
        j2=(EditText) findViewById(R.id.jugador2);
        boton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.sig1:
                String jug1=j1.getText().toString();
                String jug2=j2.getText().toString();
                String p="0";

                Intent siguiente=new Intent(Inicio_Gato.this, AreaJuego_Gato.class);
                siguiente.putExtra("jugador1",jug1);
                siguiente.putExtra("jugador2",jug2);
                siguiente.putExtra("ganador",p);
                Toast toast1 = Toast.makeText(getApplicationContext(),"X "+jug1+"    |     "+"O "+jug2, Toast.LENGTH_LONG);
                toast1.show();
                startActivity(siguiente);
                break;
        }
    }
}
