package com.dsm.gestorevaluacionesdsm.Modelos;

public class Opciones {
    public int idOpcion;
    public String opcion;
    public int esCorrecta;
    public int idPregunta;

    public Opciones() {
    }

    public Opciones(int idOpcion, String opcion, int esCorrecta, int idPregunta) {
        this.idOpcion = idOpcion;
        this.opcion = opcion;
        this.esCorrecta = esCorrecta;
        this.idPregunta = idPregunta;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public String getOpcion() {
        return opcion;
    }

    public void setOpcion(String opcion) {
        this.opcion = opcion;
    }

    public int getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(int esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public int getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(int idPregunta) {
        this.idPregunta = idPregunta;
    }
}
