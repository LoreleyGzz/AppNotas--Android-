package com.example.login2;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.TooManyListenersException;

public class Notas extends AppCompatActivity {

    TextView conteoNotas;

    //variables de la lista
    ListView listaViewNotas;
    ArrayList<String> listaInformacion;
    ArrayList<ObjNotas> listaNotas; //ListaUsuario


    SQLiteDatabase sql;
    AdminSQLiteOpenHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas);

        //ArrayList<ObjNotas> lista = new ArrayList<ObjNotas>();

        //Declaraciones
        TextView agregar = (TextView) findViewById(R.id.agregarNota);
        listaViewNotas = (ListView) findViewById(R.id.notasLista);
        conteoNotas = (TextView) findViewById(R.id.cantidadNotas);


        //AQUI EMPIEZA TODA LA RELACION CON EL LISTVIEW
        consultarListaNotas();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listaViewNotas.setAdapter(adaptador);

        //CANTIDAD DE NOTAS QUE EXISTEN DENTRO DEL LISTVIEW
        int elem =adaptador.getCount();
        conteoNotas.setText(String.valueOf(elem) + " notas guardadas");
        adaptador.notifyDataSetChanged();


        listaViewNotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ObjNotas objNotas = listaNotas.get(position);
                Intent intent = new Intent(Notas.this, EditarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("objNotas", objNotas);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        listaViewNotas.setLongClickable(true);
        listaViewNotas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final int posicion=position;
                ObjNotas objNotas = listaNotas.get(position);

                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Notas.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿ Eliminar esta nota ?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {

                        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
                        SQLiteDatabase db = conn.getWritableDatabase();
                        db.execSQL("delete from notas where _id = '" + objNotas.getId().toString() + "'");

                        listaViewNotas.invalidateViews();
                        adaptador.notifyDataSetChanged();
                        finish();
                        startActivity(getIntent());
                        return;

                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                    }
                });
                dialogo1.show();
                adaptador.notifyDataSetChanged();
                return true;
            }
        });

        //AQUI TERMINA TODA RELACION CON EL LIST VIEW

        agregar.setOnClickListener(view -> {
            Intent intent = new Intent(Notas.this, AgregarNota.class);
            startActivity(intent);
            finish();
        });


    }

    private void consultarListaNotas() {

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        ObjNotas objNotas = null;
        listaNotas =new ArrayList<ObjNotas>();
        Cursor cursor = db.rawQuery("Select * from notas", null);

        while (cursor.moveToNext()){
            objNotas = new ObjNotas();
            objNotas.setId(cursor.getInt(0));
            objNotas.setTitulo(cursor.getString(1));
            objNotas.setDesc(cursor.getString(2));

            listaNotas.add(objNotas);
        }

        obtenerLista();
    }

    private void obtenerLista() {

        listaInformacion = new ArrayList<String>();

        for(int i = 0; i < listaNotas.size(); i++){

            if(listaNotas.get(i).getTitulo().isEmpty()){
                if(listaNotas.get(i).getDesc().length() >=30){
                    listaInformacion.add(listaNotas.get(i).getDesc().substring(0, 30) +"...");
                }else{
                    listaInformacion.add(listaNotas.get(i).getDesc());
                }
            }else{
                if(listaNotas.get(i).getTitulo().length() >=30){
                    listaInformacion.add(listaNotas.get(i).getTitulo().substring(0, 30) +"...");
                }else{
                    listaInformacion.add(listaNotas.get(i).getTitulo());
                }
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode== KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(Notas.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

}