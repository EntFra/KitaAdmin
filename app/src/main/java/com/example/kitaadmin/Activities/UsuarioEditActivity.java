package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity usuario edit, permite editar la información de un usuario dado
 */
public class UsuarioEditActivity extends AppCompatActivity {

    private final ArrayList<String> LISTAROLES = Utils.getRoles();
    private final ArrayList<String> listaNombreAlumnos = new ArrayList<>();
    private final ApiService apiService = Network.getInstance().create(ApiService.class);
    private ActivityUsuarioAddEditBinding binding;
    private Usuarios usuario;
    private List<Alumnos> listaAlumnos;
    private ArrayAdapter<String> arrayAdapterAlumnos;
    private Padres padre = new Padres();
    private Alumnos alumno = new Alumnos();
    private boolean cambioCampos = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        usuario = new Gson().fromJson(extras.getString("Usuario"), Usuarios.class);
        getAlumnos();

        binding = ActivityUsuarioAddEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.btnAdd.setVisibility(View.GONE);
        binding.btnAdd.setClickable(false);
        binding.editNombre.setText(usuario.getNombre());
        binding.editNombreUsuario.setText(usuario.getNombre_usuario());
        binding.editContrasenia.setText(usuario.getContrasenia());
        binding.editTelefono.setText(String.valueOf(usuario.getTelefono()));
        binding.editEmail.setText(usuario.getEmail());
        //Adapter para el spinner de roles
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, LISTAROLES);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRol.setAdapter(arrayAdapter);
        //Se idenetifica el rol actual del usuario para mostrarlo en el spinner
        for (String g : LISTAROLES
        ) {
            if (g.equals(usuario.getRol())) {
                binding.spinnerRol.setSelection(LISTAROLES.indexOf(g));
            }
        }
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

        if (usuario.getRol().equals("padre")) {
            try {
                //Se obtiene el padre
                getPadreAlumno();

                //Se obtiene el alumno correspondiente en caso de que el rol fuese padre
                Timer timer1 = new Timer();
                timer1.schedule(new TimerTask() {
                    public void run() {
                        getAlumno();
                        timer1.cancel();
                    }
                }, 100);

                //Se busca en la lista de alumnos para mostrar el alumno relacionado con el usuario padre actual
                Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    public void run() {
                        for (Alumnos a : listaAlumnos
                        ) {
                            //Se busca en la lista el alumno relacionado con el padre para mostrarlo en el spinner
                            if (a.getNombre().equals(alumno.getNombre())) {
                                //Actualiza la UI, necesario ya que sólo el Thread principal puede actualizarla
                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        binding.spinnerAlumno.setSelection(listaAlumnos.indexOf(a));
                                        arrayAdapterAlumnos.notifyDataSetChanged();

                                    }
                                });

                            }
                        }
                        timer2.cancel();
                    }
                }, 700);

            } catch (Exception e) {
                System.out.println(e);
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

                cambioCampos = true;

            }
        };

        binding.editNombre.addTextChangedListener(tw);
        binding.editNombreUsuario.addTextChangedListener(tw);
        binding.editContrasenia.addTextChangedListener(tw);
        binding.editEmail.addTextChangedListener(tw);
        binding.editTelefono.addTextChangedListener(tw);


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

    //Método que obtiene el padre
    private void getPadreAlumno() {
        //Se llama al servicio que obtiene el de alumno segun usuarioId del padre
        Call<Padres> call = apiService.getPadresByUsuariosId(usuario.getUsuarios_id());
        call.enqueue(new Callback<Padres>() {
            @Override
            public void onResponse(Call<Padres> call, Response<Padres> response) {
                padre = response.body();
            }


            @Override
            public void onFailure(Call<Padres> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }
    //Método que obtiene el alumno segun el padre
    private void getAlumno() {
        //Se llama al servicio que obtiene el de alumno segun usuarioId del padre
        Call<Alumnos> call = apiService.getAlumnoByAlumnoId(padre.getAlumnoId());
        call.enqueue(new Callback<Alumnos>() {
            @Override
            public void onResponse(Call<Alumnos> call, Response<Alumnos> response) {
                alumno = response.body();
            }


            @Override
            public void onFailure(Call<Alumnos> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });

    }
    //Método que actualiza el usuario
    public void updateUsuario(View view) {
        usuario.setNombre_usuario(binding.editNombreUsuario.getText().toString());
        usuario.setNombre(binding.editNombre.getText().toString());
        usuario.setContrasenia(binding.editContrasenia.getText().toString());
        usuario.setEmail(binding.editEmail.getText().toString());
        usuario.setTelefono(Integer.parseInt(binding.editTelefono.getText().toString()));
        usuario.setRol(binding.spinnerRol.getSelectedItem().toString());
        if (padre != null && alumno != null) {
            for (Alumnos a : listaAlumnos
            ) {
                //Se busca en la lista el alumno seleccionado para establecer la relación en la BD
                if (a.getNombre().equals(binding.spinnerAlumno.getSelectedItem().toString())) {
                    padre.setAlumnoId(a.getAlumno_id());
                }
            }
            padre.setUsuariosId(usuario.getUsuarios_id());
            Call<Padres> call = apiService.updatePadres(padre);
            call.enqueue(new Callback<Padres>() {
                @Override
                public void onResponse(Call<Padres> call, Response<Padres> response) {

                }

                @Override
                public void onFailure(Call<Padres> call, Throwable t) {

                }
            });
        }

        Call<Usuarios> call = apiService.updateUsuarios(usuario);
        call.enqueue(new Callback<Usuarios>() {
            @Override
            public void onResponse(Call<Usuarios> call, Response<Usuarios> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UsuarioEditActivity.this, R.string.usuarioActualizado, Toast.LENGTH_SHORT).show();
                    volverUsuariosMenu();
                } else {
                    Toast.makeText(UsuarioEditActivity.this, R.string.errorActualizaUsuario, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuarios> call, Throwable t) {

            }
        });


    }

    private void dialogoBack() {

        AlertDialog alertDialog = new AlertDialog.Builder(UsuarioEditActivity.this)
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


    @Override
    public void onBackPressed() {
        if (!cambioCampos) {
            volverUsuariosMenu();
        } else {
            dialogoBack();
        }
    }

    private void volverUsuariosMenu() {
        Intent vueltaUsuarioMenu = new Intent(this, UsuariosActivity.class);
        startActivity(vueltaUsuarioMenu);
    }

}