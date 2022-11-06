package com.example.kitaadmin;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Informacion;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityInformacionActualBinding;
import com.example.kitaadmin.databinding.ActivityInformeBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformacionActualActivity extends AppCompatActivity {
    ApiService apiService;
    ActivityInformacionActualBinding binding;
    List<Informacion> informacion = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_actual);

        binding = ActivityInformacionActualBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        getInformacion();
    }

    private void getInformacion() {
        apiService = Network.getInstance().create(ApiService.class);

        Call<List<Informacion>> call = apiService.getInformacion();

        call.enqueue(new Callback<List<Informacion>>() {

            @Override
            public void onResponse(Call<List<Informacion>> call, Response<List<Informacion>> response) {
                if (response.body() != null) {
                    informacion = response.body();
                    binding.textViewActual.setText(informacion.get(0).getInformacion());
                }
            }

            @Override
            public void onFailure(Call<List<Informacion>> call, Throwable t) {

            }
        });

    }
}