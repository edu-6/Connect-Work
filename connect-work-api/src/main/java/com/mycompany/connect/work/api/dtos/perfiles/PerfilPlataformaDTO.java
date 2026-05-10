/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.perfiles;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class PerfilPlataformaDTO {
    
    private PerfilSimpleDTO perfilSimple;
    
    private String correo;
    private String telefono;
    private LocalDate fechaNacimiento;
    private String cui;
    
    
    private PerfilClienteDTO perfilCliente;
    private PerfilFreelancerDTO perfilFreelancer;

    public PerfilPlataformaDTO(String correo, String telefono, LocalDate fechaNacimiento, String cui) {
        this.correo = correo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.cui = cui;
    }

    public void setPerfilSimple(PerfilSimpleDTO perfilSimple) {
        this.perfilSimple = perfilSimple;
    }

    public void setPerfilCliente(PerfilClienteDTO perfilCliente) {
        this.perfilCliente = perfilCliente;
    }

    public void setPerfilFreelancer(PerfilFreelancerDTO perfilFreelancer) {
        this.perfilFreelancer = perfilFreelancer;
    }

    public String getCui() {
        return cui;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
