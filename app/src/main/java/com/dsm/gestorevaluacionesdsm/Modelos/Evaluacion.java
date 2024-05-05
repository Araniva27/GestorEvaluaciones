package com.dsm.gestorevaluacionesdsm.Modelos;

public class Evaluacion {
    private int idEvaluacion;
    private String nombreEvaluacion;
    private String descripcionEvaluacion;
    private String img =  null;

    public Evaluacion(int idEvaluacion, String nombreEvaluacion, String descripcionEvaluacion, String img) {
        this.idEvaluacion = idEvaluacion;
        this.nombreEvaluacion = nombreEvaluacion;
        this.descripcionEvaluacion = descripcionEvaluacion;
        this.img = img;
    }

    public Evaluacion() {
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public String getNombreEvaluacion() {
        return nombreEvaluacion;
    }

    public void setNombreEvaluacion(String nombreEvaluacion) {
        this.nombreEvaluacion = nombreEvaluacion;
    }

    public String getDescripcionEvaluacion() {
        return descripcionEvaluacion;
    }

    public void setDescripcionEvaluacion(String descripcionEvaluacion) {
        this.descripcionEvaluacion = descripcionEvaluacion;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
