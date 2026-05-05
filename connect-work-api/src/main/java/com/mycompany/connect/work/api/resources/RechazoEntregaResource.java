/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.RechazoEntrega;
import com.mycompany.connect.work.api.servicios.RechazoEntregaService;
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
@WebServlet(name = "RechazoPropuestaService", urlPatterns = {"/api/rechazo-propuesta/*"})
public class RechazoEntregaResource extends HttpServlet {
    
    
    private RechazoEntregaService service = new RechazoEntregaService();
    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();


    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RechazoEntrega nuevoRechazo = gson.fromJson(req.getReader(), RechazoEntrega.class);
            service.crear(nuevoRechazo);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (CamposVaciosException | DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }


    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);

        if (ruta != null && esNumero(ruta)) {
            try {
                int idSolicitud = Integer.parseInt(ruta);
                RechazoEntrega rechazo = service.buscarPorSolicitud(idSolicitud);
                
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, rechazo);
            } catch (NotFoundException ex) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError("Se requiere un ID de solicitud válido en la ruta.", resp);
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
