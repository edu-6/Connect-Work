/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.contratos.ContratoResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaPorID;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import com.mycompany.connect.work.api.modelos.Contrato;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class ContratosDB implements CreacionEntidad<Contrato>,
        BusquedaPorID<Contrato>, ExtraerEntidad<Contrato> {

    private static final String CREAR
            = "INSERT INTO contrato "
            + "(fecha_entrega, porcentaje_comision, fecha_generacion, id_propuesta, cui_freelancer) "
            + "VALUES (?, ?, ?, ?, ?)";
    
    private static final String CANCELAR
            = "DELETE FROM contrato WHERE id = ?";

    private static final String BUSCAR_POR_ID
            = "SELECT * FROM contrato WHERE id = ?";
    
    
    private static final String BUSCAR_CONTRATO_DE_RPOYECTO
            = "SELECT c.id, u.nombre, p.presupuesto_ofertado, c.fecha_entrega "
            + "FROM contrato c "
            + "JOIN propuesta_proyecto p ON c.id_propuesta = p.id "
            + "JOIN usuario_plataforma up ON c.cui_freelancer = up.cui "
            + "JOIN usuario_sistema u ON up.nickname = u.nickname "
            + "WHERE p.id_proyecto = ?";
    
    
    

    
    private static final String BUSCAR_ID_CONTRATO_CON_ID_ENTREGA = "select *from contrato c"
            + " JOIN propuesta_proyecto p ON  c.id_propuesta = p.id"
            + " JOIN entrega e ON e.id_proyecto = p.id_proyecto where e.id = ?";
    
    @Override
    public void crear(Contrato contrato) throws DBException {

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(CREAR)) {

            ps.setDate(1, java.sql.Date.valueOf(contrato.getFechaEntrega()));
            ps.setInt(2, contrato.getPorcentajeComision());
            ps.setDate(3, java.sql.Date.valueOf(contrato.getFechaGeneracion()));
            ps.setInt(4, contrato.getIdPropuesta());
            ps.setString(5, contrato.getCuiFreelancer());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Error al crear contrato: " + e.getMessage());
        }
    }

    public void cancelar(int idContrato) throws DBException {

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(CANCELAR)) {
            
            ps.setInt(1, idContrato);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Error al cancelar contrato: " + e.getMessage());
        }
    }
    
    

    public Contrato buscarContratoSimpleDeProyecto(int idEntrega) throws DBException {

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUSCAR_ID_CONTRATO_CON_ID_ENTREGA)) {
            ps.setInt(1, idEntrega);
            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extraer(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar contrato: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Contrato buscarPorId(int id) throws DBException {

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(BUSCAR_POR_ID)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return extraer(rs);
                }
            }

        } catch (SQLException e) {
            throw new DBException("Error al buscar contrato: " + e.getMessage());
        }

        return null;
    }
    
    
    public ContratoResponse buscarContratoDeProyecto(int idProyecto) throws DBException {
        try (Connection con = ConexionDB.getConnection(); PreparedStatement ps = con.prepareStatement(BUSCAR_CONTRATO_DE_RPOYECTO)) {

            ps.setInt(1, idProyecto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new ContratoResponse(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("presupuesto_ofertado"),
                            rs.getDate("fecha_entrega").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            throw new DBException("Error al obtener el contrato del proyecto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Contrato extraer(ResultSet rs) throws SQLException {

        return new Contrato(
                rs.getInt("porcentaje_comision"),
                rs.getDate("fecha_entrega").toLocalDate(),
                rs.getDate("fecha_generacion").toLocalDate(),
                rs.getString("cui_freelancer"),
                rs.getInt("id_propuesta"),
                rs.getInt("id")
        );
    }
}