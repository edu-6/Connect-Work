/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.proyectos;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.proyectos.BusquedaProyecto;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ProyectosBusquedaDB implements ExtraerEntidad<ProyectoResponse> {

    private static final String BUSQUEDA_SIMPLE = "select p.id_estado, p.id, p.titulo,"
            + " p.descripcion, p.presupuesto_maximo, p.fecha_publicacion, p.fecha_entrega_deseada,"
            + " c.nombre AS nombre_categoria,"
            + " es.nombre AS nombre_estado,"
            + " us.nombre AS nombre_cliente"
            + " FROM proyecto p"
            + " JOIN categoria c ON c.id = p.id_categoria"
            + " JOIN estado_proyecto es ON es.id = p.id_estado"
            + " JOIN usuario_plataforma up ON up.cui = p.cui_cliente"
            + " JOIN usuario_sistema us ON us.nickname = up.nickname";
    
    private static final String PROYECTO_ABIERTO = "AND p.id_estado = 1 ";

    private static final String FILTRO_HABILIDADES = 
            " JOIN habilidad_categoria h where h.id_categoria  = p.id_categoria AND h.id_habilidad = ? "+ PROYECTO_ABIERTO;
    
    private static final String FILTRO_CREADOR = " where p.cui_cliente = ? ";

    private static final String FILTRO_PRESUPUESTO = " where p.presupuesto_maximo >= ? and p.presupuesto_maximo <= ? " + PROYECTO_ABIERTO;

    private static final String FILTRO_PERIODO = " where p.fecha_publicacion >= ? and p.fecha_publicacion <= ? and p.cui_cliente = ? ";

    private static final String FILTRO_CATEGORIA = " where p.id_categoria = ? " + PROYECTO_ABIERTO;

    private static final String FILTRO_CONTRATO = 
    " JOIN propuesta_proyecto prop ON prop.id_proyecto = p.id"+
    " JOIN contrato contr ON contr.id_propuesta = prop.id where prop.cui_freelancer = ? AND p.id_estado IN (3,4)";


    private static final String BUSQUEDA_POR_ID = BUSQUEDA_SIMPLE + " WHERE p.id = ?";

    // PARA CLIENTE
    private static final String BUSQUeDA_EN_CLIENTE_POR_PERIODO = BUSQUEDA_SIMPLE + FILTRO_PERIODO;

    private static final String BUSQUeDA_EN_CLIENTE_TODO = BUSQUEDA_SIMPLE + FILTRO_CREADOR;

    // PARA FREELANCER
    private static final String BUSQUEDA_POR_PRESUPUESTO = BUSQUEDA_SIMPLE + FILTRO_PRESUPUESTO;

    private static final String BUSQUEDA_POR_CATEGORIA = BUSQUEDA_SIMPLE + FILTRO_CATEGORIA;

    private static final String BUSQUEDA_POR_HABIIDADES = BUSQUEDA_SIMPLE + FILTRO_HABILIDADES;

    // PARA PROYECTOS ACTIVOS FREELANCER
    private static final String BUSQUEDA_CONTRATOS_ACTIVOS = BUSQUEDA_SIMPLE + FILTRO_CONTRATO;

    public ArrayList<ProyectoResponse> buscarEnClientePorPeriodo(BusquedaProyecto busqueda) throws DBException {

        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUeDA_EN_CLIENTE_POR_PERIODO)) {
            ps.setDate(1, Date.valueOf(busqueda.getFechaInicio()));
            ps.setDate(2, Date.valueOf(busqueda.getFechaFin()));
            ps.setString(3, busqueda.getCuiCliente());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }

        } catch (SQLException e) {
            throw new DBException("error al buscar por periodo " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<ProyectoResponse> buscarEnClienteTodo(BusquedaProyecto busqueda) throws DBException {

        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUeDA_EN_CLIENTE_TODO)) {
            ps.setString(1, busqueda.getCuiCliente());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }

        } catch (SQLException e) {
            throw new DBException("error al buscar todos los proyectos " + e.getMessage());
        }

        return lista;
    }

    public ArrayList<ProyectoResponse> buscarPorPresupuesto(BusquedaProyecto busqueda) throws DBException {
        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_PRESUPUESTO)) {
            ps.setDouble(1, busqueda.getMinPresupuesto());
            ps.setDouble(2, busqueda.getMaxiPresupuesto());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar por presupuesto " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ProyectoResponse> buscarPorCategoria(BusquedaProyecto busqueda) throws DBException {

        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_CATEGORIA)) {
            ps.setInt(1, busqueda.getIdCategoria());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar por categoria" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ProyectoResponse> buscarPorHabilidad(BusquedaProyecto busqueda) throws DBException {

        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_HABIIDADES)) {
            ps.setInt(1, busqueda.getIdHabilidad());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar por habilidad" + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ProyectoResponse> buscarContratosActivos(BusquedaProyecto busqueda) throws DBException {

        ArrayList<ProyectoResponse> lista = new ArrayList();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_CONTRATOS_ACTIVOS)) {
            ps.setString(1, busqueda.getCuiFreelancer());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar contratos activos" + e.getMessage());
        }
        return lista;
    }

    public ProyectoResponse buscarResponsePorId(int id) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar el proyecto por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ProyectoResponse extraer(ResultSet rs) throws SQLException {
        ProyectoResponse proyecto = new ProyectoResponse(
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getString("nombre_categoria"),
                rs.getString("nombre_estado"),
                rs.getDouble("presupuesto_maximo"),
                rs.getString("nombre_cliente"),
                rs.getDate("fecha_publicacion").toLocalDate(),
                rs.getDate("fecha_entrega_deseada").toLocalDate(),
                rs.getInt("id"),
                rs.getInt("id_estado")
        );
        return proyecto;
    }

}
