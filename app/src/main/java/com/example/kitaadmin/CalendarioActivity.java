package com.example.kitaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

import com.example.kitaadmin.Model.Calendario;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityCalendarioBinding;
import com.example.kitaadmin.databinding.ActivityProfesorEditBinding;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarioActivity extends AppCompatActivity {

    ActivityCalendarioBinding binding;
    ApiService apiService;
    Calendario calendario;
    Calendar calendar;
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        binding = ActivityCalendarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        CalendarView v = (CalendarView)findViewById(R.id.calendarView);
        v.setFirstDayOfWeek(Calendar.MONDAY);
        v.setOnDateChangeListener( new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                LocalDate selectedDate = LocalDate.of( year,  (month+1), dayOfMonth);
                try {
                   String day =  Utils.getDate(selectedDate.toString());
                   getCalendarioDia(day);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        getCalendarioDia(date);
    }

    private void getCalendarioDia(String fecha) {

        binding.textEventoCalendarioDia.setText(R.string.calendarioSinEvento);

        apiService = Network.getInstance().create(ApiService.class);

        apiService.getCalendario(fecha).enqueue(new Callback<Calendario>() {

            @Override
            public void onResponse(Call<Calendario> call, Response<Calendario> response) {

                if (response.body() != null) {
                    calendario = response.body();
                    //Muestra el evento guardado en la base de datos
                    if(calendario.getEvento().length()!=0){
                        binding.textEventoCalendarioDia.setText(calendario.getEvento());
                    }

                }
            }

            @Override
            public void onFailure(Call<Calendario> call, Throwable t) {

            }
        });


    }
}