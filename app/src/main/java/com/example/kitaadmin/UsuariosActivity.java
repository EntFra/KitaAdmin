package com.example.kitaadmin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
    List<Usuarios> listaUsuarios;
    FragmentTransaction transaction;
    Fragment fragmentAdd, fragmentEdit;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_menu);

        cargarActivity();



        fragmentAdd = new UsuarioAddFragment();
        fragmentEdit = new UsuarioEditFragment();

    }

    public void cargaFragmentAddUsuario(View v) {
        transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragmentUsuario, fragmentAdd);
        transaction.commit();
    }


    private void deleteUsuario(int position) {
        new AlertDialog.Builder(UsuariosActivity.this)
                .setTitle(R.string.borrarUsuario)
                .setMessage(R.string.confirmaBorraUsuario)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los profesores
                        Call<Usuarios> call = apiService.deleteUsuarios(listaUsuarios.get(position).getUsuarios_id());
                        call.enqueue(new Callback<Usuarios>() {
                            @Override
                            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UsuariosActivity.this, R.string.usuarioBorrado, Toast.LENGTH_SHORT).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<Usuarios> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
        getUsuarios();
    }

    private void cargarActivity() {
        binding = ActivityUsuariosMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getUsuarios();


    }

    public void getUsuarios() {
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los usuarios
        Call<List<Usuarios>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                listaUsuarios = response.body();

                buildRecyclerView();

            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    public void buildRecyclerView() {
        recyclerUsuarios = findViewById(R.id.recyclerViewUsuarios);
        recyclerUsuarios.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new UsuariosAdapter(listaUsuarios);

        recyclerUsuarios.setLayoutManager(mLayoutManager);
        recyclerUsuarios.setAdapter(adapter);

        adapter.setOnItemClickListener(new UsuariosAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
            }

            @Override
            public void onDeleteClick(int position) {
                deleteUsuario(position);
            }

            @Override
            public void onEditClick(int position){
                editUsuario(position);
            }
        });
    }

    private void editUsuario(int position) {

        Bundle bundle = new Bundle();
        bundle.putString("Usuario", new Gson().toJson(listaUsuarios.get(position)));

        fragmentEdit.setArguments(bundle);

        transaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragmentUsuario, fragmentEdit);
        transaction.commit();

    }

}
