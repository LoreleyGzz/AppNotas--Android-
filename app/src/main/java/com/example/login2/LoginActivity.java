package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    EditText email, pass;
    TextView recovery;
    private Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.valUser);
        pass = (EditText) findViewById(R.id.valPass);


        //ENTRAR A LA APLICACION - INICIO DE SESION
        Button ingresar = (Button) findViewById(R.id.inicioSesion);
        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validarUsuario();

            }
        });

        //CREACION DE NUEVO USUARIO
        TextView crearUsuario = (TextView) findViewById(R.id.nuevoUsuario);
        crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CrearUsuario.class);
                startActivity(intent);
            }
        });

        //RECUPERAR CONTRASEÑA
        recovery = (TextView) findViewById(R.id.olvidasteContraseña);
        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, EnviarCorreo.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){

            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    public void validarUsuario(){

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String valCorreo = email.getText().toString();
        String valPass= pass.getText().toString();
        fila = db.rawQuery("SELECT * FROM user WHERE correo = '" + valCorreo + "' and contraseña = '" + valPass + "'", null);

        if(fila.moveToFirst()){
            String valorEmail = fila.getString(1);
            String valorContraseña = fila.getString(2);
            if(valorEmail.equals(valCorreo) && valorContraseña.equals(valPass)){
                Intent intent = new Intent(LoginActivity.this, Notas.class);
                startActivity(intent);
                finish();
            }
        }else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_SHORT);
            toast1.show();
        }

        db.close();
        pass.setText("");

    }

}