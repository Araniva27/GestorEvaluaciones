package com.dsm.gestorevaluacionesdsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluaciones;
import com.dsm.gestorevaluacionesdsm.Adaptadores.AdaptadorEvaluacionesUsuario;
import com.dsm.gestorevaluacionesdsm.DAOs.EvaluacionDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;

import java.util.List;

public class MenuUsuario extends AppCompatActivity implements AdaptadorEvaluacionesUsuario.OnItemClickListener {
    private EvaluacionDAO evaluacionDAO;
    private AdaptadorEvaluacionesUsuario adaptadorEvaluaciones;

    private RecyclerView recyclerEvaluaciones;

    Button btnCerrarSesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        recyclerEvaluaciones = findViewById(R.id.GridEvaluaciones);
        recyclerEvaluaciones.setLayoutManager(new GridLayoutManager(this, 2));

        evaluacionDAO = new EvaluacionDAO(this);
        mostrarEvaluaciones();

        btnCerrarSesion=findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MenuUsuario.this, LoginActivity.class);
                startActivity(intent);
            }

        });
    }


    public void onItemClick(Evaluacion evaluacion) {
        // Manejar el clic en el elemento
        //Toast.makeText(this, "Clic en: " + evaluacion.getIdEvaluacion(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MenuUsuario.this, DetalleCuestionario.class);
        intent.putExtra("idEvaluacion",evaluacion.getIdEvaluacion());
       startActivity(intent);
    }
    private void mostrarEvaluaciones() {
        // Obtener la lista de evaluaciones desde la base de datos
        List<Evaluacion> evaluaciones = evaluacionDAO.obtenerTodasLasEvaluaciones();

        // Crear y establecer el adaptador para el RecyclerView
        adaptadorEvaluaciones = new AdaptadorEvaluacionesUsuario(this, evaluaciones, this);
        recyclerEvaluaciones.setAdapter(adaptadorEvaluaciones);
    }

}
