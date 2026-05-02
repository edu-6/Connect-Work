/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.proyectos;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class BusquedaProyecto {
    
    private int idCategoria;
    private int idHabilidad;
    private LocalDate fechaCreacion;
    private String cuiFreelancer;
    private String cuiCliente;
    
    private double minPresupuesto;
    private double maxiPresupuesto;
    
    private int idBusqueda;
    
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getIdHabilidad() {
        return idHabilidad;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public String getCuiFreelancer() {
        return cuiFreelancer;
    }

    public String getCuiCliente() {
        return cuiCliente;
    }

    public int getIdBusqueda() {
        return idBusqueda;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public double getMinPresupuesto() {
        return minPresupuesto;
    }

    public double getMaxiPresupuesto() {
        return maxiPresupuesto;
    }
    
    
    
    
    
    
    
    
}
