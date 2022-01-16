package com.example.kitaadmin.Model;


import java.time.Duration;
import java.util.Date;

/**
 * Clase Informes, objeto que almacena la informacion diaria de cada alumno
 */
public class Informes {

    private int alumno_id;
    private boolean deposicion;
    private boolean comida;
    private boolean bebida;
    private Date fecha;
    private Duration suenio;

    public Informes(int alumno_id, boolean deposicion, boolean comida, boolean bebida, Date fecha, Duration suenio) {
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Duration getSuenio() {
        return suenio;
    }

    public void setSuenio(Duration suenio) {
        this.suenio = suenio;
    }
}
