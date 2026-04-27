/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioPlataforma;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class UsuariosPlataformaDB implements CreacionEntidad<UsuarioPlataforma>{
    
    
    private static final String CREAR = "insert into usuario_plataforma"
            + " (nickname,nombre,cui,correo,telefono,perfil_completado,"
            + "fecha_nacimiento,direccion) values (?,?,?,?,?,?,?,?)";
    
    private static final String EXISTE_TELEFONO = "select telefono from usuario_plataforma where telefono = ?";
    private static final String EXISTE_CORREO = "select correo from usuario_plataforma where correo = ?";
    private static final String EXISTE_CUI = "select cui from usuario_plataforma where cui = ?";
    

    @Override
    public void crear(UsuarioPlataforma entidad) throws DBException {
        
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(CREAR)){
            
            ps.setString(1, entidad.getNickname());
            ps.setString(2, entidad.getNombre());
            ps.setString(3, entidad.getCui());
            ps.setString(4, entidad.getCorreo());
            ps.setString(5, entidad.getTelefono());
            ps.setBoolean(6, false);
            ps.setDate(7, Date.valueOf(entidad.getFechaNacimiento()));
            ps.setString(8, entidad.getDireccion());
            
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new DBException("error al registrar usuario-plataforma" +e.getMessage());
        }
        
    }

    public static String getCREAR() {
        return CREAR;
    }

    public static String getEXISTE_TELEFONO() {
        return EXISTE_TELEFONO;
    }

    public static String getEXISTE_CORREO() {
        return EXISTE_CORREO;
    }

    public static String getEXISTE_CUI() {
        return EXISTE_CUI;
    }
    
    
    
}
