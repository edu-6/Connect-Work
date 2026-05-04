/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.propuestas;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class PropuestaResponse {
    
    private String cartaPresentacion;
    private double presupuestoOfertado;
    private int plazoEntrega;
    private LocalDate  fechaCreacion;
    private String estado;

    public PropuestaResponse(String cartaPresentacion, double presupuestoOfertado, int plazoEntrega, LocalDate fechaCreacion, String estado) {
        this.cartaPresentacion = cartaPresentacion;
        this.presupuestoOfertado = presupuestoOfertado;
        this.plazoEntrega = plazoEntrega;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    public double getPresupuestoOfertado() {
        return presupuestoOfertado;
    }

    public void setPresupuestoOfertado(double presupuestoOfertado) {
        this.presupuestoOfertado = presupuestoOfertado;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    public void setPlazoEntrega(int plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
     
    
}
