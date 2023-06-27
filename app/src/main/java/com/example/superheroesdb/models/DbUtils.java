package com.example.superheroesdb.models;

public class DbUtils {

    public static final String DATABASE_NAME = "superheroes.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLA_SUPER = "superheroes";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_NOMBREREAL = "nombrereal";
       public static final String CAMPO_IMAGENID = "imagenid";

    public static final String CREAR_TABLA_SUPER = "CREATE TABLE " + TABLA_SUPER + " (" +
            CAMPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
            "," + CAMPO_NOMBRE + " TEXT NOT NULL" +
            "," + CAMPO_NOMBREREAL + " TEXT NOT NULL" +
            "," + CAMPO_IMAGENID + " TEXT)";

    public static final String DROP_TABLA_SUPER = "DROP TABLE IF EXISTS " + TABLA_SUPER;
    public static final String SELECT_SUPER = "SELECT * FROM " + TABLA_SUPER;
    public static final String DELETE_SUPER = "DELETE FROM " + TABLA_SUPER;


}
