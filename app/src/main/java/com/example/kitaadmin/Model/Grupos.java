package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;
/**
 * Clase que representa la tabla grupos de la base de datos
 */
@Generated("com.robohorse.robopojogenerator")
public class Grupos implements Serializable {
    @SerializedName("nombre")
    private String nombreGrupo;

    public Grupos(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }


    @Override
    public String toString() {
        return "Grupos{" +
                "nombreGrupo='" + nombreGrupo + '\'' +
                '}';
    }
}
