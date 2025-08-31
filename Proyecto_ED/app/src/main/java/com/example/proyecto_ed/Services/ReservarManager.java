package com.example.proyecto_ed.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Ubicacion;
import com.example.proyecto_ed.ReservaVuelo;

import java.util.LinkedList;
import java.util.List;

public class ReservarManager {
    private static ReservarManager instance;
    private LinkedList<ReservaVuelo> reservas;
    private static final String NAME_FILE_Reservas = "Reservas.txt";

    private ReservarManager(){
        this.reservas = new LinkedList<>();
    }

    public static ReservarManager getInstance(){
        if (instance == null) {
            instance = new ReservarManager();
        }
        return instance;
    }

    public void cargarReservas(Context context){
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE_Reservas);
        for (String content: contenidos){

            try {
                String[] componentes = content.split(",");

                

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Existe un error en los datos guardados del archivo "+NAME_FILE_Reservas, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al momento de cargar los vuelos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
