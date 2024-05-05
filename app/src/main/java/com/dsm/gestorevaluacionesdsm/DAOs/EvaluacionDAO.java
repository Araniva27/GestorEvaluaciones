package com.dsm.gestorevaluacionesdsm.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dsm.gestorevaluacionesdsm.DBHelper.DBHelper;
import com.dsm.gestorevaluacionesdsm.EvaluacionesCreadas;
import com.dsm.gestorevaluacionesdsm.Modelos.Evaluacion;

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
}
