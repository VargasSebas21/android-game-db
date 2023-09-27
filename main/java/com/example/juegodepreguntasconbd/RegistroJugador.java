package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroJugador extends AppCompatActivity {

    EditText etNickName;
    Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_jugador);
        etNickName = findViewById(R.id.etNickName);
        btnContinuar = findViewById(R.id.btnContinuar);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nickName = etNickName.getText().toString();
                if(nickName.equals("")){
                    Toast.makeText(RegistroJugador.this, "Por favor ingrese un nombre antes de continuar", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intentJugador = new Intent(getApplicationContext(), Juego.class);
                    intentJugador.putExtra("nickname", nickName);
                    startActivity(intentJugador);
                }
            }
        });
    }
}