package com.example.proyecto_ed.Services;

import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Models.Vuelo;

public class ReservarVuelo {
    private int id;
    private Usuario pasajero;
    private Vuelo vuelo;

    public ReservarVuelo(int id, Usuario pasajero, Vuelo vuelo) {
        this.id = id;
        this.pasajero = pasajero;
        this.vuelo = vuelo;
    }

}
