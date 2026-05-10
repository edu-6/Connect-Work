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

/**
 *
 * @author edu
 */
public class PerfilesCompletosDB {

    private static final String BUSCAR_INFO_USUARIOS_BASE = "select u.*, r.nombre as rol from usuario_sistema"
            + " JOIN rol r ON rol.id = u.id_rol ";

    private static final String FILTRO_ROL = " where u.id_rol = ?";
    private static final String FILTRO_NICKNAME = " where u.nickname = ?";

    //base 
    private static final String BUSCAR_USUARIOS_POR_ROL = BUSCAR_INFO_USUARIOS_BASE + FILTRO_ROL;
    private static final String BUSCAR_USUARIO_POR_NICKNAME = BUSCAR_INFO_USUARIOS_BASE + FILTRO_NICKNAME;

    // plataforma
    private static final String BUSCAR_INFO_USUARIO_PLATAFORMA = "select correo, telefono, fecha_nacimiento from usuario_plataforma where cui = ?";
    
    
    public PerfilSimpleDTO buscarUsuarioBase(String nickname)

    public PerfilPlataformaDTO buscarInfoUsuarioPlataforma(String cui) throws DBException {
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_INFO_USUARIO_PLATAFORMA)) {
            ps.setString(1, cui);

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
                rs.getDate("fecha_nacimiento").toLocalDate()
        );
    }

}
