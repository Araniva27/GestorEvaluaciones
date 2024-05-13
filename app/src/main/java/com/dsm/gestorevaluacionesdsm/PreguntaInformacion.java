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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorOpciones;
import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorPreguntas;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.OpcionesDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.PreguntaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PreguntaInformacion extends AppCompatActivity implements AdaptadorOpciones.OnItemClickListener {

    EditText txtIdPregunta, txtIdEvaluacion;
    TextView lblNombreEvaluacion, lblPregunta, txtPreguntaActualizar, txtValoracionActualizar;
    RecyclerView recyclerView;
    OpcionesDAO opcionesDAO = new OpcionesDAO(PreguntaInformacion.this);
    PreguntaDAO preguntasDAO = new PreguntaDAO(PreguntaInformacion.this);
    AdaptadorOpciones adaptador;
    ImageView btnRegresar, btnEliminar, btnModificar;
    int idEva = 0;
    int idPre = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregunta_informacion);

        txtIdPregunta = findViewById(R.id.txtIdPregunta);
        txtIdEvaluacion = findViewById(R.id.txtIdEvaluacion);
        lblNombreEvaluacion = findViewById(R.id.lblTituloPreguntaDetalle);

        Intent intent = getIntent();
        int idPregunta = intent.getIntExtra("idPregunta",-1);
        int idEvaluacion = intent.getIntExtra("idEvaluacion",-1);
        txtIdPregunta.setText(""+idPregunta);
        txtIdEvaluacion.setText(""+idEvaluacion);
        lblPregunta = findViewById(R.id.lblPregunta);
        recyclerView = findViewById(R.id.recyclerViewOpciones);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PreguntaDAO pDao = new PreguntaDAO(PreguntaInformacion.this);
        String nombre = pDao.obtenerNombreEvaluacion(idEvaluacion);
        lblNombreEvaluacion.setText(nombre);

        FloatingActionButton fabAgregarOpcion = findViewById(R.id.btnAgregarOpcion);
        idEva = idEvaluacion;
        fabAgregarOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarOpcion(PreguntaInformacion.this, idPregunta
                );
            }
        });

        mostrarOpciones(idPregunta);

        String pregunta = pDao.obtenerPregunta(idPregunta);
        lblPregunta.setText(""+pregunta);
        btnRegresar = findViewById(R.id.btnRegresar);
        btnEliminar = findViewById(R.id.btnEliminarPregunta);
        btnModificar = findViewById(R.id.btnModificar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreguntaInformacion.this, EvaluacionInformacion.class);
                intent.putExtra("idEvaluacion", idEvaluacion);
                startActivity(intent);
            }
        });


        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacionEliminacion(PreguntaInformacion.this, idPregunta, idEvaluacion);
            }
        });
        idPre = idPregunta;
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoModificarPregunta(PreguntaInformacion.this, idPregunta, idEvaluacion);
            }
        });
    }

    @Override
    public void onItemClick(Opciones opcion) {
        // Manejar el clic en el elemento
        //Toast.makeText(this, "Clic en: " + opcion.getIdOpcion(), Toast.LENGTH_SHORT).show();
        mostrarDialogActualizarOpcion(PreguntaInformacion.this, opcion.getIdOpcion());
        /*Intent intent = new Intent(EvaluacionInformacion.this, PreguntaInformacion.class);
        intent.putExtra("idPregunta",pregunta.getIdPregunta());
        intent.putExtra("idEvaluacion",Integer.parseInt(txtIdEvaluacion.getText().toString()));
        startActivity(intent);*/
    }
    private void mostrarDialogoAgregarOpcion(Context context, int idPregunta) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Agregar Opción");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_agregar_opcion, null);
        final EditText inputOpcion = viewInflated.findViewById(R.id.txtOpcion);
        final RadioButton opcionCorrecta = viewInflated.findViewById(R.id.rdbOpcionCorrecta);
        final EditText inputIdPregunta = viewInflated.findViewById(R.id.txtIdPregunta);
        inputIdPregunta.setText("" + idPregunta);
        builder.setView(viewInflated);

        // Configurar los botones del diálogo
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String opcion = inputOpcion.getText().toString();
                int opcionCorrectaValue = opcionCorrecta.isChecked() ? 1 : 0;
                int idPregunta = Integer.parseInt(inputIdPregunta.getText().toString());
                Opciones opcionesObj = new Opciones();

                // Validar que los campos no estén vacíos
                if (!TextUtils.isEmpty(opcion)) {
                    opcionesObj.setOpcion(opcion);
                    opcionesObj.setEsCorrecta(opcionCorrectaValue);
                    opcionesObj.setIdPregunta(idPregunta);

                    OpcionesDAO pDao = new OpcionesDAO(PreguntaInformacion.this);

                    long result = pDao.insertarOpcion(opcionesObj);
                    if (result == -2) {
                        Toast.makeText(PreguntaInformacion.this, "Ya existe una opción marcada como correcta para esta pregunta", Toast.LENGTH_SHORT).show();
                    } else if (result == -3) {
                        Toast.makeText(PreguntaInformacion.this, "Esta pregunta ya tiene 3 opciones", Toast.LENGTH_SHORT).show();
                    } else if (result != -1) {
                        mostrarOpciones(idPregunta);
                        Toast.makeText(PreguntaInformacion.this, "Opción agregada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
        });
        //Comentario de prueba
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void mostrarOpciones(int idPregunta) {
        List<Opciones> opciones = opcionesDAO.obtenerOpcionesPregunta(idPregunta);
        adaptador = new AdaptadorOpciones(opciones,this, this);
        recyclerView.setAdapter(adaptador);
    }

    private void mostrarDialogoConfirmacionEliminacion(Context context, int idPregunta, int idEvaluacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("IMPORTANTE");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_confirmacion_borrar_pregunta, null);
        builder.setView(viewInflated);

        // Configurar los botones del diálogo
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EvaluacionDAO eDao = new EvaluacionDAO(PreguntaInformacion.this);
                if(preguntasDAO.eliminarPregunta(idPregunta) != -1){
                    //startActivity(new Intent(PreguntaInformacion.this, EvaluacionesCreadas.class));
                    Intent intent = new Intent(PreguntaInformacion.this, EvaluacionInformacion.class);
                    intent.putExtra("idEvaluacion", idEvaluacion);
                    startActivity(intent);
                    Toast.makeText(context, "La pregunta ha sido eliminada correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(context, "Ha ocurrido un error al eliminar la pregunta", Toast.LENGTH_SHORT).show();
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

    private void mostrarDialogoModificarPregunta(Context context, int idPregunta, int idEvaluacion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("IMPORTANTE");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_modificar_pregunta, null);
        builder.setView(viewInflated);

        txtPreguntaActualizar = viewInflated.findViewById(R.id.txtPreguntaActualizar);
        txtValoracionActualizar = viewInflated.findViewById(R.id.txtValoracionActualizar);
        txtIdPregunta = viewInflated.findViewById(R.id.txtIdPregunta);
        PreguntaDAO preguntaDao = new PreguntaDAO(PreguntaInformacion.this);
        txtPreguntaActualizar.setText(preguntaDao.obtenerPregunta(idPregunta));
        txtValoracionActualizar.setText(preguntaDao.obtenerValoracion(idPregunta).toString());
        // Configurar los botones del diálogo
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String pregunta = txtPreguntaActualizar.getText().toString();
                //int idPreguntaActualiz = Integer.parseInt(txtIdPregunta.getText().toString());
                float valoracion = Float.parseFloat(txtValoracionActualizar.getText().toString());

                if (TextUtils.isEmpty(pregunta)) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(valoracion <= preguntaDao.puntajeDisponible(idEvaluacion)){
                    Pregunta preguntaObj = new Pregunta();
                    preguntaObj.setPregunta(pregunta);
                    preguntaObj.setValoracion(valoracion);

                    if(preguntasDAO.modificarPregunta(preguntaObj, idPregunta) != -1){
                        lblPregunta.setText(""+preguntaDao.obtenerPregunta(idPregunta));
                        Toast.makeText(context, "La pregunta ha sido modificada correctamente", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Ha ocurrido un error al modificar la pregunta", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "La valoracion supera el valor permitido", Toast.LENGTH_SHORT).show();
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

    private void mostrarDialogActualizarOpcion(Context context, int idOpcion){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("IMPORTANTE");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_modificar_opcion, null);
        builder.setView(viewInflated);

        EditText txtOpcion= viewInflated.findViewById(R.id.txtOpcionActualizar);
        CheckBox chbEsCorrecta = viewInflated.findViewById(R.id.chbRespuestaCorrecta);
        EditText txtIdOpcion = viewInflated.findViewById(R.id.txtIdOpcion);
        OpcionesDAO opcionesDao = new OpcionesDAO(PreguntaInformacion.this);
        txtOpcion.setText(""+opcionesDao.obtenerOpcion(idOpcion));
        int esCorrecta = opcionesDao.obtenerCorrecta(idOpcion);
        chbEsCorrecta.setChecked(esCorrecta == 1);
        // Configurar los botones del diálogo
        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String opcion = txtOpcion.getText().toString();
                //int idPreguntaActualiz = Integer.parseInt(txtIdPregunta.getText().toString());
                int esCorrecta = chbEsCorrecta.isChecked() ? 1 : 0;

                if (TextUtils.isEmpty(opcion)) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                Opciones opcionesObje = new Opciones();


                opcionesObje.setOpcion(opcion);
                opcionesObje.setEsCorrecta(esCorrecta);

                if(opcionesDAO.modificarOpcion(opcionesObje, idOpcion, idPre) != -1){
                    if(opcionesDAO.modificarOpcion(opcionesObje, idOpcion, idPre) != -2){
                        Toast.makeText(context, "Ya existe una opcion correcta", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(context, "Se ha actualizado la opcion correctamente", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(context, "Ha ocurrido un error al actualizar la opcion", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNeutralButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                mostrarDialogoConfirmacionEliminacionOpcion(PreguntaInformacion.this, idOpcion);
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

    private void mostrarDialogoConfirmacionEliminacionOpcion(Context context, int idOpcion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("IMPORTANTE");

        // Inflar el layout personalizado para el diálogo
        View viewInflated = LayoutInflater.from(context).inflate(R.layout.dialog_eliminar_opcion, null);
        builder.setView(viewInflated);

        // Configurar los botones del diálogo
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                OpcionesDAO oDao = new OpcionesDAO(PreguntaInformacion.this);
                if(oDao.eliminarOpcion(idOpcion) != -1){
                    //startActivity(new Intent(EvaluacionInformacion.this, EvaluacionesCreadas.class));
                    Toast.makeText(context, "La opcion ha sido eliminada correctamente", Toast.LENGTH_SHORT).show();
                    mostrarOpciones(idEva);

                }else{
                    Toast.makeText(context, "Ha ocurrido un error al eliminar la opcion", Toast.LENGTH_SHORT).show();
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