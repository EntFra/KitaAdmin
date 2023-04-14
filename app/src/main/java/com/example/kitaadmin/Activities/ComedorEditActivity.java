package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Comedor;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.example.kitaadmin.databinding.ActivityComedorEditBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Clase que maneja el activity Comedor edit, permite editar el menú del día
 */
public class ComedorEditActivity extends AppCompatActivity {

    Comedor menuDiaOld;
    Comedor menuDiaNew;
    String fecha;
    String menuDia;
    //Variable que almacena si hubo cambios en los campos para realizar update
    boolean noCambioCampos;
    private ActivityComedorEditBinding binding;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comedor_edit);
        binding = ActivityComedorEditBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            menuDia = extras.getString("menuDia");
            fecha = extras.getString("fecha");

            menuDiaOld = new Gson().fromJson(menuDia, Comedor.class);
        }

        binding.dateComedor.setText(fecha);
        if (menuDiaOld != null) {
            getComedorDia();
        }

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

        binding.editDesayuno.addTextChangedListener(tw);
        binding.editSnack.addTextChangedListener(tw);
        binding.editPlatoPrincipal.addTextChangedListener(tw);
        binding.editPostre.addTextChangedListener(tw);

        binding.buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateComedor();
            }
        });
    }

    private boolean setCambioCampos(boolean isChanged) {

        return noCambioCampos = isChanged;
    }

    private boolean getNoCambioCampos() {
        return noCambioCampos;
    }
    //Método que obtiene los datos del menú del día
    private void getComedorDia() {
        binding.dateComedor.setText(menuDiaOld.getFecha());
        binding.editDesayuno.setText(menuDiaOld.getDesayuno());
        binding.editPlatoPrincipal.setText(menuDiaOld.getPlato_principal());
        binding.editSnack.setText(menuDiaOld.getSnack());
        binding.editPostre.setText(menuDiaOld.getPostre());
    }
    //Método que actualiza el menú del día
    public void updateComedor() {

        getMenuDiaNew();

        apiService = Network.getInstance().create(ApiService.class);
        apiService.updateComedor(menuDiaNew).enqueue(new Callback<Comedor>() {

            @Override
            public void onResponse(Call<Comedor> call, Response<Comedor> response) {
                if (response.body() != null) {
                    Toast.makeText(ComedorEditActivity.this, R.string.comedorActualizado, Toast.LENGTH_SHORT).show();
                    volverComedor();
                } else {
                    Toast.makeText(ComedorEditActivity.this, R.string.errorActualizaComedor, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Comedor> call, Throwable t) {

            }
        });

    }

    private void volverComedor() {
        Intent fechaSeleccionada = new Intent(this, ComedorActivity.class);
        fechaSeleccionada.putExtra("fecha", fecha);
        startActivity(fechaSeleccionada);
    }

    //Método que obtiene los datos del menú del día
    private Comedor getMenuDiaNew() {
        menuDiaNew = new Comedor();
        menuDiaNew.setFecha(fecha);
        menuDiaNew.setDesayuno(binding.editDesayuno.getText().toString());
        menuDiaNew.setPlato_principal(binding.editPlatoPrincipal.getText().toString());
        menuDiaNew.setSnack(binding.editSnack.getText().toString());
        menuDiaNew.setPostre(binding.editPostre.getText().toString());
        return menuDiaNew;
    }
    //Método que muestra un dialogo de confirmación para salir sin guardar cambios
    private void dialogoBack() {

        androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(ComedorEditActivity.this)
                .setTitle(R.string.cambiosSinGuardar)
                .setMessage(R.string.salirSinGuardarCambios)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        volverComedor();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();


    }
    //Método que comprueba si los campos están vacíos
    private boolean isEmpty() {
        boolean isEmpty = binding.editPostre.getText().toString().isEmpty() &&
                binding.editSnack.getText().toString().isEmpty() &&
                binding.editDesayuno.getText().toString().isEmpty() &&
                binding.editPlatoPrincipal.getText().toString().isEmpty();

        return isEmpty;
    }

    @Override
    public void onBackPressed() {
        getMenuDiaNew();
        if (isEmpty() || getNoCambioCampos()) {
            volverComedor();
        } else {
            dialogoBack();
        }
    }
}
