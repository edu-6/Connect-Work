/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos;

import java.math.BigDecimal;

/**
 *
 * @author edu
 */
public class CarteraDigital {
    
    private int id;
    private String cuiCliente;
    private double saldo;

    public CarteraDigital() {
    }

    public CarteraDigital(String cuiCliente, double saldo) {
        this.cuiCliente = cuiCliente;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCuiCliente() {
        return cuiCliente;
    }

    public void setCuiCliente(String cuiCliente) {
        this.cuiCliente = cuiCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
}
