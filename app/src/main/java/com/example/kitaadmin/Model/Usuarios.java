package com.example.kitaadmin.Model;

public class Usuarios {

    private int usuarios_id;
    private String nombre;
    private String nombre_usuario;
    private String contrasenia;
    private int rol;
    private int telefono;
    private String email;

    public Usuarios(String nombre, String nombre_usuario, String contrasenia, int rol, int telefono, String email) {
        this.nombre = nombre;
        this.nombre_usuario = nombre_usuario;
        this.contrasenia = contrasenia;
        this.rol = rol;
        this.telefono = telefono;
        this.email = email;
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

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
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
}
