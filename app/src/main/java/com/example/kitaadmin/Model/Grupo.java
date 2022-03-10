package com.example.kitaadmin.Model;

/**
 * Clase Grupos que representa cada grupo en los que se estructura la guarderia
 */
public class Grupo {

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
}
