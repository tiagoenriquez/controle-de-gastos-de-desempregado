/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.daos;

import com.tiagoenriquez.controleDeGastosDeDesempregado.utils.MyConnection;
import java.sql.Connection;

/**
 *
 * @author tiago
 */
public class Dao {
    
    Connection connection;
    
    public Dao() throws Exception {
        this.connection = MyConnection.getConnection();
    }
    
}
