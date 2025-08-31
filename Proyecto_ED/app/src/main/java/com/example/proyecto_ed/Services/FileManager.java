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


    public static void escribirLinea(Context context, String fileName, String datos) {
        /*
        try {
            List<String> lineas = leerArchivo(context, fileName);

            lineas.add(datos);

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
        */
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            writer.write(datos + "\n");
            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sobreescribir(Context context, String fileName, List<String> datos) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            for (String l : datos) {
                writer.write(l + "\n");
            }
            writer.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
