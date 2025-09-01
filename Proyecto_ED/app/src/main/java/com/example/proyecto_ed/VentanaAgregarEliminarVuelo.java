package com.example.proyecto_ed;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.ReservarVuelo;
import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorVuelos;
import com.example.proyecto_ed.Services.GraphManager;
import com.example.proyecto_ed.Td.TDA_GRAFO.Graph;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class VentanaAgregarEliminarVuelo extends AppCompatActivity {

    private EditText etOrigen, etDestino, etFS, etFL, etHS, etHL, etDistancia, etAerolinea, etCapacidad, etAsientos, etPrecio;
    private MaterialButton btnAgregarVuelo, btnEliminarVuelo;
    private ListView listViewVuelos;
    private GestorVuelos gestorVuelos = GestorVuelos.getInstance();
    private GraphManager graphManager = GraphManager.getInstance(null);
    private static final String NAME_FILE_VUELOS = "Vuelos.txt";
    private List<Vuelo> vueloList;
    private Vuelo select;
    private int selectedHour = 0;
    private int selectedMinute = 0;
    private int selectedYear, selectedMonth, selectedDay;
    String fechaSalida, fechaLlegada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_agregar_eliminar_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.ventanaAgregar), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etOrigen = findViewById(R.id.origen);
        etDestino = findViewById(R.id.destino);
        etFS = findViewById(R.id.Fsalida);
        etFL = findViewById(R.id.Fllegada);
        etHS = findViewById(R.id.HoraSalida);
        etHL = findViewById(R.id.HoraLlegada);
        etDistancia = findViewById(R.id.distancia);
        etAerolinea = findViewById(R.id.aerolinea);
        etCapacidad = findViewById(R.id.capacidad);
        etAsientos = findViewById(R.id.asientosDisp);
        etPrecio = findViewById(R.id.precio);

        btnAgregarVuelo = findViewById(R.id.btn_agregar_vuelo);
        btnEliminarVuelo = findViewById(R.id.btn_eliminar_vuelo);
        listViewVuelos = findViewById(R.id.ls_listar_vuelos);

        //Establecer reloj
        etHS.setOnClickListener(v -> showTimePickerDialog(etHS));
        etHL.setOnClickListener(v -> showTimePickerDialog(etHL));

        //Establecer calendario
        etFS.setOnClickListener(v -> showDatePickerDialog(etFS, "fechaSalida"));
        etFL.setOnClickListener(v -> showDatePickerDialog(etFL, "fechaLlegada"));

        cargarVuelos();

        btnAgregarVuelo.setOnClickListener(v -> {
            String origen = etOrigen.getText().toString();
            String destino = etDestino.getText().toString();
            String fechaS = etFS.getText().toString();
            String fechaL = etFL.getText().toString();
            String horaS = etHS.getText().toString();
            String horaL = etHL.getText().toString();
            String distancia = etDistancia.getText().toString();
            String aerolinea = etAerolinea.getText().toString();
            String capacidad = etCapacidad.getText().toString();
            String asientodD = etAsientos.getText().toString();
            String precio = etPrecio.getText().toString();

            if (validarCadena(origen) || validarCadena(destino) || validarCadena(fechaS) ||
                    validarCadena(fechaL) || validarCadena(horaS) || validarCadena(horaL) ||
                    validarCadena(distancia) || validarCadena(aerolinea) || validarCadena(capacidad) ||
                    validarCadena(asientodD) || validarCadena(precio)) {
                try {
                    //Crear el objeto
                    int id = gestorVuelos.getLastID() + 1;
                    Vuelo vuelo = new Vuelo(
                            id,
                            origen,
                            destino,
                            LocalDate.parse(fechaSalida),
                            LocalTime.parse(horaS),
                            LocalDate.parse(fechaLlegada),
                            LocalTime.parse(horaL),
                            Double.parseDouble(distancia),
                            aerolinea,
                            Integer.parseInt(capacidad),
                            Integer.parseInt(asientodD),
                            Double.parseDouble(precio)
                    );

                    //Guardar en gestor vuelos
                    gestorVuelos.agregarVuelos(vuelo);

                    //Guardar en el archivo
                    String cadena = id+","+origen+","+destino+","+fechaSalida+","+horaS+","+fechaLlegada+","+
                            horaL+","+vuelo.getDuracion()+","+distancia+","+aerolinea+","+capacidad+","+
                            asientodD+","+precio;
                    FileManager.escribirLinea(this, NAME_FILE_VUELOS, cadena);

                    //Hacer la conexion en grafo
                    graphManager.agregarConexion(origen, destino, vuelo.getDuracion());
                    Toast.makeText(this, "Su vuelo ha sido agregado con exito", Toast.LENGTH_SHORT).show();
                    limpiarCasillas();
                    //Actualizar Listview
                    cargarVuelos();
                } catch (Exception e) {
                    Toast.makeText(this, "Ha ocurrido un error al intentar añadir el vuelo", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(this, "Llene los datos de manera correcta", Toast.LENGTH_SHORT).show();
            }

        });

        btnEliminarVuelo.setOnClickListener(v -> {
            if (select != null) {
                int index = gestorVuelos.getVuelos().indexOf(select);
                //Borrar de gestoVuelos
                gestorVuelos.getVuelos().remove(index);

                //Borrar de archivos
                List<String> lectura = FileManager.leerArchivo(this, NAME_FILE_VUELOS);
                lectura.remove(index);
                FileManager.sobreescribir(this, NAME_FILE_VUELOS, lectura);

                //Borrar conexion de grafo
                graphManager.eliminarConexion(select.getOrigen(), select.getDestino());

                //Actualizar ListView
                cargarVuelos();
            } else {
                Toast.makeText(this, "Seleccione el vuelo a eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actualizarLista(){
        vueloList = gestorVuelos.getVuelos();
    }

    public boolean validarCadena(String cadena){
        if (cadena == null || cadena.isEmpty()) return false;
        return true;
    }

    public void limpiarCasillas(){
        etOrigen.getText().clear();
        etDestino.getText().clear();
        etFS.getText().clear();
        etFL.getText().clear();
        etHS.getText().clear();
        etHL.getText().clear();
        etDistancia.getText().clear();
        etAerolinea.getText().clear();
        etCapacidad.getText().clear();
        etAsientos.getText().clear();
        etPrecio.getText().clear();
    }

    public void cargarVuelos(){

        actualizarLista();

        ArrayAdapter<Vuelo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout por defecto
                vueloList
        );

        listViewVuelos.setAdapter(adapter);

        listViewVuelos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select = vueloList.get(position);
            }
        });
    }

    //Reloj desplegable
    private void showTimePickerDialog(EditText editText) {
        // Crear un diálogo personalizado
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_time_picker);
        dialog.setTitle("Seleccionar Hora");

        TimePicker timePicker = dialog.findViewById(R.id.timePicker);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);

        // Configurar hora actual por defecto
        Calendar calendar = Calendar.getInstance();
        timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setMinute(calendar.get(Calendar.MINUTE));

        btnConfirm.setOnClickListener(v -> {
            selectedHour = timePicker.getHour();
            selectedMinute = timePicker.getMinute();

            String timeString = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);
            editText.setText(timeString);

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    //Calendario desplegable
    private void showDatePickerDialog(EditText editText, String date) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_date_picker);
        dialog.setTitle("Seleccionar Fecha");

        DatePicker datePicker = dialog.findViewById(R.id.datePicker);
        Button btnConfirm = dialog.findViewById(R.id.btnConfirmDate);
        Button btnCancel = dialog.findViewById(R.id.btnCancelDate);

        // Configurar fecha actual por defecto
        datePicker.init(selectedYear, selectedMonth, selectedDay, null);

        btnConfirm.setOnClickListener(v -> {
            selectedYear = datePicker.getYear();
            selectedMonth = datePicker.getMonth();
            selectedDay = datePicker.getDayOfMonth();

            updateEditText(editText);
            dialog.dismiss();
            saveDate(date); // Guardar automáticamente al confirmar
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void updateEditText(EditText editText) {
        String dateString = String.format(Locale.getDefault(), "%02d/%02d/%d", selectedDay, selectedMonth + 1, selectedYear);
        editText.setText(dateString);
    }

    private void saveDate(String date) {
        String dateString = String.format(Locale.getDefault(), "%02d-%02d-%d", selectedYear, selectedMonth + 1, selectedDay);
        if (date.equals("fechaSalida")){
            fechaSalida = dateString;
        } else if (date.equals("fechaLlegada")) {
            fechaLlegada = dateString;
        }
    }

}
