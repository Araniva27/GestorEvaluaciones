package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Persona;

public class PersonaDAO {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    public PersonaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    //Función para registrar personas (usuarios)
    public long insertarPersona(Persona persona)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_persona", persona.getNombrePersona());
        values.put("apellido_persona", persona.getApellidoPersona());
        values.put("telefono_persona", persona.getTelefonoPersona());
        values.put("correo_persona", persona.getCorreoPersona());
        values.put("usuario_persona", persona.getUsuarioPersona());
        values.put("contraseña_persona", persona.getContrasenaPersona());
        values.put("id_tipo_usuario", persona.getTipoUsuarioPersona());

        long result = db.insert("personas", null, values);

        db.close();
        return result;
    }
}
