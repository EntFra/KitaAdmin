package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityAlumnoBinding;
import com.google.gson.Gson;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Esta clase representa la actividad de detalle de un alumno en la aplicación.
 * Se encarga de mostrar la información del alumno seleccionado, permitir su edición, borrado,
 * acceso a la lista de padres y acceso a la sección de informes.
 */

public class AlumnoActivity extends AppCompatActivity {

    ApiService apiService;
    public String alumnoSeleccionado;
    public Alumnos alumno;
    public String grupo;
    public ActivityAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlumnoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cargarActivity();
    }

    /**
     * Método que carga la información del alumno seleccionado y actualiza la vista.
     * También se encarga de desactivar la posibilidad de modificar los switches y ocultar los botones
     * de edición y borrado según el rol del usuario que inició sesión.
     */
    public void cargarActivity() {

        //Recoge el alumno seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumnoSeleccionado");
            grupo = extras.getString("grupo");

            alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);

            recuperaInfoAlumno();
        }


        if(alumno!=null){
            binding.btnDelet.setOnClickListener(v -> borrar(alumno));
        }

    }

    /**
     * Método que actualiza la vista con la información del alumno seleccionado.
     * También desactiva la posibilidad de modificar los switches y oculta los botones
     * de edición y borrado según el rol del usuario que inició sesión.
     */
    void recuperaInfoAlumno() {

        binding.textFechaNac.setText(alumno.getFecha_nac());
        binding.textNombreAlumno.setText(alumno.getNombre());
        binding.textAlergia.setText(alumno.getAlergias());
        binding.textObservaciones.setText(alumno.getObservaciones());
        binding.switchFotos.setChecked(alumno.isAuto_fotos());
        binding.switchSalidas.setChecked(alumno.isAuto_salidas());
        binding.switchComedor.setChecked(alumno.isComedor());
        //Desactivamos la posibilidad de alterar los switches
        binding.switchComedor.setClickable(false);
        binding.switchFotos.setClickable(false);
        binding.switchSalidas.setClickable(false);
        //Desactivamos botones según Rol
        if (LoginActivity.getRol() != null && LoginActivity.getRol().equals("padre") || Objects.equals(LoginActivity.getRol(), "profesor")) {
            binding.btnDelet.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.GONE);
        }
    }

    //Método que borra el alumno seleccionado
    public void borrar(Alumnos alumno) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.borrarAlumno)
                .setMessage(R.string.confirmaBorraAlumno)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los alumnos
                        Call<Void> call = apiService.deleteAlumno(alumno.getAlumno_id());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(AlumnoActivity.this, R.string.alumnoBorrado, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                        vueltaListaAlumnos();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }
    //Método que abre la actividad de edición de alumnos
    public void editarAlumno(View view) {
        Intent intent = new Intent(this, AlumnoEditActivity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        intent.putExtra("grupo", grupo);
        startActivity(intent);
    }

    //Método que abre la actividad de padres
    public void verPadres(View view) {
        Intent intent = new Intent(this, PadresAlumnoActivity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        startActivity(intent);
    }

    //Método que abre la actividad de informes
    public void informes(View view) {
        Intent intent = new Intent(this, InformeActivity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        startActivity(intent);
    }

    //Método que regresa a la lista
    public void vueltaListaAlumnos() {
        Intent intent = new Intent(this, ListaAlumnosActivity.class);
        intent.putExtra("grupoSeleccionado", alumno.getNombre_grupo());
        startActivity(intent);
    }

    //Método que regresa a la lista al pulsar el botón de atrás
    @Override
    public void onBackPressed() {
        vueltaListaAlumnos();
    }

    //Método que recarga la actividad al pulsar el botón de atrás
    @Override
    public void onRestart() {
        super.onRestart();
        cargarActivity();
    }



}