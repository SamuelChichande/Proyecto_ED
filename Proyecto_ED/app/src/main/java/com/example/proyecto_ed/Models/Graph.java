package com.example.proyecto_ed.Models;

import java.util.Comparator;
import java.util.LinkedList;

public class Graph<Aeropuerto, E>{
    private LinkedList<VertexAeropuerto<Aeropuerto, E>> vertices;
    private boolean isDirected;
    private Comparator<Aeropuerto> cmp;

    public Graph(Comparator<Aeropuerto> cmp) {
        this.isDirected = true;
        this.cmp = cmp;
        this.vertices = new LinkedList<>();
    }
}
