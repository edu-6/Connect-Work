/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.solicitudes;

/**
 *
 * @author edu
 */
public class SolicitudResponse {
    private String tipoSolicitud;
    private String estado;
    private String nombre;
    private int id;

    public SolicitudResponse(String tipoSolicitud, String estado, String nombre, int id) {
        this.tipoSolicitud = tipoSolicitud;
        this.estado = estado;
        this.nombre = nombre;
        this.id = id;
    }
    
    
    
}
