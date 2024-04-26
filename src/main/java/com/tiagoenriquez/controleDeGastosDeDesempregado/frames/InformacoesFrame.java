/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.frames;

import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Conta;
import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Expectativa;
import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Gasto;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author tiago
 */
public class InformacoesFrame extends javax.swing.JFrame {
    
    private final MensagemPane mensagemPane;
    private final Locale brasilLocale = new Locale("pt", "BR");
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brasilLocale);
    private final PrincipalFrame principalFrame;

    /**
     * Creates new form InformacoesFrame
     * @param principalFrame
     */
    public InformacoesFrame(PrincipalFrame principalFrame) {
        this.mensagemPane = new MensagemPane();
        this.principalFrame = principalFrame;
        initComponents();
        this.setLocationRelativeTo(this);
        this.preencherSaldo();
        this.preencherGastosNoMes();
    }
    
    private void preencherGastosNoMes() {
        try {
            BigDecimal gastos = Gasto.somarValoresDoMesAtual();
            String gastosString = this.numberFormat.format(gastos);
            this.gastosNoMesTextField.setText(gastosString);
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void preencherSaldo() {
        try {
            Conta conta = Conta.procurar();
            String saldoString = numberFormat.format(conta.getSaldo());
            this.saldoTextField.setText(saldoString);
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void calcular() {
        try {
            String mesString = this.mesesComboBox.getSelectedItem().toString();
            String anoString = this.anoTextField.getText();
            String saldoString = this.saldoTextField.getText();
            String gastoNoMesString = this.gastosNoMesTextField.getText();
            int anoInt = Integer.parseInt(anoString) - 1900;
            int mesInt = Integer.parseInt(mesString);
            Date data = new Date(anoInt, mesInt, 1);
            data.setDate(data.getDate() - 1);
            Date hoje = new Date();
            double saldoDouble = this.numberFormat.parse(saldoString).doubleValue();
            double gastosNoMesDouble = this.numberFormat.parse(gastoNoMesString).doubleValue();
            BigDecimal saldo = new BigDecimal(saldoDouble);
            BigDecimal gastosNoMes = new BigDecimal(gastosNoMesDouble);
            Expectativa expectativa = new Expectativa(data, saldo, gastosNoMes, hoje);
            String quantoPorMesString = this.numberFormat.format(expectativa.getQuantoPorMes());
            this.quantoPorMesTextField.setText(quantoPorMesString);
            String quantoPorDiaString = this.numberFormat.format(expectativa.getQuantoPorDia());
            this.quantoPorDiaTextField.setText(quantoPorDiaString);
        } catch (NumberFormatException numberFormatException) {
            this.mensagemPane.mostrarMensagemDeErro("Ano preenchido de maneira incorreta.");
        } catch (ParseException ex) {
            this.mensagemPane.mostrarMensagemDeErro("Erro ao converter dados monetários.");
        }
    }
    
    private void calcularInformandoQuantoPorMes() {
        try {
            String quantoPorMesString = this.quantoPorMesTextField.getText();
            BigDecimal quantoPorMes = new BigDecimal(quantoPorMesString.replace(",", "."));
            String saldoString = this.saldoTextField.getText();
            BigDecimal saldo = new BigDecimal(this.numberFormat.parse(saldoString).doubleValue());
            String gastoNoMesString = this.gastosNoMesTextField.getText();
            BigDecimal gastosNoMes = new BigDecimal(this.numberFormat.parse(gastoNoMesString).doubleValue());
            Date hoje = new Date();
            Expectativa expectativa = new Expectativa(quantoPorMes, saldo, gastosNoMes, hoje);
            Date dataDaExpectativa = expectativa.getData();
            int mesDaExpectativa = dataDaExpectativa.getMonth() + 1;
            int anoDaExpectativa = dataDaExpectativa.getYear();
            this.mesesComboBox.setSelectedItem(String.format("%2s", String.valueOf(mesDaExpectativa)).replace(" ", "0"));
            this.anoTextField.setText(String.valueOf(anoDaExpectativa));
            String quantoPorDiaString = this.numberFormat.format(expectativa.getQuantoPorDia());
            this.quantoPorDiaTextField.setText(quantoPorDiaString);
        } catch (ParseException ex) {
            this.mensagemPane.mostrarMensagemDeErro("Erro ao converter dados monetários.");
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

        informacoesPanel = new javax.swing.JPanel();
        saldoLabel = new javax.swing.JLabel();
        gastosNoMesLabel = new javax.swing.JLabel();
        expectativaLabel = new javax.swing.JLabel();
        quantoPorMesLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        mesesComboBox = new javax.swing.JComboBox<>();
        anoTextField = new javax.swing.JTextField();
        saldoTextField = new javax.swing.JLabel();
        gastosNoMesTextField = new javax.swing.JLabel();
        quantoPorMesTextField = new javax.swing.JTextField();
        calcularButton = new javax.swing.JButton();
        quantoPorDiaTextField = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Informações");
        setPreferredSize(new java.awt.Dimension(464, 336));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                fechar(evt);
            }
        });

        informacoesPanel.setPreferredSize(new java.awt.Dimension(448, 304));

        saldoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        saldoLabel.setText("Saldo");
        saldoLabel.setPreferredSize(new java.awt.Dimension(224, 32));

        gastosNoMesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        gastosNoMesLabel.setText("Gastos no mês");
        gastosNoMesLabel.setPreferredSize(new java.awt.Dimension(224, 32));

        expectativaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        expectativaLabel.setText("Expectativa de fim do saldo");
        expectativaLabel.setPreferredSize(new java.awt.Dimension(224, 32));

        quantoPorMesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        quantoPorMesLabel.setText("Quanto posso gastar por mês");
        quantoPorMesLabel.setPreferredSize(new java.awt.Dimension(224, 32));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Quanto posso gastar por dia");
        jLabel1.setPreferredSize(new java.awt.Dimension(224, 32));

        mesesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        mesesComboBox.setPreferredSize(new java.awt.Dimension(64, 32));

        anoTextField.setPreferredSize(new java.awt.Dimension(96, 32));
        anoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularInformandoAno(evt);
            }
        });

        saldoTextField.setPreferredSize(new java.awt.Dimension(176, 32));

        gastosNoMesTextField.setPreferredSize(new java.awt.Dimension(176, 32));

        quantoPorMesTextField.setPreferredSize(new java.awt.Dimension(176, 32));
        quantoPorMesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularInformandoQuantoPorMes(evt);
            }
        });

        calcularButton.setText("Calcular quanto posso gastar");
        calcularButton.setPreferredSize(new java.awt.Dimension(416, 32));
        calcularButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcular(evt);
            }
        });
        calcularButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                calcularKeyPressed(evt);
            }
        });

        quantoPorDiaTextField.setPreferredSize(new java.awt.Dimension(176, 32));

        javax.swing.GroupLayout informacoesPanelLayout = new javax.swing.GroupLayout(informacoesPanel);
        informacoesPanel.setLayout(informacoesPanelLayout);
        informacoesPanelLayout.setHorizontalGroup(
            informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacoesPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(calcularButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(informacoesPanelLayout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(quantoPorDiaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(informacoesPanelLayout.createSequentialGroup()
                            .addComponent(quantoPorMesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(quantoPorMesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(informacoesPanelLayout.createSequentialGroup()
                            .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(saldoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(informacoesPanelLayout.createSequentialGroup()
                            .addComponent(expectativaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(mesesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(anoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(informacoesPanelLayout.createSequentialGroup()
                            .addComponent(gastosNoMesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(gastosNoMesTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        informacoesPanelLayout.setVerticalGroup(
            informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(informacoesPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saldoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gastosNoMesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gastosNoMesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(expectativaLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(mesesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(anoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quantoPorMesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantoPorMesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(informacoesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantoPorDiaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(calcularButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(informacoesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(informacoesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fechar(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_fechar
        // TODO add your handling code here:
        this.principalFrame.fecharInformacoesFrame();
    }//GEN-LAST:event_fechar

    private void calcular(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcular
        // TODO add your handling code here:
        this.calcular();
    }//GEN-LAST:event_calcular

    private void calcularInformandoAno(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularInformandoAno
        // TODO add your handling code here:
        this.calcular();
    }//GEN-LAST:event_calcularInformandoAno

    private void calcularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_calcularKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.calcular();
        }
    }//GEN-LAST:event_calcularKeyPressed

    private void calcularInformandoQuantoPorMes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularInformandoQuantoPorMes
        // TODO add your handling code here:
        this.calcularInformandoQuantoPorMes();
    }//GEN-LAST:event_calcularInformandoQuantoPorMes
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField anoTextField;
    private javax.swing.JButton calcularButton;
    private javax.swing.JLabel expectativaLabel;
    private javax.swing.JLabel gastosNoMesLabel;
    private javax.swing.JLabel gastosNoMesTextField;
    private javax.swing.JPanel informacoesPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JComboBox<String> mesesComboBox;
    private javax.swing.JLabel quantoPorDiaTextField;
    private javax.swing.JLabel quantoPorMesLabel;
    private javax.swing.JTextField quantoPorMesTextField;
    private javax.swing.JLabel saldoLabel;
    private javax.swing.JLabel saldoTextField;
    // End of variables declaration//GEN-END:variables
}
