/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.connect.work.api.resources;

import com.mycompany.connect.work.api.db.EnumsDB;
import com.mycompany.connect.work.api.exceptions.DBException;
import com.mycompany.connect.work.api.modelos.enums.Rol;
import com.mycompany.connect.work.api.utils.EscritorJson;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
@WebServlet(name = "EnumsResource", urlPatterns = {"/enums/*"})
public class EnumsResource extends HttpServlet {

    private EnumsDB db = new EnumsDB();
    private EscritorJson escritor = new EscritorJson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String tipoEnum = obtenerTipoEnum(req);
        if (tipoEnum == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        switch (tipoEnum) {
            case "roles":
                ArrayList<Rol> roles = new ArrayList();
                try {
                    roles = db.obtenerRoles();
                    resp.setStatus(HttpServletResponse.SC_OK);
                    escritor.escribirJson(resp, roles);
                } catch (DBException ex) {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    escritor.escribirError(ex.getMessage(), resp);
                }
                break;
        }
    }

    private String obtenerTipoEnum(HttpServletRequest req) {
        String ruta = req.getPathInfo();
        if (ruta == null) {
            return ruta;
        }
        return ruta.substring(1);
    }

}
