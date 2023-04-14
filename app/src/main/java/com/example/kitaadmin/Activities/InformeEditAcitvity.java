package com.example.kitaadmin.Activities;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityInformeEditBinding;
import com.google.gson.Gson;

import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Informe edit, permite la edicón del informe
 */
public class InformeEditAcitvity extends AppCompatActivity {
    Alumnos alumno;
    Informes informeOld;
    Informes informeNew;
    String alumnoSeleccionado;
    String informeSeleccionado;
    String fecha;
    int hour, minute;
    int horaMostrada;
    int minutosMostrados;
    private ActivityInformeEditBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_edit);
        binding = ActivityInformeEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumno");
            informeSeleccionado = extras.getString("informe");
            fecha = extras.getString("fecha");

            alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);
            informeOld = new Gson().fromJson(informeSeleccionado, Informes.class);
        }

        binding.dateInforme.setText(fecha);
        if (informeOld != null) {
            getInformeAlumno();
            getHorasInforme();
            getMinutosInforme();
        }



    }
    //Método que actualiza el informe
    public void updateInformeAlumno(View view) {

        getInformeNew();

        apiService = Network.getInstance().create(ApiService.class);
        apiService.updateInformes(informeNew).enqueue(new Callback<Informes>() {

            @Override
            public void onResponse(Call<Informes> call, Response<Informes> response) {
                if (response.body() != null) {
                    Toast.makeText(InformeEditAcitvity.this, R.string.informeActualizado, Toast.LENGTH_SHORT).show();
                    volverInforme();
                } else {
                    Toast.makeText(InformeEditAcitvity.this, R.string.errorActualizaInforme, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Informes> call, Throwable t) {

            }
        });

    }
    //Método que ontiene los datos nuevos del informe
    private Informes getInformeNew() {
        informeNew = new Informes(alumno.getAlumno_id(), false, null, false, false, null, null);
        informeNew.setObservaciones(binding.textObservacionesInforme.getText().toString());
        informeNew.setSuenio(Utils.stringToTime(binding.textSueno.getText().toString()));
        informeNew.setAlumnoId(alumno.getAlumno_id());
        informeNew.setBebida(binding.switchBebida.isChecked());
        informeNew.setComida(binding.switchComida.isChecked());
        informeNew.setDeposicion(binding.switchDeposicion.isChecked());
        informeNew.setFecha(fecha);
        return informeNew;
    }
    //Método que obtiene los datos del informe
    private void getInformeAlumno() {

        binding.dateInforme.setText(informeOld.getFecha());
        binding.switchDeposicion.setChecked(informeOld.isDeposicion());
        binding.switchComida.setChecked(informeOld.isComida());
        binding.switchBebida.setChecked(informeOld.isBebida());
        binding.textSueno.setText(Utils.formatTimeDuration(informeOld.getSuenio()));
        binding.textObservacionesInforme.setText(informeOld.getObservaciones());


    }
    //Obtiene las horas del informe cambiando el formato
    private int getHorasInforme() {
        String[] horaMinutos = informeOld.getSuenio().split(":");
        int horas = Integer.parseInt(horaMinutos[0]);
        setHoras(horas);
        return horas;
    }
    //Obtiene los minutos del informe cambiando el formato
    private int getMinutosInforme() {
        String[] horaMinutos = informeOld.getSuenio().split(":");
        int minutos = Integer.parseInt(horaMinutos[1]);
        setMinutos(minutos);
        return minutos;
    }
    //Establece los minutos
    private void setMinutos(int minutos) {
        minutosMostrados = minutos;

    }
    //Establece las horas
    private void setHoras(int horas) {
        horaMostrada = horas;
    }
    //Método que muestra el time picker
    public void showTimePicker(View view) {

        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {


            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                setMinutos(minute);
                setHoras(hour);
                binding.textSueno.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, horaMostrada, minutosMostrados, true);


        timePickerDialog.setTitle(R.string.tiempoDormido);
        timePickerDialog.show();
    }
    //Método que muestra un dialogo de confirmación para salir sin guardar cambios
    private void dialogoBack() {

        androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(InformeEditAcitvity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverInforme();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }

    public void volverInforme() {
        Intent alumnoSeleccionado = new Intent(this, InformeActivity.class);
        alumnoSeleccionado.putExtra("alumnoSeleccionado", new Gson().toJson(alumno));
        alumnoSeleccionado.putExtra("fecha", fecha);
        startActivity(alumnoSeleccionado);
    }

    @Override
    public void onBackPressed() {
        getInformeNew();
        if (informeOld == null || Objects.equals(informeOld.toString(), informeNew.toString())) {
            volverInforme();
        } else {
            dialogoBack();
        }


    }
}
