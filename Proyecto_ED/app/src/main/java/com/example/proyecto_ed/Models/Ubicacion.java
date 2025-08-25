package com.example.proyecto_ed.Models;

public class Ubicacion {
    private double latitud;
    private double longitud;

    public Ubicacion(double lat, double lon) {
        this.latitud = lat;
        this.longitud = lon;
    }

    // Getters y setters
    public double getLatitud() { return latitud; }
    public double getLongitud() { return longitud; }
}
