package com.example.superheroesdb;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;

import com.example.superheroesdb.adapters.SuperheroesAdapter;
import com.example.superheroesdb.models.Constantes;
import com.example.superheroesdb.models.Superheroe;
import com.example.superheroesdb.models.SuperheroesDb;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SuperheroesAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager lmg = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lmg);

        cargarLista();

        FloatingActionButton fab = findViewById(R.id.flotante);
        fab.setOnClickListener(view -> nuevoRegistro());

    }

    private void cargarLista() {
        SuperheroesDb datos = new SuperheroesDb(this);
        datos.cargar();

        SuperheroesAdapter.OnItemClickListener listener = new SuperheroesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Superheroe item) {
                editarRegistro(item.getId());
            }

            @Override
            public void onItemLongClick(Superheroe item, int position) {
                /* Pido nombre para mensaje de confirmación borrar
                String nombre = item.getNombre();
                int pos = datos.getLista().indexOf(item);
                eliminarRegistro(item.getId(), pos, nombre);
                cargarLista();*/
                // Elimino directamente
                eliminarRegistro(item.getId(), position);

            }
        };

        myAdapter = new SuperheroesAdapter(datos.getLista(), listener);
        recyclerView.setAdapter(myAdapter);
    }

    private void nuevoRegistro() {
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.ACCION, Constantes.NUEVO);
        bundle.putInt(Constantes.ID, -1);

        Intent intent = new Intent(getApplicationContext(), FichaActivity.class);

        intent.putExtras(bundle);
        lanzador.launch(intent);
    }

    private void editarRegistro(int id) {
        Bundle bundle = new Bundle();
        bundle.putString(Constantes.ACCION, Constantes.EDITAR);
        bundle.putInt(Constantes.ID, id);

        Intent intent = new Intent(getApplicationContext(), FichaActivity.class);

        intent.putExtras(bundle);
        lanzador.launch(intent);
    }

    ActivityResultLauncher<Intent> lanzador = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //Si todo ha ido OK --> RESULT_OK
                if (result.getResultCode() == RESULT_OK) {
                    cargarLista();
                }
            });


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_importar:
                crearRegistrosDemo();
                break;
            case R.id.menu_eliminar:
                eliminarTodosRegistros();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;

    }

    // Como "mejora" le paso también el nombre para que aparezca en el mensaje de confirmación
    private void eliminarRegistro(int id, int posicion) {

        String nombre = myAdapter.getDatos().get(posicion).getNombre();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.confirmar);
        //builder.setMessage(R.string.preguntaeliminar);
        builder.setMessage("¿Desea eliminar a " + nombre + "?");
        builder.setPositiveButton(R.string.confirmar,
                (dialog, which) -> {

                    SuperheroesDb datos = new SuperheroesDb(getApplicationContext());
                    datos.eliminar(id);

                    myAdapter.getDatos().remove(posicion);
                    myAdapter.notifyItemRemoved(posicion);
                });
        builder.setNegativeButton(R.string.cancelar,
                (dialog, which) -> {
                });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void eliminarTodosRegistros() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(R.string.confirmar);
        builder.setMessage(R.string.preguntaeliminartodos);
        builder.setPositiveButton(R.string.confirmar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SuperheroesDb datos = new SuperheroesDb(getApplicationContext());
                        datos.eliminarTodos();
                        cargarLista();
                    }
                });
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void crearRegistrosDemo() {
        SuperheroesDb datos = new SuperheroesDb(this);
        datos.crearRegistrosDemo();
        cargarLista();
    }
}