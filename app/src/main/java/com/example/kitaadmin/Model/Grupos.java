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

    public Grupos(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

        public String getNombreGrupo () {
            return nombreGrupo;
        }

        public void setNombreGrupo (String nombreGrupo){
            this.nombreGrupo = nombreGrupo;
        }




    @Override
    public String toString() {
        return "Grupos{" +
                "nombreGrupo='" + nombreGrupo + '\'' +
                '}';
    }
}
