/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class Contrato {
    
    
    private int porcentajeComision;
    private LocalDate fechaEntrega;
    private LocalDate fechaGeneracion;
    private String cuiFreelancer;
    private int idPropuesta;

    public Contrato(int porcentajeComision, LocalDate fechaEntrega, LocalDate fechaGeneracion, String cuiFreelancer, int idPropuesta) {
        this.porcentajeComision = porcentajeComision;
        this.fechaEntrega = fechaEntrega;
        this.fechaGeneracion = fechaGeneracion;
        this.cuiFreelancer = cuiFreelancer;
        this.idPropuesta = idPropuesta;
    }

    public int getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(int porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public String getCuiFreelancer() {
        return cuiFreelancer;
    }

    public void setCuiFreelancer(String cuiFreelancer) {
        this.cuiFreelancer = cuiFreelancer;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public void setIdPropuesta(int idPropuesta) {
        this.idPropuesta = idPropuesta;
    }

    
    
    
    
}
