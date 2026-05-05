/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.modelos.CarteraDigital;
import com.mycompany.connect.work.api.servicios.CarteraDigitalService;
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
@WebServlet(name = "CarteraResource", urlPatterns = {"/api/cartera/*"})
public class CarteraResource extends HttpServlet {

   private Gson gson = new Gson();
    private EscritorJson escritor = new EscritorJson();
    private CarteraDigitalService service = new CarteraDigitalService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String cui = obtenerParametroRuta(req);

        try {
            if (cui == null) {
                throw new CamposVaciosException("Debe proporcionar el CUI del cliente.");
            }
            
            CarteraDigital cartera = service.obtenerCartera(cui);
            
            if (cartera == null) {
                throw new NotFoundException("No se encontró una cartera para el CUI proporcionado.");
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJson(resp, cartera);

        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (NotFoundException ex) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarteraDigital datos = gson.fromJson(req.getReader(), CarteraDigital.class);

        try {
            service.recargarSaldo(datos.getCuiCliente(), datos.getSaldo());
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (CamposVaciosException ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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