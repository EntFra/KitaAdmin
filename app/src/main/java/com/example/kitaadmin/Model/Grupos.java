package com.example.kitaadmin.Model;

/**
 * Clase Grupos que representa cada grupo en los que se estructura la guarderia
 */
public class Grupos {

    private String nombre;

    public Grupos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
