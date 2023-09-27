package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class AgregarPregunta extends AppCompatActivity {

    EditText etEnunciado, etOpcion1, etOpcion2, etOpcion3, etOpcion4, etOpcionCorrecta;
    Button btnAgregarPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_pregunta);
        conectar();
        btnAgregarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarPregunta();
            }
        });
    }

    private void agregarPregunta(){
        String enunciado = etEnunciado.getText().toString();
        String opcion1 = etOpcion1.getText().toString();
        String opcion2 = etOpcion2.getText().toString();
        String opcion3 = etOpcion3.getText().toString();
        String opcion4 = etOpcion4.getText().toString();
        String opcionCorrecta = etOpcionCorrecta.getText().toString();

        DBHelper helper = new DBHelper(this, "BD", null, 1);
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("enunciado", enunciado);
            contentValues.put("opcion1", opcion1);
            contentValues.put("opcion2", opcion2);
            contentValues.put("opcion3", opcion3);
            contentValues.put("opcion4", opcion4);
            contentValues.put("opcion_correcta", opcionCorrecta);
            db.insert("Preguntas", null, contentValues);
            Toast.makeText(AgregarPregunta.this, "Pregunta agregada", Toast.LENGTH_SHORT).show();
            db.close();

    }catch (Exception exception){
            Toast.makeText(AgregarPregunta.this, "HA OCURRIDO UN ERROR: "+ exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void conectar() {
        etEnunciado = findViewById(R.id.etEnunciado);
        etOpcion1 = findViewById(R.id.etOpcion1);
        etOpcion2 = findViewById(R.id.etOpcion2);
        etOpcion3 = findViewById(R.id.etOpcion3);
        etOpcion4 = findViewById(R.id.etOpcion4);
        etOpcionCorrecta = findViewById(R.id.etOpcionCorrecta);
        btnAgregarPregunta = findViewById(R.id.btnAgregarPregunta);
    }
}