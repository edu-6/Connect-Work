/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.usuarios;

/**
 *
 * @author edu
 */
public class UsuarioBase {
    protected String nickname;
    protected String contraseña;
    protected boolean activo;
    protected int idRol;

    public UsuarioBase(String nickname, String contraseña, boolean activo, int idRol) {
        this.nickname = nickname;
        this.contraseña = contraseña;
        this.activo = activo;
        this.idRol = idRol;
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
}
