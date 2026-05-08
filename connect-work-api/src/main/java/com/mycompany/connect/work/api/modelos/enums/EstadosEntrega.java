/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.enums;

/**
 *
 * @author edu
 */
public enum EstadosEntrega {
    ENVIADA(1),
    RECHAZADA(2),
    ACEPTADA(3);
    
    private final int id;

    private EstadosEntrega(int id) {   
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
