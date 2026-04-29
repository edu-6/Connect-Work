/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.dtos.UsuarioLoginRequest;
import com.mycompany.connect.work.api.dtos.UsuarioLoginResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.ExtraerEntidad;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class LoginDB implements ExtraerEntidad<UsuarioLoginResponse> {

    private static final String BUSCAR_USUARIO = "select us.nombre AS nombre_usuario,"
            + "us.nickname, r.nombre AS nombre_rol from usuario_sistema us"
            + " JOIN rol as r ON r.id = us.id_rol where us.nickname = ? AND us.contraseña = ?";

    private static final String BUSCAR_CUI = "select cui from usuario_plataforma where nickname = ?";
    
    
    private static final String BUSCAR_ESTADO_PERFIL = "select perfil_completado from usuario_plataforma where nickname = ?";

    public UsuarioLoginResponse loguearUsuario(UsuarioLoginRequest request) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_USUARIO)) {
            ps.setString(1, request.getNickname());
            ps.setString(2, request.getContraseña());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    UsuarioLoginResponse response = extraer(rs);
                    if(!response.getRol().equals("Admin")){
                        // añadir cui
                        response.setCui(buscarCui(response.getNickname()));
                        
                        // añadir estado del perfil( completado/ pendiente )
                        response.setPerfilCompletado(buscarEstadoPerfil(response.getNickname()));
                    }
                    
                    return response;
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar usuario " + e.getMessage());
        }

        return null;
    }

    @Override
    public UsuarioLoginResponse extraer(ResultSet rs) throws SQLException {
        return new UsuarioLoginResponse(
                rs.getString("nombre_usuario"),
                rs.getString("nickname"),
                rs.getString("nombre_rol")
        );
    }

    private String buscarCui(String nickanme) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_CUI)) {
            ps.setString(1, nickanme);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("cui");
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar usuario " + e.getMessage());
        }

        return null;

    }
    
    
    private boolean buscarEstadoPerfil(String nickname) throws DBException{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_ESTADO_PERFIL)) {
            ps.setString(1, nickname);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("perfil_completado");
                }
            }
        } catch (SQLException e) {
            throw new DBException("error al buscar usuario " + e.getMessage());
        }
        return false;
        
    }

}
