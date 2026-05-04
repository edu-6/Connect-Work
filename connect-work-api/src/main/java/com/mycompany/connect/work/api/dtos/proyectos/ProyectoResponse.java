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
public class ProyectoResponse {
    private String nombre;
    private String descripcion;
    private String categoria;
    private String estado;
    private double presupuestoMaximo;
    private String nombreCliente;
    private LocalDate fechaCreacion;
    private LocalDate fechaEntregaDeseada;
    private int id;
    private int idEstado;
    
    private boolean editable;
    

    public ProyectoResponse(String nombre, String descripcion, String categoria, String estado,
            double presupuestoMaximo, String nombreCliente, LocalDate fechaCreacion,
            LocalDate fechaEntregaDeseada, int id, int idEstado) {
        
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.estado = estado;
        this.presupuestoMaximo = presupuestoMaximo;
        this.nombreCliente = nombreCliente;
        this.fechaCreacion = fechaCreacion;
        this.fechaEntregaDeseada = fechaEntregaDeseada;
        this.id = id;
        this.idEstado = idEstado;
        
        this.editable = (idEstado ==1);
    }
    
    
}




