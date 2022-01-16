package com.example.kitaadmin.Model;

/**
 * Clase Calendario que representa los eventos diarios
 */
public class Calendario {

    private int dia_id;
    private String evento;

    public Calendario(int dia_id, String evento) {
        this.dia_id = dia_id;
        this.evento = evento;
    }

    public int getDia_id() {
        return dia_id;
    }

    public void setDia_id(int dia_id) {
        this.dia_id = dia_id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
