package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.UsuariosAdapter;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityUsuariosMenuBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity usuarios, muestra la lista de usuarios almacenada
 */

public class UsuariosActivity extends AppCompatActivity {

    ApiService apiService;
    UsuariosAdapter adapter;
    RecyclerView recyclerUsuarios;
    List<Usuarios> listaUsuarios = new ArrayList<>();
    private ActivityUsuariosMenuBinding binding;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUsuarios();
        binding = ActivityUsuariosMenuBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        recyclerUsuarios = findViewById(R.id.recyclerViewUsuarios);
        recyclerUsuarios.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerUsuarios.setLayoutManager(mLayoutManager);
        adapter = new UsuariosAdapter(listaUsuarios);
        recyclerUsuarios.setAdapter(adapter);

        //Vista para la búsqueda de usuarios
        SearchView simpleSearchView = binding.searchView;
        simpleSearchView.setQueryHint(getString(R.string.busqueda));

        simpleSearchView.setOnQueryTextListener(new android.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        binding.addUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUsuario();
            }
        });


    }

    //Método para borrar a un usuario
    public void deleteUsuario(int position) {
        new AlertDialog.Builder(UsuariosActivity.this)
                .setTitle(R.string.borrarUsuario)
                .setMessage(R.string.confirmaBorraUsuario)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los profesores
                        Call<Void> call = apiService.deleteUsuarios(listaUsuarios.get(position).getUsuarios_id());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(UsuariosActivity.this, R.string.usuarioBorrado, Toast.LENGTH_SHORT).show();
                                    //Se borra el usuario de la lista y posteriormente se notifica al adapter
                                    listaUsuarios.remove(position);
                                    adapter.notifyItemRemoved(position);
                                    adapter.notifyItemRangeChanged(position, listaUsuarios.size());
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    //Método para obtener la lista de usuarios
    public void getUsuarios() {
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los usuarios
        Call<List<Usuarios>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                listaUsuarios = response.body();
                adapter = new UsuariosAdapter(listaUsuarios);
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
                    public void onEditClick(int position) {
                        editUsuario(position);
                    }
                });
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    //Método para editar a un usuario
    private void editUsuario(int position) {
        Intent intent = new Intent(this, UsuarioEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("Usuario", new Gson().toJson(listaUsuarios.get(position)));
        intent.putExtras(bundle);

        startActivity(intent);
    }
    //Método para añadir un usuario
    private void addUsuario() {
        Intent intent = new Intent(this, UsuarioAddActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        vueltaMenu();
    }

    private void vueltaMenu() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("rol", LoginActivity.getRol());
        startActivity(intent);
    }

}
