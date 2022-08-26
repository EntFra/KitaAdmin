package com.example.kitaadmin;

import static com.example.kitaadmin.Utils.DatePickerFragment.twoDigits;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.DatePickerFragment;
import com.example.kitaadmin.databinding.ActivityProfesorEditBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfesorEditActivity extends AppCompatActivity {

    private ActivityProfesorEditBinding binding;
    Profesores profesor;
    String grupo;
    String profesorSeleccionado;
    CalendarView calendarView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cargaActivity();

    }

    private void cargaActivity(){
        setContentView(R.layout.activity_profesor_edit);

        binding = ActivityProfesorEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //Recoge el profesor seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profesorSeleccionado = extras.getString("profesorSeleccionado");
            profesor = new Gson().fromJson(profesorSeleccionado, Profesores.class);
            grupo = extras.getString("grupo");


            usuarioProfesor = profesor.getUsuario();

            dniOld = profesor.getDni();
            fecha_nacOld = profesor.getFecha_nac();
            fecha_altaOld = profesor.getFecha_nac();
            direccionOld = profesor.getDireccion();
            grupoOld = profesor.getNombre_grupo();
            emailOld = usuarioProfesor.getEmail();
            telefonoOld = usuarioProfesor.getTelefono();
        }

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

        recuperaInfoProfesor();
    }


    private void recuperaInfoProfesor() {

        binding.textTituloProfesor.setText(profesor.getUsuario().getNombre());
        binding.editFechaNac.setText(fecha_nacOld);
        binding.editTextTelf.setText(String.valueOf(telefonoOld));
        binding.editEmail.setText(emailOld);
        binding.editDirecc.setText(direccionOld);
        binding.editDNI.setText(dniOld);
        binding.editFechaAlt.setText(fecha_altaOld);
        binding.editGrupo.setText(grupoOld);

    }


    private void showDatePickerDialog(final EditText editText) {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = twoDigits(day) + "-" +   twoDigits(month + 1) + "-" + year ;
                editText.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void getCamposActualizados(){
        dniNew = binding.editDNI.getText().toString();
        fecha_altaNew = (binding.editFechaAlt.getText().toString());
        direccionNew = binding.editDirecc.getText().toString();
        grupoNew = binding.editGrupo.getText().toString();
        fecha_nacNew = (binding.editFechaNac.getText().toString());
        emailNew = binding.editEmail.getText().toString();
        telefonoNew = Integer.parseInt(binding.editTextTelf.getText().toString());
    }

    public void updateProfesor(View view) {

        getCamposActualizados();

        usuarioProfesor.setEmail(emailNew);
        usuarioProfesor.setTelefono(telefonoNew);

        profesor = new Profesores(dniNew, fecha_altaNew,fecha_nacNew, direccionNew, grupoNew, usuarioProfesor);

        if(compruebaCampos()){
            ApiService apiService = Network.getInstance().create(ApiService.class);
            Call<Profesores> call = apiService.updateProfesor(profesor);
            call.enqueue(new Callback<Profesores>() {
                @Override
                public void onResponse(Call<Profesores> call, Response<Profesores> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProfesorEditActivity.this, R.string.profesorActualizado,Toast.LENGTH_SHORT).show();
                        volverProfesor();
                    } else {
                        Toast.makeText(ProfesorEditActivity.this, R.string.errorActualizaProfesor,Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Profesores> call, Throwable t) {

                }
            });
        }



    }

    private boolean compruebaCampos(){

        getCamposActualizados();

        if(dniNew.isEmpty() || fecha_altaNew.isEmpty() || grupoNew.isEmpty()) {
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
                    }else if (!dniOld.equals(dniNew) || !direccionOld.equals(direccionNew) || !fecha_altaOld.equals(fecha_altaNew)
                    || fecha_nacOld.equals(fecha_nacNew) || !grupoOld.equals(grupoNew) || !emailOld.equals(emailNew)
                    || telefonoOld != telefonoNew) {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
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
                alertDialog.setCancelable(false);
            return false;
            }

        return true;



        }


    public void volverProfesor (){
        Intent profesorSeleccionado = new Intent(this, ProfesorActivity.class);
        profesorSeleccionado.putExtra("profesorSeleccionado", new Gson().toJson(profesor));
        profesorSeleccionado.putExtra("grupo",grupoNew);
        startActivity(profesorSeleccionado);
    }

    @Override
    public void onBackPressed(){
        if(compruebaCampos()){
            volverProfesor();
        }

    }


    @Override
    public void onRestart() {
        super.onRestart();
        cargaActivity();
        //When BACK BUTTON is pressed, the activity on the stack is restarted
        //Do what you want on the refresh procedure here
    }
}