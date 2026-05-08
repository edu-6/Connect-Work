/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteGastoCategoria {
    
    private String nombreCategoria;
    private double totalGastado;

    public ReporteGastoCategoria(String nombreCategoria, double totalGastado) {
        this.nombreCategoria = nombreCategoria;
        this.totalGastado = totalGastado;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(double totalGastado) {
        this.totalGastado = totalGastado;
    }
    
    
    
}
