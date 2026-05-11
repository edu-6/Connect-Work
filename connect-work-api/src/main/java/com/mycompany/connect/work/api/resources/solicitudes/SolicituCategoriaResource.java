/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.solicitudes;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.dtos.solicitudes.CambioEnSolicitud;
import com.mycompany.connect.work.api.dtos.solicitudes.SolicitudRequest;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.servicios.SolicitudesService;
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
@WebServlet(name = "SolicituCategoriaResource", urlPatterns = {"/api/solicitudes/categorias/*"})
public class SolicituCategoriaResource extends HttpServlet {
    
    private Gson gson = new Gson();
    private SolicitudesService service = new SolicitudesService();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // para rechazar o aceptar
        
        CambioEnSolicitud cambio = gson.fromJson(req.getReader(), CambioEnSolicitud.class);

        try {
            if (cambio.getTipo().equals("ACEPTAR")) {
                service.aprobarCategoria(cambio.getIdSolicitud());

            } else if (cambio.getTipo().equals("RECHAZAR")) {
                service.rechazarCategoria(cambio.getIdSolicitud());
            }

            resp.setStatus(HttpServletResponse.SC_OK);

        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // para crear la solicitud

            SolicitudRequest solicitud = gson.fromJson(req.getReader(), SolicitudRequest.class);
            service.crearSolicitudCategoria(solicitud);

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
        // para listar las de un cliente
        String cui = obtenerParametroRuta(req);
        if (cui != null) {
            try {
                 Object objeto = service.obtenerMisCategorias(cui);
                 resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, objeto);
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

    
    

}
