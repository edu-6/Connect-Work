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
public class ReporteContratoCompletado {
    private String nombreCliente;
    private String tituloProyecto;
    private double montoRecibido;
    private int calificacion;
    private LocalDate fechaPago;

    public ReporteContratoCompletado(String nombreCliente, String tituloProyecto, double montoRecibido, int calificacion, LocalDate fechaPago) {
        this.nombreCliente = nombreCliente;
        this.tituloProyecto = tituloProyecto;
        this.montoRecibido = montoRecibido;
        this.calificacion = calificacion;
        this.fechaPago = fechaPago;
    }
    
}
