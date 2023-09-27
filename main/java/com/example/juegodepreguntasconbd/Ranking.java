package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Ranking extends AppCompatActivity {

    ListView lvRanking;
    DBHelper helper = new DBHelper(this, "BD", null, 1);
    ArrayList<RankingJugador> rankings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        lvRanking = findViewById(R.id.lvRanking);
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            String obtener_preguntas = "SELECT * FROM Ranking ORDER BY puntaje DESC";
            Cursor cursor = db.rawQuery(obtener_preguntas, null);
            if(cursor.moveToFirst()){
                do {
                    String nombre_jugador = cursor.getString(1);
                    Integer puntaje = cursor.getInt(2);
                    RankingJugador rankingJugador = new RankingJugador(nombre_jugador, puntaje);
                    rankings.add(rankingJugador);
                }while(cursor.moveToNext());
                ArrayList<String> rankingsStr = new ArrayList<>();
                for (RankingJugador rankingJugador : rankings) {
                    rankingsStr.add(rankingJugador.toString());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, rankingsStr);
                lvRanking.setAdapter(adapter);
                db.close();
            }
        }catch(Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR"+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}