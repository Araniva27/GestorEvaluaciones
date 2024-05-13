package com.dsm.gestorevaluacionesdsm.Modelos;

import java.util.List;

public class PreguntaConOpciones {
    private Pregunta pregunta;
    private List<Opciones> opciones;

    public PreguntaConOpciones(Pregunta pregunta, List<Opciones> opciones) {
        this.pregunta = pregunta;
        this.opciones = opciones;
    }

    public Pregunta getPregunta() {
        return pregunta;
    }

    public List<Opciones> getOpciones() {
        return opciones;
    }
}
