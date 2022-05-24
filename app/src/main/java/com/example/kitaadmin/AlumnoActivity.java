package com.example.kitaadmin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AlumnoActivity extends AppCompatActivity {
    String grupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno);

        //Recoge el nombre del grupo
        Bundle extras = getIntent().getExtras();
        grupo = extras.getString("grupoSeleccionado");
    }
}