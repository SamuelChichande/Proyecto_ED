package com.example.proyecto_ed.Td.TDA_GRAFO;

import com.example.proyecto_ed.Models.Aeropuerto;

import java.util.LinkedList;

public class VertexAeropuerto {
        private Aeropuerto content;
        private LinkedList<EdgeVuelo> edgesVuelos;
        boolean isVisited;
        int cummulativeDistance;
        VertexAeropuerto predecesor;

    public VertexAeropuerto(Aeropuerto content) {
        this.content = content;
        this.edgesVuelos = new LinkedList<>();
        this.isVisited = false;
        this.cummulativeDistance = 0;
        this.predecesor = null;
    }

    public Aeropuerto getContent() {
        return content;
    }

    public LinkedList<EdgeVuelo> getEdgesVuelos() {
        return edgesVuelos;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public int getCummulativeDistance() {
        return cummulativeDistance;
    }

    public void setCummulativeDistance(int cd) {
        this.cummulativeDistance = cd;
    }

    public VertexAeropuerto getPredecesor() {
        return predecesor;
    }

    public void setPredecesor(VertexAeropuerto p) {
        this.predecesor = p;
    }
}
