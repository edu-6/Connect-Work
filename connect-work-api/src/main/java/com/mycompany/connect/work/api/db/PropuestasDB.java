/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.propuestas.PropuestaRequest;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaPorID;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.EliminacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class PropuestasDB implements CreacionEntidad< PropuestaRequest>,
        EliminacionEntidad, BusquedaPorID<PropuestaRequest>, ExtraerEntidad<PropuestaRequest> {

    private static final String CREAR = "INSERT INTO propuesta_proyecto (plazo_entrega, presupuesto_ofertado, carta_presentacion, fecha_creacion, id_proyecto, id_estado, cui_freelancer) VALUES (?, ?, ?, CURRENT_DATE, ?, 1, ?)";
    private static final String ELIMINAR = "DELETE FROM propuesta_proyecto WHERE id = ?";
    private static final String CAMBIAR_ESTADO = "UPDATE propuesta_proyecto SET id_estado = ? WHERE id = ?";
    private static final String EXISTE_PROPUESTA = "SELECT id FROM propuesta_proyecto WHERE id_proyecto = ? AND cui_freelancer = ?";
    
    private static final String ACTUALIZAR_ESTADO_PROPUESTA
            = "UPDATE propuesta_proyecto SET id_estado = 2 WHERE id = ?";
    

    private static final String BUSCAR_POR_ID
            = "SELECT * FROM propuesta_proyecto WHERE id = ?";

    @Override
    public void crear(PropuestaRequest p) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {

            ps.setInt(1, p.getPlazoEntrega());
            ps.setDouble(2, p.getPresupuestoOfertado());
            ps.setString(3, p.getCartaPresentacion());
            ps.setInt(4, p.getIdProyecto());
            ps.setString(5, p.getCuiFreelancer());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al registrar propuesta: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(int idPropuesta) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ELIMINAR)) {

            ps.setInt(1, idPropuesta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al eliminar la propuesta: " + e.getMessage());
        }
    }

    public void cambiarEstado(int idEstado, int idPropuesta) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CAMBIAR_ESTADO)) {

            ps.setInt(1, idEstado);
            ps.setInt(2, idPropuesta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al actualizar estado de propuesta: " + e.getMessage());
        }
    }

    public boolean existePropuesta(int idProyecto, String cuiFreelancer) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EXISTE_PROPUESTA)) {

            ps.setInt(1, idProyecto);
            ps.setString(2, cuiFreelancer);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DBException("Error al verificar existencia de propuesta: " + e.getMessage());
        }
    }

    public void marcarPropuestaComoRechazada(int idPropuesta) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_ESTADO_PROPUESTA)) {

            ps.setInt(1, idPropuesta);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Error al actualizar estado de la propuesta: " + e.getMessage());
        }
    }

    @Override
    public PropuestaRequest buscarPorId(int id) throws DBException {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extraer(rs);
                }

                return null;
            }

        } catch (SQLException e) {
            throw new DBException("Error al buscar propuesta: " + e.getMessage());
        }
    }

    @Override
    public PropuestaRequest extraer(ResultSet rs) throws SQLException {
        return new PropuestaRequest(
                rs.getString("cui_freelancer"),
                rs.getInt("id_proyecto"),
                rs.getString("carta_presentacion"),
                rs.getDouble("presupuesto_ofertado"),
                rs.getInt("plazo_entrega")
        );
    }
}
