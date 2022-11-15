package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Padres que representa un objeto padre, contiene los datos del id padre e id del niño con el que estan relacionados
 */
@Generated("com.robohorse.robopojogenerator")
public class Padres implements Serializable {

    @SerializedName("alumnoId")
    private int alumnoId;
    @SerializedName("usuariosId")
    private int usuariosId;

    public Padres() {
    }

    public Padres(int alumnoId, int usuariosIdPad) {
        this.alumnoId = alumnoId;
        this.usuariosId = usuariosIdPad;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getUsuariosId() {
        return usuariosId;
    }

    public void setUsuariosId(int usuariosId) {
        this.usuariosId = usuariosId;
    }

    @Override
    public String toString() {
        return "Padres{" +
                "alumnoId=" + alumnoId +
                ", usuariosIdP=" + usuariosId +
                '}';
    }
}


