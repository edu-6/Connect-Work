/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.utils;

/**
 *
 * @author edu
 */
public class ErrorRequest {
    
    private String motivo;
    private String detalles;
    
    

    public ErrorRequest(String motivo, String detalles) {
        this.motivo = motivo;
        this.detalles = detalles;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    
    
    
}
