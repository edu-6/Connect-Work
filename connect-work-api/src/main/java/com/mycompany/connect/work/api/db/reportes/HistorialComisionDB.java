/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.reportes;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteHistorialComision;
import com.mycompany.connect.work.api.exceptions.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author edu
 */
public class HistorialComisionDB {
    
    private static final String QUERY = 
        "SELECT porcentaje, fecha FROM cambio_comision ORDER BY fecha ASC";

    public ArrayList<ReporteHistorialComision> obtenerTodo() throws DBException {
        ArrayList<ReporteHistorialComision> lista = new ArrayList<>();
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(QUERY);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new ReporteHistorialComision(
                    rs.getDouble("porcentaje"),
                    rs.getDate("fecha").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            throw new DBException("Error en historial comisiones: " + e.getMessage());
        }
        return lista;
    }
    
}
