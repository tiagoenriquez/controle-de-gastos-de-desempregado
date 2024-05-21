/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.clipboards;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Gasto;
import java.util.List;

/**
 *
 * @author Tiago Enriquez Tachy
 */
public class GastoClipboard extends MyClipboard{
    
    public GastoClipboard() throws Exception {
        this.gerarConteudo();
    }
    
    private void gerarConteudo() throws Exception {
        List<Gasto> gastos = Gasto.todos();
        this.conteudoASerCopiado = "";
        for (Gasto gasto : gastos) {
            this.conteudoASerCopiado += String.valueOf(gasto.getId()) + "\t";
            this.conteudoASerCopiado += String.valueOf(gasto.getData()) + "\t";
            this.conteudoASerCopiado += String.valueOf(gasto.getValor()) + "\t";
            this.conteudoASerCopiado += String.valueOf(gasto.getItemId()) + "\t";
            this.conteudoASerCopiado += String.valueOf(gasto.getCreatedAt()) + "\t";
            this.conteudoASerCopiado += String.valueOf(gasto.getUpdatedAt()) + "\t";
            this.conteudoASerCopiado += "\n";
        }
    }
    
}
