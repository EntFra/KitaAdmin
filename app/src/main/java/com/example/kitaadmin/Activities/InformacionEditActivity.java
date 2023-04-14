package com.example.kitaadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Informacion;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityInformacionActualEditBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Información actual edit, permite la edición de la información almacenada
 */
public class InformacionEditActivity extends AppCompatActivity {

    ApiService apiService;
    ActivityInformacionActualEditBinding binding;
    Informacion info;
    String informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_actual);

        binding = ActivityInformacionActualEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            informacion = extras.getString("informacion");
            info = new Gson().fromJson(informacion, Informacion.class);
            binding.editInfo.setText(info.getInformacion());
        }


        binding.buttonGuardarInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInfo();
            }
        });

    }
    // Método que guarda la información editada
    private void guardarInfo() {
        apiService = Network.getInstance().create(ApiService.class);
        info.setInformacion(binding.editInfo.getText().toString());
        Call<Informacion> call = apiService.updateInformacion(info);
        call.enqueue(new Callback<Informacion>() {
            @Override
            public void onResponse(Call<Informacion> call, Response<Informacion> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(InformacionEditActivity.this, R.string.informacionActualizada, Toast.LENGTH_SHORT).show();

                    volverInfo();
                } else {
                    Toast.makeText(InformacionEditActivity.this, R.string.errorActualizaInformacion, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Informacion> call, Throwable t) {

            }
        });
    }

    private void volverInfo() {
        Intent intent = new Intent(this, InformacionActualActivity.class);
        startActivity(intent);
    }


}
