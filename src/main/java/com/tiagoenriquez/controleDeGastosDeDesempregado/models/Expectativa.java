/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.models;

import java.math.BigDecimal;
import java.math.MathContext;
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
        this.calcularQuantoPorMes();
        this.calcularQuantoPorDia();
    }

    public Expectativa(BigDecimal quantoPorMes, BigDecimal saldo, BigDecimal gastosPorMes, Date hoje) {
        this.quantoPorMes = quantoPorMes;
        this.saldo = saldo;
        this.gastosNoMes = gastosPorMes;
        this.hoje = hoje;
        this.calcularData();
        this.calcularQuantoPorDia();
    }

    public Date getData() {
        return this.data;
    }

    public BigDecimal getQuantoPorDia() {
        return this.quantoPorDia;
    }

    public BigDecimal getQuantoPorMes() {
        return this.quantoPorMes;
    }
    
    public int getDiferenca() {
        return this.diferenca;
    }
    
    private void calcularData() {
        int mesesDoIntervalo = this.saldo.divide(this.quantoPorMes, MathContext.DECIMAL128).intValue();
        int anosAMais = mesesDoIntervalo / 12;
        int mesesAMais = mesesDoIntervalo % 12;
        this.data = new Date(this.hoje.getYear() + anosAMais + 1900, this.hoje.getMonth() + mesesAMais, 15);
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
    
    private void calcularQuantoPorDia() {
        Date ultimoDoMes = new Date(this.hoje.getYear(), this.hoje.getMonth(), 0);
        int diasRestantesNoMes = ultimoDoMes.getDate() + 1 - this.hoje.getDate();
        BigDecimal quantoNoMes = this.quantoPorMes.subtract(this.gastosNoMes);
        if (diasRestantesNoMes != 0) {
            this.quantoPorDia = quantoNoMes.divide(new BigDecimal(diasRestantesNoMes), MathContext.DECIMAL128);
        }
    }
    
    private void calcularQuantoPorMes() {
        this.quantoPorMes = this.saldo.divide(new BigDecimal(this.diferenca), MathContext.DECIMAL128);
    }
    
}
