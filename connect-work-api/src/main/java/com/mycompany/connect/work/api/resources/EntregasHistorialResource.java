/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.servicios.Entregasservice;
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
@WebServlet(name = "EntregasHistorialResource", urlPatterns = {"/api/entregas-historial/*"})
public class EntregasHistorialResource extends HttpServlet {
    
    private Entregasservice service = new Entregasservice();
    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String ruta = obtenerParametroRuta(req);

        if (ruta != null && esNumero(ruta)) {
            try {

                Object historial = service.buscarHisotrialDeEntregas(Integer.valueOf(ruta));

                if (historial == null) {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    escritor.escribirError("Historial no encontrado ", resp);
                    return;
                }

                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJsonConFecha(resp, historial);

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
