package com.dsm.gestorevaluacionesdsm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.PreguntaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        FloatingActionButton fabAgregarPregunta = findViewById(R.id.btnAgregarPregunta);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion",-1);
        txtIdEvaluacion.setText(""+idEvaluacion);

        String nombreEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[0];
        String descripcionEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[1];

        lblTitulo.setText(""+nombreEvaluacion);
        lblDescripcion.setText(""+descripcionEvaluacion);

        int cantidadPreguntas = evaluacionDAO.obtenerCantidadPreguntasEvaluacion(idEvaluacion);
        lblCantidadPreguntas.setText(""+cantidadPreguntas+" preguntas");

        fabAgregarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarPregunta(EvaluacionInformacion.this, idEvaluacion);
            }
        });
    }

    private void mostrarDialogoAgregarPregunta(Context context, int idEvaluacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Agregar Pregunta");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_pregunta, null);
        // Obtener la referencia al EditText en el layout personalizado
        //final EditText input = viewInflated.findViewById(R.id.txtNombreEvaluacion);
        final EditText inputPregunta = viewInflated.findViewById(R.id.txtPreguntaEvaluacion);
        final EditText inputPuntaje= viewInflated.findViewById(R.id.txtPuntaje);
        final EditText inputIdPregunta = viewInflated.findViewById(R.id.txtIdEvaluacionDialog);
        inputIdPregunta.setText(""+idEvaluacion);
        builder.setView(viewInflated);

        // Configurar los botones del diálogo
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pregunta = inputPregunta.getText().toString();
                String puntaje = inputPuntaje.getText().toString();
                Pregunta preguntaObj = new Pregunta();

                // Validar que los campos no estén vacíos
                if (!TextUtils.isEmpty(pregunta) && !TextUtils.isEmpty(puntaje)) {
                    preguntaObj.setPregunta(pregunta);
                    preguntaObj.setValoracion(Float.parseFloat(puntaje));
                    preguntaObj.setIdEvaluacion(idEvaluacion);

                    PreguntaDAO pDao = new PreguntaDAO(EvaluacionInformacion.this);
                    if(Float.parseFloat(puntaje) >= 1 && Float.parseFloat(puntaje) <= 10){
                        if(Float.parseFloat(puntaje) <= pDao.puntajeDisponible(idEvaluacion)){
                            if(pDao.insertarPregunta(preguntaObj) != -1){
                                Toast.makeText(context, "Pregunta registrada", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                //mostrarEvaluaciones();
                            }else{
                                Toast.makeText(EvaluacionInformacion.this, "Ha ocurrido un error al registrar la pregunta", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(EvaluacionInformacion.this, "El puntaje que se desea asignar supera al permitido", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(EvaluacionInformacion.this, "El puntaje debe de encontrarse entre 1 y 10", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Mostrar un mensaje de error si algún campo está vacío
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Mostrar el diálogo
        builder.show();
    }
}