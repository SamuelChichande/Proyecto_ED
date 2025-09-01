package com.example.proyecto_ed;

import static android.widget.Toast.LENGTH_LONG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.google.android.material.textfield.TextInputEditText;
import java.time.LocalDate;
import java.time.LocalTime;

public class ventana_seleccion_vuelo_modificar extends AppCompatActivity {
    private EditText origen, destino,horaSalida,horaLlegada,distancia,aerolinea,
    asientos,precio,capacidad;

    private DatePicker fechaSalida,fechaLLegada;
    private Button cancelarModificacion, guardarModificacion;
    private TextView vueloSelect;

    private final String FILE_NAME_VUELOS = "Vuelos.txt";
    private ImageButton volver;
    private Vuelo vueloActual;

    private final GestorVuelos gestorVuelos = GestorVuelos.getInstance();

    private String datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_modificacion_vuelo_seleccionado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.modificacionVuelo), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        origen = findViewById(R.id.origen);
        destino = findViewById(R.id.destino);
        horaSalida = findViewById(R.id.HoraSalida);
        horaLlegada = findViewById(R.id.HoraLlegada);
        distancia = findViewById(R.id.dist);
        aerolinea = findViewById(R.id.Aerolinea);
        asientos = findViewById(R.id.asientosDisp);
        precio = findViewById(R.id.Precio);
        capacidad = findViewById(R.id.Capacidad);

        volver = findViewById(R.id.back);

        vueloSelect = findViewById(R.id.mostrarDatosVuelo);

        cancelarModificacion = findViewById(R.id.cancelar);
        guardarModificacion = findViewById(R.id.guardar);


        fechaLLegada = findViewById(R.id.Fllegada);
        fechaSalida = findViewById(R.id.Fsalida);


        String datosVueloSeleccionado  = getIntent().getStringExtra("vueloSeleccionado");
        int idVuelo = getIntent().getIntExtra("idVuelo",-1);
        vueloActual = gestorVuelos.getVuelo(idVuelo);


        vueloSelect.setText(datosVueloSeleccionado);

        guardarModificacion.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View view) {

                int diaLlegada = fechaLLegada.getDayOfMonth();
                int mesLlegada = fechaLLegada.getMonth();
                int añoLlegada = fechaLLegada.getYear();

                int diaSalida = fechaSalida.getDayOfMonth();
                int mesSalida = fechaSalida.getMonth();
                int añoSalida = fechaSalida.getYear();

                if(!horaLlegada.getText().toString().isEmpty() && !horaSalida.getText().toString().isEmpty()) {
                    String[] llegada = horaLlegada.getText().toString().split(":");
                    String[] salida = horaSalida.getText().toString().split(":");
                    LocalTime hllegada = LocalTime.of(Integer.parseInt(llegada[0].trim()),Integer.parseInt(llegada[1].trim()));
                    LocalTime hSalida = LocalTime.of(Integer.parseInt(salida[0].trim()),Integer.parseInt(salida[1].trim()));
                    vueloActual.setHoraLlegada(hllegada);
                    vueloActual.setHoraSalida(hSalida);
                }


                LocalDate fechaLlegada = LocalDate.of(añoLlegada,mesLlegada,diaLlegada);

                LocalDate fechaS = LocalDate.of(añoSalida,mesSalida,diaSalida);

                if(!origen.getText().toString().isEmpty()){
                    vueloActual.setOrigen(origen.getText().toString());
                }

                if(!destino.getText().toString().isEmpty()){
                    vueloActual.setDestino(destino.getText().toString());
                }

                if(!distancia.getText().toString().isEmpty()){
                    vueloActual.setDistancia(Double.parseDouble(distancia.getText().toString()));
                }

                if(!aerolinea.getText().toString().isEmpty()){
                    vueloActual.setAerolinea(aerolinea.getText().toString());
                }

                if(!asientos.getText().toString().isEmpty()){
                    vueloActual.setAsientosDisponibles(Integer.parseInt(asientos.getText().toString()));
                }

                if(!precio.getText().toString().isEmpty()){
                    vueloActual.setPrecio(Integer.parseInt(precio.getText().toString()));
                }

                if(!capacidad.getText().toString().isEmpty()){
                    vueloActual.setCapacidad(Integer.parseInt(capacidad.getText().toString()));
                }


                vueloActual.setFechaSalida(fechaS);
                vueloActual.setFechaLlegada(fechaLlegada);


                //se actualizan los atributos del vuelo
                gestorVuelos.modificarVuelo(idVuelo,vueloActual);

                 datos = vueloActual.getId() + ","+vueloActual.getOrigen()+","+vueloActual.getDestino() +
                        ","+vueloActual.getFechaSalida()+","+vueloActual.getHoraSalida() + ","+vueloActual.getFechaLlegada()+
                        "," + vueloActual.getHoraLlegada()+","+vueloActual.getDistancia()+","+vueloActual.getAerolinea()+
                        ","+vueloActual.getCapacidad() + ","+vueloActual.getAsientosDisponibles() + "," + vueloActual.getPrecio();

                FileManager.escribirLinea(ventana_seleccion_vuelo_modificar.this, FILE_NAME_VUELOS, datos);
                origen.setText("");
                destino.setText("");
                horaSalida.setText("");
                horaLlegada.setText("");
                distancia.setText("");
                aerolinea.setText("");
                asientos.setText("");
                capacidad.setText("");
                Toast.makeText(ventana_seleccion_vuelo_modificar.this,"Vuelo modificado correctamente",LENGTH_LONG).show();

            }



        });

        cancelarModificacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ventana_seleccion_vuelo_modificar.this,VentanaModificar.class);
                startActivity(intent);
                finish();
            }
        });
    }





}
