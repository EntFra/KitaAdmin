package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase que representa la tabla alumnos de la base de datos
 */
@Generated("com.robohorse.robopojogenerator")
public class Alumnos implements Serializable {
    @SerializedName("alumno_id")
    private int alumno_id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("alergias")
    private String alergias;
    @SerializedName("observaciones")
    private String observaciones;
    @SerializedName("auto_salidas")
    private boolean auto_salidas;
    @SerializedName("auto_fotos")
    private boolean auto_fotos;
    @SerializedName("comedor")
    private boolean comedor;
    @SerializedName("grupo")
    private String nombre_grupo;
    @SerializedName("fecha_nac")
    private String fecha_nac;

    public Alumnos(String nombre, String alergias, String observaciones, boolean auto_salidas, boolean auto_fotos, boolean comedor, String nombre_grupo, String fecha_nacimiento) {

        this.nombre = nombre;
        this.alergias = alergias;
        this.observaciones = observaciones;
        this.auto_salidas = auto_salidas;
        this.auto_fotos = auto_fotos;
        this.comedor = comedor;
        this.nombre_grupo = nombre_grupo;
        this.fecha_nac = fecha_nacimiento;
    }

    public Alumnos() {
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isAuto_salidas() {
        return auto_salidas;
    }

    public void setAuto_salidas(boolean auto_salidas) {
        this.auto_salidas = auto_salidas;
    }

    public boolean isAuto_fotos() {
        return auto_fotos;
    }

    public void setAuto_fotos(boolean auto_fotos) {
        this.auto_fotos = auto_fotos;
    }

    public boolean isComedor() {
        return comedor;
    }

    public void setComedor(boolean comedor) {
        this.comedor = comedor;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public String getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(String fecha_nac) {
        this.fecha_nac = fecha_nac;
    }
}
