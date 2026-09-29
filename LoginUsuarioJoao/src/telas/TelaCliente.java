/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import dal.Mod_conexao;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

// VARIÁVEL PARA SABER SE O CLIENTE É PF OU PJ
    String tipoCliente = "PF";

    public TelaCliente() {
        initComponents();
        conexao = Mod_conexao.conector();
        carregarCidades();
    }
    
    // CIDADES - CARREGAR CIDADES DO IBGE


    private void carregarCidades() {

        try {

            // URL da API do IBGE
            URL url = new URL(
                    "https://servicodados.ibge.gov.br/api/v1/localidades/estados/43/municipios"

            );

            // Abre conexão com a API
            HttpURLConnection conexaoIBGE
                    = (HttpURLConnection) url.openConnection();

            // Define o método da requisição
            conexaoIBGE.setRequestMethod("GET");

            // Lê a resposta da API
            BufferedReader leitor = new BufferedReader(
                    new InputStreamReader(
                            conexaoIBGE.getInputStream(),
                            "UTF-8"
                    )
            );

            StringBuilder resposta = new StringBuilder();
            String linha;

            // Junta todas as linhas recebidas
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }

            leitor.close();

            // Limpa as cidades que já estão no ComboBox
            cmbCidade.removeAllItems();

            // Adiciona uma opção inicial
            cmbCidade.addItem("Selecione a cidade");

            // =================================================
            // PEGA O NOME DOS MUNICÍPIOS DA RESPOSTA
            // =================================================
            String json = resposta.toString();

            // Procura pelo campo "nome" no JSON
            String[] partes = json.split("\"nome\":\"");

            for (int i = 1; i < partes.length; i++) {

                // Pega o nome até a próxima aspas
                String cidade = partes[i].split("\"")[0];

                // Adiciona a cidade no ComboBox
                cmbCidade.addItem(cidade);
            }

            // Fecha a conexão
            conexaoIBGE.disconnect();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao carregar cidades: " + e.getMessage()
            );
        }
    }

    // ================== MÉTODOS CRUD ==================
    // CONSULTAR
    private void consultar() {

        // SQL para buscar o cliente pelo ID
        // ATENÇÃO: os nomes abaixo são os nomes da sua tabela
        String sql = "SELECT * FROM tb_clientes WHERE id_cliente = ?";

        try {

            // Prepara o comando SQL
            pst = conexao.prepareStatement(sql);

            // Pega o ID digitado na tela
            pst.setString(1, txtIdCliente.getText());

            // Executa a consulta
            rs = pst.executeQuery();

            // Verifica se encontrou o cliente
            if (rs.next()) {

                // =========================
                // DADOS DO CLIENTE
                // =========================
                txtNomeCliente.setText(
                        rs.getString("nome_cliente")
                );

                txtEndeCliente.setText(
                        rs.getString("endereco_cliente")
                );

                cmbCidade.setSelectedItem(
                        rs.getString("cidade_cliente")
                );

                txtUfCliente.setText(
                        rs.getString("uf_cliente")
                );

                TXTdocumento.setText(
                        rs.getString("cpf_cliente")
                );

                txtTeleCliente.setText(
                        rs.getString("telefone_cliente")
                );

                txtDatNascCliente.setText(
                        rs.getString("dat_nasc_cliente")
                );

                // =========================
                // PF OU PJ
                // =========================
                String tipo = rs.getString("tipo_cliente");

                // Se for PJ
                if ("PJ".equalsIgnoreCase(tipo)) {

                    RB.setSelected(true);
                    tipoCliente = "PJ";

                } else {

                    // Se for PF
                    rbCPF.setSelected(true);
                    tipoCliente = "PF";
                }

                // Mensagem informando o tipo
                if ("PJ".equalsIgnoreCase(tipo)) {

                    JOptionPane.showMessageDialog(
                            null,
                            "Cliente encontrado!\nTipo: Pessoa Jurídica (PJ)"
                    );

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Cliente encontrado!\nTipo: Pessoa Física (PF)"
                    );
                }

            } else {

                // Cliente não encontrado
                JOptionPane.showMessageDialog(
                        null,
                        "CLIENTE NÃO CADASTRADO..."
                );

                // Limpa os campos
                txtNomeCliente.setText(null);
                txtEndeCliente.setText(null);
                cmbCidade.setSelectedItem(null);
                txtUfCliente.setText(null);
                TXTdocumento.setText(null);
                txtTeleCliente.setText(null);
                txtDatNascCliente.setText(null);
            }

        } catch (Exception e) {

            // Mostra o erro caso aconteça
            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao consultar cliente: " + e.getMessage()
            );
        }
    }

    // ADICIONAR
    private void adicionar() {

        // SQL para cadastrar um novo cliente
        // Agora também salva o tipo PF ou PJ
        String sql = "INSERT INTO tb_clientes "
                + "(nome_cliente, endereco_cliente, cidade_cliente, "
                + "uf_cliente, cpf_cliente, telefone_cliente, "
                + "dat_nasc_cliente, tipo_cliente) "
                + "VALUES (?,?,?,?,?,?,?,?)";

        try {

            // Prepara o SQL
            pst = conexao.prepareStatement(sql);

            // PREENCHENDO OS CAMPOS
            pst.setString(
                    1,
                    txtNomeCliente.getText()
            );

            pst.setString(
                    2,
                    txtEndeCliente.getText()
            );

            pst.setString(
                    3,
                    cmbCidade.getSelectedItem().toString()
            );

            pst.setString(
                    4,
                    txtUfCliente.getText()
            );

            // CPF ou CNPJ
            pst.setString(
                    5,
                    TXTdocumento.getText()
            );

            pst.setString(
                    6,
                    txtTeleCliente.getText()
            );

            pst.setString(
                    7,
                    txtDatNascCliente.getText()
            );

            // PF ou PJ
            pst.setString(
                    8,
                    tipoCliente
            );

            // Executa o INSERT
            int adicionado = pst.executeUpdate();

            // =========================
            // RESULTADO
            // =========================
            if (adicionado > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "Cliente cadastrado com sucesso!\n"
                        + "Tipo: "
                        + (tipoCliente.equals("PF")
                        ? "Pessoa Física (PF)"
                        : "Pessoa Jurídica (PJ)")
                );

                // Limpa os campos
                txtNomeCliente.setText(null);
                txtEndeCliente.setText(null);
                cmbCidade.setSelectedItem(null);
                txtUfCliente.setText(null);
                TXTdocumento.setText(null);
                txtTeleCliente.setText(null);
                txtDatNascCliente.setText(null);

            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao adicionar cliente: "
                    + e.getMessage()
            );
        }
    }

    // ALTERAR
    private void alterar() {

        // SQL para alterar o cliente
        // Também altera o tipo PF ou PJ
        String sql = "UPDATE tb_clientes SET "
                + "nome_cliente = ?, "
                + "endereco_cliente = ?, "
                + "cidade_cliente = ?, "
                + "uf_cliente = ?, "
                + "cpf_cliente = ?, "
                + "telefone_cliente = ?, "
                + "dat_nasc_cliente = ?, "
                + "tipo_cliente = ? "
                + "WHERE id_cliente = ?";

        try {

            // Prepara o SQL
            pst = conexao.prepareStatement(sql);

            // PREENCHENDO OS CAMPOS
            pst.setString(
                    1,
                    txtNomeCliente.getText()
            );

            pst.setString(
                    2,
                    txtEndeCliente.getText()
            );

            pst.setString(
                    3,
                    cmbCidade.getSelectedItem().toString()
            );

            pst.setString(
                    4,
                    txtUfCliente.getText()
            );

            // CPF ou CNPJ
            pst.setString(
                    5,
                    TXTdocumento.getText()
            );

            pst.setString(
                    6,
                    txtTeleCliente.getText()
            );

            pst.setString(
                    7,
                    txtDatNascCliente.getText()
            );

            // PF ou PJ
            pst.setString(
                    8,
                    tipoCliente
            );

            // ID do cliente que será alterado
            pst.setString(
                    9,
                    txtIdCliente.getText()
            );

            // Executa o UPDATE
            int alterado = pst.executeUpdate();

            // Verifica se alterou
            if (alterado > 0) {

                JOptionPane.showMessageDialog(
                        null,
                        "CLIENTE ALTERADO COM SUCESSO!\n"
                        + "Tipo: "
                        + (tipoCliente.equals("PF")
                        ? "Pessoa Física (PF)"
                        : "Pessoa Jurídica (PJ)")
                );
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "Erro ao alterar cliente: "
                    + e.getMessage()
            );
        }
    }

// APAGAR 
    private void apagar() {

        // Pergunta se realmente deseja excluir
        int confirma = JOptionPane.showConfirmDialog(
                null,
                "Tem certeza que deseja excluir este cliente?",
                "ATENÇÃO",
                JOptionPane.YES_NO_OPTION
        );

        // Se clicou em SIM
        if (confirma == JOptionPane.YES_OPTION) {

            // SQL para excluir pelo ID
            String sql = "DELETE FROM tb_clientes "
                    + "WHERE id_cliente = ?";

            try {

                // Prepara o SQL
                pst = conexao.prepareStatement(sql);

                // ID do cliente
                pst.setString(
                        1,
                        txtIdCliente.getText()
                );

                // Executa o DELETE
                int apagado = pst.executeUpdate();

                if (apagado > 0) {

                    JOptionPane.showMessageDialog(
                            null,
                            "CLIENTE APAGADO COM SUCESSO!"
                    );

                    // Limpa os campos
                    txtNomeCliente.setText(null);
                    txtEndeCliente.setText(null);
                    cmbCidade.setSelectedItem(null);
                    txtUfCliente.setText(null);
                    TXTdocumento.setText(null);
                    txtTeleCliente.setText(null);
                    txtDatNascCliente.setText(null);
                }

            } catch (Exception e) {

                JOptionPane.showMessageDialog(
                        null,
                        "Erro ao apagar cliente: "
                        + e.getMessage()
                );
            }
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

        jLabel10 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        txtEndeCliente = new javax.swing.JTextField();
        txtUfCliente = new javax.swing.JTextField();
        txtTeleCliente = new javax.swing.JTextField();
        txtDatNascCliente = new javax.swing.JTextField();
        btnAdicionarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnVizualizarCliente = new javax.swing.JButton();
        btnApagarCliente = new javax.swing.JButton();
        cmbCidade = new javax.swing.JComboBox<>();
        RB = new javax.swing.JRadioButton();
        rbCPF = new javax.swing.JRadioButton();
        TXTdocumento = new javax.swing.JTextField();

        jLabel10.setText("jLabel10");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setText("Cadastro clientes");

        jLabel2.setText("ID:");

        jLabel3.setText("Nome:");

        jLabel4.setText("Endereco:");

        jLabel5.setText("Cidade:");

        jLabel6.setText("UF:");

        jLabel8.setText("Telefone:");

        jLabel9.setText("DataNasc:");

        txtIdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdClienteActionPerformed(evt);
            }
        });

        txtNomeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeClienteActionPerformed(evt);
            }
        });

        txtEndeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEndeClienteActionPerformed(evt);
            }
        });

        txtUfCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfClienteActionPerformed(evt);
            }
        });

        txtTeleCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTeleClienteActionPerformed(evt);
            }
        });

        txtDatNascCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDatNascClienteActionPerformed(evt);
            }
        });

        btnAdicionarCliente.setText("Adicionar");
        btnAdicionarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarClienteActionPerformed(evt);
            }
        });

        btnEditarCliente.setText("Editar");
        btnEditarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarClienteActionPerformed(evt);
            }
        });

        btnVizualizarCliente.setText("Vizualizar");
        btnVizualizarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVizualizarClienteActionPerformed(evt);
            }
        });

        btnApagarCliente.setText("Apagar");
        btnApagarCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApagarClienteActionPerformed(evt);
            }
        });

        cmbCidade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione a cidade", "Taquara", "Parobé", "Igrejinha", "Três Coroas", "Gramado", "Canela" }));
        cmbCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCidadeActionPerformed(evt);
            }
        });

        buttonGroup1.add(RB);
        RB.setText("CNPJ");
        RB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RBActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbCPF);
        rbCPF.setText("CPF");
        rbCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbCPFActionPerformed(evt);
            }
        });

        TXTdocumento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TXTdocumentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnApagarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAdicionarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnVizualizarCliente)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(40, 40, 40)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5)
                                .addComponent(jLabel4)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(rbCPF, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(RB)
                                .addComponent(jLabel8))
                            .addGap(36, 36, 36)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                                .addComponent(txtNomeCliente)
                                .addComponent(txtEndeCliente)
                                .addComponent(cmbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtUfCliente)
                                .addComponent(TXTdocumento)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(39, 39, 39)
                            .addComponent(jLabel9)
                            .addGap(42, 42, 42)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTeleCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                                .addComponent(txtDatNascCliente)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 257, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(255, 255, 255))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtEndeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cmbCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(TXTdocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbCPF)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(RB)
                        .addGap(18, 18, 18)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTeleCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDatNascCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarCliente)
                    .addComponent(btnEditarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVizualizarCliente)
                    .addComponent(btnApagarCliente))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTeleClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeleClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleClienteActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
        consultar();
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtEndeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndeClienteActionPerformed

    private void txtUfClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfClienteActionPerformed

    private void txtDatNascClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDatNascClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDatNascClienteActionPerformed

    private void btnAdicionarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarClienteActionPerformed
        adicionar();
    }//GEN-LAST:event_btnAdicionarClienteActionPerformed

    private void btnEditarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarClienteActionPerformed
        alterar();
    }//GEN-LAST:event_btnEditarClienteActionPerformed

    private void btnVizualizarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVizualizarClienteActionPerformed
        consultar();
    }//GEN-LAST:event_btnVizualizarClienteActionPerformed

    private void btnApagarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApagarClienteActionPerformed
        apagar();
    }//GEN-LAST:event_btnApagarClienteActionPerformed

    private void cmbCidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCidadeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCidadeActionPerformed

    private void RBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RBActionPerformed
        // ============================================
        // CLIENTE É PESSOA JURÍDICA
        // ============================================

        tipoCliente = "PJ";

        // Limpa o campo do documento
        TXTdocumento.setText("");

        // Mostra mensagem
        JOptionPane.showMessageDialog(
                null,
                "Tipo selecionado: Pessoa Jurídica (PJ)"
        );
    }//GEN-LAST:event_RBActionPerformed

    private void rbCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbCPFActionPerformed
        // ============================================
        // CLIENTE É PESSOA FÍSICA
        // ============================================

        tipoCliente = "PF";

        // Limpa o campo do documento
        TXTdocumento.setText("");

        // Mostra mensagem
        JOptionPane.showMessageDialog(
                null,
                "Tipo selecionado: Pessoa Física (PF)"
        );
    }//GEN-LAST:event_rbCPFActionPerformed

    private void TXTdocumentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TXTdocumentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TXTdocumentoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton RB;
    private javax.swing.JTextField TXTdocumento;
    private javax.swing.JButton btnAdicionarCliente;
    private javax.swing.JButton btnApagarCliente;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnVizualizarCliente;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cmbCidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbCPF;
    private javax.swing.JTextField txtDatNascCliente;
    private javax.swing.JTextField txtEndeCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtTeleCliente;
    private javax.swing.JTextField txtUfCliente;
    // End of variables declaration//GEN-END:variables
}
