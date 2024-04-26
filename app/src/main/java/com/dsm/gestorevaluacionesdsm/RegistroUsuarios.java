package com.dsm.gestorevaluacionesdsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistroUsuarios extends AppCompatActivity {

    Button btnRegistrar, btnRegresar;
    EditText txtNombre, txtApellido, txtCorreo, txtTelefono, txtUsuario, txtContra, txtContraRepeticion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        btnRegresar = findViewById(R.id.btnRegresarLogin);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroUsuarios.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}