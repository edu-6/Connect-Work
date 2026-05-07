/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.ContratosDB;
import com.mycompany.connect.work.api.db.proyectos.ProyectosDB;
import com.mycompany.connect.work.api.dtos.contratos.ContratoRequest;
import com.mycompany.connect.work.api.dtos.contratos.ContratoResponse;
import com.mycompany.connect.work.api.dtos.propuestas.PropuestaRequest;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.modelos.Contrato;
import com.mycompany.connect.work.api.modelos.enums.EstadosProyecto;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ContratosService {
    
    private PropuestasService propuestasService = new PropuestasService();
    private ProyectosService proyectosService = new ProyectosService();
    private CarteraDigitalService carterasService = new CarteraDigitalService();
    private ContratosDB contratosDB = new ContratosDB();
    private ProyectosDB proyectosDB = new ProyectosDB();
    private ComisionesService comisiones = new ComisionesService();
    
    public void crearContrato(ContratoRequest req) throws DBException, ErrorDeLogicaException, CamposVaciosException{
        
        PropuestaRequest propuesta = propuestasService.obtenerPropuestaRequestById(req.getIdPropuesta());
        ProyectoRequest proyecto = proyectosService.buscarPorId(propuesta.getIdProyecto());
        
        int estadoProyecto = proyectosService.buscarEstadoProyecto(propuesta.getIdProyecto());
        
        if(estadoProyecto != EstadosProyecto.ABIERTO.getId()){
            throw new ErrorDeLogicaException("el proyecto no está abierto,");
        }
        
        
        double saldoCliente = carterasService.obtenerCartera(proyecto.getCuiCliente()).getSaldo();
        double costoDelProyecto = propuesta.getPresupuestoOfertado();
        
        if(saldoCliente < costoDelProyecto){
            throw new ErrorDeLogicaException(" tu saldo es insuficiente, recarga tu tarjeta SALDO : "+saldoCliente);
        }
        
        
        carterasService.restarSaldo(proyecto.getCuiCliente(), costoDelProyecto);
        
        proyectosDB.cambiarEstadoProyecto(propuesta.getIdProyecto(),EstadosProyecto.EN_PROGRESO.getId());
        
        System.out.println("el estado del poryecto será "+ EstadosProyecto.EN_PROGRESO.getId());
        
        
        
        int porcentajeComision =  comisiones.obtener().getPorcentajeComision();
        LocalDate fechaEntrega = LocalDate.now().plusDays(propuesta.getPlazoEntrega());
        
        Contrato contrato = new Contrato(
                porcentajeComision,
                fechaEntrega,
                LocalDate.now(),
                propuesta.getCuiFreelancer(),
                req.getIdPropuesta()
        );
        
        contratosDB.crear(contrato);

        // traer a la propuesta completa
        // revisar que el proyecto esté en estado abierto
        // revisar que el cliente tenga el suficiente saldo
        // quitar el saldo al cliente 
        // marcar el proyecto como en progeso
        // crear el contrato       
    }
    
    
    public void cancelarContrato(int idContrato) throws DBException, ErrorDeLogicaException, CamposVaciosException {
        
        Contrato contrato = contratosDB.buscarPorId(idContrato);
        PropuestaRequest propuesta = propuestasService.obtenerPropuestaRequestById(contrato.getIdPropuesta());
        
        
        ProyectoRequest proyecto = proyectosService.buscarPorId(propuesta.getIdProyecto());
        
        int estadoProyecto = proyectosService.buscarEstadoProyecto(propuesta.getIdProyecto());
        
        if(estadoProyecto == EstadosProyecto.COMPLETADO.getId()){
            throw new ErrorDeLogicaException("El proyecto ha sido completado, no puede cancelar ");
        }
        
        contratosDB.cancelar(idContrato);
        
        
        proyectosDB.cambiarEstadoProyecto(propuesta.getIdProyecto(), EstadosProyecto.CANCELADO.getId());
        carterasService.recargarSaldo(proyecto.getCuiCliente(), propuesta.getPresupuestoOfertado());
        
        // traer el contrato
        // eliminar el contrato
        // poner el proyecto como cancelado
        // retornar el dinero al cliente
    }
    
    
    
    public ContratoResponse buscarContratoDeProyecto(int idProyecto) throws DBException{
        return contratosDB.buscarContratoDeProyecto(idProyecto);
    }
    

    
    
    
    
    
    
    
}
