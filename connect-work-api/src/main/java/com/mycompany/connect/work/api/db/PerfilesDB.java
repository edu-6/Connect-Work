/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.db;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.interfaces.BusquedaUnitariaString;
import com.mycompany.connect.work.api.interfaces.CreacionEntidad;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioPlataforma;

/**
 *
 * @author edu
 */
public class PerfilesDB implements CreacionEntidad<UsuarioPlataforma>, BusquedaUnitariaString<UsuarioPlataforma>{
    
    private static final String CREAR = "";

    @Override
    public void crear(UsuarioPlataforma entidad) throws DBException {
        
    }

    @Override
    public UsuarioPlataforma buscar(String nombre) throws DBException {
       return null;
    }
    
}
