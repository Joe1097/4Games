package com.example.pantallainicio.Ahorcado;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pantallainicio.Login;
import com.example.pantallainicio.R;
import com.example.pantallainicio.bd.Transaccion;

import java.util.ArrayList;
import java.util.Random;

public class Juego_Ahorcado extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_palabra, tv_letras_usadas, tv_oportunidades, tv_puntaje;
    private Button b_q, b_w, b_e, b_r, b_t, b_y, b_u, b_i, b_o, b_p, b_a, b_s, b_d, b_f, b_g, b_h, b_j, b_k, b_l, b_enie, b_z, b_x, b_c, b_v, b_b, b_n,  b_m;
    private String palabra;
    private int oportunidades, puntaje;
    static ArrayList<String> letras_usadas = new ArrayList<>();
    private ImageView iv_ahorcado;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_ahorcado);

        String categoria = getIntent().getStringExtra("dato_categoria");
        String dificultad = getIntent().getStringExtra("dato_dificultad");
        nickname = getIntent().getStringExtra("dato_nickname");
        Toast.makeText(this, "Categoría: "+categoria+"\n"+"Dificultad: "+dificultad, Toast.LENGTH_SHORT).show();

        oportunidades = 6;
        puntaje = 60;
        letras_usadas.clear();

        enlazarControles(); // Se llama este método

        palabra = elegirPalabra(categoria, dificultad); // Se llama el método que obtiene la palabra y la guarda en la variable "palabra"

        // El texto de tv_palabra será la palabra seleccionada pero con cada letra ocultada con un "-"
        String palabra_cifrada="";
        for(int i=0;i<palabra.length();i++){
            palabra_cifrada=palabra_cifrada.concat("-");
        }
        tv_palabra.setText(palabra_cifrada);
    }

    // Método que hace enlace entre la interfaz gráfica y el código
    private void enlazarControles(){
        tv_palabra = (TextView)findViewById(R.id.textView_titulo_conecta4);
        tv_oportunidades = (TextView)findViewById(R.id.textView_oportunidades);
        tv_puntaje = (TextView)findViewById(R.id.textView_Puntaje);
        tv_letras_usadas = (TextView)findViewById(R.id.textView_letrasUsadas);
        iv_ahorcado = (ImageView) findViewById(R.id.imageView_ahorcado);

        b_q = (Button)findViewById(R.id.button_q);
        b_w = (Button)findViewById(R.id.button_w);
        b_e = (Button)findViewById(R.id.button_e);
        b_r = (Button)findViewById(R.id.button_r);
        b_t = (Button)findViewById(R.id.button_t);
        b_y = (Button)findViewById(R.id.button_y);
        b_u = (Button)findViewById(R.id.button_u);
        b_i = (Button)findViewById(R.id.button_i);
        b_o = (Button)findViewById(R.id.button_o);
        b_p = (Button)findViewById(R.id.button_p);
        b_a = (Button)findViewById(R.id.button_a);
        b_s = (Button)findViewById(R.id.button_s);
        b_d = (Button)findViewById(R.id.button_d);
        b_f = (Button)findViewById(R.id.button_f);
        b_g = (Button)findViewById(R.id.button_g);
        b_h = (Button)findViewById(R.id.button_h);
        b_j = (Button)findViewById(R.id.button_j);
        b_k = (Button)findViewById(R.id.button_k);
        b_l = (Button)findViewById(R.id.button_l);
        b_enie = (Button)findViewById(R.id.button_enie);
        b_z = (Button)findViewById(R.id.button_z);
        b_x = (Button)findViewById(R.id.button_x);
        b_c = (Button)findViewById(R.id.button_c);
        b_v = (Button)findViewById(R.id.button_v);
        b_b = (Button)findViewById(R.id.button_b);
        b_n = (Button)findViewById(R.id.button_n);
        b_m = (Button)findViewById(R.id.button_m);

        b_q.setOnClickListener(this);
        b_w.setOnClickListener(this);
        b_e.setOnClickListener(this);
        b_r.setOnClickListener(this);
        b_t.setOnClickListener(this);
        b_y.setOnClickListener(this);
        b_u.setOnClickListener(this);
        b_i.setOnClickListener(this);
        b_o.setOnClickListener(this);
        b_p.setOnClickListener(this);
        b_a.setOnClickListener(this);
        b_s.setOnClickListener(this);
        b_d.setOnClickListener(this);
        b_f.setOnClickListener(this);
        b_g.setOnClickListener(this);
        b_h.setOnClickListener(this);
        b_j.setOnClickListener(this);
        b_k.setOnClickListener(this);
        b_l.setOnClickListener(this);
        b_enie.setOnClickListener(this);
        b_z.setOnClickListener(this);
        b_x.setOnClickListener(this);
        b_c.setOnClickListener(this);
        b_v.setOnClickListener(this);
        b_b.setOnClickListener(this);
        b_n.setOnClickListener(this);
        b_m.setOnClickListener(this);
    }

    // Metodo para generar el numero random dentro de un rango
    public static int generateRandomIntIntRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    // Método que retorna aleatoriamente una palabra segun la dificultad y la categoria especificada
    private String elegirPalabra(String categoria, String dificultad){
        String palabra="";
        switch (categoria){
            case "Animales":
                if(dificultad.equals("Fácil")){
                    String palabras_posibles[]={"gato","raton","panda","nutria","lince"}; // 4 a 6
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if (dificultad.equals("Moderada")){
                    String palabras_posibles[]={"pelicano","pinguino","chimpance","tortuga","caracol"}; // 7 a 9
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if(dificultad.equals("Difícil")){
                    String palabras_posibles[]={"ornitorrinco","rinoceronte","hipopotamo","murcielago","saltamontes"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)]; // 10 a 12
                }
                break;
            case "Transportes":
                if(dificultad.equals("Fácil")){
                    String palabras_posibles[]={"auto","yate","barco","avion","camion"}; // 4 a 6
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if (dificultad.equals("Moderada")){
                    String palabras_posibles[]={"autobus","metrobus","patineta","submarino","bicicleta"}; // 7 a 9
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if(dificultad.equals("Difícil")){
                    String palabras_posibles[]={"transatlantico","motocicleta","helicoptero","ferrocarril","cuatrimoto"}; // 10 a 12
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                }
                break;
            case "Colores":
                if(dificultad.equals("Fácil")){
                    String palabras_posibles[]={"rojo","beige","blanco","verde","indigo"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if (dificultad.equals("Moderada")){
                    String palabras_posibles[]={"purpura","turquesa","esmeralda","naranja","lavanda"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if(dificultad.equals("Difícil")){
                    String palabras_posibles[]={"aguamarina","azulmarino","albaricoque","azulectrico","verdepasto"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                }
                break;
            case "Electrodomésticos":
                if(dificultad.equals("Fácil")){
                    String palabras_posibles[]={"horno","boiler","estufa","foco"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,3)];
                } else if (dificultad.equals("Moderada")){
                    String palabras_posibles[]={"plancha","estereo","tostadora","licuadora","secadora"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if(dificultad.equals("Difícil")){
                    String palabras_posibles[]={"television","microondas","computadora","sandwichera","lavavajillas"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                }
                break;
            case "Astros":
                if(dificultad.equals("Fácil")){
                    String palabras_posibles[]={"luna","cometa","pulsar","cuasar","bolido"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if (dificultad.equals("Moderada")){
                    String palabras_posibles[]={"galaxia","planeta","estrella","nebulosa","meteoro"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                } else if(dificultad.equals("Difícil")){
                    String palabras_posibles[]={"meteoroide","enanablanca","agujeronegro","constelacion","supercumulo"};
                    palabra = palabras_posibles[generateRandomIntIntRange(0,4)];
                }
                break;
        }
        return palabra;
    }

    // Método que es llamado cada vez que el jugador hace clic en una letra
    @Override
    public void onClick(View v) {
        // Se obtiene el caracter según el botón al que se le hizo clic
        char caracter=' ';
        switch (v.getId()){
            case R.id.button_q:
                caracter='q';
                break;
            case R.id.button_w:
                caracter='w';
                break;
            case R.id.button_e:
                caracter='e';
                break;
            case R.id.button_r:
                caracter='r';
                break;
            case R.id.button_t:
                caracter='t';
                break;
            case R.id.button_y:
                caracter='y';
                break;
            case R.id.button_u:
                caracter='u';
                break;
            case R.id.button_i:
                caracter='i';
                break;
            case R.id.button_o:
                caracter='o';
                break;
            case R.id.button_p:
                caracter='p';
                break;
            case R.id.button_a:
                caracter='a';
                break;
            case R.id.button_s:
                caracter='s';
                break;
            case R.id.button_d:
                caracter='d';
                break;
            case R.id.button_f:
                caracter='f';
                break;
            case R.id.button_g:
                caracter='g';
                break;
            case R.id.button_h:
                caracter='h';
                break;
            case R.id.button_j:
                caracter='j';
                break;
            case R.id.button_k:
                caracter='k';
                break;
            case R.id.button_l:
                caracter='l';
                break;
            case R.id.button_enie:
                caracter='ñ';
                break;
            case R.id.button_z:
                caracter='z';
                break;
            case R.id.button_x:
                caracter='x';
                break;
            case R.id.button_c:
                caracter='c';
                break;
            case R.id.button_v:
                caracter='v';
                break;
            case R.id.button_b:
                caracter='b';
                break;
            case R.id.button_n:
                caracter='n';
                break;
            case R.id.button_m:
                caracter='m';
                break;
        }

        if(!letras_usadas.contains(String.valueOf(caracter))){ // Si el ArrayList letras_usadas tadavía no contiene el caracter, ...

            String palabra_decifrandose = tv_palabra.getText().toString(); // Se obtiene le texto actual de tv_palabra,
            StringBuilder p = new StringBuilder(palabra_decifrandose); // se convierte a un nuevo tipo de variable, parecido a String, pero con otras funciones
            boolean acerto=false; // Varible para poder determinar si el jugador acertó

            for(int i=0;i<palabra.length();i++){

                if(palabra.substring(i,i+1).equals(String.valueOf(caracter))){ // Si la palabra contiene el caracter en la posicion i, ...

                    p.setCharAt(i,caracter); // el caracter "-" de la posicion i es cambiado por el caracter
                    acerto=true; // El jugador acertó, la variable se hace true

                    Toast t = Toast.makeText(this, "Acertaste!", Toast.LENGTH_SHORT); // se le notifica al jugador que acertó
                    t.show();

                    puntaje+=20;
                    tv_puntaje.setText("Puntaje: "+puntaje);
                }
                if(i==palabra.length()-1 && !acerto){ // Si, al final del ciclo, no se encontró el caracter dentro de la palabra

                    if(puntaje-10>=0){ // Si el puntaje todavia va a ser positivo...,
                        puntaje-=10;
                        tv_puntaje.setText("Puntaje: "+puntaje);
                        Toast t = Toast.makeText(this, "Fallaste!", Toast.LENGTH_SHORT); // se le notifica al jugador que falló
                        t.show();
                    }
                    else{
                        Toast t = Toast.makeText(this, "Fallaste!", Toast.LENGTH_SHORT); // se le notifica al jugador que falló
                        t.show();
                    }

                    oportunidades--; // Ahora le queda una oportunidad menos al jugador
                    tv_oportunidades.setText("Oportunidades: "+oportunidades);
                    cambiarImagen();
                }
            }

            tv_palabra.setText(p.toString());
            letras_usadas.add(String.valueOf(caracter)); // Al ArrayList letras_usadas se le agrega un nuevo elemento, el caracter
            // Se forma un String con todas las letras que tiene el ArrayList letras_usadas
            String string_letras_usadas = "";
            for(String s:letras_usadas){
                string_letras_usadas = string_letras_usadas.concat(s);
            }
            tv_letras_usadas.setText("Letras usadas: "+string_letras_usadas);
        }
        else{ // si ya lo contiene, ...

            if(puntaje-10>=0){

                puntaje-=10;
                tv_puntaje.setText("Puntaje: "+puntaje);
                Toast t = Toast.makeText(this, "Fallaste, la letra "+(String.valueOf(caracter))+" ya la habías usado!", Toast.LENGTH_SHORT); // se le notifica al jugador que ya había tecleado la letra anteriormente
                t.show();
            }
            else{
                Toast t = Toast.makeText(this, "Fallaste, la letra "+(String.valueOf(caracter))+" ya la habías usado!", Toast.LENGTH_SHORT); // se le notifica al jugador que ya había tecleado la letra anteriormente
                t.show();
            }

            oportunidades--; // Ahora le queda una oportunidad menos al jugador
            tv_oportunidades.setText("Oportunidades: "+oportunidades);
            cambiarImagen();
        }

        if(oportunidades==0){ // Si el jugador se ha quedado sin oportunidades, ...

            Toast t = Toast.makeText(this, "Perdiste!", Toast.LENGTH_SHORT); // se le notifica al jugador que ha perdido
            t.show();

            Intent i = new Intent(this, Perdio_Ahorcado.class);
            i.putExtra("dato_palabra", palabra);
            i.putExtra("dato_puntaje", String.valueOf(puntaje));
            i.putExtra("dato_nickname",nickname);
            startActivity(i);
        }
        else if(!tv_palabra.getText().toString().contains("-")){ // Si el jugador ha descubierto todas las letras de la palabra

            Toast t = Toast.makeText(this, "Ganaste!!!", Toast.LENGTH_SHORT);  // se le notifica al jugador que ha ganado
            t.show();

            puntaje+=50;
            tv_puntaje.setText("Puntaje: "+puntaje);

            Intent i = new Intent(this, Gano_Ahorcado.class);
            i.putExtra("dato_palabra", palabra);
            i.putExtra("dato_puntaje", String.valueOf(puntaje));
            i.putExtra("dato_nickname",nickname);
            startActivity(i);
        }
    }

    // Metodo que cambia la imagen del ahorcado cuando el jugador pierda una oportunidad
    private void cambiarImagen(){
        switch (oportunidades){
            case 5:
                iv_ahorcado.setImageResource(R.drawable.cabeza);
                break;
            case 4:
                iv_ahorcado.setImageResource(R.drawable.torso);
                break;
            case 3:
                iv_ahorcado.setImageResource(R.drawable.brazoizq);
                break;
            case 2:
                iv_ahorcado.setImageResource(R.drawable.brazoder);
                break;
            case 1:
                iv_ahorcado.setImageResource(R.drawable.piernaizq);
                break;
            case 0:
                iv_ahorcado.setImageResource(R.drawable.piernader);
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
        (new Transaccion(this)).registrarPuntuacion(this, nickname, String.valueOf(puntaje),"0", "0","0");
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
        notificacion.setContentText("Continúa jugando Ahorcado :)");
        notificacion.setWhen(System.currentTimeMillis());
        Intent i = new Intent(this, Login.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(pi);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica,notificacion.build());
    }

}
