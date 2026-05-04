/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.propuestas.PropuestaResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaPorID;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class BusquedaPropuestasDB implements BusquedaPorID<PropuestaResponse>, ExtraerEntidad<PropuestaResponse> {

    private static final String BUSQUEDA_BASE = "SELECT p.carta_presentacion,"
            + " p.presupuesto_ofertado, p.plazo_entrega,"
            + " p.fecha_creacion, e.nombre AS nombre_estado "
            + "FROM propuesta_proyecto p "
            + "JOIN estado_propuesta e ON e.id = p.id_estado ";

    private static final String BUSQUEDA_POR_ID = BUSQUEDA_BASE + " WHERE p.id = ?";
    private static final String BUSQUEDA_POR_PROYECTO = BUSQUEDA_BASE + " WHERE p.id_proyecto = ?";
    private static final String BUSQUEDA_FREELANCER_PROYECTO = BUSQUEDA_BASE + " WHERE p.id_proyecto = ? AND p.cui_freelancer = ?";

    @Override
    public PropuestaResponse buscarPorId(int id) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar la propuesta por ID: " + e.getMessage());
        }
        return null;
    }

    public ArrayList<PropuestaResponse> buscarPorProyecto(int idProyecto) throws DBException {
        ArrayList<PropuestaResponse> propuestas = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_POR_PROYECTO)) {

            ps.setInt(1, idProyecto);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    propuestas.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al listar propuestas del proyecto: " + e.getMessage());
        }
        return propuestas;
    }

    public PropuestaResponse buscarPropuestaEnProyecto(int idProyecto, String cuiFreelancer) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSQUEDA_FREELANCER_PROYECTO)) {

            ps.setInt(1, idProyecto);
            ps.setString(2, cuiFreelancer);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar propuesta del freelancer: " + e.getMessage());
        }
        return null;
    }

    @Override
    public PropuestaResponse extraer(ResultSet rs) throws SQLException {
        return new PropuestaResponse(
                rs.getString("carta_presentacion"),
                rs.getDouble("presupuesto_ofertado"),
                rs.getInt("plazo_entrega"),
                rs.getDate("fecha_creacion").toLocalDate(),
                rs.getString("nombre_estado")
        );
    }

}
