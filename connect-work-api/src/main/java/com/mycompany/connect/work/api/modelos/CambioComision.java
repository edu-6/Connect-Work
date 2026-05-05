/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CambioComision {
    
    private int id;
    private LocalDate fecha;
    private int porcentaje;

    public CambioComision() {}

    public CambioComision(LocalDate fecha, int porcentaje) {
        this.fecha = fecha;
        this.porcentaje = porcentaje;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }
 
    
}
