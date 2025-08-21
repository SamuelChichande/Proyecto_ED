package com.example.proyecto_ed.Models;

public abstract class Usuario {
    protected int idUser;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String usuario;
    protected String contraseña;
    protected boolean isAdmin;

    public Usuario(String nombre, String apellido, String cedula, String usuario, String contraseña) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    @Override
    public String toString() {
        return idUser+","+nombre+","+apellido+","+cedula+","+usuario+","+contraseña;
    }
}
