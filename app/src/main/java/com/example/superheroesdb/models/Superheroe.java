package com.example.superheroesdb.models;

public class Superheroe {

    private int id;
    private String nombre;
    private String nombreReal;

    private String imagenId;

    public Superheroe(){

    }
    public Superheroe(int id, String nombre, String nombreReal, String imagenId) {
        this.id = id;
        this.nombre = nombre;
        this.nombreReal = nombreReal;
        this.imagenId = imagenId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }
}
