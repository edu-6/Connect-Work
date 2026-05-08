/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author edu
 */
public class ReporteHistorialComision {
    private double porcentaje;
    private LocalDate fechaCambio; 

    public ReporteHistorialComision(double porcentaje, LocalDate fechaCambio) {
        this.porcentaje = porcentaje;
        this.fechaCambio = fechaCambio;
    }

    public double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }
    
    
    
}
