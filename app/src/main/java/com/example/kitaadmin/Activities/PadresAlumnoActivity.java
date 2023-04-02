package com.example.kitaadmin.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityPadresAlumnoBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity padres de alumno, muestra la información de los padres del alumno seleccionado
 */
public class PadresAlumnoActivity extends AppCompatActivity {

    String alumnoSeleccionado;
    Alumnos alumno;
    Padres padre1;
    Padres padre2;
    List<Padres> listaPadres = new ArrayList<>();
    Usuarios usuarios1;
    Usuarios usuarios2;
    private ActivityPadresAlumnoBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPadresAlumnoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Recoge el alumno seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumnoSeleccionado");
        }
        alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);
        getUsuarioPadre();

    }


    //Método que obtiene los padres del alumno seleccionado
    private void getUsuarioPadre() {
        //Se crea una instancia de llamada a la API
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los padres segun id de alumno y seguidamente se comprueba si son dos padres
        //o si solo existe uno en el sistema, recuperadno su información de usuario
        Call<List<Padres>> callPadres = apiService.getPadres(alumno.getAlumno_id());
        callPadres.enqueue(new Callback<List<Padres>>() {
            @Override
            public void onResponse(Call<List<Padres>> call, Response<List<Padres>> response) {
                if (response.isSuccessful()) {
                    listaPadres = response.body();
                    Log.d("TAG", "onResponse: " + listaPadres);
                    //Se llama al método que muestra la lista de profesores

                    int i = 0;
                    if (listaPadres.size() == 1) {
                        getPadre1(i);
                    } else if (listaPadres.size() == 2) {
                        getPadre1(i);
                        getPadre2(i+1);
                    } else {
                        //no funciona
                        binding.nombreApellidos1.setVisibility(View.GONE);
                        binding.nombreApellidos2.setVisibility(View.GONE);
                        binding.email1.setVisibility(View.GONE);
                        binding.email2.setVisibility(View.GONE);
                        binding.telefono1.setVisibility(View.GONE);
                        binding.telefono2.setVisibility(View.GONE);
                        binding.sinDatosPadresText.setText(R.string.sinPadres);
                        binding.sinDatosPadresText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                    }


                } else {
                    Log.d("TAG", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Padres>> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    private void getPadre1(int i) {
        binding.sinDatosPadresText.setVisibility(View.GONE);
        padre1 = listaPadres.get(i);
        Call<Usuarios> callUsuarios = apiService.getUsuario(padre1.getUsuariosId());
        callUsuarios.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                usuarios1 = response.body();
                //Se muestran los datos por pantalla
                binding.nombreApellidos1text.setText(usuarios1.getNombre());
                binding.email1text.setText(usuarios1.getEmail());
                binding.telefono1text.setText(Integer.toString(usuarios1.getTelefono()));
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());

            }

        });
    }

    private void getPadre2(int i) {
        padre2 = listaPadres.get(i + 1);
        Call<Usuarios> callUsuarios2 = apiService.getUsuario(padre2.getUsuariosId());
        callUsuarios2.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                usuarios2 = response.body();
                //Se muestran los datos por pantalla
                binding.nombreApellidos2text.setText(usuarios2.getNombre());
                binding.email2text.setText(usuarios2.getEmail());
                binding.telefono2text.setText(Integer.toString(usuarios2.getTelefono()));

            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());

            }

        });
    }


}

