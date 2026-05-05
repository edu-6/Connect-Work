/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.LoginDB;
import com.mycompany.connect.work.api.dtos.usuariosLogin.UsuarioLoginRequest;
import com.mycompany.connect.work.api.dtos.usuariosLogin.UsuarioLoginResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;

/**
 *
 * @author edu
 */
public class LoginService {
    private LoginDB db = new LoginDB();
    
    public UsuarioLoginResponse loguearUsuario(UsuarioLoginRequest request) throws NotFoundException, DBException{
        UsuarioLoginResponse reponse= db.loguearUsuario(request);
        if(reponse == null){
            throw new NotFoundException("credenciales incorrectas");
        }
        return reponse;
    }
    
    
    
}
