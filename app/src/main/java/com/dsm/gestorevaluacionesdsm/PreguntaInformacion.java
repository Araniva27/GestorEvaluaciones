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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DAOs.OpcionesDAO;
import com.dsm.gestorevaluacionesdsm.DAOs.PreguntaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PreguntaInformacion extends AppCompatActivity {

    EditText txtIdPregunta, txtIdEvaluacion;
    TextView lblNombreEvaluacion;
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

        PreguntaDAO pDao = new PreguntaDAO(PreguntaInformacion.this);
        String nombre = pDao.obtenerNombreEvaluacion(idEvaluacion);
        lblNombreEvaluacion.setText(nombre);

        FloatingActionButton fabAgregarOpcion = findViewById(R.id.btnAgregarOpcion);

        fabAgregarOpcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoAgregarOpcion(PreguntaInformacion.this, idPregunta);
            }
        });
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
                        Toast.makeText(PreguntaInformacion.this, "Opción agregada correctamente", Toast.LENGTH_SHORT).show();
                    }
                }else{

                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}