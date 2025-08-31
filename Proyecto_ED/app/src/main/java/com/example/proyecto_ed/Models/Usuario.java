package com.example.proyecto_ed.Models;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
    protected int idUser;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String usuario;
    protected String contraseña;
    protected boolean isAdmin;

    public Usuario(int idUser, String nombre, String apellido, String cedula, String usuario, String contraseña) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public String getUsuario(){
        return usuario;
    }

    public String getContraseña(){
        return contraseña;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        return idUser+","+nombre+","+apellido+","+cedula+","+usuario+","+contraseña;
    }
}
