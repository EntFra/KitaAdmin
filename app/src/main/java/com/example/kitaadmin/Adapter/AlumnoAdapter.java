package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kitaadmin.Model.Alumno;
import com.example.kitaadmin.R;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {

    private final Context context;
    private final List<Alumno> alumnos;

    //Obtiene los obetos de tipo alumno
    public AlumnoAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Alumno> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.alumnos = objects;
    }

    //Obtiene la vista
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_alumno)

    }
}
