package com.example.pantallainicio.Gato;

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pantallainicio.Login;
import com.example.pantallainicio.MenuPrincipal;
import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

public class AreaJuego_Gato extends AppCompatActivity  implements View.OnClickListener {

    Toast toast2,toast3,toast4;
    String j1,j2;
    TextView turno;
    Intent siguiente,empate,intent;
    Bundle extras;

    private int puntuacion_j1;
    private int puntuacion_j2;
    private boolean gamestarted;
    Thread crono;
    private int segundos;

    int trn,win1,win2,win3,win4,win5,win6,win7,win8,win9;
    ImageButton i1, i2, i3, i4, i5, i6, i7, i8, i9,t;
    boolean im1,im2,im3,im4,im5,im6,im7,im8,im9,yagano;

    private SensorManager sm;
    private float acelVal, acelLast, shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_juego_gato);

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

        turno = (TextView) findViewById(R.id.tjugador);
        intent = getIntent();
        extras = intent.getExtras();

        j1 = extras.getString("jugador1");
        j2 = extras.getString("jugador2");

        restaurarValores();

        siguiente=new Intent(AreaJuego_Gato.this, FinJuego_Gato.class);
        empate=new Intent(AreaJuego_Gato.this, AreaJuego_Gato.class);
        toast2 = Toast.makeText(getApplicationContext(),"Movimiento no permitido", Toast.LENGTH_LONG);

        i1 = (ImageButton) findViewById(R.id.i1);
        i2 = (ImageButton) findViewById(R.id.i2);
        i3 = (ImageButton) findViewById(R.id.i3);
        i4 = (ImageButton) findViewById(R.id.i4);
        i5 = (ImageButton) findViewById(R.id.i5);
        i6 = (ImageButton) findViewById(R.id.i6);
        i7 = (ImageButton) findViewById(R.id.i7);
        i8 = (ImageButton) findViewById(R.id.i8);
        i9 = (ImageButton) findViewById(R.id.i9);
        t = (ImageButton) findViewById(R.id.turno);

        i1.setOnClickListener(this);
        i2.setOnClickListener(this);
        i3.setOnClickListener(this);
        i4.setOnClickListener(this);
        i5.setOnClickListener(this);
        i6.setOnClickListener(this);
        i7.setOnClickListener(this);
        i8.setOnClickListener(this);
        i9.setOnClickListener(this);

        crono = new Thread(new Runnable(){
            @Override
            public void run(){
                while (true){
                    if(gamestarted){
                        try{
                            Thread.sleep(1000);
                        }catch (InterruptedException e){  }
                        segundos++;

                    }
                }
            }
        });
        crono.start();
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
        Toast.makeText(this,"Gato reiniciado",Toast.LENGTH_SHORT).show();
        restaurarValores();
        i1.setImageResource(R.drawable.gato);
        i2.setImageResource(R.drawable.gato);
        i3.setImageResource(R.drawable.gato);
        i4.setImageResource(R.drawable.gato);
        i5.setImageResource(R.drawable.gato);
        i6.setImageResource(R.drawable.gato);
        i7.setImageResource(R.drawable.gato);
        i8.setImageResource(R.drawable.gato);
        t.setImageResource(R.drawable.equis);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //sm.unregisterListener(sensorListener);
    }

    private void restaurarValores(){
        im1=true; im2=true; im3=true; im4=true; im5=true;im6=true;im7=true;im8=true;im9=true;yagano=false;
        turno.setText(j1);
        trn=1;
        puntuacion_j1=600;
        puntuacion_j2=600;
        gamestarted=false;
        segundos=0;
    }

    @Override
    public void onClick(View v) {
        gamestarted = true;
        switch (v.getId()) {
            case R.id.i1:
                if(im1==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i1.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win1=1;
                        if(win1==win2&&win2==win3||win1==win4&&win4==win7||win1==win5&&win5==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();

                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i1.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win1=2;
                        if(win1==win2&&win2==win3||win1==win4&&win4==win7||win1==win5&&win5==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im1=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i2:
                if(im2==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i2.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win2=1;
                        if(win1==win2&&win2==win3||win2==win5&&win5==win8)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i2.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win2=2;
                        if(win1==win2&&win2==win3||win2==win5&&win5==win8)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        startActivity(siguiente);
                    }
                    im2=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i3:
                if(im3==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i3.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win3=1;
                        if(win1==win2&&win2==win3||win3==win6&&win6==win9||win3==win5&&win5==win7)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i3.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win3=2;
                        if(win1==win2&&win2==win3||win3==win6&&win6==win9||win3==win5&&win5==win7)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im3=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i4:
                if(im4==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i4.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win4=1;
                        if(win1==win4&&win4==win7||win4==win5&&win5==win6)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i4.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win4=2;
                        if(win1==win4&&win4==win7||win4==win5&&win5==win6)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im4=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i5:
                if(im5==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i5.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win5=1;
                        if(win1==win5&&win5==win9||win2==win5&&win5==win8||win3==win5&&win5==win7||win4==win5&&win5==win6)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i5.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win5=2;
                        if(win1==win5&&win5==win9||win2==win5&&win5==win8||win3==win5&&win5==win7||win4==win5&&win5==win6)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im5=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i6:
                if(im6==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i6.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win6=1;
                        if(win4==win5&&win5==win6||win3==win6&&win6==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i6.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win6=2;
                        if(win4==win5&&win5==win6||win3==win6&&win6==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im6=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i7:
                if(im7==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i7.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win7=1;
                        if(win1==win4&&win4==win7||win7==win5&&win5==win3||win7==win8&&win8==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i7.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win7=2;
                        if(win1==win4&&win4==win7||win7==win5&&win5==win3||win7==win8&&win8==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im7=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i8:
                if(im8==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i8.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win8=1;
                        if(win8==win5&&win5==win2||win8==win7&&win7==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i8.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win8=2;
                        if(win8==win5&&win5==win2||win8==win7&&win7==win9)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im8=false;
                }
                else
                    toast2.show();
                break;
            case R.id.i9:
                if(im9==true)
                {
                    if (trn==1||trn==3||trn==5||trn==7||trn==9)
                    {
                        i9.setImageResource(R.drawable.equis);
                        turno.setText(j2);
                        win9=1;
                        if(win9==win5&&win5==win1||win9==win6&&win6==win3||win9==win8&&win8==win7)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos)+100;
                            puntuacion_j2=puntuacion_j2-(10*segundos);
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j1);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j1, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.circulo);
                    }
                    else
                    {
                        i9.setImageResource(R.drawable.circulo);
                        turno.setText(j1);
                        win9=2;
                        if(win9==win5&&win5==win1||win9==win6&&win6==win3||win9==win8&&win8==win7)
                        {
                            puntuacion_j1=puntuacion_j1-(10*segundos);
                            puntuacion_j2=puntuacion_j2-(10*segundos)+100;
                            if(puntuacion_j1<0)
                                puntuacion_j1=10;
                            if(puntuacion_j2<0)
                                puntuacion_j2=0;
                            siguiente.putExtra("jugador1",extras.getString("jugador1"));
                            siguiente.putExtra("jugador2",extras.getString("jugador2"));
                            siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                            siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                            siguiente.putExtra("ganador",j2);
                            yagano=true;
                            toast3 = Toast.makeText(getApplicationContext(),"El ganador es "+j2, Toast.LENGTH_LONG);
                            toast3.show();
                            startActivity(siguiente);
                        }
                        t.setImageResource(R.drawable.equis);
                    }
                    trn++;
                    if(trn>=10&&yagano!=true)
                    {
                        Intent siguiente=new Intent(v.getContext(), Empate_Gato.class);
                        puntuacion_j1=puntuacion_j1-(10*segundos);
                        puntuacion_j2=puntuacion_j1;
                        if(puntuacion_j1<0)
                            puntuacion_j1=10;
                        if(puntuacion_j2<0)
                            puntuacion_j2=0;
                        siguiente.putExtra("jugador1",extras.getString("jugador1"));
                        siguiente.putExtra("jugador2",extras.getString("jugador2"));
                        siguiente.putExtra("puntuacion_j1",puntuacion_j1);
                        siguiente.putExtra("puntuacion_j2",puntuacion_j2);
                        toast4 = Toast.makeText(getApplicationContext(),"Es un empate", Toast.LENGTH_LONG);
                        toast4.show();
                        startActivity(siguiente);
                    }
                    im9=false;
                }
                else
                    toast2.show();
                break;
            case R.id.button_RegresarAlMenu_Gato:
                Intent i = new Intent(this, MenuPrincipal.class);
                i.putExtra("dato_nickname",j1);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, j1,"0","0", String.valueOf(puntuacion_j1),"0");
        (new Transaccion(this)).registrarPuntuacion(this, j2,"0","0", String.valueOf(puntuacion_j2),"0");

    }

    // Funciones y variables para las notificaciones

    NotificationCompat.Builder notificacion;
    private static final int idUnica = 51623;

    @Override
    protected void onPause(){
        super.onPause();

        //sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

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
        notificacion.setContentText("Continúa jugando Gato :)");
        notificacion.setWhen(System.currentTimeMillis());
        Intent i = new Intent(this, Login.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(pi);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica,notificacion.build());
    }
}