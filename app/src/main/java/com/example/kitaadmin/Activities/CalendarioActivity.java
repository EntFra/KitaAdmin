package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Calendario;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityCalendarioBinding;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity Calendario, mostrará las información del día seleccionado, permitiendo acceder a su edición o realizar el borrado
 */
public class CalendarioActivity extends AppCompatActivity {

    ActivityCalendarioBinding binding;
    ApiService apiService;
    Calendario calendario = new Calendario();
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());
    String selectedDay = date;
    CalendarView v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        binding = ActivityCalendarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fecha = extras.getString("fecha");

            date = fecha;
        }


        v = findViewById(R.id.calendarView);
        v.setFirstDayOfWeek(Calendar.MONDAY);
        v.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                LocalDate selectedDate = LocalDate.of(year, (month + 1), dayOfMonth);
                try {
                    selectedDay = Utils.getDate(selectedDate.toString());
                    getCalendarioDia(selectedDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        getCalendarioDia(date);

        binding.btnDeleteCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCalendarioDia(selectedDay);
            }
        });
    }

    private void deleteCalendarioDia(String fecha) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.borrarCalendario)
                .setMessage(R.string.confirmaBorraCalendario)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los alumnos
                        Call<Void> call = apiService.deleteCalendario(fecha);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(CalendarioActivity.this, R.string.calendarioBorrado, Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                        getCalendarioDia(fecha);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();


    }


    private void getCalendarioDia(String fecha) {

        binding.textEventoCalendarioDia.setText(R.string.calendarioSinEvento);

        apiService = Network.getInstance().create(ApiService.class);
        calendario.setDia(fecha);
        calendario.setEvento("");
        apiService.getCalendario(fecha).enqueue(new Callback<Calendario>() {

            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {

                if (response.body() != null) {
                    calendario = response.body();
                    //Muestra el evento guardado en la base de datos
                    if (calendario.getEvento().length() != 0) {
                        try {
                            v.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(fecha).getTime(), true, true);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        binding.textEventoCalendarioDia.setText(calendario.getEvento());
                    }

                }
            }

            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {

            }
        });


    }

    public void calendarioEdit(View view) {
        Intent intent = new Intent(this, CalendarioEditActivity.class);
        intent.putExtra("calendario", new Gson().toJson(calendario));
        intent.putExtra("fecha", calendario.getDia());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("rol", LoginActivity.getRol());
        startActivity(intent);
    }
}