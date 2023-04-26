package com.example.kitaadmin.Activities;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableStringBuilder;
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
import com.example.kitaadmin.databinding.ActivityAlumnoAddBinding;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumnoAddActivity extends AppCompatActivity {

    static Alumnos alumno = new Alumnos();
    static Editable editable = new SpannableStringBuilder(DateTimeFormatter.ofPattern("dd-MM-yyyy").withZone(ZoneId.of("UTC")).format(Instant.now()));
    String grupo;
    boolean noCambioCampos;
    private ActivityAlumnoAddBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargaActivity();

        binding.buttonAddAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAlumno();
            }
        });

    }

    //Método que carga la actividad de edición de un alumno
    private void cargaActivity() {
        setContentView(R.layout.activity_alumno_edit);

        binding = ActivityAlumnoAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle extras = getIntent().getExtras();
        grupo = extras.getString("grupoSeleccionado");
        //Se cargan los grupos disponibles en el spinner
        spinnerGrupos();
        //Se añade un listener para el botón de selección de fecha de nacimiento
        binding.editTextFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.editTextFechaNac);
            }
        });

        noCambioCampos = true;
        //Se crea un TextWatcher para detectar cambios en los campos
        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Se establece la variable cambioCampos como falsa para indicar que se han realizado cambios
                setCambioCampos(false);

            }
        };

        binding.editObservaciones.addTextChangedListener(tw);
        binding.editTextAlergia.addTextChangedListener(tw);
        binding.editTextFechaNac.addTextChangedListener(tw);
        binding.textNombreAlumno.addTextChangedListener(tw);

    }

    //Muestra el diálogo de selección de fecha
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

    private boolean compruebaCampos() {
        return binding.textNombreAlumno.getText().toString().isEmpty() ||
                binding.editTextFechaNac.getText().toString().isEmpty();
    }

    //Método para añadir un alumno
    public void addAlumno() {

        if (compruebaCampos()) {
            Toast.makeText(AlumnoAddActivity.this, R.string.campoObligatorio, Toast.LENGTH_SHORT).show();
            return;
        }


        alumno.setNombre(binding.textNombreAlumno.getText().toString());
        alumno.setAlergias(binding.editTextAlergia.getText().toString());
        alumno.setFecha_nac(binding.editTextFechaNac.getText().toString());
        alumno.setComedor(binding.switchComedor.isChecked());
        alumno.setAuto_fotos(binding.switchFotos.isChecked());
        alumno.setAuto_salidas(binding.switchSalidas.isChecked());
        alumno.setNombre_grupo(binding.spinnerGrupoAlumno.getSelectedItem().toString());
        alumno.setObservaciones(binding.editObservaciones.getText().toString());


        ApiService apiService = Network.getInstance().create(ApiService.class);
        Call<Alumnos> call = apiService.addAlumno(alumno);
        call.enqueue(new Callback<Alumnos>() {
            @Override
            public void onResponse(Call<Alumnos> call, Response<Alumnos> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AlumnoAddActivity.this, R.string.alumnoCreado, Toast.LENGTH_SHORT).show();
                    grupo = binding.spinnerGrupoAlumno.getSelectedItem().toString();
                    volverLista();
                } else {
                    Toast.makeText(AlumnoAddActivity.this, R.string.errorCreaAlumno, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Alumnos> call, Throwable t) {

            }
        });
    }

    //Carga la lista de grupos en el spinner
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

        for (Grupos g : GruposActivity.listaGrupos()
        ) {
            if (g.getNombreGrupo().equals(grupo)) {
                binding.spinnerGrupoAlumno.setSelection(GruposActivity.listaGrupos().indexOf(g));
            }
        }


    }
    //Dialogo de confirmación de salida
    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(AlumnoAddActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverLista();
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
    //Método para volver a la lista de alumnos
    public void volverLista() {
        Intent listaAlumnos = new Intent(this, ListaAlumnosActivity.class);
        listaAlumnos.putExtra("grupoSeleccionado", grupo);
        startActivity(listaAlumnos);
    }
    //Método para comprobar si se han realizado cambios en los campos antes de salir
    @Override
    public void onBackPressed() {
        if (getNoCambioCampos()) {
            volverLista();
        } else {
            dialogoBack();
        }


    }

    @Override
    public void onRestart() {
        super.onRestart();
        cargaActivity();
    }

    //Muestra un fragment para seleccionar fechas
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
                c.setTime(Objects.requireNonNull(sdf.parse(String.valueOf(editable))));
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