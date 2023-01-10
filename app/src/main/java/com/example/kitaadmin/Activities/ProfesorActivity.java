package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityProfesorBinding;
import com.google.gson.Gson;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity profesor, muestra la información del profesor seleccionado permitiendo su edición o borrado
 */
public class ProfesorActivity extends AppCompatActivity {

    ApiService apiService;
    String profesorSeleccionado;
    Profesores profesor;
    String grupo;
    private ActivityProfesorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarActivity();
    }

    private void cargarActivity() {
        binding = ActivityProfesorBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Recoge el profesor seleccionado
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profesorSeleccionado = extras.getString("profesorSeleccionado");
            grupo = extras.getString("grupo");
        }
        profesor = new Gson().fromJson(profesorSeleccionado, Profesores.class);

        try {
            recuperaInfoProfesor();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        binding.btnDelet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrar(profesor);

            }
        });
    }


    private void recuperaInfoProfesor() throws ParseException {

        binding.textNombreProf.setText(profesor.getUsuario().getNombre());
        binding.textFechaNac.setText(profesor.getFecha_nac());
        binding.textTelf.setText(Integer.toString(profesor.getUsuario().getTelefono()));
        binding.textEmail.setText(profesor.getUsuario().getEmail());
        binding.textDirecc.setText(profesor.getDireccion());
        binding.textDNI.setText(profesor.getDni());
        binding.textFechaAlta.setText(profesor.getFecha_alta());
        binding.textGrupo.setText(grupo);

    }

    //Método público para borrar el profesor actual de la base de datos
    public void borrar(Profesores profesor) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.borrarProfesor)
                .setMessage(R.string.preguntaBorraProfesor)
                .setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Se crea una instancia de llamada a la API
                        apiService = Network.getInstance().create(ApiService.class);
                        //Se llama al servicio que obtiene los profesores
                        Call<Void> call = apiService.deleteUsuarios(profesor.getUsuario().getUsuarios_id());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(ProfesorActivity.this, R.string.profesorBorrado, Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Log.e("Error", t.getMessage());
                            }
                        });
                        vueltaListaProfesores();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void editarProfesor(View view) {
        Intent intent = new Intent(this, ProfesorEditActivity.class);
        intent.putExtra("profesorSeleccionado", profesorSeleccionado);
        startActivity(intent);
    }


    //Método que regresa a la lista
    private void vueltaListaProfesores() {
        Intent intent = new Intent(this, ListaProfesoresActivity.class);
        intent.putExtra("grupoSeleccionado", profesor.getNombre_grupo());
        startActivity(intent);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        cargarActivity();
    }

    @Override
    public void onBackPressed() {
        vueltaListaProfesores();
    }
}
