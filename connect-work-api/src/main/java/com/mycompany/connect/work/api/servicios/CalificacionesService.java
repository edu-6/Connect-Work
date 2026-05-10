/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;
import com.mycompany.connect.work.api.db.CalificacionesFreelancerDB;
import com.mycompany.connect.work.api.db.EntregasDB;
import com.mycompany.connect.work.api.dtos.calificaciones.CalificacionRequest;
import com.mycompany.connect.work.api.dtos.calificaciones.CalificacionResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.CalificacionProyecto;
import com.mycompany.connect.work.api.modelos.Contrato;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class CalificacionesService {
    private ContratosService contratosService = new ContratosService();
    private CalificacionesFreelancerDB db = new CalificacionesFreelancerDB();
    private EntregasDB entregas = new EntregasDB();
    
    
    public void crearCalificacion(CalificacionRequest calificacion) throws DBException{
        
        int idEntrega = calificacion.getIdEntrega();
        Contrato contrato = contratosService.buscarContratoProyectRaw(idEntrega);
        
        String idFreelancer = contrato.getCuiFreelancer();
       
        int idProyecto = entregas.encontrarIdProyectoConIdEntrega(idEntrega);
        
        CalificacionProyecto nueva = new CalificacionProyecto(
            calificacion.getCantidadEstrellas(),
            LocalDate.now(),
            calificacion.getComentario(),
            idFreelancer,
            idProyecto
        );
   
        db.insertar(nueva);
       
    }
    
    
    public ArrayList<CalificacionResponse> buscarCalificacionesFreelancer(String cuiFreelancer) throws DBException{
        return db.buscarPorCui(cuiFreelancer);
    }
    
    
    public double buscarPromedioCalificaciones(String cuiFreelancer) throws DBException{
        return db.buscarPromedioCalificacion(cuiFreelancer);
    }
    
}
