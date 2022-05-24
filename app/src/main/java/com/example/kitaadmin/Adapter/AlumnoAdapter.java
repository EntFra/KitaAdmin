package com.example.kitaadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.R;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumnos> {

    private final Context context;
    private final List<Alumnos> alumnos;

    //Obtiene los obetos de tipo alumno
    public AlumnoAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Alumnos> objects) {
        super(context, resource, textViewResourceId, objects);
        this.context = context;
        this.alumnos = objects;
    }

    //Obtiene la vista y cargo los datos
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_lista_alumnos, parent, false);

        TextView txtNombreAlumno = (TextView) view.findViewById(R.id.item_list);

        txtNombreAlumno.setText(alumnos.get(position).getNombre());

        return view;
    }
}
