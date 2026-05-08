/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

/**
 *
 * @author edu
 */
public class ReporteTopFreelancer {
    private String nombreFreelancer;
    private int contratosCompletados;
    private double totalGenerado; 
    private double comisionPlataforma; 

    public ReporteTopFreelancer(String nombreFreelancer, int contratosCompletados, double totalGenerado, double comisionPlataforma) {
        this.nombreFreelancer = nombreFreelancer;
        this.contratosCompletados = contratosCompletados;
        this.totalGenerado = totalGenerado;
        this.comisionPlataforma = comisionPlataforma;
    }

    public String getNombreFreelancer() {
        return nombreFreelancer;
    }

    public void setNombreFreelancer(String nombreFreelancer) {
        this.nombreFreelancer = nombreFreelancer;
    }

    public int getContratosCompletados() {
        return contratosCompletados;
    }

    public void setContratosCompletados(int contratosCompletados) {
        this.contratosCompletados = contratosCompletados;
    }

    public double getTotalGenerado() {
        return totalGenerado;
    }

    public void setTotalGenerado(double totalGenerado) {
        this.totalGenerado = totalGenerado;
    }

    public double getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(double comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }
    
    
    
}
