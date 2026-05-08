/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.entregas;

import com.mycompany.connect.work.api.modelos.RechazoEntrega;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class EntregaResponse {
    private int id;
    private String descripcion;
    private String [] archivos;
    private String estado;
    private LocalDate fechaEnvio;
    
    
    private RechazoEntrega rechazo;

    public EntregaResponse(int id, String descripcion, String estado, LocalDate fechaEnvio) {
        this.id = id;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
    }
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String[] getArchivos() {
        return archivos;
    }

    public void setArchivos(String[] archivos) {
        this.archivos = archivos;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDate fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setRechazo(RechazoEntrega rechazo) {
        this.rechazo = rechazo;
    }
    
    
    
    
    
    
    
    
}
