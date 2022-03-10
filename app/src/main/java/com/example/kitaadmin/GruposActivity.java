package com.example.kitaadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.GruposAdapter;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GruposActivity extends AppCompatActivity implements GruposAdapter.OnGruposListener {

    ApiService apiService;
    List<Grupos> listaGrupos = new ArrayList<>();
    RecyclerView recyclerViewGrupos;
    GruposAdapter gruposAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupos);

        getListaGrupos();


    }

    //Método que obtiene la lista de grupos
    public void getListaGrupos() {
        //Se crea una instancia de llamada a la API
        apiService =  Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los grupos
        Call<List<Grupos>> call = apiService.getGrupos();
        call.enqueue(new Callback<List<Grupos>>() {
            @Override
            public void onResponse(Call<List<Grupos>> call, Response<List<Grupos>> response) {
                listaGrupos = response.body();
                recyclerViewGrupos = findViewById(R.id.recyclerGrupos);
                recyclerViewGrupos.setLayoutManager(new GridLayoutManager(GruposActivity.this,2));
                gruposAdapter = new GruposAdapter(GruposActivity.this, listaGrupos);
                recyclerViewGrupos.setAdapter(gruposAdapter);
            }

            @Override
            public void onFailure(Call<List<Grupos>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    //Inicia la pantalla con la información del grupo seleccionado
    @Override
    public void onGruposClick(int position) {
        Intent grupo = new Intent(this,GrupoActivity.class);
        grupo.putExtra("grupoSeleccionado", (Serializable) listaGrupos.get(position));
        startActivity(grupo);
    }
}
