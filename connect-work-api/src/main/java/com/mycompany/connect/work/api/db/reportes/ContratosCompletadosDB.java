/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteContratoCompletado;
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
public class ContratosCompletadosDB {
    
    private static final String QUERY_BASE = 
        "SELECT us.nombre AS cliente, p.titulo, pg.monto, " +
        "COALESCE(cal.cantidad_estrellas, 0) AS estrellas, pg.fecha_pago " +
        "FROM pago_proyecto pg " +
        "JOIN contrato con ON pg.id_contrato = con.id " +
        "JOIN propuesta_proyecto prop ON con.id_propuesta = prop.id " +
        "JOIN proyecto p ON prop.id_proyecto = p.id " +
        "JOIN usuario_plataforma up ON p.cui_cliente = up.cui " +
        "JOIN usuario_sistema us ON up.nickname = us.nickname " +
        "LEFT JOIN calificacion_freelancer cal ON cal.id_proyecto = p.id "+
        "WHERE prop.cui_freelancer = ? AND p.id_estado = 5 "; 

    private static final String FILTRO_PERIODO = " AND pg.fecha_pago >= ? AND pg.fecha_pago <= ? ";
    private static final String ORDER = " ORDER BY pg.fecha_pago DESC";

    public ArrayList<ReporteContratoCompletado> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteContratoCompletado> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(QUERY_BASE + ORDER)) {
            ps.setString(1, request.getCuiUsuario());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte contratos: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteContratoCompletado> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteContratoCompletado> lista = new ArrayList<>();
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
            throw new DBException("Error en reporte contratos periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteContratoCompletado extraer(ResultSet rs) throws SQLException {
        return new ReporteContratoCompletado(
            rs.getString("cliente"),
            rs.getString("titulo"),
            rs.getDouble("monto"),
            rs.getInt("estrellas"),
            rs.getDate("fecha_pago").toLocalDate()
        );
    }
    
}
