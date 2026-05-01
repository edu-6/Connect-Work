/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.connect.work.api.exceptions.CamposVaciosException;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.DatosMuyLargosException;
import com.mycompany.connect.work.api.modelos.perfles.PerfilCliente;
import com.mycompany.connect.work.api.modelos.perfles.PerfilFreelancer;
import com.mycompany.connect.work.api.servicios.PerfilesCrudService;
import com.mycompany.connect.work.api.utils.ConvertidorFechas;
import com.mycompany.connect.work.api.utils.EscritorJson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author edu
 */
@WebServlet(name = "PerfilesResource", urlPatterns = {"/api/perfiles/*"})
public class PerfilesResource extends HttpServlet {
    
    private PerfilesCrudService perfilesService = new PerfilesCrudService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new ConvertidorFechas()).create();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String ruta = obtenerParametroRuta(req);
        if (ruta == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            escritor.escribirError("error al recibir el parametro", resp);
        }
        
        if (ruta.equals("cliente")) {
            
            PerfilCliente perfil = gson.fromJson(req.getReader(), PerfilCliente.class);
            try {
                this.perfilesService.crearPerfilCliente(perfil);
            } catch (CamposVaciosException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ruta, resp);
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ruta, resp);
            } catch (DatosMuyLargosException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ruta, resp);
            }
            
        } else if (ruta.equals("freelancer")) {
            PerfilFreelancer perfil = gson.fromJson(req.getReader(), PerfilFreelancer.class);
            try {
                this.perfilesService.crearPerfilFreelancer(perfil);
            } catch (CamposVaciosException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (DBException ex) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                escritor.escribirError(ex.getMessage(), resp);
            } catch (DatosMuyLargosException ex) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
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
    
}
