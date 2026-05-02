/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.enums;

/**
 *
 * @author edu
 */
public enum EstadosProyecto {
    ABIERTO(1),
    EN_REVISION(2),
    EN_PROGRESO(3),
    ENTREGA_PENDIENTE(4),
    COMPLETADO(5),
    CANCELADO(6);
    
    private final int id;

    private EstadosProyecto(int id) {   
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
