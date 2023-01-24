package com.example.kitaadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.AlumnoAdapter;
import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityListaAlumnosBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity lista alumnos, muestra el listado de alumnos del grupo seleccionado
 */
public class ListaAlumnosActivity extends AppCompatActivity implements AlumnoAdapter.OnAlumnoListener {

    List<Alumnos> listaAlumnos = new ArrayList<>();
    RecyclerView recyclerViewAlumnos;
    AlumnoAdapter alumnoAdapter;
    static ApiService apiService = Network.getInstance().create(ApiService.class);;
    String grupo;
    ActivityListaAlumnosBinding binding;
    String rol = LoginActivity.getRol();
    static Alumnos alumno = new Alumnos();
    static Padres padre = MenuActivity.getPadre();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alumnos);
        binding = ActivityListaAlumnosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerViewAlumnos = findViewById(R.id.recyclerAlumnos);
        recyclerViewAlumnos.setLayoutManager(new LinearLayoutManager(this));
        //Recoge el nombre del grupo
        Bundle extras = getIntent().getExtras();
        grupo = extras.getString("grupoSeleccionado");
        getListaAlumnos();
        getAlumnoID();
        alumnoAdapter = new AlumnoAdapter(ListaAlumnosActivity.this, listaAlumnos, ListaAlumnosActivity.this::onAlumnoClick);
        recyclerViewAlumnos.setAdapter(alumnoAdapter);

        if(LoginActivity.getRol().equals("padre")||LoginActivity.getRol().equals("profesor")){
            binding.addAlumno.setVisibility(View.GONE);
        }

        binding.addAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlumno();
            }
        });


    }

    //Método que obtiene la lista de alumnos para el grupo seleccionado
    public void getListaAlumnos() {
        //Se crea una instancia de llamada a la API
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los alumnos
        Call<List<Alumnos>> call = apiService.getAlumnos(grupo);
        call.enqueue(new Callback<List<Alumnos>>() {
            @Override
            public void onResponse(Call<List<Alumnos>> call, Response<List<Alumnos>> response) {
                listaAlumnos = response.body();
                alumnoAdapter = new AlumnoAdapter(ListaAlumnosActivity.this, listaAlumnos, ListaAlumnosActivity.this::onAlumnoClick);
                recyclerViewAlumnos.setAdapter(alumnoAdapter);
            }

            @Override
            public void onFailure(Call<List<Alumnos>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    //Inicia la pantalla con la información del profesor seleccionado

    public void onAlumnoClick(int position) {
        try {
            if (rol.equals("admin") || rol.equals("director") || rol.equals("profesor_admin") || rol.equals("profesor")) {
                Intent alumnoSeleccionado = new Intent(this, AlumnoActivity.class);
                alumnoSeleccionado.putExtra("alumnoSeleccionado", new Gson().toJson(listaAlumnos.get(position)));
                alumnoSeleccionado.putExtra("grupo", grupo);
                startActivity(alumnoSeleccionado);
            }else if(rol.equals("padre") && getAlumnoID()==listaAlumnos.get(position).getAlumno_id()){
                Intent alumnoSeleccionado = new Intent(this, AlumnoActivity.class);
                alumnoSeleccionado.putExtra("alumnoSeleccionado", new Gson().toJson(listaAlumnos.get(position)));
                alumnoSeleccionado.putExtra("grupo", grupo);
                startActivity(alumnoSeleccionado);
            }
        } catch (Exception e) {

        }
    }



    private static int getAlumnoID() {
        //Se llama al servicio que obtiene el de alumno segun usuarioId del padre
        Call<Alumnos> call = apiService.getAlumnoByAlumnoId(padre.getAlumnoId());
        call.enqueue(new Callback<Alumnos>() {
            @Override
            public void onResponse(Call<Alumnos> call, Response<Alumnos> response) {
                alumno = response.body();

            }
            @Override
            public void onFailure(Call<Alumnos> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
        return alumno.getAlumno_id();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GrupoActivity.class);
        intent.putExtra("grupoSeleccionado", grupo);
        startActivity(intent);
    }

    public void addAlumno() {
        Intent addAlumno = new Intent(this, AlumnoAddActivity.class);
        addAlumno.putExtra("grupoSeleccionado", grupo);
        startActivity(addAlumno);
    }
}