package com.example.proyecto_ed.Models;

import java.util.LinkedList;
import java.util.List;

public class Aeropuerto {
    private String codigo;
    private String nombre;
    private String ciudad;
    private String pais;
    private Ubicacion ubicacion;
    private List<Vuelo> vuelos;

    public Aeropuerto(String codigo, String nombre, String ciudad, String pais, Ubicacion ubicacion) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.pais = pais;
        this.ubicacion = ubicacion;
        this.vuelos = new LinkedList<>();
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

    public Ubicacion getUbicacion(){
        return ubicacion;
    };

    public List<Vuelo> getVuelos() {
        return vuelos;
    }
}
