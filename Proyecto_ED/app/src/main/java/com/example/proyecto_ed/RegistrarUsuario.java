package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrarUsuario extends AppCompatActivity {

    TextInputLayout nombre, apellido, identificacion, usuario, contraseña;
    private MaterialButton btnRegistrarse;
    private static final String FILE_NAME = "usuarios.txt";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro_usuario);
        /*
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        */
        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        identificacion = findViewById(R.id.cedula);
        usuario = findViewById(R.id.Usuario);
        contraseña = findViewById(R.id.contraseñaRegistro);
        btnRegistrarse = findViewById(R.id.btnRegistrarUsuario);

        btnRegistrarse.setOnClickListener(v -> {
            String name = nombre.getEditText().getText().toString();
            String lastName = apellido.getEditText().getText().toString();
            String ID = identificacion.getEditText().getText().toString();
            String user = usuario.getEditText().getText().toString();
            String pass = contraseña.getEditText().getText().toString();
            if (validarDatosExistentes(user, pass)){
                Toast.makeText(this, "El usuario o contraseña ya existen", Toast.LENGTH_SHORT).show();
            } else {
                guardarUsuario(name, lastName, ID, user, pass);
                //Dependiendo si se registra como usuario o administrador
            }
        });

    }

    private void guardarUsuario(String nombre, String apellido, String identificacion, String usuario, String contraseña) {

    }
    private boolean validarDatosExistentes(String user, String pass){
        return false;
    }
}
