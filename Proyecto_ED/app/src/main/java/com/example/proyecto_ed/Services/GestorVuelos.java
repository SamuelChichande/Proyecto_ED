package com.example.proyecto_ed.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.proyecto_ed.Models.Vuelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

public class GestorVuelos {
    private static GestorVuelos instance;
    private LinkedList<Vuelo> vuelos;
    private static final String NAME_FILE_VUELOS = "vuelos.txt";

    private GestorVuelos(){
        this.vuelos = new LinkedList<>();
    }

    public static GestorVuelos getInstance(){
        if (instance == null) {
            instance = new GestorVuelos();
        }
        return instance;
    }

    public void cargarVuelos(Context context){
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE_VUELOS);
        for (String content: contenidos){

            try {
                String[] componentes = content.split(",");

                Vuelo vuelo = new Vuelo(
                        Integer.parseInt(componentes[0]),    //ID
                        componentes[1], componentes[2],      //Claves de aeropuertos de origen y destino
                        LocalDate.parse(componentes[3]),     //Fecha de salida
                        LocalTime.parse(componentes[4]),     //Hora de Salida
                        LocalDate.parse(componentes[5]),     //Fecha de llegada
                        LocalTime.parse(componentes[6]),     //Hora de llegada
                        Double.parseDouble(componentes[8]),  //Distancia
                        componentes[9],                      //Nombre de aerolinea
                        Integer.parseInt(componentes[10]),   //Capacidad
                        Integer.parseInt(componentes[11]),   //Asientos disponibles
                        Double.parseDouble(componentes[12])  //Precio
                );
                agregarVuelos(vuelo);

            } catch (DateTimeParseException e) {
                Toast.makeText(context, "Ha ocurrido un error al transformar los datos", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Existe un error en los datos guardados del archivo "+NAME_FILE_VUELOS, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al momento de cargar los vuelos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void agregarVuelos(Vuelo vuelo){
        if (vuelo == null) return;
        vuelos.add(vuelo);
    }

    public void eliminarVuelo(int id){
        if (id < 0 || id > vuelos.size()) return;

        for (Vuelo vuelo: vuelos){
            if (vuelo.getId() == id) vuelos.remove(id);
        }
    }

    public void modificarVuelo(int id, Vuelo vueloModificado){
        if (id < 0 || id > vuelos.size() || vueloModificado == null) return;

        for (Vuelo vuelo: vuelos){
            if (vuelo.getId() == id) vuelos.add(id, vueloModificado);
        }
    }

    public int getLastID(){
        return vuelos.getLast().getId();
    }

    public LinkedList<Vuelo> getVuelos(){
        return vuelos;
    }
}
