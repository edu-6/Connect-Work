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
public class VerificadorDeHabilidadesFreelancer {
    
    private static final String BUSCAR_UNA_HABILIDAD_COMUN = "SELECT h.id AS id from habilidad_freelancer h"
            + " JOIN habilidad_categoria hc ON hc.id_habilidad = h.id_habilidad "
            + " JOIN proyecto p ON p.id_categoria = hc.id_categoria where p.id = ? and h.cui_freelancer = ? ";
    
    
    public boolean tieneAlMenosUnaHabilidadRequerida(int idProecto,String cuiFreelancer) throws DBException{
        
        try(Connection conn = ConexionDB.getConnection(); PreparedStatement ps = conn.prepareStatement(BUSCAR_UNA_HABILIDAD_COMUN)) {
            ps.setInt(1, idProecto);
            ps.setString(2, cuiFreelancer);
            
            try(ResultSet rs = ps.executeQuery()){
                return rs.next();
            }
        } catch (SQLException e) {
            throw new DBException("Erro al verificar las habilidades del freelancer  "+ e.getMessage());
        }
    }
    
}
