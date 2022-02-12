package com.example.kitaadmin.Remote;

import com.example.kitaadmin.Response.ResponseClass;
import com.example.kitaadmin.Response.ResponseRegisterClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {

    @PUT("/addUser")
    Call<ResponseClass> addUsuario(@Body ResponseRegisterClass responseRegisterClass);

    @POST("/getUser")
    Call<ResponseClass> getUsuario(@Body ResponseRegisterClass responseRegisterClass);

}