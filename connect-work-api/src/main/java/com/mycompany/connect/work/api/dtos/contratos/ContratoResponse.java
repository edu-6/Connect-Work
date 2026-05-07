/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.contratos;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ContratoResponse {
    
    private int id;
    private String nombreFreelancer;
    private double monto;
    private LocalDate fechaEntrega;

    public ContratoResponse() {
    }

    public ContratoResponse(int id, String nombreFreelancer, double monto, LocalDate fechaEntrega) {
        this.id = id;
        this.nombreFreelancer = nombreFreelancer;
        this.monto = monto;
        this.fechaEntrega = fechaEntrega;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreFreelancer() {
        return nombreFreelancer;
    }

    public void setNombreFreelancer(String nombreFreelancer) {
        this.nombreFreelancer = nombreFreelancer;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
}
