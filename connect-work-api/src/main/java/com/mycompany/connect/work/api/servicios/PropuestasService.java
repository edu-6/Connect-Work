/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.BusquedaPropuestasDB;
import com.mycompany.connect.work.api.db.PropuestasDB;
import com.mycompany.connect.work.api.db.proyectos.ProyectosDB;
import com.mycompany.connect.work.api.dtos.propuestas.BusquedaPropuesta;
import com.mycompany.connect.work.api.dtos.propuestas.PropuestaRequest;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.modelos.enums.EstadosProyecto;
import com.mycompany.connect.work.api.modelos.enums.PropuestaTipoBusqueda;

/**
 *
 * @author edu
 */
public class PropuestasService extends CrudService {
    
    private PropuestasDB db = new PropuestasDB();
    private ProyectosDB proyectosDB = new ProyectosDB();
    private BusquedaPropuestasDB busquedas = new BusquedaPropuestasDB();
    
    public void crear(PropuestaRequest request) throws ErrorDeLogicaException, DBException, EntidadDuplicadaException, CamposVaciosException, DatosMuyLargosException{
        
        if(request == null) throw new ErrorDeLogicaException("error al recibir la propuesta");
        
        this.revisarDatosCorrectos(request);
        
        boolean existeOtraPropuesta = db.existePropuesta(request.getIdProyecto(), request.getCuiFreelancer());
        
        if(existeOtraPropuesta) throw new EntidadDuplicadaException("Ya publicó una propuesta, retirela antes ");
        
        
        ProyectoRequest proyecto  = proyectosDB.buscarPorId(request.getIdProyecto());
        if(request.getPresupuestoOfertado() > proyecto.getPresupuestoMaximo()){
            throw new ErrorDeLogicaException(" su oferta es mayor al prresupuesta máximo");
        }
        
        db.crear(request); 
    }
    
    public void eliminar(int id) throws DBException, ErrorDeLogicaException{
        int estadoProyecto = busquedas.buscarEstadoProyecto(id);
        if(estadoProyecto != EstadosProyecto.ABIERTO.getId() ){
            throw new ErrorDeLogicaException("no se puede eliminar, el proyecto ya no está abierto");
        }
        db.eliminar(id);
    }
    
    public Object buscarPropuestas(BusquedaPropuesta busqueda) throws ErrorDeLogicaException, CamposVaciosException, DBException{
        if(busqueda == null) throw new ErrorDeLogicaException("Error al recibir la busqueda");
        
        
        switch (busqueda.getIdTipoBusqueda()) {
            case PropuestaTipoBusqueda.PROPUESTA_POR_ID:
                
                validarCuiFreelancer(busqueda.getCuiFreelancer());
                return busquedas.buscarPorId(busqueda.getIdPropuesta());
                
            case PropuestaTipoBusqueda.PROPUESTA_DE_FREELANCER_EN_PROYECTO:
                
                validarCuiFreelancer(busqueda.getCuiFreelancer());
                return busquedas.buscarPropuestaEnProyecto(busqueda.getIdProyecto(), busqueda.getCuiFreelancer());
            case PropuestaTipoBusqueda.PROPUESTAS_EN_PROYECTO:
                return busquedas.buscarPorProyecto(busqueda.getIdProyecto());
        }
        throw new ErrorDeLogicaException("no se reconoción la busqueda");
    }
    
    private void validarCuiFreelancer(String cuiFreelancer) throws CamposVaciosException{
        if(cuiFreelancer == null) throw new CamposVaciosException("erorr, no se recibió el cui freelancer");
    }
    
}
