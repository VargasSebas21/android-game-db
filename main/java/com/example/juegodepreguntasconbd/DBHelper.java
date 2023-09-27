package com.example.juegodepreguntasconbd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    String Crear_tabla_preguntas = "CREATE TABLE Preguntas(Id_pregunta INTEGER PRIMARY KEY AUTOINCREMENT," +
            "enunciado TEXT, opcion1 text, opcion2 TEXT, opcion3 TEXT, opcion4 TEXT, opcion_correcta TEXT)";

    String Crear_tabla_ranking = "CREATE TABLE Ranking(Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombre_jugador TEXT, puntaje INTEGER)";

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Crear_tabla_preguntas);
        db.execSQL(Crear_tabla_ranking);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE Preguntas");
        db.execSQL(Crear_tabla_preguntas);
        db.execSQL("DROP TABLE Ranking");
        db.execSQL(Crear_tabla_ranking);
    }
}
