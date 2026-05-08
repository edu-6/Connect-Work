/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteTopFreelancer;
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
public class TopFreelancersDB {
    
    private static final String QUERY_BASE = 
        "SELECT us.nombre AS freelancer, COUNT(DISTINCT con.id) AS contratos, " +
        "SUM(pg.monto) AS total_freelancer, SUM(pg.comision) AS total_admin " +
        "FROM pago_proyecto pg " +
        "JOIN contrato con ON pg.id_contrato = con.id " +
        "JOIN propuesta_proyecto prop ON con.id_propuesta = prop.id " +
        "JOIN usuario_plataforma up ON prop.cui_freelancer = up.cui " +
        "JOIN usuario_sistema us ON up.nickname = us.nickname ";

    private static final String FILTRO_PERIODO = " WHERE pg.fecha_pago >= ? AND pg.fecha_pago <= ? ";
    
    private static final String GROUP_BY_LIMIT = " GROUP BY up.cui ORDER BY total_freelancer DESC LIMIT 5";

    public ArrayList<ReporteTopFreelancer> obtenerTodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteTopFreelancer> lista = new ArrayList<>();
        String sql = QUERY_BASE + GROUP_BY_LIMIT;
        
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en Top Freelancers: " + e.getMessage());
        }
        return lista;
    }

    public ArrayList<ReporteTopFreelancer> obtenerPorPeriodo(ReporteRequest request) throws DBException {
        ArrayList<ReporteTopFreelancer> lista = new ArrayList<>();
        String sql = QUERY_BASE + FILTRO_PERIODO + GROUP_BY_LIMIT;

        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(request.getFechaInicio()));
            ps.setDate(2, Date.valueOf(request.getFechaFinal()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error en Top Freelancers Periodo: " + e.getMessage());
        }
        return lista;
    }

    private ReporteTopFreelancer extraer(ResultSet rs) throws SQLException {
        return new ReporteTopFreelancer(
            rs.getString("freelancer"),
            rs.getInt("contratos"),
            rs.getDouble("total_freelancer"),
            rs.getDouble("total_admin")
        );
    }
    
}
