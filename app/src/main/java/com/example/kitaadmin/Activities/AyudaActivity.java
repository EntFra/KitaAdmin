package com.example.kitaadmin.Activities;

import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.AyudaAdapter;
import com.example.kitaadmin.R;

/**
 * Clase que maneja el activity Ayuda, muestra un pequeño FAQ, en el que al ser pulsada la pregunta expande la respuesta correspondiente
 */
public class AyudaActivity extends AppCompatActivity {
    AyudaAdapter adapter;
    LinearLayout layout;
    private String[] listaAyudaTitles;
    private String[] listaAyudaDescriptions;
    private RecyclerView recycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        listaAyudaTitles = getResources().getStringArray(R.array.itemsAyuda);
        listaAyudaDescriptions = getResources().getStringArray(R.array.ayduaDescriptions);


        recycler = findViewById(R.id.recyclerAyuda);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new AyudaAdapter(AyudaActivity.this, listaAyudaTitles, listaAyudaDescriptions, AyudaActivity.this::onAyudaClick);
        recycler.setAdapter(adapter);


    }

    //Expande la información de respuesta
    void onAyudaClick(int i) {
        TextView textDescription = findViewById(R.id.textDescription);
        if(textDescription!=null){
        int v = (textDescription.getVisibility() == View.GONE) ? View.VISIBLE : View.GONE;

        TransitionManager.beginDelayedTransition(findViewById(R.id.layoutDescription), new AutoTransition());
        textDescription.setVisibility(v);}
    }

}
