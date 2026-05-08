/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.enums;

/**
 *
 * @author edu
 */
public enum TiposDeReporte {
    CLIENTE_HISTORIAL_PROYECTOS("CLIENTE_HISTORIAL_PROYECTOS"),
    CLIENTE_HISTORIAL_RECARGAS("CLIENTE_HISTORIAL_RECARGAS"),
    CLIENTE_GASTO_POR_CATEGORIA("CLIENTE_GASTO_POR_CATEGORIA"),

    FREELANCER_SALDO_ACTUAL("FREELANCER_SALDO_ACTUAL"),
    FREELANCER_CONTRATOS_COMPLETADOS("FREELANCER_CONTRATOS_COMPLETADOS"),
    FREELANCER_TOP_CATEGORIAS("FREELANCER_TOP_CATEGORIAS"),
    FREELANCER_PROPUESTAS_ENVIADAS("FREELANCER_PROPUESTAS_ENVIADAS"),

    
    ADMIN_HISTORIAL_COMISIONES("ADMIN_HISTORIAL_COMISIONES"),
    ADMIN_TOP_FREELANCERS_INGRESOS("ADMIN_TOP_FREELANCERS_INGRESOS"),
    ADMIN_TOP_CATEGORIAS_ACTIVIDAD("ADMIN_TOP_CATEGORIAS_ACTIVIDAD"),
    ADMIN_TOTAL_INGRESOS_PLATAFORMA("ADMIN_TOTAL_INGRESOS_PLATAFORMA");

    private final String valor;

    TiposDeReporte(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
    
}
