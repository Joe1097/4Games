package com.example.pantallainicio.bd;

public class Utilidades {
    //constantes de los campos de la tabla jugador
    public static final String TABLA_JUGADOR="jugador";
    public static final String Campo_Nickname="Nickname";
    public static final String MaPuntajeJ1="MaPuntajeJ1";
    public static final String MaPuntajeJ2="MaPuntajeJ2";
    public static final String MaPuntajeJ3="MaPuntajeJ3";
    public static final String MaPuntajeJ4="MaPuntajeJ4";

    public static final String CREAR_TABLA_JUGADOR="CREATE TABLE "+TABLA_JUGADOR+"("+Campo_Nickname+" TEXT, "+MaPuntajeJ1+" INTEGER, "+MaPuntajeJ2+" INTEGER, "+MaPuntajeJ3+" INTEGER, "+MaPuntajeJ4+" INTEGER)";
}
