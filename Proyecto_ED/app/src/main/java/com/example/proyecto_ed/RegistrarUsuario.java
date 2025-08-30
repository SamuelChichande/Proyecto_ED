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

import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorUsuarios;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Iterator;
import java.util.List;

public class RegistrarUsuario extends AppCompatActivity {

    private final GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
    private final String FILE_NAME_USUARIO = "Usuarios.txt";
    private TextInputEditText nombre, apellido, identificacion, usuario, contraseña;
    private MaterialButton btnRegistrarse;

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
            String name = nombre.getText().toString().trim();
            String lastName = apellido.getText().toString().trim();
            String ID = identificacion.getText().toString().trim();
            String user = usuario.getText().toString().trim();
            String pass = contraseña.getText().toString().trim();

            if (validarUsuarioExistente(user, ID)){
                Toast.makeText(this, "Ya existe una cuenta con ese usuario o cedula", Toast.LENGTH_SHORT).show();
            } else {
                if (guardarUsuario(name, lastName, ID, user, pass)){
                    Toast.makeText(this, "Usuario registrado de manera exitosa", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, InicioSesion.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Complete los datos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean guardarUsuario(String nombre, String apellido, String identificacion, String usuario, String contraseña) {
        if (nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() ||
                identificacion == null || identificacion.isEmpty() || usuario == null || usuario.isEmpty() ||
                contraseña == null || contraseña.isEmpty()){
            return false;
        } else {
            int lastID = gestorUsuarios.getLastID()+1;
            Usuario user = new Cliente(lastID, nombre, apellido, identificacion, usuario, contraseña);
            gestorUsuarios.agregarUsuario(user);

            String cadena = lastID+","+nombre+","+apellido+","+identificacion+","+usuario+
                    ","+contraseña+",false";
            FileManager.escribirLinea(this, FILE_NAME_USUARIO, cadena);

            return true;
        }
    }

    private boolean validarUsuarioExistente(String user, String ID){
        if (user == null || user.isEmpty() || ID == null || ID.isEmpty()) return false;

        for (Usuario usuario: gestorUsuarios.getUsuarios()){
            if (usuario.getUsuario().equals(user) || usuario.getCedula().equals(ID)){
                return true;
            }
        }

        return false;
    }

}
