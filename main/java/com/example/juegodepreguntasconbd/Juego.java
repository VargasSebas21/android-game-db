package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14,
            btn15, btn16, btn17, btn18, btn19, btn20, btnOpcion1, btnOpcion2, btnOpcion3, btnOpcion4, btnTerminarJuego;

    Button btnPreguntaActual;
    TextView tvPregunta, tvPuntaje, tvNickName;
    Pregunta preguntaEscogida;
    Random random = new Random();
    List<Pregunta> preguntas = new ArrayList<>();
    ArrayList<Button> botonesRespondidos = new ArrayList<>();
    Integer puntaje = 0;
    Integer contadorJugadas = 0;
    DBHelper helper = new DBHelper(this, "BD", null, 1);
    Integer tamañoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);
        conectar();
        llenarListaPreguntas();

        enableButtons(false);

        createOnClickListenerPreguntas(btn1);
        createOnClickListenerPreguntas(btn2);
        createOnClickListenerPreguntas(btn3);
        createOnClickListenerPreguntas(btn4);
        createOnClickListenerPreguntas(btn5);
        createOnClickListenerPreguntas(btn6);
        createOnClickListenerPreguntas(btn7);
        createOnClickListenerPreguntas(btn8);
        createOnClickListenerPreguntas(btn9);
        createOnClickListenerPreguntas(btn10);
        createOnClickListenerPreguntas(btn11);
        createOnClickListenerPreguntas(btn12);
        createOnClickListenerPreguntas(btn13);
        createOnClickListenerPreguntas(btn14);
        createOnClickListenerPreguntas(btn15);
        createOnClickListenerPreguntas(btn16);
        createOnClickListenerPreguntas(btn17);
        createOnClickListenerPreguntas(btn18);
        createOnClickListenerPreguntas(btn19);
        createOnClickListenerPreguntas(btn20);

        createOnClickListenerRespuestas(btnOpcion1);
        createOnClickListenerRespuestas(btnOpcion2);
        createOnClickListenerRespuestas(btnOpcion3);
        createOnClickListenerRespuestas(btnOpcion4);

        Bundle info = getIntent().getExtras();

        String nickName = info.getString("nickname");
        tvNickName.setText("Jugando: " + nickName);


        Context context = this;
        SharedPreferences sharedPreferences = getSharedPreferences("RankingJugadores", context.MODE_PRIVATE);

        btnTerminarJuego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(nickName, puntaje).apply();
                Intent intentRanking = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intentRanking);
            }
        });
    }

    private void llenarListaPreguntas(){
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            String obtener_preguntas = "SELECT * FROM Preguntas";
            Cursor cursor = db.rawQuery(obtener_preguntas, null);
            if (cursor.moveToFirst()) {
                do {
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
                } while (cursor.moveToNext());
            }
            db.close();
            tamañoInicial = preguntas.size();
        }catch (Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void createOnClickListenerPreguntas(Button btn) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pregunta pregunta = cogerPreguntaAleatoria();
                preguntaEscogida = pregunta;
                btnPreguntaActual = btn;
                tvPregunta.setText(pregunta.getPregunta());
                enableButtons(true);
                btnOpcion1.setText(pregunta.getOpciones().get(0));
                btnOpcion2.setText(pregunta.getOpciones().get(1));
                btnOpcion3.setText(pregunta.getOpciones().get(2));
                btnOpcion4.setText(pregunta.getOpciones().get(3));
            }
        });
    }


    private void createOnClickListenerRespuestas(Button btn){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String respuestaEscogida = btn.getText().toString();
                Boolean respondioCorrectamente = responderPregunta(respuestaEscogida);
                if(respondioCorrectamente){
                    btnPreguntaActual.setBackgroundColor(Color.GREEN);
                    btnPreguntaActual.setEnabled(false);
                    botonesRespondidos.add(btnPreguntaActual);
                }
                tvPuntaje.setText("Puntaje actual: " + puntaje);
            }
        });
    }

    private Pregunta cogerPreguntaAleatoria(){
        Integer index = random.nextInt(preguntas.size());
        Pregunta pregunta = preguntas.get(index);
        return pregunta;
    }

    private void enableButtons(Boolean instruccion){
        btnOpcion1.setEnabled(instruccion);
        btnOpcion2.setEnabled(instruccion);
        btnOpcion3.setEnabled(instruccion);
        btnOpcion4.setEnabled(instruccion);
    }

    private Boolean responderPregunta(String respuestaEscogida){
        Boolean respuestaEsCorrecta = preguntaEscogida.verificarRespuesta(respuestaEscogida);
        if(respuestaEsCorrecta){
            contadorJugadas ++;
            preguntas.remove(preguntaEscogida);
            Toast.makeText(this, "Respuesta correcta", Toast.LENGTH_SHORT).show();
            enableButtons(false);
            puntaje += 50;
            if(contadorJugadas == tamañoInicial){
                Toast.makeText(Juego.this, "Has terminado el juego, felicidades", Toast.LENGTH_SHORT).show();
                tvPuntaje.setText("Felicidades tu puntaje es: " + puntaje);
                btnTerminarJuego.setVisibility(View.VISIBLE);
                btnTerminarJuego.setEnabled(true);
                SQLiteDatabase db = helper.getWritableDatabase();
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("nombre_jugador", tvNickName.getText().toString());
                    contentValues.put("puntaje", puntaje);
                    db.insert("Ranking", null, contentValues);
                    db.close();
                    Intent intentGoRanking = new Intent(this, Ranking.class);
                    startActivity(intentGoRanking);
                }catch (Exception exception){
                    Toast.makeText(this, "HA OCURRIDO UN ERROR" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        }
        puntaje -= 10;
        Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show();
        reconfigurarJuego();
        return false;

    }

    private void reconfigurarJuego(){
        btnOpcion1.setText("A.");
        btnOpcion2.setText("B.");
        btnOpcion3.setText("C.");
        btnOpcion4.setText("D.");
        tvPregunta.setHint("Pregunta");
        for (Button boton : botonesRespondidos) {
            boton.setBackgroundColor(Color.parseColor("#5F6F94"));
            boton.setEnabled(true);
        }
        preguntas.clear();
        llenarListaPreguntas();
        contadorJugadas = 0;
    }

    private void conectar() {
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        btn17 = findViewById(R.id.btn17);
        btn18 = findViewById(R.id.btn18);
        btn19 = findViewById(R.id.btn19);
        btn20 = findViewById(R.id.btn20);
        btnOpcion1 = findViewById(R.id.btnOcion1);
        btnOpcion2 = findViewById(R.id.btnOcion2);
        btnOpcion3 = findViewById(R.id.btnOcion3);
        btnOpcion4 = findViewById(R.id.btnOcion4);
        btnTerminarJuego = findViewById(R.id.btnTerminarJuego);

        tvPregunta = findViewById(R.id.tvPregunta);
        tvPuntaje = findViewById(R.id.tvPuntaje);
        tvNickName = findViewById(R.id.tvNickName);
    }


}