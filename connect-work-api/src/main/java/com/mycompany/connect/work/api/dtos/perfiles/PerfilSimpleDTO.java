/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.perfiles;

/**
 *
 * @author edu
 */
public class PerfilSimpleDTO {
    
    protected String nickname;
    protected String nombre;
    protected String rol;
    protected boolean activo;

    public PerfilSimpleDTO(String nickname, String nombre, String rol, boolean activo) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.rol = rol;
        this.activo = activo;
    }

    public String getNickname() {
        return nickname;
    }
    
    
    
    
    
    
    
    
}
