/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.CarteraDigitalDB;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.modelos.CarteraDigital;

/**
 *
 * @author edu
 */
public class CarteraDigitalService extends CrudService {

    private CarteraDigitalDB carteraDB = new CarteraDigitalDB();

    
    public void recargarSaldo(String cui, double monto) throws DBException, CamposVaciosException {
        if (monto <= 0) {
            throw new CamposVaciosException("El monto a recargar debe ser mayor a 0");
        }

        if (cui == null || cui.trim().isEmpty()) {
            throw new CamposVaciosException("El CUI del cliente es obligatorio");
        }

        carteraDB.recargar(cui, monto);
    }

    public CarteraDigital obtenerCartera(String cui) throws DBException, CamposVaciosException {
        if (cui == null || cui.trim().isEmpty()) {
            throw new CamposVaciosException("CUI no proporcionado");
        }
        return carteraDB.obtenerCartera(cui);
    }


    public void revisarDatosCorrectos(CarteraDigital cartera) throws CamposVaciosException, DatosMuyLargosException {
        if (cartera.getCuiCliente() == null || cartera.getCuiCliente().trim().isEmpty()) {
            throw new CamposVaciosException("El CUI del cliente no puede estar vacío");
        }

        if (cartera.getCuiCliente().length() > 50) {
            throw new DatosMuyLargosException();
        }
        
        if (cartera.getSaldo() < 0) {
            throw new CamposVaciosException("El saldo no puede ser negativo");
        }
    }

}
