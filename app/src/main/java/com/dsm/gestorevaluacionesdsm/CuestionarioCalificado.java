package com.dsm.gestorevaluacionesdsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.OpcionesDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.PreguntaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.dsm.gestorevaluacionesdsm.Modelos.Respuestas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CuestionarioCalificado extends AppCompatActivity {
    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(CuestionarioCalificado.this);
    PreguntaDAO preguntaDAO = new PreguntaDAO(CuestionarioCalificado.this);
    private List<Pregunta> preguntas = new ArrayList<>();

    OpcionesDAO opcionesDAO= new OpcionesDAO(CuestionarioCalificado.this);
    private List<Opciones> opciones = new ArrayList<>();
    float calificacion=0.0f;
    protected void onCreate(Bundle savedInstanceState) {

        TextView textoNota;
        Button btnSalir;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuestionario_calificado);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion", -1);
        // Recuperar la cadena JSON del Intent
        String respuestasJson = getIntent().getStringExtra("RespuestaJson");

        // Convertir la cadena JSON de vuelta a un objeto ArrayList<Respuestas>
        ArrayList<Respuestas> respuestasTotal = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(respuestasJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int idPregunta = jsonObject.getInt("idPregunta");
                int idRespuesta = jsonObject.getInt("idRespuesta");
                Respuestas respuesta = new Respuestas(idPregunta, idRespuesta);
                respuestasTotal.add(respuesta);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Toast.makeText(getApplicationContext(), "Tamanio: "+respuestasTotal.size(), Toast.LENGTH_SHORT).show();

        preguntas=preguntaDAO.obtenerPreguntasEvaluacion(idEvaluacion);
        for (int i = 0; i < preguntas.size(); i++) {
            int idPregunta=preguntas.get(i).getIdPregunta();
            opciones=opcionesDAO.obtenerOpcionesPregunta(idPregunta);
            for (int j = 0; j < opciones.size(); j++) {

                if(respuestasTotal.get(i).getIdPregunta()==preguntas.get(i).getIdPregunta()){
                    //Toast.makeText(getApplicationContext(), "Si son la misma pregunta", Toast.LENGTH_SHORT).show();

                    if(respuestasTotal.get(i).getIdRespuesta()==opciones.get(j).getIdOpcion()){
                        if(opciones.get(j).getEsCorrecta()==1){
                            float valoracion = (float) preguntas.get(i).getValoracion();
                           // Toast.makeText(getApplicationContext(), "Suma puntos:"+valoracion, Toast.LENGTH_SHORT).show();
                            calificacion+=valoracion;

                        }else{
                            calificacion+=0;
                        }
                    }
                }
            }
        }
        //Toast.makeText(getApplicationContext(), "Calificacion: "+String.valueOf(calificacion), Toast.LENGTH_SHORT).show();
        btnSalir = findViewById(R.id.btnSalir);
        textoNota=findViewById(R.id.textoNota);
        textoNota.setText(String.valueOf(calificacion));
        btnSalir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(CuestionarioCalificado.this, MenuUsuario.class);
                startActivity(intent);
            }

        });

    }
}
