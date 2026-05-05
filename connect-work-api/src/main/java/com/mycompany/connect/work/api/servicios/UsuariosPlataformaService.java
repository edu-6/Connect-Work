/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.BuscadorAtributoRepetido;
import com.mycompany.connect.work.api.db.CarteraDigitalDB;
import com.mycompany.connect.work.api.db.UsuariosBaseDB;
import com.mycompany.connect.work.api.db.UsuariosPlataformaDB;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.modelos.CarteraDigital;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioPlataforma;

/**
 *
 * @author edu
 */
public class UsuariosPlataformaService extends CrudService {

    private BuscadorAtributoRepetido df = new BuscadorAtributoRepetido();
    private UsuariosBaseDB baseDB = new UsuariosBaseDB();
    private UsuariosPlataformaDB usuariosDB = new UsuariosPlataformaDB();
    private CarteraDigitalDB carterasDB = new CarteraDigitalDB();

    public void crear(UsuarioPlataforma entidad) throws DBException, EntidadDuplicadaException, CamposVaciosException, DatosMuyLargosException {
        
        
        this.revisarDatosCorrectos(entidad);

        if (df.existeAtributoRepetido(entidad.getNickname(), baseDB.getEXISTE_NICKNAME())) {
            throw new EntidadDuplicadaException("ya existe el nickname " + entidad.getNickname());
        }
        
        if (df.existeAtributoRepetido(entidad.getTelefono(), usuariosDB.getEXISTE_TELEFONO())) {
            throw new EntidadDuplicadaException("ya existe el telefono " + entidad.getTelefono());
        }
        
        
        if (df.existeAtributoRepetido(entidad.getCui(), usuariosDB.getEXISTE_CUI())) {
            throw new EntidadDuplicadaException("ya existe el cui " + entidad.getCui());
        }
        
        if (df.existeAtributoRepetido(entidad.getCorreo(), usuariosDB.getEXISTE_CORREO())) {
            throw new EntidadDuplicadaException("ya existe el correo " + entidad.getCorreo());
        }
        
        
        baseDB.crear(entidad);
        usuariosDB.crear(entidad);
        
        carterasDB.crearCartera(entidad.getCui());
        
        

    }

}
