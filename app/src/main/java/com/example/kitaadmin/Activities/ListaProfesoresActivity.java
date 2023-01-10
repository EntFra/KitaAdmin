package com.example.kitaadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.ProfesorAdapter;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityListaProfesoresBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity lista profesores, muestra el listado de alumnos del grupo seleccionado
 */
public class ListaProfesoresActivity extends AppCompatActivity implements ProfesorAdapter.OnProfesorListener {


    List<Profesores> listaProfesores = new ArrayList<>();
    RecyclerView recyclerViewProfesores;
    ProfesorAdapter profesorAdapter;
    ApiService apiService;
    String grupo;
    ActivityListaProfesoresBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_profesores);

        binding = ActivityListaProfesoresBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        recyclerViewProfesores = findViewById(R.id.recyclerProfesores);
        recyclerViewProfesores.setLayoutManager(new LinearLayoutManager(this));
        //Recoge el nombre del grupo
        Bundle extras = getIntent().getExtras();
        grupo = extras.getString("grupoSeleccionado");
        getListaProfesores();
        profesorAdapter = new ProfesorAdapter(ListaProfesoresActivity.this, listaProfesores, ListaProfesoresActivity.this::onProfesorClick);
        recyclerViewProfesores.setAdapter(profesorAdapter);

        binding.addProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfesor();
            }
        });
    }

    //Método que obtiene la lista de profesores para el grupo seleccionado
    public void getListaProfesores() {
        //Se crea una instancia de llamada a la API
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los profesores
        Call<List<Profesores>> call = apiService.getProfesores(grupo);
        call.enqueue(new Callback<List<Profesores>>() {
            @Override
            public void onResponse(Call<List<Profesores>> call, Response<List<Profesores>> response) {
                listaProfesores = response.body();
                profesorAdapter = new ProfesorAdapter(ListaProfesoresActivity.this, listaProfesores, ListaProfesoresActivity.this::onProfesorClick);
                recyclerViewProfesores.setAdapter(profesorAdapter);
            }

            @Override
            public void onFailure(Call<List<Profesores>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    //Inicia la pantalla con la información del profesor seleccionado

    public void onProfesorClick(int position) {
        String rol = LoginActivity.getRol();
        try {
            if (rol.equals("admin") || rol.equals("director") || rol.equals("profesor_admin")) {
                Intent profesorSeleccionado = new Intent(this, ProfesorActivity.class);
                profesorSeleccionado.putExtra("profesorSeleccionado", new Gson().toJson(listaProfesores.get(position)));
                profesorSeleccionado.putExtra("grupo", grupo);
                startActivity(profesorSeleccionado);
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GrupoActivity.class);
        intent.putExtra("grupoSeleccionado", grupo);
        startActivity(intent);
    }

    public void addProfesor() {
        Intent addProfesor = new Intent(this, ProfesorAddActivity.class);
        addProfesor.putExtra("grupoSeleccionado", grupo);
        startActivity(addProfesor);
    }

}