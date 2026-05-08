/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteTopCategoria;
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
public class TopCategoriasDB {
    
    private static final String QUERY_BASE = 
        "SELECT cat.nombre AS categoria, COUNT(con.id) AS cantidad, SUM(pg.monto) AS ingresos " +
        "FROM pago_proyecto pg " +
        "JOIN contrato con ON pg.id_contrato = con.id " +
        "JOIN propuesta_proyecto prop ON con.id_propuesta = prop.id " +
        "JOIN proyecto p ON prop.id_proyecto = p.id " +
        "JOIN categoria cat ON p.id_categoria = cat.id " +
        "WHERE prop.cui_freelancer = ? ";

    private static final String GROUP_AND_LIMIT = 
        " GROUP BY cat.id ORDER BY ingresos DESC LIMIT 5";

    private static final String FILTRO_PERIODO = " AND pg.fecha_pago >= ? AND pg.fecha_pago <= ? ";

    public ArrayList<ReporteTopCategoria> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteTopCategoria> lista = new ArrayList<>();
        String sql = QUERY_BASE + GROUP_AND_LIMIT;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, request.getCuiUsuario());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en Top 5 Categorías: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteTopCategoria> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteTopCategoria> lista = new ArrayList<>();
        String sql = QUERY_BASE + FILTRO_PERIODO + GROUP_AND_LIMIT;

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
            throw new DBException("Error en Top 5 Categorías Periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteTopCategoria extraer(ResultSet rs) throws SQLException {
        return new ReporteTopCategoria(
            rs.getString("categoria"),
            rs.getInt("cantidad"),
            rs.getDouble("ingresos")
        );
    }
    
}
