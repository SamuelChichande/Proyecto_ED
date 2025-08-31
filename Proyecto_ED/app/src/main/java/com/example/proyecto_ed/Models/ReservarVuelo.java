package com.example.proyecto_ed.Models;

import java.util.List;

public class ReservarVuelo {
    private int id;
    private Usuario pasajero;
    private List<Vuelo> vuelos;

    public ReservarVuelo(int id, Usuario pasajero, List<Vuelo> vuelo) {
        this.id = id;
        this.pasajero = pasajero;
        this.vuelos = vuelo;
    }

    public int getId(){
        return id;
    }

}
