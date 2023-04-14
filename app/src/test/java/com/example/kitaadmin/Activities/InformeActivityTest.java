package com.example.kitaadmin.Activities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RunWith(RobolectricTestRunner.class)
public class InformeActivityTest {

    InformeActivity informeActivity;

    @Before
    public void setUp() {
        informeActivity = new InformeActivity();
    }

    @Test
    public void getDayAfter() {
        String fechaActual = "01-01-2022";
        String fechaEsperada = "02-01-2022";

        informeActivity.setFechaDia(fechaActual);

        try {
            informeActivity.getDayAfter(null);
            String fechaReal = fechaActual;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(fechaActual));
            c.add(Calendar.DATE, 1);
            fechaReal = sdf.format(c.getTime());
            assertEquals(
                    fechaEsperada,
                    fechaReal);
        } catch (ParseException e) {
            fail("ParseException should not be thrown");
        }
    }
}