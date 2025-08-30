package com.example.proyecto_ed;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ReservaVuelo extends AppCompatActivity {
    private Spinner origen, destino;
    private ListView vuelosProgramados;
    private MaterialButton reservarVuelo;
    private final GestorVuelos gestorVuelos = GestorVuelos.getInstance();
    private List<Vuelo> vueloList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reserva_vuelo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        origen = findViewById(R.id.sp_Origen);
        destino = findViewById(R.id.sp_Destino);
        vuelosProgramados = findViewById(R.id.lv_Vuelos);
        reservarVuelo = findViewById(R.id.btn_reservarVuelo);

        cargarOrigen();
        //cargarDestinos();
    }

    private String[] arregloOrigen(){
        ArrayList<String> o = new ArrayList<>();
        for (Vuelo vuelo: gestorVuelos.getVuelos()){
            if (!o.contains(vuelo.getOrigen())) o.add(vuelo.getOrigen());
        }
        return o.toArray(new String[0]);
    }

    private void cargarOrigen(){
        String[] o = arregloOrigen();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                o // Pasar el arreglo directamente
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        origen.setAdapter(adapter);

        origen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = o[position];
                String[] destinos = arregloDestino(seleccion);
                cargarDestinos(destinos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public String[] arregloDestino(String partida){
        ArrayList<String> d = new ArrayList<>();
        for (Vuelo vuelo: gestorVuelos.getVuelos()){
            if (partida.equals(vuelo.getOrigen())) d.add(vuelo.getDestino());
        }
        return d.toArray(new String[0]);
    }

    private void cargarDestinos(String[] destinos){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                destinos // Pasar el arreglo directamente
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        destino.setAdapter(adapter);

        destino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String seleccion = destinos[position];
                listaVuelos(seleccion);
                cargarVuelos();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void listaVuelos(String destino){
        for (Vuelo vuelo: gestorVuelos.getVuelos()){
            if (destino.equals(vuelo.getDestino())) vueloList.add(vuelo);
        }
    }

    private void cargarVuelos(){
        ArrayAdapter<Vuelo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout por defecto
                vueloList
        );

        vuelosProgramados.setAdapter(adapter);

        vuelosProgramados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Vuelo elementoSeleccionado = vueloList.get(position);
                //Reservar el vuelo en el usuario
            }
        });
    }
}