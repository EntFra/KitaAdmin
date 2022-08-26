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
    @SerializedName("usuariosIdPad")
    private int usuariosIdPad;

    public Padres(int alumnoId, int usuariosIdPad) {
        this.alumnoId = alumnoId;
        this.usuariosIdPad = usuariosIdPad;
    }

    public int getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(int alumnoId) {
        this.alumnoId = alumnoId;
    }

    public int getUsuariosIdPad() {
        return usuariosIdPad;
    }

    public void setUsuariosIdPad(int usuariosIdPad) {
        this.usuariosIdPad = usuariosIdPad;
    }

    @Override
    public String toString() {
        return "Padres{" +
                "alumno_id=" + alumnoId +
                ", usuariosIdPad=" + usuariosIdPad +
                '}';
    }
}


