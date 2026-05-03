/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.proyectos.ProyectosBusquedaDB;
import com.mycompany.connect.work.api.db.proyectos.ProyectosDB;
import com.mycompany.connect.work.api.dtos.proyectos.BusquedaProyecto;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.modelos.enums.ProyectoTipoBusqueda;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class BusquedasProyectosService {
    
    private ProyectosDB db = new ProyectosDB();
    private ProyectosBusquedaDB  busquedaDB = new ProyectosBusquedaDB();
    
    
    
    
    public ProyectoRequest buscarPorId(int id) throws DBException{
         return db.buscarPorId(id);
    }
    
    
    
    public ArrayList<ProyectoResponse> buscarProyectos(BusquedaProyecto busqueda) throws ErrorDeLogicaException, DBException {
        
        switch (busqueda.getIdBusqueda()) {
            case ProyectoTipoBusqueda.POR_PRESUPUESTO:
                validarBusquedaPresupuesto(busqueda);
                return busquedaDB.buscarPorPresupuesto(busqueda);
                
            case ProyectoTipoBusqueda.POR_PERIODO:
                validarBusquedaPeriodo(busqueda);
                return busquedaDB.buscarEnClientePorPeriodo(busqueda);
                
            case ProyectoTipoBusqueda.POR_HABILIDAD:
                validarBusquedaHabilidad(busqueda);
                return busquedaDB.buscarPorHabilidad(busqueda);
                
            case ProyectoTipoBusqueda.POR_CATEGORIA:
                validarBusquedaCategoria(busqueda);
                return busquedaDB.buscarPorCategoria(busqueda);
                
            case ProyectoTipoBusqueda.CONTRATOS_ACTIVOS:
                validarBusquedaContratos(busqueda);
                return busquedaDB.buscarContratosActivos(busqueda);
            case ProyectoTipoBusqueda.TODOS_DEL_USUARIO:
                validarCuiCliente(busqueda);
                return busquedaDB.buscarEnClienteTodo(busqueda);
        }
        throw new ErrorDeLogicaException(" busqueda no definida");
    }
    
    
    private void validarBusquedaPresupuesto(BusquedaProyecto b) throws ErrorDeLogicaException {
        if (b.getMinPresupuesto() < 0 || b.getMaxiPresupuesto() < 0) {
            throw new ErrorDeLogicaException("El presupuesto no puede ser valores negativos");
        }
        if (b.getMinPresupuesto() > b.getMaxiPresupuesto()) {
            throw new ErrorDeLogicaException("El presupuesto mínimo no puede ser mayor al máximo");
        }
        if (b.getMaxiPresupuesto() == 0) {
            throw new ErrorDeLogicaException("Debes definir un presupuesto máximo mayor a 0");
        }
    }

    private void validarBusquedaPeriodo(BusquedaProyecto b) throws ErrorDeLogicaException {
        if (b.getFechaInicio() == null || b.getFechaFin() == null) {
            throw new ErrorDeLogicaException("Ingrese tanto la fecha de inicio como la de fin");
        }
        if (b.getFechaFin().isBefore(b.getFechaInicio())) {
            throw new ErrorDeLogicaException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        this.validarCuiCliente(b);
    }

    private void validarBusquedaCategoria(BusquedaProyecto b) throws ErrorDeLogicaException {
        if (b.getIdCategoria() <= 0) {
            throw new ErrorDeLogicaException("Debes seleccionar una categoría válida para la búsqueda.");
        }
    }

    private void validarBusquedaHabilidad(BusquedaProyecto b) throws ErrorDeLogicaException {
        if (b.getIdHabilidad() <= 0) {
            throw new ErrorDeLogicaException("Debes seleccionar una habilidad específica para filtrar.");
        }
    }

    private void validarBusquedaContratos(BusquedaProyecto b) throws ErrorDeLogicaException {
        if ((b.getCuiFreelancer() == null || b.getCuiFreelancer().isBlank()) && 
            (b.getCuiCliente() == null || b.getCuiCliente().isBlank())) {
            throw new ErrorDeLogicaException("No se puede buscar contratos sin un identificador de usuario (CUI).");
        }
    }

    
    private void validarCuiCliente(BusquedaProyecto b) throws ErrorDeLogicaException {
        if (b.getCuiCliente() == null || b.getCuiCliente().trim().isEmpty()) {
            throw new ErrorDeLogicaException("El CUI del cliente es obligatorio para esta operación.");
        }
    }
    
    
}
