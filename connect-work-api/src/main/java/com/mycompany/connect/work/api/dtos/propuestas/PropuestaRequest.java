/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.propuestas;

import com.mycompany.connect.work.api.modelos.Entidad;

/**
 *
 * @author edu
 */
public class PropuestaRequest extends Entidad {
    
    private String cuiFreelancer;
    private int idProyecto;
    private String cartaPresentacion;
    private double presupuestoOfertado;
    private int plazoEntrega;

    public PropuestaRequest(String cuiFreelancer, int idProyecto, String cartaPresentacion, double presupuestoOfertado, int plazoEntrega) {
        this.cuiFreelancer = cuiFreelancer;
        this.idProyecto = idProyecto;
        this.cartaPresentacion = cartaPresentacion;
        this.presupuestoOfertado = presupuestoOfertado;
        this.plazoEntrega = plazoEntrega;
    }
    
    
    

    public String getCuiFreelancer() {
        return cuiFreelancer;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public String getCartaPresentacion() {
        return cartaPresentacion;
    }

    public double getPresupuestoOfertado() {
        return presupuestoOfertado;
    }

    public int getPlazoEntrega() {
        return plazoEntrega;
    }

    @Override
    public boolean datosCompletos() {
        return cuiFreelancer != null && !cuiFreelancer.isBlank() &&
               idProyecto > 0 &&
               cartaPresentacion != null && !cartaPresentacion.isBlank() &&
               presupuestoOfertado > 0 &&
               plazoEntrega > 0;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return  
               cartaPresentacion.length() <= 500;
    }

    public void setCuiFreelancer(String cuiFreelancer) {
        this.cuiFreelancer = cuiFreelancer;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public void setCartaPresentacion(String cartaPresentacion) {
        this.cartaPresentacion = cartaPresentacion;
    }

    public void setPresupuestoOfertado(double presupuestoOfertado) {
        this.presupuestoOfertado = presupuestoOfertado;
    }

    public void setPlazoEntrega(int plazoEntrega) {
        this.plazoEntrega = plazoEntrega;
    }
    
    
    
    
    
     
    
}
