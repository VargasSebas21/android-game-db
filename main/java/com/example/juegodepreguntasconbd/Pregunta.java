package com.example.juegodepreguntasconbd;

import java.util.ArrayList;

public class Pregunta {

    private String pregunta;
    private ArrayList<String> opciones;
    private Integer indexRespuestaCorrecta;


    public Pregunta(String pregunta, ArrayList<String> opciones, Integer respuestaCorrecta) {
        this.pregunta = pregunta;
        this.opciones = opciones;
        this.indexRespuestaCorrecta = respuestaCorrecta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public ArrayList<String> getOpciones() {return opciones;}

    public void setOpciones(ArrayList<String> opciones) {
        this.opciones = opciones;
    }

    public Integer getRespuestaCorrecta() {
        return indexRespuestaCorrecta;
    }

    public void setRespuestaCorrecta(Integer respuestaCorrecta) {
        this.indexRespuestaCorrecta = respuestaCorrecta;
    }

    public Boolean verificarRespuesta(String respuesta){
        String respuestaCorrecta = opciones.get(this.indexRespuestaCorrecta-1);
        return respuestaCorrecta.equals(respuesta);
    }

    @Override
    public String toString() {
        return pregunta+ "\n"
                + "A) " + opciones.get(0)+ "\n"
                + "B) " + opciones.get(1)+ "\n"
                + "C) " + opciones.get(2)+ "\n"
                + "D) " + opciones.get(3)+ "\n"
                + "Numero opcion correcta -> " + indexRespuestaCorrecta.toString();
    }
}
