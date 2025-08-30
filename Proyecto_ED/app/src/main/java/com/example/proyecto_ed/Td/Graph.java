package com.example.proyecto_ed.Td;

import java.time.Duration;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Graph<Aeropuerto, E>{
    //El grafo siempre sera dirigido
    private LinkedList<VertexAeropuerto<Aeropuerto, E>> vertices;
    private Comparator<Aeropuerto> cmp;

    public Graph(Comparator<Aeropuerto> cmp) {
        this.cmp = cmp;
        this.vertices = new LinkedList<>();
    }

    public boolean agregarAeropuerto(Aeropuerto a){
        if (a == null || findVertex(a) == null) return false;
        VertexAeropuerto<Aeropuerto, E> v = new VertexAeropuerto(a);
        vertices.addLast(v);
        return true;
    }

    private VertexAeropuerto<Aeropuerto, E> findVertex(Aeropuerto content){
        if (content == null) return null;
        Iterator<VertexAeropuerto<Aeropuerto, E>> it = vertices.iterator();
        while (it.hasNext()){
            VertexAeropuerto<Aeropuerto, E> v = it.next();
            if (cmp.compare(v.getContent(), content) == 0) return v;
        }
        return null;
    }

    public boolean eliminarAeropuerto(Aeropuerto a){
        VertexAeropuerto<Aeropuerto, E> v = findVertex(a);
        if (a == null || v == null) return false;
        eliminarRutasOtrosAeropuertos(v);
        vertices.remove(v);
        return true;
    }

    private void eliminarRutasOtrosAeropuertos(VertexAeropuerto<Aeropuerto, E> v){
        //Elimina todas las conexiones de los demas vertices a este

    }

    public boolean agregarVuelo(Aeropuerto origen, Aeropuerto destino, Duration weight, E data){
        VertexAeropuerto<Aeropuerto, E> vOrigen = findVertex(origen);
        VertexAeropuerto<Aeropuerto, E> vDestino = findVertex(destino);
        if (origen == null || vOrigen == null || destino == null ||
                vDestino == null || weight == null) return false;

        vOrigen.getEdgesVuelos().add(new EdgeVuelo(data, vOrigen, vDestino, weight));
        return true;
    }

    public void buscarRutaMasCorta(Aeropuerto origen, Aeropuerto destino){

    }

    public void getEstadisticaConectividad(){

    }
}
