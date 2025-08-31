package com.example.proyecto_ed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Models.Vuelo;
import com.google.android.material.button.MaterialButton;

public class DatosVuelo extends AppCompatActivity {

    private TableLayout tableLayout;
    MaterialButton btn_regresar;
    private Vuelo vuelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_datos_vuelo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tableLayout = findViewById(R.id.tl_DatosVuelos);
        btn_regresar = findViewById(R.id.btn_regresar_viewDatos);

        Intent intent = getIntent();
        vuelo = (Vuelo) intent.getSerializableExtra("vuelo");
        llenarDatosAeropuerto(vuelo);

        btn_regresar.setOnClickListener(v -> {
            finish();
        });
    }

    private void llenarDatosAeropuerto(Vuelo vuelo) {
        tableLayout.removeAllViews();

        String[][] datos = {
                {"Código", String.valueOf(vuelo.getId())},
                {"Origen", vuelo.getOrigen()},
                {"Destino", vuelo.getDestino()},
                {"Fecha de Salida", String.valueOf(vuelo.getFechaSalida())},
                {"Hora de salida", String.valueOf(vuelo.getHoraSalida())},
                {"Fecha de llegada", String.valueOf(vuelo.getFechaLlegada())},
                {"Hora de llegada", String.valueOf(vuelo.getHoraLlegada())},
                {"Duracion (m)", String.valueOf(vuelo.getDuracion().toMinutes())},
                {"Distancia (km)", String.valueOf(vuelo.getDistancia())},
                {"Aerolínea", vuelo.getAerolinea()},
                {"Precio", String.valueOf(vuelo.getPrecio())}
        };

        for (String[] fila : datos) {
            agregarFilaATabla(fila[0], fila[1]);
        }
    }

    private void agregarFilaATabla(String titulo, String valor) {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        row.setLayoutParams(params);

        TextView tvTitulo = crearTextView(titulo, true);
        TextView tvValor = crearTextView(valor, false);

        row.addView(tvTitulo);
        row.addView(tvValor);

        tableLayout.addView(row);
    }

    private TextView crearTextView(String texto, boolean isHeader) {
        TextView textView = new TextView(this);
        TableRow.LayoutParams params = new TableRow.LayoutParams(
                0, // 0 weight para que se expandan igual
                TableRow.LayoutParams.WRAP_CONTENT,
                1f // Peso 1 para que ambas columnas sean iguales
        );
        params.setMargins(2, 2, 2, 2);
        textView.setLayoutParams(params);

        textView.setText(texto);
        textView.setPadding(16, 12, 16, 12);
        textView.setTextSize(14);

        if (isHeader) {
            //Estilo para la columna de títulos
            textView.setTypeface(null, android.graphics.Typeface.BOLD);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setBackgroundColor(Color.parseColor("#3D79CE"));
        } else {
            //Estilo para la columna de valores
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setBackgroundColor(Color.parseColor("#DBBB9D"));
        }

        textView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);

        return textView;
    }
}