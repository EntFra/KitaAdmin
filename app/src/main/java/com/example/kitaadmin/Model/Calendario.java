package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Calendario que representa los eventos diarios
 */
@Generated("com.robohorse.robopojogenerator")
public class Calendario implements Serializable {
    @SerializedName("dia_id")
    private int fecha;
    @SerializedName("evento")
    private String evento;

    public Calendario(int dia_id, String evento) {
        this.fecha = dia_id;
        this.evento = evento;
    }

    public int getDia_id() {
        return fecha;
    }

    public void setDia_id(int dia_id) {
        this.fecha = dia_id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
