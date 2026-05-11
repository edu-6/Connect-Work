/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.UsuariosPlataformaDB;
import com.mycompany.connect.work.api.db.perfiles.PerfilClienteDB;
import com.mycompany.connect.work.api.db.perfiles.PerfilFreelancerDB;
import com.mycompany.connect.work.api.db.perfiles.PerfilesCompletosDB;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilClienteDTO;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilFreelancerDTO;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilPlataformaDTO;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilSimpleDTO;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.modelos.perfles.PerfilCliente;
import com.mycompany.connect.work.api.modelos.perfles.PerfilFreelancer;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PerfilesCrudService extends CrudService {

    private PerfilClienteDB perfilClienteDB = new PerfilClienteDB();
    private PerfilFreelancerDB perfilFreelancerDB = new PerfilFreelancerDB();
    private UsuariosPlataformaDB usuariosPlataformaDB = new UsuariosPlataformaDB();
    private CalificacionesService calificacionesService = new CalificacionesService();
    private PerfilesCompletosDB perfilesCompletosDB = new PerfilesCompletosDB();

    public void crearPerfilCliente(PerfilCliente perfil) throws CamposVaciosException, DBException, DatosMuyLargosException {
        this.revisarDatosCorrectos(perfil);
        perfilClienteDB.crear(perfil);
        usuariosPlataformaDB.marcarPerfilCompletado(perfil.getCuiUsuario());
    }

    public void crearPerfilFreelancer(PerfilFreelancer perfil) throws CamposVaciosException, DBException, DatosMuyLargosException {
        this.revisarDatosCorrectos(perfil);

        if (perfil.getHabilidades().size() <= 0) {
            throw new CamposVaciosException("Debe agregar al menos una habilidad");
        }
        perfilFreelancerDB.crear(perfil);
        usuariosPlataformaDB.marcarPerfilCompletado(perfil.getCuiFreelancer());

        perfilFreelancerDB.crearHabilidadesFreelancer(perfil.getHabilidades());
    }

    public ArrayList<PerfilSimpleDTO> buscarPerfilesPorRol(int rol) throws DBException {
        
        return perfilesCompletosDB.buscarPerfilesPorRol(rol);
    }
    
    
    public PerfilPlataformaDTO buscarPerfilCompletoPlataforma(String nickname) throws DBException{
        
        PerfilSimpleDTO simple =perfilesCompletosDB.buscarUsuarioBase(nickname);
        
        PerfilPlataformaDTO perfilPlataforma = perfilesCompletosDB.buscarInfoUsuarioPlataforma(nickname);
        perfilPlataforma.setPerfilSimple(simple);
        
        
        PerfilClienteDTO perfilCliente = perfilClienteDB.buscar(perfilPlataforma.getCui());
        
        if(perfilCliente != null){
            perfilPlataforma.setPerfilCliente(perfilCliente);
        }
        
        PerfilFreelancerDTO perfilFreelancer = perfilFreelancerDB.buscar(perfilPlataforma.getCui());
        if(perfilFreelancer != null){
            perfilPlataforma.setPerfilFreelancer(perfilFreelancer);
            
            perfilFreelancer.setCalificaciones(calificacionesService.buscarCalificacionesFreelancer(perfilPlataforma.getCui()));
            perfilFreelancer.setPromedioCalificaciones(calificacionesService.buscarPromedioCalificaciones(perfilPlataforma.getCui()));
            
            perfilFreelancer.setHabilidades(perfilFreelancerDB.buscarHabilidadesFreelancer(perfilPlataforma.getCui()));
        }
        
        return perfilPlataforma;
        
        
    }
    
    
    
    
    
    

}
