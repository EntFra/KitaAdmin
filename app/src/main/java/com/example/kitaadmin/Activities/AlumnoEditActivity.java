package com.example.kitaadmin.Activities;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityAlumnoEditBinding;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity Alumno edit, permite modificar la información del alumno seleccionado
 */
public class AlumnoEditActivity extends AppCompatActivity {

    static Alumnos alumno;
    String grupo;
    String alumnoSeleccionado;
    boolean noCambioCampos;
    private ActivityAlumnoEditBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargaActivity();
    }

    private void cargaActivity() {
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
            spinnerGrupos();
            recuperaInfoAlumno();
        }

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

    private void recuperaInfoAlumno() {

        binding.editTextFechaNac.setText(alumno.getFecha_nac());
        binding.textTituloAlumno.setText(alumno.getNombre());
        binding.editTextAlergia.setText(alumno.getAlergias());
        binding.editObservaciones.setText(alumno.getObservaciones());
        binding.switchFotos.setChecked(alumno.isAuto_fotos());
        binding.switchSalidas.setChecked(alumno.isAuto_salidas());
        binding.switchComedor.setChecked(alumno.isComedor());
        for (Grupos g : GruposActivity.listaGrupos()
        ) {
            if (g.getNombreGrupo().equals(alumno.getNombre_grupo())) {
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

    private void spinnerGrupos() {
        //Lista de datos a cargar
        List<Grupos> listaGrupos = GruposActivity.listaGrupos();
        ArrayList<String> listaLimpia = new ArrayList<>();
        //S obtiene el nombre del grupo en String
        for (Grupos g : listaGrupos
        ) {
            listaLimpia.add(g.getNombreGrupo());
        }

        //Adapter
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaLimpia);
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

    public void volverAlumno() {
        Intent alumnoSeleccionado = new Intent(this, AlumnoActivity.class);
        alumnoSeleccionado.putExtra("alumnoSeleccionado", new Gson().toJson(alumno));
        alumnoSeleccionado.putExtra("grupo", grupo);
        startActivity(alumnoSeleccionado);
    }

    @Override
    public void onBackPressed() {
        if (getNoCambioCampos()) {

            volverAlumno();
        } else {
            dialogoBack();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        cargaActivity();
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        //Se establece la fecha actual del objeto alumno para que sea mostrada al abrir el calendario
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
            try {
                c.setTime(Objects.requireNonNull(sdf.parse(alumno.getFecha_nac())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }


    }


}