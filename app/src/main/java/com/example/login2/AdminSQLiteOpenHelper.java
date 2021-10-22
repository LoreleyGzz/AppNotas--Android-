package com.example.login2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    //***NOTA: Si importan las mayusculas y minusculas

    //Nombre de la tabla
    public static final String TABLE = "notas";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_TITULO = "titulo";
    public static final String CAMPO_DESC = "descripcion";

    public static final String CAMPO_CORREO = "correo";
    public static final String CAMPO_PASS = "contraseña";

    // información del a base de datos
    static final String DB_NAME = "DBNOTAS";

    static final int DB_VERSION = 1;

    private AdminSQLiteOpenHelper dbhelper;
    private SQLiteDatabase db;

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS user (_id INTEGER PRIMARY KEY AUTOINCREMENT, correo varchar(100) not null, contraseña varchar (100) not null)");
        db.execSQL("CREATE TABLE IF NOT EXISTS notas (_id INTEGER PRIMARY KEY AUTOINCREMENT, titulo varchar(100) not null, descripcion varchar (4096) not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
