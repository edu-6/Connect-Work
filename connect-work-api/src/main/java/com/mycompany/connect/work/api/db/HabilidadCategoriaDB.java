/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.modelos.HabilidadCategoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class HabilidadCategoriaDB implements CreacionEntidad<ArrayList<HabilidadCategoria>> {

    private static final String CREAR = "INSERT INTO habilidad_categoria (id_habilidad, id_categoria) VALUES (?, ?)";
    private static final String ELIMINAR = "DELETE FROM habilidad_categoria WHERE id_habilidad = ? AND id_categoria = ?";
   

    @Override
    public void crear(ArrayList<HabilidadCategoria> entidad) throws DBException {
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

    
    public void eliminar(HabilidadCategoria eliminacion) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ELIMINAR)) {

            ps.setInt(1, eliminacion.getIdHabilidad() );
            ps.setInt(2, eliminacion.getIdCategoria());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new DBException("No se encontró la categoria-habildad ");
            }

        } catch (SQLException e) {
            throw new DBException("Error al eliminar categoria-habilidad: " + e.getMessage());
        }
    }

}
