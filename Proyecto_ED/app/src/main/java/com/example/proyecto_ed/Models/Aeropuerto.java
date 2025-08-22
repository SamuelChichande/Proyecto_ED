package com.example.proyecto_ed.Models;

import java.util.LinkedList;
import java.util.List;

public class Aeropuerto {
    private int id;
    private String nombre;
    private String codigo;
    private String ciudad;
    private String pais;
    private List<Vuelo> vuelos;

    public Aeropuerto(int id, String nombre, String codigo, String ciudad, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
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

    public String getCodigo() {
        return codigo;
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
