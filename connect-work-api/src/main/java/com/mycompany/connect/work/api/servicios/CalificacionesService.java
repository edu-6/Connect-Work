/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.EntregasDB;
import com.mycompany.connect.work.api.db.PagosDB;
import com.mycompany.connect.work.api.db.RechazosEntregasDB;
import com.mycompany.connect.work.api.dtos.calificaciones.CalificacionRequest;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.CalificacionFreelancer;
import com.mycompany.connect.work.api.modelos.Contrato;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class CalificacionesService {
    private ProyectosService proyectosService = new ProyectosService();
    private EntregasDB entregasDB = new EntregasDB();
    private RechazosEntregasDB rechazosDB = new RechazosEntregasDB();
    private ContratosService contratosService = new ContratosService();
    private PagosDB pagosDB = new PagosDB();
    private CarteraDigitalService carterasService = new CarteraDigitalService();
    
    
    public void crearCalificacion(CalificacionRequest calificacion) throws DBException{
        
        int idEntrega = calificacion.getIdEntrega();
        Contrato contrato = contratosService.buscarContratoProyectRaw(idEntrega);
        
        String idFreelancer = contrato.getCuiFreelancer();
        
        /*
        CalificacionFreelancer nueva = new CalificacionFreelancer(
            LocalDate.now(),
            calificacion.getComentario(),
            idFreelancer
        );*/
        
        
        
         
         
        
    }
    
}
