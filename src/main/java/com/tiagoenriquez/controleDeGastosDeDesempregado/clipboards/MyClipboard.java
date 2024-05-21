/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.clipboards;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 *
 * @author Tiago Enriquez Tachy
 */
public abstract class MyClipboard {
    
    protected String conteudoASerCopiado;
    
    public void copiar() {
        StringSelection stringSelection = new StringSelection(this.conteudoASerCopiado);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
    
}
