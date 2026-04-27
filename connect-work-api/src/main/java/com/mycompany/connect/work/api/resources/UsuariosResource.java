/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.EntidadDuplicadaException;
import com.mycompany.connect.work.api.modelos.usuarios.UsuarioPlataforma;
import com.mycompany.connect.work.api.servicios.UsuariosPlataformaService;
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
@WebServlet(name = "UsuariosResource", urlPatterns = {"/usuarios/*"})
public class UsuariosResource extends HttpServlet {
    
    private UsuariosPlataformaService usuariosService = new UsuariosPlataformaService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       UsuarioPlataforma nuevo = gson.fromJson(req.getReader(), UsuarioPlataforma.class);
        try {
            usuariosService.crear(nuevo);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        } catch (EntidadDuplicadaException ex) {
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }


}
