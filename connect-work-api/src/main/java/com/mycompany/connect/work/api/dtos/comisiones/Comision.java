/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.comisiones;

import com.mycompany.connect.work.api.modelos.Entidad;

/**
 *
 * @author edu
 */
public class Comision extends Entidad {
    private int porcentajeComision;

    public Comision(int porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    
    

    @Override
    public boolean datosCompletos() {
        return porcentajeComision > 0 && porcentajeComision <= 100;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return porcentajeComision <= 100;
    }

    public int getPorcentajeComision() {
        return porcentajeComision;
    }
    
     
    
}
