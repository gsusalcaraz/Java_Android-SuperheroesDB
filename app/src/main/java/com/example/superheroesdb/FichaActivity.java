package com.example.superheroesdb;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.superheroesdb.models.Constantes;
import com.example.superheroesdb.models.Superheroe;
import com.example.superheroesdb.models.SuperheroesDb;

public class FichaActivity extends AppCompatActivity {

    EditText nombre;
    EditText nombreReal;
    ImageView imageView;

    String accion;
    int id;

    private int imagenId = R.drawable.avengers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha);

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        accion = bundle.getString(Constantes.ACCION);
        id = bundle.getInt(Constantes.ID);

        nombre = findViewById(R.id.etNombre);
        nombreReal = findViewById(R.id.etNombreReal);
        Button btnGrabar = findViewById(R.id.btnGrabar);
        imageView = findViewById(R.id.imageView);

        btnGrabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar campos
                if (validarCampos()){
                    grabar(accion, id);
                }

            }
        });
        leerContacto(accion, id);

    }

    private void leerContacto(String accion, int id) {

        setTitle(R.string.crearregistro);

        if (accion.equals(Constantes.EDITAR)) {
            setTitle(R.string.editarregistro);

            SuperheroesDb datos = new SuperheroesDb(this);
            Superheroe item = datos.cargar(id);
            if (item != null) {
                nombre.setText(item.getNombre());
                nombreReal.setText(item.getNombreReal());
                imagenId = Integer.valueOf(item.getImagenId());

                imageView.setImageResource(imagenId);

            }
        }
    }
    private void grabar(String accion, int id) {

        long resultado;
        Superheroe item = new Superheroe();
        item.setId(id);
        item.setNombre(nombre.getText().toString());
        item.setNombreReal(nombreReal.getText().toString());

        item.setImagenId(String.valueOf(imagenId));

        SuperheroesDb datos = new SuperheroesDb(this);

        if (accion.equals(Constantes.NUEVO))
            resultado = datos.insertar(item);
        else
            resultado = datos.actualizar(item, id);

        if (resultado > 0)
        {
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    /**
     * Función que valida los campos del formulario
     * @return
     */
    private Boolean validarCampos() {
        if ("".equals(nombre.getText().toString())) {
            nombre.setError("Escribe el nombre del superheroe");
            nombre.requestFocus();
            return false;
        }
        if ("".equals(nombreReal.getText().toString())) {
            nombreReal.setError("Escribe el nombre real del superheroe");
            nombreReal.requestFocus();
            return false;
        }
        return true;
    }
}