package com.example.proyecto_ed.Models;

public abstract class Usuario {
    protected int idUser;
    protected String nombre;
    protected String apellido;
    protected String cedula;
    protected String usuario;
    protected String contraseña;

    public Usuario(int idUser, String nombre, String apellido, String cedula, String usuario, String contraseña) {
        this.idUser = idUser;
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }
}
