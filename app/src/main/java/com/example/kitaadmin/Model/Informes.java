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
    private int alumnoId;
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
    @SerializedName("observaciones")
    private String observaciones;


    public Informes(int alumno_id, boolean deposicion, String suenio, boolean comida, boolean bebida, String fecha, String observaciones) {
        this.alumnoId = alumno_id;
        this.deposicion = deposicion;
        this.suenio = suenio;
        this.comida = comida;
        this.bebida = bebida;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    public Informes() {

    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public boolean isDeposicion() {
        return deposicion;
    }

    public void setDeposicion(boolean deposicion) {
        this.deposicion = deposicion;
    }

    public String getSuenio() {
        return suenio;
    }

    public void setSuenio(String suenio) {
        this.suenio = suenio;
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

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "Informes{" +
                "alumnoId=" + alumnoId +
                ", deposicion=" + deposicion +
                ", suenio='" + suenio + '\'' +
                ", comida=" + comida +
                ", bebida=" + bebida +
                ", fecha='" + fecha + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
