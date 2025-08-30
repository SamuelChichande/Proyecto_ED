package com.example.proyecto_ed.Td;

import java.time.Duration;

public class EdgeVuelo<E, Aeropuerto> {
    private E data;
    private VertexAeropuerto<Aeropuerto, E> source;
    private VertexAeropuerto<Aeropuerto, E> target;
    private Duration weight;

    public EdgeVuelo(E data, VertexAeropuerto<Aeropuerto, E> source, VertexAeropuerto<Aeropuerto, E> target, Duration weight) {
        this.data = data;
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    public EdgeVuelo(VertexAeropuerto<Aeropuerto, E> source, Duration weight, VertexAeropuerto<Aeropuerto, E> target) {
        this.source = source;
        this.weight = weight;
        this.target = target;
        this.data = null;
    }

    public E getData() {
        return data;
    }

    public VertexAeropuerto<Aeropuerto, E> getSource() {
        return source;
    }

    public VertexAeropuerto<Aeropuerto, E> getTarget() {
        return target;
    }

    public Duration getWeight() {
        return weight;
    }
}
