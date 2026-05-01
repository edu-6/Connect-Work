/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.HabilidadCategoria;
import com.mycompany.connect.work.api.servicios.CategoriasService;
import com.mycompany.connect.work.api.servicios.HabilidadesService;
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
@WebServlet(name = "HabilidadesCategoriaResource", urlPatterns = {"/api/habilidades-categoria/*"})
public class HabilidadesCategoriaResource extends HttpServlet {

    private CategoriasService service = new CategoriasService();
    private HabilidadesService habilidadesService = new HabilidadesService();
    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HabilidadCategoria eliminacion = gson.fromJson(req.getReader(), HabilidadCategoria.class);

        try {
            service.eliminarHabilidadEnCategoria(eliminacion);
            resp.setStatus(HttpServletResponse.SC_OK);
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
                Object habilidadesEnCategoria = habilidadesService.buscarHabilidadesEnCategoria(Integer.valueOf(ruta));
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, habilidadesEnCategoria);
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
