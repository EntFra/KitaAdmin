package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;
/**
 * Clase que representa el objeto estadisticas de la API
 */
@Generated("com.robohorse.robopojogenerator")
public class Estadisticas implements Serializable {

    @SerializedName("numeroPadres")
    private int numeroPadres;

    @SerializedName("numeroAlumnos")
    private int numeroAlumnos;

    @SerializedName("numeroProfesores")
    private int numeroProfesores;

    @SerializedName("numeroUsuarios")
    private int numeroUsuarios;

    public Estadisticas(int numeroPadres, int numeroAlumnos, int numeroProfesores, int numeroUsuarios) {
        this.numeroPadres = numeroPadres;
        this.numeroAlumnos = numeroAlumnos;
        this.numeroProfesores = numeroProfesores;
        this.numeroUsuarios = numeroUsuarios;
    }

    public int getNumeroPadres() {
        return numeroPadres;
    }

    public void setNumeroPadres(int numeroPadres) {
        this.numeroPadres = numeroPadres;
    }

    public int getNumeroAlumnos() {
        return numeroAlumnos;
    }

    public void setNumeroAlumnos(int numeroAlumnos) {
        this.numeroAlumnos = numeroAlumnos;
    }

    public int getNumeroProfesores() {
        return numeroProfesores;
    }

    public void setNumeroProfesores(int numeroProfesores) {
        this.numeroProfesores = numeroProfesores;
    }

    public int getNumeroUsuarios() {
        return numeroUsuarios;
    }

    public void setNumeroUsuarios(int numeroUsuarios) {
        this.numeroUsuarios = numeroUsuarios;
    }

    @Override
    public String toString() {
        return "Estadísticas{" +
                "numeroPadres=" + numeroPadres +
                ", numeroAlumnos=" + numeroAlumnos +
                ", numeroProfesores=" + numeroProfesores +
                ", numeroUsuarios=" + numeroUsuarios +
                '}';
    }
}
