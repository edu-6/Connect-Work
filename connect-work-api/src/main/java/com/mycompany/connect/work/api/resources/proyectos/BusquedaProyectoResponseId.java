/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.proyectos;

import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.servicios.BusquedasProyectosService;
import com.mycompany.connect.work.api.utils.EscritorJson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author edu
 */
@WebServlet(name = "BusquedaProyectoResponseId", urlPatterns = {"/api/proyectoResponse/*"})
public class BusquedaProyectoResponseId extends HttpServlet {
    
    private BusquedasProyectosService service = new BusquedasProyectosService();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String ruta = obtenerParametroRuta(req);
        if(!(ruta != null && esNumero(ruta))) return;
        
        try {
            Object objeto = service.buscarResponsePorId(Integer.valueOf(ruta));
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
