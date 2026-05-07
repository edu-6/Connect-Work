/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.dtos.contratos.ContratoRequest;
import com.mycompany.connect.work.api.dtos.contratos.ContratoResponse;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.servicios.ContratosService;
import com.mycompany.connect.work.api.utils.ConvertidorFechas;
import com.mycompany.connect.work.api.utils.EscritorJson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;

/**
 *
 * @author edu
 */
@WebServlet(name = "ContratosResource", urlPatterns = {"/api/contratos/*"})
public class ContratosResource extends HttpServlet {

    private ContratosService contratosService = new ContratosService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);
        if (ruta != null && esNumero(ruta)) {
            try {
                contratosService.cancelarContrato(Integer.valueOf(ruta));
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (ErrorDeLogicaException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (CamposVaciosException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ex.getMessage(), resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ContratoRequest contrato = gson.fromJson(req.getReader(), ContratoRequest.class);
        try {
            contratosService.crearContrato(contrato);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);
        if (ruta != null && esNumero(ruta)) {
            try {
                ContratoResponse contrato = contratosService.buscarContratoDeProyecto(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJsonConFecha(resp, contrato);
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }

        }

    }

    private String obtenerParametroRuta(HttpServletRequest req) {
        String ruta = req.getPathInfo();
        if (ruta == null || ruta.equals("/")) {
            return null;
        } else {
            return ruta.substring(1);
        }
    }

    private boolean esNumero(String cadena) {
        try {
            Integer.valueOf(cadena);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
