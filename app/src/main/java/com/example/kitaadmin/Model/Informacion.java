package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Informacion que almacena informaciones destacables segun grupo
 */
@Generated("com.robohorse.robopojogenerator")
public class Informacion implements Serializable {
    @SerializedName("informacion_id")
    private int informacion_id;
    @SerializedName("informacion")
    private String informacion;

    public Informacion(int informacion_id, String informacion) {
        this.informacion_id = informacion_id;
        this.informacion = informacion;
    }

    public int getInformacion_id() {
        return informacion_id;
    }

    public void setInformacion_id(int informacion_id) {
        this.informacion_id = informacion_id;
    }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }

    @Override
    public String toString() {
        return "Infomacion{" +
                "informacion_id=" + informacion_id +
                ", informacion='" + informacion + '\'' +
                '}';
    }
}
