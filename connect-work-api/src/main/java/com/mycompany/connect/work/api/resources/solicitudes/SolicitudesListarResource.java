/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.solicitudes;

import com.mycompany.connect.work.api.exceptions.DBException;
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
@WebServlet(name = "SolicitudesListarResource", urlPatterns = {"/api/solicitudes/listar/*"})
public class SolicitudesListarResource extends HttpServlet {
    
    private EscritorJson escritor = new EscritorJson();
    private SolicitudesService service = new SolicitudesService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // para obtener todas las enviadas segun  HABILIDAD o CATEGORIA

        String ruta = obtenerParametroRuta(req);
        if (ruta == null) {
            return;
        }
        
        Object objeto = null;
        
        try {
            if (ruta.equals("habilidades")) {
                objeto = service.obtenerHabilidadesEnviadas();
                
            } else if (ruta.equals("categorias")) {
                objeto = service.obtenerCategoriasEnviadas();
                
            }
            
            if (objeto != null) {
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, objeto);
            }
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
    
}
