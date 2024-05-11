package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;

public class OpcionesDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public OpcionesDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    /*public long insertarOpcion(Opciones opcion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Contar cuántas opciones tiene ya esta pregunta
        String countQuery = "SELECT COUNT(*) FROM pregunta_opciones WHERE id_pregunta = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{String.valueOf(opcion.getIdPregunta())});
        cursor.moveToFirst();
        int numeroOpciones = cursor.getInt(0);
        cursor.close();

        // Verificar que la pregunta no tenga ya tres opciones
        if (numeroOpciones < 3) {
            // Si la pregunta tiene menos de tres opciones, podemos insertar una nueva

            // Verificar si ya existe una opción marcada como correcta para la misma pregunta
            if (opcion.getEsCorrecta() == 1) {
                String query = "SELECT COUNT(*) FROM pregunta_opciones WHERE id_pregunta = ? AND respuesta_correcta = 1";
                cursor = db.rawQuery(query, new String[]{String.valueOf(opcion.getIdPregunta())});
                cursor.moveToFirst();
                int opcionesCorrectas = cursor.getInt(0);
                cursor.close();

                // Si ya hay una opción marcada como correcta, mostrar un Toast y no insertar la nueva opción
                if (opcionesCorrectas > 0) {
                    return -1;
                }
            }

            ContentValues values = new ContentValues();
            values.put("opcion", opcion.getOpcion());
            values.put("respuesta_correcta", opcion.getEsCorrecta());
            values.put("id_pregunta", opcion.getIdPregunta());

            long result = db.insert("pregunta_opciones", null, values);

            db.close();
            return result;
        } else {
            return -3;
        }
    }*/

    public long insertarOpcion(Opciones opcion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Contar cuántas opciones tiene ya esta pregunta
        String countQuery = "SELECT COUNT(*) FROM pregunta_opciones WHERE id_pregunta = ?";
        Cursor cursor = db.rawQuery(countQuery, new String[]{String.valueOf(opcion.getIdPregunta())});
        cursor.moveToFirst();
        int numeroOpciones = cursor.getInt(0);
        cursor.close();

        // Verificar que la pregunta no tenga ya tres opciones
        if (numeroOpciones < 3) {
            // Si la pregunta tiene menos de tres opciones, podemos insertar una nueva

            // Verificar si ya existe una opción marcada como correcta para la misma pregunta
            if (opcion.getEsCorrecta() == 1) {
                String query = "SELECT COUNT(*) FROM pregunta_opciones WHERE id_pregunta = ? AND respuesta_correcta = 1";
                cursor = db.rawQuery(query, new String[]{String.valueOf(opcion.getIdPregunta())});
                cursor.moveToFirst();
                int opcionesCorrectas = cursor.getInt(0);
                cursor.close();

                // Si ya hay una opción marcada como correcta, no insertar la nueva opción
                if (opcionesCorrectas > 0) {
                    return -2; // Indica que ya existe una opción marcada como correcta
                }
            }

            ContentValues values = new ContentValues();
            values.put("opcion", opcion.getOpcion());
            values.put("respuesta_correcta", opcion.getEsCorrecta());
            values.put("id_pregunta", opcion.getIdPregunta());

            long result = db.insert("pregunta_opciones", null, values);

            db.close();
            return result; // Devuelve el ID del nuevo registro insertado
        } else {
            return -3; // Indica que la pregunta ya tiene 3 opciones
        }
    }



}
