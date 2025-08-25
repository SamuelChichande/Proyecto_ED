package com.example.proyecto_ed;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Ubicacion;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class VentanaMapa extends AppCompatActivity {
    private static final String NAME_FILE = "Aeropuertos.txt";

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
        GestorAeropuertos ga = GestorAeropuertos.getInstance();
        ga.cargarAeropuerto(this);
        LinkedList<Aeropuerto> aeropuertos = ga.getAeropuertos();
        Iterator<Aeropuerto> it = aeropuertos.iterator();
        while (it.hasNext()){
            Aeropuerto a = it.next();
            LatLng ubicacion = new LatLng(a.getUbicacion().getLatitud(), a.getUbicacion().getLongitud());
            googleMap.addMarker(new MarkerOptions().
                    position(ubicacion).
                    title(a.getNombre()).
                    snippet(a.getCodigo()));
        }
    }
}
