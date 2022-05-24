package com.example.kitaadmin;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class GrupoActivity extends AppCompatActivity {

    TextView nombreGrupo;
    //Almacena el nombre del grupo seleccionado
    String grupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);

        //Recoge el nombre del grupo
        Bundle extras = getIntent().getExtras();
        grupo = extras.getString("grupoSeleccionado");

        nombreGrupo = findViewById(R.id.textViewMenuGrupo);

        nombreGrupo.setText(String.format("Grupo %s", grupo));

    }


    //Método para ir a la pantalla con el listado de  profesores
    public void listaProfesores(View v) {
        Intent intent = new Intent(this, ListaProfesoresActivity.class);
        intent.putExtra("grupoSeleccionado",  grupo);
        startActivity(intent);
    }

    //Método para ir a la pantalla con el listado de  alumnos
    public void listaAlumnos(View v) {
        Intent intent = new Intent(this, AlumnoActivity.class);
        intent.putExtra("grupoSeleccionado",  grupo);
        startActivity(intent);
    }

}