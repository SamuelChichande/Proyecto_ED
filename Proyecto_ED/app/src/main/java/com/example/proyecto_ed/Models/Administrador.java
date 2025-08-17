package com.example.proyecto_ed.Models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Administrador extends Usuario{

    public Administrador(int idUser, String nombre, String apellido, String cedula, String usuario, String contraseña) {
        super(idUser, nombre, apellido, cedula, usuario, contraseña);
    }

    public void crearVuelo(String origen, String destino, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, double distancia, String aerolinea, int capacidad, int asientosDisponibles, int precio){
        Vuelo v = new Vuelo(origen, destino, fechaSalida, horaSalida, fechaLlegada, horaLlegada, distancia, aerolinea, capacidad, asientosDisponibles, precio);
        //Registrarlo en la base de datos

    }

    public void modificarVuelo(){

    }

    public void cancelarVuelo(){

    }


}
