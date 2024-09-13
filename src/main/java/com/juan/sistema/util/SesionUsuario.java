package com.juan.sistema.util;

public class SesionUsuario {
    private static SesionUsuario instance;
    private String usuario;
    private String email;

    private SesionUsuario() {}

    // Patron Singleton
    public static SesionUsuario getInstance() {
        if(instance == null) {
            instance = new SesionUsuario();
        }
        return instance;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
