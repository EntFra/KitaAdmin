package com.example.kitaadmin.Response;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ResponseRegisterClass implements Serializable {

    @SerializedName("nombre_usuario")
    private String nombre_usuario;

    @SerializedName("contrasenia")
    private String contrasenia;

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

    public ResponseRegisterClass(String nombre_usuario, String contrasenia) {
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
    }

    @Override
    public String toString() {
        return
                "ResponseRegisterClass{" +
                        "nombre_usuario = '" + nombre_usuario + '\'' +
                        ",contrasenia = '" + contrasenia + '\'' +
                        "}";
    }
}