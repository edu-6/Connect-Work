/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.perfiles;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.perfles.PerfilFreelancer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class PerfilFreelancerDB implements CreacionEntidad<PerfilFreelancer>, BusquedaUnitariaString<PerfilFreelancer>,
        ExtraerEntidad<PerfilFreelancer> {

    private static final String CREAR = "INSERT INTO perfil_freelancer (cui_freelancer, biografia, tarifa_hora, id_nivel_experiencia) VALUES (?, ?, ?, ?)";

    private static final String BUSCAR_POR_CUI = "SELECT * FROM perfil_freelancer WHERE cui_freelancer = ?";

    @Override
    public void crear(PerfilFreelancer entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {
            ps.setString(1, entidad.getCuiFreelancer());
            ps.setString(2, entidad.getBiografia());
            ps.setDouble(3, entidad.getTarifaHora());
            ps.setInt(4, entidad.getIdNivelExperiencia());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DBException("Error al crear el perfil de freelancer: " + e.getMessage());
        }
    }

    @Override
    public PerfilFreelancer buscar(String cui) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_CUI)) {
            ps.setString(1, cui);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar perfil freelancer por CUI: " + e.getMessage());
        }
        return null;
    }

    
    @Override
    public PerfilFreelancer extraer(ResultSet rs) throws SQLException {
        return new PerfilFreelancer(
                rs.getString("cui_freelancer"),
                rs.getString("biografia"),
                rs.getDouble("tarifa_hora"),
                rs.getInt("id_nivel_experiencia")
        );
    }

}
