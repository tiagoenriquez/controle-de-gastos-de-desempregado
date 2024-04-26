/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.daos;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Item;
import com.tiagoenriquez.controleDeGastosDeDesempregado.utils.MyConnection;
import java.sql.Connection;
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
public class ItemDao extends Dao{

    public ItemDao() throws Exception {
    }
    
    public void atualizar(Item item) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "update items set nome = ?, genero = ?, updated_at = ? "
                    + "where id = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, item.getNome());
            statement.setString(2, item.getGenero());
            statement.setTimestamp(3, item.getUpdatedAt());
            statement.setInt(4, item.getId());
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao atualizar item:\n" + sQLException.getSQLState());
        } finally {
            MyConnection.closeConnection(connection, statement);
        }
    }
    
    public void atualizarGenero(String generoAtualizado, String generoDesatualizado) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "update items set genero = ? where genero = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, generoAtualizado);
            statement.setString(2, generoDesatualizado);
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao atualizar gênero:\n" + 
                    sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement);
        }
    }
    
    public void excluir(int id) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "delete from items where id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao excluir item:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement);
        }
    }
    
    public void inserir(Item item) throws SQLException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = this.connection;
            String query = "insert into items (nome, genero, created_at, "
                    + "updated_at) values (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, item.getNome());
            statement.setString(2, item.getGenero());
            statement.setTimestamp(3, item.getCreatedAt());
            statement.setTimestamp(4, item.getUpdatedAt());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new SQLException("Erro ao inserir item:\n" + 
                    exception.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement);
        }
    }
    
    public List<Item> listar() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select * from items order by nome asc";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<Item> itens = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String genero = resultSet.getString("genero");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                Timestamp updatedAt = resultSet.getTimestamp("updated_at");
                Item item = new Item(id, nome, genero, createdAt, updatedAt);
                itens.add(item);
            }
            return itens;
        } catch (SQLException exception) {
            throw new Exception("Erro ao listar itens:\n" + exception.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement, resultSet);
        }
    }
    
    public List<String> listarGeneros() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select distinct genero from items "
                    + "order by genero asc";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<String> generos = new ArrayList<>();
            while (resultSet.next()) {
                generos.add(resultSet.getString("genero"));
            }
            return generos;
        } catch (SQLException exception) {
            throw new Exception("Erro ao listar gêneros:\n" 
                    + exception.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement, resultSet);
        }
    }
    
    public Item procurar(int id) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.connection;
            String query = "select * from items where id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new Exception(
                        "Não foi possível encontrar o id procurado");
            }
            String nome = resultSet.getString("nome");
            String genero = resultSet.getString("genero");
            Timestamp createdAt = resultSet.getTimestamp("created_at");
            Timestamp updatedAt = resultSet.getTimestamp("updated_at");
            return new Item(id, nome, genero, createdAt, updatedAt);
        } catch (SQLException sQLException) {
            throw new Exception("Erro ao procurar o item:\n" 
                    + sQLException.getMessage());
        } finally {
            MyConnection.closeConnection(connection, statement, resultSet);
        }
    }
    
}
