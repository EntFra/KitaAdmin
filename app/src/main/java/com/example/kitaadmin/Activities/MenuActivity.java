package com.example.kitaadmin.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.R;

/**
 * Clase que maneja el activity menu, muestra las opciones de la app. En caso de no poseer un rol con autorización para alguna opción, esta será ocultada
 */

public class MenuActivity extends AppCompatActivity {

    ImageButton btnDataBase, btnGrupos, btnComedor, btnCalendario, btnInfo, btnAyuda, btnUsuarios;
    TextView txtUsuariosDataBase;
    Button exitBtn;


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
        //Se evalúa el rol del usuario para ocultar la opción de administrar usuarios del menú
        if (!(rol.equalsIgnoreCase("director") || rol.equalsIgnoreCase("profesor_admin"))) {
            btnDataBase.setVisibility(View.INVISIBLE);
            txtUsuariosDataBase.setVisibility(View.INVISIBLE);
        }

    }

    //Método para volver a la pantalla de login
    public void volver(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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

    @Override
    public void onBackPressed() {
        vueltaLogin();
    }

    private void vueltaLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
