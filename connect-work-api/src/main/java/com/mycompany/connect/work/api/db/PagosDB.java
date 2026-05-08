/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.modelos.Pago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class PagosDB implements CreacionEntidad<Pago> {
    
    private static final String CREAR_PAGO = "INSERT INTO pago_proyecto (fecha_pago, monto, comision, id_contrato) VALUES (?, ?, ?, ?)";

    @Override
    public void crear(Pago pago) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(CREAR_PAGO)) {

            ps.setDate(1, java.sql.Date.valueOf(pago.getFechaPago()));
            ps.setDouble(2, pago.getMonto());
            ps.setDouble(3, pago.getComision());
            ps.setInt(4, pago.getIdContrato());
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DBException("Error al registrar el pago: " + e.getMessage());
        }
    }

}
