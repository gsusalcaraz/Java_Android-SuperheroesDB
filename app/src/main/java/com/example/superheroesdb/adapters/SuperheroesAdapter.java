package com.example.superheroesdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.superheroesdb.models.Superheroe;
import com.example.superheroesdb.R;
import java.util.List;

public class SuperheroesAdapter extends RecyclerView.Adapter<SuperheroesAdapter.myHolderView>{

    private final List<Superheroe> datos;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Superheroe item);
        void onItemLongClick(Superheroe item, int position);
    }

    public List<Superheroe> getDatos() {
        return datos;
    }

    public SuperheroesAdapter(List<Superheroe> datos, OnItemClickListener listener) {
        this.datos = datos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public myHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.row_superheroes, parent, false);

        return new myHolderView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolderView holder, int position) {

        holder.nombre.setText(datos.get(position).getNombre());
        holder.nombreReal.setText(datos.get(position).getNombreReal());

        if (!datos.get(position).getImagenId().isEmpty()) {
            try {
                holder.imageView.setImageResource(Integer.valueOf(datos.get(position).getImagenId()));
            }
            catch (Exception e){
                holder.imageView.setImageResource(R.drawable.avengers);
            }
        }
        else {
            holder.imageView.setImageResource(R.drawable.avengers);
        }

        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(datos.get(holder.getAdapterPosition()));
            }
        });

        // Gestionar el evento de pulsación larga
        holder.contenedor.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onItemLongClick(datos.get(holder.getAdapterPosition()), holder.getAdapterPosition());
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    static class myHolderView extends RecyclerView.ViewHolder {

        Context context;
        ConstraintLayout contenedor;
        ImageView imageView;
        TextView nombre;
        TextView nombreReal;

        public myHolderView(@NonNull View itemView) {
            super(itemView);
            contenedor = itemView.findViewById(R.id.contenedor);
            imageView = itemView.findViewById(R.id.imageView);
            nombre = itemView.findViewById(R.id.tvNombre);
            nombreReal = itemView.findViewById(R.id.tvNombreReal);
            context = itemView.getContext();
        }
    }
}