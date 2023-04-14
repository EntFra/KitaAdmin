package com.example.kitaadmin.Activities;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityInformeBinding;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Informe, muestra el informe del día para el alumno seleccionado permitiendo la edición
 */
public class InformeActivity extends AppCompatActivity {

    Alumnos alumno;
    String alumnoSeleccionado;
    Informes informe;
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());
    private ActivityInformeBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("fecha") != null) {
            setFechaDia(extras.getString("fecha"));
        }
        binding = ActivityInformeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        cargaActivity(date);
        if(LoginActivity.getRol().equals("padre")){
            binding.btnEditInforme.setVisibility(View.GONE);
        }


    }

    private void cargaActivity(String fecha) {



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumnoSeleccionado");
            fecha = extras.getString("fecha");
        }

        alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);

        binding.dateInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.dateInforme);
            }
        });

        if (fecha == null) {
            getInformeAlumno(date);
        } else {
            getInformeAlumno(fecha);
        }


    }
    //Establece la fecha del informe
    void setFechaDia(String fecha) {
        date = fecha;

    }
    //Muestra el datepicker para seleccionar la fecha del informe
    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                editText.setText(selectedDate);
                getInformeAlumno(selectedDate);
                date = selectedDate;
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    //Método para mostrar el informe del día siguiente
    public void getDayAfter(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        date = sdf.format(c.getTime());
        getInformeAlumno(date);
    }
    //Método para mostrar el informe del día anterior
    public void getDayBefore(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, -1);
        date = sdf.format(c.getTime());
        getInformeAlumno(date);
    }
    //Método que muestra el informe del día actual
    private void getInformeAlumno(String fecha) {
        //Primero se establece la fecha y se oculta la tabla con la información, en caso de existir datos que mostrar se cambia la visibilidad de la tabla
        if(binding!=null){
        binding.dateInforme.setText(fecha);
        binding.tableInforme.setVisibility(View.GONE);
        binding.textViewSinInforme.setVisibility(View.VISIBLE);}

        apiService = Network.getInstance().create(ApiService.class);
        if(alumno!=null) {
            apiService.getInformes(alumno.getAlumno_id(), fecha).enqueue(new Callback<Informes>() {

                @Override
                public void onResponse(Call<Informes> call, Response<Informes> response) {
                    if (response.body() != null) {
                        informe = response.body();
                        //Formatea el tiempo dormido a horas y minutos
                        String suenio = Utils.formatTimeDuration(informe.getSuenio());
                        binding.textSueno.setText(suenio);
                        binding.textViewSinInforme.setVisibility(View.GONE);
                        binding.tableInforme.setVisibility(View.VISIBLE);
                        binding.dateInforme.setText(informe.getFecha());
                        binding.textDeposicion.setText(isTrue(informe.isDeposicion()));
                        binding.textComida.setText(isTrue(informe.isComida()));
                        binding.textBebida.setText(isTrue(informe.isBebida()));

                        binding.textObservacionesInforme.setText(informe.getObservaciones());
                    }
                }

                @Override
                public void onFailure(Call<Informes> call, Throwable t) {

                }
            });

        }
    }

    //Método que muestra nuevo activity para modificar informe
    public void informeEdit(View view) {
        Intent intent = new Intent(this, InformeEditAcitvity.class);
        intent.putExtra("alumno", alumnoSeleccionado);
        if (informe != null && informe.getFecha().equals(binding.dateInforme.getText().toString())) {
            intent.putExtra("informe", new Gson().toJson(informe));
        }

        intent.putExtra("fecha", binding.dateInforme.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AlumnoActivity.class);
        intent.putExtra("grupoSeleccionado", alumno.getNombre_grupo());
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        startActivity(intent);
    }


    private String isTrue(boolean b) {
        if (b) {
            return "Si";
        } else {
            return "No";
        }
    }


}

