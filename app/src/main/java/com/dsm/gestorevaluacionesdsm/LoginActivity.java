package com.dsm.gestorevaluacionesdsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DAOs.PersonaDAO;

import org.apache.commons.codec.digest.DigestUtils;

public class LoginActivity extends AppCompatActivity {

    Button btnRegistrar, btnIngresar;
    EditText txtUsuario, txtContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContrasena = findViewById(R.id.txtContrasena);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrarse);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistroUsuarios.class);
                startActivity(intent);
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usuario = txtUsuario.getText().toString().trim();
                String contrasena = txtContrasena.getText().toString().trim();
                String contrasenaHash = generarHashSHA256(contrasena);

                if(usuario.isEmpty() || contrasena.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Debe de ingresar todos los datos", Toast.LENGTH_SHORT).show();
                }else{
                    PersonaDAO pDao = new PersonaDAO(LoginActivity.this);
                    long datosUsuario[] = pDao.iniciarSesion(usuario, contrasenaHash);

                    if(datosUsuario != null)
                    {
                        if(datosUsuario[1] == 1)
                        {
                            Intent intent = new Intent(LoginActivity.this, EvaluacionesCreadas.class);
                            startActivity(intent);
                            Toast.makeText(LoginActivity.this, "Bienvenido Administrador", Toast.LENGTH_SHORT).show();
                        } else if (datosUsuario[1] == 2){
                            Toast.makeText(LoginActivity.this, "Bienvenido Usuario", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MenuUsuario.class);
                            startActivity(intent);
                        }

                    }else{
                        Toast.makeText(LoginActivity.this, "Información incorrecta", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public static String generarHashSHA256(String input) {
        return DigestUtils.sha256Hex(input);
    }
}