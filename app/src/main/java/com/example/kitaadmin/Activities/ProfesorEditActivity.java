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

import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityProfesorEditBinding;
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
 * Clase que maneja el activity profesor edit, muestra la información contenida permitiendo su edición
 */
public class ProfesorEditActivity extends AppCompatActivity {

    static String fechaView;
    ActivityProfesorEditBinding binding;
    Profesores profesor;
    String grupo;
    String profesorSeleccionado;
    Usuarios usuarioProfesor;
    String dniOld;
    String fecha_altaOld;
    String direccionOld;
    String grupoOld;
    String fecha_nacOld;
    String emailOld;
    int telefonoOld;

    String dniNew;
    String fecha_altaNew;
    String direccionNew;
    String grupoNew;
    String fecha_nacNew;
    String emailNew;
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

        binding = ActivityProfesorEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //Recoge el profesor seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profesorSeleccionado = extras.getString("profesorSeleccionado");
            profesor = new Gson().fromJson(profesorSeleccionado, Profesores.class);
            grupo = profesor.getNombre_grupo();


            usuarioProfesor = profesor.getUsuario();

            dniOld = profesor.getDni();
            fecha_nacOld = profesor.getFecha_nac();
            fecha_altaOld = profesor.getFecha_alta();
            direccionOld = profesor.getDireccion();
            grupoOld = profesor.getNombre_grupo();
            emailOld = usuarioProfesor.getEmail();
            telefonoOld = usuarioProfesor.getTelefono();
        }
        //Lanza el selector de calendario con la fecha guardada en la base de datos
        binding.editFechaAlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFechaView(profesor.getFecha_alta());
                showDatePickerDialog(binding.editFechaAlt, getFechaView());
            }
        });
        binding.editFechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFechaView(profesor.getFecha_nac());
                showDatePickerDialog(binding.editFechaNac, getFechaView());
            }
        });
        spinnerGrupos();
        recuperaInfoProfesor();
        noCambioCampos = true;


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
    }

    private String getFechaView() {
        return fechaView;
    }

    //Establece el valor de la variable para arrancar la view del calendario con la fecha guardada en la bd
    private void setFechaView(String fecha) {
        fechaView = fecha;
    }

    private void recuperaInfoProfesor() {

        binding.textTituloProfesor.setText(profesor.getUsuario().getNombre());
        binding.editFechaNac.setText(fecha_nacOld);
        binding.editTextTelf.setText(String.valueOf(telefonoOld));
        binding.editEmail.setText(emailOld);
        binding.editDirecc.setText(direccionOld);
        binding.editDNI.setText(dniOld);
        binding.editFechaAlt.setText(fecha_altaOld);
        //Se compara el grupo obtenido del objeto profesor con la lista de grupos para marcar la selección en el spinner
        for (Grupos g : GruposActivity.listaGrupos()
        ) {
            if (g.getNombreGrupo().equals(grupoOld)) {
                binding.spinnerGrupo.setSelection(GruposActivity.listaGrupos().indexOf(g));
            }
        }


    }


    private void showDatePickerDialog(final EditText editText, String fechaView) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private boolean isValidDNI(String dni) {
        Boolean isValid = false;
        String dniString = "[0-9]{8}[A-Za-z]{1}";
        if (dni.matches(dniString)) {
            isValid = true;

        } else if (!dni.matches(dniString)) {
            Toast.makeText(getApplicationContext(), R.string.formatoDNI, Toast.LENGTH_SHORT).show();

        }


        return isValid;
    }

    private void getCamposActualizados() {
        dniNew = binding.editDNI.getText().toString();
        fecha_altaNew = (binding.editFechaAlt.getText().toString());
        direccionNew = binding.editDirecc.getText().toString();
        grupoNew = binding.spinnerGrupo.getSelectedItem().toString();
        fecha_nacNew = (binding.editFechaNac.getText().toString());
        emailNew = binding.editEmail.getText().toString();
        telefonoNew = Integer.parseInt(binding.editTextTelf.getText().toString());
    }

    public void updateProfesor(View view) {

        if (isValidDNI(binding.editDNI.getText().toString().trim())) {

            getCamposActualizados();

            usuarioProfesor.setEmail(emailNew);
            usuarioProfesor.setTelefono(telefonoNew);

            profesor.setDni(dniNew);
            profesor.setFecha_alta(fecha_altaNew);
            profesor.setFecha_nac(fecha_nacNew);
            profesor.setDireccion(direccionNew);
            profesor.setNombre_grupo(grupoNew);

            if (compruebaCampos()) {
                ApiService apiService = Network.getInstance().create(ApiService.class);
                Call<Profesores> call = apiService.updateProfesor(profesor);
                call.enqueue(new Callback<Profesores>() {
                    @Override
                    public void onResponse(Call<Profesores> call, Response<Profesores> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProfesorEditActivity.this, R.string.profesorActualizado, Toast.LENGTH_SHORT).show();
                            grupo = grupoNew;
                            volverProfesor();
                        } else {
                            Toast.makeText(ProfesorEditActivity.this, R.string.errorActualizaProfesor, Toast.LENGTH_SHORT).show();
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

    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(ProfesorEditActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverProfesor();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }

    public void volverProfesor() {
        Intent profesorSeleccionado = new Intent(this, ProfesorActivity.class);
        profesorSeleccionado.putExtra("profesorSeleccionado", new Gson().toJson(profesor));
        profesorSeleccionado.putExtra("grupo", grupo);
        startActivity(profesorSeleccionado);
    }

    @Override
    public void onBackPressed() {
        if (compruebaCampos() && getNoCambioCampos()) {

            volverProfesor();
        } else if (compruebaCampos() && !getNoCambioCampos()) {
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
                c.setTime(Objects.requireNonNull(sdf.parse(fechaView)));
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