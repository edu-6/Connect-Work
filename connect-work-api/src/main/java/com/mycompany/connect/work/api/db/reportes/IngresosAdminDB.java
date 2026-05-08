/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteIngresosAdmin;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.reportes.ReporteRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class IngresosAdminDB {

    private static final String QUERY_BASE
            = "SELECT COUNT(DISTINCT id_contrato) AS total_contratos, SUM(comision) AS gran_total "
            + "FROM pago_proyecto";

    private static final String FILTRO_PERIODO = " WHERE fecha_pago >= ? AND fecha_pago <= ?";

    public ReporteIngresosAdmin obtenerReporte(ReporteRequest request) throws DBException {
        String sql = request.reporteConRango() ? QUERY_BASE + FILTRO_PERIODO : QUERY_BASE;

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            if (request.reporteConRango()) {
                ps.setDate(1, Date.valueOf(request.getFechaInicio()));
                ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ReporteIngresosAdmin(
                            rs.getInt("total_contratos"),
                            rs.getDouble("gran_total")
                    );
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en reporte de ingresos: " + e.getMessage());
        }
        return new ReporteIngresosAdmin(0, 0.0);
    }
}
