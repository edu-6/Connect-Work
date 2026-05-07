/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.proyectos.ProyectosDB;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ProyectosService extends CrudService {
    
    private ProyectosDB db = new ProyectosDB();
    
    public void crear(ProyectoRequest proyecto) throws DatosMuyLargosException, CamposVaciosException, ErrorDeLogicaException, DBException, EntidadDuplicadaException{
        
        this.revisarDatosCorrectos(proyecto);
        this.revisarLogica(proyecto);
        
        int idDuplicado = db.existeProyectoRepetido(proyecto.getCuiCliente(), proyecto.getNombre());
        if(idDuplicado >0){
            throw new EntidadDuplicadaException("ya tienes un proyecto con nombre "+proyecto.getNombre());
        }
        
        db.crear(proyecto);
        
    }
    
    public void editar(ProyectoRequest proyecto) throws CamposVaciosException, DatosMuyLargosException, ErrorDeLogicaException, DBException, EntidadDuplicadaException{
        
        this.revisarDatosCorrectos(proyecto);
        this.revisarLogica(proyecto);
        
        int idDuplicado = db.existeProyectoRepetido(proyecto.getCuiCliente(), proyecto.getNombre());
        
        if(idDuplicado>0 &&  proyecto.getId() != idDuplicado){
            throw new EntidadDuplicadaException("ya tienes un proyecto con nombre "+proyecto.getNombre());
        }
        
        db.editar(proyecto);
    }
    
    public ProyectoRequest buscarPorId(int id) throws DBException{
         return db.buscarPorId(id);
    }
    
    
    
    private void revisarLogica(ProyectoRequest proyecto) throws ErrorDeLogicaException{
        
        if(proyecto.getPresupuestoMaximo() <=0){
            throw new ErrorDeLogicaException("el presupuesto debe ser mayor a 0");
        }
        
        if(!proyecto.getFechaEntregaDeseada().isAfter(LocalDate.now())){
            throw new ErrorDeLogicaException("La fecha de entrega debe ser después de hoy");
        }
    }
    
    
    public int buscarEstadoProyecto(int idProyecto) throws DBException{
        return db.buscarEstadoProyecto(idProyecto);
    }
    
    
    private void cancelarProyecto(int id){
        
    }
}
