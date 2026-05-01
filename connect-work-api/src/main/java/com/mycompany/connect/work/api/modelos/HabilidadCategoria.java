/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

/**
 *
 * @author edu
 */
public class HabilidadCategoria extends Entidad {

    private int idCategoria;
    private int idHabilidad;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public void setIdHabilidad(int idHabilidad) {
        this.idHabilidad = idHabilidad;
    }

    @Override
    public boolean datosCompletos() {
        return true;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return true;
    }
    
}
