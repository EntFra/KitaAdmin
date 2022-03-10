package com.example.kitaadmin.Model;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kitaadmin.R;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

@Generated("com.robohorse.robopojogenerator")
public class Grupos implements Serializable {
    @SerializedName("nombre")
    private String nombreGrupo;
    private byte[] fotoGrupo;

    public Grupos(String nombreGrupo, byte[] fotoGrupo) {
        this.nombreGrupo = nombreGrupo;
        this.fotoGrupo = fotoGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public byte[] getFotoGrupo() {
        return fotoGrupo;
    }

    public void setFotoGrupo(byte[] fotoGrupo) {
        this.fotoGrupo = fotoGrupo;
    }

    @Override
    public String toString() {
        return
                "ResponseClass{" +
                        "nombre = '" + nombreGrupo + '\'' +
                        "}";
    }
}
