/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.EliminacionEntidad;
import com.mycompany.connect.work.api.modelos.HabilidadCategoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class HabilidadCategoriaDB implements CreacionEntidad<HabilidadCategoria []>, EliminacionEntidad {

    private static final String CREAR = "INSERT INTO habilidad_categoria (id_habilidad, id_categoria) VALUES (?, ?)";
    private static final String ELIMINAR = "DELETE FROM habilidad_categoria WHERE id = ?";

    @Override
    public void crear(HabilidadCategoria[] entidad) throws DBException {
        for (HabilidadCategoria habilidad : entidad) {
            try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {

                ps.setInt(1, habilidad.getIdHabilidad());
                ps.setInt(2, habilidad.getIdCategoria());

                ps.executeUpdate();

            } catch (SQLException e) {
                throw new DBException("Error al registrar la relación Habilidad-Categoría: " + e.getMessage());
            }
        }

    }

    @Override
    public void eliminar(int id) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ELIMINAR)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DBException("No se encontró la categoria-habildad: " + id);
            }

        } catch (SQLException e) {
            throw new DBException("Error al eliminar categoria-habilidad: " + e.getMessage());
        }
    }

}
