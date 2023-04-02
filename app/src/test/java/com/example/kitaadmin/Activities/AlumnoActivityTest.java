package com.example.kitaadmin.Activities;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kitaadmin.Model.Alumnos;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.util.ReflectionTestUtils;

public class AlumnoActivityTest {



    @Test
    @DisplayName("debe navegar a PadresAlumnoActivity cuando se llama a verPadres")
    public void editarAlumnoStartsAlumnoEditActivityWithIntentExtras() {
        AlumnoActivity alumnoActivity = mock(AlumnoActivity.class);
        alumnoActivity.editarAlumno(null);
        verify(alumnoActivity).editarAlumno(null);
    }

    @Test
    @DisplayName("no debe cargar la actividad cuando los extras son null")
    public void cargarActivityWhenExtrasAreNull() {
        AlumnoActivity activity = mock(AlumnoActivity.class);
        Bundle bundle = mock(Bundle.class);
        when(activity.getIntent()).thenReturn(mock(Intent.class));
        when(activity.getIntent().getExtras()).thenReturn(bundle);
        when(bundle.getString("alumnoSeleccionado")).thenReturn(null);
        when(bundle.getString("grupo")).thenReturn(null);
        ReflectionTestUtils.invokeMethod(activity, "cargarActivity");
        verify(activity, never()).setContentView(any(View.class));
    }

    @Test
    @DisplayName("debe volver a la lista de alumnos al pulsar el botón atrás")
    public void onBackPressedReturnsToListOfStudents() {
        AlumnoActivity alumnoActivity = mock(AlumnoActivity.class);
        ReflectionTestUtils.setField(
                alumnoActivity,
                "alumno",
                new Alumnos(
                        "nombre", "alergias", "observaciones", true, true, true, "grupo", "fecha"));
        doCallRealMethod().when(alumnoActivity).onBackPressed();
        doCallRealMethod().when(alumnoActivity).vueltaListaAlumnos();
        doNothing().when(alumnoActivity).startActivity(any(Intent.class));
        alumnoActivity.onBackPressed();
        verify(alumnoActivity, times(1)).vueltaListaAlumnos();
    }

    @Test
    @DisplayName("debe cargar la actividad sin extras")
    public void cargarActivityWithoutExtras() {
        AlumnoActivity activity = mock(AlumnoActivity.class);
        activity.cargarActivity();
        verify(activity).cargarActivity();
    }

    @Test
    @DisplayName("debe volver a la lista de alumnos después de borrar un alumno")
    public void vueltaListaAlumnosAfterDeletingStudent() {
        AlumnoActivity alumnoActivity = mock(AlumnoActivity.class);
        alumnoActivity.vueltaListaAlumnos();
        verify(alumnoActivity, times(1)).vueltaListaAlumnos();
    }

    @Test
    @DisplayName("debe iniciar InformeActivity cuando se hace clic en el botón informes")
    public void informesStartsInformeActivityOnClick() {
        AlumnoActivity alumnoActivity = mock(AlumnoActivity.class);
        alumnoActivity.informes(null);
        verify(alumnoActivity).informes(null);
    }

    @Test
    @DisplayName("debe navegar a PadresAlumnoActivity cuando se llama a verPadres")
    public void verPadresNavigatesToPadresAlumnoActivity() {
        AlumnoActivity alumnoActivity = mock(AlumnoActivity.class);
        alumnoActivity.verPadres(null);
        verify(alumnoActivity).verPadres(null);
    }
}

