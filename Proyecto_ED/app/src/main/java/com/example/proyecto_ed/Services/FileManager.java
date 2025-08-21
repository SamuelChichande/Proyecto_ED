package com.example.proyecto_ed.Services;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    public static List<String> leerArchivo(Context context, String fileName) {
        List<String> lineas = new ArrayList<>();

        try {
            FileInputStream fis = context.openFileInput(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            String line;

            while ((line = reader.readLine()) != null) {
                lineas.add(line);
            }

            reader.close();
            fis.close();

        } catch (FileNotFoundException e) {
            // Si no existe, devolver nulo
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    public static void escribirLinea(Context context, String fileName, String datosSinId) {
        try {
            // Leemos el archivo actual
            List<String> lineas = leerArchivo(context, fileName);

            // Buscar ID máximo actual
            int maxId = 0;
            for (String linea : lineas) {
                String[] parts = linea.split(",");
                if (parts.length > 0) {
                    try {
                        int id = Integer.parseInt(parts[0]);
                        if (id > maxId) {
                            maxId = id;
                        }
                    } catch (NumberFormatException ignored) {}
                }
            }

            // Generar nuevo ID
            int nuevoId = maxId + 1;

            // Crear nueva línea con ID
            String nuevaLinea = nuevoId + "," + datosSinId;
            lineas.add(nuevaLinea);

            // Reescribir el archivo
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            for (String l : lineas) {
                writer.write(l + "\n");
            }
            writer.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
