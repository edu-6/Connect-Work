/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.propuestas;

/**
 *
 * @author edu
 */
public class PropuestaRequest {
    
    private String cuiFreelancer;
    private int idProyecto;
    private String cartaPresentacion;
    private double presupuestoOfertado;
    private int plazoEntrega;

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
    
    
     
    
}
