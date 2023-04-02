package com.example.kitaadmin;

import static junit.framework.TestCase.assertEquals;

import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.kitaadmin.Activities.AlumnoActivity;
import com.example.kitaadmin.Model.Alumnos;
import com.google.gson.Gson;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AlumnoActivityTest {

    @Rule
    public ActivityScenarioRule<AlumnoActivity> activityScenarioRule
            = new ActivityScenarioRule<>(AlumnoActivity.class);

    @Test
    public void testRecuperaInfoAlumno() {
        // Simula la obtención de un alumno a través de los extras del intent
        Alumnos alumno = new Alumnos();
        alumno.setNombre("Juan");
        alumno.setAlergias("Ninguna");
        alumno.setObservaciones("Buen estudiante");
        alumno.setAuto_fotos(true);
        alumno.setAuto_salidas(false);
        alumno.setComedor(true);

        Bundle extras = new Bundle();
        extras.putString("alumnoSeleccionado", new Gson().toJson(alumno));
        extras.putString("grupo", "Grupo A");
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), AlumnoActivity.class);
        intent.putExtras(extras);

        // Inicia la actividad y espera a que se cargue
        ActivityScenario<AlumnoActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> {
            // Verifica que la información del alumno se haya mostrado correctamente en la interfaz
            assertEquals(alumno.getNombre(), activity.binding.textNombreAlumno.getText().toString());
            assertEquals(alumno.getAlergias(), activity.binding.textAlergia.getText().toString());
            assertEquals(alumno.getObservaciones(), activity.binding.textObservaciones.getText().toString());
            assertEquals(alumno.isAuto_fotos(), activity.binding.switchFotos.isChecked());
            assertEquals(alumno.isAuto_salidas(), activity.binding.switchSalidas.isChecked());
            assertEquals(alumno.isComedor(), activity.binding.switchComedor.isChecked());
        });
    }

    

}
