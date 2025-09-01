package com.example.proyecto_ed.Td.TDA_GRAFO;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Vuelo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Graph{
    //El grafo siempre sera dirigido
    private LinkedList<VertexAeropuerto> vertices;
    private Comparator<String> cmp;

    public Graph(Comparator<String> cmp) {
        this.cmp = cmp;
        this.vertices = new LinkedList<>();
    }

    public LinkedList<VertexAeropuerto> getVertices(){
        return vertices;
    }

    public boolean agregarAeropuerto(Aeropuerto a){
        VertexAeropuerto vertex = findVertex(a.getCodigo());
        if (a == null || vertex != null) return false;
        VertexAeropuerto v = new VertexAeropuerto(a);
        vertices.addLast(v);
        return true;
    }

    private VertexAeropuerto findVertex(String content){
        if (content == null || content.isEmpty()) return null;
        Iterator<VertexAeropuerto> it = vertices.iterator();
        while (it.hasNext()){
            VertexAeropuerto v = it.next();
            if (cmp.compare(v.getContent().getCodigo(), content) == 0) return v;
        }
        return null;
    }

    public boolean eliminarAeropuerto(Aeropuerto a){
        VertexAeropuerto v = findVertex(a.getCodigo());
        if (a == null || v == null) return false;
        eliminarRutasOtrosAeropuertos(v);
        vertices.remove(v);
        return true;
    }

    private void eliminarRutasOtrosAeropuertos(VertexAeropuerto v){
        //Elimina todas las conexiones de los demas vertices a este

    }

    public boolean agregarVuelo(String codigoOrigen, String codigoDestino, Duration weight){
        VertexAeropuerto vOrigen = findVertex(codigoOrigen);
        VertexAeropuerto vDestino = findVertex(codigoDestino);
        if (codigoOrigen == null || vOrigen == null || codigoDestino == null ||
                codigoOrigen.equals(codigoDestino) || vDestino == null || weight == null) return false;

        vOrigen.getEdgesVuelos().add(new EdgeVuelo(vOrigen, vDestino, weight));
        return true;
    }

    public boolean eliminarVuelo(String contenido1, String contenido2){
        if (contenido1 == null || contenido1.isEmpty() || contenido2 == null || contenido2.isEmpty()) return false;
        for (VertexAeropuerto vertexAeropuerto: vertices) {
            Aeropuerto aeropuerto = vertexAeropuerto.getContent();
            if (aeropuerto.getCodigo().equals(contenido1)) {
                for (Vuelo vuelo: aeropuerto.getVuelos()) {
                    if (vuelo.getDestino().equals(contenido2)) {
                        vertexAeropuerto.getContent().getVuelos().remove(vuelo);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<VertexAeropuerto> dijkstra(String origen, String destino) {
        if (origen == null || origen.isEmpty() || destino == null || destino.isEmpty()) return null;

        //Inicializar todos los vértices
        for (VertexAeropuerto v : vertices) {
            v.setCummulativeDistance(Integer.MAX_VALUE);
            v.setVisited(false);
            v.setPredecesor(null);
        }

        VertexAeropuerto source = findVertex(origen);
        VertexAeropuerto target = findVertex(destino);

        if (source == null || target == null) {
            return null; // Verificar que existan los vértices
        }

        source.setCummulativeDistance(0);

        PriorityQueue<VertexAeropuerto> pq = new PriorityQueue<>(
                Comparator.comparingInt(VertexAeropuerto::getCummulativeDistance)
        );
        pq.add(source);

        while (!pq.isEmpty()) {
            VertexAeropuerto u = pq.poll();
            if (u.isVisited()) continue;
            u.setVisited(true);

            if (u.equals(target)) break;

            for (EdgeVuelo e : u.getEdgesVuelos()) {
                VertexAeropuerto v = e.getTarget();
                if (v.isVisited()) continue;
                int nuevoDist = u.getCummulativeDistance() + (int)e.getWeight().toMinutes();
                if (nuevoDist < v.getCummulativeDistance()) {
                    v.setCummulativeDistance(nuevoDist);
                    v.setPredecesor(u);
                    pq.add(v);
                }
            }
        }

        // reconstruir camino
        List<VertexAeropuerto> path = new ArrayList<>();
        VertexAeropuerto u = target;
        if (u.getPredecesor() != null || u.equals(source)) {
            while (u != null) {
                path.add(u);
                u = u.getPredecesor();
            }
            Collections.reverse(path);
        }
        return path;
    }



    public void getEstadisticaConectividad(){

    }
}
