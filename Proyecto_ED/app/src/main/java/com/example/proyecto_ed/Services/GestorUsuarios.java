package com.example.proyecto_ed.Services;

import android.content.Context;
import android.widget.Toast;

import com.example.proyecto_ed.Models.Administrador;
import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.Usuario;

import java.util.LinkedList;
import java.util.List;

public class GestorUsuarios {
    private static GestorUsuarios instance;
    private LinkedList<Usuario> usuarios;
    private static final String NAME_FILE_USUARIOS = "Usuarios.txt";

    private GestorUsuarios(){
        this.usuarios = new LinkedList<>();
    }

    public static GestorUsuarios getInstance(){
        if (instance == null) {
            instance = new GestorUsuarios();
        }
        return instance;
    }

    public void cargarUsuarios(Context context){
        List<String> contenidos = FileManager.leerArchivo(context, NAME_FILE_USUARIOS);
        for (String content: contenidos){

            try {
                String[] componentes = content.split(",");

                Usuario user = null;
                boolean isAdmin = Boolean.parseBoolean(componentes[6]);

                if (isAdmin){
                    user = new Administrador(
                            Integer.parseInt(componentes[0]),
                            componentes[1],componentes[2],
                            componentes[3],componentes[4],
                            componentes[5]);
                } else {
                    user = new Cliente(
                            Integer.parseInt(componentes[0]),
                            componentes[1],componentes[2],
                            componentes[3],componentes[4],
                            componentes[5]);
                }
                agregarUsuario(user);

            } catch (NumberFormatException e) {
                Toast.makeText(context, "Existe un error en los datos guardados del archivo "+NAME_FILE_USUARIOS, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(context, "Ha ocurrido un error al momento de cargar los vuelos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void agregarUsuario(Usuario usuario){
        if (usuario == null) return;
        usuarios.add(usuario);
    }

    public int getLastID(){
        if (usuarios == null || usuarios.isEmpty()) {
            return 1;
        }
        return usuarios.getLast().getIdUser();
    }

    public LinkedList<Usuario> getUsuarios(){
        return usuarios;
    }
}
