/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.connect.work.api.interfaces;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author edu
 */
public interface ExtraerEntidad <T>{
    public T  extraer(ResultSet rs) throws SQLException;
}
