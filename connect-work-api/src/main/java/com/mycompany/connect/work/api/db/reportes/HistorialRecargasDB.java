/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteRecarga;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.reportes.ReporteRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class HistorialRecargasDB {

    private static final String HISTORIAL_RECARGAS_TOTAL
            = "SELECT monto, fecha FROM recargo_tarjeta WHERE cui_cliente = ? ORDER BY fecha DESC";

    public ArrayList<ReporteRecarga> obtenerReporteGlobal(ReporteRequest request) throws DBException {
        ArrayList<ReporteRecarga> lista = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(HISTORIAL_RECARGAS_TOTAL)) {

            ps.setString(1, request.getCuiUsuario());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraer(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al obtener el historial de recargos: " + e.getMessage());
        }
        return lista;
    }

    private ReporteRecarga extraer(ResultSet rs) throws SQLException {
        return new ReporteRecarga(
                rs.getDouble("monto"),
                rs.getDate("fecha").toLocalDate()
        );
    }

}
