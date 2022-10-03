package com.example.kitaadmin;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.databinding.ActivityInformeBinding;
import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformeActivity extends AppCompatActivity {

    private ActivityInformeBinding binding;
    Alumnos alumno;
    String AlumnoSeleccionado;
    Informes informe;
    private ApiService apiService;
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());

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
        ;
        binding.dateInforme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.dateInforme);
            }
        });
        getInformeAlumno(date);
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                editText.setText(selectedDate);
                getInformeAlumno(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getDayAfter(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = sdf.format(c.getTime());  // dt is now the new date
        getInformeAlumno(date);
    }

    public void getDayBefore(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, -1);  // number of days to add
        date = sdf.format(c.getTime());  // dt is now the new date
        getInformeAlumno(date);
    }

    private void getInformeAlumno(String fecha) {
        //Primero se establece la fecha y se oculta la tabla con la información, en caso de existir datos que mostrar se cambia la visibilidad de la tabla
        binding.dateInforme.setText(fecha);
        binding.tableInforme.setVisibility(View.GONE);
        binding.textViewSinInforme.setVisibility(View.VISIBLE);

        apiService = Network.getInstance().create(ApiService.class);

        apiService.getInformes(alumno.getAlumno_id(), fecha).enqueue(new Callback<Informes>() {

            @Override
            public void onResponse(Call<Informes> call, Response<Informes> response) {
                System.out.println("yo he llamao");
                if(response.body()!=null) {
                    informe = response.body();
                    System.out.println("Que ase aki");
                    binding.textViewSinInforme.setVisibility(View.GONE);
                    binding.tableInforme.setVisibility(View.VISIBLE);
                    binding.dateInforme.setText(informe.getFecha());
                    binding.textDeposicion.setText(isTrue(informe.isDeposicion()));
                    binding.textComida.setText(isTrue(informe.isComida()));
                    binding.textBebida.setText(isTrue(informe.isBebida()));
                    binding.textSueno.setText((CharSequence) informe.getSuenio());
                }else{
                    System.out.println("Esto va");
                    binding.dateInforme.setText(fecha);
                }
            }

            @Override
            public void onFailure(Call<Informes> call, Throwable t) {

            }
        });


    }

    private Calendar parseStringToCalendar(String date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd" + "-" + "MM" + "-" + "yyyy", Locale.ENGLISH);
        cal.setTime(Objects.requireNonNull(sdf.parse(date)));// all done
        return cal;
    }

    private String isTrue(boolean b) {
        if(b){
            return "Si";
        }else{
            return "No";
        }
    }


}

