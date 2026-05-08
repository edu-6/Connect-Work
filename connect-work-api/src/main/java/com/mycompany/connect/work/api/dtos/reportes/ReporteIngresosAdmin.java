/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteIngresosAdmin {
    private int contratosCompletados;
    private double totalComisiones;

    public ReporteIngresosAdmin(int contratosCompletados, double totalComisiones) {
        this.contratosCompletados = contratosCompletados;
        this.totalComisiones = totalComisiones;
    }

    public int getContratosCompletados() {
        return contratosCompletados;
    }

    public void setContratosCompletados(int contratosCompletados) {
        this.contratosCompletados = contratosCompletados;
    }

    public double getTotalComisiones() {
        return totalComisiones;
    }

    public void setTotalComisiones(double totalComisiones) {
        this.totalComisiones = totalComisiones;
    }
    
    
    
}
