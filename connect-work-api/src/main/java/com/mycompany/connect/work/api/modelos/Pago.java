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
public class Pago {
    
    private LocalDate fechaPago;
    private double comision;
    private double monto;
    private int idContrato;

    public Pago(double comision, double monto, int idContrato) {
        this.comision = comision;
        this.monto = monto;
        this.idContrato = idContrato;
        this.fechaPago= LocalDate.now();
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getIdContrato() {
        return idContrato;
    }

    public void setIdContrato(int idContrato) {
        this.idContrato = idContrato;
    }
    
    
    
    
    


    
    
}
