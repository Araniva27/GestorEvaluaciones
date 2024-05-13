package com.dsm.gestorevaluacionesdsm.Modelos;

public class Respuestas {
    private int idPregunta;
    private int idRespuesta;

    public Respuestas(int idPregunta, int idRespuesta) {
        this.idPregunta = idPregunta;
        this.idRespuesta = idRespuesta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }
}
