package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.Generated;

/**
 * Clase Profesores que representa un objeto profesor, indicando sus distintos parametros y el grupo al que pertenecen
 */
@Generated("com.robohorse.robopojogenerator")
public class Profesores implements Serializable {
    @SerializedName("dni")
    private String dni;
    @SerializedName("fecha_alta")
    private Date fecha_alta;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("fecha_nac")
    private Date fecha_nac;
    @SerializedName("nombre_g1rupo")
    private String nombre_grupo;
    @SerializedName("usuarios_id_prof")
    private Usuarios usuario;
    public Profesores(String dni, Date fecha_alta, String direccion, Date fecha_nac, String nombre_grupo, Usuarios usuario) {
        this.dni = dni;
        this.fecha_alta = fecha_alta;
        this.direccion = direccion;
        this.fecha_nac = fecha_nac;
        this.nombre_grupo = nombre_grupo;
        this.usuario = usuario;
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

    public Usuarios getUsuario() {
        return usuario;
    }

    @Override
    public String toString() {
        return "Profesores{" +
                "dni='" + dni + '\'' +
                ", fecha_alta=" + fecha_alta +
                ", direccion='" + direccion + '\'' +
                ", fecha_nac=" + fecha_nac +
                ", nombre_grupo='" + nombre_grupo + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}

