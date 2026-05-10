/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.perfiles;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilClienteDTO;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.perfles.PerfilCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class PerfilClienteDB implements CreacionEntidad<PerfilCliente>, BusquedaUnitariaString<PerfilClienteDTO>,
         ExtraerEntidad<PerfilClienteDTO> {

    private static final String CREAR = "INSERT INTO perfil_cliente (cui_usuario, descripcion, sitio_web, industria) VALUES (?, ?, ?, ?)";

    private static final String BUSCAR_POR_CUI = "SELECT * FROM perfil_cliente WHERE cui_usuario = ?";

    @Override
    public void crear(PerfilCliente entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {
            ps.setString(1, entidad.getCuiUsuario());
            ps.setString(2, entidad.getDescripcion());
            ps.setString(3, entidad.getSitioWeb());
            ps.setString(4, entidad.getIndustria());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al crear el perfil de cliente: " + e.getMessage());
        }
    }

    @Override
    public PerfilClienteDTO buscar(String cui) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_CUI)) {
            ps.setString(1, cui);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar perfil por CUI: " + e.getMessage());
        }
        return null;
    }

    @Override
    public PerfilClienteDTO extraer(ResultSet rs) throws SQLException {
        return new PerfilClienteDTO(
                rs.getString("descripcion"),
                rs.getString("sitio_web"),
                rs.getString("industria")
        );
    }

}
