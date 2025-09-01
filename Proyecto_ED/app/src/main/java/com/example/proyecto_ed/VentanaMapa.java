package com.example.proyecto_ed;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Ubicacion;
import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Td.TDA_GRAFO.EdgeVuelo;
import com.example.proyecto_ed.Td.TDA_GRAFO.VertexAeropuerto;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VentanaMapa extends AppCompatActivity {
    private GraphManager graphManager = GraphManager.getInstance(null);
    private Map<String, Marker> marcadoresAeropuertos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_mapa);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //MAPA
        createMap();

    }

    public void createMap(){
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(googleMap -> {
                googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                agregarMarcadores(googleMap);
            });
        }
    }

    public void agregarMarcadores(GoogleMap googleMap){
        LinkedList<VertexAeropuerto> vertexAeropuertos = graphManager.getGraph().getVertices();;
        Iterator<VertexAeropuerto> it = vertexAeropuertos.iterator();
        while (it.hasNext()){
            VertexAeropuerto vA = it.next();
            Aeropuerto a = vA.getContent();
            LatLng ubicacion = new LatLng(a.getUbicacion().getLatitud(), a.getUbicacion().getLongitud());
            Marker marker = googleMap.addMarker(new MarkerOptions().
                    position(ubicacion).
                    title(a.getNombre()).
                    snippet(a.getCodigo()));

            marcadoresAeropuertos.put(a.getCodigo(), marker);
        }

        agregarConeciones(googleMap);
    }

    public void agregarConeciones(GoogleMap googleMap){
        LinkedList<VertexAeropuerto> vertices = graphManager.getGraph().getVertices();
        for (VertexAeropuerto vA: vertices){
            Aeropuerto aeropuertoOrigen = vA.getContent();
            Marker marcadorOrigen = marcadoresAeropuertos.get(aeropuertoOrigen.getCodigo());
            if (marcadorOrigen != null) {
                LinkedList<EdgeVuelo> edgesVuelos = vA.getEdgesVuelos();

                for (EdgeVuelo edge : edgesVuelos) {
                    Aeropuerto aeropuertoDestino = edge.getTarget().getContent();
                    Marker marcadorDestino = marcadoresAeropuertos.get(aeropuertoDestino.getCodigo());

                    if (marcadorDestino != null) {
                        dibujarConexion(googleMap, marcadorOrigen, marcadorDestino, edge.getData());
                    }
                }
            }
        }
    }

    private void dibujarConexion(GoogleMap googleMap, Marker origen, Marker destino, Vuelo vuelo) {
        LatLng posOrigen = origen.getPosition();
        LatLng posDestino = destino.getPosition();

        // Crear la polyline (línea de conexión)
        Polyline polyline = googleMap.addPolyline(new PolylineOptions()
                .add(posOrigen, posDestino)
                .width(5) // Grosor de la línea
                .color(Color.RED) // Color basado en duración
                .geodesic(true)); // Línea curva que sigue la curvatura de la tierra

        // Agregar patrón de flecha para indicar dirección
        dibujarFlechaSimple(googleMap, posOrigen, posDestino, Color.RED);

        // Opcional: agregar click listener a la polyline
        googleMap.setOnPolylineClickListener(polyline1 -> {
            mostrarInfoVuelo(vuelo, origen, destino);
        });
    }

    private void dibujarFlechaSimple(GoogleMap googleMap, LatLng start, LatLng end, int color) {
        // Punto a 80% del camino para colocar la flecha
        double lat = start.latitude + (end.latitude - start.latitude) * 0.8;
        double lng = start.longitude + (end.longitude - start.longitude) * 0.8;
        LatLng arrowPoint = new LatLng(lat, lng);

        // Pequeños desplazamientos para crear efecto de flecha
        LatLng arrowLeft = new LatLng(lat + 0.1, lng - 0.1);
        LatLng arrowRight = new LatLng(lat + 0.1, lng + 0.1);

        googleMap.addPolyline(new PolylineOptions()
                .add(arrowPoint, arrowLeft)
                .width(3)
                .color(color));

        googleMap.addPolyline(new PolylineOptions()
                .add(arrowPoint, arrowRight)
                .width(3)
                .color(color));
    }

    private void mostrarInfoVuelo(Vuelo vuelo, Marker origen, Marker destino) {
        String mensaje = "Vuelo: " + vuelo.getId() + "\n" +
                "De: " + origen.getTitle() + "\n" +
                "A: " + destino.getTitle() + "\n" +
                "Duración: " + formatDuracion(vuelo.getDuracion()) + "\n" +
                "Aerolinea: " + vuelo.getAerolinea();

        new AlertDialog.Builder(this)
                .setTitle("Información del Vuelo")
                .setMessage(mensaje)
                .setPositiveButton("OK", null)
                .show();
    }

    private String formatDuracion(Duration duracion) {
        long hours = duracion.toHours();
        long minutes = duracion.toMinutes() % 60;
        return hours + "h " + minutes + "m";
    }
    public void viewVolver(View view){
        Intent intent = new Intent(this, MenuInicio.class);
        startActivity(intent);
    }
}
