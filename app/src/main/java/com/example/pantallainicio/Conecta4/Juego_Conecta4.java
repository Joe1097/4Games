package com.example.pantallainicio.Conecta4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pantallainicio.Login;
import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

public class Juego_Conecta4 extends AppCompatActivity {

    // Para el jugador 1 su ficha sera la roja
    // Para el jugador 2 su ficha sera la amarilla

    TextView tv_turno;
    private int puntaje_rojas;
    private int puntaje_amarillas;
    private int turno;
    private int[][] tablero = new int[7][7];
    ImageView ivf0c0, ivf0c1, ivf0c2, ivf0c3, ivf0c4, ivf0c5, ivf0c6,
            ivf1c0, ivf1c1, ivf1c2, ivf1c3, ivf1c4, ivf1c5, ivf1c6,
            ivf2c0, ivf2c1, ivf2c2, ivf2c3, ivf2c4, ivf2c5, ivf2c6,
            ivf3c0, ivf3c1, ivf3c2, ivf3c3, ivf3c4, ivf3c5, ivf3c6,
            ivf4c0, ivf4c1, ivf4c2, ivf4c3, ivf4c4, ivf4c5, ivf4c6,
            ivf5c0, ivf5c1, ivf5c2, ivf5c3, ivf5c4, ivf5c5, ivf5c6,
            ivf6c0, ivf6c1, ivf6c2, ivf6c3, ivf6c4, ivf6c5, ivf6c6;
    private String jugador1, jugador2;

    private SensorManager sm;
    private float acelVal, acelLast, shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_conecta4);

        jugador1 = getIntent().getStringExtra("jugador1");
        jugador2 = getIntent().getStringExtra("jugador2");

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        enlazarControles();
        restaurarValores();

    }

    // Funciones para reiniciar el juego al momento de agitar el celular

    private final SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            acelLast = acelVal;
            acelVal = (float) Math.sqrt((double)(x*x+y*y+z*z));
            float delta = acelVal-acelLast;
            shake = shake*0.9f+delta;
            if(shake>12){
                agitando();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    private void agitando(){
        Toast.makeText(this,"Conecta 4 reiniciado",Toast.LENGTH_SHORT).show();
        restaurarValores();
        ivf0c0.setImageResource(R.drawable.espaciovacio); ivf0c1.setImageResource(R.drawable.espaciovacio); ivf0c2.setImageResource(R.drawable.espaciovacio); ivf0c3.setImageResource(R.drawable.espaciovacio); ivf0c4.setImageResource(R.drawable.espaciovacio); ivf0c5.setImageResource(R.drawable.espaciovacio); ivf0c6.setImageResource(R.drawable.espaciovacio);
        ivf1c0.setImageResource(R.drawable.espaciovacio); ivf1c1.setImageResource(R.drawable.espaciovacio); ivf1c2.setImageResource(R.drawable.espaciovacio); ivf1c3.setImageResource(R.drawable.espaciovacio); ivf1c4.setImageResource(R.drawable.espaciovacio); ivf1c5.setImageResource(R.drawable.espaciovacio); ivf1c6.setImageResource(R.drawable.espaciovacio);
        ivf2c0.setImageResource(R.drawable.espaciovacio); ivf2c1.setImageResource(R.drawable.espaciovacio); ivf2c2.setImageResource(R.drawable.espaciovacio); ivf2c3.setImageResource(R.drawable.espaciovacio); ivf2c4.setImageResource(R.drawable.espaciovacio); ivf2c5.setImageResource(R.drawable.espaciovacio); ivf2c6.setImageResource(R.drawable.espaciovacio);
        ivf3c0.setImageResource(R.drawable.espaciovacio); ivf3c1.setImageResource(R.drawable.espaciovacio); ivf3c2.setImageResource(R.drawable.espaciovacio); ivf3c3.setImageResource(R.drawable.espaciovacio); ivf3c4.setImageResource(R.drawable.espaciovacio); ivf3c5.setImageResource(R.drawable.espaciovacio); ivf3c6.setImageResource(R.drawable.espaciovacio);
        ivf4c0.setImageResource(R.drawable.espaciovacio); ivf4c1.setImageResource(R.drawable.espaciovacio); ivf4c2.setImageResource(R.drawable.espaciovacio); ivf4c3.setImageResource(R.drawable.espaciovacio); ivf4c4.setImageResource(R.drawable.espaciovacio); ivf4c5.setImageResource(R.drawable.espaciovacio); ivf4c6.setImageResource(R.drawable.espaciovacio);
        ivf5c0.setImageResource(R.drawable.espaciovacio); ivf5c1.setImageResource(R.drawable.espaciovacio); ivf5c2.setImageResource(R.drawable.espaciovacio); ivf5c3.setImageResource(R.drawable.espaciovacio); ivf5c4.setImageResource(R.drawable.espaciovacio); ivf5c5.setImageResource(R.drawable.espaciovacio); ivf5c6.setImageResource(R.drawable.espaciovacio);
        ivf6c0.setImageResource(R.drawable.espaciovacio); ivf6c1.setImageResource(R.drawable.espaciovacio); ivf6c2.setImageResource(R.drawable.espaciovacio); ivf6c3.setImageResource(R.drawable.espaciovacio); ivf6c4.setImageResource(R.drawable.espaciovacio); ivf6c5.setImageResource(R.drawable.espaciovacio); ivf6c6.setImageResource(R.drawable.espaciovacio);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Funciones para el juego

    private void enlazarControles(){
        ivf0c0 = findViewById(R.id.imageView_f0c0);
        ivf0c1 = findViewById(R.id.imageView_f0c1);
        ivf0c2 = findViewById(R.id.imageView_f0c2);
        ivf0c3 = findViewById(R.id.imageView_f0c3);
        ivf0c4 = findViewById(R.id.imageView_f0c4);
        ivf0c5 = findViewById(R.id.imageView_f0c5);
        ivf0c6 = findViewById(R.id.imageView_f0c6);
        ivf1c0 = findViewById(R.id.imageView_f1c0);
        ivf1c1 = findViewById(R.id.imageView_f1c1);
        ivf1c2 = findViewById(R.id.imageView_f1c2);
        ivf1c3 = findViewById(R.id.imageView_f1c3);
        ivf1c4 = findViewById(R.id.imageView_f1c4);
        ivf1c5 = findViewById(R.id.imageView_f1c5);
        ivf1c6 = findViewById(R.id.imageView_f1c6);
        ivf2c0 = findViewById(R.id.imageView_f2c0);
        ivf2c1 = findViewById(R.id.imageView_f2c1);
        ivf2c2 = findViewById(R.id.imageView_f2c2);
        ivf2c3 = findViewById(R.id.imageView_f2c3);
        ivf2c4 = findViewById(R.id.imageView_f2c4);
        ivf2c5 = findViewById(R.id.imageView_f2c5);
        ivf2c6 = findViewById(R.id.imageView_f2c6);
        ivf3c0 = findViewById(R.id.imageView_f3c0);
        ivf3c1 = findViewById(R.id.imageView_f3c1);
        ivf3c2 = findViewById(R.id.imageView_f3c2);
        ivf3c3 = findViewById(R.id.imageView_f3c3);
        ivf3c4 = findViewById(R.id.imageView_f3c4);
        ivf3c5 = findViewById(R.id.imageView_f3c5);
        ivf3c6 = findViewById(R.id.imageView_f3c6);
        ivf4c0 = findViewById(R.id.imageView_f4c0);
        ivf4c1 = findViewById(R.id.imageView_f4c1);
        ivf4c2 = findViewById(R.id.imageView_f4c2);
        ivf4c3 = findViewById(R.id.imageView_f4c3);
        ivf4c4 = findViewById(R.id.imageView_f4c4);
        ivf4c5 = findViewById(R.id.imageView_f4c5);
        ivf4c6 = findViewById(R.id.imageView_f4c6);
        ivf5c0 = findViewById(R.id.imageView_f5c0);
        ivf5c1 = findViewById(R.id.imageView_f5c1);
        ivf5c2 = findViewById(R.id.imageView_f5c2);
        ivf5c3 = findViewById(R.id.imageView_f5c3);
        ivf5c4 = findViewById(R.id.imageView_f5c4);
        ivf5c5 = findViewById(R.id.imageView_f5c5);
        ivf5c6 = findViewById(R.id.imageView_f5c6);
        ivf6c0 = findViewById(R.id.imageView_f6c0);
        ivf6c1 = findViewById(R.id.imageView_f6c1);
        ivf6c2 = findViewById(R.id.imageView_f6c2);
        ivf6c3 = findViewById(R.id.imageView_f6c3);
        ivf6c4 = findViewById(R.id.imageView_f6c4);
        ivf6c5 = findViewById(R.id.imageView_f6c5);
        ivf6c6 = findViewById(R.id.imageView_f6c6);

        tv_turno = findViewById(R.id.textView_turno);

    }

    private void restaurarValores(){
        for(int f=0;f<7;f++){
            for(int c=0;c<7;c++){
                tablero[f][c]=0;
            }
        }

        turno=1;
        puntaje_rojas=0;
        puntaje_amarillas=0;

        tv_turno.setText("Turno de "+jugador1);
    }

    public void regresarAlMenu(View view){
        Intent i = new Intent(this, Inicio_Conecta4.class);
        i.putExtra("jugador1",jugador1);
        startActivity(i);
    }

    public void button_c0_Click(View view){
        click(0);
        analizarTablero();
    }

    public void button_c1_Click(View view){
        click(1);
        analizarTablero();
    }

    public void button_c2_Click(View view){
        click(2);
        analizarTablero();
    }

    public void button_c3_Click(View view){
        click(3);
        analizarTablero();
    }

    public void button_c4_Click(View view){
        click(4);
        analizarTablero();
    }

    public void button_c5_Click(View view){
        click(5);
        analizarTablero();
    }

    public void button_c6_Click(View view){
        click(6);
        analizarTablero();
    }

    private void click(int c){
        int f=6;
        while(f>=0){
            if(tablero[f][c]==0) {
                if(turno==1){
                    tablero[f][c]=1;
                    colocarFicha(1,f,c);
                    turno=2;
                }else if(turno==2){
                    tablero[f][c]=2;
                    colocarFicha(2,f,c);
                    turno=1;
                }
                break;
            }
            f--;
        }
        if(turno==1){
            tv_turno.setText("Turno de "+jugador1);
        }else if(turno==2){
            tv_turno.setText("Turno de "+jugador2);
        }
    }

    private void analizarTablero(){
        Intent intent = new Intent(this, FinJuego_Conecta4.class);

        // Chequeo horizontal
        int amarillas=0;
        int rojas=0;
        int p_a=0;
        int p_r=0;
        for(int f=0;f<7;f++){
            for(int c=0;c<7;c++){
                if(tablero[f][c]==1){
                    rojas++;
                    amarillas=0;
                    if(rojas==3){
                        p_r++;
                    }
                }else if(tablero[f][c]==2){
                    amarillas++;
                    rojas=0;
                    if(amarillas==3){
                        p_a++;
                    }

                }else if(tablero[f][c]==0){
                    rojas=0;
                    amarillas=0;
                }
                if(rojas>=4){
                    puntaje_rojas+=600;
                    intent.putExtra("dato_ganador",jugador1);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_rojas));
                    intent.putExtra("dato_perdedor",jugador2);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_amarillas));
                    startActivity(intent);

                    return;
                }else if(amarillas>=4){
                    puntaje_amarillas+=600;
                    intent.putExtra("dato_ganador",jugador2);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_amarillas));
                    intent.putExtra("dato_perdedor",jugador1);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_rojas));
                    startActivity(intent);
                    return;
                }
            }
        }

        // Chequeo vertical
        amarillas=0;
        rojas=0;
        for(int c=0;c<7;c++){
            for(int f=0;f<7;f++){
                if(tablero[f][c]==1){
                    rojas++;
                    amarillas=0;
                    if(rojas==3){
                        p_r++;
                    }
                }else if(tablero[f][c]==2){
                    amarillas++;
                    rojas=0;
                    if(amarillas==3){
                        p_a++;
                    }

                }else if(tablero[f][c]==0){
                    rojas=0;
                    amarillas=0;
                }
                if(rojas>=4){
                    puntaje_rojas+=600;
                    intent.putExtra("dato_ganador",jugador1);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_rojas));
                    intent.putExtra("dato_perdedor",jugador2);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_amarillas));
                    startActivity(intent);
                    return;
                }else if(amarillas>=4){
                    puntaje_amarillas+=600;
                    intent.putExtra("dato_ganador",jugador2);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_amarillas));
                    intent.putExtra("dato_perdedor",jugador1);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_rojas));
                    startActivity(intent);
                    return;
                }
            }
        }

        // Chequeo de las diagonales principales
        int fila=3;
        int columna=0;
        int p=4;
        for(int revs=0;revs<7;revs++){
            amarillas=0;
            rojas=0;
            int f=fila;
            int c=columna;
            for(int i=0;i<p;i++){
                if(tablero[f][c]==1){
                    rojas++;
                    amarillas=0;
                    if(rojas==3){
                        p_r++;
                    }
                }else if(tablero[f][c]==2){
                    amarillas++;
                    rojas=0;
                    if(amarillas==3){
                        p_a++;
                    }

                }else if(tablero[f][c]==0){
                    rojas=0;
                    amarillas=0;
                }
                if(rojas>=4){
                    puntaje_rojas+=600;
                    intent.putExtra("dato_ganador",jugador1);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_rojas));
                    intent.putExtra("dato_perdedor",jugador2);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_amarillas));
                    startActivity(intent);
                    return;
                }else if(amarillas>=4){
                    puntaje_amarillas+=600;
                    intent.putExtra("dato_ganador",jugador2);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_amarillas));
                    intent.putExtra("dato_perdedor",jugador1);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_rojas));
                    startActivity(intent);
                    return;
                }
                c++;
                f++;
            }
            if(revs<3){
                fila--;
                p++;
            }
            else{
                columna++;
                p--;
            }
        }

        // Chequeo de las diagonales secundarias
        fila=0;
        columna=3;
        p=4;
        for(int revs=0;revs<7;revs++){
            amarillas=0;
            rojas=0;
            int f=fila;
            int c=columna;
            for(int i=0;i<p;i++){
                if(tablero[f][c]==1){
                    rojas++;
                    amarillas=0;
                    if(rojas==3){
                        p_r++;
                    }
                }else if(tablero[f][c]==2){
                    amarillas++;
                    rojas=0;
                    if(amarillas==3){
                        p_a++;
                    }

                }else if(tablero[f][c]==0){
                    rojas=0;
                    amarillas=0;
                }
                if(rojas>=4){
                    puntaje_rojas+=600;
                    intent.putExtra("dato_ganador",jugador1);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_rojas));
                    intent.putExtra("dato_perdedor",jugador2);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_amarillas));
                    startActivity(intent);
                    startActivity(intent);
                    return;
                }else if(amarillas>=4){
                    puntaje_amarillas+=600;
                    intent.putExtra("dato_ganador",jugador2);
                    intent.putExtra("dato_puntajeganador",String.valueOf(puntaje_amarillas));
                    intent.putExtra("dato_perdedor",jugador1);
                    intent.putExtra("dato_puntajeperdedor",String.valueOf(puntaje_rojas));
                    startActivity(intent);
                    return;
                }
                c--;
                f++;
            }
            if(revs<3){
                columna++;
                p++;
            }
            else{
                fila++;
                p--;
            }
        }

        puntaje_amarillas=p_a*300;
        puntaje_rojas=p_r*300;
        //Toast t = Toast.makeText(this,jugador1+": "+puntaje_rojas+" "+jugador2+": "+puntaje_amarillas,Toast.LENGTH_SHORT);
        //t.setGravity(Gravity.BOTTOM,0,0);
        //t.show();
    }

    private void colocarFicha(int turno, int fila, int columna){
        if(turno==1){
            switch (fila){
                case 0:
                    switch (columna){
                        case 0:
                            ivf0c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf0c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf0c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf0c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf0c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf0c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf0c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 1:
                    switch (columna){
                        case 0:
                            ivf1c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf1c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf1c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf1c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf1c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf1c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf1c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 2:
                    switch (columna){
                        case 0:
                            ivf2c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf2c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf2c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf2c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf2c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf2c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf2c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 3:
                    switch (columna){
                        case 0:
                            ivf3c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf3c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf3c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf3c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf3c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf3c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf3c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 4:
                    switch (columna){
                        case 0:
                            ivf4c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf4c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf4c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf4c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf4c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf4c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf4c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 5:
                    switch (columna){
                        case 0:
                            ivf5c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf5c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf5c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf5c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf5c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf5c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf5c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
                case 6:
                    switch (columna){
                        case 0:
                            ivf6c0.setImageResource(R.drawable.ficharoja);
                            break;
                        case 1:
                            ivf6c1.setImageResource(R.drawable.ficharoja);
                            break;
                        case 2:
                            ivf6c2.setImageResource(R.drawable.ficharoja);
                            break;
                        case 3:
                            ivf6c3.setImageResource(R.drawable.ficharoja);
                            break;
                        case 4:
                            ivf6c4.setImageResource(R.drawable.ficharoja);
                            break;
                        case 5:
                            ivf6c5.setImageResource(R.drawable.ficharoja);
                            break;
                        case 6:
                            ivf6c6.setImageResource(R.drawable.ficharoja);
                            break;
                    }
                    break;
            }
        }else if(turno==2){
            switch (fila){
                case 0:
                    switch (columna){
                        case 0:
                            ivf0c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf0c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf0c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf0c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf0c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf0c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf0c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 1:
                    switch (columna){
                        case 0:
                            ivf1c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf1c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf1c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf1c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf1c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf1c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf1c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 2:
                    switch (columna){
                        case 0:
                            ivf2c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf2c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf2c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf2c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf2c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf2c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf2c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 3:
                    switch (columna){
                        case 0:
                            ivf3c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf3c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf3c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf3c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf3c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf3c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf3c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 4:
                    switch (columna){
                        case 0:
                            ivf4c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf4c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf4c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf4c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf4c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf4c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf4c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 5:
                    switch (columna){
                        case 0:
                            ivf5c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf5c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf5c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf5c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf5c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf5c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf5c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
                case 6:
                    switch (columna){
                        case 0:
                            ivf6c0.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 1:
                            ivf6c1.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 2:
                            ivf6c2.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 3:
                            ivf6c3.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 4:
                            ivf6c4.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 5:
                            ivf6c5.setImageResource(R.drawable.fichaamarilla);
                            break;
                        case 6:
                            ivf6c6.setImageResource(R.drawable.fichaamarilla);
                            break;
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, jugador1,"0","0", String.valueOf(puntaje_rojas),"0");
        (new Transaccion(this)).registrarPuntuacion(this, jugador2,"0","0", String.valueOf(puntaje_amarillas),"0");
    }

    // Funciones y variables para las notificaciones

    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;

    @Override
    protected void onPause(){
        super.onPause();

        sm.unregisterListener(sensorListener);

        notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);

        super.onPause();
        try {
            Thread.sleep(5000);
            crearNotificacion();
        }catch (InterruptedException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        try {
            Thread.sleep(5000);
            crearNotificacion();
        }catch (InterruptedException e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void crearNotificacion(){
        notificacion.setSmallIcon(R.mipmap.ic_launcher);
        notificacion.setTicker("Nueva notificación");
        notificacion.setPriority(Notification.PRIORITY_HIGH);
        notificacion.setContentTitle("Juegos");
        notificacion.setContentText("Continúa jugando Conecta4 :)");
        notificacion.setWhen(System.currentTimeMillis());
        Intent i = new Intent(this, Login.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(pi);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica,notificacion.build());
    }

}
