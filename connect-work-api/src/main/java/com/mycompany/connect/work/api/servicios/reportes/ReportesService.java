/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios.reportes;

import com.mycompany.connect.work.api.db.reportes.HistorialProyectosDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteHistorialProyecto;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.modelos.enums.TiposDeReporte;
import com.mycompany.connect.work.api.modelos.reportes.ReporteRequest;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public class ReportesService {

    private HistorialProyectosDB historialProyectosDB = new HistorialProyectosDB();

    public Object generarReporte(ReporteRequest request) throws ErrorDeLogicaException, DBException {

        String tipoReporte = request.getTipoReporte();

        if (tipoReporte.equals(TiposDeReporte.CLIENTE_HISTORIAL_PROYECTOS.getValor())) {
            return this.generarReporteProyectos(request);
        }

        return null;
    }

    private void validarRequest(ReporteRequest request) throws ErrorDeLogicaException {
        if (request == null) {
            throw new ErrorDeLogicaException("Erorr al recibir la peticion");
        }

        request.validarPeriodos();
    }

    private ArrayList<ReporteHistorialProyecto> generarReporteProyectos(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);

        if (request.reporteConRango()) {
            return this.historialProyectosDB.obtenerPorPeriodo(request);

        } else {
            return this.historialProyectosDB.obtenerTodo(request);
        }
    }

    // metodos para la generación 
    private void asegurarReporteNoNUll(Object reporte) throws ErrorDeLogicaException {
        if (reporte == null) {
            throw new ErrorDeLogicaException(" no se encontró informacion");
        }

    }

}
