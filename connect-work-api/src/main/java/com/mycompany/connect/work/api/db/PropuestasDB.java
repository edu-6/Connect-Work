/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.propuestas.PropuestaRequest;
import com.mycompany.connect.work.api.dtos.propuestas.PropuestaResponse;
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
        EliminacionEntidad{

    private static final String CREAR = "INSERT INTO propuesta_proyecto (plazo_entrega, presupuesto_ofertado, carta_presentacion, fecha_creacion, id_proyecto, id_estado, cui_freelancer) VALUES (?, ?, ?, CURRENT_DATE, ?, 1, ?)";
    private static final String ELIMINAR = "DELETE FROM propuesta_proyecto WHERE id = ?";
    private static final String CAMBIAR_ESTADO = "UPDATE propuesta_proyecto SET id_estado = ? WHERE id = ?";
    
    private static final String BUSQUEDA_BASE = "SELECT p.carta_presentacion,"
            + " p.presupuesto_ofertado, p.plazo_entrega,"
            + " p.fecha_creacion, e.nombre AS nombre_estado "
        + "FROM propuesta_proyecto p "
        + "JOIN estado_propuesta e ON e.id = p.id_estado ";

    private static final String BUSQUEDA_POR_ID = BUSQUEDA_BASE + " WHERE p.id = ?";

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


}
