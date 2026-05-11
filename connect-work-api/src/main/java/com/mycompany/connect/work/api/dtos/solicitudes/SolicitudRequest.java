/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.solicitudes;

/**
 *
 * @author edu
 */
public class SolicitudRequest {
    
    private String nombre;
    private String descripcion;
    private String cuiUsuario;

    public SolicitudRequest(String nombre, String descripcion, String cuiUsuario) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cuiUsuario = cuiUsuario;
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCuiUsuario() {
        return cuiUsuario;
    }
    
    
    
    
    
}
