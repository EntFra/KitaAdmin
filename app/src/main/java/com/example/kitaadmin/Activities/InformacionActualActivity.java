package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Informacion;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityInformacionActualBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Información actual, muestra la información actual y permite el acceso a su edición. No se permitirá el guardado de varios registros ya que se considera innecesario
 */
public class InformacionActualActivity extends AppCompatActivity {
    ApiService apiService;
    ActivityInformacionActualBinding binding;
    List<Informacion> informacion = new ArrayList<>();
    Informacion info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_actual);

        binding = ActivityInformacionActualBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(LoginActivity.getRol().equals("padre")||LoginActivity.getRol().equals("profesor")){
            binding.btnDeleteInfo.setVisibility(View.GONE);
            binding.imageButtonEditInfo.setVisibility(View.GONE);
        }


        binding.btnDeleteInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteInfo(info);
            }
        });

        binding.imageButtonEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editInfo();
            }
        });

        getInformacion();
    }
    //Método que abre la actividad de edición de información
    private void editInfo() {
        Intent intent = new Intent(this, InformacionEditActivity.class);
        intent.putExtra("informacion", new Gson().toJson(info));
        startActivity(intent);
    }
    //Método que borra la información actual
    private void deleteInfo(Informacion info) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.borrarInformacion)
                .setMessage(R.string.confirmaBorraInformacion)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se vacía la información del campo información, sin llegar a borrar la entrada de la tabla,
                        // ya que no es un valor necesario guardar cada entrada
                        info.setInformacion("");
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene la información
                        Call<Informacion> call = apiService.updateInformacion(info);
                        call.enqueue(new Callback<Informacion>() {
                            @Override
                            public void onResponse(Call<Informacion> call, Response<Informacion> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(InformacionActualActivity.this, R.string.informacionBorrada, Toast.LENGTH_SHORT).show();
                                    getInformacion();
                                }
                            }

                            @Override
                            public void onFailure(Call<Informacion> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();

    }
    //Método que obtiene la información actual
    private void getInformacion() {
        apiService = Network.getInstance().create(ApiService.class);

        Call<List<Informacion>> call = apiService.getInformacion();

        call.enqueue(new Callback<List<Informacion>>() {

            @Override
            public void onResponse(Call<List<Informacion>> call, Response<List<Informacion>> response) {
                if (response.body() != null) {
                    informacion = response.body();
                    binding.textViewActual.setText(getResources().getString(R.string.sinInfoActual));
                    if(!(informacion.get(informacion.size() - 1).getInformacion()).equalsIgnoreCase("")){
                        binding.textViewActual.setText(informacion.get(informacion.size() - 1).getInformacion());
                    }
                    info = informacion.get(informacion.size() - 1);

                }
            }

            @Override
            public void onFailure(Call<List<Informacion>> call, Throwable t) {

            }
        });

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