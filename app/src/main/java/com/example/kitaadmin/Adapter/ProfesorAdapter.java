package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.R;

import java.util.List;
/**
 * Adapter del recyclerView de profesores que carga la información de los profesores disponibles en la base de datos
 */
public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ViewHolderProfesores> {

    private final OnProfesorListener onProfesorListener;
    private final LayoutInflater inflater;
    List<Profesores> listaProfesores;


    public ProfesorAdapter(Context context, List<Profesores> listaProfesores, OnProfesorListener onProfesorListener) {
        this.listaProfesores = listaProfesores;
        this.inflater = LayoutInflater.from(context);
        this.onProfesorListener = onProfesorListener;
    }

    @NonNull
    @Override
    public ViewHolderProfesores onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderProfesores(view, onProfesorListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesorAdapter.ViewHolderProfesores holder, int position) {
        if (listaProfesores.get(position).getUsuario() != null) {
            holder.nombreProfesor.setText(listaProfesores.get(position).getUsuario().getNombre());
        }

    }

    @Override
    public int getItemCount() {
        return listaProfesores.size();
    }

    //Maneja el click sobre un objeto
    public interface OnProfesorListener {
        void onProfesorClick(int position);

    }

    public class ViewHolderProfesores extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nombreProfesor;
        OnProfesorListener onProfesorListener;

        public ViewHolderProfesores(@NonNull View itemView, OnProfesorListener onProfesorListener) {
            super(itemView);
            nombreProfesor = itemView.findViewById(R.id.item_list);
            this.onProfesorListener = onProfesorListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onProfesorListener.onProfesorClick(getAdapterPosition());

        }
    }
}
