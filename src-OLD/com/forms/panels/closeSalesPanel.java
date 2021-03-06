/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * closeSalesPanel.java
 *
 * Created on 01/05/2010, 06:36:37 م
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.model.DBClass;
import com.generic.model.localTableModel;
import com.generic.utils.XTableColumnModel;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author yusuf
 */
public class closeSalesPanel extends javax.swing.JPanel {

    /** Creates new form closeSalesPanel */
    public closeSalesPanel() {
        initComponents();


    }

    public void setParentJF(MainFrame parentjf) {
        this.parentJf = parentjf;
    }

    public void customIntialize(MainFrame mf) {
        if (dbc == null) {
            try {
                parentJf = mf;
                jTable1.setColumnModel(new XTableColumnModel());
                dbc = tbModel.createDBClassFromConnection(parentJf.getDbConneciton());
                decf = new DecimalFormat(parentJf.getMapVars().get("money_number"));
            } catch (SQLException ex) {
                Logger.getLogger(closeSalesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        load_data();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        cmdRefresh = new javax.swing.JButton();
        txtUntilDate = new javax.swing.JTextField();
        txtcloseDate = new javax.swing.JTextField();
        cmdCloseSale = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txtSum = new javax.swing.JLabel();

        jSplitPane1.setDividerLocation(125);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel1.setText("Untill Date");

        jLabel2.setText("Last date Closed");

        cmdRefresh.setText("Refresh Details");
        cmdRefresh.setMargin(new java.awt.Insets(2, 2, 2, 2));
        cmdRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRefreshActionPerformed(evt);
            }
        });

        txtcloseDate.setEditable(false);
        txtcloseDate.setText("01/01/2001");

        cmdCloseSale.setText("Close Sales");
        cmdCloseSale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseSaleActionPerformed(evt);
            }
        });

        jButton8.setText("Open voucher");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmdRefresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtUntilDate)
                    .addComponent(txtcloseDate, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCloseSale, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                .addComponent(jButton8))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtcloseDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUntilDate, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdCloseSale, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(94, Short.MAX_VALUE)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setTopComponent(jPanel1);

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(25);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        txtSum.setBackground(java.awt.Color.yellow);
        txtSum.setFont(new java.awt.Font("Tahoma", 0, 18));
        txtSum.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtSum.setText("Total : ");
        txtSum.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtSum.setOpaque(true);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSum, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSum, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRefreshActionPerformed
        try {

            tbModel.executeQuery("select KEYFLD,invoice_no,invoice_date,inv_ref,inv_refnm,disc_amt," +
                    " DECODE(INVOICE_CODE,30,(inv_amt+add_charge)-disc_amt,20,(((inv_amt+add_charge)-disc_amt)*-1)) AMOUNT " +
                    " from pospur1 where INVOICE_CODE IN (30,20) AND " +
                    "flag=1 and location_code='" + parentJf.getMapVars().get("DEFAULT_LOCATION") + "'" +
                    " AND INVOICE_DATE<=TO_DATE('" + txtUntilDate.getText() + "','DD/MM/RRRR' ) " +
                    " order by invoice_code,invoice_no", true);
            tbModel.setEditAllowed(false);
            jTable1.getColumnModel().setColumnSelectionAllowed(false);
            jTable1.getTableHeader().setReorderingAllowed(false);
            tbModel.getColByName("AMOUNT").setNumberFormat(decf.toPattern());
            tbModel.getColByName("INVOICE_NO").setAlignmnet(JLabel.CENTER);
            tbModel.getColByName("KEYFLD").setVisible(false);
            tbModel.getVisbleQrycols().remove(tbModel.getColByName("KEYFLD"));
            jTable1.setModel(dfltmodel);
            jTable1.setModel(tbModel);
            for (int colx = 0; colx <
                    jTable1.getModel().getColumnCount(); colx++) {
                jTable1.getColumnModel().getColumn(colx).setCellRenderer(new ColorRenderer(true));
            }

            jTable1.revalidate();
            jTable1.updateUI();
            txtSum.setText("Total : " + decf.format(tbModel.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM)));
        } catch (SQLException ex) {
            Logger.getLogger(closeSalesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJf, ex.getMessage());
        }
}//GEN-LAST:event_cmdRefreshActionPerformed

    private void cmdCloseSaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseSaleActionPerformed
        try {
            dbc.getDbConnection().setAutoCommit(false);
            PreparedStatement ps2 = dbc.getDbConnection().prepareStatement("begin x_post_pos2(?,?,?); end;");
            ps2.setString(1, parentJf.getMapVars().get("DEFAULT_LOCATION"));
            ps2.setString(2, parentJf.getLp().getLogon_user());
            ps2.setTimestamp(3, new Timestamp(dfc.parse(txtUntilDate.getText()).getTime()));
            ps2.executeUpdate();
            JOptionPane.showMessageDialog(parentJf, "...Closed Successfully,Credits To Yusuf Jiruwala (:p ");
            parentJf.getMapVars().put("POS_CLOSE_DATE_" + parentJf.getMapVars().get("DEFAULT_LOCATION"), txtUntilDate.getText());
            ps2.close();
            dbc.getDbConnection().commit();
            load_data();
        } catch (Exception ex) {
            Logger.getLogger(closeSalesPanel.class.getName()).log(Level.SEVERE, null, ex);
            try {
                dbc.getDbConnection().rollback();

            } catch (SQLException ex1) {
                Logger.getLogger(closeSalesPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
            JOptionPane.showMessageDialog(parentJf, ex.getMessage());
        }
    }//GEN-LAST:event_cmdCloseSaleActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            open_vou();
        }
}//GEN-LAST:event_jTable1MouseClicked
    public void open_vou() {
        if (Float.valueOf(tbModel.getFieldValue(jTable1.getSelectedRow(), "AMOUNT").toString()) > 0) {
            String s = tbModel.getFieldValue(jTable1.getSelectedRow(), "KEYFLD").toString();
            parentJf.getCmdSales().doClick();
            parentJf.getSp().load_data(s);
            parentJf.getCmdSales().doClick();
        } else {
            String s = tbModel.getFieldValue(jTable1.getSelectedRow(), "KEYFLD").toString();
            parentJf.getCmdSales().doClick();
            parentJf.getSpr().load_data(s);
            parentJf.getCmdReturn().doClick();

        }
    }

        private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
            open_vou();
}//GEN-LAST:event_jButton8ActionPerformed

    public void load_data() {
        if (parentJf.getMapVars().get("POS_CLOSE_DATE_" + parentJf.getMapVars().get("DEFAULT_LOCATION")) != null) {
            txtcloseDate.setText(parentJf.getMapVars().get("POS_CLOSE_DATE_" + parentJf.getMapVars().get("DEFAULT_LOCATION")));
        }
        txtUntilDate.setText(dfc.format(new Date(System.currentTimeMillis())));
        cmdRefresh.doClick();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdCloseSale;
    private javax.swing.JButton cmdRefresh;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel txtSum;
    private javax.swing.JTextField txtUntilDate;
    private javax.swing.JTextField txtcloseDate;
    // End of variables declaration//GEN-END:variables
    private MainFrame parentJf = null;
    private localTableModel tbModel = new localTableModel();
    private DBClass dbc = null;
    private SimpleDateFormat dfc = new SimpleDateFormat("dd/MM/yyyy");
    private javax.swing.table.DefaultTableModel dfltmodel = new javax.swing.table.DefaultTableModel();
    private DecimalFormat decf = null;
}
