/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.dtos.proyectos.ProyectoRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.servicios.BusquedasProyectosService;
import com.mycompany.connect.work.api.servicios.ProyectosService;
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
@WebServlet(name = "ProyectosResrouce", urlPatterns = {"/api/proyectos"})
public class ProyectosResrouce extends HttpServlet {
    
    private EscritorJson escritor = new EscritorJson();
    private ProyectosService proyectosService = new ProyectosService();
    private BusquedasProyectosService busquedasProyectos = new BusquedasProyectosService();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        ProyectoRequest proyecto = gson.fromJson(req.getReader(), ProyectoRequest.class);
        try {
            proyectosService.editar(proyecto);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DatosMuyLargosException ex) {
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       ProyectoRequest proyecto = gson.fromJson(req.getReader(), ProyectoRequest.class);
        try {
            proyectosService.crear(proyecto);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DatosMuyLargosException ex) {
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_EXPECTATION_FAILED);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);
        if(!(ruta != null && esNumero(ruta))) return;
        
        try {
            Object objeto = busquedasProyectos.buscarPorId(Integer.valueOf(ruta));
             resp.setStatus(HttpServletResponse.SC_OK);
             escritor.escribirJsonConFecha(resp, objeto);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
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
