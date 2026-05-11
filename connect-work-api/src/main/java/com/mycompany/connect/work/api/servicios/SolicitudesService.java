/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.CategoriasDB;
import com.mycompany.connect.work.api.db.HabilidadesDB;
import com.mycompany.connect.work.api.db.SolicitudesDB;
import com.mycompany.connect.work.api.dtos.solicitudes.SolicitudRequest;
import com.mycompany.connect.work.api.dtos.solicitudes.SolicitudResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.modelos.Categoria;
import com.mycompany.connect.work.api.modelos.Habilidad;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author edu
 */
public class SolicitudesService {

    private SolicitudesDB db = new SolicitudesDB();
    private CategoriasDB categoriasDB = new CategoriasDB();
    private HabilidadesDB habilidadesDB = new HabilidadesDB();

    // Habilidades
    public void crearSolicitudHabilidad(SolicitudRequest solicitud) throws DBException, EntidadDuplicadaException {
        try {

            Habilidad encontrada = habilidadesDB.buscar(solicitud.getNombre());
            if (encontrada != null) {
                throw new EntidadDuplicadaException("La habilidad con este nombre ya existe.");
            }
            db.crearSolicitudHabilidad(solicitud);
        } catch (SQLException ex) {
            throw new DBException("error al registrar la solicitud habilidad  " + ex.getMessage());
        }
    }

    public void aprobarHabilidad(int id) throws DBException {
        try {
            SolicitudRequest solicitud = db.obtenerPorId("solicitud_habilidad", id);
            
            Habilidad habilidad = new Habilidad(solicitud.getNombre());
            habilidadesDB.crear(habilidad);

            db.cambiarEstado("solicitud_habilidad", id, 3);
            
        } catch (SQLException ex) {
            throw new DBException("error al aprobar la habilidad  " + ex.getMessage());
        }
    }

    public void rechazarHabilidad(int id) throws DBException {
        try {
            db.cambiarEstado("solicitud_habilidad", id, 2);
        } catch (SQLException ex) {
            throw new DBException("error al rechazar habilidad  " + ex.getMessage());
        }
    }

    public List<SolicitudResponse> obtenerMisHabilidades(String cui) throws DBException {
        try {
            return db.listarPorUsuario("solicitud_habilidad", "HABILIDAD", cui);
        } catch (SQLException ex) {
            throw new DBException("error al buscar listado " + ex.getMessage());
        }
    }
    
    
    public List<SolicitudResponse> obtenerHabilidadesEnviadas() throws DBException{
        try {
            return db.listarEnviadas("solicitud_habilidad", "HABILIDAD");
        } catch (SQLException ex) {
             throw new DBException("error al buscar listado " + ex.getMessage());
        }
    }

    // Categorias
    public void crearSolicitudCategoria(SolicitudRequest solicitud) throws DBException, EntidadDuplicadaException {
        try {
            Categoria categoria = categoriasDB.buscar(solicitud.getNombre());
            if (categoria != null) {
                throw new EntidadDuplicadaException("Ya existe la categoria " + categoria.getNombre());
            }
            
            db.crearSolicitudCategoria(solicitud);
        } catch (SQLException ex) {
            throw new DBException("error al registrar la solicitud habilidad  " + ex.getMessage());
        }
    }

    public void aprobarCategoria(int id) throws DBException {
        try {
            
            SolicitudRequest solicitud = db.obtenerPorId("solicitud_categoria", id);
            Categoria categoria = new Categoria(solicitud.getNombre());
            categoriasDB.crear(categoria);
            db.cambiarEstado("solicitud_categoria", id, 3);
            
        } catch (SQLException ex) {
            throw new DBException("error al aprobar la categoria  " + ex.getMessage());
        }
    }

    public void rechazarCategoria(int id) throws DBException {
        try {
            db.cambiarEstado("solicitud_categoria", id, 2);
        } catch (SQLException ex) {
            throw new DBException("error al rechazar categoria  " + ex.getMessage());
        }
    }

    public List<SolicitudResponse> obtenerMisCategorias(String cui) throws DBException {
        try {
            return db.listarPorUsuario("solicitud_categoria", "CATEGORIA", cui);
        } catch (SQLException ex) {
            throw new DBException("error al buscar listado " + ex.getMessage());
        }
    }
    
    
    public List<SolicitudResponse> obtenerCategoriasEnviadas() throws DBException{
        try {
            return db.listarEnviadas("solicitud_categoria", "CATEGORIA");
        } catch (SQLException ex) {
             throw new DBException("error al buscar listado " + ex.getMessage());
        }
    }
    

}
