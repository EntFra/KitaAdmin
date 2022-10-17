package com.example.kitaadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.AlumnoAdapter;
import com.example.kitaadmin.Adapter.UsuariosAdapter;
import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityUsuariosMenuBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuariosActivity extends AppCompatActivity {

    private ActivityUsuariosMenuBinding binding;
    ApiService apiService;
    Usuarios usuario;
    UsuariosAdapter adapter;
    RecyclerView recyclerUsuarios;
    List<Usuarios> listaUsuarios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_menu);
        cargarActivity();
}

    private void cargarActivity() {
        binding = ActivityUsuariosMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerUsuarios = binding.recyclerViewUsuarios;
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        getUsuarios();

        adapter = new UsuariosAdapter(this, listaUsuarios, UsuariosActivity.this::onUsuariosClick);
        recyclerUsuarios.setAdapter(adapter);


    }

    public void getUsuarios() {
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los profesores
        Call<List<Usuarios>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                listaUsuarios = response.body();
                adapter = new UsuariosAdapter(UsuariosActivity.this, listaUsuarios, UsuariosActivity.this::onUsuariosClick);
                recyclerUsuarios.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private void onUsuariosClick(int position) {

    }
}
