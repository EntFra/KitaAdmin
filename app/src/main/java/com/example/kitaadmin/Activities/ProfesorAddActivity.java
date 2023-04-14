package com.example.kitaadmin.Activities;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityProfesorAddBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity profesor add, permite añadir un profesor nuevo
 */
public class ProfesorAddActivity extends AppCompatActivity {

    ActivityProfesorAddBinding binding;
    Profesores profesor = new Profesores();
    String grupo;
    Usuarios usuarioProfesor = new Usuarios();
    ApiService apiService;
    List<Usuarios> listaUsuarios = new ArrayList<>();
    List<Profesores> listaProfesores = new ArrayList<>();
    List<String> nombresProfesores = new ArrayList<>();

    String dniNew = "";
    String fecha_altaNew = "";
    String direccionNew = "";
    String grupoNew = "";
    String fecha_nacNew = "";
    String emailNew = "";
    int telefonoNew;
    //Variable que almacena si hubo cambios en los campos para realizar update
    boolean noCambioCampos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargaActivity();

    }

    private void cargaActivity() {

        setContentView(R.layout.activity_profesor_edit);

        binding = ActivityProfesorAddBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //Recoge el profesor seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            grupo = extras.getString("grupoSeleccionado");
        }
        getprofesores();
        //Lanza el selector de calendario con la fecha guardada en la base de datos
        binding.editFechaAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.editFechaAlt);
            }
        });
        binding.editFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(binding.editFechaNac);
            }
        });
        spinnerGrupos();
        noCambioCampos = true;

        //Se compara el grupo obtenido del objeto profesor con la lista de grupos para marcar la selección en el spinner
        for (Grupos g : GruposActivity.listaGrupos()
        ) {
            if (g.getNombreGrupo().equals(grupo)) {
                binding.spinnerGrupo.setSelection(GruposActivity.listaGrupos().indexOf(g));
            }
        }


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

        binding.editFechaNac.addTextChangedListener(tw);
        binding.editDNI.addTextChangedListener(tw);
        binding.editFechaAlt.addTextChangedListener(tw);
        binding.editDirecc.addTextChangedListener(tw);
        binding.editEmail.addTextChangedListener(tw);
        binding.editTextTelf.addTextChangedListener(tw);

        binding.buttonAddProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProfesor();
            }
        });
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
    //Método que valida el formato del DNI
    public boolean isValidDNI(String dni) {
        Boolean isValid = false;
        String dniString = "[0-9]{8}[A-Za-z]{1}";
        if (dni.matches(dniString)) {
            isValid = true;

        } else if (!dni.matches(dniString)) {
            Toast.makeText(getApplicationContext(), R.string.formatoDNI, Toast.LENGTH_SHORT).show();

        }


        return isValid;
    }
    //Método que obtiene los campos actualizados
    private void getCamposActualizados() {
        dniNew = binding.editDNI.getText().toString();
        fecha_altaNew = (binding.editFechaAlt.getText().toString());
        direccionNew = binding.editDirecc.getText().toString();
        grupoNew = binding.spinnerGrupo.getSelectedItem().toString();
        fecha_nacNew = (binding.editFechaNac.getText().toString());
        emailNew = binding.editEmail.getText().toString();
        try {
            telefonoNew = Integer.parseInt(binding.editTextTelf.getText().toString());
        } catch (Exception e) {
            System.err.println(e);
        }

    }
    //Método para añadir un profesor
    public void addProfesor() {

        if (isValidDNI(binding.editDNI.getText().toString().trim())) {

            getCamposActualizados();

            usuarioProfesor.setEmail(emailNew);
            usuarioProfesor.setTelefono(telefonoNew);

            for (Usuarios u : listaUsuarios)
                if (u.getNombre().equals(binding.spinnerNombreProfesor.getSelectedItem().toString())) {
                    profesor.setId(u.getUsuarios_id());

                }

            profesor.setDni(dniNew);
            profesor.setFecha_alta(fecha_altaNew);
            profesor.setFecha_nac(fecha_nacNew);
            profesor.setDireccion(direccionNew);
            profesor.setNombre_grupo(grupoNew);

            if (compruebaCampos()) {
                ApiService apiService = Network.getInstance().create(ApiService.class);
                Call<Profesores> call = apiService.addProfesor(profesor);
                call.enqueue(new Callback<Profesores>() {
                    @Override
                    public void onResponse(Call<Profesores> call, Response<Profesores> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProfesorAddActivity.this, R.string.profesorCreado, Toast.LENGTH_SHORT).show();
                            grupo = grupoNew;
                            vueltaListaProfesores();
                        } else {
                            Toast.makeText(ProfesorAddActivity.this, R.string.errorCreaProfesor, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Profesores> call, Throwable t) {

                    }
                });
            }
        }


    }

    private boolean compruebaCampos() {

        getCamposActualizados();

        if (dniNew.isEmpty() || fecha_altaNew.isEmpty() || grupoNew.isEmpty()) {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle(R.string.campoObligatorio)
                    .setMessage(R.string.camposNecesarios)
                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .show();
            alertDialog.setCancelable(false);
            return false;
        }

        return true;


    }

    private boolean setCambioCampos(boolean isChanged) {

        return noCambioCampos = isChanged;
    }

    private boolean getNoCambioCampos() {
        return noCambioCampos;
    }
    //Método que muestra los nombres de los grupos en el spinner
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

        binding.spinnerGrupo.setAdapter(arrayAdapter);
    }
    //Método que muestra los nombres de los profesores en el spinner
    private void spinnerNombreProfesores() {
        if (nombresProfesores.size() != 0) {
            if (binding.tableAddProfesor.getVisibility() == View.GONE) {
                binding.tableAddProfesor.setVisibility(View.VISIBLE);
                binding.buttonAddProfesor.setVisibility(View.VISIBLE);

            }
            //Adapter
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresProfesores);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            binding.spinnerNombreProfesor.setAdapter(arrayAdapter);
        } else {
            binding.tableAddProfesor.setVisibility(View.GONE);
            binding.sinProfesores.setVisibility(View.VISIBLE);
            binding.buttonAddProfesor.setVisibility(View.GONE);
        }


    }
    //Método que obtiene los nombres de los profesores
    private void getprofesores() {
        //Se crea una instancia de llamada a la API
        apiService = Network.getInstance().create(ApiService.class);
        //Se llama al servicio que obtiene los usuarios
        Call<List<Usuarios>> call = apiService.getUsuarios();
        call.enqueue(new Callback<List<Usuarios>>() {
            @Override
            public void onResponse(Call<List<Usuarios>> call, Response<List<Usuarios>> response) {
                listaUsuarios = response.body();
            }

            @Override
            public void onFailure(Call<List<Usuarios>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

        //Se llama al servicio que obtiene los usuarios
        Call<List<Profesores>> call2 = apiService.getProfesoresAll();
        call2.enqueue(new Callback<List<Profesores>>() {
            @Override
            public void onResponse(Call<List<Profesores>> call2, Response<List<Profesores>> response) {
                listaProfesores = response.body();
            }

            @Override
            public void onFailure(Call<List<Profesores>> call2, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });


        Timer timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            public void run() {
                //S obtiene el nombre de los profesores en String, comprobando primero los usuarios que aún no tienen un profesor creado
                for (Usuarios u : listaUsuarios) {
                    if (u.getRol().equalsIgnoreCase("profesor") ||
                            u.getRol().equalsIgnoreCase("profesor_admin")) {
                        nombresProfesores.add(u.getNombre());

                        for (Profesores p : listaProfesores) {
                            if (u.getUsuarios_id() == (p.getUsuario().getUsuarios_id())) {
                                nombresProfesores.remove(u.getNombre());
                            }
                        }
                    }
                }
                timer1.cancel();
            }
        }, 100);

        //se retrasa la llamada al spinner en la UI para que de tiempo a recibir la información antes de su llenado
        Timer timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerNombreProfesores();
                    }
                });

                timer2.cancel();
            }
        }, 500);


    }

    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(ProfesorAddActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        vueltaListaProfesores();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }

    //Método que regresa a la lista
    private void vueltaListaProfesores() {
        Intent intent = new Intent(this, ListaProfesoresActivity.class);
        intent.putExtra("grupoSeleccionado", binding.spinnerGrupo.getSelectedItem().toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (getNoCambioCampos()) {
            vueltaListaProfesores();
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
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }


    }
}