/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.dtos.entregas.EntregaRequest;
import com.mycompany.connect.work.api.dtos.entregas.EntregaResponse;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.exceptions.ErrorDeLogicaException;
import com.mycompany.connect.work.api.servicios.Entregasservice;
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
@WebServlet(name = "EntregasResource", urlPatterns = {"/api/entregas/*"})
public class EntregasResource extends HttpServlet {

    private Entregasservice service = new Entregasservice();
    private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        EntregaRequest entrega = gson.fromJson(req.getReader(), EntregaRequest.class);
        try {
            service.crearEntrega(entrega);
            resp.setStatus(HttpServletResponse.SC_CREATED);

        } catch (CamposVaciosException | DatosMuyLargosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (ErrorDeLogicaException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);

        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String ruta = obtenerParametroRuta(req);

        if (ruta != null && esNumero(ruta)) {
            try {

                EntregaResponse entrega = service.buscarEntregaSinRevisar(Integer.valueOf(ruta));

                if (entrega == null) {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    return;
                }

                resp.setStatus(HttpServletResponse.SC_OK);
                escritor.escribirJsonConFecha(resp, entrega);

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
