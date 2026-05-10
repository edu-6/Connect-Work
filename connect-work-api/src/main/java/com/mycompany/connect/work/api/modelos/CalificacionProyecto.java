/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CalificacionProyecto {
    
    private int cantidadEstrellas;
    private LocalDate fechaCalificacion;
    private String comentario;
    private String cuiFreelancer;
    private int idProyecto;

    public CalificacionProyecto(int cantidadEstrellas, LocalDate fechaCalificacion, String comentario, String cuiFreelancer, int idProyecto) {
        this.cantidadEstrellas = cantidadEstrellas;
        this.fechaCalificacion = fechaCalificacion;
        this.comentario = comentario;
        this.cuiFreelancer = cuiFreelancer;
        this.idProyecto = idProyecto;
    }
    
    
    

    public int getCantidadEstrellas() {
        return cantidadEstrellas;
    }

    public void setCantidadEstrellas(int cantidadEstrellas) {
        this.cantidadEstrellas = cantidadEstrellas;
    }

    public LocalDate getFechaCalificacion() {
        return fechaCalificacion;
    }

    public void setFechaCalificacion(LocalDate fechaCalificacion) {
        this.fechaCalificacion = fechaCalificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getCuiFreelancer() {
        return cuiFreelancer;
    }

    public void setCuiFreelancer(String cuiFreelancer) {
        this.cuiFreelancer = cuiFreelancer;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }
    

     

    

    
    
}
