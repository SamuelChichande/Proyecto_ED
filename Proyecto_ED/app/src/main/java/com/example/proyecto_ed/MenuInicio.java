package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.example.proyecto_ed.Services.GestorUsuarios;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Services.ReservarManager;

import java.util.Comparator;

public class MenuInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        GestorAeropuertos gestorAeropuertos = GestorAeropuertos.getInstance();
        GestorVuelos gestorVuelos = GestorVuelos.getInstance();
        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
        ReservarManager reservarManager = ReservarManager.getInstance();

        Comparator<String> cmp = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.equals(o2)){
                    return 0;
                } else {
                    return 1;
                }
            }
        };
        GraphManager graphManager = GraphManager.getInstance(cmp);

        gestorAeropuertos.cargarAeropuerto(this);
        gestorVuelos.cargarVuelos(this);
        gestorUsuarios.cargarUsuarios(this);
        reservarManager.cargarReservas(this);

        for (Aeropuerto aeropuerto: gestorAeropuertos.getAeropuertos()){
            graphManager.actualizarListaVertices(aeropuerto);
        }

        for (Vuelo vuelo: gestorVuelos.getVuelos()){
            graphManager.agregarConexion(vuelo.getOrigen(), vuelo.getDestino(), vuelo.getDuracion());
        }

    }

    public void cambiarVentanaGrafo(View view){
        Intent intent = new Intent(this, VentanaMapa.class);
        //Intent intent = new Intent(this, InicioSesion.class);
        startActivity(intent);
    }
    public void viewIniciarSesion(View view) {
        startActivity(new Intent(this, InicioSesion.class));
    }

}