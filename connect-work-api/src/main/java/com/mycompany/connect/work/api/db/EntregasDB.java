/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.entregas.EntregaRequest;
import com.mycompany.connect.work.api.dtos.entregas.EntregaResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionReturnId;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author edu
 */
public class EntregasDB implements CreacionReturnId<EntregaRequest>, ExtraerEntidad<EntregaResponse> {

    private static final String CREAR
            = "INSERT INTO entrega (descripcion, fecha, id_proyecto, id_estado) VALUES (?, CURRENT_DATE, ?, 1)";

    private static final String INSERTAR_ARCHIVO
            = "INSERT INTO archivo_entrega (id_entrega, archivo) VALUES (?, ?)";

    private static final String BUSCAR_ENTREGA_SIN_REVISAR
            = "SELECT e.id, e.descripcion, e.fecha, es.nombre AS estado "
            + "FROM entrega e "
            + "INNER JOIN estado_entrega es ON e.id_estado = es.id "
            + "WHERE e.id_proyecto = ? and id_estado = 1 ";
    
    
    private static final String BUSCAR_HISTORIAL_ENTREGAS
            = "SELECT e.id, e.descripcion, e.fecha, es.nombre AS estado "
            + "FROM entrega e "
            + "INNER JOIN estado_entrega es ON e.id_estado = es.id "
            + "WHERE e.id_proyecto = ? ";

    private static final String BUSCAR_ARCHIVOS
            = "SELECT archivo FROM archivo_entrega WHERE id_entrega = ?";
    
    private static final String HAY_ENTREGA_SIN_REVISAR = "select *from entrega where id_proyecto = ? and id_estado = 1";
    
    
    private static final String CAMBIAR_ESTADO_ENTREGA = "update entrega set id_estado = ? where id = ?";
    
    private static final String ENCONTRAR_ID_PROYECTO_CON_ENTREGA = "select id_proyecto from entrega where id = ?";

    @Override
    public int crear(EntregaRequest e) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getDescripcion());
            ps.setInt(2, e.getIdProyecto());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
            return -1;
        } catch (SQLException ex) {
            throw new DBException("Error al crear entrega: " + ex.getMessage());
        }
    }
    
    public void cambiarEstadoEntrega(int idEntrega, int idEstado) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CAMBIAR_ESTADO_ENTREGA)) {

            ps.setInt(1, idEstado);
            ps.setInt(2, idEntrega);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Error al cambiar estado de entrega: " + e.getMessage());
        }
    }
    
    
    public int encontrarIdProyectoConIdEntrega(int idEntrega) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(ENCONTRAR_ID_PROYECTO_CON_ENTREGA)) {
            ps.setInt(1, idEntrega);
            
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return rs.getInt("id_proyecto");
                }
            }
            
            return -1;

        } catch (SQLException e) {
            throw new DBException("Error al buscar id del proyecto asociado: " + e.getMessage());
        }
    }
    
    
    
    

    public void insertarArchivo(int idEntrega, String archivo) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(INSERTAR_ARCHIVO)) {

            ps.setInt(1, idEntrega);
            ps.setString(2, archivo);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DBException("Error al insertar archivo: " + e.getMessage());
        }
    }

    public EntregaResponse buscarEntregaSinRevisar(int idProyecto) throws DBException {

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_ENTREGA_SIN_REVISAR)) {

            ps.setInt(1, idProyecto);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return this.extraer(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DBException("Error al buscar entrega: " + e.getMessage());
        }
    }
    
    
    public ArrayList<EntregaResponse> buscarHistorialDeEntregas(int idProyecto) throws DBException {

        ArrayList<EntregaResponse> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_HISTORIAL_ENTREGAS)) {
            ps.setInt(1, idProyecto);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(this.extraer(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new DBException("Error al buscar historial de entregas: " + e.getMessage());
        }
    }
    
    
    public boolean existeEntregaSinRevisar(int idProyecto) throws DBException{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(HAY_ENTREGA_SIN_REVISAR)) {
            ps.setInt(1, idProyecto);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DBException("Error al buscar entrega sin revisar: " + e.getMessage());
        }
        
        
    }

    public String[] buscarArchivos(int idEntrega) throws DBException {

        List<String> archivos = new ArrayList<>();

        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_ARCHIVOS)) {

            ps.setInt(1, idEntrega);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    archivos.add(rs.getString("archivo"));
                }
            }
            return archivos.toArray(new String[0]);

        } catch (SQLException e) {
            throw new DBException("Error al buscar archivos: " + e.getMessage());
        }
    }

    @Override
    public EntregaResponse extraer(ResultSet rs) throws SQLException {
        return new EntregaResponse(
                rs.getInt("id"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getDate("fecha").toLocalDate()
        );
    }
}
