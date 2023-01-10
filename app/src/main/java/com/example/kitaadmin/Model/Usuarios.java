package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Usuarios que representa un usuario de la aplicacion, estableciendo la información necesaria para el correcto uso de la aplicacion
 */
@Generated("com.robohorse.robopojogenerator")
public class Usuarios implements Serializable {
    @SerializedName("id")
    private int usuarios_id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("nombre_usuario")
    private String nombre_usuario;
    @SerializedName("contrasenia")
    private String contrasenia;
    @SerializedName("rol")
    private String rol;
    @SerializedName("telefono")
    private int telefono;
    @SerializedName("email")
    private String email;

    public Usuarios(String nombre, String nombre_usuario, String contrasenia, String rol, int telefono, String email) {
        this.nombre = nombre;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.telefono = telefono;
        this.email = email;
    }

    public Usuarios() {

    }

    public Usuarios(String nombre_usuario, String contrasenia) {
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
    }

    public int getUsuarios_id() {
        return usuarios_id;
    }

    public void setUsuarios_id(int usuarios_id) {
        this.usuarios_id = usuarios_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Usuarios{" +
                "usuarios_id=" + usuarios_id +
                ", nombre='" + nombre + '\'' +
                ", nombre_usuario='" + nombre_usuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", rol=" + rol +
                ", telefono=" + telefono +
                ", email='" + email + '\'' +
                '}';
    }
}
