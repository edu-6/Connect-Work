/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.modelos.Entidad;

/**
 *
 * @author edu
 */
public class CrudService {
    
    public void revisarDatosCorrectos(Entidad entidad) throws CamposVaciosException, DatosMuyLargosException{
        if(!entidad.datosCompletos()){
            throw new CamposVaciosException();
        }
        if(!entidad.datosTamañoCorrecto()){
            throw new DatosMuyLargosException();
        }
    }
    
}
