package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.util.Linkify;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class EditarActivity extends AppCompatActivity {

    TextView editarTitulo, editarDesc, editarID;

    private ObjNotas Item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editarDesc = (TextView) findViewById(R.id.descripcion);
        editarTitulo = (TextView) findViewById(R.id.titulo);
        editarID = (TextView) findViewById(R.id.textID);

        editarDesc.setClickable(true);
        editarDesc.setMovementMethod(LinkMovementMethod.getInstance());

        Bundle objetoEnviado=getIntent().getExtras();
        ObjNotas objNotas = null;

        if(objetoEnviado!=null){
            objNotas=(ObjNotas) objetoEnviado.getSerializable("objNotas");
            editarTitulo.setText(objNotas.getTitulo().toString());
            editarDesc.setText(objNotas.getDesc().toString());
            editarID.setText(objNotas.getId().toString());
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            if(editarTitulo.getText().toString().isEmpty() && editarDesc.getText().toString().isEmpty()) {
                //volver a la pantalla anterior

                eliminarNota();
                Intent intent = new Intent(EditarActivity.this, Notas.class);
                startActivity(intent);
                finish();

                Toast toast1 = Toast.makeText(getApplicationContext(), "No hay nada que guardar, nota descartada", Toast.LENGTH_SHORT);
                toast1.show();
            }else{
                actualizarNota();

                //volver a la pantalla anterior
                Intent intent = new Intent(EditarActivity.this, Notas.class);
                startActivity(intent);
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void eliminarNota() {
        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.execSQL("delete from notas where _id = '" + editarID.getText() + "'");

    }

    public void actualizarNota(){

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        db.execSQL("update notas set titulo = '" + editarTitulo.getText().toString() + "', " +
                "descripcion = '" + editarDesc.getText().toString() + "' where _id = " + editarID.getText());
    }

}