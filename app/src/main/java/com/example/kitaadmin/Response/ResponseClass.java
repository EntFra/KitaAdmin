package com.example.kitaadmin.Response;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ResponseClass implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("nombre_usuario")
    private String nombre_usuario;

    @SerializedName("contrasenia")
    private String contrasenia;

    @SerializedName("rol")
    private String rol;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    @Override
    public String toString() {
        return
                "ResponseClass{" +
                        "id = '" + id + '\'' +
                        ",nombre_usuario = '" + nombre_usuario + '\'' +
                        ",contrasenia = '" + contrasenia + '\'' +
                        ",rol = '" + rol + '\'' +
                        "}";
    }
}