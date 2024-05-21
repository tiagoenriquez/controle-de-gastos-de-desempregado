/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.clipboards;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Item;
import java.util.List;

/**
 *
 * @author Tiago Enriquez Tachy
 */
public class ItemClipboard extends MyClipboard {

    public ItemClipboard() throws Exception {
        this.gerarConteudo();
    }
    
    private void gerarConteudo() throws Exception {
        this.conteudoASerCopiado = "";
        List<Item> itens = Item.todos();
        for (Item item : itens) {
            conteudoASerCopiado += String.valueOf(item.getId()) + "\t";
            conteudoASerCopiado += item.getNome() + "\t";
            conteudoASerCopiado += item.getGenero() + "\t";
            conteudoASerCopiado += String.valueOf(item.getCreatedAt()) + "\t";
            conteudoASerCopiado += String.valueOf(item.getUpdatedAt()) + "\t";
            conteudoASerCopiado += "\n";
        }
    }
    
}
