/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.reportes;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ReporteHistorialProyecto {
    
    private String titulo;
    private String estado;
    private double presupuesto;
    private String nombreFreelancer;
    private LocalDate fechaPublicacion;

    public ReporteHistorialProyecto(String titulo, String estado, double presupuesto, String nombreFreelancer, LocalDate fechaPublicacion) {
        this.titulo = titulo;
        this.estado = estado;
        this.presupuesto = presupuesto;
        this.nombreFreelancer = nombreFreelancer;
        this.fechaPublicacion = fechaPublicacion;
        
        if(nombreFreelancer == null){
            nombreFreelancer = " sin freelancer";
        }
    }
    
    
    
}
