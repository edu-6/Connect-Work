/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.enums.NivelExperiencia;
import com.mycompany.connect.work.api.modelos.enums.Rol;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EnumsDB {

    private static final String OBTENER_ROLES = "select *from rol where nombre != 'Admin'";
    private static final String OBTENER_NIVELES_EXPERIENCIA = "select * from nivel_experiencia";

    public ArrayList<Rol> obtenerRoles() throws DBException {
        ArrayList<Rol> lista = new ArrayList();
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(OBTENER_ROLES)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraerRol(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar roles " + e.getMessage());
        }
        return lista;
    }
    
    public ArrayList<NivelExperiencia> obtenerNivelesExperiencia() throws DBException {
        ArrayList<NivelExperiencia> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(OBTENER_NIVELES_EXPERIENCIA)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(extraerNivel(rs));
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar niveles de experiencia " + e.getMessage());
        }
        return lista;
    }
    
    
    private NivelExperiencia extraerNivel(ResultSet rs) throws SQLException {
        return new NivelExperiencia(rs.getInt("id"), rs.getString("nombre"));
    }

    private Rol extraerRol(ResultSet rs) throws SQLException {
        return new Rol(rs.getString("nombre"), rs.getInt("id"));
    }

}
