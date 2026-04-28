/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.perfles;

import com.mycompany.connect.work.api.modelos.Entidad;

/**
 *
 * @author edu
 */
public class PerfilCliente extends Entidad {
    private String cuiUsuario;
    private String descripcion;
    private String sitioWeb;
    private String industria;

    @Override
    public boolean datosCompletos() {
        return cuiUsuario != null && !cuiUsuario.isBlank() &&
               descripcion != null && !descripcion.isBlank() &&
               industria != null && !industria.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return cuiUsuario.length() <= 50 &&
               descripcion.length() <= 400 &&
               (sitioWeb == null || sitioWeb.length() <= 500) &&
               industria.length() <= 100;
    }

    public PerfilCliente(String cuiUsuario, String descripcion, String sitioWeb, String industria) {
        this.cuiUsuario = cuiUsuario;
        this.descripcion = descripcion;
        this.sitioWeb = sitioWeb;
        this.industria = industria;
    }
    
    
    

    public String getCuiUsuario() {
        return cuiUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public String getIndustria() {
        return industria;
    }
    
    
}
