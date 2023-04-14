package com.example.kitaadmin.Activities;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Comedor;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.databinding.ActivityComedorBinding;
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
 * Clase que maneja el activity Comedor, mostará el menú del comedor para el día seleccionado, permitiendo seleccionar una fecha distinta, acceder a su edeición, así como su borrado
 */

public class ComedorActivity extends AppCompatActivity {
    //Variable que almacena la fecha del día con el formato dd-MM-yyyy
    String date = DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now());
    Comedor menuDia;
    private ActivityComedorBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.getString("fecha") != null) {
            setFechaDia(extras.getString("fecha"));
        }

        cargaActivity(date);

        binding.btnDeleteComedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteComedorDia(date);
            }
        });

    }
    //Método que carga el menú del comedor para el día seleccionado
    private void cargaActivity(String date) {
        binding = ActivityComedorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getComedorDia(date);
        if(LoginActivity.getRol().equals("padre")||LoginActivity.getRol().equals("profesor")){
            binding.btnDeleteComedor.setVisibility(View.GONE);
            binding.ButtonEditComedor.setVisibility(View.GONE);
        }

        binding.dateComedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.dateComedor);
            }
        });

    }
    //Establece la fecha seleccionada
    private void setFechaDia(String fecha) {
        date = fecha;

    }
    //Método que muestra el DatePicker
    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                editText.setText(selectedDate);
                getComedorDia(selectedDate);
                date = selectedDate;
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    //Método que muestra el menú del día siguiente
    public void getDayAfter(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        date = sdf.format(c.getTime());
        getComedorDia(date);
    }
    //Método que muestra el menú del día anterior
    public void getDayBefore(View view) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, -1);
        date = sdf.format(c.getTime());
        getComedorDia(date);
    }
    //Método que muestra el menú del día seleccionado
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

                    binding.textViewSinInforme.setVisibility(View.GONE);
                    binding.tableComedor.setVisibility(View.VISIBLE);
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
    //Método que borra el menú del comedor para el día seleccionado
    private void deleteComedorDia(String date) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.borrarCalendario)
                .setMessage(R.string.confirmaBorraCalendario)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los alumnos
                        Call<Void> call = apiService.deleteComedor(date);
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ComedorActivity.this, R.string.comedorBorrado, Toast.LENGTH_SHORT).show();
                                }
                            }


                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                        getComedorDia(date);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();


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

    //Abre el activity para introducir o modificar los datos de menú del día seleccionado
    public void comedorEdit(View view) {
        Intent intent = new Intent(this, ComedorEditActivity.class);
        if (menuDia != null && menuDia.getFecha().equals(binding.dateComedor.getText().toString())) {
            intent.putExtra("menuDia", new Gson().toJson(menuDia));
        }

        intent.putExtra("fecha", binding.dateComedor.getText().toString());
        startActivity(intent);
    }

}


