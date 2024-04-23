/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.frames;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Conta;
import java.math.BigDecimal;

/**
 *
 * @author tiago
 */
public class ContaFrame extends javax.swing.JFrame {
    
    private final MensagemPane mensagemPane;
    private final PrincipalFrame principalFrame;

    /**
     * Creates new form ContaFrame
     * @param principalFrame
     */
    public ContaFrame(PrincipalFrame principalFrame) {
        this.principalFrame = principalFrame;
        this.mensagemPane = new MensagemPane();
        initComponents();
        this.setLocationRelativeTo(this);
    }
    
    private void atualizar() {
        try {
            String saldoString = this.saldoTextField.getText().replace(",", ".");
            BigDecimal saldo = new BigDecimal(saldoString);
            Conta.atualizar(saldo);
            this.principalFrame.alimentarSaldoLabel();
            this.mensagemPane.mostrarMensagemDeSucesso("Conta atualizada com sucesso");
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeSucesso(exception.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contaPanel = new javax.swing.JPanel();
        saldoLabel = new javax.swing.JLabel();
        saldoTextField = new javax.swing.JTextField();
        atualizarButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editor de Conta");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                fechar(evt);
            }
        });

        contaPanel.setPreferredSize(new java.awt.Dimension(232, 112));

        saldoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        saldoLabel.setText("Saldo");
        saldoLabel.setPreferredSize(new java.awt.Dimension(64, 32));

        saldoTextField.setPreferredSize(new java.awt.Dimension(120, 32));
        saldoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarTextField(evt);
            }
        });

        atualizarButton.setText("Atualizar");
        atualizarButton.setPreferredSize(new java.awt.Dimension(200, 32));
        atualizarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizar(evt);
            }
        });
        atualizarButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                atualizarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout contaPanelLayout = new javax.swing.GroupLayout(contaPanel);
        contaPanel.setLayout(contaPanelLayout);
        contaPanelLayout.setHorizontalGroup(
            contaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contaPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(contaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(atualizarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(contaPanelLayout.createSequentialGroup()
                        .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(saldoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        contaPanelLayout.setVerticalGroup(
            contaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contaPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(contaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saldoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(atualizarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contaPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechar(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_fechar
        // TODO add your handling code here:
        this.principalFrame.fecharContaFrame();
    }//GEN-LAST:event_fechar

    private void atualizarTextField(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarTextField
        // TODO add your handling code here:
        this.atualizar();
    }//GEN-LAST:event_atualizarTextField

    private void atualizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atualizarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.atualizar();
        }
    }//GEN-LAST:event_atualizarKeyPressed

    private void atualizar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizar
        // TODO add your handling code here:
        this.atualizar();
    }//GEN-LAST:event_atualizar

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton atualizarButton;
    private javax.swing.JPanel contaPanel;
    private javax.swing.JLabel saldoLabel;
    private javax.swing.JTextField saldoTextField;
    // End of variables declaration//GEN-END:variables
}
