/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.perfles;

import com.mycompany.connect.work.api.modelos.Entidad;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PerfilFreelancer extends Entidad {
    private String cuiFreelancer;
    private String biografia;
    private double tarifaHora;
    private int idNivelExperiencia;
    
    private ArrayList<HabilidadFreelancer> habilidades;
    
    

    @Override
    public boolean datosCompletos() {
        return cuiFreelancer != null && !cuiFreelancer.isBlank() &&
               biografia != null && !biografia.isBlank() &&
               tarifaHora > 0 &&
               idNivelExperiencia > 0;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return cuiFreelancer.length() <= 50 &&
               biografia.length() <= 300;
    }

    public PerfilFreelancer(String cuiFreelancer, String biografia, double tarifaHora, int idNivelExperiencia) {
        this.cuiFreelancer = cuiFreelancer;
        this.biografia = biografia;
        this.tarifaHora = tarifaHora;
        this.idNivelExperiencia = idNivelExperiencia;
    }
    
    
    

    public String getCuiFreelancer() {
        return cuiFreelancer;
    }

    public String getBiografia() {
        return biografia;
    }

    public double getTarifaHora() {
        return tarifaHora;
    }

    public int getIdNivelExperiencia() {
        return idNivelExperiencia;
    }

    public ArrayList<HabilidadFreelancer> getHabilidades() {
        return habilidades;
    }
    
    
    
    
    
}
