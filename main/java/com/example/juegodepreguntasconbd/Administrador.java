package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Administrador extends AppCompatActivity {

    Button btnIrAgregarPregunta;
    ListView lvPreguntas;
    ArrayList<Pregunta> preguntas = new ArrayList<>();
    ArrayList<Integer> idPreguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrador);
        conectar();
        irAgregarPreguntaActivity();
        clickListViewItem();
        DBHelper helper = new DBHelper(this, "BD", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            String obtener_preguntas = "SELECT * FROM Preguntas";
            Cursor cursor = db.rawQuery(obtener_preguntas, null);
            if(cursor.moveToFirst()){
                do {
                    idPreguntas.add(cursor.getInt(0));
                    String enunciado = cursor.getString(1);
                    String opcion1 = cursor.getString(2);
                    String opcion2 = cursor.getString(3);
                    String opcion3 = cursor.getString(4);
                    String opcion4 = cursor.getString(5);
                    String opcion_correcta = cursor.getString(6);
                    ArrayList<String> opciones = new ArrayList<>();
                    opciones.add(opcion1);
                    opciones.add(opcion2);
                    opciones.add(opcion3);
                    opciones.add(opcion4);
                    Pregunta pregunta = new Pregunta(enunciado, opciones, Integer.parseInt(opcion_correcta));
                    preguntas.add(pregunta);
                }while(cursor.moveToNext());
                btnIrAgregarPregunta.setEnabled(true);
                ArrayList<String> preguntasStr = new ArrayList<>();
                for (Pregunta pregunta : preguntas) {
                    preguntasStr.add(pregunta.toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, preguntasStr);
                lvPreguntas.setAdapter(adapter);
                db.close();
            }else{
                btnIrAgregarPregunta.setEnabled(false);
            }
        }catch(Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void irAgregarPreguntaActivity(){
        btnIrAgregarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAgregarPregunta = new Intent(getApplicationContext(), AgregarPregunta.class);
                startActivity(intentAgregarPregunta);
            }
        });
    }

    private void clickListViewItem(){
        lvPreguntas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pregunta preguntaSeleccionada = preguntas.get(i);
                Intent intentGoToGestionarPregunta = new Intent(getApplicationContext(), GestionarPregunta.class);
                intentGoToGestionarPregunta.putExtra("id", idPreguntas.get(i));
                intentGoToGestionarPregunta.putExtra("enunciado", preguntaSeleccionada.getPregunta());
                intentGoToGestionarPregunta.putExtra("opcion1", preguntaSeleccionada.getOpciones().get(0));
                intentGoToGestionarPregunta.putExtra("opcion2", preguntaSeleccionada.getOpciones().get(1));
                intentGoToGestionarPregunta.putExtra("opcion3", preguntaSeleccionada.getOpciones().get(2));
                intentGoToGestionarPregunta.putExtra("opcion4", preguntaSeleccionada.getOpciones().get(3));
                intentGoToGestionarPregunta.putExtra("opcionCorrecta", preguntaSeleccionada.getRespuestaCorrecta());
                startActivity(intentGoToGestionarPregunta);
            }
        });
    }

    private void conectar() {
        btnIrAgregarPregunta = findViewById(R.id.btnIrAgregarPregunta);
        lvPreguntas = findViewById(R.id.lvPreguntas);
    }
}