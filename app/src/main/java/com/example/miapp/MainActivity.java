package com.example.miapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    //Decalaramos variables para instanciar componentes
EditText username , password, repassword;
Button singnup , singnin;

DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializamos varibles

        //para los campos de texto
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        repassword =(EditText)findViewById(R.id.repassword);

        //para los botones
        singnup =(Button)findViewById(R.id.btnsinup);
        singnin =(Button)findViewById(R.id.btnsignin);

        //para las variable de helper
        DB = new DBHelper(this);

        // Metodos para recibir click de los botones setclick

        singnup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = username.getText().toString();
                String pass =password.getText().toString();
                String repass = repassword.getText().toString();

                if (user.equals("")|| pass.equals("")||repass.equals("")){
                    Toast.makeText(MainActivity.this,"Rellene todos los campos",Toast.LENGTH_LONG).show();
                }else {

                    if (pass.equals(repass)){

                        boolean checkuser = DB.checkusername(user);

                        if(checkuser ==false){
                            boolean insert = DB.insertData(user,pass);

                            if (insert==true){
                                Toast.makeText(MainActivity.this, "Registrado Exisitosamente", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);

                            }else {
                                Toast.makeText(MainActivity.this, "Fallo al Registrarse", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "El usuario ya existe inicie Sessión", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(MainActivity.this, "Contraseña y usuario nno coinciden", Toast.LENGTH_SHORT).show();
                    }
                    

                }


            }
        });


        singnin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);

            }
        });




    }
}