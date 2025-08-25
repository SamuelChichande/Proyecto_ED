package com.example.proyecto_ed.Services;

import android.content.Context;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Ubicacion;

import java.util.LinkedList;
import java.util.List;

public class GestorAeropuertos {
    private static GestorAeropuertos instance;
    private LinkedList<Aeropuerto> aeropuertos;
    private static final String NAME_FILE = "Aeropuertos.txt";

    private GestorAeropuertos(){
        this.aeropuertos = new LinkedList<>();
    }

    public static GestorAeropuertos getInstance(){
        if (instance == null) {
            instance = new GestorAeropuertos();
        }
        return instance;
    }

    public void agregarAeropuerto(Aeropuerto a){
        if (a == null) return;
        aeropuertos.add(a);
    }

    public void modificarAeropuerto(){}

    public void eliminarAeropuerto(){}

    public void cargarAeropuerto(Context context){
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE);
        for (String content: contenidos){
            String[] componentes = content.split(",");
            Ubicacion ubicacion = new Ubicacion(Double.parseDouble(componentes[5]), Double.parseDouble(componentes[6]));
            Aeropuerto a = new Aeropuerto(Integer.parseInt(componentes[0]),componentes[1],componentes[2],componentes[3],
                    componentes[4], ubicacion);
            agregarAeropuerto(a);
        }
    }

    public LinkedList<Aeropuerto> getAeropuertos(){
        return aeropuertos;
    }
}
