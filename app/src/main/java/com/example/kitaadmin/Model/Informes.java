package com.example.kitaadmin.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Informes, objeto que almacena la informacion diaria de cada alumno
 */
@Generated("com.robohorse.robopojogenerator")
public class Informes implements Serializable {

    @SerializedName("alumnoId")
    private int alumno_id;
    @SerializedName("deposicion")
    private boolean deposicion;
    @SerializedName("suenio")
    private String suenio;
    @SerializedName("comida")
    private boolean comida;
    @SerializedName("bebida")
    private boolean bebida;
    @SerializedName("fecha")
    private String fecha;


    public Informes(int alumno_id, boolean deposicion, boolean comida, boolean bebida, String fecha, String suenio) {
        this.alumno_id = alumno_id;
        this.deposicion = deposicion;
        this.comida = comida;
        this.bebida = bebida;
        this.fecha = fecha;
        this.suenio = suenio;
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public boolean isDeposicion() {
        return deposicion;
    }

    public void setDeposicion(boolean deposicion) {
        this.deposicion = deposicion;
    }

    public boolean isComida() {
        return comida;
    }

    public void setComida(boolean comida) {
        this.comida = comida;
    }

    public boolean isBebida() {
        return bebida;
    }

    public void setBebida(boolean bebida) {
        this.bebida = bebida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSuenio() {
        return suenio;
    }

    public void setSuenio(String suenio) {
        this.suenio = suenio;
    }

    @Override
    public String toString() {
        return "Informes{" +
                "alumno_id=" + alumno_id +
                ", deposicion=" + deposicion +
                ", comida=" + comida +
                ", bebida=" + bebida +
                ", fecha=" + fecha +
                ", suenio=" + suenio +
                '}';
    }
}
