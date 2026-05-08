/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.entregas;

/**
 *
 * @author edu
 */
public class ArchivoEntrega {
    
    private String archivo;
    private int idEntrega;

    public ArchivoEntrega(String archivo, int idEntrega) {
        this.archivo = archivo;
        this.idEntrega = idEntrega;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public int getIdEntrega() {
        return idEntrega;
    }

    public void setIdEntrega(int idEntrega) {
        this.idEntrega = idEntrega;
    }
    
    
    
    
    
    
}
