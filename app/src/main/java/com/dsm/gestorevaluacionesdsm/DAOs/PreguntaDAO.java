package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;
import com.dsm.gestorevaluacionesdsm.Modelos.PreguntaConOpciones;

import java.util.ArrayList;
import java.util.List;

public class PreguntaDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public PreguntaDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertarPregunta(Pregunta pregunta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pregunta", pregunta.getPregunta());
        values.put("valoracion", pregunta.getValoracion());
        values.put("id_evaluacion", pregunta.getIdEvaluacion());

        long result = db.insert("preguntas_evaluacion", null, values);

        db.close();
        return result;


    }

    public float puntajeDisponible(int idEvaluacion){
        float puntajeAcumulado = 0;
        float puntajeDispo = 0;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(valoracion) FROM preguntas_evaluacion WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                puntajeAcumulado = cursor.getInt(0);
            }
            cursor.close();
        }

        puntajeDispo = 10 - puntajeAcumulado;
        db.close();

        return puntajeDispo;
    }

    public List<Pregunta> obtenerPreguntasEvaluacion(int idEvaluacion) {
        List<Pregunta> preguntas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id_pregunta, pregunta,valoracion FROM preguntas_evaluacion WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        if (cursor.moveToFirst()) {
            do {
                Pregunta pregunta = new Pregunta();
                pregunta.setIdPregunta(cursor.getInt(cursor.getColumnIndex("id_pregunta")));
                pregunta.setPregunta(cursor.getString(cursor.getColumnIndex("pregunta")));
                pregunta.setValoracion(cursor.getFloat(cursor.getColumnIndex("valoracion")));
                preguntas.add(pregunta);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return preguntas;
    }

    public String obtenerNombreEvaluacion(int idEvaluacion){
        String nombre = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre_evaluacion FROM evaluaciones WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        if (cursor.moveToFirst()) {
            do {
                nombre = cursor.getString(cursor.getColumnIndex("nombre_evaluacion"));
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return nombre;
    }

    public String obtenerPregunta(int idPregunta){
        String nombre = "";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT pregunta FROM preguntas_evaluacion WHERE id_pregunta = ?", new String[]{String.valueOf(idPregunta)});
        if (cursor.moveToFirst()) {
            do {
                nombre = cursor.getString(cursor.getColumnIndex("pregunta"));
            } while (cursor.moveToNext());
        }

        return nombre;
    }

    public Float obtenerValoracion(int idPregunta){
        Float valoracion = 0.0f;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT valoracion FROM preguntas_evaluacion WHERE id_pregunta = ?", new String[]{String.valueOf(idPregunta)});
        if (cursor.moveToFirst()) {
            do {
                valoracion = cursor.getFloat(cursor.getColumnIndex("valoracion"));
            } while (cursor.moveToNext());
        }

        return valoracion;
    }

    public int eliminarPregunta(int idPregunta){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String whereClause = "id_pregunta= ?";
        String[] whereArgs = {String.valueOf(idPregunta)};

        int result = db.delete("pregunta_opciones", whereClause, whereArgs);
        int resultPregunta = db.delete("preguntas_evaluacion", whereClause, whereArgs);

        db.close();
        return resultPregunta;
    }

    public int modificarPregunta(Pregunta pregunta, int idPregunta) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("pregunta", pregunta.getPregunta());
        values.put("valoracion", pregunta.getValoracion());


        // La condición WHERE especifica qué lista debe ser actualizada
        String whereClause = "id_pregunta = ?";
        String[] whereArgs = {String.valueOf(idPregunta)};

        int result = db.update("preguntas_evaluacion", values, whereClause, whereArgs);

        db.close();
        return result;
    }

    public List<PreguntaConOpciones> obtenerPreguntasConOpciones(int idEvaluacion) {
        //
        return null;
    }
}