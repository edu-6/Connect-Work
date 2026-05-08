/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioBase;
import com.mycompany.connect.work.api.utils.HashUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class UsuariosBaseDB implements CreacionEntidad<UsuarioBase> {
    
    private static final String CREAR = "insert into usuario_sistema (nombre,nickname,activo,contraseña,id_rol)"
            + " values (?,?,?,?,?)";
    
    
    private static final String EXISTE_NICKNAME = "select nickname from usuario_sistema where nickname = ?";

    @Override
    public void crear(UsuarioBase entidad) throws DBException {
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)) {
            
            
          String contraseñaCifrada = HashUtil.sha256(entidad.getContraseña());
            
           ps.setString(1, entidad.getNombre());
           ps.setString(2, entidad.getNickname());
           ps.setBoolean(3, true);
           ps.setString(4, contraseñaCifrada);
           ps.setInt(5, entidad.getIdRol());
           
           ps.executeUpdate();
           
        } catch (SQLException e) {
            throw new DBException("Error al registrar usuario base "+e.getMessage());
        }
    }

    public static String getEXISTE_NICKNAME() {
        return EXISTE_NICKNAME;
    }
    
    
    
    
    
    
    
    
}
