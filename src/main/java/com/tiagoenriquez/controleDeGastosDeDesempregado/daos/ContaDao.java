/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.daos;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Conta;
import com.tiagoenriquez.controleDeGastosDeDesempregado.utils.MyConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author tiago
 */
public class ContaDao extends Dao {

    public ContaDao() throws Exception {
    }
    
    public void atualizar(Conta conta) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "update contas set saldo = ?, updated_at = ? "
                    + "where id = ?";
            statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, conta.getSaldo());
            statement.setTimestamp(2, conta.getUpdatedAt());
            statement.setInt(3, conta.getId());
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao atualizar conta:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection);
        }
    }
    
    public Conta procurar(int id) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select * from contas where id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("Conta não encontrada.");
            }
            BigDecimal saldo = resultSet.getBigDecimal("saldo");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            Timestamp updatedAt = resultSet.getTimestamp("updated_at");
            Conta conta = new Conta(id, saldo, createdAt, updatedAt);
            return conta;
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao procurar conta:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, resultSet);
        }
    }
    
}
