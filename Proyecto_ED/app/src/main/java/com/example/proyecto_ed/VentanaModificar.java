package com.example.proyecto_ed;

import static android.view.View.INVISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Services.ReservarManager;

import java.util.Iterator;
import java.util.LinkedList;

public class VentanaModificar extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView listaVuelos;
    private Button modificar;
    private final GestorVuelos gestorVuelos = GestorVuelos.getInstance();
    private final ReservarManager reservarManager = ReservarManager.getInstance();
    private final String FILE_NAME_RESERVAS = "Reservas.txt";

    private String vueloSeleccionado;
    private int idVuelo;

    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_modificar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.modificar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listaVuelos = findViewById(R.id.mostrarVuelos);
        modificar = findViewById(R.id.botonModificar);
        back = findViewById(R.id.atras);

        modificar.setVisibility(View.INVISIBLE);

        //vuelos registrados en el sistemas
        LinkedList<Vuelo> vuelos = gestorVuelos.getVuelos();
        gestorVuelos.cargarVuelos(this);
        //datos de cada uno de los vuelos
        String[] datosVuelos = arrayVuelos(vuelos);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datosVuelos);
        listaVuelos.setAdapter(adapter);
        listaVuelos.setOnItemClickListener(this);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VentanaModificar.this,ventana_seleccion_vuelo_modificar.class);
                intent.putExtra("vueloSeleccionado",vueloSeleccionado);
                intent.putExtra("idVuelo",idVuelo);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(VentanaModificar.this,MenuAdmin.class);
                startActivity(intent2);
                finish();
            }
        });

    }


    public String[] arrayVuelos(LinkedList<Vuelo> vuelos){
        Iterator<Vuelo> it = vuelos.iterator();
        String[] datosVuelos = new String[vuelos.size()];

        int i = 0;

        while(it.hasNext()){

            Vuelo vuelo = it.next();
            datosVuelos[i] = vuelo.toString();
            i++;

        }

        return datosVuelos;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        vueloSeleccionado = adapterView.getItemAtPosition(i).toString();
        idVuelo = i;
        modificar.setVisibility(View.VISIBLE);

    }

}
