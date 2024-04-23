/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.models;

import com.tiagoenriquez.controleDeGastosDeDesempregado.daos.GastoDao;
import com.tiagoenriquez.controleDeGastosDeDesempregado.daos.ItemDao;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

/**
 *
 * @author tiago
 */
public class Gasto {
    
    private final int id;
    private final Date data;
    private final BigDecimal valor;
    private final int itemId;
    private final int contaId;
    private Conta conta;
    private final Item item;
    private final Timestamp createdAt;
    private final Timestamp updatedAt;

    public Gasto(int id, Date data, BigDecimal valor, int itemId, boolean busca, Timestamp createdAt, Timestamp updatedAt) throws Exception {
        this.id = id;
        this.data = data;
        this.valor = valor;
        this.itemId = itemId;
        this.contaId = 1;
        this.item = this.item(itemId, busca);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public int getItemId() {
        return itemId;
    }

    public int getContaId() {
        return contaId;
    }

    public Conta getConta() {
        return conta;
    }

    public Item getItem() {
        return item;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }
    
    private Item item(int id, boolean busca) throws Exception {
        if (busca) {
            return new ItemDao().procurar(id);
        }
        return null;
    }
    
    public Object[] dados() {
        Locale brasilLocale = new Locale("pt", "BR");
        Object[] dados = new Object[6];
        dados[0] = String.valueOf(id);
        dados[1] = String.valueOf(itemId);
        dados[2] = item.getNome();
        dados[3] = DateFormat.getDateInstance().format(data);
        dados[4] = NumberFormat.getCurrencyInstance(brasilLocale).format(valor);
        dados[5] = item.getGenero();
        return dados;
    }
    
    public static Gasto procurar(int id) throws Exception {
        return new GastoDao().procurar(id);
    }
    
    public static List<Gasto> doMes(Date data) throws Exception {
        Date inicio = new Date(data.getTime());
        Date fim =  new Date(data.getTime());
        inicio.setDate(1);
        fim.setMonth(fim.getMonth() + 1);
        fim.setDate(1);
        fim.setDate(fim.getDate() - 1);
        return new GastoDao().listarDoMes(inicio, fim);
    }
    
    public static List<java.util.Date> meses() throws Exception {
        return new GastoDao().listarMeses();
    }
    
    public static void atualizar(int id, Date data, BigDecimal valor, int itemId) throws Exception {
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Gasto gasto = new Gasto(id, data, valor, itemId, false, null, agora);
        BigDecimal valorAntigo = new GastoDao().procurar(id).getValor();
        new GastoDao().atualizar(gasto);
        Conta.comGastoAtualizado(valor, valorAntigo);
    }
    
    public static void excluir(int id) throws Exception {
        BigDecimal valor = new GastoDao().procurar(id).getValor();
        new GastoDao().excluir(id);
        Conta.comGastoExcluido(valor);
    }
    
    public static void inserir(Date data, BigDecimal valor, int itemId) throws Exception {
        Timestamp agora = Timestamp.valueOf(LocalDateTime.now());
        Gasto gasto = new Gasto(0, data, valor, itemId, false, agora, agora);
        new GastoDao().inserir(gasto);
        Conta.comGastoInserido(valor);
    }
    
    public static BigDecimal somarValoresDoMesAtual() throws Exception {
        return new GastoDao().somarValoresNoMesAtual();
    }
    
}
