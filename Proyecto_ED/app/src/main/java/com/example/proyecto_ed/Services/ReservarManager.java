package com.example.proyecto_ed.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.proyecto_ed.Models.ReservarVuelo;
import com.example.proyecto_ed.Models.Vuelo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReservarManager {
    private static ReservarManager instance;
    private LinkedList<ReservarVuelo> reservas;
    private static final String NAME_FILE_RESERVAS = "Reservas.txt";
    private final GestorVuelos gestorVuelos = GestorVuelos.getInstance();

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
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE_RESERVAS);

        if (contenidos == null) return;

        for (String content: contenidos){

            try {
                String[] componentes = content.split(",");
                String[] idVuelos = componentes[3].split("-");

                List<Vuelo> vueloList = new ArrayList<>();

                for (Vuelo vuelo: gestorVuelos.getVuelos()){
                    int i = 0;
                    while (i < idVuelos.length){
                        if (vuelo.getId() == Integer.parseInt(idVuelos[i])){
                            vueloList.add(vuelo);
                            break;
                        }
                        i++;
                    }
                }

                ReservarVuelo reservarVuelo = new ReservarVuelo(
                        Integer.parseInt(componentes[0]),
                        Integer.parseInt(componentes[1]),
                        componentes[2],
                        vueloList
                );

                agregarReserva(reservarVuelo);

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Existe un error en los datos guardados del archivo "+NAME_FILE_RESERVAS, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al momento de cargar las reservas", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void agregarReserva(ReservarVuelo rv){
        if (rv == null) return;
        reservas.add(rv);
    }

    public void eliminarReserva(ReservarVuelo rv){
        if (rv == null) return;
        reservas.remove(rv);
    }

    public LinkedList<ReservarVuelo> getReservas(){
        return reservas;
    }
}
