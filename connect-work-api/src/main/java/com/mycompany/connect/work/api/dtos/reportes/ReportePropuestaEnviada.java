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
public class ReportePropuestaEnviada {
    private String tituloProyecto;
    private double montoOfertado;
    private String estado;
    private LocalDate fechaEnviada;

    public ReportePropuestaEnviada(String tituloProyecto, double montoOfertado, String estado, LocalDate fechaEnviada) {
        this.tituloProyecto = tituloProyecto;
        this.montoOfertado = montoOfertado;
        this.estado = estado;
        this.fechaEnviada = fechaEnviada;
    }

    public String getTituloProyecto() {
        return tituloProyecto;
    }

    public void setTituloProyecto(String tituloProyecto) {
        this.tituloProyecto = tituloProyecto;
    }

    public double getMontoOfertado() {
        return montoOfertado;
    }

    public void setMontoOfertado(double montoOfertado) {
        this.montoOfertado = montoOfertado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEnviada() {
        return fechaEnviada;
    }

    public void setFechaEnviada(LocalDate fechaEnviada) {
        this.fechaEnviada = fechaEnviada;
    }
    
    
}
