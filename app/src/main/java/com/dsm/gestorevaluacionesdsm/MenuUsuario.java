package com.dsm.gestorevaluacionesdsm;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        recyclerEvaluaciones = findViewById(R.id.GridEvaluaciones);
        recyclerEvaluaciones.setLayoutManager(new GridLayoutManager(this, 2));

        evaluacionDAO = new EvaluacionDAO(this);
        mostrarEvaluaciones();
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
