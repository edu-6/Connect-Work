/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.dtos.propuestas.BusquedaPropuesta;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
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
@WebServlet(name = "BusquedasPropuestas", urlPatterns = {"/propuestas/busquedas"})
public class BusquedasPropuestas extends HttpServlet {
    
    
    private PropuestasService service = new PropuestasService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        BusquedaPropuesta busqueda = gson.fromJson(req.getReader(), BusquedaPropuesta.class);
        
        try {
            Object resultados = service.buscarPropuestas(busqueda);
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJsonConFecha(resp, resultados);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
        
    }

   

}
