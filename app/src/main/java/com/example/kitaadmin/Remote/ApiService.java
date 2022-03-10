package com.example.kitaadmin.Remote;

import com.example.kitaadmin.Model.Grupos;
import com.example.kitaadmin.Response.ResponseClass;
import com.example.kitaadmin.Response.ResponseRegisterClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @PUT("/addUsuario")
    Call<ResponseClass> addUsuario(@Body ResponseRegisterClass responseRegisterClass);

    @POST("/getUsuario")
    Call<ResponseClass> getUsuario(@Body ResponseRegisterClass responseRegisterClass);

    @GET("/getGrupos")
    Call<List<Grupos>> getGrupos();

}