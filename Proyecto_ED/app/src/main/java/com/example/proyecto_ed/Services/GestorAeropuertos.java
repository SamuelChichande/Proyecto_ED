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
                        Double.parseDouble(componentes[4]), Double.parseDouble(componentes[5]));
                Aeropuerto a = new Aeropuerto(
                        componentes[0],componentes[1],
                        componentes[2],componentes[3],
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

    public void eliminarAeropuerto(String codigo){
        if (codigo == null || codigo.isEmpty()) return;

        for (Aeropuerto aeropuerto: aeropuertos){
            if (aeropuerto.getCodigo() == codigo) aeropuertos.removeIf(aeropuerto1 -> aeropuerto1.getCodigo().equals(codigo));
        }
    }

    public void modificarAeropuerto(String codigo, Aeropuerto aeropuertoModificado){
        if (codigo == null || codigo.isEmpty() || aeropuertoModificado == null) return;

        for (int i = 0; i < aeropuertos.size(); i++){
            if (aeropuertos.get(i).getCodigo() == codigo) aeropuertos.add(i, aeropuertoModificado);
        }
    }

    public String getLastID(){
        return aeropuertos.getLast().getCodigo();
    }

    public LinkedList<Aeropuerto> getAeropuertos(){
        return aeropuertos;
    }
}
