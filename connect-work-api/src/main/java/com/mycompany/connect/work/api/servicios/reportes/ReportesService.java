/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.servicios.reportes;

import com.mycompany.connect.work.api.db.reportes.ContratosCompletadosDB;
import com.mycompany.connect.work.api.db.reportes.GastosCategoriaDB;
import com.mycompany.connect.work.api.db.reportes.HistorialComisionDB;
import com.mycompany.connect.work.api.db.reportes.HistorialProyectosDB;
import com.mycompany.connect.work.api.db.reportes.HistorialRecargasDB;
import com.mycompany.connect.work.api.db.reportes.PropuestasEnviadasDB;
import com.mycompany.connect.work.api.db.reportes.TopCategoriasDB;
import com.mycompany.connect.work.api.db.reportes.TopFreelancersDB;
import com.mycompany.connect.work.api.dtos.reportes.ReporteContratoCompletado;
import com.mycompany.connect.work.api.dtos.reportes.ReporteGastoCategoria;
import com.mycompany.connect.work.api.dtos.reportes.ReporteHistorialComision;
import com.mycompany.connect.work.api.dtos.reportes.ReporteHistorialProyecto;
import com.mycompany.connect.work.api.dtos.reportes.ReportePropuestaEnviada;
import com.mycompany.connect.work.api.dtos.reportes.ReporteRecarga;
import com.mycompany.connect.work.api.dtos.reportes.ReporteTopCategoria;
import com.mycompany.connect.work.api.dtos.reportes.ReporteTopFreelancer;
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
    private HistorialRecargasDB historiaRecargasDB = new HistorialRecargasDB();
    private GastosCategoriaDB gastosCategoriaDB = new GastosCategoriaDB();
    private ContratosCompletadosDB contratosCompletadosDB = new ContratosCompletadosDB();
    private TopCategoriasDB topCategoriasDB = new TopCategoriasDB();
    private PropuestasEnviadasDB propuestasEnviadasDB = new PropuestasEnviadasDB();
    private HistorialComisionDB historialComisionDB = new HistorialComisionDB();
    private TopFreelancersDB topFreelancersDB = new TopFreelancersDB();

    public Object generarReporte(ReporteRequest request) throws ErrorDeLogicaException, DBException {

        String tipoReporte = request.getTipoReporte();

        if (tipoReporte.equals(TiposDeReporte.CLIENTE_HISTORIAL_PROYECTOS.getValor())) {
            return this.generarReporteProyectos(request);
        }

        if (tipoReporte.equals(TiposDeReporte.CLIENTE_HISTORIAL_RECARGAS.getValor())) {
            return this.generarReporteRecargas(request);
        }

        if (tipoReporte.equals(TiposDeReporte.CLIENTE_GASTO_POR_CATEGORIA.getValor())) {
            return this.reporteGastoCategoria(request);
        }

        if (tipoReporte.equals(TiposDeReporte.FREELANCER_CONTRATOS_COMPLETADOS.getValor())) {
            return this.generarReporteContratosCompletados(request);
        }

        if (tipoReporte.equals(TiposDeReporte.FREELANCER_TOP_CATEGORIAS.getValor())) {
            return this.generarReporteTopCategorias(request);
        }
        if (tipoReporte.equals(TiposDeReporte.FREELANCER_PROPUESTAS_ENVIADAS.getValor())) {
            return this.generarReportePropuestas(request);
        }
        
        if (tipoReporte.equals(TiposDeReporte.ADMIN_HISTORIAL_COMISIONES.getValor())) {
            return this.generarReporteHistorialComision(request);
        }
        
        if (tipoReporte.equals(TiposDeReporte.ADMIN_TOP_FREELANCERS_INGRESOS.getValor())) {
            return this.generarTopFreelancers(request);
        }
        
        

        return null;
    }

    private void validarRequest(ReporteRequest request) throws ErrorDeLogicaException {
        if (request == null) {
            throw new ErrorDeLogicaException("Erorr al recibir la peticion");
        }

        request.validarPeriodos();
    }

    private ArrayList<ReporteTopFreelancer> generarTopFreelancers(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);
        if(request.reporteConRango()){
            return this.topFreelancersDB.obtenerPorPeriodo(request);
        }else{
            return this.topFreelancersDB.obtenerTodo(request);
        }
    }
    
    private ArrayList<ReporteHistorialComision> generarReporteHistorialComision(ReporteRequest request) throws DBException {
    return this.historialComisionDB.obtenerTodo();
}

    private ArrayList<ReportePropuestaEnviada> generarReportePropuestas(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);
        if (request.reporteConRango()) {
            return this.propuestasEnviadasDB.obtenerPorPeriodo(request);
        } else {
            return this.propuestasEnviadasDB.obtenerTodo(request);
        }
    }

    private ArrayList<ReporteTopCategoria> generarReporteTopCategorias(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);

        if (request.reporteConRango()) {
            return this.topCategoriasDB.obtenerPorPeriodo(request);
        } else {
            return this.topCategoriasDB.obtenerTodo(request);
        }
    }

    private ArrayList<ReporteContratoCompletado> generarReporteContratosCompletados(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);

        if (request.reporteConRango()) {
            return this.contratosCompletadosDB.obtenerPorPeriodo(request);
        } else {
            return this.contratosCompletadosDB.obtenerTodo(request);
        }
    }

    private ArrayList<ReporteHistorialProyecto> generarReporteProyectos(ReporteRequest request) throws DBException, ErrorDeLogicaException {
        this.validarRequest(request);

        if (request.reporteConRango()) {
            return this.historialProyectosDB.obtenerPorPeriodo(request);

        } else {
            return this.historialProyectosDB.obtenerTodo(request);
        }
    }

    private ArrayList<ReporteGastoCategoria> reporteGastoCategoria(ReporteRequest request) throws DBException {
        if (request.reporteConRango()) {
            return this.gastosCategoriaDB.obtenerPorPeriodo(request);
        } else {
            return this.gastosCategoriaDB.obtenerTodo(request);
        }
    }

    private ArrayList<ReporteRecarga> generarReporteRecargas(ReporteRequest request) throws DBException {
        return this.historiaRecargasDB.obtenerReporteGlobal(request);
    }

    // metodos para la generación 
    private void asegurarReporteNoNUll(Object reporte) throws ErrorDeLogicaException {
        if (reporte == null) {
            throw new ErrorDeLogicaException(" no se encontró informacion");
        }

    }

}
