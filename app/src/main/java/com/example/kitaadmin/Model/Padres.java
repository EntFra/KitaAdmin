package com.example.kitaadmin.Model;

/**
 * Clase Padres que representa un objeto padre, contiene los datos del id padre e id del niño con el que estan relacionados
 */
public class Padres {

    private int alumno_id;
    private int usuarios_id_pad;

    public Padres(int alumno_id, int usuarios_id_pad) {
        this.alumno_id = alumno_id;
        this.usuarios_id_pad = usuarios_id_pad;
    }

    public int getAlumno_id() {
        return alumno_id;
    }

    public void setAlumno_id(int alumno_id) {
        this.alumno_id = alumno_id;
    }

    public int getUsuarios_id_pad() {
        return usuarios_id_pad;
    }

    public void setUsuarios_id_pad(int usuarios_id_pad) {
        this.usuarios_id_pad = usuarios_id_pad;
    }
}
