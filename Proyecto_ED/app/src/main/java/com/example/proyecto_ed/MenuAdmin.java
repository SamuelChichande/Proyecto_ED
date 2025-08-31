package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Usuario;

public class MenuAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();
        Usuario user = (Usuario) intent.getSerializableExtra("usuario");
    }

    public void viewAgregarVuelo(View view){
        Intent intent = new Intent(this, MenuInicio.class);
        startActivity(intent);
    }

    public void viewModificarVuelo(View view){
        Intent intent = new Intent(this, MenuInicio.class);
        startActivity(intent);
    }

    public void viewEliminarVuelo(View view){
        Intent intent = new Intent(this, MenuInicio.class);
        startActivity(intent);
    }
    public void viewEstadistica(View view){
        Intent intent = new Intent(this, VentanaEstadistica.class);
        startActivity(intent);
    }
}