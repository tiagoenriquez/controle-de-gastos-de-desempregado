/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.frames;

import javax.swing.JOptionPane;

/**
 *
 * @author tiago
 */
public class MensagemPane {
    
    void mostrarMensagemDeErro(String erro) {
        JOptionPane.showMessageDialog(null,
                erro,
                "Erro",
                JOptionPane.ERROR_MESSAGE
        );
    }
    
    void mostrarMensagemDeSucesso(String mensagem) {
        JOptionPane.showMessageDialog(
                null,
                mensagem,
                "Operação bem sucedida",
                JOptionPane.INFORMATION_MESSAGE
        );
    }
    
}
