/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.modelos.usuarios;

import java.time.LocalDate;

/**
 *
 * @author edu
 */
public class UsuarioPlataforma extends UsuarioBase {
    private String cui;
    private String correo;
    private String nombre;
    private String telefono;
    private String direccion;
    private LocalDate fechaNacimiento;
    private boolean perfilCompletado;

    public UsuarioPlataforma(String cui, String correo, String nombre,
            String telefono, String direccion, LocalDate fechaNacimiento,
            boolean perfilCompletado, String nickname, String contraseña,
            boolean activo, int idRol) {
        
        super(nickname, contraseña, activo, idRol);
        this.cui = cui;
        this.correo = correo;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.perfilCompletado = perfilCompletado;
    }

    public String getCui() {
        return cui;
    }

    public String getCorreo() {
        return correo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public boolean isPerfilCompletado() {
        return perfilCompletado;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setPerfilCompletado(boolean perfilCompletado) {
        this.perfilCompletado = perfilCompletado;
    }


    
    @Override
    public boolean datosCompletos() {
        return cui != null && !cui.isBlank() &&
               correo != null && !correo.isBlank() &&
               nombre != null && !nombre.isBlank() &&
               telefono != null && !telefono.isBlank() &&
               direccion != null && !direccion.isBlank() &&
               nickname != null && !nickname.isBlank() &&
               contraseña != null && !contraseña.isBlank() &&
               fechaNacimiento != null;
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return nickname.length() <= 30 &&
               nombre.length() <= 80 &&
               cui.length() <= 50 &&
               correo.length() <= 100 &&
               telefono.length() <= 20 &&
               direccion.length() <= 300 &&
               contraseña.length() <= 700;
    }
    
    
    
    
}
