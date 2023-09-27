package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnIngresarComoAdmin, btnIngresarComoJugador, btnVerRankings;
    TextView tvTitle;
    ArrayList<Pregunta> preguntas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        conectar();
        agregarPreguntasIniciales();

        btnIngresarComoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdminActivity = new Intent(getApplicationContext(), Administrador.class);
                startActivity(intentAdminActivity);
            }
        });

        btnIngresarComoJugador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentJugadorActivity = new Intent(getApplicationContext(), RegistroJugador.class);
                startActivity(intentJugadorActivity);
            }
        });

        btnVerRankings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRankingActivity = new Intent(getApplicationContext(), Ranking.class);
                startActivity(intentRankingActivity);
            }
        });
    }

    private void agregarPreguntasIniciales(){
        DBHelper helper = new DBHelper(this, "BD", null, 1);
        SQLiteDatabase db = helper.getReadableDatabase();
        try {
            String obtener_preguntas = "SELECT * FROM Preguntas";
            Cursor cursor = db.rawQuery(obtener_preguntas, null);
            if(!cursor.moveToFirst()){
                generarPreguntas();
                SQLiteDatabase dbWritable = helper.getWritableDatabase();
                for (Pregunta pregunta: preguntas) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("enunciado", pregunta.getPregunta());
                    contentValues.put("opcion1", pregunta.getOpciones().get(0));
                    contentValues.put("opcion2", pregunta.getOpciones().get(1));
                    contentValues.put("opcion3", pregunta.getOpciones().get(2));
                    contentValues.put("opcion4", pregunta.getOpciones().get(3));
                    contentValues.put("opcion_correcta", pregunta.getRespuestaCorrecta().toString());
                    db.insert("Preguntas", null, contentValues);
                }
                dbWritable.close();
                db.close();
            }

        }catch (Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR"+exception.getMessage(), Toast.LENGTH_SHORT).show();
            tvTitle.setText(exception.getMessage());
        }
    }

    private void cosntruirPregunta(String enunciadoPregunta, String opcion1, String opcion2, String opcion3, String opcion4, Integer respuestaCorrecta){
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add(opcion1);
        opciones.add(opcion2);
        opciones.add(opcion3);
        opciones.add(opcion4);
        Pregunta pregunta = new Pregunta(enunciadoPregunta, opciones, respuestaCorrecta);
        preguntas.add(pregunta);
    }


    private void generarPreguntas(){
        cosntruirPregunta("¿Cuántos jugadores por equipo participan en un partido de voleibol?",
                "6",
                "2",
                "4",
                "5",
                1);

        cosntruirPregunta("¿Cuál es la altura de la red de voleibol en los juegos masculino y femenino?",
                "2,5 m y 2,0 m",
                "2,43 m y 2,24 m",
                "1,8 m y 1,5 m",
                "2,4 m para ambos",
                2);

        cosntruirPregunta("¿Cuántos jugadores pueden participar por equipo en el basquetbol?",
                "2",
                "3",
                "5",
                "4",
                3);

        cosntruirPregunta("¿Cuántos jugadores pueden participar por equipo en el futbol?",
                "10",
                "12",
                "9",
                "11",
                4);

        cosntruirPregunta("¿Que deporte practica Cristiano Ronaldo?",
                "futbol",
                "basquetbol",
                "voleibol",
                "ciclismo",
                1);

        cosntruirPregunta("¿En qué periodo de la prehistoria fue descubierto el fuego?",
                "Neolítico",
                "Paleolítico",
                "Edad de los metales",
                "Edad de piedra",
                2);

        cosntruirPregunta("¿Qué líder mundial quedó conocida como 'La Dama de Hierro'?",
                "Margaret Thatcher",
                "Hillary Clinton",
                "Angela Merkel",
                "Dilma Rouseff",
                1);

        cosntruirPregunta("¿Qué es el Acuerdo de París?",
                "El acuerdo entre países europeos acerca de la inmigración.",
                "El acuerdo entre Francia, EE.UU y Canadá para acusar a China del calentamiento global.",
                "El acuerdo de cooperación financiera internacional entre las tres mayores potencias mundiales.",
                "El acuerdo entre varios países acerca de las consecuencias del calentamiento global.",
                4);

        cosntruirPregunta("¿Qué es la Triple Alianza?",
                "El acuerdo económico entre México, EE.UU y Canadá.",
                "El anillo que se intercambian los novios cuando contraen matrimonio.",
                "El acuerdo entre Alemania, el imperio Austro Húngaro e Italia para apoyarse en caso de guerra.",
                "El acuerdo económico entre Inglaterra, Francia y Rusia.",
                3);

        cosntruirPregunta("¿Cuál es la religión monoteísta que cuenta con el mayor número de adeptos en el mundo?",
                "Cristianismo",
                "Zoroastrismo",
                "Judaísmo",
                "Hinduismo",
                1);

        cosntruirPregunta("¿Quién pintó la obra 'Guernica'?",
                "Pablo Picasso",
                "Salvador Dalí",
                "Frida Kahlo",
                "Paul Cézanne",
                1);

        cosntruirPregunta("¿Por qué película recibió un premio Oscar Leonardo DiCaprio?",
                "The Revenant (2015)",
                "Diamantes de sangre (2006)",
                "Titanic (1997)",
                "El Lobo de Wall Street (2013)",
                1);

        cosntruirPregunta("¿Cuál de estas películas se basó en el plebiscito chileno de 1988?",
                "NO",
                "Diarios de motocicleta",
                "Pinochet in Suburbia",
                "Enterrados vivos",
                1);

        cosntruirPregunta("¿Cuál artista es conocido como uno de los exponentes máximos del ready-made (objeto-encontrado)?",
                "Marcel Duchamp",
                "Van Gogh",
                "Leonardo de Vinci",
                "Salvador Dalí",
                1);

        cosntruirPregunta("¿Quién pintó la bóveda de la capilla sixtina?",
                "Miguel Angel",
                "Boticelli",
                "Donatello",
                "Leonardo da Vinci",
                1);

        cosntruirPregunta("¿Cuál es el país más grande y el más pequeño del mundo?",
                "Canadá y Mónaco",
                "Rusia y Vaticano",
                "China y Nauru",
                "India y San Marino",
                2);

        cosntruirPregunta("¿Cuál es la montaña más alta del continente americano?",
                "Pico Neblina",
                "Aconcagua",
                "Pico Bolívar",
                "Monte Everest",
                2);

        cosntruirPregunta("¿En qué país de África el español es la lengua oficial?",
                "Cabo Verde",
                "Guinea ecuatorial",
                "Liberia",
                "Costa de Marfil",
                2);

        cosntruirPregunta("¿Cuáles de estas construcciones famosas quedan en los Estados Unidos?",
                "Dancing House, 30 st Mary Axe, La Casa Blanca",
                "Estatua de la Libertad, puente Golden Gate, Empire State Building",
                "La ciudad prohibida, la Sagrada Familia, el Panteón",
                "Lincoln Memorial, Sydney Opera House, Burj Khalifa",
                2);

        cosntruirPregunta("¿Cuál de estos países se extiende entre dos continentes?",
                "Tanzania",
                "Rusia",
                "Groenlandia",
                "Egipto",
                2);
    }


    private void conectar() {
        btnIngresarComoAdmin = findViewById(R.id.btnIngresarComoAdmin);
        btnIngresarComoJugador = findViewById(R.id.btnIngresarComoJugador);
        btnVerRankings = findViewById(R.id.btnVerRankings);
        tvTitle = findViewById(R.id.tvTitle);
    }
}