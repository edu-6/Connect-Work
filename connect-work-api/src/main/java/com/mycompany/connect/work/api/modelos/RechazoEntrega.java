/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

/**
 *
 * @author edu
 */
public class RechazoEntrega extends Entidad {
    
    private int idSolicitud;
    private String motivo;
    private int id;

    public RechazoEntrega() {
    }

    public RechazoEntrega(int id, String motivo) {
        this.id = id;
        this.motivo = motivo;
    }

    @Override
    public boolean datosCompletos() {
        return idSolicitud > 0 && 
               motivo != null && !motivo.trim().isEmpty();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return motivo.length() <= 300;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
    
}
