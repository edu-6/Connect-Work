/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.perfiles;

import com.mycompany.connect.work.api.dtos.calificaciones.CalificacionResponse;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PerfilFreelancerDTO {
    
    private String experiencia;
    private double tarifaPorHora;
    private String biografia;
    
    private ArrayList<CalificacionResponse> calificaciones;
    private double promedioCalificaciones;
    
    

    public PerfilFreelancerDTO(String experiencia, double tarifaPorHora, String biografia) {
        this.experiencia = experiencia;
        this.tarifaPorHora = tarifaPorHora;
        this.biografia = biografia;
    }

    public void setCalificaciones(ArrayList<CalificacionResponse> calificaciones) {
        this.calificaciones = calificaciones;
    }

    public void setPromedioCalificaciones(double promedioCalificaciones) {
        this.promedioCalificaciones = promedioCalificaciones;
    }
    
    
    
    
    
    
    
}
