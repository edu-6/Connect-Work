/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteHistorialProyecto;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.reportes.ReporteRequest;
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
public class HistorialProyectosDB {

    private static final String QUERY_BASE = 
    "SELECT p.titulo, es.nombre AS nombre_estado, p.presupuesto_maximo, " +
            "us.nombre AS nombre_freelancer, p.fecha_publicacion " +
            "FROM proyecto p " +
            "JOIN estado_proyecto es ON es.id = p.id_estado " +
            "LEFT JOIN propuesta_proyecto prop ON prop.id_proyecto = p.id AND prop.id_estado = 3 " + // 3 = Aceptada
            "LEFT JOIN usuario_plataforma up ON up.cui = prop.cui_freelancer " +
            "LEFT JOIN usuario_sistema us ON us.nickname = up.nickname ";

    
    private static final String FILTRO_CLIENTE = " WHERE p.cui_cliente = ? ";

    private static final String FILTRO_PERIODO = " AND p.fecha_publicacion >= ? AND p.fecha_publicacion <= ? ";

    private static final String HISTORIAL_TODO = QUERY_BASE + FILTRO_CLIENTE;
    private static final String HISTORIAL_POR_PERIODO = QUERY_BASE + FILTRO_CLIENTE + FILTRO_PERIODO;

    public ArrayList<ReporteHistorialProyecto> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteHistorialProyecto> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(HISTORIAL_TODO)) {

            ps.setString(1, request.getCuiUsuario());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al obtener historial completo: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteHistorialProyecto> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteHistorialProyecto> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(HISTORIAL_POR_PERIODO)) {

            ps.setString(1, request.getCuiUsuario());
            ps.setDate(2, Date.valueOf(request.getFechaInicio()));
            ps.setDate(3, Date.valueOf(request.getFechaFinal()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al obtener historial por periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteHistorialProyecto extraer(ResultSet rs) throws SQLException {
        return new ReporteHistorialProyecto(
                rs.getString("titulo"),
                rs.getString("nombre_estado"),
                rs.getDouble("presupuesto_maximo"),
                rs.getString("nombre_freelancer"),
                rs.getDate("fecha_publicacion").toLocalDate()
        );
    }

}
