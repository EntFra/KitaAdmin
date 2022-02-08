package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Alumno;
import com.example.kitaadmin.R;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
    private Context context;
    private List<Alumno> alumnoList;

    public RecyclerAdapter(Context context, List<Alumno> alumnoList) {
        this.context = context;
        this.alumnoList = alumnoList;
    }

    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_lista_alumnos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        holder.textViewNombreAlumno.setText(alumnoList.get(position).getNombre());
    }

    @Override
    public int getItemCount() {

        return alumnoList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombreAlumno;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombreAlumno = (TextView) itemView.findViewById(R.id.item_list);
        }
    }
}