package com.example.superheroesdb.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.superheroesdb.R;

import java.util.ArrayList;
import java.util.List;

public class SuperheroesDb  extends SQLiteOpenHelper {

    // Fuente: Ejemplo de la práctica de SQLite

    private List<Superheroe> lista;
    private Context contexto;

    public List<Superheroe> getLista() {
        return lista;
    }

    public SuperheroesDb(@Nullable Context context) {
        super(context, DbUtils.DATABASE_NAME, null, DbUtils.DATABASE_VERSION);
        lista = new ArrayList<>();
        this.contexto = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DbUtils.CREAR_TABLA_SUPER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versionAntigua, int versionNueva) {
        sqLiteDatabase.execSQL(DbUtils.DROP_TABLA_SUPER);
        onCreate(sqLiteDatabase);
    }

    public boolean cargar() {

        lista.clear();

        SQLiteDatabase db = this.getReadableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery(DbUtils.SELECT_SUPER, null);
            if (cursor.moveToFirst()) {
                do {
                    Superheroe item = new Superheroe(cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_ID))
                            , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBRE))
                            , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBREREAL))
                            , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_IMAGENID)));
                    lista.add(item);
                } while (cursor.moveToNext());

            }
            cursor.close();
            db.close();
        }

        return !lista.isEmpty();
    }

    public Superheroe cargar(int id) {
        //Completar el método

        // Devolver el objeto Superheroe
        Superheroe item = null;
        SQLiteDatabase db = this.getReadableDatabase(); // Modo solo lectura
        if (db.isOpen()) {

            // Definir la consulta
            String[] columns = new String[]{DbUtils.CAMPO_ID, DbUtils.CAMPO_NOMBRE, DbUtils.CAMPO_NOMBREREAL, DbUtils.CAMPO_IMAGENID};
            String where = DbUtils.CAMPO_ID + "=?";
            String[] whereArgs = new String[]{String.valueOf(id)};

            // Ejecutar la consulta
            Cursor cursor = db.query(DbUtils.TABLA_SUPER
                , columns , where , whereArgs
                , null, null, null);

            // Recorrer el cursor
            if (cursor.moveToFirst()) {

                item = new Superheroe(cursor.getInt(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_ID))
                    , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBRE))
                    , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_NOMBREREAL))
                    , cursor.getString(cursor.getColumnIndexOrThrow(DbUtils.CAMPO_IMAGENID)));

            }
            // Cerrar el cursor
            cursor.close();
            // Cerrar la base de datos
            db.close();
        }

        // Devolver el objeto Superheroe
        return item;
        //return null;
    }

    public long insertar(Superheroe item) {

        ContentValues valores = new ContentValues();
        valores.put(DbUtils.CAMPO_NOMBRE, item.getNombre());
        valores.put(DbUtils.CAMPO_NOMBREREAL, item.getNombreReal());
        valores.put(DbUtils.CAMPO_IMAGENID, item.getImagenId());

        SQLiteDatabase db = this.getWritableDatabase();
        long resultado = db.insert(DbUtils.TABLA_SUPER, null, valores);
        db.close();

        return resultado;
    }

    public int actualizar(Superheroe item, int id) {
        //Completar el método

        // Actualizar el registro
        ContentValues valores = new ContentValues();
        valores.put(DbUtils.CAMPO_NOMBRE, item.getNombre());
        valores.put(DbUtils.CAMPO_NOMBREREAL, item.getNombreReal());
        valores.put(DbUtils.CAMPO_IMAGENID, item.getImagenId());

        String where = DbUtils.CAMPO_ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(item.getId())};

        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.update(DbUtils.TABLA_SUPER, valores, where, whereArgs);
        db.close();

        // Devolver el resultado
        return resultado;
        //return 0;
    }

    public int eliminar(int id) {
        //Completar el método

        // Eliminar el registro
        String where = DbUtils.CAMPO_ID + "=?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        SQLiteDatabase db = this.getWritableDatabase();
        int resultado = db.delete(DbUtils.TABLA_SUPER, where, whereArgs);
        db.close();

        // Devolver el resultado
        return resultado;
        //return 0;
    }

    public void eliminarTodos(){
        //Completar el método

        // Eliminar todos los registros
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(DbUtils.DROP_TABLA_SUPER);
        db.execSQL(DbUtils.CREAR_TABLA_SUPER); // Crear la tabla de nuevo
        db.close();
    }
    public void crearRegistrosDemo(){

        insertar(new Superheroe(0, "Iron Man", "Anthony Edward Stark", String.valueOf(R.drawable.ironman)));
        insertar(new Superheroe(0, "Superman", "Clark Joseph Kent", String.valueOf(R.drawable.superman)));
        insertar(new Superheroe(0, "Batman", "Bruce Wayne", String.valueOf(R.drawable.batman)));
        insertar(new Superheroe(0, "Capitan America", "Steven Grant Rogers", String.valueOf(R.drawable.capitan)));
        insertar(new Superheroe(0, "Lobezno", "James Howlett", String.valueOf(R.drawable.lobezno)));
        insertar(new Superheroe(0, "Thor", "Thor Odinson Donald Blake", String.valueOf(R.drawable.thor)));
        insertar(new Superheroe(0, "Vision", "Ni puta idea", String.valueOf(R.drawable.vision)));

    }
}
