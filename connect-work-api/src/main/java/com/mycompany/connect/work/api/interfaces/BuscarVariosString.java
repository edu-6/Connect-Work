/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.connect.work.api.interfaces;
import com.mycompany.connect.work.api.exceptions.DBException;
import java.util.ArrayList;

/**
 *
 * @author edu
 */
public interface BuscarVariosString <T> {
    public ArrayList<T> buscarVariosPorString(String parametro) throws DBException;
}
