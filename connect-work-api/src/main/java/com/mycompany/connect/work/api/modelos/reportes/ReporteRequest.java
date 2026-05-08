/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.reportes;

import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ReporteRequest {

    private LocalDate fechaInicio;
    private LocalDate fechaFinal;
    private String tipoReporte;
    private String cuiUsuario;

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(LocalDate fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getCuiUsuario() {
        return cuiUsuario;
    }

    public void validarPeriodos() throws ErrorDeLogicaException {

        if (fechaInicio != null && fechaFinal != null) {
            if (fechaInicio.isAfter(fechaFinal)) {
                throw new ErrorDeLogicaException(" Ingrese un rango valido !");
            }

            if (fechaInicio.isEqual(fechaFinal)) {
                throw new ErrorDeLogicaException(" Ingrese un rango valido !");
            }
        }

        boolean faltaFechaInicio = fechaInicio == null && fechaFinal != null;
        boolean faltaFechaFinal = fechaInicio != null && fechaFinal == null;

        if (faltaFechaInicio || faltaFechaFinal) {
            throw new ErrorDeLogicaException("Ingrese ambas fechas");
        }

    }

    public boolean reporteConRango() {
        return fechaInicio != null && fechaFinal != null;
    }

}
