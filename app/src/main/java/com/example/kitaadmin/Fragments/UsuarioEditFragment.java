package com.example.kitaadmin.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.Utils.Utils;
import com.example.kitaadmin.databinding.FragmentUsuarioAddEditBinding;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UsuarioEditFragment extends Fragment {

    private FragmentUsuarioAddEditBinding binding;
    private Usuarios usuario;
    private final ArrayList<String> LISTAROLES = Utils.getRoles();
    private List<Alumnos> listaAlumnos;
    private final ArrayList<String> listaNombreAlumnos = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapterAlumnos;
    private final ApiService apiService = Network.getInstance().create(ApiService.class);
    ;
    private Padres padre;
    private Alumnos alumno;
    int numero;

    public UsuarioEditFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        usuario = new Gson().fromJson(getArguments().getString("Usuario"), Usuarios.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getAlumnos();

        binding = FragmentUsuarioAddEditBinding.inflate(getLayoutInflater(), container, false);
        binding.btnAdd.setVisibility(View.GONE);
        binding.editNombre.setText(usuario.getNombre());
        binding.editNombreUsuario.setText(usuario.getNombre_usuario());
        binding.editContrasenia.setText(usuario.getContrasenia());
        binding.editTelefono.setText(String.valueOf(usuario.getTelefono()));
        binding.editEmail.setText(usuario.getEmail());
        //Adapter para el spinner de roles
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, LISTAROLES);
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
        //Obtiene la lista de alumnos completa


        //Adapter para mostrar la lista en el spinner
        arrayAdapterAlumnos = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, listaNombreAlumnos);
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
                timer1.schedule(new TimerTask() {
                    public void run() {
                        System.out.println(alumno.getNombre());
                        for (Alumnos a : listaAlumnos
                        ) {
                            if (a.getNombre().equals(alumno.getNombre())) {
                                System.out.println("GEFUNDEN");
                                binding.spinnerAlumno.setSelection(listaAlumnos.indexOf(a));
                                arrayAdapterAlumnos.notifyDataSetChanged();
                            }
                        }
                        timer2.cancel();
                    }
                }, 200);

            } catch (Exception e) {
                System.out.println(e);
            }

        }


        return binding.getRoot();
    }


    private void getAlumnos() {
        //Se crea una instancia de llamada a la API

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

}