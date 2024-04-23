/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.models;

import com.tiagoenriquez.controleDeGastosDeDesempregado.daos.ItemDao;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author tiago
 */
public class Item {
    
    private final int id;
    private final String nome;
    private final String genero;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public Item(int id, String nome, String genero, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getGenero() {
        return this.genero;
    }
    
    public String getNome() {
        return this.nome;
    }
    
    public Timestamp getCreatedAt() {
        return this.createdAt;
    }
    
    public Timestamp getUpdatedAt() {
        return this.updatedAt;
    }
    
    public static Item procurar(int id) throws Exception {
        return new ItemDao().procurar(id);
    }
    
    public static List<Item> todos() throws Exception {
        return new ItemDao().listar();
    }
    
    public static List<String> generos() throws Exception {
        return new ItemDao().listarGeneros();
    }
    
    public static void atualizar(int id, String nome, String genero) throws Exception {
        String generoDesatualizado = new ItemDao().procurar(id).getGenero();
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Item item = new Item(id, nome, genero, null, agora);
        Item.atualizarGenero(genero, generoDesatualizado);
    }
    
    private static void atualizarGenero(String genero, String generoDesatualizado) throws Exception {
        new ItemDao().atualizarGenero(genero, generoDesatualizado);
    }
    
    public static void excluido(int id) throws Exception {
        new ItemDao().excluir(id);
    }
    
    public static void inserir(String nome, String genero) throws Exception {
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Item item = new Item(0, nome, genero, agora, agora);
        new ItemDao().inserir(item);
    }
    
}
