/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.solicitudes.SolicitudRequest;
import com.mycompany.connect.work.api.dtos.solicitudes.SolicitudResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edu
 */
public class SolicitudesDB {

    private static final String SQL_LISTAR_POR_USUARIO
            = "SELECT s.id, s.descripcion, s.nombre, e.nombre as estado "
            + "FROM %s s "
            + "JOIN estado_solicitud e ON s.id_estado = e.id "
            + "WHERE s.cui_freelancer = ?";

    public void crearSolicitudHabilidad(SolicitudRequest req) throws SQLException {
        insertarSolicitud("solicitud_habilidad", req);
    }

    public void crearSolicitudCategoria(SolicitudRequest req) throws SQLException {
        insertarSolicitud("solicitud_categoria", req);
    }

    private void insertarSolicitud(String tabla, SolicitudRequest req) throws SQLException {
        String sql = "INSERT INTO " + tabla + " (nombre, descripcion, id_estado, fecha, cui_freelancer) VALUES (?, ?, 1, CURDATE(), ?)";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, req.getNombre());
            ps.setString(2, req.getDescripcion());
            ps.setString(3, req.getCuiUsuario());
            ps.executeUpdate();
        }
    }

    public void cambiarEstado(String tabla, int id, int nuevoEstado) throws SQLException {
        String sql = "UPDATE " + tabla + " SET id_estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nuevoEstado);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public List<SolicitudResponse> listarEnviadas(String tabla, String tipo) throws SQLException {
        List<SolicitudResponse> lista = new ArrayList<>();
        String sql = "SELECT s.id, s.nombre, s.descripcion, e.nombre as estado_nombre FROM " + tabla
                + " s JOIN estado_solicitud e ON s.id_estado = e.id WHERE s.id_estado = 1";

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new SolicitudResponse(
                        tipo,
                        rs.getString("estado_nombre"),
                        rs.getString("nombre"),
                        rs.getInt("id"),
                        rs.getString("descripcion")
                ));
            }
        }
        return lista;
    }

    public SolicitudRequest obtenerPorId(String tabla, int id) throws SQLException {
        String sql = "SELECT nombre, descripcion, cui_freelancer FROM " + tabla + " WHERE id = ?";
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SolicitudRequest(
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getString("cui_freelancer")
                    );
                }
            }
        }
        return null;
    }

    public List<SolicitudResponse> listarPorUsuario(String tabla, String tipoEtiqueta, String cui) throws SQLException {
        List<SolicitudResponse> lista = new ArrayList<>();
        String sql = String.format(SQL_LISTAR_POR_USUARIO, tabla);
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cui);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new SolicitudResponse(
                            tipoEtiqueta,
                            rs.getString("estado"),
                            rs.getString("nombre"),
                            rs.getInt("id"),
                            rs.getString("descripcion")
                    ));
                }
            }
        }
        return lista;
    }

}
