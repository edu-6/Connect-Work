/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.calificaciones.CalificacionResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.CalificacionProyecto;
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
public class CalificacionesFreelancerDB implements ExtraerEntidad<CalificacionResponse> {

    private static final String INSERTAR_CALIFICACION
            = "INSERT INTO calificacion_freelancer (cui_freelancer, cantidad_estrellas, fecha_calificacion, comentario, id_proyecto) VALUES (?, ?, ?, ?, ?)";

    private static final String BUSCAR_POR_CUI
            = "SELECT id, cui_freelancer, cantidad_estrellas, fecha_calificacion, comentario FROM calificacion_freelancer WHERE cui_freelancer = ?";

    public void insertar(CalificacionProyecto calificacion) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERTAR_CALIFICACION)) {

            ps.setString(1, calificacion.getCuiFreelancer());
            ps.setInt(2, calificacion.getCantidadEstrellas());
            ps.setDate(3, Date.valueOf(calificacion.getFechaCalificacion()));
            ps.setString(4, calificacion.getComentario());
            ps.setInt(5, calificacion.getIdProyecto());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al insertar calificación: " + e.getMessage());
        }
    }

    public List<CalificacionResponse> buscarPorCui(String cui) throws DBException {
        List<CalificacionResponse> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_CUI)) {

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

    @Override
    public CalificacionResponse extraer(ResultSet rs) throws SQLException {
        return new CalificacionResponse(
                rs.getInt("cantidad_estrellas"),
                rs.getString("comentario"),
                rs.getDate("fecha_calificacion").toLocalDate()
        );
    }

}
