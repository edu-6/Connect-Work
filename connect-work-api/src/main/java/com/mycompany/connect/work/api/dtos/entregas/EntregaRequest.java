/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.dtos.entregas;

import com.mycompany.connect.work.api.modelos.Entidad;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class EntregaRequest extends Entidad {
    
    private String descripcion;
    private int idProyecto;

    private String[] archivos;
    
    
    private ArrayList<ArchivoEntrega> archivosEntrega;

    public String getDescripcion() {
        return descripcion;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public String[] getArchivos() {
        return archivos;
    }

    @Override
    public boolean datosCompletos() {
        return descripcion != null && !descripcion.isBlank();
    }

    @Override
    public boolean datosTamañoCorrecto() {
        return descripcion != null && descripcion.length() <= 300;
    }
    
    
    
    public ArrayList<ArchivoEntrega> crearArchivosEntregas(int idEntrega){
        archivosEntrega = new ArrayList();
        for (String archivo : archivos) {
            archivosEntrega.add(new ArchivoEntrega(archivo, idEntrega));
        }
        return archivosEntrega;
    }

}
