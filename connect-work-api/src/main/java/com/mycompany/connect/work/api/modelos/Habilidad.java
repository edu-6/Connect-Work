/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

/**
 *
 * @author edu
 */
public class Habilidad extends Entidad{
    private String nombre;
    private int id;
    private boolean activa;

    public Habilidad(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    

    public Habilidad() {
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
