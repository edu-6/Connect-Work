/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.ComisionesDB;
import com.mycompany.connect.work.api.dtos.comisiones.Comision;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;

/**
 *
 * @author edu
 */
public class ComisionesService extends CrudService {
    private final ComisionesDB comisionesDB = new ComisionesDB();

    public Comision obtener() throws DBException {
        return comisionesDB.obtener();
    }

    public void actualizar(Comision comision) throws CamposVaciosException, DatosMuyLargosException, DBException {
        revisarDatosCorrectos(comision);
        comisionesDB.actualizar(comision);
    }
    
}
