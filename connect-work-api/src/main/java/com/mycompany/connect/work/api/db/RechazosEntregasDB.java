/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaPorID;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.RechazoEntrega;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class RechazosEntregasDB implements CreacionEntidad<RechazoEntrega>, BusquedaPorID<RechazoEntrega>, ExtraerEntidad<RechazoEntrega> {

    private static final String CREAR = "INSERT INTO rechazo_entrega (motivo, fecha, id_entrega) VALUES (?, ?, ?)";
    private static final String BUSCAR_POR_ENTREGA = "SELECT * FROM rechazo_entrega WHERE id_entrega = ?";
    

    @Override
    public void crear(RechazoEntrega entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {
            ps.setString(1, entidad.getMotivo());
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            ps.setInt(3, entidad.getIdEntrega());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al registrar el rechazo: " + e.getMessage());
        }
    }

    @Override
    public RechazoEntrega buscarPorId(int idSolicitud) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_ENTREGA)) {

            ps.setInt(1, idSolicitud);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar rechazo por solicitud: " + e.getMessage());
        }
        return null;
    }

    @Override
    public RechazoEntrega extraer(ResultSet rs) throws SQLException {
        RechazoEntrega rechazo = new RechazoEntrega();
        rechazo.setId(rs.getInt("id"));
        rechazo.setMotivo(rs.getString("motivo"));
        return rechazo;
    }

    

}
