/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * wzReturnDlg.java
 *
 * Created on Jul 31, 2014, 3:42:56 AM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.model.localTableModel;
import com.generic.utils.XTableColumnModel;
import com.generic.utils.utils;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Yusuf
 */
public class wzReturnDlg extends javax.swing.JDialog {

    private localTableModel data_sales = null;
    private MainFrame parentJf = null;
    private localTableModel emptyData = new localTableModel(0);
    private double selectedKeyfld = -1;
    private localTableModel dataItems = new localTableModel();

    /** Creates new form wzReturnDlg */
    public wzReturnDlg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        tblSales.setColumnModel(new XTableColumnModel());
        tblSales.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                cmdNext.setEnabled(false);
                if (tblSales.getSelectedRow() > -1) {
                    cmdNext.setEnabled(true);
                }

            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlSalesSelection = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSales = new javax.swing.JTable();
        pnlSaleItems = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pnlParas1 = new javax.swing.JPanel();
        pnl_paras = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cmdBack = new javax.swing.JButton();
        cmdNext = new javax.swing.JButton();
        cmdCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblSales.setModel(new javax.swing.table.DefaultTableModel(
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
        tblSales.setRowHeight(24);
        tblSales.setShowHorizontalLines(false);
        tblSales.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tblSales);

        javax.swing.GroupLayout pnlSalesSelectionLayout = new javax.swing.GroupLayout(pnlSalesSelection);
        pnlSalesSelection.setLayout(pnlSalesSelectionLayout);
        pnlSalesSelectionLayout.setHorizontalGroup(
            pnlSalesSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );
        pnlSalesSelectionLayout.setVerticalGroup(
            pnlSalesSelectionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Sales", pnlSalesSelection);

        pnlParas1.setLayout(new java.awt.BorderLayout());

        pnl_paras.setLayout(new java.awt.GridBagLayout());
        pnlParas1.add(pnl_paras, java.awt.BorderLayout.NORTH);

        jScrollPane2.setViewportView(pnlParas1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/check_all.png"))); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/uncheck_all.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon("D:\\yaali\\products\\CHANNEL\\ChannelPOS12\\src\\com\\forms\\panels\\images\\up.png")); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon("D:\\yaali\\products\\CHANNEL\\ChannelPOS12\\src\\com\\forms\\panels\\images\\down.png")); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 277, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton5))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(405, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(15, 15, 15)))
        );

        javax.swing.GroupLayout pnlSaleItemsLayout = new javax.swing.GroupLayout(pnlSaleItems);
        pnlSaleItems.setLayout(pnlSaleItemsLayout);
        pnlSaleItemsLayout.setHorizontalGroup(
            pnlSaleItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
        );
        pnlSaleItemsLayout.setVerticalGroup(
            pnlSaleItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSaleItemsLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab2", pnlSaleItems);

        cmdBack.setText("Back");
        cmdBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBackActionPerformed(evt);
            }
        });

        cmdNext.setText("Next");
        cmdNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdNextActionPerformed(evt);
            }
        });

        cmdCancel.setText("Cancel");
        cmdCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(249, Short.MAX_VALUE)
                .addComponent(cmdBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdNext)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCancel)
                .addGap(23, 23, 23))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdNext)
                    .addComponent(cmdCancel)
                    .addComponent(cmdBack))
                .addContainerGap(11, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 417, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdNextActionPerformed
        do_next();
}//GEN-LAST:event_cmdNextActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        chkUnCheckAll(true);
}//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        chkUnCheckAll(false);
}//GEN-LAST:event_jButton4ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (jScrollPane1.getVerticalScrollBar().getValue() > 40) {
            jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getValue() - 50);
        }
}//GEN-LAST:event_jButton6ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getValue() + 50);
}//GEN-LAST:event_jButton5ActionPerformed

    private void cmdCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_cmdCancelActionPerformed

    private void cmdBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBackActionPerformed
        do_back();
    }//GEN-LAST:event_cmdBackActionPerformed
    public void setParentJf(MainFrame parentJf) {
        this.parentJf = parentJf;
        jTabbedPane1.removeAll();
        jTabbedPane1.addTab("Sales", pnlSalesSelection);
        try {
            if (data_sales == null) {

                data_sales = new localTableModel();
                data_sales.createDBClassFromConnection(parentJf.getDbConneciton());
            }

            tblSales.setModel(emptyData);
            data_sales.executeQuery("select KEYFLD,INVOICE_NO,inv_ref Customer_Code,inv_refnm Customer_Name,INVOICE_DATE,"
                    + " DECODE(INVOICE_CODE, 10, (inv_amt + ADD_CHARGE) - disc_amt, 30, (inv_amt + ADD_CHARGE) - disc_amt, 20, "
                    + " ((inv_amt - disc_amt) * -1)) AMOUNT "
                    + " from pospur1,INVOICETYPE "
                    + " where INVOICETYPE.NO=TYPE AND INVOICE_CODE=30  AND "
                    + " POSPUR1.flag=1 "
                    + " and POSPUR1.LOCATION_CODE=INVOICETYPE.LOCATION_CODE "
                    + " and POSPUR1.LOCATION_CODE='" + parentJf.getSpr().dataLocationCode + "'"
                    + " ORDER BY INVOICE_NO", true);
            data_sales.getColByName("AMOUNT").setNumberFormat(utils.FORMAT_MONEY);
            data_sales.getColByName("AMOUNT").setAlignmnet(JLabel.RIGHT);
            tblSales.setModel(data_sales);
            data_sales.setEditAllowed(false);
            setSize(600, 600);
            setLocationRelativeTo(null);
            cmdBack.setEnabled(false);
            if (tblSales.getSelectedRow() < 0) {
                cmdNext.setEnabled(false);
            }
            setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(wzReturnDlg.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdBack;
    private javax.swing.JButton cmdCancel;
    private javax.swing.JButton cmdNext;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlParas1;
    private javax.swing.JPanel pnlSaleItems;
    private javax.swing.JPanel pnlSalesSelection;
    private javax.swing.JPanel pnl_paras;
    private javax.swing.JTable tblSales;
    // End of variables declaration//GEN-END:variables

    private void do_next() {
        try {
            if (jTabbedPane1.getSelectedComponent() == pnlSaleItems) {
                if (genReturn() <= 0) {
                    JOptionPane.showMessageDialog(this, "Must select records !");
                    return;
                }
                setVisible(false);
                parentJf.getSpr().cmdClose.doClick();
            }

            if (jTabbedPane1.getSelectedComponent() == pnlSalesSelection) {
                jTabbedPane1.removeAll();
                jTabbedPane1.addTab("Select Items", pnlSaleItems);
                selectedKeyfld = Double.valueOf(data_sales.getFieldValue(tblSales.getSelectedRow(), "KEYFLD").toString());
                show_return_items();
            }


        } catch (SQLException ex) {
            Logger.getLogger(wzReturnDlg.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }


    }

    private void show_return_items() throws SQLException {
        cmdBack.setEnabled(true);
        pnl_paras.removeAll();
        GridBagConstraints gc = new GridBagConstraints();
        int i = 0;
        int i1 = 0;
        dataItems.setDbclass(data_sales.getDbclass());
        dataItems.executeQuery("select p2.itempos,p2.refer, p2.allqty/p2.pack pack_qty,"
                + " p2.size_of_descr,items.descr,'' chk,PRICE from pospur2 p2,items "
                + " where reference=refer and p2.keyfld='" + selectedKeyfld
                + "'  order by p2.itempos ", true);

        for (int j = 0; j < dataItems.getRowCount(); j++) {
            String s = j + " - " + dataItems.getFieldValue(j, "DESCR").toString() + " - "
                    + dataItems.getFieldValue(j, "SIZE_OF_DESCR").toString();
            JCheckBox jr = new JCheckBox(s);
            jr.setSelected(true);
            jr.setActionCommand(j + "");
            jr.setFont(new Font("Dialog", Font.BOLD, 14));
            gc.gridx = 0;
            gc.gridy = j;
            gc.insets = new Insets(10, 10, 10, 10);
            gc.gridwidth = 1;
            gc.weightx = 0.0;
            gc.fill = GridBagConstraints.HORIZONTAL;
            pnl_paras.add(jr, gc);
            dataItems.setFieldValue(j, "CHK", jr);
        }
        pnl_paras.updateUI();
    }

    public void chkUnCheckAll(boolean t) {
        for (int i = 0; i < dataItems.getRowCount(); i++) {
            ((JCheckBox) dataItems.getFieldValue(i, "CHK")).setSelected(t);
        }
    }

    public int genReturn() {

        if (parentJf.cp != parentJf.getSpr()) {
            return -1;
        }
        parentJf.getSpr().load_data("");
        parentJf.getSpr().varSaleKeyfld = selectedKeyfld + "";
        int rc = 0;
        for (int i = 0; i < dataItems.getRowCount(); i++) {
            boolean t = ((JCheckBox) dataItems.getFieldValue(i, "CHK")).isSelected();
            if (t) {
                try {
                    String itm = dataItems.getFieldValue(i, "REFER").toString();
                    String ctg = dataItems.getFieldValue(i, "SIZE_OF_DESCR").toString();
                    double qty = Double.valueOf(dataItems.getFieldValue(i, "PACK_QTY").toString());
                    double pr = Double.valueOf(dataItems.getFieldValue(i, "PRICE").toString());
                    parentJf.getSpr().addUpdate(itm, ctg, qty, pr);
                    rc++;
                } catch (Exception ex) {
                    Logger.getLogger(frmSelReturned.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        parentJf.getSpr().getJTable1().updateUI();
        return rc;
    }

    private void do_back() {
        if (jTabbedPane1.getSelectedComponent() == pnlSaleItems) {
            jTabbedPane1.removeAll();
            jTabbedPane1.addTab("Sales", pnlSalesSelection);
        }
    }
}
