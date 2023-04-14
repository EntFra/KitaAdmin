package com.example.kitaadmin.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Estadisticas;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityEstadisticasBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Clase que muestra las estadísticas de la aplicación
 */
public class EstadisticasActivity extends AppCompatActivity {

    ActivityEstadisticasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEstadisticasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        obtenerEstadisticas();
    }

    public void obtenerEstadisticas() {
        ApiService apiService = Network.getInstance().create(ApiService.class);;
        Estadisticas estadisticas = null;

        Call<Estadisticas> call = apiService.getEstadisticas();
        call.enqueue(new Callback<Estadisticas>() {
            @Override
            public void onResponse(Call<Estadisticas> call, Response<Estadisticas> response) {
                if(response.isSuccessful()) {
                    Estadisticas estadisticas = response.body();

                    binding.txtNumeroAlumnos.setText(String.valueOf(estadisticas.getNumeroAlumnos()));
                    binding.txtNumeroPadres.setText(String.valueOf(estadisticas.getNumeroPadres()));
                    binding.txtNumeroUsuarios.setText(String.valueOf(estadisticas.getNumeroUsuarios()));
                    binding.txtNumeroProfesores.setText(String.valueOf(estadisticas.getNumeroProfesores()));
                }
            }

            @Override
            public void onFailure(Call<Estadisticas> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
