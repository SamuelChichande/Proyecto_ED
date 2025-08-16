package com.example.proyecto_ed.Models;

import java.util.LinkedList;

public class VertexAeropuerto<Aeropuerto, E> {
    private Aeropuerto content;
    private LinkedList<EdgeVuelo<E, Aeropuerto>> edgesVuelos;

    public VertexAeropuerto(Aeropuerto content) {
        this.content = content;
        this.edgesVuelos = new LinkedList<>();
    }
}
