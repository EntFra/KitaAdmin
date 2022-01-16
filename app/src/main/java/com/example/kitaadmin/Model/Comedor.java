package com.example.kitaadmin.Model;

/**
 * Clase Comedor que representa el menu del dia
 */
public class Comedor {

    private int menu_id;
    private int dia_id;
    private String snack;
    private String desayuno;
    private String plato_principal;
    private String postre;
    private String nombre_grupo;

    public Comedor(int dia_id, String snack, String desayuno, String plato_principal, String postre, String nombre_grupo) {
        this.dia_id = dia_id;
        this.snack = snack;
        this.desayuno = desayuno;
        this.plato_principal = plato_principal;
        this.postre = postre;
        this.nombre_grupo = nombre_grupo;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public int getDia_id() {
        return dia_id;
    }

    public void setDia_id(int dia_id) {
        this.dia_id = dia_id;
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

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }
}
