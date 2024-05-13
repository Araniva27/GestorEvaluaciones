package com.dsm.gestorevaluacionesdsm;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluacionesUsuario;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.OpcionesDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.PreguntaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.dsm.gestorevaluacionesdsm.Modelos.Respuestas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class Preguntas extends AppCompatActivity implements AdaptadorEvaluacionesUsuario.OnItemClickListener {
    ImageView imgBtn_Atras;
    Button btnSiguiente,btnAtras;
    TextView textTituloEvaluacion, textoPregunta;

    RadioGroup GrupoRespuestas;
    RadioButton rbtn1,rbtn2,rbtn3;

    // Declara el contador como un campo de la clase
    private int contador = 0;

    // Declarar una matriz para almacenar los pares de ID
    private List<Respuestas> respuestasTotal=new ArrayList<>();

    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(Preguntas.this);
    PreguntaDAO preguntaDAO = new PreguntaDAO(Preguntas.this);
    private List<Pregunta> preguntas = new ArrayList<>();

    OpcionesDAO opcionesDAO= new OpcionesDAO(Preguntas.this);
    private List<Opciones> opciones = new ArrayList<>();
    int IdpreguntaSelecionada, IdrespuestaSeleccionada;



    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preguntas);
        Intent intent = getIntent();
        int idEvaluacion = intent.getIntExtra("idEvaluacion", -1);
        String nombreEvaluacion = evaluacionDAO.obtenerDescripcionEvaluacion(idEvaluacion)[0];

        imgBtn_Atras = findViewById(R.id.btn_Atras);
        textTituloEvaluacion = findViewById(R.id.textTituloEvaluacion);
        textTituloEvaluacion.setText(nombreEvaluacion);
        btnSiguiente=findViewById(R.id.btnSiguiente);
        btnAtras=findViewById(R.id.btnAtras);
        btnAtras.setVisibility(View.INVISIBLE);
        textoPregunta = findViewById(R.id.textoPregunta);
        rbtn1=findViewById(R.id.radioButton1);
        rbtn2=findViewById(R.id.radioButton2);
        rbtn3=findViewById(R.id.radioButton3);
        RadioGroup grupoRespuestas = findViewById(R.id.rGrupoRespuestas);

        preguntas=preguntaDAO.obtenerPreguntasEvaluacion(idEvaluacion);

        // Listener para el cambio en la selección de RadioButtons
        grupoRespuestas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Verificar si algún RadioButton está seleccionado
                if (checkedId != -1) {
                    // Encontrar el RadioButton seleccionado
                    RadioButton radioButton = findViewById(checkedId);

                    // Obtener el texto seleccionado del RadioButton
                    int rbtnSelecionado = Integer.parseInt(radioButton.getTag().toString());
                    guardarRespuestas(preguntas.get(contador).getIdPregunta(),rbtnSelecionado);
                } else {
                    // No se ha seleccionado ningún RadioButton
                    // Puedes manejar este caso según sea necesario
                }
            }
        });




        // Verifica si la lista de preguntas no está vacía antes de intentar acceder a la primera pregunta
        if (!preguntas.isEmpty()) {
            // Solo accede a la primera pregunta si la lista no está vacía
            textoPregunta.setText(obtenerpreguntas(preguntas,contador));
        }

        imgBtn_Atras.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed(); // Regresar a la interfaz anterior
            }
        });

        // Listener para el botón "Siguiente"
        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // Verificar si algún RadioButton está seleccionado y deseleccionarlo
                if (grupoRespuestas.getCheckedRadioButtonId() != -1) {
                    //grupoRespuestas.clearCheck();
                    // Incrementa el contador
                    Respuestas res = new Respuestas(IdpreguntaSelecionada,IdrespuestaSeleccionada);
                    respuestasTotal.add(res);

                    contador++;

                    // Si el contador supera el índice máximo de la lista, vuelve al inicio
                    if (contador == (preguntas.size()-1)) {
                        btnSiguiente.setText("Terminar");

                    }
                    if (contador == preguntas.size()) {
                        Intent intent = new Intent(Preguntas.this, CuestionarioCalificado.class);
                        intent.putExtra("idEvaluacion", idEvaluacion);
                        // Convertir el objeto ArrayList<Respuestas> a una cadena JSON
                        JSONArray jsonArray = new JSONArray();
                        for (Respuestas respuesta : respuestasTotal) {
                            JSONObject jsonObject = new JSONObject();
                            try {
                                jsonObject.put("idPregunta", respuesta.getIdPregunta());
                                jsonObject.put("idRespuesta", respuesta.getIdRespuesta());
                                jsonArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        String respuestasJson = jsonArray.toString();
                        intent.putExtra("RespuestaJson", respuestasJson);
                        startActivity(intent);
                    }

                    // Actualiza el texto del TextView con la nueva pregunta obtenida según el contador actualizado
                    textoPregunta.setText(obtenerpreguntas(preguntas, contador));

                }else{
                    Toast.makeText(getApplicationContext(), "Selecione una opcion", Toast.LENGTH_SHORT).show();
                }


            }


        });
    }

    @Override
    public void onItemClick(Evaluacion evaluacion) {

    }

    public String obtenerpreguntas(List<Pregunta> preguntas, int contador){
        int idPregunta=preguntas.get(contador).getIdPregunta();
        obtenerOpciones(idPregunta);
        return preguntas.get(contador).getPregunta();
    }
    public void obtenerOpciones(int idPregunta){
        opciones=opcionesDAO.obtenerOpcionesPregunta(idPregunta);
        rbtn1.setText(opciones.get(0).getOpcion());
        rbtn1.setTag(opciones.get(0).getIdOpcion());
        rbtn2.setText(opciones.get(1).getOpcion());
        rbtn2.setTag(opciones.get(1).getIdOpcion());
        rbtn3.setText(opciones.get(2).getOpcion());
        rbtn3.setTag(opciones.get(2).getIdOpcion());
    }
    public void guardarRespuestas(int idpregunta, int idrespuesta){
        IdpreguntaSelecionada=idpregunta;
        IdrespuestaSeleccionada=idrespuesta;
    }
}
