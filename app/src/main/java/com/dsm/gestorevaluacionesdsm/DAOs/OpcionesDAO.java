package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Opciones;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;

import java.util.ArrayList;
import java.util.List;

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


    public List<Opciones> obtenerOpcionesPregunta(int idPregunta) {
        List<Opciones> opciones = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pregunta_opciones WHERE id_pregunta = "+idPregunta, null);
        if (cursor.moveToFirst()) {
            do {
                Opciones opcion = new Opciones();
                opcion.setIdOpcion(cursor.getInt(cursor.getColumnIndex("id_opcion")));
                opcion.setOpcion(cursor.getString(cursor.getColumnIndex("opcion")));
                opcion.setEsCorrecta(cursor.getInt(cursor.getColumnIndex("respuesta_correcta")));
                opciones.add(opcion);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return opciones;
    }


    public String obtenerOpcion(int idOpcion){
        String opcion = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT opcion FROM pregunta_opciones WHERE id_opcion = ?", new String[]{String.valueOf(idOpcion)});
        if (cursor.moveToFirst()) {
            do {
                opcion = cursor.getString(cursor.getColumnIndex("opcion"));
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return opcion;
    }

    public int obtenerCorrecta(int idOpcion){
        int correcta = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT respuesta_correcta FROM pregunta_opciones WHERE id_opcion = ?", new String[]{String.valueOf(idOpcion)});
        if (cursor.moveToFirst()) {
            do {
                correcta = cursor.getInt(cursor.getColumnIndex("respuesta_correcta"));
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return correcta;
    }

    /*public int modificarOpcion(Opciones opciones, int idOpcion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("opcion", opciones.getOpcion());
        values.put("respuesta_correcta", opciones.getEsCorrecta());


        // La condición WHERE especifica qué lista debe ser actualizada
        String whereClause = "id_opcion = ?";
        String[] whereArgs = {String.valueOf(idOpcion)};

        int result = db.update("pregunta_opciones", values, whereClause, whereArgs);

        db.close();
        return result;
    }*/

    public int modificarOpcion(Opciones opciones, int idOpcion, int idPregunta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("opcion", opciones.getOpcion());

        // Verificar si la opción que se va a modificar es la correcta
        if (opciones.getEsCorrecta() == 1) {
            // Verificar si ya existe otra opción marcada como correcta para la misma pregunta
            String query = "SELECT COUNT(*) FROM pregunta_opciones WHERE id_pregunta = ? AND respuesta_correcta = 1 AND id_opcion != ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idPregunta), String.valueOf(idOpcion)});
            cursor.moveToFirst();
            int opcionesCorrectas = cursor.getInt(0);
            cursor.close();

            // Si ya hay otra opción marcada como correcta, no modificar la opción actual
            if (opcionesCorrectas > 0) {
                db.close();
                return -2; // Indica que ya existe otra opción marcada como correcta
            }
        }

        values.put("respuesta_correcta", opciones.getEsCorrecta());

        // La condición WHERE especifica qué lista debe ser actualizada
        String whereClause = "id_opcion = ?";
        String[] whereArgs = {String.valueOf(idOpcion)};

        int result = db.update("pregunta_opciones", values, whereClause, whereArgs);

        db.close();
        return result;
    }
    public int eliminarOpcion(int idOpcion){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "id_opcion = ?";
        String[] whereArgs = {String.valueOf(idOpcion)};

        int result = db.delete("pregunta_opciones", whereClause, whereArgs);
        //int resultEvaluation = db.delete("evaluaciones", whereClause, whereArgs);

        db.close();
        return result;
    }
}
