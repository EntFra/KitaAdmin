package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.ActivityUsuarioAddEditBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity usuario add, permite añadir un usuario
 */
public class UsuarioAddActivity extends AppCompatActivity {

    private final ArrayList<String> LISTAROLES = Utils.getRoles();
    private final ArrayList<String> listaNombreAlumnos = new ArrayList<>();
    private final ApiService apiService = Network.getInstance().create(ApiService.class);
    private List<Alumnos> listaAlumnos;
    private ActivityUsuarioAddEditBinding binding;
    private ArrayAdapter<String> arrayAdapterAlumnos;
    private Usuarios usuario = new Usuarios();
    private Padres padre = new Padres();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAlumnos();
        binding = ActivityUsuarioAddEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnActualizar.setVisibility(View.GONE);
        binding.btnActualizar.setClickable(false);

        //Adapter para el spinner de roles
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LISTAROLES);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRol.setAdapter(arrayAdapter);

        //Listener para mostrar el spinner de alumnos sólo en caso de estar seleccionado el rol "padre"
        binding.spinnerRol.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                if (!parent.getItemAtPosition(position).toString().equals("padre")) {
                    binding.tableAlumno.setVisibility(View.GONE);

                } else {
                    binding.tableAlumno.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Adapter para mostrar la lista en el spinner
        arrayAdapterAlumnos = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaNombreAlumnos);
        arrayAdapterAlumnos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerAlumno.setAdapter(arrayAdapterAlumnos);
    }

    //Método que obtiene los alumnos
    private void getAlumnos() {

        //Se llama al servicio que obtiene los alumnos
        Call<List<Alumnos>> call = apiService.getAlumnosAll();
        call.enqueue(new Callback<List<Alumnos>>() {
            @Override
            public void onResponse(Call<List<Alumnos>> call, Response<List<Alumnos>> response) {
                listaAlumnos = response.body();
                assert listaAlumnos != null;
                for (Alumnos a : listaAlumnos
                ) {
                    listaNombreAlumnos.add(a.getNombre());
                }
                //Una vez llena la lista se notifica al adapter
                arrayAdapterAlumnos.notifyDataSetChanged();
            }


            @Override
            public void onFailure(Call<List<Alumnos>> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }

    private boolean compruebaCampos() {
        return binding.editNombre.getText().toString().isEmpty() ||
                binding.editNombreUsuario.getText().toString().isEmpty() ||
                binding.editContrasenia.getText().toString().isEmpty() ||
                binding.editTelefono.getText().toString().isEmpty() ||
                binding.editEmail.getText().toString().isEmpty();
    }

    //Método para crear un usuario
    public void addUsuario(View view) {

        if (compruebaCampos()) {
            Toast.makeText(UsuarioAddActivity.this, R.string.campoObligatorio, Toast.LENGTH_SHORT).show();
            return;
        }
        usuario.setNombre_usuario(binding.editNombreUsuario.getText().toString());
        usuario.setNombre(binding.editNombre.getText().toString());
        usuario.setContrasenia(binding.editContrasenia.getText().toString());
        usuario.setEmail(binding.editEmail.getText().toString());
        try {
            usuario.setTelefono(Integer.parseInt(binding.editTelefono.getText().toString()));
        } catch (Exception e) {

        }

        usuario.setRol(binding.spinnerRol.getSelectedItem().toString());

        if (binding.spinnerRol.getSelectedItem().toString().equals("padre")) {

            for (Alumnos a : listaAlumnos
            ) {
                //Se busca en la lista el alumno seleccionado para establecer la relación en la BD
                if (a.getNombre().equals(binding.spinnerAlumno.getSelectedItem().toString())) {
                    padre.setAlumnoId(a.getAlumno_id());
                }
            }
            padre.setUsuariosId(usuario.getUsuarios_id());
            Call<Padres> call = apiService.addPadres(padre);
            call.enqueue(new Callback<Padres>() {
                @Override
                public void onResponse(Call<Padres> call, Response<Padres> response) {

                }

                @Override
                public void onFailure(Call<Padres> call, Throwable t) {

                }
            });
        }

        Call<Usuarios> call = apiService.addUsuario(usuario);
        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UsuarioAddActivity.this, R.string.usuarioCreado, Toast.LENGTH_SHORT).show();
                    volverUsuariosMenu();
                } else {
                    Toast.makeText(UsuarioAddActivity.this, R.string.errorCreaUsuario, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });

    }

    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(UsuarioAddActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverUsuariosMenu();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }

    private boolean cambioCampos() {
        boolean vacio = false;
        if (binding.editNombre.getText().toString().isEmpty() &&
                binding.editNombreUsuario.getText().toString().isEmpty() &&
                binding.editContrasenia.getText().toString().isEmpty() &&
                binding.editTelefono.getText().toString().isEmpty() &&
                binding.editEmail.getText().toString().isEmpty()) {
            vacio = true;
        }
        return vacio;
    }

    @Override
    public void onBackPressed() {
        if (cambioCampos()) {
            volverUsuariosMenu();
        } else {
            dialogoBack();
        }
    }
    //Método para volver al menú de usuarios
    private void volverUsuariosMenu() {
        Intent vueltaUsuarioMenu = new Intent(this, UsuariosActivity.class);
        startActivity(vueltaUsuarioMenu);
    }

}
