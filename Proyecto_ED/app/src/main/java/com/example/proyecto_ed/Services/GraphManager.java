package com.example.proyecto_ed.Services;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Td.TDA_GRAFO.Graph;

import java.time.Duration;
import java.util.Comparator;

public class GraphManager {
    private static GraphManager instance;
    private static Graph graph;
    private static Comparator<String> comparator;
    private GraphManager(Comparator<String> cmp) {
        this.comparator = cmp;
        this.graph = new Graph(comparator);
    }

    public static GraphManager getInstance(Comparator<String> cmp) {
        if (instance == null) {
            instance = new GraphManager(cmp);
        }
        return instance;
    }

    public void actualizarListaVertices(Aeropuerto a) {
        graph.agregarAeropuerto(a);
    }

    public void agregarConexion(String codigoOrigen, String codigoDestino, Duration weight){
        graph.agregarVuelo(codigoOrigen, codigoDestino, weight);
    }

    // Getters
    public Graph getGraph() {
        return graph;
    }

}
