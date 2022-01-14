package com.example.kitaadmin.Model;

import java.util.Date;

public class Profesores {

    private String dni;
    private Date fecha_alta;
    private String direccion;
    private Date fecha_nac;
    private String nombre_grupo;
    private int usuarios_id_prof;

    public Profesores(String dni, Date fecha_alta, String direccion, Date fecha_nac, String nombre_grupo, int usuarios_id_prof) {
        this.dni = dni;
        this.fecha_alta = fecha_alta;
        this.direccion = direccion;
        this.fecha_nac = fecha_nac;
        this.nombre_grupo = nombre_grupo;
        this.usuarios_id_prof = usuarios_id_prof;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public int getUsuarios_id_prof() {
        return usuarios_id_prof;
    }

    public void setUsuarios_id_prof(int usuarios_id_prof) {
        this.usuarios_id_prof = usuarios_id_prof;
    }
}

