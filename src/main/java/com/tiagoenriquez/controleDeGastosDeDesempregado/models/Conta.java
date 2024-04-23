/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.models;

import com.tiagoenriquez.controleDeGastosDeDesempregado.daos.ContaDao;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author tiago
 */
public class Conta {
    
    private final int id;
    private BigDecimal saldo;
    private final Timestamp createdAt;
    private Timestamp updatedAt;

    public Conta(int id, BigDecimal saldo, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.saldo = saldo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    
    private void atualizarGasto(BigDecimal valorAntigo, BigDecimal valorNovo) {
        this.saldo = this.saldo.subtract(valorAntigo);
        this.saldo = this.saldo.add(valorNovo);
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
    
    private void excluirGasto(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
    
    private void inserirGasto(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
    
    public static Conta procurar() throws Exception {
        return new ContaDao().procurar(1);
    }
    
    public static void atualizar(BigDecimal saldo) throws Exception {
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Conta conta = new Conta(1, saldo, null, agora);
        new ContaDao().atualizar(conta);
    }
    
    public static void comGastoAtualizado(BigDecimal valorAntigo, BigDecimal valorNovo) throws Exception {
        Conta conta = new ContaDao().procurar(1);
        conta.atualizarGasto(valorAntigo, valorNovo);
        new ContaDao().atualizar(conta);
        
    }
    
    public static void comGastoExcluido(BigDecimal valor) throws Exception {
        Conta conta = new ContaDao().procurar(1);
        conta.excluirGasto(valor);
        new ContaDao().atualizar(conta);
    }
    
    public static void comGastoInserido(BigDecimal valor) throws Exception {
        Conta conta = new ContaDao().procurar(1);
        conta.inserirGasto(valor);
        new ContaDao().atualizar(conta);
    }
    
}
