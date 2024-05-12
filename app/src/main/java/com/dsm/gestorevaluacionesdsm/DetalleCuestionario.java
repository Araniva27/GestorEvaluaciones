package com.dsm.gestorevaluacionesdsm;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluacionesUsuario;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;

public class DetalleCuestionario extends AppCompatActivity implements AdaptadorEvaluacionesUsuario.OnItemClickListener {
    TextView txtNombreCuestionario, txtDescripcionCuestionario;
    ImageView imgBtnAtras;

    Button btnComenzar;

    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(DetalleCuestionario.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cuestionario);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion", -1);
        String nombreEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[0];
        String descripcionEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[1];
        txtNombreCuestionario = findViewById(R.id.txtNombreCuestionario);
        txtDescripcionCuestionario = findViewById(R.id.txtDescripcionCuestionario);
        imgBtnAtras = findViewById(R.id.btnAtras);
        btnComenzar= findViewById(R.id.btnComenzar);
        txtNombreCuestionario.setText(nombreEvaluacion);
        txtDescripcionCuestionario.setText(descripcionEvaluacion);

        imgBtnAtras.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                onBackPressed(); // Regresar a la interfaz anterior
            }
        });
        btnComenzar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(DetalleCuestionario.this,Preguntas.class );
                intent.putExtra("idEvaluacion",idEvaluacion);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(Evaluacion evaluacion) {

    }
}
