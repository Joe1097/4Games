package com.example.pantallainicio.Memorama;

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

public class Juego_Memorama extends AppCompatActivity implements View.OnClickListener {

    Memoria tarjeta_volteada;
    int id_imagen;
    boolean primer_movimiento;
    private int puntaje_j1, puntaje_j2;
    private String jugador1, jugador2;
    private int turno;

    private TextView tv_puntaje_j1, tv_puntaje_j2, tv_turno;
    private ImageButton i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i11, i12, i13, i14, i15, i16;
    private int vectorimagenes[] = {R.drawable.taco, R.drawable.pizza, R.drawable.sushi, R.drawable.cake,
                                    R.drawable.donut, R.drawable.coffe, R.drawable.combo, R.drawable.ice_cream,
                                    R.drawable.taco, R.drawable.pizza, R.drawable.sushi, R.drawable.cake,
                                    R.drawable.donut, R.drawable.coffe, R.drawable.combo, R.drawable.ice_cream};
    private boolean ocupado[] = new boolean[16];
    private Memoria[] memorias = new Memoria[16];
    public ImageButton[] vectorimagen=new ImageButton[16];

    private SensorManager sm;
    private float acelVal, acelLast, shake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_memorama);
        iniciarVariables();

        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.registerListener(sensorListener, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

        acelVal = SensorManager.GRAVITY_EARTH;
        acelLast = SensorManager.GRAVITY_EARTH;
        shake = 0.00f;

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
        Toast.makeText(this,"Reinciciando memorama...",Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        finish();
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //sm.unregisterListener(sensorListener);
    }

    // Funciones para el juego

    private void restaurarValores(){

        vectorimagen[0]=i1;
        vectorimagen[1]=i2;
        vectorimagen[2]=i3;
        vectorimagen[3]=i4;
        vectorimagen[4]=i5;
        vectorimagen[5]=i6;
        vectorimagen[6]=i7;
        vectorimagen[7]=i8;
        vectorimagen[8]=i9;
        vectorimagen[9]=i10;
        vectorimagen[10]=i11;
        vectorimagen[11]=i12;
        vectorimagen[12]=i13;
        vectorimagen[13]=i14;
        vectorimagen[14]=i15;
        vectorimagen[15]=i16;

        for(int i=0; i<vectorimagen.length;i++){
            memorias[i]=new Memoria(vectorimagen[i],regresaImagen());
            vectorimagen[i].setOnClickListener(this);
        }

        jugador1=getIntent().getStringExtra("jugador1");
        jugador2=getIntent().getStringExtra("jugador2");

        puntaje_j1=0;
        puntaje_j2=0;

        turno=1;

        tv_turno.setText("Turno de "+jugador1);
        tv_puntaje_j1.setText(jugador1+": 0");
        tv_puntaje_j2.setText(jugador2+": 0");

        primer_movimiento=false;
    }

    private int regresaImagen(){
        int i=0;
        do{
            i=((int)(Math.random()*16));

        }while (validarRepetidos(i));
        this.ocupado[i]=true;
        return vectorimagenes[i];
    }

    private boolean validarRepetidos(int i){
        if(ocupado[i])
            return true;
        return false;
    }

    private void iniciarVariables(){
        i1=(ImageButton)findViewById(R.id.i1);
        i2=(ImageButton)findViewById(R.id.i2);
        i3=(ImageButton)findViewById(R.id.i3);
        i4=(ImageButton)findViewById(R.id.i4);
        i5=(ImageButton)findViewById(R.id.i5);
        i6=(ImageButton)findViewById(R.id.i6);
        i7=(ImageButton)findViewById(R.id.i7);
        i8=(ImageButton)findViewById(R.id.i8);
        i9=(ImageButton)findViewById(R.id.i9);
        i10=(ImageButton)findViewById(R.id.i10);
        i11=(ImageButton)findViewById(R.id.i11);
        i12=(ImageButton)findViewById(R.id.i12);
        i13=(ImageButton)findViewById(R.id.i13);
        i14=(ImageButton)findViewById(R.id.i14);
        i15=(ImageButton)findViewById(R.id.i15);
        i16=(ImageButton)findViewById(R.id.i16);

        tv_turno=findViewById(R.id.textView_TurnoMemorama);
        tv_puntaje_j1=findViewById(R.id.textView_titulo_conecta4);
        tv_puntaje_j2=findViewById(R.id.textView_Puntos_j2);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.i1:
                memorias[0].onClick();
                if(primer_movimiento ==false){ // Si no se ha volteado una tarjeta...
                    id_imagen=0;
                    tarjeta_volteada =memorias[0];
                    primer_movimiento =true;
                }
                else { // Si ya la habia volteado
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[0].getIdimagen()!= tarjeta_volteada.getIdimagen()){ // Si la tarjeta 0 no tiene el mimsmo id de imagen
                        ImageButton b=(ImageButton)findViewById(R.id.i1);
                        b.setImageResource(R.drawable.questionmark); // la tarjeta recien volteada vuelve a estar boca abajo
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark); // la tarjeta antes volteada vuleve a estar boca abajo

                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i2:
                memorias[1].onClick();
                if(primer_movimiento ==false){
                    id_imagen=1;
                    tarjeta_volteada =memorias[1];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[1].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i2);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i3:
                memorias[2].onClick();
                if(primer_movimiento ==false){
                    id_imagen=2;
                    tarjeta_volteada =memorias[2];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[2].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i3);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i4:
                memorias[3].onClick();
                if(primer_movimiento ==false){
                    id_imagen=3;
                    tarjeta_volteada =memorias[3];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[3].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i4);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i5:
                memorias[4].onClick();
                if(primer_movimiento ==false){
                    id_imagen=4;
                    tarjeta_volteada =memorias[4];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[4].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i5);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i6:
                memorias[5].onClick();
                if(primer_movimiento ==false){
                    id_imagen=5;
                    tarjeta_volteada =memorias[5];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[5].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i6);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i7:
                memorias[6].onClick();
                if(primer_movimiento ==false){
                    id_imagen=6;
                    tarjeta_volteada =memorias[6];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[6].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i7);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i8:
                memorias[7].onClick();
                if(primer_movimiento ==false){
                    id_imagen=7;
                    tarjeta_volteada =memorias[7];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[7].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i8);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i9:
                memorias[8].onClick();
                if(primer_movimiento ==false){
                    id_imagen=8;
                    tarjeta_volteada =memorias[8];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[8].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i9);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i10:
                memorias[9].onClick();
                if(primer_movimiento ==false){
                    id_imagen=9;
                    tarjeta_volteada =memorias[9];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[9].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i10);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i11:
                memorias[10].onClick();
                if(primer_movimiento ==false){
                    id_imagen=10;
                    tarjeta_volteada =memorias[10];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[10].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i11);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i12:
                memorias[11].onClick();
                if(primer_movimiento ==false){
                    id_imagen=11;
                    tarjeta_volteada =memorias[11];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[11].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i12);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i13:
                memorias[12].onClick();
                if(primer_movimiento ==false){
                    id_imagen=12;
                    tarjeta_volteada =memorias[12];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[12].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i13);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i14:
                memorias[13].onClick();
                if(primer_movimiento ==false){
                    id_imagen=13;
                    tarjeta_volteada =memorias[13];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[13].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i14);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i15:
                memorias[14].onClick();
                if(primer_movimiento ==false){
                    id_imagen=14;
                    tarjeta_volteada =memorias[14];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){ }

                    if(memorias[14].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i15);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
            case R.id.i16:
                memorias[15].onClick();
                if(primer_movimiento ==false){
                    id_imagen=15;
                    tarjeta_volteada =memorias[15];
                    primer_movimiento =true;
                }
                else {
                    primer_movimiento =false;

                    if(memorias[15].getIdimagen()!= tarjeta_volteada.getIdimagen()){
                        ImageButton b=(ImageButton)findViewById(R.id.i16);
                        b.setImageResource(R.drawable.questionmark);
                        vectorimagen[id_imagen].setImageResource(R.drawable.questionmark);
                    }
                    else{
                        cambiarPuntaje();
                    }
                    cambiarTurno();
                }
                break;
        }
        if((puntaje_j1+puntaje_j2)==8){
            tv_turno.setText("Fin del juego!");
            if(puntaje_j1>puntaje_j2)
                Toast.makeText(this,jugador1+" ha ganado!",Toast.LENGTH_LONG).show();
            else if(puntaje_j2>puntaje_j1)
                Toast.makeText(this,jugador2+" ha ganado!",Toast.LENGTH_LONG).show();
            else if(puntaje_j1==puntaje_j2)
                Toast.makeText(this,"Empate!",Toast.LENGTH_LONG).show();

            // Juego 1: Ahorcado
            // Juego 2: Conecta4
            // Juego 3: Gato
            // Juego 4: Memorama
            (new Transaccion(this)).registrarPuntuacion(this, jugador1,"0","0","0",String.valueOf(puntaje_j1));
            (new Transaccion(this)).registrarPuntuacion(this, jugador2,"0","0","0",String.valueOf(puntaje_j2));
        }
    }

    private void cambiarTurno(){
        if(turno==1){
            turno=2;
            tv_turno.setText("Turno de "+jugador2);
        }else{
            turno=1;
            tv_turno.setText("Turno de "+jugador1);
        }
    }

    private void cambiarPuntaje(){
        if(turno==1){
            puntaje_j1++;
            tv_puntaje_j1.setText(jugador1+": "+puntaje_j1);
        }else{
            puntaje_j2++;
            tv_puntaje_j2.setText(jugador2+": "+puntaje_j2);
        }
    }

    public void regresarAlMenu(View view){
        Intent i = new Intent(this, MenuPrincipal.class);
        i.putExtra("dato_nickname",jugador1);
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Juego 1: Ahorcado
        // Juego 2: Conecta4
        // Juego 3: Gato
        // Juego 4: Memorama
        (new Transaccion(this)).registrarPuntuacion(this, jugador1,"0","0","0",String.valueOf(puntaje_j1));
        (new Transaccion(this)).registrarPuntuacion(this, jugador2,"0","0","0",String.valueOf(puntaje_j2));

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
        notificacion.setContentText("Continúa jugando Memorama :)");
        notificacion.setWhen(System.currentTimeMillis());
        Intent i = new Intent(this, Login.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(pi);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(idUnica,notificacion.build());
    }
}
