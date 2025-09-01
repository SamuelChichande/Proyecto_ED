package com.example.proyecto_ed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto_ed.Models.Cliente;
import com.example.proyecto_ed.Models.ReservarVuelo;
import com.example.proyecto_ed.Models.Usuario;
import com.example.proyecto_ed.Models.Vuelo;
import com.example.proyecto_ed.Services.FileManager;
import com.example.proyecto_ed.Services.GestorUsuarios;
import com.example.proyecto_ed.Services.ReservarManager;
import com.google.android.material.button.MaterialButton;

import java.util.LinkedList;
import java.util.List;

public class VentanaCancelarVuelo extends AppCompatActivity {
    private ListView reservas;
    private MaterialButton btnCancelar;
    private final ReservarManager reservarManager = ReservarManager.getInstance();
    private final GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();
    private static final String NAME_FILE_RESERVAS = "Reservas.txt";
    private Usuario user;
    private ReservarVuelo select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.ventana_cancelar_vuelo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        reservas = findViewById(R.id.lv_reservas_usuario);
        btnCancelar = findViewById(R.id.btn_Cancelar_Reserva);

        Intent intent = getIntent();
        user = (Usuario) intent.getSerializableExtra("usuario");

        cargarReservas();

        btnCancelar.setOnClickListener(v -> {
            if (select != null){
                //Eliminar del reservarManager
                reservarManager.eliminarReserva(select);

                //Eliminar de archivos
                int index = reservarManager.getReservas().indexOf(select);
                List<String> lectura = FileManager.leerArchivo(this,NAME_FILE_RESERVAS);
                lectura.remove(index);
                FileManager.sobreescribir(this,NAME_FILE_RESERVAS, lectura);

                //Eliminar de la lista del cliente
                for (Usuario user: gestorUsuarios.getUsuarios()){
                    if (!user.isAdmin() && user.getIdUser() == select.getIdUser()) {
                        Cliente c = (Cliente) user;
                        c.cancelarReserva(select.getId());
                    }
                }

                Toast.makeText(this, "Su reserva ha sido cancelada de manera exitosa", Toast.LENGTH_SHORT).show();


            } else{
                Toast.makeText(this, "Seleccione la reserva a cancelar", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void cargarReservas(){
        List<ReservarVuelo> list = new LinkedList<>();

        for (ReservarVuelo reservarVuelo: reservarManager.getReservas()){
            if (reservarVuelo.getIdUser() == user.getIdUser() &&
                    reservarVuelo.getNameUser().equals(user.getUsuario())){
                list.add(reservarVuelo);
            }
        }

        ArrayAdapter<ReservarVuelo> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1, // Layout por defecto
                list
        );

        reservas.setAdapter(adapter);

        reservas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                select = list.get(position);
            }
        });
    }
}
