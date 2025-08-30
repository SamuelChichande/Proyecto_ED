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

import com.example.proyecto_ed.Services.GestorAeropuertos;
import com.example.proyecto_ed.Services.GestorUsuarios;
import com.example.proyecto_ed.Services.GestorVuelos;

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

        gestorAeropuertos.cargarAeropuerto(this);
        gestorVuelos.cargarVuelos(this);
        gestorUsuarios.cargarUsuarios(this);


    }

    public void cambiarVentanaGrafo(View view){
        //Intent intent = new Intent(this, VentanaMapa.class);
        Intent intent = new Intent(this, InicioSesion.class);
        startActivity(intent);
    }
}