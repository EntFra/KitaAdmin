package com.example.kitaadmin.Activities;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;

import com.example.kitaadmin.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.shadows.ShadowActivity;

import static org.junit.Assert.assertEquals;
import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class GrupoActivityTest {

    @Test
    public void listaAlumnosIniciaConElGrupoSeleccionado() {
        String selectedGroup = "B";
        Intent intent =
                new Intent(ApplicationProvider.getApplicationContext(), GrupoActivity.class);
        intent.putExtra("grupoSeleccionado", selectedGroup);

        ActivityController<GrupoActivity> activityController = Robolectric.buildActivity(GrupoActivity.class, intent);
        GrupoActivity grupoActivity = activityController.create().visible().get();

        // Simula el click en el botón de lista de alumnos
        grupoActivity.findViewById(R.id.textViewAlumnos).performClick();

        ShadowActivity shadowActivity = shadowOf(grupoActivity);
        Intent startedIntent = shadowActivity.getNextStartedActivity();

        assertEquals(ListaAlumnosActivity.class.getName(), startedIntent.getComponent().getClassName());
        assertEquals(selectedGroup, startedIntent.getStringExtra("grupoSeleccionado"));
    }
}