package com.dsm.gestorevaluacionesdsm.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.R;

import java.util.List;

public class AdaptadorEvaluacionesUsuario extends RecyclerView.Adapter<AdaptadorEvaluacionesUsuario.ViewHolder> {
    private List<Evaluacion> listaEvaluaciones;
    private Context context;
    private OnItemClickListener listener;
    //private EvaluacionDAO edao;

    public AdaptadorEvaluacionesUsuario(Context context, List<Evaluacion> listaEvaluaciones, OnItemClickListener listener) {
        this.context = context;
        this.listaEvaluaciones = listaEvaluaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_evaluaciones_usuarios, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Evaluacion evaluacion = listaEvaluaciones.get(position);
        holder.bind(evaluacion);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(evaluacion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEvaluaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblTituloEvaluacion;
        private TextView txtCantidadPreguntas;
        private EditText txtIdEvaluacion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblTituloEvaluacion = itemView.findViewById(R.id.lblTituloEvaluacion);
            txtCantidadPreguntas = itemView.findViewById(R.id.txtCantidadPreguntas);
            txtIdEvaluacion = itemView.findViewById(R.id.txtIdEvaluacion);
        }

        public void bind(Evaluacion evaluacion) {
            lblTituloEvaluacion.setText(evaluacion.getNombreEvaluacion());
            txtCantidadPreguntas.setText("Cuestionario");
            txtIdEvaluacion.setText(""+evaluacion.getIdEvaluacion());
        }


    }

    public interface OnItemClickListener {
        void onItemClick(Evaluacion evaluacion);
    }
}
