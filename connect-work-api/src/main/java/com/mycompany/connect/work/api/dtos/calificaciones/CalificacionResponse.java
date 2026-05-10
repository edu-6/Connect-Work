/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.calificaciones;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CalificacionResponse {
    
    private int cantidadEstrellas;
    private String comentario;
    private LocalDate fechaCalificacion;

    public CalificacionResponse(int cantidadEstrellas, String comentario, LocalDate fechaCalificacion) {
        this.cantidadEstrellas = cantidadEstrellas;
        this.comentario = comentario;
        this.fechaCalificacion = fechaCalificacion;
    }
    
    
    
}
