package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter del recyclerView de alumnos que llena la lista con los alumnos disponibles en la base de datos
 */

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.ViewHolderAlumnos> {

    private final OnAlumnoListener onAlumnoListener;
    List<Alumnos> listaAlumnos = new ArrayList<>();


    public AlumnoAdapter(Context context, List<Alumnos> listaAlumnos, AlumnoAdapter.OnAlumnoListener onAlumnoListener) {
        this.listaAlumnos = listaAlumnos;
        LayoutInflater inflater = LayoutInflater.from(context);
        this.onAlumnoListener = onAlumnoListener;
    }

    @NonNull
    @Override
    public ViewHolderAlumnos onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, null, false);
        return new ViewHolderAlumnos(view, onAlumnoListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoAdapter.ViewHolderAlumnos holder, int position) {
        if (listaAlumnos.get(position).getNombre() != null) {
            holder.nombreAlumno.setText(listaAlumnos.get(position).getNombre());
        }

    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public interface OnAlumnoListener {
        void onAlumnoClick(int position);
    }

    protected class ViewHolderAlumnos extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nombreAlumno;
        OnAlumnoListener onAlumnoListener;

        public ViewHolderAlumnos(@NonNull View itemView, OnAlumnoListener onAlumnoListener) {
            super(itemView);
            nombreAlumno = itemView.findViewById(R.id.item_list);
            this.onAlumnoListener = onAlumnoListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAlumnoListener.onAlumnoClick(getAdapterPosition());
        }

    }
}

