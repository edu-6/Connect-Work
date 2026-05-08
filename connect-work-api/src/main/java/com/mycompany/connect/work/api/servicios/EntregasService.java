/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.EntregasDB;
import com.mycompany.connect.work.api.db.PagosDB;
import com.mycompany.connect.work.api.db.RechazosEntregasDB;
import com.mycompany.connect.work.api.dtos.entregas.ArchivoEntrega;
import com.mycompany.connect.work.api.dtos.entregas.EntregaRequest;
import com.mycompany.connect.work.api.dtos.entregas.EntregaResponse;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.modelos.Contrato;
import com.mycompany.connect.work.api.modelos.Pago;
import com.mycompany.connect.work.api.modelos.RechazoEntrega;
import com.mycompany.connect.work.api.modelos.enums.EstadosEntrega;
import com.mycompany.connect.work.api.modelos.enums.EstadosProyecto;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EntregasService extends CrudService {
    
    private ProyectosService proyectosService = new ProyectosService();
    private EntregasDB entregasDB = new EntregasDB();
    private RechazosEntregasDB rechazosDB = new RechazosEntregasDB();
    private ContratosService contratosService = new ContratosService();
    private PagosDB pagosDB = new PagosDB();
    private CarteraDigitalService carterasService = new CarteraDigitalService();
    
    
    public void crearEntrega(EntregaRequest entrega) throws DatosMuyLargosException, CamposVaciosException, ErrorDeLogicaException, DBException{
  
        this.revisarDatosCorrectos(entrega);
        
        int estadoProyecto = proyectosService.buscarEstadoProyecto(entrega.getIdProyecto());
        
        if(entregasDB.existeEntregaSinRevisar(entrega.getIdProyecto())){
            throw new ErrorDeLogicaException(" no puede realizar entregas, el cliente no ha revisado su ultima entrega");
        }
        
        if(estadoProyecto != EstadosProyecto.EN_PROGRESO.getId()){
            throw new ErrorDeLogicaException(" no puede realizar entregas, el proyecto no está en etapa de progreso");
        }
        
        
        int idEntrega = entregasDB.crear(entrega);
        
        ArrayList<ArchivoEntrega> archivos = entrega.crearArchivosEntregas(idEntrega);
        
        for (ArchivoEntrega archivo : archivos) {
            entregasDB.insertarArchivo(archivo.getIdEntrega(),archivo.getArchivo());
        }
        
        
        proyectosService.cambiarEstadoProyecto(entrega.getIdProyecto(), EstadosProyecto.ENTREGA_PENDIENTE.getId());
    }
    
    
    public ArrayList<EntregaResponse> buscarHisotrialDeEntregas(int idProyecto) throws DBException{
        ArrayList<EntregaResponse> lista =  this.entregasDB.buscarHistorialDeEntregas(idProyecto);
        for (EntregaResponse entregaResponse : lista) {
            entregaResponse.setArchivos(entregasDB.buscarArchivos(entregaResponse.getId()));
            entregaResponse.setRechazo(rechazosDB.buscarPorId(entregaResponse.getId()));
        }
        return lista;
    }
    
    
    
    public EntregaResponse buscarEntregaSinRevisar(int idProyecto) throws DBException{
        EntregaResponse entrega = entregasDB.buscarEntregaSinRevisar(idProyecto);
        
        if(entrega == null) return null;
        
        entrega.setArchivos(entregasDB.buscarArchivos(entrega.getId()));
        entrega.setRechazo(this.buscarRechazoEntrega(entrega.getId()));
        return entrega;
    }
    
    
    public void aceptarEntrega(int idEntrega) throws DBException, CamposVaciosException{
        
        //cambios en proyecto y entregas
        entregasDB.cambiarEstadoEntrega(idEntrega, EstadosEntrega.ACEPTADA.getId());
        int idProyecto = entregasDB.encontrarIdProyectoConIdEntrega(idEntrega);
        proyectosService.cambiarEstadoProyecto(idProyecto, EstadosProyecto.COMPLETADO.getId());
        
        
        Contrato contrato = contratosService.buscarContratoProyectRaw(idEntrega);
        int porcentajeComision = contrato.getPorcentajeComision();
        double montoProyecto = entregasDB.encontrarMontoDelContrato(idEntrega);
        
        
        double pagoFreelancer = montoProyecto - (montoProyecto*porcentajeComision)/100;
        double comisionPlataforma = montoProyecto*porcentajeComision/100;
        this.carterasService.recargarSaldo(contrato.getCuiFreelancer(), pagoFreelancer);
        
        Pago pago = new Pago(comisionPlataforma, pagoFreelancer, contrato.getId());
        this.pagosDB.crear(pago);
        
    }
    
    
    public void rechazarEntrega(RechazoEntrega rechazo) throws CamposVaciosException, DatosMuyLargosException, DBException, ErrorDeLogicaException {
        this.revisarDatosCorrectos(rechazo);
        entregasDB.cambiarEstadoEntrega(rechazo.getIdEntrega(), EstadosEntrega.RECHAZADA.getId());
        
        int idProyecto = entregasDB.encontrarIdProyectoConIdEntrega(rechazo.getIdEntrega());
        
        
        if(idProyecto <0){
            throw new ErrorDeLogicaException(" no encontró el id del proyecto");
        }
        proyectosService.cambiarEstadoProyecto(idProyecto, EstadosProyecto.EN_PROGRESO.getId());
        
        
        rechazosDB.crear(rechazo);
    }

    private RechazoEntrega buscarRechazoEntrega(int idEntrega) throws DBException {
        RechazoEntrega rechazo = rechazosDB.buscarPorId(idEntrega);
        return rechazo;
    }
}
