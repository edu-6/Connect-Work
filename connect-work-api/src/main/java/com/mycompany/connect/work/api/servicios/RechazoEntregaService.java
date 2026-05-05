/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.RechazosEntregasDB;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.RechazoEntrega;

/**
 *
 * @author edu
 */
public class RechazoEntregaService extends CrudService {
    private RechazosEntregasDB rechazosDB = new RechazosEntregasDB();


    public void crear(RechazoEntrega rechazo) throws CamposVaciosException, DatosMuyLargosException, DBException {
        
        this.revisarDatosCorrectos(rechazo);
        rechazosDB.crear(rechazo);
    }

    public RechazoEntrega buscarPorSolicitud(int idSolicitud) throws DBException, NotFoundException {
        RechazoEntrega rechazo = rechazosDB.buscarPorId(idSolicitud);
        
        if (rechazo == null) {
            throw new NotFoundException("No existe un motivo de rechazo para la entrega ");
        }
        
        return rechazo;
    }
    
}
