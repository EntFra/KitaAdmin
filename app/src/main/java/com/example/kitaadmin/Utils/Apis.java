package com.example.kitaadmin.Utils;

public class Apis {

    public static final String URL_001 = "localhost:8080";

    //Obtiene la conexion al servicio
    public static AlumnoService getAlumnoService() {
        return Client.getClient(URL_001).create(AlumnoService.class);
    }
}
