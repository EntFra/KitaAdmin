package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Calendario que representa los eventos diarios
 */
@Generated("com.robohorse.robopojogenerator")
public class Calendario implements Serializable {
    @SerializedName("calendario_id")
    private int id;
    @SerializedName("dia")
    private String dia;
    @SerializedName("evento")
    private String evento;

    public Calendario() {

    }

    public Calendario(int id, String dia_id, String evento) {
        this.dia = dia_id;
        this.evento = evento;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia_id) {
        this.dia = dia_id;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }
}
