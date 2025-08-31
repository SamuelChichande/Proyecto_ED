package com.example.proyecto_ed.Td.TDA_GRAFO;

import com.example.proyecto_ed.Models.Vuelo;

import java.time.Duration;

public class EdgeVuelo {
    private VertexAeropuerto source;
    private VertexAeropuerto target;
    private Duration weight;
    private Vuelo data;

    public EdgeVuelo(VertexAeropuerto source, VertexAeropuerto target, Duration weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public VertexAeropuerto getSource() {
        return source;
    }

    public VertexAeropuerto getTarget() {
        return target;
    }

    public Duration getWeight() {
        return weight;
    }

    public Vuelo getData() {
        return data;
    }
}
