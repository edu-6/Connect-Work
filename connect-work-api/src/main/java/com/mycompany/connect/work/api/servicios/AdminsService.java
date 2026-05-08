/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.BuscadorAtributoRepetido;
import com.mycompany.connect.work.api.db.UsuariosBaseDB;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioBase;

/**
 *
 * @author edu
 */
public class AdminsService {
    
    private UsuariosBaseDB db = new UsuariosBaseDB();
    private BuscadorAtributoRepetido df = new BuscadorAtributoRepetido();
    
    public void crearAdmin(UsuarioBase admin) throws DBException, EntidadDuplicadaException {
        if (df.existeAtributoRepetido(admin.getNickname(), db.getEXISTE_NICKNAME())) {
            throw new EntidadDuplicadaException("ya existe el nickname " + admin.getNickname());
        }
        
        db.crear(admin);
    }
    
}
