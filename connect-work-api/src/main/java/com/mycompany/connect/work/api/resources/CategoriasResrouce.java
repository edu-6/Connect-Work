/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.dtos.categorias.CategoriaRequest;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.servicios.CategoriasService;
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
@WebServlet(name = "CategoriasResource", urlPatterns = {"/categorias/*"})
public class CategoriasResrouce extends HttpServlet {

    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();
    private CategoriasService service = new CategoriasService();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriaRequest categoria = gson.fromJson(req.getReader(), CategoriaRequest.class);

        try {
            service.editar(categoria);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException | DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriaRequest categoria = gson.fromJson(req.getReader(), CategoriaRequest.class);

        try {
            service.crear(categoria);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (CamposVaciosException | DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
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
        String ruta = obtenerParametroRuta(req);
        
        try {
            Object objeto;
            if (ruta == null || ruta.equals("todas")) {
                objeto = service.buscarTodas();
            } else if (ruta.equals("activas")) {
                objeto = service.buscarCategoriasActivas();
            } else {
                objeto = service.buscarCategoriaFull(ruta);
            }
            
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJson(resp, objeto);
            
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (NotFoundException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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
