package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.Habilidad;
import com.mycompany.connect.work.api.servicios.HabilidadesService;
import com.mycompany.connect.work.api.utils.EscritorJson;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "HabilidadesResource", urlPatterns = {"/api/habilidades/*"})
public class HabilidadesResource extends HttpServlet {

    private HabilidadesService habilidadesService = new HabilidadesService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().create();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Habilidad nueva = gson.fromJson(req.getReader(), Habilidad.class);
        try {
            habilidadesService.crear(nueva);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Habilidad editada = gson.fromJson(req.getReader(), Habilidad.class);
        try {
            habilidadesService.editar(editada);
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ruta = obtenerParametroRuta(req);

        try {
            if (ruta == null || ruta.equals("todas")) {
                java.util.ArrayList<Habilidad> habilidades = habilidadesService.buscarTodos();
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, habilidades);
            } else if (ruta.equals("activas")) {
                java.util.ArrayList<Habilidad> habilidades = habilidadesService.buscarActivos();
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, habilidades);
            } else {
                Habilidad encontrada = habilidadesService.buscar(ruta);
                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJson(resp, encontrada);
            }
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
}
