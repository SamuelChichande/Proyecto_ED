package com.example.proyecto_ed.Models;

public class Cliente extends Usuario{
    public Cliente(String nombre, String apellido, String cedula, String usuario, String contraseña) {
        super(nombre, apellido, cedula, usuario, contraseña);
        this.isAdmin = false;
    }

    public void reservarVuelo(){

    }

    public void cancelarReserva(){

    }

}
