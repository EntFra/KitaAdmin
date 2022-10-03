package com.example.kitaadmin;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.databinding.ActivityAlumnoBinding;
import com.example.kitaadmin.databinding.ActivityAlumnoEditBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoEditActivity extends AppCompatActivity {

    private ActivityAlumnoEditBinding binding;
    Alumnos alumno;
    String grupo;
    String alumnoSeleccionado;
    boolean noCambioCampos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargaActivity();
    }

    private void cargaActivity(){
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
        spinnerGrupos();
        recuperaInfoAlumno();
        noCambioCampos = true;

        binding.editTextFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.editTextFechaNac);
            }
        });

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

        binding.editObservaciones.addTextChangedListener(tw);
        binding.editTextAlergia.addTextChangedListener(tw);
        binding.editTextFechaNac.addTextChangedListener(tw);
    }

    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
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
        for (Grupos g:GruposActivity.listaGrupos()
        ) {
            if(g.getNombreGrupo().equals(alumno.getNombre_grupo())) {
                binding.spinnerGrupoAlumno.setSelection(GruposActivity.listaGrupos().indexOf(g));
            }
        }
    }

    public void updateAlumno(View view) {




            alumno.setAlergias(binding.editTextAlergia.getText().toString());
            alumno.setFecha_nac(binding.editTextFechaNac.getText().toString());
            alumno.setComedor(binding.switchComedor.isChecked());
            alumno.setAuto_fotos(binding.switchFotos.isChecked());
            alumno.setAuto_salidas(binding.switchSalidas.isChecked());
            alumno.setNombre_grupo(binding.spinnerGrupoAlumno.getSelectedItem().toString());
            alumno.setObservaciones(binding.editObservaciones.getText().toString());


                ApiService apiService = Network.getInstance().create(ApiService.class);
                Call<Alumnos> call = apiService.updateAlumno(alumno);
                call.enqueue(new Callback<Alumnos>() {
                    @Override
                    public void onResponse(Call<Alumnos> call, Response<Alumnos> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AlumnoEditActivity.this, R.string.alumnoActualizado, Toast.LENGTH_SHORT).show();
                            grupo = binding.spinnerGrupoAlumno.getSelectedItem().toString();
                            volverAlumno();
                        } else {
                            Toast.makeText(AlumnoEditActivity.this, R.string.errorActualizaAlumno, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Alumnos> call, Throwable t) {

                    }
                });
            }




    private void spinnerGrupos(){
        //Lista de datos a cargar
        List<Grupos> listaGrupos = GruposActivity.listaGrupos();
        ArrayList<String> listaLimpia = new ArrayList<>();
        //S obtiene el nombre del grupo en String
        for (Grupos g:listaGrupos
        ) {
            listaLimpia.add(g.getNombreGrupo());
        }

        //Adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listaLimpia);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerGrupoAlumno.setAdapter(arrayAdapter);


    }

    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(AlumnoEditActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverAlumno();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }

    private boolean setCambioCampos(boolean isChanged) {

        return noCambioCampos = isChanged;
    }

    private boolean getNoCambioCampos() {
        return noCambioCampos;
    }


    public void volverAlumno (){
        Intent alumnoSeleccionado = new Intent(this, AlumnoActivity.class);
        alumnoSeleccionado.putExtra("alumnoSeleccionado", new Gson().toJson(alumno));
        alumnoSeleccionado.putExtra("grupo",grupo);
        startActivity(alumnoSeleccionado);
    }

    @Override
    public void onBackPressed(){
        if(getNoCambioCampos()){

            volverAlumno();
        }else {
            dialogoBack();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        cargaActivity();
    }


}