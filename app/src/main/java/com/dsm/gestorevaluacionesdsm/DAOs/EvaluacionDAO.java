package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.EvaluacionesCreadas;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;

import java.util.ArrayList;
import java.util.List;

public class EvaluacionDAO {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;
    public EvaluacionDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public long insertarEvaluacion(Evaluacion evaluacion)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre_evaluacion", evaluacion.getNombreEvaluacion());
        values.put("descripcion_evaluacion", evaluacion.getDescripcionEvaluacion());
        values.put("img", evaluacion.getImg());

        long result = db.insert("evaluaciones", null, values);

        db.close();
        return result;
    }

    public List<Evaluacion> obtenerTodasLasEvaluaciones() {
        List<Evaluacion> evaluaciones = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM evaluaciones", null);
        if (cursor.moveToFirst()) {
            do {
                Evaluacion evaluacion = new Evaluacion();
                evaluacion.setIdEvaluacion(cursor.getInt(cursor.getColumnIndex("id_evaluacion")));
                evaluacion.setNombreEvaluacion(cursor.getString(cursor.getColumnIndex("nombre_evaluacion")));
                evaluacion.setDescripcionEvaluacion(cursor.getString(cursor.getColumnIndex("descripcion_evaluacion")));
                evaluacion.setImg(cursor.getString(cursor.getColumnIndex("img")));
                evaluaciones.add(evaluacion);
            } while (cursor.moveToNext());
        }



        cursor.close();
        db.close();

        return evaluaciones;
    }

    public int obtenerCantidadPreguntasEvaluacion(int idEvaluacion){
        int cantidadPreguntas = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM preguntas_evaluacion WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                cantidadPreguntas = cursor.getInt(0);
            }
            cursor.close();
        }


        db.close();

        return cantidadPreguntas;
    }

    public String[] obtenerDescripcionEvaluacion(int idEvaluacion){

        String[] informacionEvaluacion = new String[2];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT nombre_evaluacion,descripcion_evaluacion FROM evaluaciones WHERE id_evaluacion = ?", new String[]{String.valueOf(idEvaluacion)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                informacionEvaluacion[0] = cursor.getString(cursor.getColumnIndex("nombre_evaluacion"));
                informacionEvaluacion[1] = cursor.getString(cursor.getColumnIndex("descripcion_evaluacion"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }


        db.close();

        return informacionEvaluacion;
    }

}
