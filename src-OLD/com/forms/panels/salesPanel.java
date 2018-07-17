/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * salesPanel.java
 *
 * Created on 04/04/2010, 03:14:28 م
 * 
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.forms.lovDialog;
import com.generic.model.DBClass;
import com.generic.model.Row;
import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import com.generic.model.qryColumn;
import com.generic.model.rowTriggerListner;
import com.generic.utils.NumberEditor;
import com.generic.utils.XTableColumnModel;
import com.generic.utils.utils;


import com.lov.selectListView;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author yusuf
 */
public class salesPanel extends javax.swing.JPanel {

    /** Creates new form salesPanel */
    public salesPanel(int ic) {
        initComponents();
        jTable1.setColumnModel(new XTableColumnModel());
        itemTable.setColumnModel(new XTableColumnModel());
        groupTable.setColumnModel(new XTableColumnModel());

        dataInvoiceCode = ic;
        if (dataInvoiceCode == 20) {
            jPanel3.setBackground(Color.GREEN);
            lblAmt.setVisible(false);
            lblChangeAmt.setVisible(false);
            sumTxtChangeAmt.setVisible(false);
            sumTxtPaidAmt.setVisible(false);
            jTable1.setBackground(Color.GREEN);

        }

    }

    public void setupItemCols() {
        selItems.clearALl();
        selItems.getQrycols().clear();
        selItems.getVisbleQrycols().clear();
        selItems.setEditAllowed(false);
        itemTable.setRowHeight(Integer.valueOf(parentJF.getMapVars().get("sale_item_row_height")));
        itemTable.setRowSelectionAllowed(false);
        itemTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);

        qryColumn qc = null;
        for (int colx = 0; colx < no_of_item_cols; colx++) {
            qc = new qryColumn(colx, String.valueOf(colx));
            selItems.getQrycols().add(qc);
            selItems.getVisbleQrycols().add(qc);
            //selItems.setHeaderText(colx, String.valueOf(colx));
        }
        itemTable.setModel(selItems);
        for (int colx = 0; colx < itemTable.getModel().getColumnCount(); colx++) {
            itemTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionLst, true));
        }

    }

    public void setupGroupCols() {
        selGrpItems.clearALl();
        selGrpItems.getQrycols().clear();
        selGrpItems.getVisbleQrycols().clear();
        selGrpItems.setEditAllowed(false);
        groupTable.setRowHeight(Integer.valueOf(parentJF.getMapVars().get("sale_item_row_height")));
        groupTable.setRowSelectionAllowed(false);
        groupTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        groupTable.setSelectionBackground(Color.BLUE);


        qryColumn qc = null;
        for (int colx = 0; colx < no_of_item_cols; colx++) {
            qc = new qryColumn(colx, String.valueOf(colx));
            selGrpItems.getQrycols().add(qc);
            selGrpItems.getVisbleQrycols().add(qc);
            //selItems.setHeaderText(colx, String.valueOf(colx));
        }
        groupTable.setModel(selGrpItems);
        for (int colx = 0; colx < groupTable.getModel().getColumnCount(); colx++) {
            groupTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionLst, true));
        }

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
        jPanel10 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cmdItemAdd5 = new javax.swing.JButton();
        cmdItemAdd6 = new javax.swing.JButton();
        cmdItemAdd4 = new javax.swing.JButton();
        cmdItemAdd3 = new javax.swing.JButton();
        cmdItemAdd1 = new javax.swing.JButton();
        cmdPayment = new javax.swing.JButton();
        cmdBasic = new javax.swing.JButton();
        cmdItemAdd2 = new javax.swing.JButton();
        panelHeader = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtKeyfld = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtInvoiceNo = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtInvoiceDate = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel7 = new javax.swing.JPanel();
        txtItemCode = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtItemQty = new javax.swing.JTextField();
        cmdItemAdd = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        splitGroupItem = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        groupTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        listDriver = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        adrPhone = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        adrCustomerName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        adrArea = new javax.swing.JComboBox();
        jLabel14 = new javax.swing.JLabel();
        adrDeliveryDate = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        adrOtherTel = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        adrBlock = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        adrStreet = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        adrJedda = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        adrBldg = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        adrFloorNo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        adrWorkAddress = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        payTable = new javax.swing.JTable();
        cmdPayHereAll = new javax.swing.JButton();
        cmdPayRestHere = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtDiscDescr = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblAmt = new javax.swing.JLabel();
        sumTxtPaidAmt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        sumTxtGrossAmount = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        sumTxtNetAmount = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        sumTxtAddAmt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        sumTxtDiscAmt = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        sumTxtCashAmt = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        sumTxtTotQty = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        sumTxtDiscP = new javax.swing.JTextField();
        lblChangeAmt = new javax.swing.JLabel();
        sumTxtChangeAmt = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jSplitPane1.setDividerLocation(251);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));

        jPanel10.setAlignmentX(0.0F);
        jPanel10.setAlignmentY(0.0F);

        jPanel5.setFont(new java.awt.Font("Tahoma", 0, 10));
        jPanel5.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel5.setLayout(new java.awt.GridLayout(2, 0, 5, 5));

        cmdItemAdd5.setFont(new java.awt.Font("Arial", 0, 11));
        cmdItemAdd5.setText("Del Record");
        cmdItemAdd5.setToolTipText("Add Update Items");
        cmdItemAdd5.setFocusable(false);
        cmdItemAdd5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd5ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd5);

        cmdItemAdd6.setFont(new java.awt.Font("Arial", 0, 11));
        cmdItemAdd6.setText("Clear All");
        cmdItemAdd6.setToolTipText("Add Update Items");
        cmdItemAdd6.setFocusable(false);
        cmdItemAdd6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd6ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd6);

        cmdItemAdd4.setFont(new java.awt.Font("Arial", 0, 11));
        cmdItemAdd4.setText("Delivery");
        cmdItemAdd4.setToolTipText("Add Update Items");
        cmdItemAdd4.setFocusable(false);
        cmdItemAdd4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd4ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd4);

        cmdItemAdd3.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        cmdItemAdd3.setText("Save/ Print");
        cmdItemAdd3.setToolTipText("Add Update Items");
        cmdItemAdd3.setFocusable(false);
        cmdItemAdd3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd3ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd3);

        cmdItemAdd1.setFont(new java.awt.Font("Arial", 0, 11));
        cmdItemAdd1.setText("Items");
        cmdItemAdd1.setToolTipText("Add Update Items");
        cmdItemAdd1.setFocusable(false);
        cmdItemAdd1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd1ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd1);

        cmdPayment.setFont(new java.awt.Font("Arial", 0, 11));
        cmdPayment.setText("Payments");
        cmdPayment.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdPayment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPaymentActionPerformed(evt);
            }
        });
        jPanel5.add(cmdPayment);

        cmdBasic.setFont(new java.awt.Font("Arial", 0, 11));
        cmdBasic.setText("Main");
        cmdBasic.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdBasic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdBasicActionPerformed(evt);
            }
        });
        jPanel5.add(cmdBasic);

        cmdItemAdd2.setFont(new java.awt.Font("Arial", 0, 11));
        cmdItemAdd2.setText("Save");
        cmdItemAdd2.setToolTipText("Add Update Items");
        cmdItemAdd2.setFocusable(false);
        cmdItemAdd2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdItemAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd2ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdItemAdd2);

        panelHeader.setPreferredSize(new java.awt.Dimension(200, 70));

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(63, 55));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(244, 250));

        jPanel6.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel6.setLayout(new java.awt.GridLayout(6, 0, 5, 3));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Key ID");
        jPanel6.add(jLabel1);

        txtKeyfld.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(txtKeyfld);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Invoice No");
        jPanel6.add(jLabel3);

        txtInvoiceNo.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel6.add(txtInvoiceNo);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Invoice Date");
        jPanel6.add(jLabel4);

        txtInvoiceDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(txtInvoiceDate);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Location");
        jPanel6.add(jLabel5);

        txtLocation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel6.add(txtLocation);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Pay Types");
        jPanel6.add(jLabel6);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new dataCell[] {new dataCell("", "1")}));
        jComboBox1.setMinimumSize(new java.awt.Dimension(0, 0));
        jComboBox1.setPreferredSize(new java.awt.Dimension(29, 40));
        jPanel6.add(jComboBox1);

        jPanel7.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanel7.setLayout(new java.awt.GridLayout(2, 0, 5, 5));

        txtItemCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtItemCodeActionPerformed(evt);
            }
        });
        txtItemCode.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemCodeFocusGained(evt);
            }
        });
        txtItemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtItemCodeKeyPressed(evt);
            }
        });
        jPanel7.add(txtItemCode);
        jPanel7.add(jLabel29);

        txtItemQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItemQty.setText("1");
        jPanel7.add(txtItemQty);

        cmdItemAdd.setText("Add/Update Items");
        cmdItemAdd.setToolTipText("Add Update Items");
        cmdItemAdd.setFocusable(false);
        cmdItemAdd.setMargin(new java.awt.Insets(2, 2, 2, 2));
        cmdItemAdd.setMaximumSize(new java.awt.Dimension(99, 99));
        cmdItemAdd.setMinimumSize(new java.awt.Dimension(0, 0));
        cmdItemAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAddActionPerformed(evt);
            }
        });
        jPanel7.add(cmdItemAdd);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Basic Info", jPanel1);

        splitGroupItem.setDividerLocation(151);
        splitGroupItem.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        groupTable.setBackground(java.awt.Color.pink);
        groupTable.setModel(new javax.swing.table.DefaultTableModel(
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
        groupTable.setCellSelectionEnabled(true);
        groupTable.setName("GroupTable"); // NOI18N
        jScrollPane3.setViewportView(groupTable);

        splitGroupItem.setTopComponent(jScrollPane3);

        itemTable.setBackground(new java.awt.Color(255, 255, 51));
        itemTable.setModel(new javax.swing.table.DefaultTableModel(
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
        itemTable.setCellSelectionEnabled(true);
        itemTable.setName("ItemTable"); // NOI18N
        jScrollPane2.setViewportView(itemTable);

        splitGroupItem.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Items ", jPanel2);

        jPanel8.setMinimumSize(new java.awt.Dimension(100, 370));
        jPanel8.setLayout(new java.awt.GridLayout(12, 2, 5, 5));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Driver/Cashier");
        jPanel8.add(jLabel8);

        listDriver.setModel(new javax.swing.DefaultComboBoxModel(new dataCell[] { new dataCell("","") }));
        listDriver.setMaximumSize(null);
        listDriver.setMinimumSize(null);
        listDriver.setPreferredSize(null);
        listDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                listDriverActionPerformed(evt);
            }
        });
        jPanel8.add(listDriver);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Phone/ID");
        jPanel8.add(jLabel2);

        adrPhone.setMaximumSize(null);
        adrPhone.setMinimumSize(null);
        adrPhone.setPreferredSize(null);
        adrPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                adrPhoneFocusLost(evt);
            }
        });
        jPanel8.add(adrPhone);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Customer Name");
        jPanel8.add(jLabel12);

        adrCustomerName.setMaximumSize(null);
        adrCustomerName.setMinimumSize(null);
        adrCustomerName.setPreferredSize(null);
        jPanel8.add(adrCustomerName);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("AREA");
        jPanel8.add(jLabel13);

        adrArea.setEditable(true);
        adrArea.setMaximumSize(null);
        adrArea.setMinimumSize(null);
        adrArea.setPreferredSize(null);
        adrArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrAreaActionPerformed(evt);
            }
        });
        jPanel8.add(adrArea);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Delivery Date Time");
        jPanel8.add(jLabel14);

        adrDeliveryDate.setMaximumSize(null);
        adrDeliveryDate.setMinimumSize(null);
        adrDeliveryDate.setPreferredSize(null);
        jPanel8.add(adrDeliveryDate);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Other Tel");
        jPanel8.add(jLabel15);

        adrOtherTel.setMaximumSize(null);
        adrOtherTel.setMinimumSize(null);
        adrOtherTel.setPreferredSize(null);
        adrOtherTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrOtherTelActionPerformed(evt);
            }
        });
        jPanel8.add(adrOtherTel);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setText("Block");
        jPanel8.add(jLabel19);

        adrBlock.setMaximumSize(null);
        adrBlock.setMinimumSize(null);
        adrBlock.setPreferredSize(null);
        jPanel8.add(adrBlock);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Street");
        jPanel8.add(jLabel20);

        adrStreet.setMaximumSize(null);
        adrStreet.setMinimumSize(null);
        adrStreet.setPreferredSize(null);
        jPanel8.add(adrStreet);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel21.setText("Jedda");
        jPanel8.add(jLabel21);

        adrJedda.setMaximumSize(null);
        adrJedda.setMinimumSize(null);
        adrJedda.setPreferredSize(null);
        jPanel8.add(adrJedda);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel22.setText("Building");
        jPanel8.add(jLabel22);

        adrBldg.setMaximumSize(null);
        adrBldg.setMinimumSize(null);
        adrBldg.setPreferredSize(null);
        jPanel8.add(adrBldg);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Floor No");
        jPanel8.add(jLabel23);

        adrFloorNo.setMaximumSize(null);
        adrFloorNo.setMinimumSize(null);
        adrFloorNo.setPreferredSize(null);
        adrFloorNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrFloorNoActionPerformed(evt);
            }
        });
        jPanel8.add(adrFloorNo);

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Flat No/Unit No");
        jPanel8.add(jLabel16);

        adrWorkAddress.setMaximumSize(null);
        adrWorkAddress.setMinimumSize(null);
        adrWorkAddress.setPreferredSize(null);
        jPanel8.add(adrWorkAddress);

        jButton1.setText("Main Screen>>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 281, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Delivery Info", jPanel4);

        payTable.setFont(new java.awt.Font("Times New Roman", 0, 15));
        payTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5"
            }
        ));
        payTable.setRowHeight(30);
        payTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payTableMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(payTable);

        cmdPayHereAll.setText("Pay here All Amount");
        cmdPayHereAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayHereAllActionPerformed(evt);
            }
        });

        cmdPayRestHere.setText("Pay rest ");
        cmdPayRestHere.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdPayRestHereActionPerformed(evt);
            }
        });

        jLabel25.setText("Disc Type");
        jPanel13.add(jLabel25);

        txtDiscDescr.setColumns(18);
        jPanel13.add(txtDiscDescr);

        jButton5.setText("...");
        jButton5.setMaximumSize(new java.awt.Dimension(20, 23));
        jButton5.setMinimumSize(new java.awt.Dimension(25, 23));
        jButton5.setPreferredSize(new java.awt.Dimension(25, 23));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel13.add(jButton5);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(cmdPayHereAll, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdPayRestHere, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdPayHereAll, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdPayRestHere, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Payments", jPanel9);

        jPanel11.setLayout(new java.awt.GridLayout(5, 2));

        jLabel17.setText("Paid Account ");
        jPanel11.add(jLabel17);

        jTextField1.setEditable(false);
        jTextField1.setText("jTextField1");
        jPanel11.add(jTextField1);

        lblAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblAmt.setText("Paid Amt");
        jPanel11.add(lblAmt);

        sumTxtPaidAmt.setEditable(false);
        sumTxtPaidAmt.setFont(new java.awt.Font("Tahoma", 1, 11));
        sumTxtPaidAmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sumTxtPaidAmt.setText("0");
        sumTxtPaidAmt.setName("sumTxtPaidAmt"); // NOI18N
        sumTxtPaidAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumTxtPaidAmtActionPerformed(evt);
            }
        });
        sumTxtPaidAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sumTxtDiscAmtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sumTxtPaidAmtFocusLost(evt);
            }
        });
        jPanel11.add(sumTxtPaidAmt);

        jButton2.setText("Search Account");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel11.add(jButton2);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 284, Short.MAX_VALUE)
            .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel12Layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(114, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("", jPanel12);

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel10);

        panelCenter.setPreferredSize(new java.awt.Dimension(100, 392));

        jScrollPane1.setBackground(java.awt.Color.green);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(0, 0));

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(204, 255, 204));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setMaximumSize(new java.awt.Dimension(32767, 32767));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(new java.awt.GridLayout(0, 6, 20, 3));
        jPanel3.add(jLabel26);
        jPanel3.add(jLabel28);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Gross Amt");
        jPanel3.add(jLabel7);

        sumTxtGrossAmount.setBackground(java.awt.Color.yellow);
        sumTxtGrossAmount.setFont(new java.awt.Font("Tahoma", 0, 14));
        sumTxtGrossAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtGrossAmount.setText("0.0");
        sumTxtGrossAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtGrossAmount.setOpaque(true);
        jPanel3.add(sumTxtGrossAmount);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Net Amt");
        jPanel3.add(jLabel10);

        sumTxtNetAmount.setBackground(java.awt.Color.yellow);
        sumTxtNetAmount.setFont(new java.awt.Font("Tahoma", 1, 18));
        sumTxtNetAmount.setForeground(java.awt.Color.red);
        sumTxtNetAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtNetAmount.setText("0.0");
        sumTxtNetAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtNetAmount.setOpaque(true);
        jPanel3.add(sumTxtNetAmount);

        jLabel18.setText("Additional Charges");
        jPanel3.add(jLabel18);

        sumTxtAddAmt.setFont(new java.awt.Font("Tahoma", 1, 11));
        sumTxtAddAmt.setText("0");
        sumTxtAddAmt.setName("sumTxtAddAmt"); // NOI18N
        sumTxtAddAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumTxtAddAmtActionPerformed(evt);
            }
        });
        sumTxtAddAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sumTxtAddAmtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sumTxtAddAmtFocusLost(evt);
            }
        });
        jPanel3.add(sumTxtAddAmt);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Disc.Amt");
        jPanel3.add(jLabel9);

        sumTxtDiscAmt.setFont(new java.awt.Font("Tahoma", 1, 11));
        sumTxtDiscAmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sumTxtDiscAmt.setText("0");
        sumTxtDiscAmt.setName("sumTxtDiscAmt"); // NOI18N
        sumTxtDiscAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumTxtDiscAmtActionPerformed(evt);
            }
        });
        sumTxtDiscAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sumTxtDiscAmtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sumTxtDiscAmtFocusLost(evt);
            }
        });
        jPanel3.add(sumTxtDiscAmt);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel27.setText("Cash Given");
        jPanel3.add(jLabel27);

        sumTxtCashAmt.setFont(new java.awt.Font("Tahoma", 1, 11));
        sumTxtCashAmt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sumTxtCashAmt.setText("0");
        sumTxtCashAmt.setName("sumTxtCashAmt"); // NOI18N
        sumTxtCashAmt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumTxtCashAmtActionPerformed(evt);
            }
        });
        sumTxtCashAmt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sumTxtCashAmtsumTxtDiscAmtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sumTxtCashAmtsumTxtDiscAmtFocusLost(evt);
            }
        });
        jPanel3.add(sumTxtCashAmt);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel24.setText("Total Qty");
        jPanel3.add(jLabel24);

        sumTxtTotQty.setBackground(java.awt.Color.yellow);
        sumTxtTotQty.setFont(new java.awt.Font("Tahoma", 0, 14));
        sumTxtTotQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtTotQty.setText("0");
        sumTxtTotQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtTotQty.setOpaque(true);
        jPanel3.add(sumTxtTotQty);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Disc %");
        jPanel3.add(jLabel11);

        sumTxtDiscP.setFont(new java.awt.Font("Tahoma", 1, 11));
        sumTxtDiscP.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        sumTxtDiscP.setText("0");
        sumTxtDiscP.setName("sumTxtDiscP"); // NOI18N
        sumTxtDiscP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumTxtDiscPActionPerformed(evt);
            }
        });
        sumTxtDiscP.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sumTxtDiscAmtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                sumTxtDiscAmtFocusLost(evt);
            }
        });
        jPanel3.add(sumTxtDiscP);

        lblChangeAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblChangeAmt.setText("Change Amt");
        jPanel3.add(lblChangeAmt);

        sumTxtChangeAmt.setBackground(new java.awt.Color(255, 255, 102));
        sumTxtChangeAmt.setFont(new java.awt.Font("Tahoma", 1, 18));
        sumTxtChangeAmt.setForeground(new java.awt.Color(0, 51, 204));
        sumTxtChangeAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtChangeAmt.setText("0.0");
        sumTxtChangeAmt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtChangeAmt.setOpaque(true);
        jPanel3.add(sumTxtChangeAmt);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        jButton3.setText("+");
        jButton3.setPreferredSize(new java.awt.Dimension(50, 25));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton3);

        jButton4.setText("-");
        jButton4.setPreferredSize(new java.awt.Dimension(50, 25));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton4);

        javax.swing.GroupLayout panelCenterLayout = new javax.swing.GroupLayout(panelCenter);
        panelCenter.setLayout(panelCenterLayout);
        panelCenterLayout.setHorizontalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 444, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCenterLayout.setVerticalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(panelCenter);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 387, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdItemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAddActionPerformed
        try {
            addUpdate();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
}//GEN-LAST:event_cmdItemAddActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void cmdItemAdd1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd1ActionPerformed
        // TODO add your handling code here:
        currentItem = "";
        currentGroup = "";
        //showItems(jTable2);
        start_showItems();
        jTabbedPane1.setSelectedIndex(1);
        jPanel2.updateUI();

    }//GEN-LAST:event_cmdItemAdd1ActionPerformed

    private void sumTxtDiscAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtDiscAmtActionPerformed
        sumDiscountAmt = Double.valueOf(sumTxtDiscAmt.getText());
        update_sums();
    }//GEN-LAST:event_sumTxtDiscAmtActionPerformed

    private void sumTxtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtDiscAmtFocusLost
        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtDiscAmtFocusLost

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
    }//GEN-LAST:event_txtItemCodeActionPerformed

    private void sumTxtDiscAmtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtDiscAmtFocusGained
        /*
        if (((JTextField) evt.getComponent()).getName().equals("sumTxtPaidAmt") &&
        ((JTextField) evt.getComponent()).getText() != null &&
        ((JTextField) evt.getComponent()).getText().length() > 0 &&
        Double.valueOf(((JTextField) evt.getComponent()).getText()) == 0) {
        ((JTextField) evt.getComponent()).setText(sumTxtNetAmount.getText());
        ((JTextField) evt.getComponent()).getInputVerifier().verify((JComponent) evt.getComponent());

        }
         */
        ((JTextField) evt.getComponent()).selectAll();
    }//GEN-LAST:event_sumTxtDiscAmtFocusGained

    private void cmdItemAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd2ActionPerformed
        try {
            save_data();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            try {
                dbConnection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_cmdItemAdd2ActionPerformed

    private void cmdItemAdd3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd3ActionPerformed
        try {
            double kf = save_data();
            if (kf > 0) {
                print_data(kf);
            }
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cmdItemAdd3ActionPerformed
    public void print_data(double kf) {

        String reportSource = "reports/possale.jrxml";
        String reportDest = "reports/output/possale.html";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("KEYFLD", BigDecimal.valueOf(kf));

        double tmp = 0;
        try {
            PreparedStatement pss = dbConnection.prepareStatement("select pospur1.*,company.name company_name,company.specification from pospur1,company where company.crnt='X' and pospur1.keyfld=" + kf, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = pss.executeQuery();
            if (rss.first()) {
                params.put("INV_AMT", BigDecimal.valueOf(rss.getDouble("INV_AMT")));
                params.put("INV_NO", BigDecimal.valueOf(rss.getDouble("INVOICE_NO")));
                params.put("INV_DATE_TIME", rss.getTimestamp("INVOICE_DATE"));
                params.put("INV_LOCATION", dataLocationName);
                params.put("INV_USER", parentJF.getLp().getLogon_user());
                params.put("INV_NETAMT", (BigDecimal.valueOf((rss.getDouble("INV_AMT") + rss.getDouble("ADD_CHARGE")) - rss.getDouble("DISC_AMT"))));
                params.put("INV_PAIDAMT", BigDecimal.valueOf(rss.getDouble("PAIDAMT2")));
                params.put("INV_DISCAMT", BigDecimal.valueOf(rss.getDouble("DISC_AMT")));
                tmp = rss.getDouble("PAIDAMT2") - (rss.getDouble("INV_AMT") - rss.getDouble("DISC_AMT"));
                params.put("INV_CHANGEAMT", BigDecimal.valueOf(tmp));
                params.put("ADD_CHARGE", BigDecimal.valueOf(rss.getDouble("ADD_CHARGE")));
                params.put("COMPANY_NAME", rss.getString("company_name"));
                params.put("COMPANY_SPEC", rss.getString("specification"));
                String drivername = "";
                if (rss.getString("SLSMN") != null && rss.getString("SLSMN").length() > 0) {
                    drivername = utils.getSqlValue("select name from salesp where no=" + rss.getString("SLSMN"), dbConnection);
                }
                params.put("DLV_DRIVERNAME", drivername);
                params.put("DLV_PHONE", rss.getString("inv_ref"));
                params.put("DLV_CUSTOMERNAME", rss.getString("inv_refnm"));
                params.put("DLV_OTHERTEL", rss.getString("addr_tel"));
                params.put("DLV_AREA", rss.getString("ADDR_AREA"));
                params.put("DLV_BLOCK", rss.getString("ADDR_BLOCK"));
                params.put("DLV_STREET", rss.getString("ADDR_STREET"));
                params.put("DLV_JEDDA", rss.getString("ADDR_JEDDA"));
                params.put("DLV_BUILDING", rss.getString("ADDR_BLDG"));
                params.put("DLV_FLOORNO", rss.getString("REFERENCE_INFORMATION"));
                params.put("DLV_FLATNO", rss.getString("WORK_ADDRESS"));
            }
            pss.close();

            JasperReport jasperReport =
                    JasperCompileManager.compileReport(reportSource);

            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(
                    jasperReport, params, dbConnection);//new JREmptyDataSource());

            //JasperExportManager.exportReportToHtmlFile(
            //       jasperPrint, reportDest);
            //JasperViewer.viewReport(jasperPrint, false);
            JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }
    }
    private void cmdItemAdd4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd4ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
        jTabbedPane1.updateUI();
    }//GEN-LAST:event_cmdItemAdd4ActionPerformed

    private void adrAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrAreaActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrAreaActionPerformed

    private void cmdItemAdd5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd5ActionPerformed
        itemdetails.deleteRow(jTable1.getSelectedRow());
        jTable1.updateUI();
        update_sums();
    }//GEN-LAST:event_cmdItemAdd5ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cmdItemAdd6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd6ActionPerformed
        QRYSTR = "";
        load_data();
    }//GEN-LAST:event_cmdItemAdd6ActionPerformed

    private void adrOtherTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrOtherTelActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrOtherTelActionPerformed

    private void adrFloorNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrFloorNoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrFloorNoActionPerformed

    private void adrPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adrPhoneFocusLost
        if (adrPhone.getText() == null || adrPhone.getText().length() == 0) {
            return;
        }
        PreparedStatement ps2 = null;
        try {
            ps2 = dbConnection.prepareStatement("select *from POSCUSTOMER where CODE=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps2.setString(1, adrPhone.getText());
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.first()) {
                adrCustomerName.setText(rs2.getString("NAME"));
                adrArea.setSelectedItem(rs2.getString("AREA"));
                adrBldg.setText(rs2.getString("ADDR_BLDG"));
                adrDeliveryDate.setText("");
                adrFloorNo.setText(rs2.getString("REFERENCE"));
                adrJedda.setText(rs2.getString("ADDR_JEDDA"));
                adrOtherTel.setText(rs2.getString("TEL"));
                adrStreet.setText(rs2.getString("ADDR_STREET"));
                adrWorkAddress.setText(rs2.getString("WORK_ADDRESS"));
                adrBlock.setText(rs2.getString("addr_block"));

            }
            ps2.close();
        } catch (SQLException ex) {
            try {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                if (ps2 != null && ps2.isClosed() == false) {
                    ps2.close();
                }
            } catch (SQLException ex1) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }//GEN-LAST:event_adrPhoneFocusLost

    private void txtItemCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemCodeFocusGained
        ((JTextComponent) evt.getComponent()).selectAll();
    }//GEN-LAST:event_txtItemCodeFocusGained

    private void txtItemCodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemCodeKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_F9) {
            show_distinct_item_list();
        }
    }//GEN-LAST:event_txtItemCodeKeyPressed

    private void sumTxtDiscPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtDiscPActionPerformed
}//GEN-LAST:event_sumTxtDiscPActionPerformed

    private void payTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payTableMouseClicked
        payTable.editCellAt(payTable.getSelectedRow(), 2);
        if (payTable.getEditorComponent() != null && payTable.getEditorComponent() instanceof JFormattedTextField) {
            ((JFormattedTextField) payTable.getEditorComponent()).requestFocus();
            if (Double.valueOf(((JFormattedTextField) payTable.getEditorComponent()).getText()) != sumNetAmt - sumPaidAmt && sumPaidAmt == 0) {
                ((JFormattedTextField) payTable.getEditorComponent()).setText(String.valueOf(sumNetAmt - sumPaidAmt));
            }
            ((JFormattedTextField) payTable.getEditorComponent()).selectAll();
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    ((JFormattedTextField) payTable.getEditorComponent()).selectAll();
                }
            });
        }
    }//GEN-LAST:event_payTableMouseClicked

    private void cmdPayHereAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPayHereAllActionPerformed
        payTable.removeEditor();
        for (int i = 0; i < payRows.getRows().size(); i++) {
            Row row = payRows.getRows().get(i);
            row.lst.get(2).setValue(Double.valueOf("0"), Double.valueOf("0"));
        }
        if (payTable.getSelectedRow() >= 0) {
            payRows.setFieldValue(payTable.getSelectedRow(), "AMOUNT", Double.valueOf(sumNetAmt));
        }


        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
        update_sums();
        payTable.updateUI();
}//GEN-LAST:event_cmdPayHereAllActionPerformed

    private void cmdPayRestHereActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPayRestHereActionPerformed
        payTable.removeEditor();
        if (payTable.getSelectedRow() >= 0 && sumNetAmt - sumPaidAmt > 0) {
            payRows.setFieldValue(payTable.getSelectedRow(), "AMOUNT", Double.valueOf(sumNetAmt - sumPaidAmt));
        }
        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
        update_sums();
        payTable.updateUI();
}//GEN-LAST:event_cmdPayRestHereActionPerformed

    private void cmdPaymentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdPaymentActionPerformed
        jTabbedPane1.setSelectedIndex(3);
        jTabbedPane1.updateUI();

    }//GEN-LAST:event_cmdPaymentActionPerformed

    private void cmdBasicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdBasicActionPerformed
        jTabbedPane1.setSelectedIndex(0);
        jTabbedPane1.updateUI();

    }//GEN-LAST:event_cmdBasicActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            lovDialog ld = new lovDialog(parentJF, true);
            ld.setNoOfClicksOnSelection(selectListView.DOUBLE_CLICK_SELECTION);
            ld.getSlov().getLctb().clearALl();
            ld.setDbc(dbc);
            ld.setSqlstr("select DESCR,AMOUNT DISCOUNT,AMOUNT_P DISC_PERCENT FROM POSCHARGES WHERE FLAG=2 AND KIND='DISCOUNT'");
            ld.setwidth(500);
            ld.setupLov();
            if (ld.getSelectedNo() >= 0) {
                txtDiscDescr.setText(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "DESCR").toString());
            }

        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void sumTxtCashAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtCashAmtActionPerformed
        sumCashAmt = Double.valueOf(sumTxtCashAmt.getText());
        update_sums();

    }//GEN-LAST:event_sumTxtCashAmtActionPerformed

    private void sumTxtCashAmtsumTxtDiscAmtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtCashAmtsumTxtDiscAmtFocusGained
        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtCashAmtsumTxtDiscAmtFocusGained

    private void sumTxtCashAmtsumTxtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtCashAmtsumTxtDiscAmtFocusLost

        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtCashAmtsumTxtDiscAmtFocusLost

    private void listDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_listDriverActionPerformed
        // TODO add your handling code here:
        datasalesp = "";
        sumTxtCashAmt.setEditable(true);
        sumAddAmt = 0;
        sumTxtAddAmt.setText(String.valueOf(sumAddAmt));
        if ((listDriver.getModel().getSelectedItem()) != null) {
            datasalesp =
                    ((dataCell) listDriver.getModel().getSelectedItem()).getValue().toString();
        }
        if (datasalesp != null && datasalesp.length() > 0) {
            sumCashAmt = 0;
            sumTxtCashAmt.setText(decimalformat.format(sumCashAmt));
            if (parentJF.getMapVars().get("default_delivery_charges") != null) {
                sumAddAmt = Double.valueOf(parentJF.getMapVars().get("default_delivery_charges"));
                sumTxtAddAmt.setText(String.valueOf(sumAddAmt));
            }
            sumTxtCashAmt.setEditable(false);
            update_sums();
        }
    }//GEN-LAST:event_listDriverActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (jTable1.getSelectedRow() < 0) {
            return;
        }
        txtItemCode.setText((String) itemdetails.getFieldValue(jTable1.getSelectedRow(), "REFER"));
        txtItemQty.setText("1");
        cmdItemAdd.doClick();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (jTable1.getSelectedRow() < 0) {
            return;
        }
        txtItemCode.setText((String) itemdetails.getFieldValue(jTable1.getSelectedRow(), "REFER"));
        txtItemQty.setText("-1");
        cmdItemAdd.doClick();
        txtItemQty.setText("1");

    }//GEN-LAST:event_jButton4ActionPerformed

    private void sumTxtAddAmtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtAddAmtFocusGained
        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtAddAmtFocusGained

    private void sumTxtAddAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtAddAmtFocusLost
        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtAddAmtFocusLost

    private void sumTxtAddAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtAddAmtActionPerformed
        sumAddAmt = Double.valueOf(sumTxtAddAmt.getText());
        update_sums();
    }//GEN-LAST:event_sumTxtAddAmtActionPerformed

    private void sumTxtPaidAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtPaidAmtFocusLost
        onfocusLostSums((JTextField) evt.getComponent());
}//GEN-LAST:event_sumTxtPaidAmtFocusLost

    private void sumTxtPaidAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtPaidAmtActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_sumTxtPaidAmtActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            lovDialog ld = new lovDialog(parentJF, true);
            ld.setNoOfClicksOnSelection(selectListView.DOUBLE_CLICK_SELECTION);
            ld.getSlov().getLctb().clearALl();
            ld.setDbc(dbc);
            ld.setSqlstr("select DESCR,AMOUNT DISCOUNT,AMOUNT_P DISC_PERCENT FROM POSCHARGES WHERE FLAG=2 and kind='DISCOUNT'");
            ld.setwidth(500);
            ld.setupLov();
            ld.setVisible(true);
            if (ld.getSelectedNo() >= 0) {
                txtDiscDescr.setText(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "DESCR").toString());
            }
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton5ActionPerformed
    public void onfocusLostSums(JTextField txt) {

        if (txt.getText().length() > 0) {
            try {
                if (txt.getName().equals("sumTxtDiscAmt")) {
                    sumDiscountAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    txt.setText(decimalformat.format(sumDiscountAmt));
                }

                if (txt.getName().equals("sumTxtPaidAmt")) {
                    sumPaidAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    txt.setText(decimalformat.format(sumPaidAmt));
                }
                if (txt.getName().equals("sumTxtCashAmt")) {
                    sumCashAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    txt.setText(decimalformat.format(sumCashAmt));
                }
                if (txt.getName().equals("sumTxtAddAmt")) {
                    sumAddAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    txt.setText(decimalformat.format(sumAddAmt));
                }

                //sumDiscountAmt = (decimalformat.parse(txt.getText())).doubleValue();

            } catch (ParseException ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            }
        }
    }

    public JTable getJTable1() {
        return jTable1;
    }

    public MainFrame getParentJF() {
        return parentJF;
    }
    private Timer connection_timer = new Timer();

    public void setParentJF(MainFrame parentJF) {
        jTabbedPane1.setSelectedIndex(1);
        try {
            this.parentJF = parentJF;
            if (selItems == null) {
                no_of_item_cols = Integer.valueOf(parentJF.getMapVars().get("no_of_item_column"));
                selItems = new localTableModel(no_of_item_cols);
                setupItemCols();
                itemTable.addMouseListener(new JTableButtonMouseListener(itemTable));
            }
            if (selGrpItems == null) {
                no_of_item_cols = Integer.valueOf(parentJF.getMapVars().get("no_of_item_column"));
                selGrpItems = new localTableModel(no_of_item_cols);
                setupGroupCols();
                groupTable.addMouseListener(new JTableButtonMouseListener(groupTable));
            }

            if (dbc == null) {
                jSplitPane1.setDividerLocation(Integer.valueOf(parentJF.getMapVars().get("sales_screen_divider")));
                splitGroupItem.setDividerLocation(Integer.valueOf(parentJF.getMapVars().get("sale_items_split_divider")));
                dburl =
                        parentJF.getMapVars().get("dburl");
                username =
                        parentJF.getMapVars().get("username");
                password =
                        parentJF.getMapVars().get("password");
                sf.applyPattern(parentJF.getMapVars().get("ENGLISH_DATE_FORMAT"));
                dbc = new DBClass(dburl, username, password);
                dbConnection =
                        dbc.getDbConnection();
                if (Integer.valueOf(parentJF.getMapVars().get("connection_expire")) > 0) {
                    connection_timer.scheduleAtFixedRate(new TimerTask() {

                        private boolean is_working = false;
                        PreparedStatement xxxpp = dbConnection.prepareStatement("BEGIN NULL;END;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                        public void run() {
                            try {
                                if (is_working == false) {
                                    is_working = true;
                                    xxxpp.executeUpdate();
                                    //xxrs.first();
                                    //xxxpp.close();
                                    is_working = false;
                                    System.out.println("SalesPanel -> sql exec..." + new Date(System.currentTimeMillis()));
                                }
                            } catch (SQLException ex) {
                                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }, 5000, Integer.valueOf(parentJF.getMapVars().get("connection_expire")));
                }
                itemdetails.createDBClassFromConnection(dbConnection);


                decimalformat = new DecimalFormat(parentJF.getMapVars().get("money_number"));
                dateformat =
                        new SimpleDateFormat(parentJF.getMapVars().get("short_date_format"));
                load_data();
                payRows.clearALl();
                payRows.getQrycols().add(new qryColumn(0, "NO"));
                payRows.getQrycols().add(new qryColumn(1, "DESCR"));
                payRows.getQrycols().add(new qryColumn(2, "AMOUNT"));
                payRows.getQrycols().add(new qryColumn(3, "ACCNO"));
                payRows.getQrycols().add(new qryColumn(4, "NAME"));
                payRows.getVisbleQrycols().addAll(payRows.getQrycols());

                payRows.getColByName("NO").setCanEdit(false);
                payRows.getColByName("DESCR").setCanEdit(false);
                payRows.getColByName("ACCNO").setCanEdit(false);
                payRows.getColByName("NAME").setCanEdit(false);

                payRows.getColByName("AMOUNT").setAlignmnet(JLabel.TRAILING);
                payRows.getColByName("AMOUNT").setNumberFormat(decimalformat.toPattern());
                payRows.getColByName("AMOUNT").setDatatype(19);
                payRows.getColByName("AMOUNT").setColor(Color.YELLOW);

                payRows.getColByName("ACCNO").setVisible(false);
                payRows.getColByName("NAME").setVisible(false);
                payRows.getVisbleQrycols().remove(payRows.getColByName("ACCNO"));
                payRows.getVisbleQrycols().remove(payRows.getColByName("NAME"));

                payTable.setModel(payRows);
                payTable.getColumnModel().getColumn(0).setCellRenderer(new ColorRenderer(true));
                payTable.getColumnModel().getColumn(1).setCellRenderer(new ColorRenderer(true));
                payTable.getColumnModel().getColumn(2).setCellRenderer(new ColorRenderer(true));

                payTable.getColumnModel().getColumn(2).setCellEditor(new NumberEditor(decimalformat));
                payTable.getColumnModel().getColumn(2).getCellEditor().addCellEditorListener(new CellEditorListener() {

                    public void editingStopped(ChangeEvent e) {
                        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
                        sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
                        update_sums();
                    }

                    public void editingCanceled(ChangeEvent e) {
                        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
                        sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
                        update_sums();
                    }
                });
                payTable.getModel().addTableModelListener(new TableModelListener() {

                    public void tableChanged(TableModelEvent e) {
                    }
                });
                ((DefaultComboBoxModel) jComboBox1.getModel()).removeAllElements();
                ((DefaultComboBoxModel) listDriver.getModel()).removeAllElements();

                PreparedStatement ps = dbConnection.prepareStatement("select INVOICETYPE.*,ACACCOUNT.NAME ACNAME "
                        + " from invoicetype,ACACCOUNT where ACACCOUNT.ACCNO(+)=INVOICETYPE.ACCNO AND location_code=? order by no", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ps.setString(1, dataLocationCode);
                ResultSet rsx = ps.executeQuery();
                rsx.beforeFirst();
                while (rsx.next()) {
                    ((DefaultComboBoxModel) jComboBox1.getModel()).addElement(new dataCell(rsx.getString("DESCR"), Double.valueOf(rsx.getDouble("NO"))));
                    Row rw = new Row(5);
                    rw.lst.get(0).setValue(rsx.getString("NO"), Double.valueOf(rsx.getDouble("NO")));
                    rw.lst.get(1).setValue(rsx.getString("DESCR"), rsx.getString("DESCR"));
                    rw.lst.get(2).setValue(Double.valueOf(0), Double.valueOf(0));
                    rw.lst.get(3).setValue(rsx.getString("ACCNO"), rsx.getString("ACCNO"));
                    rw.lst.get(4).setValue(rsx.getString("ACNAME"), rsx.getString("ACNAME"));
                    payRows.getRows().add(rw);

                }
                ps.close();
                ((DefaultComboBoxModel) listDriver.getModel()).addElement(new dataCell("", ""));
                ps = dbConnection.prepareStatement("select *from salesp where (type='S' or type='D') order by no", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rsx = ps.executeQuery();
                rsx.beforeFirst();
                while (rsx.next()) {
                    ((DefaultComboBoxModel) listDriver.getModel()).addElement(new dataCell(rsx.getString("name"), rsx.getString("NO")));
                }

                ps.close();

            }

        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }

    }
    private localTableModel payRows = new localTableModel(5);
    private DefaultTableModel tmp_model = new DefaultTableModel();

    public void setPaymentdetails() throws SQLException {
        clearPayments();
        PreparedStatement ps = dbConnection.prepareStatement("select *from POSPAYMENTS where vou_keyfld=?", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps.setString(1, QRYSTR);
        ResultSet rsx = ps.executeQuery();
        rsx.beforeFirst();
        while (rsx.next()) {
            int fnd = payRows.locate("NO", Double.valueOf(rsx.getString("TYPE_NO")).toString(), localTableModel.FIND_EXACT);
            if (fnd > -1) {
                payRows.setFieldValue(fnd, "AMOUNT", Double.valueOf(rsx.getDouble("AMOUNT")));
            }

        }
        payTable.updateUI();
        ps.close();

    }

    public String getItemRefer(String b) {
        String br = "";
        if (itemsRows == null) {
            br = utils.getSqlValue("select  reference from items where flag=1 and (referENCE='" + b + "' or barcode='" + b + "')", dbConnection);
        } else {
            for (int i = 0; i < itemsRows.size(); i++) {
                if (itemsRows.get(i).lst.get(0).getValue().toString().equals(b) || itemsRows.get(i).lst.get(5).getValue().toString().equals(b)) {
                    return itemsRows.get(i).lst.get(0).getValue().toString();
                }
            }
        }
        return br;
    }
    private double lastSelectedItemPrice = 0;

    public String getItemCategory(String itm) {
        String lastitem = "";
        int cnt = 0;
        lovDialog ld = null;
        for (int i = 0; i < itemsRows.size(); i++) {

            dataCell dc1 = itemsRows.get(i).lst.get(0);
            dataCell dc2 = itemsRows.get(i).lst.get(2);
            dataCell dc3 = itemsRows.get(i).lst.get(4);
            if (dc1.getValue().toString().equals(itm)) {
                cnt++;
                lastitem = dc1.getValue().toString();
                if (i + cnt >= itemsRows.size()) {
                    dc1 = null;
                } else {
                    dc1 = itemsRows.get(i + cnt).lst.get(0);
                    dc2 = itemsRows.get(i + cnt).lst.get(2);
                    dc3 = itemsRows.get(i + cnt).lst.get(4);
                }

            }

            while (dc1 != null && lastitem.equals(dc1.getValue().toString())) {
                if (ld == null) {
                    ld = new lovDialog(parentJF, true);
                    ld.setNoOfClicksOnSelection(selectListView.SINGLE_CLICK_SELECTION);
                    ld.getSlov().getLctb().clearALl();
                    qryColumn qc = new qryColumn(0, "CODE");
                    ld.getSlov().getLctb().getQrycols().add(qc);
                    ld.getSlov().getLctb().getVisbleQrycols().add(qc);
                    qc = new qryColumn(1, "PRICE");
                    ld.getSlov().getLctb().getQrycols().add(qc);
                    ld.getSlov().getLctb().getVisbleQrycols().add(qc);
                    cnt--;
                }
                dc1 = itemsRows.get(i + cnt).lst.get(0);
                dc2 = itemsRows.get(i + cnt).lst.get(2);
                dc3 = itemsRows.get(i + cnt).lst.get(4);
                Row r = new Row(2);
                r.lst.get(0).setValue(dc3.getDisplay(), dc3.getValue());
                r.lst.get(1).setValue(dc2.getDisplay(), dc2.getValue());
                ld.getSlov().getLctb().getRows().add(r);
                cnt++;
                dc1 = itemsRows.get(i + cnt).lst.get(0);
                dc2 = itemsRows.get(i + cnt).lst.get(2);
                dc3 = itemsRows.get(i + cnt).lst.get(4);

            }

            if (ld != null) {
                ld.getSlov().getLctb().setMasterRowsAsRows();
                ld.setVisible(true);
                if (ld.getSelectedNo() < 0) {
                    lastSelectedItemPrice = 0;
                    return "";
                } else {
                    lastSelectedItemPrice = Double.valueOf(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "PRICE").toString());
                    return ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "CODE").toString();
                }
            }
            if (cnt == 1 && itemsRows.get(i).lst.get(2).getValue() != null && itemsRows.get(i).lst.get(2).getValue().toString().length() != 0) {
                dc2 = itemsRows.get(i).lst.get(2);
                lastSelectedItemPrice = Double.valueOf(dc2.getValue().toString());
                return "DEFAULT";
            }

        }
        return "DEFAULT";
    }
    private int lastFoundItem = -1;

    public Object getValueFromItemsRow(String rfr, int col) {
        // improving on quick finding items according previous call
        if (lastFoundItem != -1 && itemsRows.get(lastFoundItem).lst.get(0).getValue().toString().equals(rfr)) {
            return itemsRows.get(lastFoundItem).lst.get(col).getValue();
        }

        for (int i = 0; i < itemsRows.size(); i++) {
            if (itemsRows.get(i).lst.get(0).getValue().toString().equals(rfr)) {
                lastFoundItem = i;
                return itemsRows.get(i).lst.get(col).getValue();
            }

        }
        lastFoundItem = -1;
        return null;
    }

    public boolean is_valid_entry() throws Exception {
        boolean vld = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (parentJF.getMapVars().get("POS_CLOSE_DATE_" + parentJF.getMapVars().get("DEFAULT_LOCATION")) != null) {
            Date dt = sdf.parse(parentJF.getMapVars().get("POS_CLOSE_DATE_" + parentJF.getMapVars().get("DEFAULT_LOCATION")));
            Date dt2 = sdf.parse(sdf.format(dataInv_date));
            if (dt2.compareTo(dt) <= 0) {
                JOptionPane.showMessageDialog(parentJF, "Date should be great than " + sdf.format(dt));
                throw new Exception("Date should be great than " + sdf.format(dt));
            }
        }
        return vld;
    }

    public void addUpdate() throws Exception {
        if (!is_valid_entry()) {
            return;
        }
        String refer = getItemRefer(txtItemCode.getText());
        String ctg = "DEFAULT";
        txtItemCode.setText(refer);
        if (((BigDecimal) (getValueFromItemsRow(refer, 6))).doubleValue() > 0) {
            txtItemCode.requestFocus();
            txtItemCode.selectAll();
            JOptionPane.showMessageDialog(parentJF, "Parent Item can not select here");
            return;
        }
        if (refer.length() > 0) {
            txtItemCode.selectAll();
            ctg = getItemCategory(refer);// utils.getSqlValue("select nvl(max(code),'DEFAULT')  from masterasm_ctg where baseitem='" + refer + "' and code!='DEFAULT'", dbConnection);
            /*
            if (!df.equals("DEFAULT")) {
            lovDialog ld = new lovDialog(parentJF, true);
            ld.setNoOfClicksOnSelection(selectListView.SINGLE_CLICK_SELECTION);
            ld.setSqlstr("select CODE,NAME_E,PRICE1 FROM MASTERASM_CTG WHERE BASEITEM='" + refer + "' ORDER BY CODE");
            ld.setDbc(dbc);
            ld.setVisible(true);
            if (ld.getSelectedNo() < 0) {
            return;
            }
            ctg = ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "CODE").toString();
             * */
            if (ctg.length() == 0) {
                return;
            }

            int fnd = -1;//itemdetails.locate("REFER", refer, localTableModel.FIND_EXACT);

            for (int i = 0; i
                    < itemdetails.getRows().size(); i++) {
                if (itemdetails.getFieldValue(i, "REFER").equals(refer) && itemdetails.getFieldValue(i, "SIZE_OF_DESCR").equals(ctg)) {
                    fnd = i;
                    break;

                }


            }
            if (fnd < 0) {
                if (Double.valueOf(txtItemQty.getText()) > 0) {
                    int c = itemdetails.addRecord();
                    itemdetails.setFieldValue("ITEMPOS", BigDecimal.valueOf(itemdetails.getCursorNo() + 1));
                    itemdetails.setFieldValue("REFER", txtItemCode.getText());
                    itemdetails.setFieldValue("SIZE_OF_DESCR", ctg);
                    itemdetails.setFieldValue("PKQTY", BigDecimal.valueOf(Double.valueOf(txtItemQty.getText())));
                    if (itemsRows != null) {
                        itemdetails.setFieldValue("DESCR", getValueFromItemsRow(refer, 1));
                        itemdetails.setFieldValue("PRD_DATE", getValueFromItemsRow(refer, 7));
                        itemdetails.setFieldValue("EXP_DATE", getValueFromItemsRow(refer, 8));
                        itemdetails.setFieldValue("PACKD", getValueFromItemsRow(refer, 9));
                        itemdetails.setFieldValue("UNITD", getValueFromItemsRow(refer, 10));
                        itemdetails.setFieldValue("PACK", getValueFromItemsRow(refer, 11));
                    } else {
                        itemdetails.setFieldValue("DESCR", utils.getSqlValue("select descr from items where reference='" + refer + "'", dbConnection));
                        itemdetails.setFieldValue("PRD_DATE", utils.getSqlValue("select PRD_DT from items where reference='" + refer + "'", dbConnection));
                        itemdetails.setFieldValue("EXP_DATE", utils.getSqlValue("select EXP_DT from items where reference='" + refer + "'", dbConnection));
                    }
                    itemdetails.setFieldValue("PRICE", String.valueOf(lastSelectedItemPrice));
                }

            } else {
                BigDecimal b = BigDecimal.valueOf(Double.valueOf(itemdetails.getFieldValue(fnd, "PKQTY").toString()) + Double.valueOf(txtItemQty.getText()));
                if (b.doubleValue() <= 0) {
                    itemdetails.deleteRow(fnd);
                    itemdetails.getRowlistner().onCalc(0);
                } else {
                    itemdetails.setFieldValue(fnd, "PKQTY", b);
                }

            }
            jTable1.updateUI();
        }
    }

    public void update_sums() {
        sumNetAmt = (sumGrossAmt + sumAddAmt) - sumDiscountAmt;
        sumChangeAmt = sumCashAmt - sumNetAmt;
        sumTxtTotQty.setText(decimalformat.format(sumQty));
        sumTxtGrossAmount.setText(decimalformat.format(sumGrossAmt));
        sumTxtNetAmount.setText(decimalformat.format(sumNetAmt));
        sumTxtChangeAmt.setText(decimalformat.format(sumChangeAmt));
        sumTxtAddAmt.setText(decimalformat.format(sumAddAmt));
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
                //decimalformat.parse(txt.getText());
                if (txt.getName().equals("sumTxtDiscAmt")) {
                    sumDiscountP = 0;
                    sumDiscountAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    if (sumDiscountAmt > sumGrossAmt) {
                        sumDiscountAmt = 0;
                    }
                    if (sumGrossAmt > 0 && sumDiscountAmt > 0) {
                        sumDiscountP = (sumDiscountAmt / sumGrossAmt) * 100;
                    }
                    sumTxtDiscP.setText(String.valueOf(sumDiscountP));
                    //txt.setText(decimalformat.format(sumDiscountAmt));
                }

                if (txt.getName().equals("sumTxtPaidAmt")) {
                    sumPaidAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    //txt.setText(decimalformat.format(sumPaidAmt));
                }
                if (txt.getName().equals("sumTxtCashAmt")) {
                    sumCashAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    //txt.setText(decimalformat.format(sumCashAmt));
                }
                if (txt.getName().equals("sumTxtAddAmt")) {
                    sumAddAmt = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                }

                if (txt.getName().equals("sumTxtDiscP")) {

                    sumDiscountP = ((Number) decimalformat.parse(txt.getText())).doubleValue();
                    sumDiscountAmt = 0;
                    if (sumDiscountP > 100) {
                        sumDiscountP = 100;
                        txt.setText(String.valueOf(sumDiscountP));
                    }
                    if (sumDiscountP < 0) {
                        sumDiscountP = 0;
                        txt.setText(String.valueOf(sumDiscountP));
                    }
                    if (sumGrossAmt > 0 && sumDiscountP > 0) {
                        sumDiscountAmt = (sumGrossAmt / 100) * sumDiscountP;
                    }
                    sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
                }

                update_sums();
                ret =
                        true;
            } catch (ParseException ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                //JOptionPane.showMessageDialog(parentJF, ex.getMessage());
                ret = false;
            }

            return ret;
        }
    };

    public void clearPayments() {
        txtDiscDescr.setText("");
        payTable.removeEditor();
        for (int i = 0; i < payRows.getRows().size(); i++) {
            Row row = payRows.getRows().get(i);
            row.lst.get(2).setValue(Double.valueOf("0"), Double.valueOf("0"));
        }
        payTable.updateUI();
    }

    public void setupItemDetails() throws Exception {
        itemdetails.setRowlistner(new rowTriggerListner() {

            public void onCalc(int cursorNo) {
                double p = 0, q = 0, pk = 1;
                if (itemdetails.getFieldValue(cursorNo, "PRICE") != null) {
                    p = utils.noValue(Double.valueOf(itemdetails.getFieldValue(cursorNo, "PRICE").toString()), Double.valueOf(0));
                }

                if (itemdetails.getFieldValue(cursorNo, "PKQTY") != null && itemdetails.getFieldValue(cursorNo, "PACK") != null) {
                    q = utils.noValue(Double.valueOf(itemdetails.getFieldValue(cursorNo, "PKQTY").toString()), Double.valueOf(0));
                    pk = utils.noValue(Double.valueOf(itemdetails.getFieldValue(cursorNo, "PACK").toString()), Double.valueOf(0));
                    itemdetails.setFieldValue(cursorNo, "ALLQTY", BigDecimal.valueOf(q * pk));
                    sumQty = itemdetails.getSummaryOf("PKQTY", localTableModel.SUMMARY_SUM);

                }
                itemdetails.setFieldValue(cursorNo, "AMOUNT", BigDecimal.valueOf(p * q));
                itemdetails.setFieldValue(cursorNo, "QTY", 0);
                sumGrossAmt = itemdetails.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
                sumPaidAmt = sumGrossAmt + sumAddAmt;
                update_sums();

            }
        });

        sumTxtDiscAmt.setInputVerifier(number_iv);
        sumTxtPaidAmt.setInputVerifier(number_iv);
        sumTxtDiscP.setInputVerifier(number_iv);
        sumTxtCashAmt.setInputVerifier(number_iv);
        sumTxtAddAmt.setInputVerifier(number_iv);

        itemdetails.executeQuery("select itempos,refer,descr,price,pkqty,0 Amount,"
                + "POSPUR2.PERIODCODE, POSPUR2.LOCATION_CODE, POSPUR2.INVOICE_NO,"
                + " POSPUR2.INVOICE_CODE, POSPUR2.TYPE, POSPUR2.SYSDT, POSPUR2.STRA, "
                + "POSPUR2.STRB, POSPUR2.PKCOST, POSPUR2.DISC_AMT, POSPUR2.PACK, POSPUR2.PACKD,"
                + " POSPUR2.UNITD, POSPUR2.DAT,POSPUR2.QTY, POSPUR2.ALLQTY, POSPUR2.PRD_DATE, POSPUR2.EXP_DATE, "
                + "POSPUR2.YEAR, POSPUR2.ISCLOSE, POSPUR2.FLAG, POSPUR2.ORDWAS, POSPUR2.KEYFLD, POSPUR2.PACKAGED, "
                + "POSPUR2.ADDITIONAL_AMT, POSPUR2.RATE, POSPUR2.CURRENCY, POSPUR2.CREATDT, POSPUR2.ORDERNO, POSPUR2.DELIVEREDG,"
                + " POSPUR2.DELIVERED, POSPUR2.QTYIN, POSPUR2.QTYOUT, POSPUR2.DISC_AMT_GROSS, POSPUR2.STAND_NO,"
                + " POSPUR2.SIZE_OF, POSPUR2.SIZE_OF_DESCR, POSPUR2.SLSMNXX, POSPUR2.ADD_AMT_GROSS, "
                + "POSPUR2.COSTCENT, POSPUR2.ISSUED_DATE, POSPUR2.RECIPT_DATE, POSPUR2.HAS_ISSUED, POSPUR2.HAS_RECIEVED,"
                + " POSPUR2.ISSUED_KEYFLD, POSPUR2.RECIEVED_KEYFLD, POSPUR2.JVKEYFLD "
                + " from pospur2,items where items.reference=pospur2.refer and pospur2.keyfld='" + QRYSTR + "' order by ITEMpos", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});
        itemdetails.getColByName("DESCR").setDatabaseCol(false);
        itemdetails.getColByName("SIZE_OF_DESCR").setTitle("CATEGORY");
        itemdetails.getColByName("AMOUNT").setDatabaseCol(false);
        itemdetails.getColByName("ITEMPOS").setAlignmnet(JLabel.CENTER);
        itemdetails.getColByName("AMOUNT").setColor(Color.YELLOW);
        //itemdetails.getColByName("pack_qty").setColname("PKQTY");
        itemdetails.getColByName("PKQTY").setTitle("Pack Qty");

        jTable1.setModel(itemdetails);
        jTable1.getColumnModel().setColumnSelectionAllowed(false);
        jTable1.getTableHeader().setReorderingAllowed(false);

        for (int colx = 0; colx
                < jTable1.getModel().getColumnCount(); colx++) {
            jTable1.getColumnModel().getColumn(colx).setCellRenderer(new ColorRenderer(true));
        }

        applyColumn(itemdetails.getColByName("ITEMPOS"), jTable1, 0);
        applyColumn(itemdetails.getColByName("SIZE_OF_DESCR"), jTable1, 1);
        applyColumn(itemdetails.getColByName("REFER"), jTable1, 2);
        applyColumn(itemdetails.getColByName("DESCR"), jTable1, 3);
        applyColumn(itemdetails.getColByName("PRICE"), jTable1, 4);
        applyColumn(itemdetails.getColByName("PKQTY"), jTable1, 5);
        applyColumn(itemdetails.getColByName("AMOUNT"), jTable1, 6);

        itemdetails.getColByName("AMOUNT").setNumberFormat(decimalformat.toPattern());
        itemdetails.getColByName("PRICE").setNumberFormat(decimalformat.toPattern());


        jTable1.revalidate();

    }

    public void applyColumn(qryColumn qc, JTable table, int col) {
        int width = qc.getDisplaySize();
        if (parentJF.getMapVars().get("sale_col_" + col + "_width") != null) {
            width = Integer.valueOf(parentJF.getMapVars().get("sale_col_" + col + "_width"));
        }

        jTable1.getColumnModel().getColumn(col).setPreferredWidth(width);
        jTable1.getColumnModel().getColumn(col).setWidth(width);
    }
    private double fromk = 0;
    private double tok = 0;

    public void load_data(String qr) {
        this.QRYSTR = qr;
        load_data();
    }

    public void load_data() {
        try {
            if (parentJF.getMapVars().get("default_page") != null) {
                jTabbedPane1.setSelectedIndex(Integer.valueOf(parentJF.getMapVars().get("default_page")));
            }
            parentJF.displayDeliveryStatus(false);
            setupItemDetails();
            clearPayments();
            sumChangeAmt = 0;
            sumAddAmt = 0;
            sumDiscountAmt = 0;
            sumDiscountP = 0;
            sumGrossAmt = 0;
            sumNetAmt = 0;
            sumPaidAmt = 0;
            sumCashAmt = 0;
            sumQty = 0;
            dataInv_date.setTime(System.currentTimeMillis());
            update_sums();
            sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
            sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
            sumTxtDiscP.setText(decimalformat.format(sumDiscountP));
            sumTxtCashAmt.setText(decimalformat.format(sumCashAmt));
            itemdetails.setEditAllowed(false);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTable1.setRowHeight(30);
            fromk = Double.valueOf(parentJF.getMapVars().get("FROM_KEYFLD"));
            tok = Double.valueOf(parentJF.getMapVars().get("TO_KEYFLD"));
            dataLocationCode =
                    parentJF.getMapVars().get("DEFAULT_LOCATION");
            dataLocationName =
                    parentJF.getMapVars().get("LOCATION_NAME");
            txtLocation.setText(dataLocationName);
            txtKeyfld.setText(String.valueOf(dataKeyfld));
            txtInvoiceDate.setText(sf.format(dataInv_date));
            jComboBox1.setSelectedIndex(0);
            listDriver.setSelectedIndex(0);
            txtInvoiceNo.setText(utils.getSqlValue("select nvl(max(invoice_no),0)+1 from POSPUR1 WHERE LOCATION_CODE='" + dataLocationCode + "' and "
                    + "periodcode='" + parentJF.getMapVars().get("CURRENT_PERIOD") + "' and invoice_code=" + dataInvoiceCode, dbConnection));

            adrArea.setSelectedItem(null);
            adrBldg.setText("");
            adrBlock.setText("");
            adrCustomerName.setText("");
            adrDeliveryDate.setText("");
            adrFloorNo.setText("");
            adrJedda.setText("");
            adrOtherTel.setText("");
            adrPhone.setText("");
            adrStreet.setText("");
            adrWorkAddress.setText("");
            txtDiscDescr.setText("");
            if (QRYSTR.length() != 0) {
                ps_qry_1 = dbConnection.prepareStatement("select *from POSpur1 where keyfld=" + QRYSTR,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rss = ps_qry_1.executeQuery();
                if (rss.first()) {
                    txtKeyfld.setText(rss.getString("KEYFLD"));
                    dataKeyfld = rss.getDouble("keyfld");
                    txtInvoiceNo.setText(rss.getString("INVOICE_NO"));
                    txtInvoiceDate.setText(sf.format(new Date(rss.getTimestamp("INVOICE_DATE").getTime())));
                    txtDiscDescr.setText(rss.getString("MEMO"));

                    for (int i = 0; i < jComboBox1.getModel().getSize(); i++) {
                        if (jComboBox1.getModel().getElementAt(i) instanceof dataCell
                                && (Double.valueOf(((dataCell) jComboBox1.getModel().getElementAt(i)).getValue().toString()) == rss.getDouble("TYPE"))) {
                            jComboBox1.setSelectedIndex(i);
                            break;
                        }
                    }
                    for (int i = 0; i < listDriver.getModel().getSize(); i++) {

                        if (listDriver.getModel().getElementAt(i) instanceof dataCell
                                && ((dataCell) listDriver.getModel().getElementAt(i)).getValue().toString().length() > 0
                                && (Double.valueOf(((dataCell) listDriver.getModel().getElementAt(i)).getValue().toString()) == rss.getDouble("SLSMN"))) {
                            listDriver.setSelectedIndex(i);
                            break;
                        }
                    }

                    //summary for paid amount from total paid.

                    sumTxtCashAmt.setText(decimalformat.format(rss.getDouble("PAIDAMT2")));
                    sumTxtDiscAmt.setText(decimalformat.format(rss.getDouble("DISC_AMT")));
                    sumDiscountAmt = rss.getDouble("DISC_AMT");
                    sumCashAmt = rss.getDouble("PAIDAMT2");
                    sumAddAmt = rss.getDouble("ADD_CHARGE");
                    sumGrossAmt = rss.getDouble("INV_AMT");
                    adrArea.setSelectedItem(rss.getString("ADDR_AREA"));
                    adrBldg.setText(rss.getString("ADDR_BLDG"));
                    adrBlock.setText(rss.getString("ADDR_BLOCK"));
                    adrCustomerName.setText(rss.getString("INV_REFNM"));
                    adrDeliveryDate.setText("");
                    adrFloorNo.setText(rss.getString("REFERENCE_INFORMATION"));
                    adrJedda.setText(rss.getString("ADDR_JEDDA"));
                    adrOtherTel.setText(rss.getString("ADDR_TEL"));
                    adrPhone.setText(rss.getString("INV_REF"));
                    adrStreet.setText(rss.getString("ADDR_STREET"));
                    adrWorkAddress.setText(rss.getString("WORK_ADDRESS"));

                    update_sums();
                    updateDatas();

                    setupItemDetails();
                    if (sumTxtDiscAmt.getInputVerifier() != null) {
                        sumTxtDiscAmt.getInputVerifier().verify(sumTxtDiscAmt);
                    }

                } else {
                    //JOptionPane.showConfirmDialog(parentJF, "Invoice not found");
                    throw new Exception("invoice not found");
                }
                setPaymentdetails();
            }
            jTable1.invalidate();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            repaint();
        }
    }
    PreparedStatement ps_titems = null;
    private List<Row> itemsRows = null;
    private List<Row> GroupRows = null;

    public void showGroups() {
    }

    public int showItems(JTable jtb) {
        return showItems(jtb, true);
    }

    public int showItems(JTable jtb, boolean refresh_group) {
        if (showing_items == true) {
            return 0;
        }
        if (dataInvoiceCode == 20 && itemsRows == null) {
            if (parentJF.getSp().isShowing_items() == false && parentJF.getSp().getItemsRows() != null) {
                itemsRows = new ArrayList<Row>();
                for (int i = 0; i < parentJF.getSp().getItemsRows().size(); i++) {
                    itemsRows.add(parentJF.getSp().getItemsRows().get(i));
                    ps_titems = parentJF.getSp().getPs_titems();
                    showGroups();
                }
            }
        }
        showing_items = true;
        int itmcount = 0;
        int itmcountg = 0;
        try {
            String sqlstr = "";
            if (ps_titems == null) {
                //setupItemCols();
                currentGroup = "";
                currentItem = "";
                if (itemsRows != null) {
                    for (int i = 0; i
                            < itemsRows.size(); i++) {
                        itemsRows.get(i).lst.clear();
                    }

                    itemsRows.clear();
                }

                String par = parentJF.getMapVars().get("SHOW_ITEM_GROUP");
                sqlstr =
                        " select reference  ,descr,  NVL(masterasm_ctg.price1,ITEMS.PRICE1) PRICE1,"
                        + "  parentitem,  masterasm_ctg.CODE,  items.barcode,"
                        + "  childcounts,  items.prd_dt,   items.exp_dt,  ITEMS.packd,"
                        + "  ITEMS.unitd,  ITEMS.pack,     ITEMS.LEVELNO "
                        + "  from items,masterasm_ctg where "
                        + "  (DESCR2 LIKE (SELECT NVL(MAX(DESCR2),'%')||'%' FROM ITEMS WHERE REFERENCE='" + par + "')) AND "
                        + "  BASEITEM(+)=REFERENCE  order by descr2";
                ps_titems =
                        dbConnection.prepareStatement(sqlstr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //ResultSet rs = ps_titems.executeQuery();
                itemsRows =
                        utils.convertRows(ps_titems, "", 0);

                showGroups();
                /*
                while (rs.next()) {
                Row r = new Row(no_of_item_cols);
                //selItems.getRows().add(r);
                selItems.getMasterRows().add(r);
                for (int i = 0; i <
                no_of_item_cols; i++) {
                r.setRowStatus(Row.ROW_INSERTED);
                r.lst.get(i).setValue(rs.getString("descr"), rs.getString("REFERENCE"));
                if (!rs.next()) {
                break;
                }

                }
                }
                 */
                //ps_titems.close();
                for (int colx = 0; colx
                        < itemTable.getModel().getColumnCount(); colx++) {
                    itemTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionLst, true));
                }

            }
            setupItemCols();
            if (refresh_group) {
                setupGroupCols();
            }
            if (currentGroup.length() == 0 && !parentJF.getMapVars().get("SHOW_ITEM_GROUP").equals("ALL")) {
                currentGroup = parentJF.getMapVars().get("SHOW_ITEM_GROUP");
            }

            dataCell dc = null, dc2 = null, dc1 = null, dcLevel = null;
            int valueFilled = 0;
            int valueFilledGroup = 0;
            String last_item = "";
            String last_item_group = "";
            int j = 0;
            Row r = new Row(no_of_item_cols);
            Row rg = new Row(no_of_item_cols);
            for (int i = 0; i < itemsRows.size(); i++) {
                for (j = 0; j < no_of_item_cols; j++) {
                    if (i + j > itemsRows.size()) {
                        break;
                    }

                    try {
                        dc = itemsRows.get(i + j).lst.get(3);
                        dc1 = itemsRows.get(i + j).lst.get(0);
                        dc2 = itemsRows.get(i + j).lst.get(1);
                        dcLevel = itemsRows.get(i + j).lst.get(12);
                    } catch (IndexOutOfBoundsException ex) {
                        break;
                    }

                    if (dc.getValue().toString().equals(currentItem) && !last_item.equals(dc1.getValue().toString())) {
                        r.setRowStatus(Row.ROW_INSERTED);
                        dc1 = itemsRows.get(i + j).lst.get(0);
                        dc2 = itemsRows.get(i + j).lst.get(1);
                        dcLevel = itemsRows.get(i + j).lst.get(6);
                        r.lst.get(valueFilled).setValue(dc2.getDisplay(), dc1.getValue());
                        valueFilled++;
                        itmcount++;
                        last_item = dc1.getValue().toString();
                    }

                    if (refresh_group
                            && dc.getValue().toString().equals(currentGroup)
                            && !last_item_group.equals(dc1.getValue().toString())) {
                        rg.setRowStatus(Row.ROW_INSERTED);
                        dc1 = itemsRows.get(i + j).lst.get(0);
                        dc2 = itemsRows.get(i + j).lst.get(1);
                        dcLevel = itemsRows.get(i + j).lst.get(6);
                        rg.lst.get(valueFilledGroup).setValue(dc2.getDisplay(), dc1.getValue());
                        valueFilledGroup++;
                        itmcountg++;
                        last_item_group = dc1.getValue().toString();
                    }

                    if (valueFilled >= no_of_item_cols) {
                        selItems.getRows().add(r);
                        selItems.getMasterRows().add(r);
                        valueFilled = 0;
                        r = new Row(no_of_item_cols);
                    }
                    if (valueFilledGroup >= no_of_item_cols) {
                        selGrpItems.getRows().add(rg);
                        selGrpItems.getMasterRows().add(rg);
                        valueFilledGroup = 0;
                        rg = new Row(no_of_item_cols);
                    }

                }
                i = i + (j - 1);
            }

            if (valueFilled > 0) {
                selItems.getRows().add(r);
                selItems.getMasterRows().add(r);
                valueFilled = 0;
            }
            if (valueFilledGroup > 0) {
                selGrpItems.getRows().add(rg);
                selGrpItems.getMasterRows().add(rg);
                valueFilledGroup = 0;
            }

            itemTable.updateUI();
            groupTable.updateUI();
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        parentJF.getTxtMsg().setText(itmcount + " item(s) displayed " + currentItem + ".");
        parentJF.getTxtMsg().setForeground(Color.BLUE);
        showing_items = false;
        return itmcount;

    }

    public PreparedStatement getPs_titems() {
        return ps_titems;
    }
    private Timer tm = new Timer();

    public void start_showItems() {
        if (showing_items == false) {
            tm.schedule(new TimerTask() {

                @Override
                public void run() {
                    showItems(itemTable);
                }
            }, 100);
        }

    }

    @Override
    protected void finalize() throws Throwable {
        if (ps_titems != null && ps_titems.isClosed() == false) {
            ps_titems.close();
        }

        super.finalize();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox adrArea;
    private javax.swing.JTextField adrBldg;
    private javax.swing.JTextField adrBlock;
    private javax.swing.JTextField adrCustomerName;
    private javax.swing.JTextField adrDeliveryDate;
    private javax.swing.JTextField adrFloorNo;
    private javax.swing.JTextField adrJedda;
    private javax.swing.JTextField adrOtherTel;
    private javax.swing.JTextField adrPhone;
    private javax.swing.JTextField adrStreet;
    private javax.swing.JTextField adrWorkAddress;
    private javax.swing.JButton cmdBasic;
    private javax.swing.JButton cmdItemAdd;
    private javax.swing.JButton cmdItemAdd1;
    private javax.swing.JButton cmdItemAdd2;
    private javax.swing.JButton cmdItemAdd3;
    private javax.swing.JButton cmdItemAdd4;
    private javax.swing.JButton cmdItemAdd5;
    private javax.swing.JButton cmdItemAdd6;
    private javax.swing.JButton cmdPayHereAll;
    private javax.swing.JButton cmdPayRestHere;
    private javax.swing.JButton cmdPayment;
    private javax.swing.JTable groupTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAmt;
    private javax.swing.JLabel lblChangeAmt;
    private javax.swing.JComboBox listDriver;
    private javax.swing.JPanel panelCenter;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable payTable;
    private javax.swing.JSplitPane splitGroupItem;
    private javax.swing.JTextField sumTxtAddAmt;
    private javax.swing.JTextField sumTxtCashAmt;
    private javax.swing.JLabel sumTxtChangeAmt;
    private javax.swing.JTextField sumTxtDiscAmt;
    private javax.swing.JTextField sumTxtDiscP;
    private javax.swing.JLabel sumTxtGrossAmount;
    private javax.swing.JLabel sumTxtNetAmount;
    private javax.swing.JTextField sumTxtPaidAmt;
    private javax.swing.JLabel sumTxtTotQty;
    private javax.swing.JTextField txtDiscDescr;
    private javax.swing.JLabel txtInvoiceDate;
    private javax.swing.JTextField txtInvoiceNo;
    private javax.swing.JTextField txtItemCode;
    private javax.swing.JTextField txtItemQty;
    private javax.swing.JLabel txtKeyfld;
    private javax.swing.JLabel txtLocation;
    // End of variables declaration//GEN-END:variables
    private MainFrame parentJF = null;
    private Connection dbConnection = null;
    private String dburl = "";
    private String username = "";
    private String password = "";
    private PreparedStatement ps_qry_1 = null;
    private PreparedStatement ps_qry_2 = null;
    private double dataKeyfld = -1;
    private String datasalesp = "";
    private double dataInvoiceNo = -1;
    private Date dataInv_date = new Date(System.currentTimeMillis());
    private String dataLocationCode = "";
    private String dataLocationName = "";
    private int posno = -1;
    private int strno = 0;
    private String QRYSTR = "";
    private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    private localTableModel itemdetails = new localTableModel();
    //private ResultSetTableModelFactory factory;
    private DBClass dbc = null;
    private localTableModel selItems = null;
    private localTableModel selGrpItems = null;
    private String currentItem = "";
    private String currentGroup = "";
    private int no_of_item_cols = 3;
    private double sumGrossAmt = 0;
    private double sumNetAmt = 0;
    private double sumCashAmt = 0;
    private double sumPaidAmt = 0;
    private double sumAddAmt = 0;
    private double sumChangeAmt = 0;
    private double sumDiscountAmt = 0;
    private double sumDiscountP = 0;
    private double sumQty = 0;
    private DecimalFormat decimalformat = null;
    private SimpleDateFormat dateformat = null;
    private boolean showing_items = false;
    private int dataInvoiceCode = 30;
    private int dataStore = 1;
    private double dataInvType = 1;

    public boolean isShowing_items() {
        return showing_items;
    }

    public void setShowing_items(boolean showing_items) {
        this.showing_items = showing_items;
    }

    public List<Row> getItemsRows() {
        return itemsRows;
    }
    private ActionListener cmdActionLst = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            String name = ((JButton) e.getSource()).getName();
        }
    };

    public void actformed(dataCell dc) {
        if (dc == null || dc.getValue() == null || dc.getValue().toString().length() == 0) {
            return;
        }

        double chld = 0;
        if (itemsRows == null) {
            chld = Double.valueOf(utils.getSqlValue("select childcounts from items where reference='" + dc.getValue() + "'", dbConnection));
        } else {
            chld = Double.valueOf(getValueFromItemsRow(dc.getValue().toString(), 6).toString());
        }

        if (chld <= 0) {
            txtItemCode.setText(dc.getValue().toString());
            cmdItemAdd.doClick();
        } else {
            currentItem = dc.getValue().toString();
            showItems(itemTable);
            updateUI();

        }
    }

    private class JTableButtonMouseListener extends MouseAdapter {

        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            //int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            //int row = e.getY() / table.getRowHeight();

            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();

            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof dataCell) {
                    ((dataCell) value).setDisplay("<font color=\"red\">" + ((dataCell) value).getDisplay() + "</font");
                    actformed((dataCell) value);
                    //((dataCell) value).setDisplay("<font color=\"red\">" + ((dataCell) value).getDisplay() + "</font");
                    boolean fnd = false;
                    for (int i = 0; (i < selGrpItems.getRows().size() && fnd == false); i++) {
                        Row r = selGrpItems.getRows().get(i);
                        for (int j = 0; j < r.lst.size(); j++) {
                            if (r.lst.get(j).getValue().toString().equals(((dataCell) value).getValue().toString())) {
                                r.lst.get(j).setDisplay("<font color=\"red\">" + r.lst.get(j).getDisplay() + "</font");
                                fnd = true;
                                break;
                            }
                        }
                    }
                    //table.updateUI();
                    itemTable.updateUI();
                    groupTable.updateUI();
//                    ((JButton) value).doClick();
                }
            }
        }
    }

    public void updateDatas() throws Exception {
        dataInv_date = sf.parse(txtInvoiceDate.getText());
        dataInvoiceNo =
                Integer.valueOf(txtInvoiceNo.getText());
        dataLocationCode =
                parentJF.getMapVars().get("DEFAULT_LOCATION");
        dataStore =
                Integer.valueOf(parentJF.getMapVars().get("DEFAULT_STORE"));
        dataInvType =
                Double.valueOf(((dataCell) jComboBox1.getModel().getSelectedItem()).getValue().toString());
        datasalesp =
                ((dataCell) listDriver.getModel().getSelectedItem()).getValue().toString();
    }

    public double save_data() throws Exception {
        if (!is_valid_entry()) {
            return -1;
        }

        if (itemdetails.getRows().size() <= 0) {
            JOptionPane.showMessageDialog(parentJF, "No sales data found");
            throw new Exception("No items data found");
        }
        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        if (sumPaidAmt <= 0) {
            int f = payRows.locate("NO", ((dataCell) jComboBox1.getSelectedItem()).getValue().toString(), localTableModel.FIND_EXACT);
            if (f >= 0) {
                payTable.changeSelection(f, 2, false, false);
                cmdPayHereAll.doClick();
            }
        }
        if (utils.roundToDecimals(sumPaidAmt, 3) != utils.roundToDecimals(sumNetAmt, 3)) {
            payTable.changeSelection(0, 2, false, false);
            cmdPayHereAll.doClick();
        }

        if (utils.roundToDecimals(sumPaidAmt, 3) != utils.roundToDecimals(sumNetAmt, 3)) {
            JOptionPane.showMessageDialog(parentJF, "Oops ! paid amount is not valid, not equal to net amount");
            throw new Exception("Paid amount is not valid");
        }

        double kf = -1;
        PreparedStatement ps_up = null;
        try {
            String posinout = "";
            if (dataInvoiceCode == 30) {
                posinout = "POS_OUT_QTY";
            }

            if (dataInvoiceCode == 20) {
                posinout = "POS_IN_QTY";
            }

            dbConnection.setAutoCommit(false);
            if (QRYSTR.length() > 0) {
                PreparedStatement ps = dbConnection.prepareStatement("select *from pospur2 where keyfld=" + QRYSTR, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery();
                rs.beforeFirst();
                ps_up =
                        dbConnection.prepareStatement("begin update stori set pos_out=pos_out-? where refer=? and strno=?; "
                        + "update items set usecounts=usecounts-1 where reference=?;"
                        + "end;");
                while (rs.next()) {
                    ps_up.setDouble(1, rs.getDouble("allqty"));
                    ps_up.setString(2, rs.getString("refer"));
                    ps_up.setDouble(3, rs.getDouble("stra"));

                    ps_up.setString(4, rs.getString("refer"));
                    ps_up.executeUpdate();
                }

                ps_up.close();
                ps_up = dbConnection.prepareStatement("delete from pospur2 where keyfld=" + QRYSTR);
                ps_up.executeUpdate();
                ps_up.close();
                ps_up = dbConnection.prepareStatement("delete from pospur1 where keyfld=" + QRYSTR);
                ps_up.executeUpdate();
                ps_up.close();
            }

            if (QRYSTR.length() > 0) {
                dataKeyfld = Double.valueOf(QRYSTR);
            } else {
                dataKeyfld = Double.valueOf(utils.getSqlValue("select nvl(max(KEYFLD),0)+1 from pospur1 " + " ", dbConnection));
//                        Double.valueOf(utils.getSqlValue("select nvl(max(KEYFLD)," + fromk + ")+1 from pospur1 " +
//                        " where (keyfld>" + fromk + " or "+fromk+"=0) and (keyfld<=" + tok+" or "+tok+"=0)", dbConnection));
            }

            txtKeyfld.setText(String.valueOf(dataKeyfld));
            updateDatas();
            if (datasalesp.length() == 0 && utils.roundToDecimals(sumCashAmt, 3) < utils.roundToDecimals(sumNetAmt, 3) && dataInvoiceCode == 30) {
                sumCashAmt = sumNetAmt;
                sumTxtCashAmt.setText(decimalformat.format(sumNetAmt));
                sumTxtCashAmt.requestFocus();
                throw new Exception("Cash given value is less than  " + decimalformat.format(sumNetAmt));
            }
            /* PERIODCODE, LOCATION_CODE, INVOICE_NO,
            INVOICE_CODE, TYPE, INVOICE_DATE,
            STRA, USERNAME, INV_AMT,
            DISC_AMT, CREATDT, KEYFLD
             */

            ps_up =
                    dbConnection.prepareStatement("begin "
                    + "insert into pospur1("
                    + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
                    + "INVOICE_CODE, TYPE, INVOICE_DATE,"
                    + "STRA, USERNAME, INV_AMT, "
                    + "DISC_AMT, CREATDT, KEYFLD,YEAR,SLSMN, "
                    + "INV_REF,INV_REFNM,ADDR_AREA,"
                    + "ADDR_TEL ,HOME_ADDRESS,WORK_ADDRESS,SPEC_COMMENTS,"
                    + "COMPLAINS,addr_jedda,addr_block,addr_street,"
                    + "addr_bldg,reference_information,paidamt2,totqty,add_charge,MEMO"
                    + ")values ("
                    + "?,?,?,?,?,?,?,?,?,?,sysdate,?,'2003',?,"
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                    + ");"
                    + "update_pos_customer(?);"
                    + "delete from POSPAYMENTS where vou_keyfld=?;"
                    + "end; ");
            ps_up.setString(1, parentJF.getMapVars().get("CURRENT_PERIOD"));
            ps_up.setString(2, dataLocationCode);
            ps_up.setString(3, txtInvoiceNo.getText());
            ps_up.setInt(4, dataInvoiceCode);
            ps_up.setDouble(5, dataInvType);
            ps_up.setDate(6, new java.sql.Date(dataInv_date.getTime()));
            ps_up.setInt(7, dataStore);
            ps_up.setString(8, parentJF.getLp().getLogon_user());
            ps_up.setDouble(9, sumGrossAmt);
            ps_up.setDouble(10, sumDiscountAmt);
            ps_up.setDouble(11, dataKeyfld);
            ps_up.setString(12, datasalesp);
            ps_up.setString(13, adrPhone.getText());
            ps_up.setString(14, adrCustomerName.getText());
            ps_up.setString(15, ((String) adrArea.getSelectedItem()));
            ps_up.setString(16, adrOtherTel.getText());
            ps_up.setString(17, "");
            ps_up.setString(18, adrWorkAddress.getText());
            ps_up.setString(19, "");
            ps_up.setString(20, "");
            ps_up.setString(21, adrJedda.getText());
            ps_up.setString(22, adrBlock.getText());
            ps_up.setString(23, adrStreet.getText());
            ps_up.setString(24, adrBldg.getText());
            ps_up.setString(25, adrFloorNo.getText());
            ps_up.setDouble(26, sumCashAmt);
            ps_up.setDouble(27, sumQty);
            ps_up.setDouble(28, sumAddAmt);
            ps_up.setString(29, txtDiscDescr.getText());
            ps_up.setDouble(30, dataKeyfld);
            ps_up.setDouble(31, dataKeyfld);
            ps_up.executeUpdate();
            ps_up.close();

            double itm_gros_disc = 0, prc = 0, allq = 0, pk = 0;
            ps_up = dbConnection.prepareStatement("BEGIN INSERT INTO POSPUR2("
                    + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
                    + "INVOICE_CODE, TYPE, ITEMPOS, REFER, STRA, "
                    + "PRICE, PACK, PACKD, UNITD, DAT, QTY, "
                    + "PKQTY, ALLQTY, PRD_DATE, EXP_DATE, YEAR, KEYFLD, QTYIN, QTYOUT,creatdt,DISC_AMT_GROSS) VALUES ("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?); "
                    + posinout + "(?,?,?);"
                    + "update items set usecounts=usecounts+1 where reference=? ;"
                    + "end;");

            for (int i = 0; i
                    < itemdetails.getRows().size(); i++) {
                Row row = itemdetails.getRows().get(i);
                itemdetails.setCursorNo(i);
                double qtin = 0, qtout = 0;
                if (dataInvoiceCode == 30) {
                    qtout = Double.valueOf(itemdetails.getFieldValue(i, "ALLQTY").toString());
                }

                if (dataInvoiceCode == 20) {
                    qtin = Double.valueOf(itemdetails.getFieldValue(i, "ALLQTY").toString());
                }
                itm_gros_disc = 0;
                if (sumGrossAmt > 0 && sumDiscountAmt > 0) {
                    prc = Double.valueOf(itemdetails.getFieldValue(i, "PRICE").toString());
                    allq = Double.valueOf(itemdetails.getFieldValue(i, "ALLQTY").toString());
                    pk = Double.valueOf(itemdetails.getFieldValue(i, "PACK").toString());
                    itm_gros_disc = ((sumDiscountAmt / sumGrossAmt) * (((prc) / pk) * allq)) / (allq / pk);
                }
                ps_up.setString(1, parentJF.getMapVars().get("CURRENT_PERIOD"));
                ps_up.setString(2, dataLocationCode);
                ps_up.setString(3, txtInvoiceNo.getText());
                ps_up.setInt(4, dataInvoiceCode);
                ps_up.setDouble(5, dataInvType);
                ps_up.setInt(6, Integer.valueOf(itemdetails.getFieldValue(i, "ITEMPOS").toString()));
                ps_up.setString(7, itemdetails.getFieldValue(i, "REFER").toString());
                ps_up.setInt(8, dataStore);
                ps_up.setDouble(9, Double.valueOf(itemdetails.getFieldValue(i, "PRICE").toString()));
                ps_up.setDouble(10, Double.valueOf(itemdetails.getFieldValue(i, "PACK").toString()));
                ps_up.setString(11, (itemdetails.getFieldValue(i, "PACKD").toString()));
                ps_up.setString(12, (itemdetails.getFieldValue(i, "UNITD").toString()));
                ps_up.setDate(13, new java.sql.Date(dataInv_date.getTime()));
                ps_up.setDouble(14, Double.valueOf(itemdetails.getFieldValue(i, "QTY").toString()));
                ps_up.setDouble(15, Double.valueOf(itemdetails.getFieldValue(i, "PKQTY").toString()));
                ps_up.setDouble(16, Double.valueOf(itemdetails.getFieldValue(i, "ALLQTY").toString()));
                ps_up.setTimestamp(17, (Timestamp) itemdetails.getFieldValue(i, "PRD_DATE"));
                ps_up.setTimestamp(18, (Timestamp) itemdetails.getFieldValue(i, "EXP_DATE"));
                ps_up.setString(19, "2003");
                ps_up.setDouble(20, dataKeyfld);
                ps_up.setDouble(21, qtin);
                ps_up.setDouble(22, qtout);
                ps_up.setDouble(23, itm_gros_disc);

                //pos in out calculation
                ps_up.setString(24, itemdetails.getFieldValue(i, "REFER").toString());
                ps_up.setInt(25, dataStore);
                ps_up.setDouble(26, Double.valueOf(itemdetails.getFieldValue(i, "ALLQTY").toString()));

                // item count increments
                ps_up.setString(27, itemdetails.getFieldValue(i, "REFER").toString());

                ps_up.executeUpdate();
            }

            kf = dataKeyfld;
            ps_up.close();

            ps_up = dbConnection.prepareStatement("begin insert into POSPAYMENTS(VOU_KEYFLD, TYPE_NO,"
                    + " AMOUNT, ACCNO, ACCNAME) values ("
                    + "?,?,?,?,?); end;");
            payTable.removeEditor();
            for (int i = 0; i < payRows.getRows().size(); i++) {
                Row row = payRows.getRows().get(i);
                Double f = Double.valueOf(row.lst.get(2).getValue().toString());
                if (f > 0) {
                    if (row.lst.get(3).getValue() == null
                            || row.lst.get(4).getValue() == null) {
                        throw new Exception("Call Administrator for account  payment for KWD : " + row.lst.get(2).getValue().toString() + " for " + row.lst.get(1).getValue());
                    }
                    ps_up.setDouble(1, dataKeyfld);
                    ps_up.setDouble(2, ((Double) row.lst.get(0).getValue()));
                    ps_up.setDouble(3, ((Double) row.lst.get(2).getValue()));
                    ps_up.setString(4, row.lst.get(3).getValue().toString());
                    ps_up.setString(5, row.lst.get(4).getValue().toString());
                    ps_up.executeUpdate();
                }
            }
            ps_up.close();

            QRYSTR = "";
            dbConnection.commit();
            load_data();
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());

            try {
                dbConnection.rollback();
                return -1;
            } catch (SQLException ex1) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex1);
                JOptionPane.showMessageDialog(parentJF, ex1.getMessage());
            }
        }

        return kf;
    }

    public void show_distinct_item_list() {
        if (itemsRows == null) {
            showItems(itemTable);
        }

        lovDialog ld = new lovDialog(parentJF, true);
        ld.setNoOfClicksOnSelection(selectListView.DOUBLE_CLICK_SELECTION);
        ld.getSlov().getLctb().clearALl();
        qryColumn qc = new qryColumn(0, "ITEM_CODE");
        ld.getSlov().getLctb().getQrycols().add(qc);
        ld.getSlov().getLctb().getVisbleQrycols().add(qc);
        qc =
                new qryColumn(1, "ITEM_NAME");
        ld.getSlov().getLctb().getQrycols().add(qc);
        qc.setDisplaySize(300);
        ld.getSlov().getLctb().getVisbleQrycols().add(qc);
        qc =
                new qryColumn(2, "PRICE");
        ld.getSlov().getLctb().getQrycols().add(qc);
        ld.getSlov().getLctb().getVisbleQrycols().add(qc);

        String lastitem = "";
        for (int i = 0; i
                < itemsRows.size(); i++) {
            Row row = itemsRows.get(i);
            if (!row.lst.get(0).getValue().toString().equals(lastitem)) {
                Row nw = new Row(3);
                nw.lst.set(0, row.lst.get(0));
                nw.lst.set(1, row.lst.get(1));
                nw.lst.set(2, row.lst.get(2));
                ld.getSlov().getLctb().getRows().add(nw);
                lastitem =
                        row.lst.get(0).getValue().toString();
            }

        }

        ld.getSlov().getLctb().setMasterRowsAsRows();
        ld.setwidth(400);
        ld.setVisible(true);
        if (ld.getSelectedNo() >= 0) {
            //lastSelectedItemPrice = Double.valueOf(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "PRICE").toString());
            txtItemCode.setText(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "ITEM_CODE").toString());
            cmdItemAdd.doClick();
        }
    }
}
