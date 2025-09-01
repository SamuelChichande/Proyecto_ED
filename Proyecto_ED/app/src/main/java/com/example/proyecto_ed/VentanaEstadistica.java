package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Aeropuerto;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Td.TDA_GRAFO.EdgeVuelo;
import com.example.proyecto_ed.Td.TDA_GRAFO.VertexAeropuerto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class VentanaEstadistica extends AppCompatActivity {

    private TextView txtConexiones, txtAeropuerto, txtAeropuerto2, txtRuta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_estadistica);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtConexiones = findViewById(R.id.txtConexiones);
        txtAeropuerto   = findViewById(R.id.txtAeropuerto);
        txtAeropuerto2 = findViewById(R.id.txtAeropuerto2);

        mostrarConexionesPorAeropuerto();
        mostrarAeropuertosMasConectados();
        mostrarAeropuertosMenosConectados();
    }

    public void viewVolver(View view){
        Intent intent = new Intent(this, MenuAdmin.class);
        startActivity(intent);
    }

    private void mostrarConexionesPorAeropuerto() {
        try {
            GraphManager gm = GraphManager.getInstance(Comparator.naturalOrder());
            if (gm == null || gm.getGraph() == null || gm.getGraph().getVertices() == null || gm.getGraph().getVertices().isEmpty()) {
                txtConexiones.setText("No hay datos de aeropuertos o conexiones cargados");
                return;
            }
            LinkedList<VertexAeropuerto> vertices = gm.getGraph().getVertices();
            Map<String, Set<String>> vecinos = new LinkedHashMap<>();//<codigo,NumeroDeconexion>
            Map<String, Aeropuerto> ref = new LinkedHashMap<>();//<nombre,codigo>

            // Se inicializa
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null){
                    continue;
                }
                String code = v.getContent().getCodigo();
                if (code == null) {
                    continue;
                }
                ref.put(code, v.getContent());
                if (!vecinos.containsKey(code)) {
                    vecinos.put(code, new HashSet<String>());
                }
            }
            // simetrizapor cada arista dirigida origen a destino
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null) {
                    continue;
                }
                String origen = v.getContent().getCodigo();
                if (origen == null) {
                    continue;
                }
                LinkedList<EdgeVuelo> edges = v.getEdgesVuelos();
                if (edges == null) continue;
                for (EdgeVuelo e : edges) {
                    if (e == null || e.getTarget() == null || e.getTarget().getContent() == null) {
                        continue;
                    }
                    String destino = e.getTarget().getContent().getCodigo();
                    if (destino == null || origen.equals(destino)){
                        continue;
                    }
                    if (!vecinos.containsKey(origen)) {
                        vecinos.put(origen, new HashSet<String>());
                    }
                    if (!vecinos.containsKey(destino)){
                        vecinos.put(destino, new HashSet<String>());
                    }
                    vecinos.get(origen).add(destino);
                    vecinos.get(destino).add(origen);
                }
            }

            //Se muestra en el textView
            String texto = "";
            for (String code : ref.keySet()) {
                Aeropuerto a = ref.get(code);
                if (a == null) continue;
                Set<String> set = vecinos.get(code);
                int count = 0;
                if (set != null) {
                    count = set.size();
                }
                String linea = a.getNombre() + " (" + code + ") => conexiones=" + count;
                if (texto.isEmpty()) {
                    texto = linea;
                }
                else {
                    texto = texto + "\n" + linea;
                }
            }

            if (texto.isEmpty()) {
                texto = "No hay datos de aeropuertos o conexiones cargados.";
            }
            txtConexiones.setText(texto);
        } catch (Throwable t) {
            String msg = t.getClass().getSimpleName();
            if (t.getMessage() != null){
                msg += ": " + t.getMessage();
            }
            android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_LONG).show();
            txtConexiones.setText("Error: " + msg);
        }
    }





    private void mostrarAeropuertosMasConectados() {
        try {
            GraphManager gm = GraphManager.getInstance(Comparator.naturalOrder());
            if (gm == null || gm.getGraph() == null || gm.getGraph().getVertices() == null || gm.getGraph().getVertices().isEmpty()) {
                if (txtAeropuerto != null) {
                    txtAeropuerto.setText("No hay datos de aeropuertos o conexiones cargados.");
                }
                return;
            }
            LinkedList<VertexAeropuerto> vertices = gm.getGraph().getVertices();
            Map<String, Set<String>> vecinos = new LinkedHashMap<>();
            Map<String, Aeropuerto> ref = new LinkedHashMap<>();

            // registrar cada aeropuerto y su set vacío de vecinos
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null) {
                    continue;
                }
                String code = v.getContent().getCodigo();
                if (code == null) {
                    continue;
                }
                ref.put(code, v.getContent());
                if (!vecinos.containsKey(code)) {
                    vecinos.put(code, new HashSet<String>());
                }
            }

            // construir conexiones no dirigidas
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null) {
                    continue;
                }
                String origen = v.getContent().getCodigo();
                if (origen == null) {
                    continue;
                }

                LinkedList<EdgeVuelo> edges = v.getEdgesVuelos();
                if (edges == null) {
                    continue;
                }

                for (EdgeVuelo e : edges) {
                    if (e == null || e.getTarget() == null || e.getTarget().getContent() == null) { continue; }
                    String destino = e.getTarget().getContent().getCodigo();
                    if (destino == null || origen.equals(destino)) {
                        continue;
                    }

                    Set<String> s = vecinos.get(origen);
                    if (s == null) {
                        s = new HashSet<String>();
                        vecinos.put(origen, s);
                    }
                    s.add(destino);

                    s = vecinos.get(destino);
                    if (s == null) {
                        s = new HashSet<String>();
                        vecinos.put(destino, s);
                    }
                    s.add(origen);
                }
            }

            // encontrar el máximo y los empatados
            int max = -1;
            List<String> top = new ArrayList<>();
            for (String code : ref.keySet()) {
                Set<String> set = vecinos.get(code);
                int count = 0;
                if (set != null) {
                    count = set.size();
                }
                if (count > max) {
                    max = count;
                    top.clear();
                    top.add(code);
                } else if (count == max) {
                    top.add(code);
                }
            }

            String texto = "";
            if (top.isEmpty()) {
                texto = "No hay datos de aeropuertos o conexiones cargados.";
            } else {
                for (int i = 0; i < top.size(); i++) {
                    String code = top.get(i);
                    Aeropuerto a = ref.get(code);
                    String linea = a.getNombre() + " (" + code + ") => conexiones=" + max;
                    if (texto.isEmpty()) {
                        texto = linea;
                    }
                    else {
                        texto = texto + "\n" + linea;
                    }
                }
            }
            if (txtAeropuerto != null) {
                txtAeropuerto.setText(texto);
            }

        } catch (Throwable t) {
            String msg = t.getClass().getSimpleName();
            if (t.getMessage() != null) {
                msg += ": " + t.getMessage();
            }
            android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_LONG).show();
            if (txtAeropuerto != null) {
                txtAeropuerto.setText("Error: " + msg);
            }
        }
    }

    private void mostrarAeropuertosMenosConectados() {
        try {
            GraphManager gm = GraphManager.getInstance(Comparator.naturalOrder());
            if (gm == null || gm.getGraph() == null || gm.getGraph().getVertices() == null || gm.getGraph().getVertices().isEmpty()) {
                if (txtAeropuerto2 != null) {
                    txtAeropuerto2.setText("No hay datos de aeropuertos o conexiones cargados.");
                }
                return;
            }

            LinkedList<VertexAeropuerto> vertices = gm.getGraph().getVertices();
            Map<String, Set<String>> vecinos = new LinkedHashMap<>();
            Map<String, Aeropuerto> ref = new LinkedHashMap<>();

            // Registrar cada aeropuerto y su set vacío de vecinos
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null) {
                    continue;
                }
                String code = v.getContent().getCodigo();
                if (code == null) {
                    continue;
                }
                ref.put(code, v.getContent());
                if (!vecinos.containsKey(code)) {
                    vecinos.put(code, new HashSet<String>());
                }
            }

            // Construir conexiones no dirigidas
            for (VertexAeropuerto v : vertices) {
                if (v == null || v.getContent() == null) {
                    continue;
                }
                String origen = v.getContent().getCodigo();
                if (origen == null) {
                    continue;
                }

                LinkedList<EdgeVuelo> edges = v.getEdgesVuelos();
                if (edges == null) {
                    continue;
                }

                for (EdgeVuelo e : edges) {
                    if (e == null || e.getTarget() == null || e.getTarget().getContent() == null) { continue; }
                    String destino = e.getTarget().getContent().getCodigo();
                    if (destino == null || origen.equals(destino)) {
                        continue;
                    }

                    Set<String> s = vecinos.get(origen);
                    if (s == null) {
                        s = new HashSet<String>(); vecinos.put(origen, s);
                    }
                    s.add(destino);
                    s = vecinos.get(destino);
                    if (s == null) {
                        s = new HashSet<String>(); vecinos.put(destino, s);
                    }
                    s.add(origen);
                }
            }
            // Encontrar el mínimo y los empatados
            int min = Integer.MAX_VALUE;
            List<String> bottom = new ArrayList<>();
            for (String code : ref.keySet()) {
                Set<String> set = vecinos.get(code);
                int count = 0;
                if (set != null) {
                    count = set.size();
                }
                if (count < min) {
                    min = count;
                    bottom.clear();
                    bottom.add(code);
                } else if (count == min) {
                    bottom.add(code);
                }
            }

            String texto = "";
            if (bottom.isEmpty()) {
                texto = "No hay datos de aeropuertos o conexiones cargados.";
            } else {
                for (int i = 0; i < bottom.size(); i++) {
                    String code = bottom.get(i);
                    Aeropuerto a = ref.get(code);
                    String linea = a.getNombre() + " (" + code + ") => conexiones=" + min;
                    if (texto.isEmpty()) {
                        texto = linea;
                    }
                    else {
                        texto = texto + "\n" + linea;
                    }
                }
            }

            if (txtAeropuerto2 != null) {
                txtAeropuerto2.setText(texto);
            }

        } catch (Throwable t) {
            String msg = t.getClass().getSimpleName();
            if (t.getMessage() != null) {
                msg += ": " + t.getMessage();
            }
            android.widget.Toast.makeText(this, msg, android.widget.Toast.LENGTH_LONG).show();
            if (txtAeropuerto2 != null) {
                txtAeropuerto2.setText("Error: " + msg);
            }
        }
    }



}
