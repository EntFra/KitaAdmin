package com.example.kitaadmin.Model;

public class Infomacion {

    private int informacion_id;
    private String informacion;
    private String nombre_grupo;

    public Infomacion(String informacion, String nombre_grupo) {
        this.informacion = informacion;
        this.nombre_grupo = nombre_grupo;
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

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }
}
