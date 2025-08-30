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
    private double precio;
    private List<Cliente> pasajeros;

    public Vuelo(int id, String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, double distancia, String aerolinea, int capacidad, int asientosDisponibles, double precio) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public Duration getDuracion() {
        return duracion;
    }

    public void setDuracion(Duration duracion) {
        this.duracion = duracion;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getAerolinea() {
        return aerolinea;
    }

    public void setAerolinea(String aerolinea) {
        this.aerolinea = aerolinea;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getAsientosDisponibles() {
        return asientosDisponibles;
    }

    public void setAsientosDisponibles(int asientosDisponibles) {
        this.asientosDisponibles = asientosDisponibles;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public List<Cliente> getPasajeros() {
        return pasajeros;
    }

    public void setPasajeros(List<Cliente> pasajeros) {
        this.pasajeros = pasajeros;
    }

    @Override
    public String toString() {
        return "Vuelo "+ id +
                "\nfechaSalida=" + fechaSalida +
                ", horaSalida=" + horaSalida +
                ", fechaLlegada=" + fechaLlegada +
                ", horaLlegada=" + horaLlegada +
                ", aerolinea='" + aerolinea +
                ", precio=" + precio;
    }
}
