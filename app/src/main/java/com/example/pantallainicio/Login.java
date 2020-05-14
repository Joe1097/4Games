// Juego 1: Ahorcado
// Juego 2: Conecta4
// Juego 3: Gato
// Juego 4: Memorama

package com.example.pantallainicio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pantallainicio.bd.ConexionSQLiteHelper;

public class Login extends AppCompatActivity {

    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed=(EditText)findViewById(R.id.editText_nickname);

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios", null, 1);
    }

    public void ingresar(View view){
        String nickname=ed.getText().toString();
        if(!nickname.isEmpty()){
            Intent i = new Intent(this, MenuPrincipal.class);
            i.putExtra("dato_nickname", nickname);
            startActivity(i);
        }
        else {
            Toast.makeText(this,"Por favor, ingresa tu nickname",Toast.LENGTH_SHORT).show();
        }
    }
}
