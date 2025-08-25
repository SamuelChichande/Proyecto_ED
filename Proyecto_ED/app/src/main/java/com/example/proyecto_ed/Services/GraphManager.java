package com.example.proyecto_ed.Services;

import com.example.proyecto_ed.Models.Graph;
import com.example.proyecto_ed.Models.VertexAeropuerto;

import java.util.Comparator;
import java.util.LinkedList;

public class GraphManager<Aeropuerto, E> {
    private static GraphManager instance;
    private Graph<Aeropuerto, E> graph;
    private Comparator<Aeropuerto> comparator;
    private GraphManager(Comparator<Aeropuerto> comparator) {
        this.comparator = comparator;
        this.graph = new Graph<>(comparator);
    }

    // Método para obtener la instancia única (Singleton)
    public static <A, T> GraphManager<A, T> getInstance(Comparator<A> comparator) {
        if (instance == null) {
            synchronized (GraphManager.class) {
                if (instance == null) {
                    instance = new GraphManager<>(comparator);
                }
            }
        }
        return instance;
    }

    // Método para obtener la instancia (si ya fue creada)
    public static GraphManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GraphManager no ha sido inicializado. Use getInstance(Comparator) primero.");
        }
        return instance;
    }

    //Método para actualizar la lista de vértices cuando se agrega uno nuevo
    public void actualizarListaVertices(Aeropuerto a) {
        graph.agregarAeropuerto(a);
    }

    // Getters
    public Graph<Aeropuerto, E> getGraph() {
        return graph;
    }

    // Método para resetear la instancia (útil para testing)
    public static void resetInstance() {
        instance = null;
    }
}
