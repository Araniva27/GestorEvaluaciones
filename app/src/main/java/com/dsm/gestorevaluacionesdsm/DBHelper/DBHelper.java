package com.dsm.gestorevaluacionesdsm.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "GestorEvaluaciones.db";
    private static final int DATABASE_VERSION = 1;

    // Constantes para la tabla detalle_respuestas_estudiante
    private static final String TABLE_DETALLE_RESPUESTAS_ESTUDIANTE = "detalle_respuestas_estudiante";
    private static final String COLUMN_ID_DETALLE = "id_detalle";
    private static final String COLUMN_ID_PERSONA_DE = "id_persona"; // Cambiado de COLUMN_ID_PERSONA
    private static final String COLUMN_ID_PREGUNTA_DE = "id_pregunta"; // Cambiado de COLUMN_ID_PREGUNTA
    private static final String COLUMN_ID_OPCION = "id_opcion";

    // Constantes para la tabla tipo_usuario
    private static final String TABLE_TIPO_USUARIO = "tipo_usuario";
    private static final String COLUMN_ID_TIPO_USUARIO = "id_tipo_usuario";
    private static final String COLUMN_TIPO_USUARIO = "tipo_usuario";

    // Constantes para la tabla evaluaciones
    private static final String TABLE_EVALUACIONES = "evaluaciones";
    private static final String COLUMN_ID_EVALUACION = "id_evaluacion";
    private static final String COLUMN_NOMBRE_EVALUACION = "nombre_evaluacion";
    private static final String COLUMN_DESCRIPCION_EVALUACION = "descripcion_evaluacion";
    private static final String COLUMN_IMG = "img";

    // Constantes para la tabla preguntas_evaluacion
    private static final String TABLE_PREGUNTAS_EVALUACION = "preguntas_evaluacion";
    private static final String COLUMN_ID_PREGUNTA_PE = "id_pregunta"; // Cambiado de COLUMN_ID_PREGUNTA
    private static final String COLUMN_PREGUNTA = "pregunta";
    private static final String COLUMN_VALORACION = "valoracion";
    private static final String COLUMN_ID_EVALUACION_PE = "id_evaluacion";

    // Constantes para la tabla personas
    private static final String TABLE_PERSONAS = "personas";
    private static final String COLUMN_ID_PERSONA = "id_persona";
    private static final String COLUMN_NOMBRE_PERSONA = "nombre_persona";
    private static final String COLUMN_APELLIDO_PERSONA = "apellido_persona";
    private static final String COLUMN_TELEFONO_PERSONA = "telefono_persona";
    private static final String COLUMN_CORREO_PERSONA = "correo_persona";
    private static final String COLUMN_USUARIO_PERSONA = "usuario_persona";
    private static final String COLUMN_CONTRASEÑA_PERSONA = "contraseña_persona";
    private static final String COLUMN_ID_TIPO_USUARIO_PERSONA = "id_tipo_usuario";

    // Constantes para la tabla pregunta_opciones
    private static final String TABLE_PREGUNTA_OPCIONES = "pregunta_opciones";
    private static final String COLUMN_ID_OPCION_PO = "id_opcion";
    private static final String COLUMN_OPCION = "opcion";
    private static final String COLUMN_RESPUESTA_CORRECTA = "respuesta_correcta";
    private static final String COLUMN_ID_PREGUNTA_PO = "id_pregunta";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla detalle_respuestas_estudiante
        String queryDetalleRespuestasEstudiante = "CREATE TABLE " + TABLE_DETALLE_RESPUESTAS_ESTUDIANTE +
                " (" + COLUMN_ID_DETALLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ID_PERSONA_DE + " INTEGER NOT NULL, " +
                COLUMN_ID_PREGUNTA_DE + " INTEGER NOT NULL, " +
                COLUMN_ID_OPCION + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_ID_PREGUNTA_DE + ") REFERENCES " + TABLE_PREGUNTAS_EVALUACION + "(" + COLUMN_ID_PREGUNTA_PE + "), " +
                "FOREIGN KEY (" + COLUMN_ID_OPCION + ") REFERENCES " + TABLE_PREGUNTA_OPCIONES + "(" + COLUMN_ID_OPCION_PO + "), " +
                "FOREIGN KEY (" + COLUMN_ID_PERSONA_DE + ") REFERENCES " + TABLE_PERSONAS + "(" + COLUMN_ID_PERSONA + "));";
        db.execSQL(queryDetalleRespuestasEstudiante);

        // Crear la tabla tipo_usuario
        String queryTipoUsuario = "CREATE TABLE " + TABLE_TIPO_USUARIO +
                " (" + COLUMN_ID_TIPO_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIPO_USUARIO + " TEXT NOT NULL);";
        db.execSQL(queryTipoUsuario);

        // Insertar registros en la tabla tipo_usuario
        ContentValues valuesAdmin = new ContentValues();
        valuesAdmin.put(COLUMN_TIPO_USUARIO, "Administrador");
        db.insert(TABLE_TIPO_USUARIO, null, valuesAdmin);

        ContentValues valuesUser = new ContentValues();
        valuesUser.put(COLUMN_TIPO_USUARIO, "Usuario");
        db.insert(TABLE_TIPO_USUARIO, null, valuesUser);

        // Crear la tabla evaluaciones
        String queryEvaluaciones = "CREATE TABLE " + TABLE_EVALUACIONES +
                " (" + COLUMN_ID_EVALUACION + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE_EVALUACION + " TEXT NOT NULL, " +
                COLUMN_DESCRIPCION_EVALUACION + " TEXT NOT NULL, " +
                COLUMN_IMG + " TEXT NOT NULL);";
        db.execSQL(queryEvaluaciones);

        // Crear la tabla preguntas_evaluacion
        String queryPreguntasEvaluacion = "CREATE TABLE " + TABLE_PREGUNTAS_EVALUACION +
                " (" + COLUMN_ID_PREGUNTA_PE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PREGUNTA + " TEXT NOT NULL, " +
                COLUMN_VALORACION + " REAL NOT NULL, " +
                COLUMN_ID_EVALUACION_PE + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_ID_EVALUACION_PE + ") REFERENCES " + TABLE_EVALUACIONES + "(" + COLUMN_ID_EVALUACION + "));";
        db.execSQL(queryPreguntasEvaluacion);

        // Crear la tabla personas
        String queryPersonas = "CREATE TABLE " + TABLE_PERSONAS +
                " (" + COLUMN_ID_PERSONA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE_PERSONA + " TEXT NOT NULL, " +
                COLUMN_APELLIDO_PERSONA + " TEXT NOT NULL, " +
                COLUMN_TELEFONO_PERSONA + " TEXT NOT NULL, " +
                COLUMN_CORREO_PERSONA + " TEXT NOT NULL, " +
                COLUMN_USUARIO_PERSONA + " TEXT NOT NULL, " +
                COLUMN_CONTRASEÑA_PERSONA + " TEXT NOT NULL, " +
                COLUMN_ID_TIPO_USUARIO_PERSONA + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_ID_TIPO_USUARIO_PERSONA + ") REFERENCES " + TABLE_TIPO_USUARIO + "(" + COLUMN_ID_TIPO_USUARIO + "));";
        db.execSQL(queryPersonas);

        // Crear la tabla pregunta_opciones
        String queryPreguntaOpciones = "CREATE TABLE " + TABLE_PREGUNTA_OPCIONES +
                " (" + COLUMN_ID_OPCION_PO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_OPCION + " TEXT NOT NULL, " +
                COLUMN_RESPUESTA_CORRECTA + " INTEGER NOT NULL, " +
                COLUMN_ID_PREGUNTA_PO + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_ID_PREGUNTA_PO + ") REFERENCES " + TABLE_PREGUNTAS_EVALUACION + "(" + COLUMN_ID_PREGUNTA_PE + "));";
        db.execSQL(queryPreguntaOpciones);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_RESPUESTAS_ESTUDIANTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPO_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUACIONES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREGUNTAS_EVALUACION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREGUNTA_OPCIONES);
        onCreate(db);
    }

}
