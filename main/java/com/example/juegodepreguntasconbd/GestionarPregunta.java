package com.example.juegodepreguntasconbd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GestionarPregunta extends AppCompatActivity {

    EditText etId, etEnunciadoEditable, etOpcion1Editable, etOpcion2Editable, etOpcion3Editable, etOpcion4Editable, etOpcionCorrectaEditable;
    Button btnEditar, btnEliminar;

    DBHelper helper = new DBHelper(this, "BD", null, 1);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_pregunta);
        conectar();
        etId.setEnabled(false);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            etId.setText(extras.getInt("id")+"");
            etEnunciadoEditable.setText(extras.getString("enunciado"));
            etOpcion1Editable.setText(extras.getString("opcion1"));
            etOpcion2Editable.setText(extras.getString("opcion2"));
            etOpcion3Editable.setText(extras.getString("opcion3"));
            etOpcion4Editable.setText(extras.getString("opcion4"));
            etOpcionCorrectaEditable.setText(extras.getInt("opcionCorrecta")+"");
        }

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerEliminar();
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerEditar();
            }
        });
    }

    private void listenerEditar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Integer id = Integer.parseInt(etId.getText().toString());
            String enunciado = etEnunciadoEditable.getText().toString();
            String opcion1 = etOpcion1Editable.getText().toString();
            String opcion2 = etOpcion2Editable.getText().toString();
            String opcion3 = etOpcion3Editable.getText().toString();
            String opcion4 = etOpcion4Editable.getText().toString();
            String opcionCorrecta = etOpcionCorrectaEditable.getText().toString();
            String query = String.format("UPDATE Preguntas SET enunciado = '%s', opcion1 = '%s', opcion2 = '%s', opcion3 = '%s', opcion4 = '%s', opcion_correcta = '%s' WHERE Id_pregunta = %s",
                    enunciado,
                    opcion1,
                    opcion2,
                    opcion3,
                    opcion4,
                    opcionCorrecta,
                    id
            );
            db.execSQL(query);
            db.close();
            Toast.makeText(this, "PREGUNTA ACTUALIZADA", Toast.LENGTH_SHORT).show();
            Intent intentGoAdministrador = new Intent(this, Administrador.class);
            startActivity(intentGoAdministrador);
        }catch (Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR" + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void listenerEliminar(){
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String query = String.format("DELETE FROM Preguntas WHERE Id_pregunta = %s", Integer.parseInt(etId.getText().toString()));
            db.execSQL(query);
            db.close();
            Toast.makeText(this, "PREGUNTA ELIMINADA", Toast.LENGTH_SHORT).show();
            Intent intentGoAdministrador = new Intent(this, Administrador.class);
            startActivity(intentGoAdministrador);
        }catch (Exception exception){
            Toast.makeText(this, "HA OCURRIDO UN ERROR: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void conectar(){
        etId = findViewById(R.id.etId);
        etEnunciadoEditable = findViewById(R.id.etEnunciadoEditable);
        etOpcion1Editable = findViewById(R.id.etOpcion1Editable);
        etOpcion2Editable = findViewById(R.id.etOpcion2Editable);
        etOpcion3Editable = findViewById(R.id.etOpcion3Editable);
        etOpcion4Editable = findViewById(R.id.etOpcion4Editable);
        etOpcionCorrectaEditable = findViewById(R.id.etOpcionCorrectaEditable);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
    }
}