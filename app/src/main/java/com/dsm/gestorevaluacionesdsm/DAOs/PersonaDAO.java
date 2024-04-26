package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Persona;

import java.lang.reflect.Array;

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

    public long[] iniciarSesion(String usuario, String contrasena)
    {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projeccion = { "id_persona", "contraseña_persona", "id_tipo_usuario" };

        String seleccion = "usuario_persona = ? AND contraseña_persona = ?";

        String[] argumentosSeleccion = { usuario, contrasena };

        Cursor cursor = db.query("personas", projeccion, seleccion, argumentosSeleccion, null, null, null);

        long[] datosUsuario = new long[2];

        if(cursor != null && cursor.moveToFirst())
        {
            long idUsuario = cursor.getLong(cursor.getColumnIndexOrThrow("id_persona"));
            long idTipoUsuaro = cursor.getLong(cursor.getColumnIndexOrThrow("id_tipo_usuario"));
            datosUsuario[0] = idUsuario;
            datosUsuario[1] = idTipoUsuaro;
            cursor.close();
            return datosUsuario;
        }else{
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }

    }
}
