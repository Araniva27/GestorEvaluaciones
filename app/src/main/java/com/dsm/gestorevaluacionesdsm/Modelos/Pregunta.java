package com.dsm.gestorevaluacionesdsm.Modelos;

public class Pregunta {
    private String idPregunta;
    private String pregunta;
    private float valoracion;
    private int idEvaluacion;

    public Pregunta(String idPregunta, String pregunta, float valoracion, int idEvaluacion) {
        this.idPregunta = idPregunta;
        this.pregunta = pregunta;
        this.valoracion = valoracion;
        this.idEvaluacion = idEvaluacion;
    }

    public Pregunta() {
    }

    public String getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(String idPregunta) {
        this.idPregunta = idPregunta;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public float getValoracion() {
        return valoracion;
    }

    public void setValoracion(float valoracion) {
        this.valoracion = valoracion;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }
}
