package com.dsm.gestorevaluacionesdsm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluaciones;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class EvaluacionesCreadas extends AppCompatActivity implements AdaptadorEvaluaciones.OnItemClickListener {
    //private RecyclerView recyclerViewEvaluaciones;
    private LinearLayout layoutEvaluaciones;
    RecyclerView recyclerView;
    private EvaluacionDAO evaluacionDAO;
    private AdaptadorEvaluaciones adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluaciones_creadas);
        //layoutEvaluaciones = findViewById(R.id.layoutEvaluaciones);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        evaluacionDAO = new EvaluacionDAO(this);


        FloatingActionButton fabAgregarEvaluacion = findViewById(R.id.fabAgregarEvaluacion);
        fabAgregarEvaluacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarEvaluacion(EvaluacionesCreadas.this);
            }
        });

        mostrarEvaluaciones();


    }
    @Override
    public void onItemClick(Evaluacion evaluacion) {
        // Manejar el clic en el elemento
        Toast.makeText(this, "Clic en: " + evaluacion.getIdEvaluacion(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(EvaluacionesCreadas.this, EvaluacionInformacion.class);
        intent.putExtra("idEvaluacion",evaluacion.getIdEvaluacion());
        startActivity(intent);
    }

    private void mostrarDialogoAgregarEvaluacion(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Agregar Evaluación");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_evaluacion, null);
        // Obtener la referencia al EditText en el layout personalizado
        //final EditText input = viewInflated.findViewById(R.id.txtNombreEvaluacion);
        final EditText inputNombre = viewInflated.findViewById(R.id.txtNombreEvaluacion);
        final EditText inputDescripcion = viewInflated.findViewById(R.id.txtPreguntaEvaluacion);
        builder.setView(viewInflated);

        // Configurar los botones del diálogo
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombreEvaluacion = inputNombre.getText().toString();
                String descripcionEvaluacion = inputDescripcion.getText().toString();
                Evaluacion evaluacion = new Evaluacion();

                // Validar que los campos no estén vacíos
                if (!TextUtils.isEmpty(nombreEvaluacion) && !TextUtils.isEmpty(descripcionEvaluacion)) {
                    evaluacion.setNombreEvaluacion(nombreEvaluacion);
                    evaluacion.setDescripcionEvaluacion(descripcionEvaluacion);
                    evaluacion.setImg("Sin imagen");


                    EvaluacionDAO eDao = new EvaluacionDAO(EvaluacionesCreadas.this);
                    if(eDao.insertarEvaluacion(evaluacion) != -1){
                        Toast.makeText(context, "Evaluacion registrada", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        mostrarEvaluaciones();
                    }else{
                        Toast.makeText(EvaluacionesCreadas.this, "Ha ocurrido un error al registrar el usuario", Toast.LENGTH_SHORT).show();
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


    private void mostrarEvaluaciones() {
        List<Evaluacion> evaluaciones = evaluacionDAO.obtenerTodasLasEvaluaciones();
        adaptador = new AdaptadorEvaluaciones(this, evaluaciones,this);
        recyclerView.setAdapter(adaptador);
    }


}