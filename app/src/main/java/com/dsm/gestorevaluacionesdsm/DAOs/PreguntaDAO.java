package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.Modelos.Pregunta;

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
}
