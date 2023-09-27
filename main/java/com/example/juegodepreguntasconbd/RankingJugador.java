package com.example.juegodepreguntasconbd;

public class RankingJugador {
    private String nickName;
    private Integer puntaje;

    public RankingJugador(String nickName, Integer puntaje) {
        this.nickName = nickName;
        this.puntaje = puntaje;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    @Override
    public String toString() {
        return "nickName: '" + nickName + "\n" + "puntaje: " + puntaje;
    }
}
