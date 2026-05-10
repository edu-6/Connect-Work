/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.perfiles;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilFreelancerDTO;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.perfles.HabilidadFreelancer;
import com.mycompany.connect.work.api.modelos.perfles.PerfilFreelancer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PerfilFreelancerDB implements CreacionEntidad<PerfilFreelancer>, BusquedaUnitariaString<PerfilFreelancerDTO>,
        ExtraerEntidad<PerfilFreelancerDTO> {

    private static final String CREAR = "INSERT INTO perfil_freelancer (cui_freelancer, biografia, tarifa_hora, id_nivel_experiencia) VALUES (?, ?, ?, ?)";

    private static final String BUSCAR_POR_CUI = "SELECT p.*, e.nombre as experiencia from "
            + " perfil_freelancer p"
            + " JOIN nivel_experiencia e ON e.id = p.id_nivel_experiencia  WHERE cui_freelancer = ?";
    
    private static final String INSERTAR_HABILIDAD_FREELANCER = "INSERT INTO habilidad_freelancer (cui_freelancer, id_habilidad) VALUES (?, ?)";

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
    public PerfilFreelancerDTO buscar(String cui) throws DBException {
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
    public PerfilFreelancerDTO extraer(ResultSet rs) throws SQLException {
        return new PerfilFreelancerDTO(
                rs.getString("experiencia"),
                rs.getDouble("tarifa_hora"),
                rs.getString("biografia")
        );
    }
    
    
    public void crearHabilidadesFreelancer(ArrayList<HabilidadFreelancer> lista) throws DBException {
        try (Connection conn = ConexionDB.getConnection()) {
            conn.setAutoCommit(false);
            
            try {
                for (HabilidadFreelancer habilidadFreelancer : lista) {
                    agregarHabilidadFreelancer(habilidadFreelancer, conn);
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new DBException("Error al insertar la lista de habilidades: " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new DBException("Error de conexión al insertar habilidades: " + e.getMessage());
        }
    }


    private void agregarHabilidadFreelancer(HabilidadFreelancer habilidad, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(INSERTAR_HABILIDAD_FREELANCER)) {
            ps.setString(1, habilidad.getCuiFreelancer());
            ps.setInt(2, habilidad.getIdHabilidad());
            ps.executeUpdate();
        }
    }

}
