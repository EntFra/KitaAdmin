package com.example.kitaadmin;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityInformeBinding;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformeActivity extends AppCompatActivity {

    private ActivityInformeBinding binding;
    Alumnos alumno;
    String AlumnoSeleccionado;
    Informes informe;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);

        binding = ActivityInformeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            AlumnoSeleccionado = extras.getString("alumnoSeleccionado");
        }
        alumno = new Gson().fromJson(AlumnoSeleccionado, Alumnos.class);
        String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());

        getInformeAlumno();
    }


    private void getInformeAlumno() {

        apiService = Network.getInstance().create(ApiService.class);
        String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());

        apiService.getInformes(alumno.getAlumno_id(), date).enqueue(new Callback<Informes>() {
            @Override
            public void onResponse(Call<Informes> call, Response<Informes> response) {
                if(response.body()!=null) {
                    informe = response.body();
                    Log.d("TAG", "onResponse: " + informe);
                    binding.dateInforme.setText((CharSequence) informe.getFecha());
                    binding.textDeposicion.setText(isTrue(informe.isDeposicion()));
                    binding.textComida.setText(isTrue(informe.isComida()));
                    binding.textBebida.setText(isTrue(informe.isBebida()));
                    binding.textSueno.setText((CharSequence) informe.getSuenio());
                }
            }

            @Override
            public void onFailure(Call<Informes> call, Throwable t) {

            }
        });


    }

    private String isTrue(boolean b) {
        if(b){
            return "Si";
        }else{
            return "No";
        }
    }


}

