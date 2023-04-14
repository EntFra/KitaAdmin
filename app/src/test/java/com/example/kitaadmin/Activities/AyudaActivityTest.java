package com.example.kitaadmin.Activities;

import static org.junit.Assert.assertEquals;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.kitaadmin.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
public class AyudaActivityTest {

    private AyudaActivity ayudaActivity;

    @Before
    public void setUp() {
        ActivityController<AyudaActivity> activityController = Robolectric.buildActivity(AyudaActivity.class);
        ayudaActivity = activityController.create().visible().get();
        LayoutInflater inflater = LayoutInflater.from(ayudaActivity);
        View view = inflater.inflate(R.layout.activity_ayuda, null);
        ayudaActivity.setContentView(view);
    }

    @Test
    public void alHacerClicEnAyudaAlternarVisibilidadDeLaDescripcionDelTexto() {
        int initialVisibility = View.GONE;
        int expectedVisibility = View.VISIBLE;
        int position = 0;

       TextView textDescription = ayudaActivity.findViewById(R.id.textDescription);
       if(textDescription!=null){ textDescription.setVisibility(initialVisibility);

        ayudaActivity.onAyudaClick(position);

        assertEquals(expectedVisibility, textDescription.getVisibility());}
    }
}