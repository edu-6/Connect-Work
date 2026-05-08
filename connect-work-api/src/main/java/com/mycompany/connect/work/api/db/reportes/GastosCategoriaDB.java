/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteGastoCategoria;
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
public class GastosCategoriaDB {
    
    
    private static final String QUERY_BASE = 
        "SELECT c.nombre AS categoria, SUM(pg.monto) AS total " +
        "FROM pago_proyecto pg " +
        "JOIN contrato con ON pg.id_contrato = con.id " +
        "JOIN propuesta_proyecto prop ON con.id_propuesta = prop.id " +
        "JOIN proyecto p ON prop.id_proyecto = p.id " +
        "JOIN categoria c ON p.id_categoria = c.id " +
        "WHERE p.cui_cliente = ? ";

    private static final String GROUP_BY = " GROUP BY c.nombre ORDER BY total DESC";

    private static final String FILTRO_PERIODO = " AND pg.fecha_pago >= ? AND pg.fecha_pago <= ? ";

    public ArrayList<ReporteGastoCategoria> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteGastoCategoria> lista = new ArrayList<>();
        String sql = QUERY_BASE + GROUP_BY;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getCuiUsuario());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte de gasto por categoría: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteGastoCategoria> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteGastoCategoria> lista = new ArrayList<>();
        String sql = QUERY_BASE + FILTRO_PERIODO + GROUP_BY;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getCuiUsuario());
            ps.setDate(2, Date.valueOf(request.getFechaInicio()));
            ps.setDate(3, Date.valueOf(request.getFechaFinal()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte de gasto por categoría periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteGastoCategoria extraer(ResultSet rs) throws SQLException {
        return new ReporteGastoCategoria(
            rs.getString("categoria"),
            rs.getDouble("total")
        );
    }
    
}
