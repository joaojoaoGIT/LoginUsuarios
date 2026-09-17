/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.sql.*;
import dal.Mod_conexao;
import javax.swing.JOptionPane;

public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    public TelaCliente() {
        initComponents();
        conexao = Mod_conexao.conector();
    }

    // ================== MÉTODOS CRUD ==================
    // CONSULTAR
    private void consultar() {
        String sql = "SELECT * FROM tb_clientes WHERE id_cliente = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtIdCliente.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtNomeCliente.setText(rs.getString("nome_cliente"));
                txtEndeCliente.setText(rs.getString("endereco_cliente"));
                txtCidadeCliente.setText(rs.getString("cidade_cliente"));
                txtUfCliente.setText(rs.getString("uf_cliente"));
                txtCpfCliente.setText(rs.getString("cpf_cliente"));
                txtTeleCliente.setText(rs.getString("telefone_cliente"));
                txtDatNascCliente.setText(rs.getString("dat_nasc_cliente"));
            } else {
                JOptionPane.showMessageDialog(null, "CLIENTE NÃO CADASTRADO...");
                // Limpa os campos
                txtNomeCliente.setText(null);
                txtEndeCliente.setText(null);
                txtCidadeCliente.setText(null);
                txtUfCliente.setText(null);
                txtCpfCliente.setText(null);
                txtTeleCliente.setText(null);
                txtDatNascCliente.setText(null);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    // ADICIONAR
    private void adicionar() {
        String sql = "INSERT INTO tb_clientes "
                + "(nome_cliente, endereco_cliente, cidade_cliente, uf_cliente, cpf_cliente, telefone_cliente, dat_nasc_cliente) "
                + "VALUES (?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEndeCliente.getText());
            pst.setString(3, txtCidadeCliente.getText());
            pst.setString(4, txtUfCliente.getText());
            pst.setString(5, txtCpfCliente.getText());
            pst.setString(6, txtTeleCliente.getText());
            pst.setString(7, txtDatNascCliente.getText());

            int adicionado = pst.executeUpdate(); // retorna 1 se estiver correto

            if (adicionado > 0) {
                JOptionPane.showMessageDialog(null, "Cliente Cadastrado com Sucesso!");
                // Limpa o formulário
                txtNomeCliente.setText(null);
                txtEndeCliente.setText(null);
                txtCidadeCliente.setText(null);
                txtUfCliente.setText(null);
                txtCpfCliente.setText(null);
                txtTeleCliente.setText(null);
                txtDatNascCliente.setText(null);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    // ALTERAR
    private void alterar() {
        String sql = "UPDATE tb_clientes SET nome_cliente = ?, endereco_cliente = ?, "
                + "cidade_cliente = ?, uf_cliente = ?, cpf_cliente = ?, telefone_cliente = ?, "
                + "dat_nasc_cliente = ? WHERE id_cliente = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEndeCliente.getText());
            pst.setString(3, txtCidadeCliente.getText());
            pst.setString(4, txtUfCliente.getText());
            pst.setString(5, txtCpfCliente.getText());
            pst.setString(6, txtTeleCliente.getText());
            pst.setString(7, txtDatNascCliente.getText());
            pst.setString(8, txtIdCliente.getText());

            int alterado = pst.executeUpdate();

            if (alterado > 0) {
                JOptionPane.showMessageDialog(null, "CLIENTE ALTERADO COM SUCESSO");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao alterar cliente: " + e.getMessage());
        }
    }

    // APAGAR
    private void apagar() {
        int confirma = JOptionPane.showConfirmDialog(null,
                "Tem certeza que deseja excluir este cliente?", "ATENÇÃO",
                JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM tb_clientes WHERE id_cliente = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtIdCliente.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "CLIENTE APAGADO");
                    // Limpa o formulário
                    txtNomeCliente.setText(null);
                    txtEndeCliente.setText(null);
                    txtCidadeCliente.setText(null);
                    txtUfCliente.setText(null);
                    txtCpfCliente.setText(null);
                    txtTeleCliente.setText(null);
                    txtDatNascCliente.setText(null);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        txtNomeCliente = new javax.swing.JTextField();
        txtEndeCliente = new javax.swing.JTextField();
        txtCidadeCliente = new javax.swing.JTextField();
        txtUfCliente = new javax.swing.JTextField();
        txtCpfCliente = new javax.swing.JTextField();
        txtTeleCliente = new javax.swing.JTextField();
        txtDatNascCliente = new javax.swing.JTextField();
        btnAdicionarCliente = new javax.swing.JButton();
        btnEditarCliente = new javax.swing.JButton();
        btnVizualizarCliente = new javax.swing.JButton();
        btnApagarCliente = new javax.swing.JButton();

        jLabel10.setText("jLabel10");

        jLabel1.setText("Cadastro clientes");

        jLabel2.setText("ID:");

        jLabel3.setText("Nome:");

        jLabel4.setText("Endereco:");

        jLabel5.setText("Cidade;");

        jLabel6.setText("UF:");

        jLabel7.setText("CPF:");

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

        txtCidadeCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCidadeClienteActionPerformed(evt);
            }
        });

        txtUfCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUfClienteActionPerformed(evt);
            }
        });

        txtCpfCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfClienteActionPerformed(evt);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTeleCliente, javax.swing.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                    .addComponent(txtCpfCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUfCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCidadeCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEndeCliente)
                    .addComponent(txtDatNascCliente))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(198, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnAdicionarCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVizualizarCliente))
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnApagarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnEditarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(60, 60, 60))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(175, 175, 175))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
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
                    .addComponent(txtCidadeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtUfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtTeleCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtDatNascCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdicionarCliente)
                    .addComponent(btnEditarCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVizualizarCliente)
                    .addComponent(btnApagarCliente))
                .addGap(0, 36, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTeleClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTeleClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTeleClienteActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void txtNomeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeClienteActionPerformed

    private void txtEndeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEndeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEndeClienteActionPerformed

    private void txtCidadeClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCidadeClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCidadeClienteActionPerformed

    private void txtUfClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUfClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUfClienteActionPerformed

    private void txtCpfClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfClienteActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdicionarCliente;
    private javax.swing.JButton btnApagarCliente;
    private javax.swing.JButton btnEditarCliente;
    private javax.swing.JButton btnVizualizarCliente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField txtCidadeCliente;
    private javax.swing.JTextField txtCpfCliente;
    private javax.swing.JTextField txtDatNascCliente;
    private javax.swing.JTextField txtEndeCliente;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNomeCliente;
    private javax.swing.JTextField txtTeleCliente;
    private javax.swing.JTextField txtUfCliente;
    // End of variables declaration//GEN-END:variables
}
