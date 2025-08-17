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

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getPais() {
        return pais;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }
}
