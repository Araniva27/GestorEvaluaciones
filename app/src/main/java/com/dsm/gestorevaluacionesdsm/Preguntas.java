package com.dsm.gestorevaluacionesdsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluacionesUsuario;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;

public class Preguntas extends AppCompatActivity implements AdaptadorEvaluacionesUsuario.OnItemClickListener {
    ImageView imgBtn_Atras;
    TextView textTituloEvaluacion;
    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(Preguntas.this);
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion", -1);
        String nombreEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[0];
        imgBtn_Atras = findViewById(R.id.btn_Atras);
        textTituloEvaluacion = findViewById(R.id.textTituloEvaluacion);
        textTituloEvaluacion.setText(nombreEvaluacion);
        imgBtn_Atras.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onBackPressed(); // Regresar a la interfaz anterior
            }
        });
    }



    @Override
    public void onItemClick(Evaluacion evaluacion) {

    }
}
