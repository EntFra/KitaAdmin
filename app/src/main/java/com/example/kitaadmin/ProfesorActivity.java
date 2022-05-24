package com.example.kitaadmin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitaadmin.Adapter.GruposAdapter;
import com.example.kitaadmin.Adapter.ProfesorAdapter;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Remote.ApiService;
import com.example.kitaadmin.Remote.Network;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfesorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
    }
}