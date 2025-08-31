package com.example.proyecto_ed;

import android.content.Intent;
import android.graphics.Insets;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Administrador;
import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorUsuarios;

import java.util.Iterator;
import java.util.List;

public class InicioSesion extends AppCompatActivity {
    private EditText correo, contrasena;
    private Button btnIniciar, btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.inicio_sesion);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            androidx.core.graphics.Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contraseña);
        btnIniciar = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnRegistrarse);

        btnIniciar.setOnClickListener(v -> {
            String user = correo.getText().toString();
            String pass = contrasena.getText().toString();

            Usuario usuario = getDateUser(user, pass);
            if (usuario != null){
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = null;
                if (usuario.isAdmin()){
                    intent = new Intent(this, MenuAdmin.class);
                } else {
                    intent = new Intent(this, MenuCliente.class);
                }
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrarUsuario.class);
            startActivity(intent);
        });
    }

    private Usuario getDateUser(String user, String pass){
        if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) return null;

        GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();

        for (Usuario usuario: gestorUsuarios.getUsuarios()) {
            if (usuario.getUsuario().equals(user) && usuario.getContraseña().equals(pass)) {
                return usuario;
            }
        }

        return null;
    }

}
