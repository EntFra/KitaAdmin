package com.example.kitaadmin.Utils;

import com.example.kitaadmin.Model.Alumno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Clase que realiza el consumo de datos
 */

public interface AlumnoService {

    //Obtiene la lista de usuarios de la base de datos
    @GET("list/")
    Call<List<Alumno>> getAlumnos();
}
