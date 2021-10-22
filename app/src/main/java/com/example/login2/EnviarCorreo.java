package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AsyncNotedAppOp;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import javax.mail.Session;


import java.util.Date;
import java.util.Properties;

public class EnviarCorreo extends AppCompatActivity {

    TextView sendEmail, botonEnviar, reenviar;
    String titulo, body, rec;
    Cursor fila;

    Context context = null;
    ProgressDialog pdialog;


    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_correo);


        sendEmail = (TextView) findViewById(R.id.enviarCorreo);
        botonEnviar = (TextView) findViewById(R.id.crearUser);
        reenviar = (TextView) findViewById(R.id.reenviar);

        //POPUP - DISEÑO VENTANA CREAR NUEVO USUARIO
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto * 0.5));
        //FIN DE DISEÑO VENTANA POPUP



        botonEnviar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (sendEmail.getText().toString().isEmpty()) {
                            Toast toast1 = Toast.makeText(getApplicationContext(), "Escriba un correo electronico", Toast.LENGTH_SHORT);
                            toast1.show();
                            return;
                        } else {
                            //validarCorreo();
                            validarCorreo();
                        }
                    }
                });


        reenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarCorreo();
            }
        });


    }

    public void validarCorreo(){
        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String valCorreo = sendEmail.getText().toString();
        fila = db.rawQuery("SELECT * FROM user WHERE correo = '" + valCorreo + "'", null);

        if(fila.moveToFirst()){
            String valorEmail = fila.getString(1);
            String valorContraseña = fila.getString(2);
            if(valorEmail.equals(valCorreo)){

                //recuperarContraseña();
                Intent intent = new Intent(EnviarCorreo.this, LoginActivity.class);
                startActivity(intent);
                finish();

                Toast toast1 = Toast.makeText(getApplicationContext(), "La contraseña es: " + valorContraseña.toString(), Toast.LENGTH_SHORT);
                toast1.show();

            }
        }else{
            Toast toast1 = Toast.makeText(getApplicationContext(), "Usuario invalido", Toast.LENGTH_SHORT);
            toast1.show();
        }

        db.close();
        sendEmail.setText("");
    }

    //ENVIAR LA CONTRASEÑA CON AYUDA DEL USUARIO
    /*protected void sendEmail() {

        String usuarioCorreo = sendEmail.getText().toString();

        Log.e("Test email:", "enviando email");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, usuarioCorreo);
        //emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recuperar contraseña");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Este es un mensaje de prueba para recuperar la contraseña");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.e("Test email:", "Fin envio email");

        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(EnviarCorreo.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }*/
    //FIN

};
