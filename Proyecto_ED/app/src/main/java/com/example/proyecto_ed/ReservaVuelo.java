package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.ReservarVuelo;
import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Td.TDA_GRAFO.VertexAeropuerto;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ReservaVuelo extends AppCompatActivity {
    private Spinner spOrigen, spDestino;
    private ListView vuelosProgramados;
    private MaterialButton reservarVuelo;
    private final GestorAeropuertos gestorAeropuertos = GestorAeropuertos.getInstance();
    private final GraphManager graphManager = GraphManager.getInstance(null);
    private final GestorVuelos gestorVuelos = GestorVuelos.getInstance();
    private Usuario user;
    private List<Vuelo> vueloList = new ArrayList<>();
    private String origen;  //Pais-CodigoAeropuerto
    private String destino; //Pais-CodigoAeropuerto
    private List<VertexAeropuerto> camino;

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

        spOrigen = findViewById(R.id.sp_Origen);
        spDestino = findViewById(R.id.sp_Destino);
        vuelosProgramados = findViewById(R.id.lv_Vuelos);
        reservarVuelo = findViewById(R.id.btn_regresar_viewDatos);

        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");

        cargarOrigen();

        reservarVuelo.setOnClickListener(v -> {
            Cliente c = (Cliente) user;
            int id = 0;
            List<ReservarVuelo> rv = c.getReservas();
            if (rv.size() > 0){
                id = rv.get(rv.size() - 1).getId();
            }
            ReservarVuelo reservarVuelo = new ReservarVuelo(id,user,vueloList);
            c.reservarVuelo(reservarVuelo);
            Toast.makeText(this, "Se ha guardado su reserva con exito", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MenuCliente.class);
            startActivity(i);
        });
    }

    private String[] arregloOrigen(){
        ArrayList<String> o = new ArrayList<>();
        for (Aeropuerto aeropuerto: gestorAeropuertos.getAeropuertos()){
            o.add(aeropuerto.getPais()+"-"+aeropuerto.getCodigo());
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
        spOrigen.setAdapter(adapter);

        spOrigen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                origen = o[position];
                String[] component = origen.split("-");
                String[] destinos = arregloDestino(component[1]);
                cargarDestinos(destinos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    public String[] arregloDestino(String partida){
        ArrayList<String> d = new ArrayList<>();
        d.add("Selecciona un destino");
        for (Aeropuerto aeropuerto: gestorAeropuertos.getAeropuertos()){
            if (!partida.equals(aeropuerto.getCodigo())) d.add(aeropuerto.getPais()+"-"+aeropuerto.getCodigo());
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
        spDestino.setAdapter(adapter);

        spDestino.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destino = destinos[position];
                if (!destino.equals("Selecciona un destino")) {
                    String[] componentO = origen.split("-");
                    String[] componentD = destino.split("-");
                    listaVuelos(componentO[1], componentD[1]);
                    cargarVuelos();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    private void listaVuelos(String inicio, String fin){
        camino = graphManager.getGraph().dijkstra(inicio, fin);
    }

    private void cargarVuelos(){
        Iterator<VertexAeropuerto> it = camino.iterator();
        VertexAeropuerto actual = it.next();
        while (it.hasNext()){
            VertexAeropuerto siguiente = it.next();
            for (Vuelo vuelo: gestorVuelos.getVuelos()){
                if (vuelo.getOrigen().equals(actual.getContent().getCodigo()) &&
                vuelo.getDestino().equals(siguiente.getContent().getCodigo())) {
                    vueloList.add(vuelo);
                }
            }
            actual = siguiente;
        }

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
                Intent intent = new Intent(ReservaVuelo.this, DatosVuelo.class);
                intent.putExtra("vuelo",elementoSeleccionado);
                startActivity(intent);
            }
        });
    }
}