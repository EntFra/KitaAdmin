package com.example.kitaadmin.Model;

public class Alumno {

    private int alumno_id;
    private String nombre;
    private String alergias;
    private String observaciones;
    private boolean auto_salidas;
    private boolean auto_fotos;
    private boolean comedor;
    private String nombre_grupo;
    private int fecha_nacimiento;

    public Alumno(String nombre, String alergias, String observaciones, boolean auto_salidas, boolean auto_fotos, boolean comedor, String nombre_grupo, int fecha_nacimiento) {

        this.nombre = nombre;
        this.alergias = alergias;
        this.observaciones = observaciones;
        this.auto_salidas = auto_salidas;
        this.auto_fotos = auto_fotos;
        this.comedor = comedor;
        this.nombre_grupo = nombre_grupo;
        this.fecha_nacimiento = fecha_nacimiento;
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isAuto_salidas() {
        return auto_salidas;
    }

    public void setAuto_salidas(boolean auto_salidas) {
        this.auto_salidas = auto_salidas;
    }

    public boolean isAuto_fotos() {
        return auto_fotos;
    }

    public void setAuto_fotos(boolean auto_fotos) {
        this.auto_fotos = auto_fotos;
    }

    public boolean isComedor() {
        return comedor;
    }

    public void setComedor(boolean comedor) {
        this.comedor = comedor;
    }

    public String getNombre_grupo() {
        return nombre_grupo;
    }

    public void setNombre_grupo(String nombre_grupo) {
        this.nombre_grupo = nombre_grupo;
    }

    public int getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(int fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }
}
