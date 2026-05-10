/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db.perfiles;

import com.mycompany.connect.work.api.db.ConexionDB;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilPlataformaDTO;
import com.mycompany.connect.work.api.dtos.perfiles.PerfilSimpleDTO;
import com.mycompany.connect.work.api.exceptions.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class PerfilesCompletosDB {

    private static final String BUSCAR_INFO_USUARIOS_BASE_TODOS = "select u.*, r.nombre as rol from usuario_sistema u "
            + " JOIN rol r ON r.id = u.id_rol ";

    private static final String FILTRO_ROL = " where u.id_rol = ?";
    private static final String FILTRO_NICKNAME = " where u.nickname = ?";
    

    //base 
    private static final String BUSCAR_USUARIOS_POR_ROL = BUSCAR_INFO_USUARIOS_BASE_TODOS + FILTRO_ROL;
    private static final String BUSCAR_USUARIO_SIMPLE = BUSCAR_INFO_USUARIOS_BASE_TODOS +FILTRO_NICKNAME;

    // plataforma
    private static final String BUSCAR_INFO_USUARIO_PLATAFORMA = "select u.correo, u.telefono, u.fecha_nacimiento, u.cui from usuario_plataforma u"
            + " JOIN usuario_sistema us ON us.nickname = u.nickname where u.nickname = ?";

    
    public PerfilSimpleDTO buscarUsuarioBase(String nickname) throws DBException{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_USUARIO_SIMPLE)) {
            ps.setString(1, nickname);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioSimple(rs);
                }
            }
        } catch (SQLException e) {
            throw new DBException(" error al  buscar en usuario base " + e.getMessage());
        }
        return null;
    }
    
    
    
    public ArrayList<PerfilSimpleDTO> buscarPerfilesPorRol(int rol) throws DBException{
        ArrayList<PerfilSimpleDTO> lista = new ArrayList();
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_USUARIOS_POR_ROL)) {
            ps.setInt(1, rol);

            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    lista.add(extraerUsuarioSimple(rs));
                }
            }
            return lista;
        } catch (SQLException e) {
            throw new DBException(" error al  buscar usuarios por rol " + e.getMessage());
        }
        
    }
    
    

    public PerfilPlataformaDTO buscarInfoUsuarioPlataforma(String nickname) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_INFO_USUARIO_PLATAFORMA)) {
            ps.setString(1, nickname);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extraerUsuarioPlataforma(rs);
                }
            }
            
        } catch (SQLException e) {
            throw new DBException(" error al  buscar el perfil en plataforma " + e.getMessage());
        }

        return null;
    }

    private PerfilPlataformaDTO extraerUsuarioPlataforma(ResultSet rs) throws SQLException {
        return new PerfilPlataformaDTO(
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getDate("fecha_nacimiento").toLocalDate(),
                rs.getString("cui")
        );
    }
    
    
    
    private PerfilSimpleDTO extraerUsuarioSimple(ResultSet rs) throws SQLException {
        return new PerfilSimpleDTO(
                rs.getString("nickname"),
                rs.getString("nombre"),
                rs.getString("rol"),
                rs.getBoolean("activo")
        );
    }

}
