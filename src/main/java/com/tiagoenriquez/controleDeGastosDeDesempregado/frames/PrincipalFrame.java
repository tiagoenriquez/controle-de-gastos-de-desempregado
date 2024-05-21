/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tiagoenriquez.controleDeGastosDeDesempregado.frames;


import com.tiagoenriquez.controleDeGastosDeDesempregado.clipboards.GastoClipboard;
import com.tiagoenriquez.controleDeGastosDeDesempregado.clipboards.ItemClipboard;
import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Conta;
import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Gasto;
import com.tiagoenriquez.controleDeGastosDeDesempregado.models.Item;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tiago
 */
public final class PrincipalFrame extends javax.swing.JFrame {
    
    private boolean contaFrameAberto = false;
    private boolean informacaoFrameAberto = false;
    private boolean escreverGeneroEItem = true;
    private final Date hoje = new Date(new java.util.Date().getTime());
    private final Locale brasilLocale = new Locale("pt", "BR");
    private final NumberFormat numberFormat = NumberFormat.getCurrencyInstance(brasilLocale);
    private final MensagemPane mensagemPane;
    private List<Item> itens;

    /**
     * Creates new form PrincipalFrame
     */
    public PrincipalFrame() {
        initComponents();
        setLocationRelativeTo(this);
        this.mensagemPane = new MensagemPane();
        try {
            this.gastoDateChooser.setDate(hoje);
            this.alimentarItensComboBox();
            this.alimentarGenerosComboBox();
            this.alimentarGastosTable(hoje);
            this.selecionarMes();
            this.alimentarMesesCombobox();
            this.alimentarSaldoLabel();
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    public void alimentarSaldoLabel() {
        try {
            Conta conta = Conta.procurar();
            this.saldoLabel.setText("Saldo: " + NumberFormat
                    .getCurrencyInstance(brasilLocale).format(conta.getSaldo()));
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    public void fecharContaFrame() {
        this.contaFrameAberto = false;
    }
    
    public void fecharInformacoesFrame() {
        this.informacaoFrameAberto = false;
    }
    
    private int itemId() {
        int itemId = 0;
        for (Item item : this.itens) {
            if (item.getNome().equals(itensComboBox.getSelectedItem().toString())) {
                itemId = item.getId();
            }
        }
        return itemId;
    }
    
    private void alimentarGastosTable(Date hoje) {
        try {
            List<Gasto> gastos = Gasto.doMes(hoje);
            DefaultTableModel gastoTableModel
                    = (DefaultTableModel) this.gastosTable.getModel();
            gastoTableModel.setRowCount(0);
            for (Gasto gasto : gastos) {
                gastoTableModel.addRow(gasto.dados());
            }
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void alimentarGenerosComboBox() {
        try {
            this.escreverGeneroEItem = false;
            List<String> generos = Item.generos();
            this.generosComboBox.removeAllItems();
            this.generosComboBox.addItem("");
            for (String genero : generos) {
                this.generosComboBox.addItem(genero);
            }
            this.escreverGeneroEItem = true;
        } catch (Exception e) {
            this.mensagemPane.mostrarMensagemDeErro(e.getMessage());
        }
    }
    
    private void alimentarItensComboBox() {
        try {
            List<Item> itens = Item.todos();
            this.itens = itens;
            this.itensComboBox.removeAllItems();
            this.itensComboBox.addItem("");
            for (Item item : itens) {
                this.itensComboBox.addItem(item.getNome());
            }
        } catch (Exception e) {
            this.mensagemPane.mostrarMensagemDeErro(e.getMessage());
        }
    }
    
    private void alimentarMesesCombobox() {
        try {
            List<java.util.Date> meses = Gasto.meses();
            this.mesesComboBox.removeAllItems();
            for (java.util.Date mes : meses) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
                this.mesesComboBox.addItem(simpleDateFormat.format(mes));
            }
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void atualizarGasto() {
        try {
            int id = Integer.parseInt(this.idDeGastoTextField.getText());
            Date data = new Date(this.gastoDateChooser.getDate().getTime());
            String valorString = this.valorTextField.getText();
            BigDecimal valor = new BigDecimal(this.numberFormat.parse(valorString).doubleValue());
            int itemId = this.itemId();
            Gasto.atualizar(id, data, valor, itemId);
            this.mensagemPane.mostrarMensagemDeSucesso("Gasto atualizado com sucesso");
            this.alimentarGastosTable(this.hoje);
            this.alimentarSaldoLabel();
            this.habilitarInsercao(true);
            this.limparCampos();
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void atualizarItem() {
        try {
            int id = Integer.parseInt(this.idDeItemTextField.getText());
            String nome = this.nomeDeItemTextField.getText();
            String genero = this.generoTextField.getText();
            Item.atualizar(id, nome, genero);
            this.alimentarItensComboBox();
            this.alimentarGenerosComboBox();
            this.alimentarMesesCombobox();
            this.alimentarGastosTable(this.hoje);
            this.mensagemPane.mostrarMensagemDeSucesso("Item atualizado com sucesso.");
            this.habilitarInsercao(true);
            this.limparCampos();
        } catch (Exception ex) {
            this.mensagemPane.mostrarMensagemDeErro(ex.getMessage());
        }
    }
    
    private void copiarGastos() {
        try {
            GastoClipboard gastoClipboard = new GastoClipboard();
            gastoClipboard.copiar();
            this.mensagemPane.mostrarMensagemDeSucesso("Gastos copiados com sucesso.");
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void copiarItens() {
        try {
            ItemClipboard itemClipboard = new ItemClipboard();
            itemClipboard.copiar();
            this.mensagemPane.mostrarMensagemDeSucesso("Itens copiados com sucesso.");
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void excluirGasto() {
        try {
            int id = Integer.parseInt(this.idDeGastoTextField.getText());
            Gasto.excluir(id);
            this.alimentarMesesCombobox();
            this.alimentarSaldoLabel();
            this.alimentarGastosTable(this.hoje);
            this.mensagemPane.mostrarMensagemDeSucesso("Gasto excluído com sucesso");
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        } finally {
            this.habilitarInsercao(true);   
            this.limparCampos();         
        }
    }
    
    private void excluirItem() {
        try {
            int id = Integer.parseInt(this.idDeItemTextField.getText());
            Item.excluido(id);
            this.alimentarItensComboBox();
            this.mensagemPane.mostrarMensagemDeSucesso("Item excluído com sucesso");
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        } finally {
            this.habilitarInsercao(true);    
            this.limparCampos();        
        }
    }
    
    private void habilitarInsercao(boolean sim) {
        this.cadastrarGastoButton.setEnabled(sim);
        this.cadastrarItemButton.setEnabled(sim);
        this.atualizarGastoButton.setEnabled(!sim);
        this.atualizarItemButton.setEnabled(!sim);
        this.excluirGastoButton.setEnabled(!sim);
        this.excluirItemButton.setEnabled(!sim);
    }
    
    private void inserirGasto() {
        try {
            Date data = new Date(this.gastoDateChooser.getDate().getTime());
            BigDecimal valor = new BigDecimal(this.valorTextField.getText().replace(",", "."));
            int itemId = this.itemId();
            Gasto.inserir(data, valor, itemId);
            this.mensagemPane.mostrarMensagemDeSucesso("Gasto inserido com sucesso.");
            this.alimentarMesesCombobox();
            this.alimentarSaldoLabel();
            this.alimentarGastosTable(this.hoje);
            this.limparCampos();
        } catch (Exception exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void inserirItem() {
        try {
            String nome = this.nomeDeItemTextField.getText();
            String genero = this.generoTextField.getText();
            Item.inserir(nome, genero);
            this.alimentarItensComboBox();
            this.alimentarGenerosComboBox();
            this.alimentarItensComboBox();
            this.alimentarGenerosComboBox();
            this.mensagemPane.mostrarMensagemDeSucesso("Item inserido com sucesso");
            this.limparCampos();
        } catch (Exception ex) {
            this.mensagemPane.mostrarMensagemDeErro(ex.getMessage());
        }
    }
    
    private void preencherEditarGastoPanel(Gasto gasto) {
        try {
            this.idDeGastoTextField.setText(String.valueOf(gasto.getId()));
            this.gastoDateChooser.setDate(gasto.getData());
            this.valorTextField.setText(numberFormat.format(gasto.getValor()));
            this.itensComboBox.setSelectedItem(gasto.getItem().getNome());
            this.cadastrarGastoButton.setEnabled(false);
            this.atualizarGastoButton.setEnabled(true);
            this.excluirGastoButton.setEnabled(true);
        } catch (Exception e) {
            this.mensagemPane.mostrarMensagemDeErro(e.getMessage());
        }
    }
    
    private void limparCampos() {
        this.idDeGastoTextField.setText("");
        this.gastoDateChooser.setDate(this.hoje);
        this.valorTextField.setText("");
        this.idDeItemTextField.setText("");
        this.nomeDeItemTextField.setText("");
        this.generoTextField.setText("");
    }
    
    private void mostrarGastosDoMes() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
            String mesString = this.mesesComboBox.getSelectedItem().toString();
            Date mes = new Date(simpleDateFormat.parse(mesString).getTime());
            this.alimentarGastosTable(mes);
        } catch (ParseException exception) {
            this.mensagemPane.mostrarMensagemDeErro(exception.getMessage());
        }
    }
    
    private void preencherItemPanel(Item item) {
        try {
            this.idDeItemTextField.setText(String.valueOf(item.getId()));
            this.nomeDeItemTextField.setText(item.getNome());
            this.generosComboBox.setSelectedItem(item.getGenero());
            this.generoTextField.setText(item.getGenero());
            this.cadastrarItemButton.setEnabled(false);
            this.atualizarItemButton.setEnabled(true);
            this.excluirItemButton.setEnabled(true);
        } catch (Exception e) {
            this.mensagemPane.mostrarMensagemDeErro(e.getMessage());
        }
    }
    
    private void procurarGasto() {        
        try {
            JTable table = this.gastosTable;
            int id = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 0).toString());
            Gasto gasto = Gasto.procurar(id);
            this.preencherEditarGastoPanel(gasto);
            this.habilitarInsercao(false);
        } catch (Exception ex) {
            this.mensagemPane.mostrarMensagemDeErro(ex.getMessage());
        }
    }
    
    private void procurarItem() {
        try {
            JTable table = this.gastosTable;
            Object idObject = table.getValueAt(table.getSelectedRow(), 1);
            String idString = idObject.toString();
            int id = Integer.parseInt(idString);
            Item item = Item.procurar(id);
            this.preencherItemPanel(item);
            this.habilitarInsercao(false);
        } catch (Exception ex) {
            this.mensagemPane.mostrarMensagemDeErro(ex.getMessage());
        }
    }
    
    private void selecionarMes() {
        java.util.Date data = new java.util.Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        this.mesesComboBox.setSelectedItem(simpleDateFormat.format(data));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        gastoPanel = new javax.swing.JPanel();
        tituloDoGastoPanelLabel = new javax.swing.JLabel();
        idDeGastoLabel = new javax.swing.JLabel();
        idDeGastoTextField = new javax.swing.JTextField();
        dataGastoLabel = new javax.swing.JLabel();
        gastoDateChooser = new com.toedter.calendar.JDateChooser();
        valorLabel = new javax.swing.JLabel();
        valorTextField = new javax.swing.JTextField();
        itemDeGastoLabel = new javax.swing.JLabel();
        itensComboBox = new javax.swing.JComboBox<>();
        cadastrarGastoButton = new javax.swing.JButton();
        atualizarGastoButton = new javax.swing.JButton();
        excluirGastoButton = new javax.swing.JButton();
        itemPanel = new javax.swing.JPanel();
        tituloDoItemPanelLabel = new javax.swing.JLabel();
        idDeItemLabel = new javax.swing.JLabel();
        idDeItemTextField = new javax.swing.JTextField();
        nomeDeItemLabel = new javax.swing.JLabel();
        nomeDeItemTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        generosComboBox = new javax.swing.JComboBox<>();
        generoTextField = new javax.swing.JTextField();
        cadastrarItemButton = new javax.swing.JButton();
        atualizarItemButton = new javax.swing.JButton();
        excluirItemButton = new javax.swing.JButton();
        gastosPanel = new javax.swing.JPanel();
        tituloDaGastosPanelLabel = new javax.swing.JLabel();
        mesSeletorLabel = new javax.swing.JLabel();
        mesesComboBox = new javax.swing.JComboBox<>();
        gastosScrollPane = new javax.swing.JScrollPane();
        gastosTable = new javax.swing.JTable();
        mostrarGastosButton = new javax.swing.JButton();
        saldoLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        contaMenu = new javax.swing.JMenu();
        atualizarContaMenuItem = new javax.swing.JMenuItem();
        informacoesMenu = new javax.swing.JMenu();
        informacoesMenuItem = new javax.swing.JMenuItem();
        copiaMenu = new javax.swing.JMenu();
        copiarGastoMenuItem = new javax.swing.JMenuItem();
        copiarItemMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Controle de Gastos");
        setPreferredSize(new java.awt.Dimension(1268, 672));
        setResizable(false);

        gastoPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gastoPanel.setPreferredSize(new java.awt.Dimension(512, 304));

        tituloDoGastoPanelLabel.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tituloDoGastoPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDoGastoPanelLabel.setText("Edição de Gastos");
        tituloDoGastoPanelLabel.setPreferredSize(new java.awt.Dimension(480, 32));

        idDeGastoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        idDeGastoLabel.setText("Id");
        idDeGastoLabel.setPreferredSize(new java.awt.Dimension(48, 32));

        idDeGastoTextField.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        idDeGastoTextField.setEnabled(false);
        idDeGastoTextField.setPreferredSize(new java.awt.Dimension(416, 32));

        dataGastoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dataGastoLabel.setText("Data");
        dataGastoLabel.setPreferredSize(new java.awt.Dimension(48, 32));

        gastoDateChooser.setPreferredSize(new java.awt.Dimension(416, 32));

        valorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        valorLabel.setText("Valor");
        valorLabel.setPreferredSize(new java.awt.Dimension(48, 32));

        valorTextField.setPreferredSize(new java.awt.Dimension(416, 32));

        itemDeGastoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        itemDeGastoLabel.setText("Item");
        itemDeGastoLabel.setPreferredSize(new java.awt.Dimension(48, 32));

        itensComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        itensComboBox.setPreferredSize(new java.awt.Dimension(416, 32));

        cadastrarGastoButton.setText("Cadastrar");
        cadastrarGastoButton.setMinimumSize(new java.awt.Dimension(96, 32));
        cadastrarGastoButton.setPreferredSize(new java.awt.Dimension(149, 32));
        cadastrarGastoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirGasto(evt);
            }
        });
        cadastrarGastoButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cadastrarKeyPressed(evt);
            }
        });

        atualizarGastoButton.setText("Atualizar");
        atualizarGastoButton.setEnabled(false);
        atualizarGastoButton.setPreferredSize(new java.awt.Dimension(150, 32));
        atualizarGastoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarGasto(evt);
            }
        });
        atualizarGastoButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                atualizarGastoKeyPressed(evt);
            }
        });

        excluirGastoButton.setText("Excluir");
        excluirGastoButton.setEnabled(false);
        excluirGastoButton.setPreferredSize(new java.awt.Dimension(149, 32));
        excluirGastoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirGasto(evt);
            }
        });
        excluirGastoButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                excluirGastoKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout gastoPanelLayout = new javax.swing.GroupLayout(gastoPanel);
        gastoPanel.setLayout(gastoPanelLayout);
        gastoPanelLayout.setHorizontalGroup(
            gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gastoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gastoPanelLayout.createSequentialGroup()
                        .addComponent(cadastrarGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(atualizarGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(excluirGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(gastoPanelLayout.createSequentialGroup()
                            .addComponent(idDeGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(16, 16, 16)
                            .addComponent(idDeGastoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(tituloDoGastoPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(gastoPanelLayout.createSequentialGroup()
                            .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(valorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dataGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(itemDeGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(16, 16, 16)
                            .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(gastoDateChooser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(valorTextField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(itensComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        gastoPanelLayout.setVerticalGroup(
            gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gastoPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(tituloDoGastoPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDeGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idDeGastoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gastoDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(valorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(valorTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(itemDeGastoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itensComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(gastoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cadastrarGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(atualizarGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(excluirGastoButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        itemPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        itemPanel.setPreferredSize(new java.awt.Dimension(512, 304));

        tituloDoItemPanelLabel.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tituloDoItemPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDoItemPanelLabel.setText("Edição de Item");
        tituloDoItemPanelLabel.setPreferredSize(new java.awt.Dimension(480, 32));

        idDeItemLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        idDeItemLabel.setText("Id");
        idDeItemLabel.setPreferredSize(new java.awt.Dimension(64, 32));

        idDeItemTextField.setEnabled(false);
        idDeItemTextField.setPreferredSize(new java.awt.Dimension(400, 32));

        nomeDeItemLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nomeDeItemLabel.setText("Nome");
        nomeDeItemLabel.setPreferredSize(new java.awt.Dimension(64, 32));

        nomeDeItemTextField.setPreferredSize(new java.awt.Dimension(400, 32));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Gênero");
        jLabel2.setPreferredSize(new java.awt.Dimension(64, 80));

        generosComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        generosComboBox.setPreferredSize(new java.awt.Dimension(400, 32));
        generosComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selecionarGenero(evt);
            }
        });

        generoTextField.setPreferredSize(new java.awt.Dimension(400, 32));
        generoTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirItemGeneroTextField(evt);
            }
        });

        cadastrarItemButton.setText("Cadastrar");
        cadastrarItemButton.setPreferredSize(new java.awt.Dimension(149, 32));
        cadastrarItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inserirItem(evt);
            }
        });
        cadastrarItemButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cadastrarItemKeyPressed(evt);
            }
        });

        atualizarItemButton.setText("Atualizar");
        atualizarItemButton.setEnabled(false);
        atualizarItemButton.setPreferredSize(new java.awt.Dimension(150, 32));
        atualizarItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarItem(evt);
            }
        });
        atualizarItemButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                atualizarItemKeyPressed(evt);
            }
        });

        excluirItemButton.setText("Excluir");
        excluirItemButton.setEnabled(false);
        excluirItemButton.setPreferredSize(new java.awt.Dimension(149, 32));
        excluirItemButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                excluirItem(evt);
            }
        });
        excluirItemButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                excluirItemKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout itemPanelLayout = new javax.swing.GroupLayout(itemPanel);
        itemPanel.setLayout(itemPanelLayout);
        itemPanelLayout.setHorizontalGroup(
            itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(itemPanelLayout.createSequentialGroup()
                        .addComponent(nomeDeItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(nomeDeItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(itemPanelLayout.createSequentialGroup()
                        .addComponent(idDeItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(idDeItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tituloDoItemPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(itemPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(generoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(itemPanelLayout.createSequentialGroup()
                        .addComponent(cadastrarItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(atualizarItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(excluirItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        itemPanelLayout.setVerticalGroup(
            itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(itemPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(tituloDoItemPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idDeItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idDeItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nomeDeItemLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nomeDeItemTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(itemPanelLayout.createSequentialGroup()
                        .addComponent(generosComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(16, 16, 16)
                .addGroup(itemPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cadastrarItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(atualizarItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(excluirItemButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        gastosPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gastosPanel.setPreferredSize(new java.awt.Dimension(744, 608));

        tituloDaGastosPanelLabel.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        tituloDaGastosPanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tituloDaGastosPanelLabel.setText("Lista de Gastos");
        tituloDaGastosPanelLabel.setPreferredSize(new java.awt.Dimension(712, 32));

        mesSeletorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mesSeletorLabel.setText("Escolha um mês");
        mesSeletorLabel.setPreferredSize(new java.awt.Dimension(136, 32));

        mesesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "03/2024", "04/2024", "05/2024", "06/2024", " " }));
        mesesComboBox.setPreferredSize(new java.awt.Dimension(112, 32));

        gastosScrollPane.setPreferredSize(new java.awt.Dimension(712, 480));

        gastosTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id do Gasto", "Id do Item", "Nome do Item", "Data", "Valor", "Gênero"
            }
        ));
        gastosTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                selecionarGasto(evt);
            }
        });
        gastosScrollPane.setViewportView(gastosTable);

        mostrarGastosButton.setText("Mostrar Gastos do Mês");
        mostrarGastosButton.setPreferredSize(new java.awt.Dimension(232, 32));
        mostrarGastosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mostrarGastosDoMes(evt);
            }
        });
        mostrarGastosButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MostrarGastosDoMesKeyPressed(evt);
            }
        });

        saldoLabel.setText("Saldo: R$99.999,99");
        saldoLabel.setPreferredSize(new java.awt.Dimension(184, 32));

        javax.swing.GroupLayout gastosPanelLayout = new javax.swing.GroupLayout(gastosPanel);
        gastosPanel.setLayout(gastosPanelLayout);
        gastosPanelLayout.setHorizontalGroup(
            gastosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gastosPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(gastosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gastosScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(gastosPanelLayout.createSequentialGroup()
                        .addComponent(mesSeletorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(mesesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(mostrarGastosButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tituloDaGastosPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        gastosPanelLayout.setVerticalGroup(
            gastosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gastosPanelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(tituloDaGastosPanelLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(gastosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gastosPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(mesSeletorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mesesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(mostrarGastosButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(saldoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16)
                .addComponent(gastosScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        menuBar.setMinimumSize(new java.awt.Dimension(1820, 32));
        menuBar.setPreferredSize(new java.awt.Dimension(1820, 32));

        contaMenu.setText("Conta");

        atualizarContaMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        atualizarContaMenuItem.setText("Atualizar Conta");
        atualizarContaMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                atualizarConta(evt);
            }
        });
        contaMenu.add(atualizarContaMenuItem);

        menuBar.add(contaMenu);

        informacoesMenu.setText("Informações");

        informacoesMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
        informacoesMenuItem.setText("ObterInformacoes");
        informacoesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirInformacoesFrame(evt);
            }
        });
        informacoesMenu.add(informacoesMenuItem);

        menuBar.add(informacoesMenu);

        copiaMenu.setText("Copiar");

        copiarGastoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        copiarGastoMenuItem.setText("Gastos");
        copiarGastoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarGastos(evt);
            }
        });
        copiaMenu.add(copiarGastoMenuItem);

        copiarItemMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        copiarItemMenuItem.setText("Itens");
        copiarItemMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiarItens(evt);
            }
        });
        copiaMenu.add(copiarItemMenuItem);

        menuBar.add(copiaMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gastoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(gastosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(gastosPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gastoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(itemPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inserirItem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirItem
        // TODO add your handling code here:
        this.inserirItem();
    }//GEN-LAST:event_inserirItem

    private void inserirItemGeneroTextField(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirItemGeneroTextField
        // TODO add your handling code here:
        this.inserirItem();
    }//GEN-LAST:event_inserirItemGeneroTextField

    private void selecionarGenero(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selecionarGenero
        // TODO add your handling code here:
        if (this.escreverGeneroEItem) {
            String genero = this.generosComboBox.getSelectedItem().toString();
            this.generoTextField.setText(genero);
        }
    }//GEN-LAST:event_selecionarGenero

    private void inserirGasto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inserirGasto
        // TODO add your handling code here:
        this.inserirGasto();
    }//GEN-LAST:event_inserirGasto

    private void cadastrarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cadastrarKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.inserirGasto();
        }
    }//GEN-LAST:event_cadastrarKeyPressed

    private void mostrarGastosDoMes(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mostrarGastosDoMes
        // TODO add your handling code here:
        this.mostrarGastosDoMes();
    }//GEN-LAST:event_mostrarGastosDoMes

    private void atualizarConta(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarConta
        // TODO add your handling code here:
        if (!this.contaFrameAberto) {
            new ContaFrame(this).setVisible(true);
            this.contaFrameAberto = true;
        }
    }//GEN-LAST:event_atualizarConta

    private void selecionarGasto(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selecionarGasto
        // TODO add your handling code here:
        this.procurarGasto();
        this.procurarItem();
    }//GEN-LAST:event_selecionarGasto

    private void atualizarGasto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarGasto
        // TODO add your handling code here:
        this.atualizarGasto();
    }//GEN-LAST:event_atualizarGasto

    private void atualizarItem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_atualizarItem
        // TODO add your handling code here:
        this.atualizarItem();
    }//GEN-LAST:event_atualizarItem

    private void excluirGasto(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirGasto
        // TODO add your handling code here:
        this.excluirGasto();
    }//GEN-LAST:event_excluirGasto

    private void excluirItem(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_excluirItem
        // TODO add your handling code here:
        this.excluirItem();
    }//GEN-LAST:event_excluirItem

    private void cadastrarItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cadastrarItemKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.inserirItem();
        }
    }//GEN-LAST:event_cadastrarItemKeyPressed

    private void atualizarGastoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atualizarGastoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.atualizarGasto();
        }
    }//GEN-LAST:event_atualizarGastoKeyPressed

    private void excluirGastoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_excluirGastoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.excluirGasto();
        }
    }//GEN-LAST:event_excluirGastoKeyPressed

    private void atualizarItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_atualizarItemKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.atualizarItem();
        }
    }//GEN-LAST:event_atualizarItemKeyPressed

    private void excluirItemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_excluirItemKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.excluirItem();
        }
    }//GEN-LAST:event_excluirItemKeyPressed

    private void MostrarGastosDoMesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MostrarGastosDoMesKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == 10) {
            this.mostrarGastosDoMes();
        }
    }//GEN-LAST:event_MostrarGastosDoMesKeyPressed

    private void abrirInformacoesFrame(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirInformacoesFrame
        // TODO add your handling code here:
        if (!this.informacaoFrameAberto) {
            new InformacoesFrame(this).setVisible(true);
            this.informacaoFrameAberto = true;
        }
    }//GEN-LAST:event_abrirInformacoesFrame

    private void copiarGastos(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarGastos
        // TODO add your handling code here:
        this.copiarGastos();
    }//GEN-LAST:event_copiarGastos

    private void copiarItens(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiarItens
        // TODO add your handling code here:
        this.copiarItens();
    }//GEN-LAST:event_copiarItens

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem atualizarContaMenuItem;
    private javax.swing.JButton atualizarGastoButton;
    private javax.swing.JButton atualizarItemButton;
    private javax.swing.JButton cadastrarGastoButton;
    private javax.swing.JButton cadastrarItemButton;
    private javax.swing.JMenu contaMenu;
    private javax.swing.JMenu copiaMenu;
    private javax.swing.JMenuItem copiarGastoMenuItem;
    private javax.swing.JMenuItem copiarItemMenuItem;
    private javax.swing.JLabel dataGastoLabel;
    private javax.swing.JButton excluirGastoButton;
    private javax.swing.JButton excluirItemButton;
    private com.toedter.calendar.JDateChooser gastoDateChooser;
    private javax.swing.JPanel gastoPanel;
    private javax.swing.JPanel gastosPanel;
    private javax.swing.JScrollPane gastosScrollPane;
    private javax.swing.JTable gastosTable;
    private javax.swing.JTextField generoTextField;
    private javax.swing.JComboBox<String> generosComboBox;
    private javax.swing.JLabel idDeGastoLabel;
    private javax.swing.JTextField idDeGastoTextField;
    private javax.swing.JLabel idDeItemLabel;
    private javax.swing.JTextField idDeItemTextField;
    private javax.swing.JMenu informacoesMenu;
    private javax.swing.JMenuItem informacoesMenuItem;
    private javax.swing.JLabel itemDeGastoLabel;
    private javax.swing.JPanel itemPanel;
    private javax.swing.JComboBox<String> itensComboBox;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel mesSeletorLabel;
    private javax.swing.JComboBox<String> mesesComboBox;
    private javax.swing.JButton mostrarGastosButton;
    private javax.swing.JLabel nomeDeItemLabel;
    private javax.swing.JTextField nomeDeItemTextField;
    private javax.swing.JLabel saldoLabel;
    private javax.swing.JLabel tituloDaGastosPanelLabel;
    private javax.swing.JLabel tituloDoGastoPanelLabel;
    private javax.swing.JLabel tituloDoItemPanelLabel;
    private javax.swing.JLabel valorLabel;
    private javax.swing.JTextField valorTextField;
    // End of variables declaration//GEN-END:variables
}
