package com.example.kitaadmin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.kitaadmin.Adapter.AlumnoAdapter;
import com.example.kitaadmin.Adapter.RecylerAdapter;
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
    RecylerAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerAlumnos);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getApplicationContext(), movieList);
        recyclerView.setAdapter(recyclerAdapter);
        

    }

    /**
     * Metodo que obtiene la lista de alumnos
     */
    public void getAlumnos(){
        Call<List<Alumno>> call = alumnoService.getAlumnos();
        call.enqueue(new Callback<List<Alumno>>() {
            @Override
            public void onResponse(Call<List<Alumno>> call, Response<List<Alumno>> response) {
                if(response.isSuccessful()){
                    listaAlumnos = response.body();

                    recyclerView.setAdapter(new AlumnoAdapter(GrupoActivity.this),R.layout.activity_grupo, listaAlumnos);

                }


            }

            @Override
            public void onFailure(Call<List<Alumno>> call, Throwable t) {

            }
        });
    }
}