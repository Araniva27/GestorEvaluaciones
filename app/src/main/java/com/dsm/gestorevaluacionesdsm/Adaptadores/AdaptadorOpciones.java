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
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.R;

import java.util.List;

public class AdaptadorOpciones extends RecyclerView.Adapter<AdaptadorOpciones.ViewHolder>{
    private List<Opciones> listaOpciones;
    private Context context;
    private AdaptadorOpciones.OnItemClickListener listener;

    public AdaptadorOpciones(List<Opciones> listaOpciones, Context context, AdaptadorOpciones.OnItemClickListener listener) {
        this.listaOpciones = listaOpciones;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdaptadorOpciones.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_opciones, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Opciones opcion = listaOpciones.get(position);
        holder.bind(opcion);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(opcion);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaOpciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblOpcion;
        private TextView txtIdOpcion;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblOpcion = itemView.findViewById(R.id.txtOpcion);
            txtIdOpcion = itemView.findViewById(R.id.lblIdOpcion);
        }

        public void bind(Opciones opcion) {
            lblOpcion.setText(opcion.getOpcion());
            //txtCantidadPreguntas.setText("Cantidad de preguntas: "+EvaluacionDAO.obtenerCantidadPreguntasEvaluacion(evaluacion.getIdEvaluacion()));
            txtIdOpcion.setText(""+opcion.getIdOpcion());
        }


    }

    public interface OnItemClickListener {
        void onItemClick(Opciones opcion);
    }
}
