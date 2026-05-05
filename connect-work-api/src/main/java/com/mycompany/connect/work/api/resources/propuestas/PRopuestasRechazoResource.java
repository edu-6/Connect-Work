/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.propuestas;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.servicios.PropuestasService;
import com.mycompany.connect.work.api.utils.ConvertidorFechas;
import com.mycompany.connect.work.api.utils.EscritorJson;
import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "PRopuestasRechazoResource", urlPatterns = {"/api/propuestasRechazo/*"})
public class PRopuestasRechazoResource extends HttpServlet {
    
     private PropuestasService service = new PropuestasService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);

        if (ruta != null && esNumero(ruta)) {
            try {
                service.rechazarPropuesta(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ex.getMessage(), resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
