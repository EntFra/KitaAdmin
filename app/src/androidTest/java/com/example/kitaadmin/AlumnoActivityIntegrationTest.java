package com.example.kitaadmin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.content.Intent;
import android.os.Build;

import com.example.kitaadmin.Activities.AlumnoActivity;
import com.example.kitaadmin.Activities.AlumnoEditActivity;
import com.example.kitaadmin.Model.Alumnos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowActivity;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.Q)
public class AlumnoActivityIntegrationTest {
    private Alumnos testAlumno;
    private String testAlumnoJson;
    private String testGrupo;

    @Before
    public void setUp() {
        testAlumno = new Alumnos("Test Nombre", "Test Alergias", "Test Observaciones", true, true, true, "Test Grupo", "2000-01-01");
        testAlumnoJson = "{\"nombre\":\"Test Nombre\",\"alergias\":\"Test Alergias\",\"observaciones\":\"Test Observaciones\",\"auto_salidas\":true,\"auto_fotos\":true,\"comedor\":true,\"grupo\":\"Test Grupo\",\"fecha_nac\":\"2000-01-01\"}";
        testGrupo = "Test Grupo";
    }

    @Test
    public void testEditarAlumno() {
        AlumnoActivity activity = Robolectric.buildActivity(AlumnoActivity.class).create().get();
        activity.alumno = testAlumno;
        activity.alumnoSeleccionado = testAlumnoJson;
        activity.grupo = testGrupo;

        activity.editarAlumno(null);

        ShadowActivity shadowActivity = org.robolectric.Shadows.shadowOf(activity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();
        assertEquals(AlumnoEditActivity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(testAlumnoJson, startedIntent.getStringExtra("alumnoSeleccionado"));
        assertEquals(testGrupo, startedIntent.getStringExtra("grupo"));
    }

    }
