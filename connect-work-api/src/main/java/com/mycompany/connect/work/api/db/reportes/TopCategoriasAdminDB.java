/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteTopCategoriaAdmin;
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
public class TopCategoriasAdminDB {
    
    private static final String QUERY_BASE = 
        "SELECT cat.nombre, COUNT(DISTINCT con.id) AS contratos, SUM(pg.comision) AS total_comisiones " +
        "FROM pago_proyecto pg " +
        "JOIN contrato con ON pg.id_contrato = con.id " +
        "JOIN propuesta_proyecto prop ON con.id_propuesta = prop.id " +
        "JOIN proyecto p ON prop.id_proyecto = p.id " +
        "JOIN categoria cat ON p.id_categoria = cat.id ";

    private static final String FILTRO_PERIODO = " WHERE pg.fecha_pago >= ? AND pg.fecha_pago <= ? ";
    private static final String GROUP_LIMIT = " GROUP BY cat.id ORDER BY total_comisiones DESC LIMIT 5";

    public ArrayList<ReporteTopCategoriaAdmin> obtenerTodo(ReporteRequest request) throws DBException {
        return ejecutarConsulta(QUERY_BASE + GROUP_LIMIT, null);
    }

    public ArrayList<ReporteTopCategoriaAdmin> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        return ejecutarConsulta(QUERY_BASE + FILTRO_PERIODO + GROUP_LIMIT, request);
    }

    private ArrayList<ReporteTopCategoriaAdmin> ejecutarConsulta(String sql, ReporteRequest request) throws DBException {
        ArrayList<ReporteTopCategoriaAdmin> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            if (request != null && request.reporteConRango()) {
                ps.setDate(1, Date.valueOf(request.getFechaInicio()));
                ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new ReporteTopCategoriaAdmin(
                        rs.getString("nombre"),
                        rs.getInt("contratos"),
                        rs.getDouble("total_comisiones")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en Top Categorías Admin: " + e.getMessage());
        }
        return lista;
    }
    
}
