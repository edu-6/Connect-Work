package com.mycompany.connect.work.api.servicios;

import com.mycompany.connect.work.api.db.HabilidadesDB;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.Habilidad;
import java.util.ArrayList;

public class HabilidadesService extends CrudService {

    private final HabilidadesDB habilidadesDB = new HabilidadesDB();
    

    public void crear(Habilidad habilidad) throws CamposVaciosException, DatosMuyLargosException, DBException, EntidadDuplicadaException {
        revisarDatosCorrectos(habilidad);

        Habilidad encontrada = habilidadesDB.buscar(habilidad.getNombre());
        if (encontrada != null) {
            throw new EntidadDuplicadaException("La habilidad con este nombre ya existe.");
        }

        habilidadesDB.crear(habilidad);
    }

    public void editar(Habilidad habilidad) throws CamposVaciosException, DatosMuyLargosException, DBException, EntidadDuplicadaException {
        revisarDatosCorrectos(habilidad);
        
        Habilidad encontrada = habilidadesDB.buscar(habilidad.getNombre());
        if (encontrada != null && encontrada.getId() != habilidad.getId()) {
            throw new EntidadDuplicadaException("Ya existe la habilidad "+ habilidad.getNombre());
        }

        habilidadesDB.editar(habilidad);
    }

    public Habilidad buscar(String nombre) throws DBException, NotFoundException {
        Habilidad habilidad = habilidadesDB.buscar(nombre);
        if(habilidad == null){
            throw new NotFoundException(" no se encontró la habilidad");
        }
        return habilidad;
    }

    public ArrayList<Habilidad> buscarTodos() throws DBException {
        return habilidadesDB.buscarVariosPorString(HabilidadesDB.getBuscarTodosQuery());
    }

    public ArrayList<Habilidad> buscarActivos() throws DBException {
        return habilidadesDB.buscarVariosPorString(HabilidadesDB.getBuscarActivosQuery());
    }
}
