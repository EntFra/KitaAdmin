package com.example.kitaadmin.Remote;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Calendario;
import com.example.kitaadmin.Model.Comedor;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Informacion;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Model.Usuarios;

import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @PUT("/addUsuario")
    Call<Usuarios> addUsuario(@Body Usuarios usuario);

    @POST("/getUsuario")
    Call<Usuarios> getUsuario(@Body Usuarios usuario);

    @POST("/getUsuario/id")
    Call<Usuarios> getUsuario(@Query(value="id", encoded=true)int id);

    @DELETE("/deleteUsuarios")
    Call<Usuarios>deleteUsuarios(@Query(value="id", encoded = true) int id);

    @GET("/getGrupos")
    Call<List<Grupos>> getGrupos();

    @GET("/getProfesores/grupo")
    Call<List<Profesores>> getProfesores(@Query(value="grupo", encoded=true)String grupo);

    @GET("/getAlumnos/grupo")
    Call<List<Alumnos>> getAlumnos(@Query(value="grupo", encoded=true)String grupo);

    @PUT("/updateAlumno")
    Call<Alumnos>updateAlumno(@Body Alumnos alumno);

    @DELETE("/deleteAlumno")
    Call<Alumnos>deleteAlumno(@Query(value="alumnoId", encoded=true)int alumno);

    @PUT("/updateProfesor")
    Call<Profesores>updateProfesor(@Body Profesores profesor);

    @GET("/getPadres/alumnoId")
    Call<List<Padres>> getPadres(@Query(value="alumnoId", encoded=true)int alumno);

    @POST("/getInformes/alumnoId")
    Call<Informes> getInformes(@Query(value="alumnoId", encoded=true)int alumno,@Query(value="fecha", encoded=true) String fecha);

    @PUT("/updateInformes")
    Call<Informes> updateInformes(@Body Informes informe);

    @POST("/getComedor")
    Call<Comedor>getComedor(@Query(value="fecha", encoded = true)String fecha);

    @PUT("updateComedor")
    Call<Comedor> updateComedor(@Body Comedor comedor);

    @POST("/getCalendario")
    Call<Calendario> getCalendario(@Query(value="fecha", encoded = true)String fecha);

    @POST("/getInformacion")
    Call<List<Informacion>> getInformacion();
}