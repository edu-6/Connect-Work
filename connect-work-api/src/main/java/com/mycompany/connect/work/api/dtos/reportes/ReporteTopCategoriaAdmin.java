/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteTopCategoriaAdmin {
    private String nombreCategoria;
    private int cantidadContratos;
    private double totalComisiones;

    public ReporteTopCategoriaAdmin(String nombreCategoria, int cantidadContratos, double totalComisiones) {
        this.nombreCategoria = nombreCategoria;
        this.cantidadContratos = cantidadContratos;
        this.totalComisiones = totalComisiones;
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

    public double getTotalComisiones() {
        return totalComisiones;
    }

    public void setTotalComisiones(double totalComisiones) {
        this.totalComisiones = totalComisiones;
    }
    
    
    
    
    
    
    
}
