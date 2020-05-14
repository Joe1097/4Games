package com.example.pantallainicio.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ConexionSQLiteHelper extends SQLiteOpenHelper {


    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Genera las tablas o scripts de las entidades
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_JUGADOR);

    }

    //cada que se ejecuta la app verifica si ya existe la bd antes y no crea una nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int VersionAntigua, int VersionNueva) {

        db.execSQL("DROP TABLE IF EXISTS jugador");
        onCreate(db);
    }
}
