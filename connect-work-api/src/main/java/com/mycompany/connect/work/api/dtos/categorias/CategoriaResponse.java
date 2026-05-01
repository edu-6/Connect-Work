/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.categorias;

import com.mycompany.connect.work.api.modelos.Categoria;
import com.mycompany.connect.work.api.modelos.Habilidad;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class CategoriaResponse {
    public Categoria categoria;
    public ArrayList<Habilidad> habilidades;

    public CategoriaResponse(Categoria categoria, ArrayList<Habilidad> habilidades) {
        this.categoria = categoria;
        this.habilidades = habilidades;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    

    
    
    
}
