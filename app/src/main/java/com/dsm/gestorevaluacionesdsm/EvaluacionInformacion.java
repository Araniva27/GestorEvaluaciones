package com.dsm.gestorevaluacionesdsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;

public class EvaluacionInformacion extends AppCompatActivity {

    EditText txtIdEvaluacion;
    TextView lblDescripcion, lblTitulo, lblCantidadPreguntas;
    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(EvaluacionInformacion.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluacion_informacion);

        txtIdEvaluacion = findViewById(R.id.txtIdEvaluacionDetalle);
        lblDescripcion = findViewById(R.id.lblPregunta);
        lblTitulo = findViewById(R.id.lblTituloEvaluacionDetalle);
        lblCantidadPreguntas = findViewById(R.id.lblCantidadPreguntasDetalle);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion",-1);
        txtIdEvaluacion.setText(""+idEvaluacion);

        String nombreEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[0];
        String descripcionEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[1];

        lblTitulo.setText(""+nombreEvaluacion);
        lblDescripcion.setText(""+descripcionEvaluacion);

        int cantidadPreguntas = evaluacionDAO.obtenerCantidadPreguntasEvaluacion(idEvaluacion);
        lblCantidadPreguntas.setText(""+cantidadPreguntas+" preguntas");


    }
}