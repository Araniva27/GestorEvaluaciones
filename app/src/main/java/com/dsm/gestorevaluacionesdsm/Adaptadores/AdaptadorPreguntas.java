package com.dsm.gestorevaluacionesdsm.Adaptadores;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.dsm.gestorevaluacionesdsm.R;
import java.util.List;

public class AdaptadorPreguntas extends RecyclerView.Adapter<AdaptadorPreguntas.ViewHolder> {

    private List<Pregunta> listaPreguntas;
    private Context context;
    private OnItemClickListener listener;

    public AdaptadorPreguntas(List<Pregunta> listaPreguntas, Context context, OnItemClickListener listener) {
        this.listaPreguntas = listaPreguntas;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_pregunta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pregunta pregunta = listaPreguntas.get(position);
        holder.bind(pregunta, position + 1);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(pregunta);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView lblPregunta;
        private TextView lblIndice, lblIdPregunta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lblPregunta = itemView.findViewById(R.id.txtPregunta);
            lblIndice = itemView.findViewById(R.id.txtIndicePregunta);
            lblIdPregunta = itemView.findViewById(R.id.lblIdPregunta);
        }

        public void bind(Pregunta pregunta, int indice) {
            lblPregunta.setText(pregunta.getPregunta());
            lblIndice.setText(String.valueOf(indice));
            lblIdPregunta.setText(""+pregunta.getIdPregunta());
        }
    }

    @Override
    public int getItemCount() {
        return listaPreguntas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Pregunta pregunta);
    }
}
