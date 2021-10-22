package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.Toast;
import android.text.util.Linkify;

public class AgregarNota extends AppCompatActivity {

    EditText campoTitulo, campoDescripcion;

    private ObjNotas Item;

    SQLiteDatabase sql;
    AdminSQLiteOpenHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_nota);

        campoTitulo = (EditText) findViewById(R.id.titulo);
        campoDescripcion = (EditText) findViewById(R.id.descripcion);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            if(campoTitulo.getText().toString().isEmpty() && campoDescripcion.getText().toString().isEmpty()) {
                //volver a la pantalla anterior
                Intent intent = new Intent(AgregarNota.this, Notas.class);
                startActivity(intent);
                finish();
            }else{
                guardarNota();

                //volver a la pantalla anterior
                Intent intent = new Intent(AgregarNota.this, Notas.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    private void guardarNota(){

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String titulo = campoTitulo.getText().toString();
        String descripcion = campoDescripcion.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("titulo", titulo);
        registro.put("descripcion", descripcion);
        db.insert("notas", null, registro);
        db.close();
        campoTitulo.setText("");
        campoDescripcion.setText("");

        Toast toast1 = Toast.makeText(getApplicationContext(), "Guardado", Toast.LENGTH_SHORT);
        toast1.show();

    }
}