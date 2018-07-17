/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DeliveryPanel.java
 *
 * Created on Aug 9, 2011, 7:28:27 PM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.model.localTableModel;
import com.generic.utils.QueryExe;
import com.generic.utils.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Yusuf
 */
public class DeliveryPanel extends javax.swing.JPanel {

    /** Creates new form DeliveryPanel */
    public DeliveryPanel() {
        initComponents();
        tbl_invs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                try {
                    show_data();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(parentJF, ex.getMessage());
                }
            }
        });
        txtAddCharges.setInputVerifier(number_iv);
        txtDiscount.setInputVerifier(number_iv);
    }
    private InputVerifier number_iv = new InputVerifier() {

        @Override
        public boolean verify(JComponent input) {
            JTextField txt = (JTextField) input;
            if (txt.getText().length() == 0) {
                return true;
            }

            boolean ret = false;
            try {
                (new DecimalFormat(utils.FORMAT_MONEY)).parse(txt.getText());
                ret = true;
            } catch (ParseException ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                //JOptionPane.showMessageDialog(parentJF, ex.getMessage());
                ret = false;
            }
            return ret;
        }
    };

    public void show_items() throws SQLException {
        if (parentJF == null || con == null) {
            return;
        }
        if (tbl_invs.getSelectedRow() < 0) {
            return;
        }
        double kf = Double.valueOf(data.getFieldValue(tbl_invs.getSelectedRow(), "KEYFLD").toString());
        data_items.clearALl();
        tbl_items.removeAll();

        data_items.executeQuery("select pospur2.*,items.descr,((pospur2.price/pospur2.pack)*pospur2.allqty) amount,allqty/pospur2.pack PACK_QTY "
                + "from pospur2,items where reference=refer and POSPUR2.keyfld=" + kf + " order by itempos", new String[]{"REFER", "DESCR", "PACK_QTY", "AMOUNT"});
        tbl_items.setModel(nullModel2);
        data_items.getColByName("AMOUNT").setNumberFormat(utils.FORMAT_MONEY);
        data_items.getColByName("AMOUNT").setAlignmnet(JLabel.RIGHT);
        data_items.getColByName("PACK_QTY").setAlignmnet(JLabel.CENTER);
        tbl_items.setModel(data_items);


    }

    public void show_data()
            throws SQLException {
        resetForm(false);
        if (parentJF == null || con == null) {
            return;
        }
        if (tbl_invs.getSelectedRow() < 0) {
            return;
        }

        double kf = Double.valueOf(data.getFieldValue(tbl_invs.getSelectedRow(), "KEYFLD").toString());
        if (ps_data == null) {
            ps_data = con.prepareStatement("select pospur1.*,(inv_amt+nvl(add_charge,0))-disc_amt net_amount from pospur1 where keyfld=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        }
        ps_data.setDouble(1, kf);
        ResultSet rs = ps_data.executeQuery();
        if (!rs.first()) {
            load_data();
            throw new SQLException("not found record, some one may have removed it.");
        }

        txtNo.setText(rs.getString("INVOICE_NO"));
        txtComments.setText(rs.getString("CTG"));
        txtDescr.setSelectedItem(rs.getString("MEMO"));
        txtDate.setText(((new SimpleDateFormat(utils.FORMAT_SHORT_DATE)).format(rs.getDate("INVOICE_DATE"))));
        varGrossAmt = rs.getDouble("INV_AMT");
        varDiscAmt = rs.getDouble("DISC_AMT");
        varPaidAmt = rs.getDouble("net_amount");
        varAddCharge = rs.getDouble("ADD_CHARGE");
        updateTextField();
        show_items();

    }

    private void updateTextField() {
        txtGrossAmt.setText(((new DecimalFormat(utils.FORMAT_MONEY)).format(varGrossAmt)));
        txtDiscount.setText(((new DecimalFormat(utils.FORMAT_MONEY)).format(varDiscAmt)));
        txtAddCharges.setText(((new DecimalFormat(utils.FORMAT_MONEY)).format(varAddCharge)));
        txtPaidAmt.setText(((new DecimalFormat(utils.FORMAT_MONEY)).format(varPaidAmt)));

    }

    private void updateData() {
        try {
            varAddCharge = ((new DecimalFormat(utils.FORMAT_MONEY)).parse(txtAddCharges.getText())).doubleValue();
            varDiscAmt = ((new DecimalFormat(utils.FORMAT_MONEY)).parse(txtDiscount.getText())).doubleValue();
            //varGrossAmt = ((new DecimalFormat(utils.FORMAT_MONEY)).parse(txtGrossAmt.getText())).doubleValue();
            varPaidAmt = (varGrossAmt + varAddCharge) - varDiscAmt;


        } catch (ParseException ex) {
            Logger.getLogger(DeliveryPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetForm() {
        resetForm(true);
    }

    public void resetForm(boolean all) {
        txtNo.setText("0");
        txtComments.setText("");
        txtDate.setText("");
        txtDescr.setSelectedItem("");
        txtDiscount.setText(new DecimalFormat(utils.FORMAT_MONEY).format(0));
        txtGrossAmt.setText(new DecimalFormat(utils.FORMAT_MONEY).format(0));
        txtPaidAmt.setText(new DecimalFormat(utils.FORMAT_MONEY).format(0));
        txtAddCharges.setText(new DecimalFormat(utils.FORMAT_MONEY).format(0));
        if (all) {
            tbl_invs.removeAll();
            data.clearALl();
        }
        data_items.clearALl();
        tbl_items.removeAll();
    }

    public void load_data() {
        DecimalFormat decf = new DecimalFormat(utils.FORMAT_MONEY);
        resetForm();

        try {
            data.executeQuery("select POSPUR1.KEYFLD,SALESP.NAME DELIVER_PERSON,INVOICE_NO,inv_ref Customer_Code,inv_refnm Customer_Name,INVOICE_DATE"
                    + " from pospur1,SALESP "
                    + " where INVOICE_CODE IN (30) AND SLSMN=SALESP.NO"
                    + " and POSPUR1.LOCATION_CODE='" + parentJF.getMapVars().get("DEFAULT_LOCATION") + "' "
                    + " and  PAIDAMT2=0"
                    + " ORDER BY POSPUR1.KEYFLD", true);

            data.getColByName("INVOICE_NO").setAlignmnet(JLabel.CENTER);

            data.getColByName("KEYFLD").setVisible(false);
            data.getColByName("INVOICE_DATE").setDateFormat(utils.FORMAT_SHORT_DATE);
            data.getVisbleQrycols().remove(data.getColByName("KEYFLD"));
            data.setEditAllowed(false);

            tbl_invs.setModel(nullModel);
            tbl_invs.setModel(data);

            tbl_invs.getColumnModel().setColumnSelectionAllowed(false);
            tbl_invs.getTableHeader().setReorderingAllowed(false);


        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(parentJF, ex);
        }

    }

    public void setParentJf(MainFrame frm) {
        this.parentJF = frm;
        this.con = frm.getDbConneciton();
        if (data.getDbclass() == null) {
            try {
                data.createDBClassFromConnection(parentJF.getDbConneciton());
                data_items.createDBClassFromConnection(parentJF.getDbConneciton());

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        int divider = 400;
        int row_size = 20;
        if (parentJF.getMapVars().get("DELIVERY_SCREEN_DIVIDER") != null) {
            divider = Integer.valueOf(parentJF.getMapVars().get("DELIVERY_SCREEN_DIVIDER"));
        }
        if (parentJF.getMapVars().get("DELIVERY_MAIN_ROW_HEIGHT") != null) {
            row_size = Integer.valueOf(parentJF.getMapVars().get("DELIVERY_MAIN_ROW_HEIGHT"));
        }

        jSplitPane1.setDividerLocation(divider);
        tbl_invs.setRowHeight(row_size);
        tbl_invs.setShowGrid(false);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_invs = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_items = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtGrossAmt = new javax.swing.JTextField();
        txtDescr = new javax.swing.JComboBox();
        txtDiscount = new javax.swing.JTextField();
        txtComments = new javax.swing.JTextField();
        txtAddCharges = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtPaidAmt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        cmdSave = new javax.swing.JButton();
        cmdRevert = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(550);

        tbl_invs.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tbl_invs);

        jSplitPane1.setLeftComponent(jScrollPane1);

        tbl_items.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_items);

        jSplitPane1.setRightComponent(jScrollPane2);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setText("No");

        txtNo.setEditable(false);

        jLabel2.setText("Comments");

        txtDate.setEditable(false);
        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });

        jLabel3.setText("Date");

        txtGrossAmt.setBackground(new java.awt.Color(51, 255, 255));
        txtGrossAmt.setEditable(false);
        txtGrossAmt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txtDescr.setEditable(true);

        txtDiscount.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtDiscount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDiscountActionPerformed(evt);
            }
        });
        txtDiscount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiscountFocusLost(evt);
            }
        });

        txtAddCharges.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtAddCharges.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAddChargesActionPerformed(evt);
            }
        });
        txtAddCharges.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAddChargesFocusLost(evt);
            }
        });

        jLabel5.setText("Gross Amount");

        jLabel6.setText("Discount");

        jLabel7.setText("Additional Charges");

        jLabel8.setText("Paid Amount");

        jLabel9.setText("Discount Descr");

        txtPaidAmt.setBackground(java.awt.Color.yellow);
        txtPaidAmt.setEditable(false);
        txtPaidAmt.setFont(new java.awt.Font("Tahoma", 1, 18));
        txtPaidAmt.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txtPaidAmt.setText("0");
        txtPaidAmt.setToolTipText("");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(72, 72, 72)
                        .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel3)
                        .addGap(43, 43, 43)
                        .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComments, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                            .addComponent(txtDescr, 0, 321, Short.MAX_VALUE))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtPaidAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtAddCharges, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtGrossAmt, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                            .addComponent(txtDiscount, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel1)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrossAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtDescr, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtComments, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(txtAddCharges, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel8)
                    .addComponent(txtPaidAmt, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 50, 5));

        cmdSave.setText("Save & Print");
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        jPanel1.add(cmdSave);

        cmdRevert.setText("   Revert Changes   ");
        cmdRevert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRevertActionPerformed(evt);
            }
        });
        jPanel1.add(cmdRevert);

        jPanel2.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 686, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed

    if (parentJF == null || con == null) {
        return;
    }
    if (tbl_invs.getSelectedRow() < 0) {
        return;
    }
    varPaidAmt = (varGrossAmt + varAddCharge) - varDiscAmt;
    try {
        con.setAutoCommit(false);
        if (varPaidAmt < 0) {
            throw new SQLException("Paid amount can not be minus");
        }
        double kf = Double.valueOf(data.getFieldValue(tbl_invs.getSelectedRow(), "KEYFLD").toString());
        String cd = (String) data.getFieldValue(tbl_invs.getSelectedRow(), "CUSTOMER_CODE");
        String sq_p1 = "update pospur1 set memo=:DESCR, ctg=:COMMENT,PAIDAMT2=:PAIDAMT2,"
                + "DISC_AMT=:DISC_AMT, ADD_CHARGE=:ADD_CHARGE where keyfld=:KEYFLD";
        String sq_c = "begin update poscustomer SET SPEC_COMMENTS=:SPEC_COMMENTS where code=:CODE ; "
                + " delete from POSPAYMENTS where VOU_keyfld=:KEYFLD ; "
                + "insert into POSPAYMENTS(VOU_KEYFLD, TYPE_NO, REMARKS, AMOUNT, ACCNO, ACCNAME) "
                + " SELECT POSPUR1.KEYFLD,POSPUR1.TYPE,'',(INV_AMT+ADD_CHARGE)-DISC_AMT,ACACCOUNT.ACCNO,ACACCOUNT.NAME ACNAME "
                + " FROM POSPUR1,ACACCOUNT,INVOICETYPE WHERE POSPUR1.KEYFLD=:KEYFLD AND INVOICETYPE.NO=POSPUR1.TYPE AND "
                + " POSPUR1.LOCATION_CODE=INVOICETYPE.LOCATION_CODE AND INVOICETYPE.ACCNO=ACACCOUNT.ACCNO;"
                + "end;";

        QueryExe qe1 = new QueryExe(sq_p1, con);
        QueryExe qe2 = new QueryExe(sq_c, con);

        qe1.setParaValue("DESCR", "");
        if (txtDescr.getSelectedItem() != null) {
            qe1.setParaValue("DESCR", txtDescr.getSelectedItem().toString());
        }


        qe1.setParaValue(
                "COMMENT", txtComments.getText());
        qe1.setParaValue(
                "PAIDAMT2", varPaidAmt);
        qe1.setParaValue(
                "DISC_AMT", varDiscAmt);
        qe1.setParaValue(
                "ADD_CHARGE", varAddCharge);
        qe1.setParaValue("KEYFLD", kf);
        qe1.execute(true);
        qe1.close();

        qe2.setParaValue("SPEC_COMMENTS", txtComments.getText());
        qe2.setParaValue("KEYFLD", kf);
        qe2.setParaValue("CODE", cd);
        qe2.execute();
        qe2.close();
        con.commit();
        JOptionPane.showMessageDialog(parentJF, "Update Done Successfully");
        load_data();
        parentJF.displayDeliveryStatus(false);
        //    qe1.setParaValue("", txt);                        
    } catch (SQLException ex) {
        try {
            Logger.getLogger(DeliveryPanel.class.getName()).log(Level.SEVERE, null, ex);
            con.rollback();
        } catch (SQLException ex1) {
        }
    }
}//GEN-LAST:event_cmdSaveActionPerformed
private void cmdRevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRevertActionPerformed
    load_data();
}//GEN-LAST:event_cmdRevertActionPerformed

private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_txtDateActionPerformed

private void txtDiscountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDiscountActionPerformed
    updateData();
}//GEN-LAST:event_txtDiscountActionPerformed

private void txtAddChargesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAddChargesActionPerformed
    updateData();
}//GEN-LAST:event_txtAddChargesActionPerformed

private void txtDiscountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiscountFocusLost
    updateData();
    updateTextField();
}//GEN-LAST:event_txtDiscountFocusLost

private void txtAddChargesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAddChargesFocusLost
    updateData();
    updateTextField();
}//GEN-LAST:event_txtAddChargesFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdRevert;
    private javax.swing.JButton cmdSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable tbl_invs;
    private javax.swing.JTable tbl_items;
    private javax.swing.JTextField txtAddCharges;
    private javax.swing.JTextField txtComments;
    private javax.swing.JTextField txtDate;
    private javax.swing.JComboBox txtDescr;
    private javax.swing.JTextField txtDiscount;
    private javax.swing.JTextField txtGrossAmt;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtPaidAmt;
    // End of variables declaration//GEN-END:variables
    localTableModel data = new localTableModel();
    localTableModel data_items = new localTableModel();
    DefaultTableModel nullModel = new DefaultTableModel();
    DefaultTableModel nullModel2 = new DefaultTableModel();
    MainFrame parentJF = null;
    Connection con = null;
    PreparedStatement ps_data = null;
    double varPaidAmt = 0;
    double varDiscAmt = 0;
    double varAddCharge = 0;
    double varGrossAmt = 0;
}
