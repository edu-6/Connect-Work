/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos;

/**
 *
 * @author edu
 */
public class UsuarioLoginResponse {
    
    private String nombre;
    private String cui;
    private String rol;
    private String token;
    private String nickname;

    public UsuarioLoginResponse(String nombre,String nickname, String rol) {
        this.rol = rol;
        this.nombre = nombre;
        this.nickname = nickname;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getNickname() {
        return nickname;
    }

    public String getRol() {
        return rol;
    }
    
    
    
    
    
    
    
    
}
