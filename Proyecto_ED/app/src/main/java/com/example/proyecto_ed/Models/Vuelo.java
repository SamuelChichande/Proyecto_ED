package com.example.proyecto_ed.Models;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class Vuelo {
    private int id;
    private String origen;
    private String destino;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegada;
    private LocalTime horaLlegada;
    private Duration duracion;
    //La distancia sera en km
    private double distancia;
    private String aerolinea;
    private int capacidad;
    private int asientosDisponibles;
    private int precio;
    private List<Cliente> pasajeros;

    public Vuelo(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, double distancia, String aerolinea, int capacidad, int asientosDisponibles, int precio) {
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.distancia = distancia;
        this.aerolinea = aerolinea;
        this.capacidad = capacidad;
        this.asientosDisponibles = asientosDisponibles;
        this.precio = precio;
        this.pasajeros = new LinkedList<>();
        this.duracion = calcularDuracion();
    }

    private Duration calcularDuracion(){
        return Duration.between(this.horaSalida, this.horaLlegada);
    }


}
