/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.usuarios;

import com.mycompany.connect.work.api.modelos.Entidad;

/**
 *
 * @author edu
 */
public class UsuarioBase extends Entidad {
    protected String nombre;
    protected String nickname;
    protected String contraseña;
    protected boolean activo;
    protected int idRol;

    public UsuarioBase(String nombre,String nickname, String contraseña, boolean activo, int idRol) {
        this.nickname = nickname;
        this.contraseña = contraseña;
        this.activo = activo;
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public UsuarioBase() {
        
    }
    
    

    public String getNickname() {
        return nickname;
    }

    public String getContraseña() {
        return contraseña;
    }

    public boolean isActivo() {
        return activo;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    

    @Override
    public boolean datosCompletos() {
        return true;
   }

    @Override
    public boolean datosTamañoCorrecto() {
        return true;
    }
}
