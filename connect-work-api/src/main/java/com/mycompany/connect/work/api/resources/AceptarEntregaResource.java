/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;
import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.IdEntrega;
import com.mycompany.connect.work.api.servicios.EntregasService;
import com.mycompany.connect.work.api.utils.EscritorJson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author edu
 */
@WebServlet(name = "AceptarEntregaResource", urlPatterns = {"/api/aceptarEntrega/*"})
public class AceptarEntregaResource extends HttpServlet {

    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();
    private EntregasService entregasService = new EntregasService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        IdEntrega idEntrega = gson.fromJson(req.getReader(), IdEntrega.class);
        
        try {
            entregasService.aceptarEntrega(idEntrega.getIdEntrega());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
             resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             escritor.escribirError(ex.getMessage(), resp);
        }
    }
    
    
    
}
