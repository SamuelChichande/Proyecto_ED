package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto_ed.Models.Administrador;
import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Services.FileManager;

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
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contraseña);
        btnIniciar = findViewById(R.id.btnIniciarSesion);
        btnRegistrar = findViewById(R.id.btnRegistrarse);

        btnIniciar.setOnClickListener(v -> {
            String user = correo.getText().toString();
            String pass = contrasena.getText().toString();

            if (validarUsuario(user, pass)) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                // Ir a otra pantalla, ya sea cliente o administrador
                Intent intent = null;
                if (userIsAdmin(user, pass)){
                    //Menu de administrador
                    intent = new Intent(this, RegistrarUsuario.class);
                } else {
                    //Menu de cliente
                    intent = new Intent(this, RegistrarUsuario.class);
                }
                //Pasar usuario
                //intent.putExtra("methodName",getDateUser(user, pass));
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
        List<String> cadenas = FileManager.leerArchivo(this, "usuarios.txt");
        if (cadenas != null){
            Iterator<String> it = cadenas.iterator();
            while (it.hasNext()){
                String c = it.next();
                String[] componentes = c.split(",");
                if (componentes[4].equals(user) && componentes[5].equals(pass)){
                    if (Boolean.parseBoolean(componentes[6])){
                        return new Administrador(componentes[1], componentes[2], componentes[3], componentes[4], componentes[5]);
                    } else {
                        return new Cliente(componentes[1], componentes[2], componentes[3], componentes[4], componentes[5]);
                    }
                }
            }
        }
        return null;
    }

    private boolean validarUsuario(String user, String pass) {
        if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) return false;
        if (getDateUser(user, pass) != null){
            return true;
        }
        return false;
    }

    private boolean userIsAdmin(String user, String pass){
        Usuario usuario = getDateUser(user, pass);
        if (user != null){
            return usuario.isAdmin();
        }
        return false;
    }
}
