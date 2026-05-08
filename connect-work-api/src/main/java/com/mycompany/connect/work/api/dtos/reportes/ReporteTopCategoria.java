/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteTopCategoria {
    
    private String nombreCategoria;
    private int cantidadContratos;
    private double totalIngresos;

    public ReporteTopCategoria(String nombreCategoria, int cantidadContratos, double totalIngresos) {
        this.nombreCategoria = nombreCategoria;
        this.cantidadContratos = cantidadContratos;
        this.totalIngresos = totalIngresos;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public int getCantidadContratos() {
        return cantidadContratos;
    }

    public void setCantidadContratos(int cantidadContratos) {
        this.cantidadContratos = cantidadContratos;
    }

    public double getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(double totalIngresos) {
        this.totalIngresos = totalIngresos;
    }
    
    
    
}
