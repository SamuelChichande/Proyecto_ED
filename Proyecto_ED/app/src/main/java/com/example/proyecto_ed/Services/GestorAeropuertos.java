package com.example.proyecto_ed.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Ubicacion;

import java.util.LinkedList;
import java.util.List;

public class GestorAeropuertos {
    private static GestorAeropuertos instance;
    private LinkedList<Aeropuerto> aeropuertos;
    private static final String NAME_FILE_AEROPUERTOS = "Aeropuertos.txt";

    private GestorAeropuertos(){
        this.aeropuertos = new LinkedList<>();
    }

    public static GestorAeropuertos getInstance(){
        if (instance == null) {
            instance = new GestorAeropuertos();
        }
        return instance;
    }

    public void cargarAeropuerto(Context context){
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE_AEROPUERTOS);
        for (String content: contenidos){

            try {
                String[] componentes = content.split(",");

                Ubicacion ubicacion = new Ubicacion(
                        Double.parseDouble(componentes[5]), Double.parseDouble(componentes[6]));
                Aeropuerto a = new Aeropuerto(
                        Integer.parseInt(componentes[0]),
                        componentes[1],componentes[2],
                        componentes[3],componentes[4],
                        ubicacion);
                agregarAeropuerto(a);

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Existe un error en los datos guardados del archivo "+NAME_FILE_AEROPUERTOS, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al momento de cargar los vuelos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void agregarAeropuerto(Aeropuerto a){
        if (a == null) return;
        aeropuertos.add(a);
    }

    public void eliminarAeropuerto(int id){
        if (id < 0 || id > aeropuertos.size()) return;

        for (Aeropuerto aeropuerto: aeropuertos){
            if (aeropuerto.getId() == id) aeropuertos.remove(id);
        }
    }

    public void modificarAeropuerto(int id, Aeropuerto aeropuertoModificado){
        if (id < 0 || id > aeropuertos.size() || aeropuertoModificado == null) return;

        for (Aeropuerto aeropuerto: aeropuertos){
            if (aeropuerto.getId() == id) aeropuertos.add(id, aeropuertoModificado);
        }
    }

    public int getLastID(){
        return aeropuertos.getLast().getId();
    }

    public LinkedList<Aeropuerto> getAeropuertos(){
        return aeropuertos;
    }
}
