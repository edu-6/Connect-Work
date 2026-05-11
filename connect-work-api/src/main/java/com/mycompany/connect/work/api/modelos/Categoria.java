package com.mycompany.connect.work.api.modelos;

public class Categoria extends Entidad {
    private String nombre;
    private int id;
    private boolean activa;

    public Categoria() {
    }

    public Categoria(String nombre) {
        this.nombre = nombre;
    }
    
    
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean datosCompletos() {
        return nombre != null && !nombre.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return nombre != null && nombre.length() <= 40;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }
}
