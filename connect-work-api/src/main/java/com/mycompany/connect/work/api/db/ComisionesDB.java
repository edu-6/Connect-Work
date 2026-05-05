/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.comisiones.Comision;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.CambioComision;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class ComisionesDB implements ExtraerEntidad<Comision> {
    
    private static final String OBTENER_COMISION = "SELECT porcentaje_comision FROM tabla_configuracion WHERE id = 1";
    private static final String ACTUALIZAR_COMISION = "UPDATE tabla_configuracion SET porcentaje_comision = ? WHERE id = 1";
    private static final String INSERTAR_HISTORIAL = "INSERT INTO cambio_comision (fecha, porcentaje) VALUES (?, ?)";
    
    public void registrarCambio(CambioComision cambio) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERTAR_HISTORIAL)) {
            ps.setDate(1, Date.valueOf(cambio.getFecha()));
            ps.setInt(2, cambio.getPorcentaje());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al registrar en el historial: " + e.getMessage());
        }
    }
    
    public Comision obtener() throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(OBTENER_COMISION); ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return extraer(rs);
            }
        } catch (SQLException e) {
            throw new DBException("Error al obtener la comisión: " + e.getMessage());
        }
        return null;
    }
    
    public void actualizar(Comision comision) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ACTUALIZAR_COMISION)) {
            ps.setInt(1, comision.getPorcentajeComision());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al actualizar la comisión: " + e.getMessage());
        }
    }
    
    @Override
    public Comision extraer(ResultSet rs) throws SQLException {
        return new Comision(rs.getInt("porcentaje_comision"));
    }
    
}
