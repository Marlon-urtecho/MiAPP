package com.example.miapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Declarar variables para instanciar componentes
    EditText username, password, repassword;
    Button signup, signin;

    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar variables
        // Para los campos de texto
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);

        // Para los botones
        signup = findViewById(R.id.btnsinup);
        signin = findViewById(R.id.btnsignin);

        // Para la variable de helper
        DB = new DBHelper(this);

        // Métodos para recibir clics de los botones
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSignup();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        DB.close(); // Cerrar la conexión a la base de datos al destruir la actividad
        super.onDestroy();
    }

    private void handleSignup() {
        String user = username.getText().toString();
        String pass = password.getText().toString();
        String repass = repassword.getText().toString();

        if (user.equals("") || pass.equals("") || repass.equals("")) {
            Toast.makeText(MainActivity.this, "Rellene todos los campos", Toast.LENGTH_LONG).show();
        } else {
            if (pass.equals(repass)) {
                boolean checkuser = DB.checkUsername(user);

                if (!checkuser) {
                    boolean insert = DB.insertData(user, pass);

                    if (insert) {
                        Toast.makeText(MainActivity.this, "Registrado Exitosamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Fallo al Registrarse", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "El usuario ya existe, inicie sesión", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Contraseña y usuario no coinciden", Toast.LENGTH_SHORT).show();
            }
        }
    }
}