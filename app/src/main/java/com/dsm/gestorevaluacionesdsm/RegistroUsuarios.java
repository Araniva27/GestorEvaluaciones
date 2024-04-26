package com.dsm.gestorevaluacionesdsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dsm.gestorevaluacionesdsm.DAOs.PersonaDAO;
import com.dsm.gestorevaluacionesdsm.Modelos.Persona;
import com.dsm.gestorevaluacionesdsm.Validator.Validator;

import org.apache.commons.codec.digest.DigestUtils;

public class RegistroUsuarios extends AppCompatActivity {

    Button btnRegistrar, btnRegresar;
    EditText txtNombre, txtApellido, txtCorreo, txtTelefono, txtUsuario, txtContra, txtContraRepeticion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContra = findViewById(R.id.txtContrasena);
        txtContraRepeticion = findViewById(R.id.txtValidacionContrasena);
        txtCorreo = findViewById(R.id.txtCorreo);
        btnRegistrar = findViewById(R.id.btnRegistrarUsuario);
        btnRegresar = findViewById(R.id.btnRegresarLogin);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroUsuarios.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = txtNombre.getText().toString().trim();
                String apellido = txtApellido.getText().toString().trim();
                String telefono = txtTelefono.getText().toString().trim();
                String correo = txtCorreo.getText().toString().trim();
                String usuario = txtUsuario.getText().toString().trim();
                String contraseña = txtContra.getText().toString().trim();
                String contraseñaRepeticion = txtContraRepeticion.getText().toString().trim();

                if (nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || usuario.isEmpty() || contraseña.isEmpty() || contraseñaRepeticion.isEmpty()) {
                    Toast.makeText(RegistroUsuarios.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    if(Validator.validarCorreo(correo))
                    {
                        if(Validator.validarTelefono(telefono))
                        {
                            if (contraseña.equals(contraseñaRepeticion)) {
                                Persona persona = new Persona();
                                persona.setNombrePersona(nombre);
                                persona.setApellidoPersona(apellido);
                                persona.setTelefonoPersona(telefono);
                                persona.setUsuarioPersona(usuario);
                                persona.setContrasenaPersona(generarHashSHA256(contraseña));
                                persona.setCorreoPersona(correo);
                                persona.setTipoUsuarioPersona(usuario.equals("Administrador") ? "1" : "2");

                                PersonaDAO pDao = new PersonaDAO(RegistroUsuarios.this);
                                if(pDao.insertarPersona(persona) != -1){
                                    Toast.makeText(RegistroUsuarios.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                                    limpiarCampos();
                                }else{
                                    Toast.makeText(RegistroUsuarios.this, "Ha ocurrido un error al registrar el usuario", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(RegistroUsuarios.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(RegistroUsuarios.this, "Formato incorrecto de teléfono", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(RegistroUsuarios.this, "Formato incorrecto de correo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void limpiarCampos()
    {
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
        txtUsuario.setText("");
        txtContra.setText("");
        txtContraRepeticion.setText("");
    }

    public static String generarHashSHA256(String input) {
        return DigestUtils.sha256Hex(input);
    }
}