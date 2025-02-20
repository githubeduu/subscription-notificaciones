package com.example.subscription_notificaciones.DTO;

public class ClienteNotificacionDTO {
    private String nombre;
    private String correo;
    private String rut;

    public ClienteNotificacionDTO() {}

    public ClienteNotificacionDTO(String nombre, String correo, String rut) {
        this.nombre = nombre;
        this.correo = correo;
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }
}
