package com.example.miapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private DBHelper DB;

    // Declarando variables buffer
    private EditText editTextNombre, editTextEdad, editTextDireccion, editTextNumeroTelefono;
    private Spinner estadoCivilSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        DB = new DBHelper(this);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.txtNombre);
        editTextEdad = findViewById(R.id.txtEdad);
        editTextDireccion = findViewById(R.id.txtDireccion);
        editTextNumeroTelefono = findViewById(R.id.txtNumerot);
        estadoCivilSpinner = findViewById(R.id.estado_civil_spinner);

        Button reg = findViewById(R.id.btnAgregar);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarInformacionPersonal();
            }
        });
    }

    @Override
    protected void onDestroy() {
        DB.close(); // Cerrar la conexión a la base de datos al destruir la actividad
        super.onDestroy();
    }

    private void agregarInformacionPersonal() {
        String nombre = editTextNombre.getText().toString();
        String edadString = editTextEdad.getText().toString();
        String direccion = editTextDireccion.getText().toString();
        String numeroTelefono = editTextNumeroTelefono.getText().toString();
        String estadoCivil = estadoCivilSpinner.getSelectedItem().toString();

        if (nombre.isEmpty() || edadString.isEmpty() || direccion.isEmpty() || numeroTelefono.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else {
            try {
                int edad = Integer.parseInt(edadString);
                if (DB.insertPersonalInfo(nombre, edad, direccion, numeroTelefono, estadoCivil)) {
                    Toast.makeText(this, "Información Agregada Correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error al agregar información personal", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Por favor, ingresa una edad válida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}