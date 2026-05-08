/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.CalificacionFreelancer;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edu
 */
public class CalificacionesFreelancerDB {/*
    private static final String INSERTAR_CALIFICACION = 
        "INSERT INTO calificacion_freelancer (cui_freelancer, cantidad_estrellas, fecha_calificacion, comentario) VALUES (?, ?, ?, ?)";
    
    private static final String BUSCAR_POR_CUI = 
        "SELECT id, cui_freelancer, cantidad_estrellas, fecha_calificacion, comentario FROM calificacion_freelancer WHERE cui_freelancer = ?";

    public void insertar(CalificacionFreelancer calificacion) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(INSERTAR_CALIFICACION)) {
            
            ps.setString(1, calificacion.getCuiFreelancer());
            ps.setInt(2, calificacion.get);
            ps.setDate(3, Date.valueOf(calificacion.getFechaCalificacion()));
            ps.setString(4, calificacion.getComentario());
            
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al insertar calificación: " + e.getMessage());
        }
    }

    public List<CalificacionFreelancer> buscarPorCui(String cui) throws DBException {
        List<CalificacionFreelancer> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_CUI)) {
            
            ps.setString(1, cui);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar calificaciones por CUI: " + e.getMessage());
        }
        return lista;
    }

    /*
    @Override
    public CalificacionFreelancer extraer(ResultSet rs) throws SQLException {
        CalificacionFreelancer calificacion = new CalificacionFreelancer(
                rs.getInt("cantidad_estrellas"),
            rs.getString("cui_freelancer"),

            rs.getDate("fecha_calificacion").toLocalDate(),
            rs.getString("comentario")
        );
        calificacion.setId(rs.getInt("id"));
        return calificacion;
    }*/
    
}
