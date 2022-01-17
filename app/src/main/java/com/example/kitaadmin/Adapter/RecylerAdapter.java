package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Model.Alumno;
import com.example.kitaadmin.R;

import java.util.List;

public class RecylerAdapter extends RecyclerView.Adapter<RecylerAdapter.MyViewHolder>{
    private Context context;
    private List<Alumno> alumnoList;

    public RecylerAdapter(Context context, List<Alumno> alumnoList) {
        this.context = context;
        this.alumnoList = alumnoList;
    }

    @Override
    public RecylerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_lista_alumnos,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecylerAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
