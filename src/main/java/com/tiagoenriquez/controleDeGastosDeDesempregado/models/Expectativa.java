/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.models;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author tiago
 */
public class Expectativa {
    
    private BigDecimal quantoPorDia;
    private BigDecimal quantoPorMes;
    private Date data;
    private final BigDecimal gastosNoMes;
    private final BigDecimal saldo;
    private final Date hoje;
    private int diferenca;

    public Expectativa(Date data, BigDecimal saldo, BigDecimal gastosPorMes, Date hoje) {
        this.data = data;
        this.saldo = saldo;
        this.gastosNoMes = gastosPorMes;
        this.hoje = hoje;
        this.calcularDiferenca();
    }

    public Expectativa(BigDecimal quantoPorMes, BigDecimal saldo, BigDecimal gastosPorMes, Date hoje) {
        this.quantoPorMes = quantoPorMes;
        this.saldo = saldo;
        this.gastosNoMes = gastosPorMes;
        this.hoje = hoje;
        this.calcularDiferenca();
    }
    
    public int getDiferenca() {
        return this.diferenca;
    }
    
    private void calcularDiferenca() {
        this.hoje.setDate(0);
        this.hoje.setMonth(this.hoje.getMonth() + 1);
        Date diferencaDate = new Date(this.data.getTime() - this.hoje.getTime());
        Date zeroDate = new Date(0);
        int diferencaDeAnos = diferencaDate.getYear() - zeroDate.getYear();
        int diferencaDeMeses = diferencaDate.getMonth() - zeroDate.getMonth();
        this.diferenca = diferencaDeAnos * 12 + diferencaDeMeses;
    }
    
    private void calcularPorMes() {
        
    }
    
}
