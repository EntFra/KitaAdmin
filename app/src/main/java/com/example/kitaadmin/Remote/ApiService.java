package com.example.kitaadmin.Remote;

import com.example.kitaadmin.Model.Alumnos;
import com.example.kitaadmin.Model.Calendario;
import com.example.kitaadmin.Model.Comedor;
import com.example.kitaadmin.Model.Estadisticas;
import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Model.Informacion;
import com.example.kitaadmin.Model.Informes;
import com.example.kitaadmin.Model.Padres;
import com.example.kitaadmin.Model.Profesores;
import com.example.kitaadmin.Model.Usuarios;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Interface que contiene las rutas de llamadas a la base de datos
 */

public interface ApiService {

    @PUT("/addUsuario")
    Call<Usuarios> addUsuario(@Body Usuarios usuario);

    @POST("/getUsuario")
    Call<Usuarios> getUsuario(@Body Usuarios usuario);

    @POST("getUsuarios")
    Call<List<Usuarios>> getUsuarios();

    @POST("/getUsuario/id")
    Call<Usuarios> getUsuario(@Query(value = "id", encoded = true) int id);

    @DELETE("/deleteUsuarios")
    Call<Void> deleteUsuarios(@Query(value = "id", encoded = true) int id);

    @GET("/getGrupos")
    Call<List<Grupos>> getGrupos();

    @PUT("/addAlumno")
    Call<Alumnos> addAlumno(@Body Alumnos alumno);

    @GET("/getAlumnos/grupo")
    Call<List<Alumnos>> getAlumnos(@Query(value = "grupo", encoded = true) String grupo);

    @PUT("/updateAlumno")
    Call<Alumnos> updateAlumno(@Body Alumnos alumno);

    @POST("getAlumnos")
    Call<List<Alumnos>> getAlumnosAll();

    @POST("/getAlumno/alumnoId")
    Call<Alumnos> getAlumnoByAlumnoId(@Query(value = "alumnoId", encoded = true) int alumno);

    @DELETE("/deleteAlumno")
    Call<Void> deleteAlumno(@Query(value = "alumnoId", encoded = true) int alumno);

    @GET("/getProfesoresAll")
    Call<List<Profesores>> getProfesoresAll();

    @GET("/getProfesores/grupo")
    Call<List<Profesores>> getProfesores(@Query(value = "grupo", encoded = true) String grupo);

    @PUT("/updateProfesor")
    Call<Profesores> updateProfesor(@Body Profesores profesor);

    @PUT("/addProfesor")
    Call<Profesores> addProfesor(@Body Profesores profesor);

    @POST("/getPadres/alumnoId")
    Call<List<Padres>> getPadres(@Query(value = "alumnoId", encoded = true) int alumno);

    @POST("/getPadres/usuarioId")
    Call<Padres> getPadresByUsuariosId(@Query(value = "usuariosIdPad", encoded = true) int usuario);

    @POST("/getInformes/alumnoId")
    Call<Informes> getInformes(@Query(value = "alumnoId", encoded = true) int alumno, @Query(value = "fecha", encoded = true) String fecha);

    @PUT("/updateInformes")
    Call<Informes> updateInformes(@Body Informes informe);

    @POST("/getComedor")
    Call<Comedor> getComedor(@Query(value = "fecha", encoded = true) String fecha);

    @PUT("updateComedor")
    Call<Comedor> updateComedor(@Body Comedor comedor);

    @POST("/getCalendario")
    Call<Calendario> getCalendario(@Query(value = "dia", encoded = true) String dia);

    @PUT("/updateCalendario")
    Call<Calendario> updateCalendario(@Body Calendario calendario);

    @POST("/getInformacion")
    Call<List<Informacion>> getInformacion();

    @PUT("/updateInformacion")
    Call<Informacion> updateInformacion(@Body Informacion informacion);

    @PUT("/updatePadres")
    Call<Padres> updatePadres(@Body Padres padre);

    @PUT("/addPadres")
    Call<Padres> addPadres(@Body Padres padre);

    @PUT("/updateUsuarios")
    Call<Usuarios> updateUsuarios(@Body Usuarios usuario);

    @DELETE("/deleteCalendario")
    Call<Void> deleteCalendario(@Query(value = "dia", encoded = true) String dia);

    @DELETE("/deleteComedor")
    Call<Void> deleteComedor(@Query(value = "dia", encoded = true) String dia);

    @POST("/getEstadisticas")
    Call<Estadisticas> getEstadisticas();

}