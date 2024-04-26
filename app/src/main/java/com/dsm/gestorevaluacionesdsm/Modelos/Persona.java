package com.dsm.gestorevaluacionesdsm.Modelos;

public class Persona {
    private int idPersona;
    private String nombrePersona;
    private String apellidoPersona;
    private String telefonoPersona;
    private String correoPersona;
    private String usuarioPersona;
    private String contrasenaPersona;
    private String tipoUsuarioPersona;

    public Persona() {
    }

    public Persona(int idPersona, String nombrePersona, String apellidoPersona, String telefonoPersona, String correoPersona, String usuarioPersona, String contrasenaPersona, String tipoUsuarioPersona) {
        this.idPersona = idPersona;
        this.nombrePersona = nombrePersona;
        this.apellidoPersona = apellidoPersona;
        this.telefonoPersona = telefonoPersona;
        this.correoPersona = correoPersona;
        this.usuarioPersona = usuarioPersona;
        this.contrasenaPersona = contrasenaPersona;
        this.tipoUsuarioPersona = tipoUsuarioPersona;
    }

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public String getApellidoPersona() {
        return apellidoPersona;
    }

    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }

    public String getTelefonoPersona() {
        return telefonoPersona;
    }

    public void setTelefonoPersona(String telefonoPersona) {
        this.telefonoPersona = telefonoPersona;
    }

    public String getCorreoPersona() {
        return correoPersona;
    }

    public void setCorreoPersona(String correoPersona) {
        this.correoPersona = correoPersona;
    }

    public String getUsuarioPersona() {
        return usuarioPersona;
    }

    public void setUsuarioPersona(String usuarioPersona) {
        this.usuarioPersona = usuarioPersona;
    }

    public String getContrasenaPersona() {
        return contrasenaPersona;
    }

    public void setContrasenaPersona(String contrasenaPersona) {
        this.contrasenaPersona = contrasenaPersona;
    }

    public String getTipoUsuarioPersona() {
        return tipoUsuarioPersona;
    }

    public void setTipoUsuarioPersona(String tipoUsuarioPersona) {
        this.tipoUsuarioPersona = tipoUsuarioPersona;
    }
}
