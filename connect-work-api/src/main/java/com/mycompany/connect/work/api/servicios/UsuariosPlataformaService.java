/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.BuscadorAtributoRepetido;
import com.mycompany.connect.work.api.db.UsuariosBaseDB;
import com.mycompany.connect.work.api.db.UsuariosPlataformaDB;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioPlataforma;

/**
 *
 * @author edu
 */
public class UsuariosPlataformaService extends CrudService {

    private BuscadorAtributoRepetido df = new BuscadorAtributoRepetido();
    private UsuariosBaseDB baseDB = new UsuariosBaseDB();
    private UsuariosPlataformaDB usuariosDB = new UsuariosPlataformaDB();

    public void crear(UsuarioPlataforma entidad) throws DBException, EntidadDuplicadaException {
        
        
        

        if (df.existeAtributoRepetido(entidad.getNickname(), baseDB.getEXISTE_NICKNAME())) {
            throw new EntidadDuplicadaException("ya existe el nickname " + entidad.getNickname());
        }
        
        if (df.existeAtributoRepetido(entidad.getNickname(), usuariosDB.getEXISTE_TELEFONO())) {
            throw new EntidadDuplicadaException("ya existe el telefono " + entidad.getNickname());
        }
        
        
        if (df.existeAtributoRepetido(entidad.getNickname(), usuariosDB.getEXISTE_CUI())) {
            throw new EntidadDuplicadaException("ya existe el correo " + entidad.getCui());
        }
        
        if (df.existeAtributoRepetido(entidad.getNickname(), usuariosDB.getEXISTE_CORREO())) {
            throw new EntidadDuplicadaException("ya existe el correo " + entidad.getCorreo());
        }
        
        

    }

}
