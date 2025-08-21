package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Services.FileManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

public class RegistrarUsuario extends AppCompatActivity {

    private TextInputLayout nombre, apellido, identificacion, usuario, contraseña;
    private MaterialButton btnRegistrarse;
    private static final String FILE_NAME = "usuarios.txt";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.registro_usuario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
            if (validarUsuarioExistente(user)){
                Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
            } else {
                guardarUsuario(name, lastName, ID, user, pass);
                Intent intent = new Intent(this, InicioSesion.class);
                startActivity(intent);
            }
        });

    }

    private boolean guardarUsuario(String nombre, String apellido, String identificacion, String usuario, String contraseña) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
                identificacion == null || identificacion.isEmpty() || usuario == null || usuario.isEmpty() ||
                contraseña == null || contraseña.isEmpty()){
            return false;
        } else {
            String cadena = nombre+apellido+identificacion+usuario+contraseña+"false";
            FileManager.escribirLinea(this, FILE_NAME, cadena);
            return true;
        }
    }

    private boolean validarUsuarioExistente(String user){
        if (user == null || user.isEmpty()) return false;
        List<String> cadenas = FileManager.leerArchivo(this, FILE_NAME);
        if (cadenas != null){
            Iterator<String> it = cadenas.iterator();
            while (it.hasNext()){
                String c = it.next();
                String[] componentes = c.split(",");
                if (componentes[4].equals(user)){
                    return true;
                }
            }
        }
        return false;
    }
}
