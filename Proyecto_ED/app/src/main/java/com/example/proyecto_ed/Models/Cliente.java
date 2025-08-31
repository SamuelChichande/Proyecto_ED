package com.example.proyecto_ed.Models;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario{
    private List<ReservarVuelo> reservas;

    public Cliente(int idUser, String nombre, String apellido, String cedula, String usuario, String contraseña) {
        super(idUser, nombre, apellido, cedula, usuario, contraseña);
        this.isAdmin = false;
        this.reservas = new ArrayList<>();
    }

    public List<ReservarVuelo> getReservas(){
        return reservas;
    }

    public boolean reservarVuelo(ReservarVuelo reservarVuelo){
        if (reservarVuelo == null) return false;
        reservas.add(reservarVuelo);
        return true;
    }

    public boolean cancelarReserva(int idReserva){
        if (idReserva < 0) return false;
        for (ReservarVuelo reservarVuelo: reservas){
            if (reservarVuelo.getId() == idReserva) reservas.remove(reservarVuelo);
        }
        return true;
    }

}
