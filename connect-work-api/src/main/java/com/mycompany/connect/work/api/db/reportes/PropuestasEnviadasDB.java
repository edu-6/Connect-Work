/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReportePropuestaEnviada;
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
public class PropuestasEnviadasDB {
    
    private static final String QUERY_BASE = 
    "SELECT p.titulo, prop.presupuesto_ofertado, prop.fecha_creacion, ep.nombre AS estado_final " +
    "FROM propuesta_proyecto prop " +
    "JOIN proyecto p ON prop.id_proyecto = p.id " +
    "JOIN estado_propuesta ep ON prop.id_estado = ep.id " +
    "WHERE prop.cui_freelancer = ? ";

    private static final String FILTRO_PERIODO = " AND prop.fecha_creacion >= ? AND prop.fecha_creacion <= ? ";
    private static final String ORDER = " ORDER BY prop.fecha_creacion DESC";

    public ArrayList<ReportePropuestaEnviada> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReportePropuestaEnviada> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY_BASE + ORDER)) {
            ps.setString(1, request.getCuiUsuario());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte propuestas: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReportePropuestaEnviada> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReportePropuestaEnviada> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY_BASE + FILTRO_PERIODO + ORDER)) {
            ps.setString(1, request.getCuiUsuario());
            ps.setDate(2, Date.valueOf(request.getFechaInicio()));
            ps.setDate(3, Date.valueOf(request.getFechaFinal()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte propuestas periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReportePropuestaEnviada extraer(ResultSet rs) throws SQLException {
        return new ReportePropuestaEnviada(
            rs.getString("titulo"),
            rs.getDouble("presupuesto_ofertado"),
            rs.getString("estado_final"),
            rs.getDate("fecha_creacion").toLocalDate()
        );
    }
    
}
