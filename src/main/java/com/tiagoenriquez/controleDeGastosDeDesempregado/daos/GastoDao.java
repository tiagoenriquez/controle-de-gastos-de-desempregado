/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.daos;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Gasto;
import com.tiagoenriquez.controleDeGastosDeDesempregado.utils.MyConnection;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tiago
 */
public class GastoDao extends Dao {

    public GastoDao() throws Exception {
    }
    
    public void atualizar(Gasto gasto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "update gastos set data = ?, valor = ?, item_id = ?,"
                    + " updated_at = ? where id = ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, gasto.getData());
            statement.setBigDecimal(2, gasto.getValor());
            statement.setInt(3, gasto.getItemId());
            statement.setTimestamp(4, gasto.getUpdatedAt());
            statement.setInt(5, gasto.getId());
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao atualizar gasto:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection);
        }
    }
    
    public void excluir(int id) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "delete from gastos where id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao excluir gasto:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection);
        }
    }
    
    public void inserir(Gasto gasto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "insert into gastos (data, valor, item_id, conta_id,"
                    + "created_at, updated_at) values (?, ?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setDate(1, gasto.getData());
            statement.setBigDecimal(2, gasto.getValor());
            statement.setInt(3, gasto.getItemId());
            statement.setInt(4, gasto.getContaId());
            statement.setTimestamp(5, gasto.getCreatedAt());
            statement.setTimestamp(6, gasto.getUpdatedAt());
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao inserir gasto:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection);
        }
    }
    
    public List<java.util.Date> listarMeses() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select distinct month(data) as mes,"
                    + " year(data) as ano from gastos";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<java.util.Date> meses = new ArrayList<>();
            while (resultSet.next()) {
                int mes = resultSet.getInt("mes");
                int ano = resultSet.getInt("ano");
                meses.add(new java.util.Date(ano - 1900, mes - 1, 1));
            }
            return meses;
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao listar os meses:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, resultSet);
        }
    }
    
    public List<Gasto> listarDoMes(Date inicio, Date fim) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select * from gastos "
                    + "inner join items on gastos.item_id = items.id "
                    + "where gastos.data between date(?) and date(?) "
                    + "order by gastos.data desc";
            statement = connection.prepareStatement(query);
            statement.setDate(1, inicio);
            statement.setDate(2, fim);
            resultSet = statement.executeQuery();
            List<Gasto> gastos = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("gastos.id");
                Date data = resultSet.getDate("gastos.data");
                BigDecimal valor = resultSet.getBigDecimal("gastos.valor");
                int itemId = resultSet.getInt("gastos.item_id");
                boolean busca = true;
                Timestamp createdAt = resultSet.getTimestamp("gastos.created_at");
                Timestamp updatedAt = resultSet.getTimestamp("gastos.updated_at");
                Gasto gasto = new Gasto(id, data, valor, itemId, busca, createdAt, updatedAt);
                gastos.add(gasto);
            }
            return gastos;
        } catch (SQLException exception) {
            throw new Exception("Erro ao listar gastos:\n" 
                    + exception.getMessage());
        } finally {
            MyConnection.closeConnection(connection, resultSet);
        }
    }
    
    public Gasto procurar(int id) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select * from gastos "
                    + "inner join items on gastos.item_id = items.id "
                    + "where gastos.id  = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("Não existe gasto com o id informado.");
            }
            Date data = resultSet.getDate("gastos.data");
            BigDecimal valor = resultSet.getBigDecimal("gastos.valor");
            int itemId = resultSet.getInt("gastos.item_id");
            boolean busca = true;
            Timestamp createdAt = resultSet.getTimestamp("gastos.created_at");
            Timestamp updatedAt = resultSet.getTimestamp("gastos.updated_at");
            Gasto gasto = new Gasto(id, data, valor, itemId, busca, createdAt, updatedAt);
            return gasto;
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao procurar o gasto:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, resultSet);
        }
    }
    
    public BigDecimal somarValoresNoMesAtual() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select sum(valor) as soma from gastos "
                    + "where month(data) = month(current_timestamp()) "
                    + "and year(data) = year(current_timestamp)";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception("Soma não retornada");
            }
            BigDecimal soma = resultSet.getBigDecimal("soma");
            return soma;
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao obter soma dos valores:\n"
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, resultSet);
        }
    }
    
}
