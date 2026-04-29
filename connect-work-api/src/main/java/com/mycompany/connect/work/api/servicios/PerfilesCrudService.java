/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.perfiles.PerfilClienteDB;
import com.mycompany.connect.work.api.db.perfiles.PerfilFreelancerDB;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.modelos.perfles.PerfilCliente;
import com.mycompany.connect.work.api.modelos.perfles.PerfilFreelancer;

/**
 *
 * @author edu
 */
public class PerfilesCrudService extends CrudService {
    
    private PerfilClienteDB perfilClienteDB = new PerfilClienteDB();
    private PerfilFreelancerDB perfilFreelancerDB = new PerfilFreelancerDB();
    
    
    
    public void crearPerfilCliente(PerfilCliente perfil) throws CamposVaciosException, DBException, DatosMuyLargosException{
        this.revisarDatosCorrectos(perfil);
        perfilClienteDB.crear(perfil);
    }
    
    public void crearPerfilFreelancer(PerfilFreelancer perfil) throws CamposVaciosException, DBException, DatosMuyLargosException{
        this.revisarDatosCorrectos(perfil);
        perfilFreelancerDB.crear(perfil);
    }
    
    
    
    
    
}
