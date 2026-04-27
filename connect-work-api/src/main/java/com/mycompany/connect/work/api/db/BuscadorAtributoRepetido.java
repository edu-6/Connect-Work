/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public class BuscadorAtributoRepetido {
    
    /**
     * Para buscar un atributo repetido en formato string
     * @param atributo
     * @param sql
     * @return
     * @throws DBException 
     */
    public boolean existeAtributoRepetido(String atributo, String sql) throws DBException{
        try (Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, atributo);
            ResultSet rs =  ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DBException("Falló al buscar atributo repetido" +e.getMessage());
        }
    }
    
}
