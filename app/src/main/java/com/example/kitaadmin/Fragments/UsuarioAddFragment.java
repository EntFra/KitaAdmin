package com.example.kitaadmin.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import com.example.kitaadmin.Adapter.UsuariosAdapter;
import com.example.kitaadmin.R;


public class UsuarioAddFragment extends Fragment {
    UsuariosAdapter adapter;


    public UsuarioAddFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_usuario_add_edit, container, false);
    }
}