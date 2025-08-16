package com.example.proyecto_ed.Models;

import java.util.LinkedList;
import java.util.List;

public class Aeropuerto {
    private int id;
    private String nombre;
    private String ciudad;
    private String pais;
    private List<Vuelo> vuelos;

    public Aeropuerto(int id, String nombre, String ciudad, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.pais = pais;
        this.vuelos = new LinkedList<>();
    }

}
