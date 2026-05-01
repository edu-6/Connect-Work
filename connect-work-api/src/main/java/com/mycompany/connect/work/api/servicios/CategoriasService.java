/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.CategoriasDB;
import com.mycompany.connect.work.api.db.HabilidadCategoriaDB;
import com.mycompany.connect.work.api.dtos.categorias.CategoriaRequest;
import com.mycompany.connect.work.api.dtos.categorias.CategoriaResponse;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.Categoria;
import com.mycompany.connect.work.api.modelos.Habilidad;
import com.mycompany.connect.work.api.modelos.HabilidadCategoria;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class CategoriasService extends CrudService {
    
    private CategoriasDB categoriasDB = new CategoriasDB();
    private HabilidadCategoriaDB habilidadCategoriaDB = new HabilidadCategoriaDB();
    private HabilidadesService habilidadesService = new HabilidadesService();

    public void  crear(CategoriaRequest categoriaFull) throws CamposVaciosException, DatosMuyLargosException, DBException, EntidadDuplicadaException{
        
        Categoria nueva = categoriaFull.getCategoria();
        
        this.revisarDatosCorrectos(nueva);
        
        
        Categoria categoria = categoriasDB.buscar(categoriaFull.getCategoria().getNombre());
        if(categoria != null && categoria.getNombre().equals(nueva.getNombre())){
            throw new EntidadDuplicadaException("Ya existe la categoria "+ categoria.getNombre());
        }
        
        
        int idNuevaCategoria = categoriasDB.crear(categoriaFull.getCategoria());
        categoriaFull.agregarIdAHabilidades(idNuevaCategoria);
        
        habilidadCategoriaDB.crear(categoriaFull.getHabilidades());
    }
    
    
    public void  editar(CategoriaRequest categoriaFull) throws DBException, EntidadDuplicadaException, CamposVaciosException, DatosMuyLargosException{
        Categoria edicion = categoriaFull.getCategoria();
        
        this.revisarDatosCorrectos(edicion);
        
        
        Categoria categoria = categoriasDB.buscar(categoriaFull.getCategoria().getNombre());
        if(categoria != null && categoria.getNombre().equals(edicion.getNombre())){
            
            if(categoria.getId() != edicion.getId()){
               throw new EntidadDuplicadaException("Ya existe la categoria "+ categoria.getNombre());
            }    
        }
        
        categoriasDB.editar(categoriaFull.getCategoria());
        categoriaFull.agregarIdAHabilidades(edicion.getId());
        
        habilidadCategoriaDB.crear(categoriaFull.getHabilidades());
    }
    
    
    public CategoriaResponse buscarCategoriaFull(String nombre) throws DBException, NotFoundException{
        Categoria categoria = categoriasDB.buscar(nombre);
        if(categoria == null){
            throw new NotFoundException(" no se encontró la categoria");
        }
        
        ArrayList<Habilidad> habilidades = habilidadesService.buscarHabilidadesEnCategoria(categoria.getId());
        
        return new CategoriaResponse(categoria, habilidades);
    }
    
    public ArrayList<Categoria> buscarCategoriasActivas() throws DBException{
        return categoriasDB.buscarVariosPorString(categoriasDB.getBuscarActivosQuery());
    }
    
    public ArrayList<Categoria> buscarTodas() throws DBException{
         return categoriasDB.buscarVariosPorString(categoriasDB.getBuscarTodosQuery());
    }
    
    
    
    public void eliminarHabilidadEnCategoria(HabilidadCategoria eliminacion) throws DBException{
        habilidadCategoriaDB.eliminar(eliminacion);
    }
    
}
