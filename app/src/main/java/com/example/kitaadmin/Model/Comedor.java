package com.example.kitaadmin.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import javax.annotation.Generated;

/**
 * Clase Comedor que representa el menu del dia
 */
@Generated("com.robohorse.robopojogenerator")
public class Comedor implements Serializable {

    @SerializedName("menu_id")
    private int menu_id;
    @SerializedName("fecha")
    private String fecha;
    @SerializedName("snack")
    private String snack;
    @SerializedName("desayuno")
    private String desayuno;
    @SerializedName("plato_principal")
    private String plato_principal;
    @SerializedName("postre")
    private String postre;

    public Comedor(int menu_id, String dia_id, String snack, String desayuno, String plato_principal, String postre) {
        this.menu_id = menu_id;
        this.fecha = dia_id;
        this.snack = snack;
        this.desayuno = desayuno;
        this.plato_principal = plato_principal;
        this.postre = postre;
    }

    public Comedor() {
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSnack() {
        return snack;
    }

    public void setSnack(String snack) {
        this.snack = snack;
    }

    public String getDesayuno() {
        return desayuno;
    }

    public void setDesayuno(String desayuno) {
        this.desayuno = desayuno;
    }

    public String getPlato_principal() {
        return plato_principal;
    }

    public void setPlato_principal(String plato_principal) {
        this.plato_principal = plato_principal;
    }

    public String getPostre() {
        return postre;
    }

    public void setPostre(String postre) {
        this.postre = postre;
    }

    @Override
    public String toString() {
        return "Comedor{" +
                "menu_id=" + menu_id +
                ", dia_id=" + fecha +
                ", snack='" + snack + '\'' +
                ", desayuno='" + desayuno + '\'' +
                ", plato_principal='" + plato_principal + '\'' +
                ", postre='" + postre + '\'' +
                '}';
    }
}
