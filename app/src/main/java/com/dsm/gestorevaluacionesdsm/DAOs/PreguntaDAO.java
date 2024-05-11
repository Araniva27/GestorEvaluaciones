package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;

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
        Cursor cursor = db.rawQuery("SELECT id_pregunta, pregunta FROM preguntas_evaluacion WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        if (cursor.moveToFirst()) {
            do {
                Pregunta pregunta = new Pregunta();
                pregunta.setIdPregunta(cursor.getInt(cursor.getColumnIndex("id_pregunta")));
                pregunta.setPregunta(cursor.getString(cursor.getColumnIndex("pregunta")));
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
}
