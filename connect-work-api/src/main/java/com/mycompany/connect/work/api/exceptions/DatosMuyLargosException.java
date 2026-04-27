/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.connect.work.api.exceptions;


/**
 *
 * @author edu
 */
public class DatosMuyLargosException extends Exception {

    public DatosMuyLargosException() {
        super("Los campos exceden el numero de caracteres");
    }
}
