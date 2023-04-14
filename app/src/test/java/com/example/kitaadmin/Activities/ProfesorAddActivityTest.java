package com.example.kitaadmin.Activities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class ProfesorAddActivityTest {

    @Mock
    ProfesorAddActivity profesorAddActivity;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void esDNIValido() {
        String validDNI = "12345678A";
        when(profesorAddActivity.isValidDNI(validDNI)).thenReturn(true);

        boolean result = profesorAddActivity.isValidDNI(validDNI);

        assertTrue(result);
    }

    @Test
    public void noEsDNIValido() {
        String invalidDNI = "1234567A";
        when(profesorAddActivity.isValidDNI(invalidDNI)).thenReturn(false);

        boolean result = profesorAddActivity.isValidDNI(invalidDNI);

        assertFalse(result);
    }
}