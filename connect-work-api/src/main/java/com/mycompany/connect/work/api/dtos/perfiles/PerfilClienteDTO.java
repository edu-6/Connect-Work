/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.perfiles;

/**
 *
 * @author edu
 */
public class PerfilClienteDTO {
    
    private String descripcion;
    private String sitioWeb;
    private String industria;

    public PerfilClienteDTO(String descripcion, String sitioWeb, String industria) {
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.industria = industria;
    }
    
    
    
}
