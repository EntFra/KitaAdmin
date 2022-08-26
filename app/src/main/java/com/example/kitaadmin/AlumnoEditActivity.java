package com.example.kitaadmin;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.databinding.ActivityAlumnoBinding;
import com.example.kitaadmin.databinding.ActivityAlumnoEditBinding;
import com.google.gson.Gson;

public class AlumnoEditActivity extends AppCompatActivity {

    private ActivityAlumnoEditBinding binding;
    Alumnos alumno;
    String grupo;
    String alumnoSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_edit);

        binding = ActivityAlumnoEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Recoge el alumno seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumnoSeleccionado");
            alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);
            grupo = extras.getString("grupo");
        }

        recuperaInfoAlumno();

        binding.editTextFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog((EditText) binding.editTextFechaNac);
            }
        });
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "/" + twoDigits(month + 1) + "/" + year;
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void recuperaInfoAlumno(){

        binding.editTextFechaNac.setText(alumno.getFecha_nac());
        binding.textTituloAlumno.setText(alumno.getNombre());
        binding.editTextAlergia.setText(alumno.getAlergias());
        binding.editObservaciones.setText(alumno.getObservaciones());
        binding.switchFotos.setChecked(alumno.isAuto_fotos());
        binding.switchSalidas.setChecked(alumno.isAuto_salidas());
        binding.switchComedor.setChecked(alumno.isComedor());
    }
}