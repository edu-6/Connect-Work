/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.propuestas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.dtos.propuestas.PropuestaRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.servicios.PropuestasService;
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
@WebServlet(name = "PropuestasResource", urlPatterns = {"/api/propuestas/*"})
public class PropuestasResource extends HttpServlet {
    
    private PropuestasService service = new PropuestasService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);
        
        if(ruta != null  && esNumero(ruta)){
            try {
                service.eliminar(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
                
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (ErrorDeLogicaException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ex.getMessage(), resp);
            }
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        PropuestaRequest propuesta = gson.fromJson(req.getReader(), PropuestaRequest.class);
        try {
            service.crear(propuesta);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
