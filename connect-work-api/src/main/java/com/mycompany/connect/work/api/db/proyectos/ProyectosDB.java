/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.proyectos;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaPorID;
import com.mycompany.connect.work.api.interfaces.CreacionReturnId;
import com.mycompany.connect.work.api.interfaces.EdicionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.enums.EstadosProyecto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class ProyectosDB implements CreacionReturnId<ProyectoRequest>,
        EdicionEntidad<ProyectoRequest>,
        BusquedaPorID<ProyectoRequest>,
        ExtraerEntidad<ProyectoRequest> {

    private static final String CREAR = "INSERT INTO proyecto (titulo, descripcion, presupuesto_maximo, "
            + "fecha_publicacion, fecha_entrega_deseada, cui_cliente, id_estado, id_categoria) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String EDITAR = "UPDATE proyecto SET titulo = ?, descripcion = ?, id_categoria = ?, "
            + "presupuesto_maximo = ?, fecha_entrega_deseada = ? WHERE id = ?";

    private static final String BUSCAR_PROYECTO_REPETIDO = "select id from proyecto where titulo = ? and cui_cliente = ?";

    private static final String BUSCAR_POR_ID = "SELECT * FROM proyecto WHERE id = ?";

    @Override
    public int crear(ProyectoRequest entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setDouble(3, entidad.getPresupuestoMaximo());
            ps.setDate(4, Date.valueOf(LocalDate.now()));
            ps.setDate(5, Date.valueOf(entidad.getFechaEntregaDeseada()));
            ps.setString(6, entidad.getCuiCliente());

            ps.setInt(7, EstadosProyecto.ABIERTO.getId());
            ps.setInt(8, entidad.getIdCategoria());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    entidad.setId(idGenerado);
                    return idGenerado;
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al registrar el proyecto: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public void editar(ProyectoRequest entidad) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(EDITAR)) {

            ps.setString(1, entidad.getNombre());
            ps.setString(2, entidad.getDescripcion());
            ps.setInt(3, entidad.getIdCategoria());
            ps.setDouble(4, entidad.getPresupuestoMaximo());
            ps.setDate(5, Date.valueOf(entidad.getFechaEntregaDeseada()));
            ps.setInt(6, entidad.getId());

            int filas = ps.executeUpdate();
            if (filas == 0) {
                throw new DBException("No se encontró el proyecto con ID: " + entidad.getId());
            }
        } catch (SQLException e) {
            throw new DBException("Error al editar el proyecto: " + e.getMessage());
        }
    }

    public int existeProyectoRepetido(String cuiCliente, String titulo) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_PROYECTO_REPETIDO)) {

            ps.setString(1, titulo);
            ps.setString(2, cuiCliente);

            try (ResultSet res = ps.executeQuery()) {
                if (res.next()) {
                    return res.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al editar el proyecto: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public ProyectoRequest buscarPorId(int id) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_ID)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException | NumberFormatException e) {
            throw new DBException("Error al buscar proyecto por ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public ProyectoRequest extraer(ResultSet rs) throws SQLException {
        return new ProyectoRequest(
                rs.getString("titulo"),
                rs.getString("descripcion"),
                rs.getInt("id_categoria"),
                rs.getInt("id"),
                rs.getDouble("presupuesto_maximo"),
                rs.getString("cui_cliente"),
                rs.getDate("fecha_entrega_deseada").toLocalDate()
        );
    }
}
