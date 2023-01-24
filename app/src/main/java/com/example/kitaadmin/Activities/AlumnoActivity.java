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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity Alumno, usado para mostrar la información del alumno seleccionado. Se permite el acceso a la edición del alumno, borrado,
 * así como a la información correspondiente a los padres del alumno seleccionado y sus informes correspondientes
 */

public class AlumnoActivity extends AppCompatActivity {

    ApiService apiService;
    String alumnoSeleccionado;
    Alumnos alumno;
    String grupo;
    private ActivityAlumnoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarActivity();
    }

    private void cargarActivity() {
        binding = ActivityAlumnoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Recoge el alumno seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            alumnoSeleccionado = extras.getString("alumnoSeleccionado");
            grupo = extras.getString("grupo");

            alumno = new Gson().fromJson(alumnoSeleccionado, Alumnos.class);

            recuperaInfoAlumno();
        }

        binding.btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar(alumno);

            }
        });
    }

    private void recuperaInfoAlumno() {

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
        if(LoginActivity.getRol().equals("padre") || LoginActivity.getRol().equals("profesor")){
            binding.btnDelet.setVisibility(View.GONE);
            binding.btnEdit.setVisibility(View.GONE);
        }
    }

    //Método público para borrar el alumno actual de la base de datos
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
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void editarAlumno(View view) {
        Intent intent = new Intent(this, AlumnoEditActivity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        intent.putExtra("grupo", grupo);
        startActivity(intent);
    }

    public void verPadres(View view) {
        Intent intent = new Intent(this, PadresAlumnoAcitvity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        startActivity(intent);
    }

    public void informes(View view) {
        Intent intent = new Intent(this, InformeActivity.class);
        intent.putExtra("alumnoSeleccionado", alumnoSeleccionado);
        startActivity(intent);
    }

    //Método que regresa a la lista
    private void vueltaListaAlumnos() {
        Intent intent = new Intent(this, ListaAlumnosActivity.class);
        intent.putExtra("grupoSeleccionado", alumno.getNombre_grupo());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        vueltaListaAlumnos();
    }


    @Override
    public void onRestart() {
        super.onRestart();
        cargarActivity();
    }


}