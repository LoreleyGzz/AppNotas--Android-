package com.example.login2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

public class CrearUsuario extends AppCompatActivity {


    EditText correoVal, pass, valPass;
    TextView crear;
    SQLiteDatabase db;

    Cursor fila;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        correoVal = (EditText) findViewById(R.id.correoVal);
        pass = (EditText) findViewById(R.id.pass);
        valPass = (EditText) findViewById(R.id.passVal);

        //POPUP - DISEÑO VENTANA CREAR NUEVO USUARIO
        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);
        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;
        getWindow().setLayout((int)(ancho * 0.85), (int)(alto * 0.6));
        //FIN DE DISEÑO VENTANA POPUP

        crear = (TextView) findViewById(R.id.crearUser);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(correoVal.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || valPass.getText().toString().isEmpty()) {
                    //volver a la pantalla anterior
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Faltan campos por llenar", Toast.LENGTH_SHORT);
                    toast1.show();
                    return;
                }else{

                    if(pass.getText().toString().equals(valPass.getText().toString())){

                        //crearUsuario();
                        validarCorreo();

                        //volver a la pantalla anterior
                        Intent intent = new Intent(CrearUsuario.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else{

                        Toast toast1 = Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT);
                        toast1.show();
                        return;

                    }
                }
            }
        });


    }


    private void crearUsuario(){

        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String correo = correoVal.getText().toString();
        String contra = pass.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("correo", correo);
        registro.put("contraseña", contra);
        db.insert("user", null, registro);
        db.close();

        correoVal.setText("");
        pass.setText("");
        valPass.setText("");

        Toast toast1 = Toast.makeText(getApplicationContext(), "¡Usuario creado correctamente!", Toast.LENGTH_SHORT);
        toast1.show();

    }

    public void validarCorreo(){
        AdminSQLiteOpenHelper conn = new AdminSQLiteOpenHelper(getApplicationContext(), "DBNOTAS", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String valCorreo = correoVal.getText().toString();
        fila = db.rawQuery("SELECT * FROM user WHERE correo = '" + valCorreo + "'", null);

        if(fila.moveToFirst()){
            String valorEmail = fila.getString(1);
            if(valorEmail.equals(valCorreo)){

                //recuperarContraseña();
                Toast toast1 = Toast.makeText(getApplicationContext(), "El correo " + valorEmail + " ya existe", Toast.LENGTH_SHORT);
                toast1.show();

            }
        }else{
            crearUsuario();
        }

        db.close();
    }
}