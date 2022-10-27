package com.example.kitaadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.AlumnoAdapter;
import com.example.kitaadmin.Adapter.UsuariosAdapter;
import com.example.kitaadmin.Fragments.UsuarioAddFragment;
import com.example.kitaadmin.Fragments.UsuarioEditFragment;
import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityUsuariosMenuBinding;
import com.google.gson.Gson;

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
    FragmentTransaction transaction;
    Fragment fragmentAdd, fragmentEdit;
    ImageButton ib = (ImageButton)findViewById(R.id.editUsuario);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_menu);
        cargarActivity();

        fragmentAdd = new UsuarioAddFragment();
        fragmentEdit = new UsuarioEditFragment();

}

    public void cargaFragmentAddUsuario(View v){
        transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragmentUsuario, fragmentAdd);
        transaction.commit();
    }

    public void cargaFragmentEditUsuario(View v){
        transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragmentUsuario, fragmentEdit);
        transaction.commit();
    }

    private void cargarActivity() {
        binding = ActivityUsuariosMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerUsuarios = binding.recyclerViewUsuarios;
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(this));

        getUsuarios();

        adapter = new UsuariosAdapter(UsuariosActivity.this, listaUsuarios, UsuariosActivity.this::onUsuariosClick);
        recyclerUsuarios.setAdapter(adapter);




    }

    public void getUsuarios() {
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los usuarios
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
    listaUsuarios.get(position);

    }
}
