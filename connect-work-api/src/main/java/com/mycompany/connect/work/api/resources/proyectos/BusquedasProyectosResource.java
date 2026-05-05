/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources.proyectos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.dtos.proyectos.BusquedaProyecto;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.servicios.BusquedasProyectosService;
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
@WebServlet(name = "BusquedasProyectosResource", urlPatterns = {"/api/busquedasProyectos/*"})
public class BusquedasProyectosResource extends HttpServlet {
    
    private EscritorJson escritor = new EscritorJson();
    private BusquedasProyectosService busquedasProyectos = new BusquedasProyectosService();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        BusquedaProyecto busqueda = gson.fromJson(req.getReader(), BusquedaProyecto.class);
        
        try {
            Object resultados = busquedasProyectos.buscarProyectos(busqueda);
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJsonConFecha(resp, resultados);
        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
        
    }
}
