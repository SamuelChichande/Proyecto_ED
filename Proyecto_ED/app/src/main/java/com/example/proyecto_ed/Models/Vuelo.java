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
    private String aerolinea;
    private Estado estado;
    private int capacidad;
    private int asientosDisponibles;
    private int precioBase;
    private List<Cliente> pasajeros;

    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, String aerolinea, Estado estado, int capacidad, int asientosDisponibles, int precioBase) {
        this.id = id;
        this.origen = origen;
        this.destino = destino;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.aerolinea = aerolinea;
        this.estado = estado;
        this.capacidad = capacidad;
        this.asientosDisponibles = asientosDisponibles;
        this.precioBase = precioBase;
        this.pasajeros = new LinkedList<>();
        this.duracion = calcularDuracion();
    }

    private Duration calcularDuracion(){
        return Duration.between(this.horaSalida, this.horaLlegada);
    }


}
