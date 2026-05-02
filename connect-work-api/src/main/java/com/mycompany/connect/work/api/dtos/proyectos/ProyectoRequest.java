/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.proyectos;

import com.mycompany.connect.work.api.modelos.Entidad;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ProyectoRequest extends Entidad {
    
    private String nombre;
    private String descripcion;
    private int idCategoria;
    private int id;
    private double presupuestoMaximo;
    private String cuiCliente;
    private LocalDate fechaEntregaDeseada;
    

    public ProyectoRequest(String nombre, String descripcion, int idCategoria, int id, double presupuestoMaximo, String cuiCliente, LocalDate fechaEntregaDeseada) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.id = id;
        this.presupuestoMaximo = presupuestoMaximo;
        this.cuiCliente = cuiCliente;
        this.fechaEntregaDeseada = fechaEntregaDeseada;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public int getId() {
        return id;
    }

    public double getPresupuestoMaximo() {
        return presupuestoMaximo;
    }

    public String getCuiCliente() {
        return cuiCliente;
    }


    public LocalDate getFechaEntregaDeseada() {
        return fechaEntregaDeseada;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPresupuestoMaximo(double presupuestoMaximo) {
        this.presupuestoMaximo = presupuestoMaximo;
    }

    public void setCuiCliente(String cuiCliente) {
        this.cuiCliente = cuiCliente;
    }


    public void setFechaEntregaDeseada(LocalDate fechaEntregaDeseada) {
        this.fechaEntregaDeseada = fechaEntregaDeseada;
    }

    @Override
    public boolean datosCompletos() {
        return nombre != null && !nombre.isBlank() &&
               descripcion != null && !descripcion.isBlank() &&
               cuiCliente != null && !cuiCliente.isBlank() &&
               idCategoria > 0 &&
               presupuestoMaximo > 0 &&
               fechaEntregaDeseada != null;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return nombre.length() <= 100 &&
               descripcion.length() <= 500 &&
               cuiCliente.length() <= 50;
    }
    
    
    
    
    
    
    
    
    
    
}
