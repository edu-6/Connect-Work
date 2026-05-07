/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.CarteraDigital;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class CarteraDigitalDB implements ExtraerEntidad<CarteraDigital> {
    
    
    private static final String CREAR_CARTERA = "INSERT INTO cartera_virtual (cui_cliente, saldo) VALUES (?, 0.0)";
    private static final String OBTENER_CARTERA = "SELECT * FROM cartera_virtual WHERE cui_cliente = ?";
    private static final String ACTUALIZAR_SALDO = "UPDATE cartera_virtual SET saldo = saldo + ? WHERE cui_cliente = ?";
    private static final String REGISTRAR_RECARGO = "INSERT INTO recargo_tarjeta (cui_cliente, monto, fecha) VALUES (?, ?, ?)";
    
    private static final String RESTAR_SALDO = "UPDATE cartera_virtual SET saldo = saldo - ? WHERE cui_cliente = ?";

    
    public void crearCartera(String cuiCliente) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(CREAR_CARTERA)) {
            ps.setString(1, cuiCliente);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al crear cartera virtual: " + e.getMessage());
        }
    }
    
    
    public void restarSaldo(String cuiCliente, double cantidad) throws DBException{
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(RESTAR_SALDO)) {
            ps.setDouble(1, cantidad);
            ps.setString(2, cuiCliente);
            ps.executeUpdate();
        } catch (SQLException e) {
          throw new DBException("Error al descontar saldo : " + e.getMessage());
        }
    }

    public void recargar(String cuiCliente, double monto) throws DBException {
        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psSaldo = conn.prepareStatement(ACTUALIZAR_SALDO)) {
                psSaldo.setDouble(1, monto);
                psSaldo.setString(2, cuiCliente);
                psSaldo.executeUpdate();
            }

            
            try (PreparedStatement psHistorial = conn.prepareStatement(REGISTRAR_RECARGO)) {
                psHistorial.setString(1, cuiCliente);
                psHistorial.setDouble(2, monto);
                psHistorial.setDate(3, new java.sql.Date(System.currentTimeMillis()));
                psHistorial.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) {}
            throw new DBException("Error en la recarga: " + e.getMessage());
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }

    public CarteraDigital obtenerCartera(String cuiCliente) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(OBTENER_CARTERA)) {
            ps.setString(1, cuiCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al consultar saldo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public CarteraDigital extraer(ResultSet rs) throws SQLException { 
        CarteraDigital cartera = new CarteraDigital();
        cartera.setCuiCliente(rs.getString("cui_cliente"));
        cartera.setSaldo(rs.getDouble("saldo"));
        return cartera;
    }
    
}
