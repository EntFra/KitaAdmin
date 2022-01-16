package com.example.kitaadmin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.kitaadmin.Adapter.AlumnoAdapter;
import com.example.kitaadmin.Model.Alumno;
import com.example.kitaadmin.Utils.AlumnoService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoActivity extends AppCompatActivity {

    AlumnoService alumnoService;
    List<Alumno> listaAlumnos = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
    }

    /**
     * Metodo que obtiene la lista de alumnos
     */
    public void getAlumnos(){
        Call<List<Alumno>> call = alumnoService.getAlumnos();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                listaAlumnos = response.body();
                recyclerView.setAdapter(new AlumnoAdapter(GrupoActivity.this),R.layout.activity_grupo, listaAlumnos);
            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }
}