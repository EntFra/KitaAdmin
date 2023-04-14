package com.example.kitaadmin.Activities;



import com.example.kitaadmin.Model.Alumnos;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlumnoActivityTest {

    private Alumnos testAlumno;

    @Before
    public void setUp() {
        testAlumno = new Alumnos("Test Nombre", "Test Alergias", "Test Observaciones", true, true, true, "Test Grupo", "2000-01-01");
    }

    @Test
    public void testAlumnoInfo() {
        assertEquals("Test Nombre", testAlumno.getNombre());
        assertEquals("Test Alergias", testAlumno.getAlergias());
        assertEquals("Test Observaciones", testAlumno.getObservaciones());
        assertEquals("Test Grupo", testAlumno.getNombre_grupo());
        assertEquals("2000-01-01", testAlumno.getFecha_nac());
    }
}
