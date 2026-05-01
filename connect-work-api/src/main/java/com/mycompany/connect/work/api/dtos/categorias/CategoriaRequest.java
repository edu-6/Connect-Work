/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.categorias;

import com.mycompany.connect.work.api.modelos.Categoria;
import com.mycompany.connect.work.api.modelos.HabilidadCategoria;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class CategoriaRequest {
    public Categoria categoria;
    public ArrayList<HabilidadCategoria> habilidades;

    public CategoriaRequest(Categoria categoria, ArrayList<HabilidadCategoria> habilidades) {
        this.categoria = categoria;
        this.habilidades = habilidades;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ArrayList<HabilidadCategoria> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(ArrayList<HabilidadCategoria> habilidades) {
        this.habilidades = habilidades;
    }
    
    
    
    public void agregarIdAHabilidades(int id){
        for (HabilidadCategoria habilidad : habilidades) {
             habilidad.setIdCategoria(id);
        }
    }
    
    
    
}
