package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Grupos que representa cada grupo en los que se estructura la guarderia
 */
@Generated("com.robohorse.robopojogenerator")
public class Grupo implements Serializable {
    @SerializedName("nombre")
    private String nombre;

    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Grupo{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
