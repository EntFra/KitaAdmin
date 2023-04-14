package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Calendario;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityCalendarioEditBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Calendario edit, permitirá la edición del evento guardado para el día seleccionado
 */
public class CalendarioEditActivity extends AppCompatActivity {

    Calendario calendarioOld;
    Calendario calendarioNew;
    String fecha;
    String evento;
    String calendario;
    //Variable que almacena si hubo cambios en los campos para realizar update
    boolean noCambioCampos = true;
    private ActivityCalendarioEditBinding binding;
    private ApiService api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor_edit);
        binding = ActivityCalendarioEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            calendario = extras.getString("calendario");
            fecha = extras.getString("fecha");

            calendarioOld = new Gson().fromJson(calendario, Calendario.class);
        }
        binding.dateCalendario.setText(fecha);
        if (calendarioOld != null) {
            getCalendarioDia();
        }

        noCambioCampos = true;

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

                setCambioCampos(false);

            }
        };

        binding.editEvento.addTextChangedListener(tw);
        binding.buttonGuardarCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCalendario();
            }
        });

    }


    private boolean setCambioCampos(boolean isChanged) {

        return noCambioCampos = isChanged;
    }
    //Método que obtiene el calendario del día seleccionado
    private void getCalendarioDia() {
        binding.dateCalendario.setText(calendarioOld.getDia());
        binding.editEvento.setText(calendarioOld.getEvento());
    }
    //Método que devuelve el calendario con los datos actualizados
    private Calendario getCalendarioNew() {
        calendarioNew = calendarioOld;
        calendarioNew.setDia(fecha);
        calendarioNew.setEvento(binding.editEvento.getText().toString());
        return calendarioNew;

    }

    //Método que realiza el update del calendario
    public void updateCalendario() {


        api = Network.getInstance().create(ApiService.class);
        api.updateCalendario(getCalendarioNew()).enqueue(new Callback<Calendario>() {

            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {
                if (response.body() != null) {
                    Toast.makeText(CalendarioEditActivity.this, R.string.calendarioActualizado, Toast.LENGTH_SHORT).show();
                    volverCalendario();
                } else {
                    Toast.makeText(CalendarioEditActivity.this, R.string.errorActualizaCalendario, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {

            }
        });

    }
    //Método que muestra un dialogo de confirmación para salir sin guardar cambios
    private void dialogoBack() {

        androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(CalendarioEditActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverCalendario();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }
    //Método que devuelve al activity calendario
    private void volverCalendario() {
        Intent fechaSeleccionada = new Intent(this, CalendarioActivity.class);
        fechaSeleccionada.putExtra("fecha", fecha);
        startActivity(fechaSeleccionada);
    }
    //Método que muestra un dialogo de confirmación para salir sin guardar cambios
    @Override
    public void onBackPressed() {
        getCalendarioNew();
        if (binding.editEvento.getText().toString().isEmpty() || noCambioCampos) {
            volverCalendario();
        } else {
            dialogoBack();
        }
    }
}
