package com.example.proyecto_ed.Models;

import java.util.List;

public class ReservarVuelo {
    private int id;
    private int idUser;
    private String nameUser;
    private List<Vuelo> vuelos;

    public ReservarVuelo(int id, int idUser, String nameUser, List<Vuelo> vuelo) {
        this.id = id;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.vuelos = vuelo;
    }
    /*
    private Usuario pasajero;
    private List<Vuelo> vuelos;

    public ReservarVuelo(int id, Usuario pasajero, List<Vuelo> vuelo) {
        this.id = id;
        this.pasajero = pasajero;
        this.vuelos = vuelo;
    }
    */

    public int getId(){
        return id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public List<Vuelo> getVuelos() {
        return vuelos;
    }
}
