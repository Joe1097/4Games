package com.example.pantallainicio.bd;

public class Jugador {

    private String Nickname; //nickname

    private Integer MaPuntajeJ1; //mayor puntaje juego 1

    private Integer MaPuntajeJ2; //mayor puntaje juego 2

    private Integer MaPuntajeJ3; //mayor puntaje juego 3

    private Integer MaPuntajeJ4; //mayor puntaje juego 4


    public Jugador(String nickname, Integer maPuntajeJ1, Integer maPuntajeJ2, Integer maPuntajeJ3, Integer maPuntajeJ4) {
        Nickname = nickname;
        MaPuntajeJ1 = maPuntajeJ1;
        MaPuntajeJ2 = maPuntajeJ2;
        MaPuntajeJ3 = maPuntajeJ3;
        MaPuntajeJ4 = maPuntajeJ4;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public Integer getMaPuntajeJ1() {
        return MaPuntajeJ1;
    }

    public void setMaPuntajeJ1(Integer maPuntajeJ1) {
        MaPuntajeJ1 = maPuntajeJ1;
    }

    public Integer getMaPuntajeJ2() {
        return MaPuntajeJ2;
    }

    public void setMaPuntajeJ2(Integer maPuntajeJ2) {
        MaPuntajeJ2 = maPuntajeJ2;
    }

    public Integer getMaPuntajeJ3() {
        return MaPuntajeJ3;
    }

    public void setMaPuntajeJ3(Integer maPuntajeJ3) {
        MaPuntajeJ3 = maPuntajeJ3;
    }

    public Integer getMaPuntajeJ4() {
        return MaPuntajeJ4;
    }

    public void setMaPuntajeJ4(Integer maPuntajeJ4) {
        MaPuntajeJ4 = maPuntajeJ4;
    }
}