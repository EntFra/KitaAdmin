package com.example.kitaadmin.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Usuarios;
import com.example.kitaadmin.R;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Clase que maneja el activity menu, muestra las opciones de la app. En caso de no poseer un rol con autorización para alguna opción, esta será ocultada
 */

public class MenuActivity extends AppCompatActivity {

    ImageButton btnDataBase;
    TextView txtUsuariosDataBase;
    Button exitBtn;
    static ApiService apiService = Network.getInstance().create(ApiService.class);
    static Padres padre = new Padres();
    static Usuarios usuario = new Usuarios();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_menu);

        txtUsuariosDataBase = findViewById(R.id.textViewUsuariosDataBase);
        btnDataBase = findViewById(R.id.databaseBtn);
        exitBtn = findViewById(R.id.exitBtn);
        //Almacena el rol del usuario logeado
        String rol;
        //Recoge el rol del usuario
        Bundle extras = getIntent().getExtras();
        rol = extras.getString("rol");
        usuario = LoginActivity.getUsuario();
        //Se evalúa el rol del usuario para ocultar la opción de administrar usuarios del menú
        if (!(rol.equalsIgnoreCase("director") || rol.equalsIgnoreCase("profesor_admin"))) {
            btnDataBase.setVisibility(View.INVISIBLE);
            txtUsuariosDataBase.setVisibility(View.INVISIBLE);
        }

        //Si el rol es padre obtenemos el objeto padre para poder mostrar las pantallas según el rol correctamente
        if(rol.equalsIgnoreCase("padre")){
            getPadreAlumno();
        }



    }


    private static void getPadreAlumno() {
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

    public static Padres getPadre(){
        return padre;
    }



    //Método para volver a la pantalla de login
    public void volver(View v) {
        vueltaLogin();
    }

    //Método para ir a la pantalla grupos
    public void grupos(View v) {
        Intent intent = new Intent(this, GruposActivity.class);
        startActivity(intent);
    }

    //Método para ir a la pantalla comedor
    public void comedor(View v) {
        Intent intent = new Intent(this, ComedorActivity.class);
        startActivity(intent);
    }

    //Método para ir a la pantalla calendario
    public void calendario(View v) {
        Intent intent = new Intent(this, CalendarioActivity.class);
        startActivity(intent);
    }

    //Método para ir a la pantalla información actual
    public void info(View v) {
        Intent intent = new Intent(this, InformacionActualActivity.class);
        startActivity(intent);
    }

    //Método para ir a la pantalla ayuda
    public void ayuda(View v) {
        Intent intent = new Intent(this, AyudaActivity.class);
        startActivity(intent);
    }

    //Método para ir a la pantalla usuarios
    public void usuarios(View v) {
        Intent intent = new Intent(this, UsuariosActivity.class);
        startActivity(intent);
    }

    public void estadisticas(View v) {
        Intent intent = new Intent(this, EstadisticasActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        vueltaLogin();
    }

    private void vueltaLogin() {
        androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this)
                .setTitle(R.string.cerrarSesionTitulo)
                .setMessage(R.string.cerrarSesion)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginActivity();
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    private void loginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
