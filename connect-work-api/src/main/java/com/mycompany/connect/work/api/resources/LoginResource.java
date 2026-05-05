/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.google.gson.Gson;
import com.mycompany.connect.work.api.dtos.usuariosLogin.UsuarioLoginRequest;
import com.mycompany.connect.work.api.dtos.usuariosLogin.UsuarioLoginResponse;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.exceptions.NotFoundException;
import com.mycompany.connect.work.api.servicios.LoginService;
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
@WebServlet(name = "LoginResource", urlPatterns = {"/api/login/*"})
public class LoginResource extends HttpServlet {
    
    
    private LoginService loginService = new LoginService();
    private EscritorJson escritor = new EscritorJson();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        UsuarioLoginRequest usuario = gson.fromJson(req.getReader(), UsuarioLoginRequest.class);
        
        try {
            UsuarioLoginResponse response = loginService.loguearUsuario(usuario);
            resp.setStatus(HttpServletResponse.SC_OK);
            escritor.escribirJson(resp, response);
        } catch (NotFoundException ex) {
           resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
           escritor.escribirError(ex.getMessage(), resp);
        } catch (DBException ex) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            escritor.escribirError(ex.getMessage(), resp);
        }
    }
    
}
