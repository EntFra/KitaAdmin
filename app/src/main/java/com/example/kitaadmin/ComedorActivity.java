package com.example.kitaadmin;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Comedor;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityComedorBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComedorActivity extends AppCompatActivity {

    private ActivityComedorBinding binding;
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());
    private ApiService apiService;
    Comedor menuDia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("fecha") != null) {
            setFechaDia(extras.getString("fecha"));
        }

        cargaActivity(date);

    }

    private void cargaActivity(String date) {
        binding = ActivityComedorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.dateComedor.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.dateComedor);
            }
        });

    }

    private void setFechaDia(String fecha) {
        date = fecha;

    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                editText.setText(selectedDate);
                getComedorDia(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void getDayAfter(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        date = sdf.format(c.getTime());
        getComedorDia(date);
    }

    public void getDayBefore(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, -1);
        date = sdf.format(c.getTime());
        getComedorDia(date);
    }

    private void getComedorDia(String fecha) {
        //Primero se establece la fecha y se oculta la tabla con la información, en caso de existir datos que mostrar se cambia la visibilidad de la tabla
        binding.dateComedor.setText(fecha);
        binding.tableComedor.setVisibility(View.GONE);
        binding.textViewSinInforme.setVisibility(View.VISIBLE);

        apiService = Network.getInstance().create(ApiService.class);

        apiService.getComedor(fecha).enqueue(new Callback<Comedor>() {

            @Override
            public void onResponse(Call<Comedor> call, Response<Comedor> response) {
                if (response.body() != null) {
                    menuDia = response.body();
                    //Formatea el tiempo dormido a horas y minutos
                    binding.textDesayuno.setText(menuDia.getDesayuno());
                    binding.textSnack.setText(menuDia.getSnack());
                    binding.textPlatoPrincipal.setText(menuDia.getPlato_principal());
                    binding.textPostre.setText(menuDia.getPostre());
                }
            }

            @Override
            public void onFailure(Call<Comedor> call, Throwable t) {

            }
        });


    }

}


