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
import com.forms.frmLovSearchPanel;
import com.forms.lovDialog;
import com.generic.model.DBClass;
import com.generic.model.Row;
import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import com.generic.model.qryColumn;
import com.generic.model.rowTriggerListner;
import com.generic.utils.BlinkLabel;
import com.generic.utils.ColorUtility;
import com.generic.utils.NumberEditor;
import com.generic.utils.QueryExe;
import com.generic.utils.RTableTriggers;
import com.generic.utils.RTables;
import com.generic.utils.RTablesCanvas;
import com.generic.utils.XTableColumnModel;
import com.generic.utils.utils;
import com.lov.lovFrame;
import com.lov.selectListView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;
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
import java.util.Iterator;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

/**
 *
 * @author yusuf
 */
public class salesPanel extends javax.swing.JPanel implements RTableTriggers {

    public salesPanel(int ic) {
        initComponents();
        utils.setupFormTextBoxes(this);
        jTable1.setColumnModel(new XTableColumnModel());
        itemTable.setColumnModel(new XTableColumnModel());
        groupTable.setColumnModel(new XTableColumnModel());
        pnl_reason_of_cancel.setVisible(false);
        dataInvoiceCode = ic;
        jTable1.setAutoCreateRowSorter(false);
        if (dataInvoiceCode == 20) {
            jPanel3.setBackground(Color.GREEN);
            lblAmt.setVisible(false);
            lblChangeAmt.setVisible(false);
            sumTxtChangeAmt.setVisible(false);
            sumTxtPaidAmt.setVisible(false);
            jTable1.setBackground(Color.GREEN);
            pnl_reason_of_cancel.setVisible(true);
            cmdDelivery.setEnabled(false);
            cmdTables.setEnabled(false);
        }

        ListSelectionListener l
                = new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e) {
                        final localTableModel t = (localTableModel) jTable1.getModel();
                        if (jTable1.getSelectedRow() < 0) {
                            return;
                        }
                        txtItemCode.setText(t.getFieldValue(jTable1.getSelectedRow(), "REFER").toString());
                        final Timer tm = new Timer();
                        tm.scheduleAtFixedRate(new TimerTask() {

                            @Override
                            public void run() {
                                String rfr = (txtItemCode.getText());
                                String ctg = (t.getFieldValue(jTable1.getSelectedRow(), "SIZE_OF_DESCR").toString());
                                Double price = Double.valueOf(t.getFieldValue(jTable1.getSelectedRow(), "PRICE").toString());
                                show_item_balance_message(rfr, ctg);
                                txtItemCode.setText(rfr);
                                txtCategory.setText(ctg);
                                txtItemPrice.setText(decimalformat.format(price));
                                tm.cancel();
                            }
                        }, 10, 1);
                    }
                };

        jTable1.getSelectionModel().addListSelectionListener(l);

        ListSelectionListener l1
                = new ListSelectionListener() {

                    public void valueChanged(ListSelectionEvent e) {
                        on_deliver_selection(true);
                    }
                };
        tbl_delivery.getSelectionModel().addListSelectionListener(l1);
    }

    public void on_deliver_selection(boolean showInfo) {
        parentJF.kf.toBack();
        final localTableModel t = (localTableModel) jTable1.getModel();
        cmdEditDeliver.setEnabled(false);
        cmdRemoveDeliver.setEnabled(false);
        if (tbl_delivery.getSelectedRow() < 0) {
            return;
        }
        if (delivery_data.getRowCount() <= 0) {
            return;
        }

        cmdEditDeliver.setEnabled(true);
        cmdRemoveDeliver.setEnabled(true);
        load_data();
        int r = tbl_delivery.getSelectedRow();
        String s = "";
        //Date dt = new Date(((Timestamp) delivery_data.getFieldValue(r, "DELIVERY_TIME")).getTime());
        Date tm = new Date(((Timestamp) delivery_data.getFieldValue(r, "DELIVERY_TIME")).getTime());
        Date adtm = dataDlvAdvanceDate;
        adtm.setTime(dataInv_date.getTime());
        if (!delivery_data.getFieldValue(r, "ADVANCE_DATE").toString().isEmpty()) {
            adtm = new Date(((Timestamp) delivery_data.getFieldValue(r, "ADVANCE_DATE")).getTime());
        }
        SimpleDateFormat sd = new SimpleDateFormat("H");
        SimpleDateFormat sd2 = new SimpleDateFormat("m");
        adrArea.setSelectedItem(delivery_data.getFieldValue(r, "ADDR_AREA"));
        adrBldg.setText(delivery_data.getFieldValue(r, "ADDR_BLDG").toString());
        adrBlock.setText(delivery_data.getFieldValue(r, "ADDR_BLOCK").toString());
        adrCustomerName.setText(delivery_data.getFieldValue(r, "CUST_NAME").toString());
        adrDeliveryDate.setText(dateformat.format(tm));
        adrHour.setText(sd.format(tm));
        adrMin.setText(sd2.format(tm));
        adrFloorNo.setText(delivery_data.getFieldValue(r, "ADDR_FLOOR").toString());
        adrJedda.setText(delivery_data.getFieldValue(r, "ADDR_JEDDA").toString());
        adrOtherTel.setText(delivery_data.getFieldValue(r, "ADDR_TEL").toString());
        adrPhone.setText(delivery_data.getFieldValue(r, "TEL").toString());
        adrStreet.setText(delivery_data.getFieldValue(r, "ADDR_STREET").toString());
        adrWorkAddress.setText(delivery_data.getFieldValue(r, "WORK_ADDRESS").toString());
        adrNotes.setText(delivery_data.getFieldValue(r, "SPEC_COMMENTS").toString());
        adrEmail.setText(delivery_data.getFieldValue(r, "EMAIL").toString());
        chkPickup.setSelected(delivery_data.getFieldValue(r, "PICK_UP").toString().equals("Y"));
        chkRCopyClient.setSelected(delivery_data.getFieldValue(r, "RECIPIENT_ADDRESS").toString().equals("Y"));
        adrRArea.setSelectedItem(delivery_data.getFieldValue(r, "ADDR_AREA"));
        adrRBldg.setText(delivery_data.getFieldValue(r, "ADDR_BLDG").toString());
        adrRBlock1.setText(delivery_data.getFieldValue(r, "ADDR_BLOCK").toString());
        adrRFloorNo.setText(delivery_data.getFieldValue(r, "ADDR_FLOOR").toString());
        adrRJedda.setText(delivery_data.getFieldValue(r, "ADDR_JEDDA").toString());
        adrROtherTel.setText(delivery_data.getFieldValue(r, "ADDR_TEL").toString());
        adrRPhone.setText(delivery_data.getFieldValue(r, "TEL").toString());
        adrRStreet.setText(delivery_data.getFieldValue(r, "ADDR_STREET").toString());
        adrRWorkAddress.setText(delivery_data.getFieldValue(r, "WORK_ADDRESS").toString());
        dataDlvDate.setTime(tm.getTime());
        dataDlvAdvanceDate.setTime(adtm.getTime());

        sumAdvanceAmt = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_PAID").toString());
        sumDiscountAmt = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "DISC_AMT").toString());
        sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
        sumTxtDiscAmt.getInputVerifier().verify(sumTxtDiscAmt);

        if (showInfo) {
            s = "<html><body> <font color=red>  Delivery time  </font>" + dateformat.format(tm) + "  " + sd.format(tm) + ":" + sd2.format(tm) + " <br>";
            s = s + "<font color=red> Pick Up </font> = " + delivery_data.getFieldValue(r, "PICK_UP").toString() + " <br>";
            s = s + "<font color=red> Tel </font>= " + delivery_data.getFieldValue(r, "TEL").toString() + "<br>";
            s = s + "<font color=red> Customer Name </font> " + delivery_data.getFieldValue(r, "CUST_NAME").toString() + " <br> ";
            s = s + "<font color=red> Notes </font> = " + delivery_data.getFieldValue(r, "SPEC_COMMENTS").toString() + " <br>";
            s = s + "<font color=red> Area </font> = " + delivery_data.getFieldValue(r, "ADDR_AREA").toString() + " <br>";
            s = s + "<font color=red> Street</font>  = " + delivery_data.getFieldValue(r, "ADDR_STREET").toString() + " <br>";
            s = s + "<font color=red> Building </font> = " + delivery_data.getFieldValue(r, "ADDR_BLDG").toString() + " <br>";
            s = s + "<font color=red> Block </font> = " + delivery_data.getFieldValue(r, "ADDR_BLOCK").toString() + " <br>";
            s = s + "<font color=red> Floor </font> = " + delivery_data.getFieldValue(r, "ADDR_FLOOR").toString() + "<br>";
            s = s + "<font color=red> Jedda </font> = " + delivery_data.getFieldValue(r, "ADDR_JEDDA").toString() + " <br>";
            s = s + "<font color=red> Other tel </font> = " + delivery_data.getFieldValue(r, "ADDR_TEL").toString() + " <br>";
            s = s + "<font color=red> Work Addr.</font>  = " + delivery_data.getFieldValue(r, "WORK_ADDRESS").toString() + " <br>";
            s = s + "<font color=red> Email </font> = " + delivery_data.getFieldValue(r, "EMAIL").toString() + " <br> </body><html>";
            ShowMessageFrame sw = new ShowMessageFrame(s, 14, 10000, true, ShowMessageFrame.RIGHT_BOTTOM);
        }
    }

    public void setupItemCols() {
        selItems.clearALl();
        selItems.getQrycols().clear();
        selItems.getVisbleQrycols().clear();
        selItems.setEditAllowed(false);
        itemTable.setRowHeight(Integer.valueOf(parentJF.getMapVars().get("sale_item_row_height")));
        itemTable.setRowSelectionAllowed(false);
        itemTable.setSelectionBackground(Color.GREEN);
        itemTable.setSelectionForeground(Color.BLACK);
        itemTable.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        qryColumn qc = null;
        for (int colx = 0; colx < no_of_item_cols; colx++) {
            qc = new qryColumn(colx, String.valueOf(colx));
            selItems.getQrycols().add(qc);
            selItems.getVisbleQrycols().add(qc);
        }
        itemTable.setModel(selItems);
        for (int colx = 0; colx < itemTable.getModel().getColumnCount(); colx++) {
            itemTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionMouse, true, Color.pink));
            ((ButtonRenderer) itemTable.getColumnModel().getColumn(colx).getCellRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
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
            groupTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionMouse, true, Color.yellow));
            ((ButtonRenderer) itemTable.getColumnModel().getColumn(colx).getCellRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txtVoucherNo = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        txtDate = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        cmdTakeAway = new javax.swing.JToggleButton();
        cmdDelivery = new javax.swing.JToggleButton();
        cmdTables = new javax.swing.JToggleButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlBasicInfo = new javax.swing.JPanel();
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
        pnlItems = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jButton8 = new javax.swing.JButton();
        lbl_itemSel = new BlinkLabel();
        chkShowSearchPanel = new javax.swing.JCheckBox();
        splitGroupItem = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        groupTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        pnlDeliverInfo = new javax.swing.JPanel();
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
        jLabel35 = new javax.swing.JLabel();
        adrHour = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        adrMin = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        adrNotes = new javax.swing.JTextArea();
        jLabel38 = new javax.swing.JLabel();
        adrEmail = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        chkPickup = new javax.swing.JCheckBox();
        jLabel40 = new javax.swing.JLabel();
        adrRArea = new javax.swing.JComboBox();
        jLabel41 = new javax.swing.JLabel();
        adrRStreet = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        adrRPhone = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        adrRWorkAddress = new javax.swing.JTextField();
        jLabel47 = new javax.swing.JLabel();
        adrRFloorNo = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        adrRJedda = new javax.swing.JTextField();
        jLabel48 = new javax.swing.JLabel();
        adrRBlock1 = new javax.swing.JTextField();
        jLabel45 = new javax.swing.JLabel();
        adrROtherTel = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        adrRBldg = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        chkRCopyClient = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();
        pnlClosing = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        payTable = new javax.swing.JTable();
        cmdPayHereAll = new javax.swing.JButton();
        cmdPayRestHere = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        txtDiscDescr = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        pnl_reason_of_cancel = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        txtReasonCancel = new javax.swing.JTextField();
        pnlAcc = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        lblAmt = new javax.swing.JLabel();
        sumTxtPaidAmt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        pnlDeliveries = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        txtCashier = new javax.swing.JComboBox();
        cmdCreateDeliver = new javax.swing.JButton();
        cmdEditDeliver = new javax.swing.JButton();
        cmdRemoveDeliver = new javax.swing.JButton();
        cmdRemoveDeliver1 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_delivery = new javax.swing.JTable();
        pnlTables = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtSection = new javax.swing.JComboBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        canvas = new RTablesCanvas();
        jPanel5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        searchPanel = new javax.swing.JPanel();
        pnlS = new com.forms.frmLovSearchPanel();
        pnlFooter_search = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        txtItemCode = new javax.swing.JTextField();
        jLabel102 = new javax.swing.JLabel();
        txtItemQty = new javax.swing.JTextField();
        cmdItemAdd = new javax.swing.JButton();
        jLabel104 = new javax.swing.JLabel();
        txtCategory = new javax.swing.JTextField();
        jLabel103 = new javax.swing.JLabel();
        txtItemPrice = new javax.swing.JTextField();
        cmdItemAdd2 = new javax.swing.JButton();
        panelCenter = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        cmdSave = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        cmdClose = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
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
        lblSelection = new BlinkLabel();

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setLastDividerLocation(250);
        jSplitPane1.setMinimumSize(new java.awt.Dimension(0, 0));

        jPanel10.setAlignmentX(0.0F);
        jPanel10.setAlignmentY(0.0F);

        jLabel31.setText("No #");

        txtVoucherNo.setFont(new java.awt.Font("Dialog", 1, 12));
        txtVoucherNo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtVoucherNo.setText(" ");
        txtVoucherNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel32.setText("Date");

        txtDate.setFont(new java.awt.Font("Dialog", 1, 12));
        txtDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtDate.setText(" ");
        txtDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVoucherNo, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtVoucherNo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel16.setLayout(new java.awt.GridLayout(1, 0, 30, 0));

        buttonGroup1.add(cmdTakeAway);
        cmdTakeAway.setText("Take Away");
        cmdTakeAway.setActionCommand("cmdTakeAway");
        cmdTakeAway.setBorderPainted(false);
        cmdTakeAway.setMargin(new Insets(3, 0, 3, 0));
        cmdTakeAway.setPreferredSize(null);
        cmdTakeAway.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTakeAwayActionPerformed(evt);
            }
        });
        jPanel16.add(cmdTakeAway);

        buttonGroup1.add(cmdDelivery);
        cmdDelivery.setText("Booking");
        cmdDelivery.setBorderPainted(false);
        cmdDelivery.setMargin(new Insets(3, 0, 3, 0));
        cmdDelivery.setActionCommand("cmdDelivery");
        cmdDelivery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDeliveryActionPerformed(evt);
            }
        });
        jPanel16.add(cmdDelivery);

        buttonGroup1.add(cmdTables);
        cmdTables.setText("Tables");
        cmdTables.setBorderPainted(false);
        cmdTables.setMargin(new Insets(3, 0, 3, 0));
        cmdTables.setActionCommand("cmdTables");
        cmdTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdTablesActionPerformed(evt);
            }
        });
        jPanel16.add(cmdTables);

        jTabbedPane1.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(63, 55));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        pnlBasicInfo.setPreferredSize(new java.awt.Dimension(244, 250));

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

        javax.swing.GroupLayout pnlBasicInfoLayout = new javax.swing.GroupLayout(pnlBasicInfo);
        pnlBasicInfo.setLayout(pnlBasicInfoLayout);
        pnlBasicInfoLayout.setHorizontalGroup(
            pnlBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        pnlBasicInfoLayout.setVerticalGroup(
            pnlBasicInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBasicInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
                .addGap(291, 291, 291))
        );

        jTabbedPane1.addTab("Basic Info", pnlBasicInfo);

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/revert.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        lbl_itemSel.setFont(new java.awt.Font("Dialog", 1, 12));
        lbl_itemSel.setForeground(new java.awt.Color(-65536,true));
        lbl_itemSel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_itemSel.setText(" ");
        lbl_itemSel.setToolTipText("");
        lbl_itemSel.setAutoscrolls(true);
        ((BlinkLabel)lbl_itemSel).setBlinking(true);

        chkShowSearchPanel.setText("Search");
        chkShowSearchPanel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowSearchPanelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_itemSel, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkShowSearchPanel)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(chkShowSearchPanel)
                .addComponent(lbl_itemSel))
            .addComponent(jButton8)
        );

        jPanel9Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chkShowSearchPanel, jButton8});

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        itemTable.setCellSelectionEnabled(true);
        itemTable.setName("ItemTable"); // NOI18N
        jScrollPane2.setViewportView(itemTable);

        splitGroupItem.setRightComponent(jScrollPane2);

        javax.swing.GroupLayout pnlItemsLayout = new javax.swing.GroupLayout(pnlItems);
        pnlItems.setLayout(pnlItemsLayout);
        pnlItemsLayout.setHorizontalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(splitGroupItem, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE)
        );
        pnlItemsLayout.setVerticalGroup(
            pnlItemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlItemsLayout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Items ", pnlItems);

        jPanel8.setMinimumSize(new java.awt.Dimension(100, 370));
        jPanel8.setLayout(new java.awt.GridLayout(30, 2, 5, 5));

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

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel35.setText("Hour");
        jPanel8.add(jLabel35);

        adrHour.setMaximumSize(null);
        adrHour.setMinimumSize(null);
        adrHour.setPreferredSize(null);
        jPanel8.add(adrHour);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel36.setText("Min");
        jPanel8.add(jLabel36);

        adrMin.setMaximumSize(null);
        adrMin.setMinimumSize(null);
        adrMin.setPreferredSize(null);
        jPanel8.add(adrMin);

        jLabel37.setText("Notes");
        jPanel8.add(jLabel37);

        adrNotes.setColumns(20);
        adrNotes.setRows(5);
        jScrollPane7.setViewportView(adrNotes);

        jPanel8.add(jScrollPane7);

        jLabel38.setText("EMAIL");
        jPanel8.add(jLabel38);

        adrEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrEmailActionPerformed(evt);
            }
        });
        jPanel8.add(adrEmail);

        jLabel39.setText("pickup");
        jPanel8.add(jLabel39);

        chkPickup.setText("pickup");
        jPanel8.add(chkPickup);

        jLabel40.setText("area");
        jPanel8.add(jLabel40);

        adrRArea.setEditable(true);
        adrRArea.setMaximumSize(null);
        adrRArea.setMinimumSize(null);
        adrRArea.setPreferredSize(null);
        adrRArea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrRAreaActionPerformed(evt);
            }
        });
        jPanel8.add(adrRArea);

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel41.setText("Street");
        jPanel8.add(jLabel41);

        adrRStreet.setMaximumSize(null);
        adrRStreet.setMinimumSize(null);
        adrRStreet.setPreferredSize(null);
        jPanel8.add(adrRStreet);

        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel46.setText("Phone");
        jPanel8.add(jLabel46);

        adrRPhone.setMaximumSize(null);
        adrRPhone.setMinimumSize(null);
        adrRPhone.setPreferredSize(null);
        adrRPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrRPhoneActionPerformed(evt);
            }
        });
        adrRPhone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                adrRPhoneFocusLost(evt);
            }
        });
        jPanel8.add(adrRPhone);

        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel43.setText("Flat No/Unit No");
        jPanel8.add(jLabel43);

        adrRWorkAddress.setMaximumSize(null);
        adrRWorkAddress.setMinimumSize(null);
        adrRWorkAddress.setPreferredSize(null);
        jPanel8.add(adrRWorkAddress);

        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel47.setText("Floor No");
        jPanel8.add(jLabel47);

        adrRFloorNo.setMaximumSize(null);
        adrRFloorNo.setMinimumSize(null);
        adrRFloorNo.setPreferredSize(null);
        adrRFloorNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrRFloorNoActionPerformed(evt);
            }
        });
        jPanel8.add(adrRFloorNo);

        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel44.setText("Jedda");
        jPanel8.add(jLabel44);

        adrRJedda.setMaximumSize(null);
        adrRJedda.setMinimumSize(null);
        adrRJedda.setPreferredSize(null);
        jPanel8.add(adrRJedda);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel48.setText("Block");
        jPanel8.add(jLabel48);

        adrRBlock1.setMaximumSize(null);
        adrRBlock1.setMinimumSize(null);
        adrRBlock1.setPreferredSize(null);
        jPanel8.add(adrRBlock1);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel45.setText("Other Tel");
        jPanel8.add(jLabel45);

        adrROtherTel.setMaximumSize(null);
        adrROtherTel.setMinimumSize(null);
        adrROtherTel.setPreferredSize(null);
        adrROtherTel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adrROtherTelActionPerformed(evt);
            }
        });
        jPanel8.add(adrROtherTel);

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel42.setText("Building");
        jPanel8.add(jLabel42);

        adrRBldg.setMaximumSize(null);
        adrRBldg.setMinimumSize(null);
        adrRBldg.setPreferredSize(null);
        jPanel8.add(adrRBldg);

        jLabel49.setText("rec address");
        jPanel8.add(jLabel49);
        jPanel8.add(chkRCopyClient);

        jButton1.setText("Main Screen>>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDeliverInfoLayout = new javax.swing.GroupLayout(pnlDeliverInfo);
        pnlDeliverInfo.setLayout(pnlDeliverInfoLayout);
        pnlDeliverInfoLayout.setHorizontalGroup(
            pnlDeliverInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeliverInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
            .addGroup(pnlDeliverInfoLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlDeliverInfoLayout.setVerticalGroup(
            pnlDeliverInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeliverInfoLayout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Delivery Info", pnlDeliverInfo);

        payTable.setFont(new java.awt.Font("Times New Roman", 0, 15)); // NOI18N
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

        jLabel30.setText("Reason Of Return");
        pnl_reason_of_cancel.add(jLabel30);

        txtReasonCancel.setColumns(15);
        pnl_reason_of_cancel.add(txtReasonCancel);

        javax.swing.GroupLayout pnlClosingLayout = new javax.swing.GroupLayout(pnlClosing);
        pnlClosing.setLayout(pnlClosingLayout);
        pnlClosingLayout.setHorizontalGroup(
            pnlClosingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClosingLayout.createSequentialGroup()
                .addComponent(cmdPayHereAll, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdPayRestHere, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
            .addComponent(pnl_reason_of_cancel, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        pnlClosingLayout.setVerticalGroup(
            pnlClosingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClosingLayout.createSequentialGroup()
                .addGroup(pnlClosingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdPayHereAll, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdPayRestHere, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnl_reason_of_cancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Payments", pnlClosing);

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
        sumTxtPaidAmt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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

        javax.swing.GroupLayout pnlAccLayout = new javax.swing.GroupLayout(pnlAcc);
        pnlAcc.setLayout(pnlAccLayout);
        pnlAccLayout.setHorizontalGroup(
            pnlAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlAccLayout.setVerticalGroup(
            pnlAccLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAccLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("", pnlAcc);

        jLabel34.setText("Cashier :");

        txtCashier.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmdCreateDeliver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/add.png"))); // NOI18N
        cmdCreateDeliver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreateDeliverActionPerformed(evt);
            }
        });

        cmdEditDeliver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/edit.png"))); // NOI18N
        cmdEditDeliver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdEditDeliverActionPerformed(evt);
            }
        });

        cmdRemoveDeliver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/remove.png"))); // NOI18N
        cmdRemoveDeliver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveDeliverActionPerformed(evt);
            }
        });

        cmdRemoveDeliver1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/print.png"))); // NOI18N
        cmdRemoveDeliver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdRemoveDeliver1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCashier, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cmdCreateDeliver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdEditDeliver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRemoveDeliver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdRemoveDeliver1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cmdEditDeliver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cmdRemoveDeliver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel34))
                        .addComponent(txtCashier, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                        .addComponent(cmdCreateDeliver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(cmdRemoveDeliver1, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addContainerGap())
        );

        tbl_delivery.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_delivery.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tbl_deliveryMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tbl_delivery);

        javax.swing.GroupLayout pnlDeliveriesLayout = new javax.swing.GroupLayout(pnlDeliveries);
        pnlDeliveries.setLayout(pnlDeliveriesLayout);
        pnlDeliveriesLayout.setHorizontalGroup(
            pnlDeliveriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, 0, 253, Short.MAX_VALUE)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
        );
        pnlDeliveriesLayout.setVerticalGroup(
            pnlDeliveriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeliveriesLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(227, 227, 227))
        );

        jTabbedPane1.addTab("Delivereis", pnlDeliveries);

        txtSection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSectionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSection, 0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtSection)
        );

        javax.swing.GroupLayout canvasLayout = new javax.swing.GroupLayout(canvas);
        canvas.setLayout(canvasLayout);
        canvasLayout.setHorizontalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        canvasLayout.setVerticalGroup(
            canvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(canvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(263, Short.MAX_VALUE))
        );

        jScrollPane5.setViewportView(jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 245, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlTablesLayout = new javax.swing.GroupLayout(pnlTables);
        pnlTables.setLayout(pnlTablesLayout);
        pnlTablesLayout.setHorizontalGroup(
            pnlTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTablesLayout.setVerticalGroup(
            pnlTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablesLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Tables", pnlTables);

        pnlFooter_search.setLayout(new java.awt.GridLayout(2, 5, 3, 3));

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel50.setText("Ref #");
        pnlFooter_search.add(jLabel50);

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
        pnlFooter_search.add(txtItemCode);

        jLabel102.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel102.setText("Qt #");
        pnlFooter_search.add(jLabel102);

        txtItemQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItemQty.setText("1");
        pnlFooter_search.add(txtItemQty);

        cmdItemAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/edit.png"))); // NOI18N
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
        pnlFooter_search.add(cmdItemAdd);

        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel104.setText("Sub-Item");
        pnlFooter_search.add(jLabel104);

        txtCategory.setEditable(false);
        pnlFooter_search.add(txtCategory);

        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel103.setText("Price#");
        pnlFooter_search.add(jLabel103);

        txtItemPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItemPrice.setText("0.000");
        pnlFooter_search.add(txtItemPrice);

        cmdItemAdd2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/edit.png"))); // NOI18N
        cmdItemAdd2.setToolTipText("Add Update Items");
        cmdItemAdd2.setFocusable(false);
        cmdItemAdd2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        cmdItemAdd2.setMaximumSize(new java.awt.Dimension(99, 99));
        cmdItemAdd2.setMinimumSize(new java.awt.Dimension(0, 0));
        cmdItemAdd2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdItemAdd2ActionPerformed(evt);
            }
        });
        pnlFooter_search.add(cmdItemAdd2);

        javax.swing.GroupLayout pnlSLayout = new javax.swing.GroupLayout(pnlS);
        pnlS.setLayout(pnlSLayout);
        pnlSLayout.setHorizontalGroup(
            pnlSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlFooter_search, javax.swing.GroupLayout.PREFERRED_SIZE, 245, Short.MAX_VALUE)
        );
        pnlSLayout.setVerticalGroup(
            pnlSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSLayout.createSequentialGroup()
                .addContainerGap(183, Short.MAX_VALUE)
                .addComponent(pnlFooter_search, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout searchPanelLayout = new javax.swing.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlS, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(searchPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("searchPanel", jPanel12);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel10);

        jPanel14.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 2));

        cmdSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/save.png"))); // NOI18N
        cmdSave.setFocusable(false);
        cmdSave.setHorizontalAlignment(javax.swing.SwingConstants.LEADING);
        cmdSave.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cmdSave.setIconTextGap(2);
        cmdSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSaveActionPerformed(evt);
            }
        });
        jPanel14.add(cmdSave);
        cmdSave.getAccessibleContext().setAccessibleName("save");

        jLabel33.setText("      ");
        jPanel14.add(jLabel33);

        cmdClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/ok.png"))); // NOI18N
        cmdClose.setFocusable(false);
        cmdClose.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cmdClose.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        cmdClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCloseActionPerformed(evt);
            }
        });
        jPanel14.add(cmdClose);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/details.png"))); // NOI18N
        jButton7.setFocusable(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton7);

        jButton4.setText("-");
        jButton4.setFocusable(false);
        jButton4.setPreferredSize(new java.awt.Dimension(50, 25));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton4);

        jButton3.setText("+");
        jButton3.setFocusable(false);
        jButton3.setPreferredSize(new java.awt.Dimension(50, 25));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton3);

        jLabel29.setText("           ");
        jPanel14.add(jLabel29);

        jButton6.setText("X");
        jButton6.setFocusable(false);
        jButton6.setPreferredSize(new java.awt.Dimension(50, 25));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton6);

        jScrollPane1.setBackground(java.awt.Color.green);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setBackground(new java.awt.Color(204, 255, 204));
        jTable1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setMaximumSize(new java.awt.Dimension(32767, 32767));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setLayout(new java.awt.GridLayout(0, 6, 20, 3));
        jPanel3.add(jLabel26);
        jPanel3.add(jLabel28);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Gross Amt");
        jPanel3.add(jLabel7);

        sumTxtGrossAmount.setBackground(java.awt.Color.yellow);
        sumTxtGrossAmount.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sumTxtGrossAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtGrossAmount.setText("0.0");
        sumTxtGrossAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtGrossAmount.setOpaque(true);
        jPanel3.add(sumTxtGrossAmount);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Net Amt");
        jPanel3.add(jLabel10);

        sumTxtNetAmount.setBackground(java.awt.Color.yellow);
        sumTxtNetAmount.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sumTxtNetAmount.setForeground(java.awt.Color.red);
        sumTxtNetAmount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtNetAmount.setText("0.0");
        sumTxtNetAmount.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtNetAmount.setOpaque(true);
        jPanel3.add(sumTxtNetAmount);

        jLabel18.setText("Additional Charges");
        jPanel3.add(jLabel18);

        sumTxtAddAmt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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

        sumTxtDiscAmt.setEditable(false);
        sumTxtDiscAmt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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

        sumTxtCashAmt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        sumTxtTotQty.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        sumTxtTotQty.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtTotQty.setText("0");
        sumTxtTotQty.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtTotQty.setOpaque(true);
        jPanel3.add(sumTxtTotQty);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Disc %");
        jPanel3.add(jLabel11);

        sumTxtDiscP.setEditable(false);
        sumTxtDiscP.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
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
        sumTxtChangeAmt.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sumTxtChangeAmt.setForeground(new java.awt.Color(0, 51, 204));
        sumTxtChangeAmt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        sumTxtChangeAmt.setText("0.0");
        sumTxtChangeAmt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        sumTxtChangeAmt.setOpaque(true);
        jPanel3.add(sumTxtChangeAmt);

        lblSelection.setFont(new java.awt.Font("Dialog", 1, 14));
        lblSelection.setForeground(new java.awt.Color(-65536,true));
        lblSelection.setText("     ");

        javax.swing.GroupLayout panelCenterLayout = new javax.swing.GroupLayout(panelCenter);
        panelCenter.setLayout(panelCenterLayout);
        panelCenterLayout.setHorizontalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSelection, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 279, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 356, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        panelCenterLayout.setVerticalGroup(
            panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCenterLayout.createSequentialGroup()
                .addGroup(panelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSelection)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jSplitPane1.setRightComponent(panelCenter);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 612, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void sumTxtDiscAmtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumTxtDiscAmtActionPerformed
        sumDiscountAmt = Double.valueOf(sumTxtDiscAmt.getText());
        update_sums();
    }//GEN-LAST:event_sumTxtDiscAmtActionPerformed

    private void sumTxtDiscAmtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sumTxtDiscAmtFocusLost
        sumTxtDiscAmt.setEditable(false);
        sumTxtDiscP.setEditable(false);
        onfocusLostSums((JTextField) evt.getComponent());
    }//GEN-LAST:event_sumTxtDiscAmtFocusLost
    private boolean inCheckDiscount = false;
    private frmDiscount fd = null;
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
        if (dataInvoiceCode == 30) {
            if (inCheckDiscount) {
                inCheckDiscount = false;
                return;
            }
            inCheckDiscount = true;
            if (!parentJF.usrlog.checkSecurity("SEC_DISCOUNT", "Discount on Sale")) {
                sumTxtDiscAmt.setEditable(false);
                sumTxtDiscP.setEditable(false);
            } else {
                sumTxtDiscAmt.setEditable(true);
                sumTxtDiscP.setEditable(true);
                if (fd == null) {
                    fd = new frmDiscount(getParentJF(), true);
                }
                fd.show(this);
                sumTxtDiscAmt.setEditable(false);
                sumTxtDiscP.setEditable(false);

            }
        }
        ((JTextField) evt.getComponent()).selectAll();
    }//GEN-LAST:event_sumTxtDiscAmtFocusGained

    public void print_data_delivery(double kf, String rpt) {
        String reportSource = rpt + ".jrxml";
        //String reportCompiled = rpt + ".jasper";
        String reportCompiled = rpt + ".jasper";

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("KEYFLD", BigDecimal.valueOf(kf));

        double tmp = 0;
        try {
            PreparedStatement pss = dbConnection.prepareStatement("select pos_onpur1.* ,company.name company_name,company.specification "
                    + " from pos_onpur1,company where company.crnt='X' and pos_onpur1.keyfld=" + kf,
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = pss.executeQuery();
            if (rss.first()) {
                params.put("INV_AMT", BigDecimal.valueOf(rss.getDouble("INV_AMT")));
                params.put("INV_NO", BigDecimal.valueOf(rss.getDouble("B_NO")));
                params.put("INV_DATE_TIME", rss.getTimestamp("B_DATE"));
                params.put("INV_LOCATION", dataLocationName);
                params.put("INV_USER", parentJF.getLp().getLogon_user());
                params.put("INV_NETAMT", (BigDecimal.valueOf(rss.getDouble("INV_AMT") - rss.getDouble("DISC_AMT"))));
                //params.put("INV_PAIDAMT", BigDecimal.valueOf(rss.getDouble("PAIDAMT2")));
                //params.put("INV_DISCAMT", BigDecimal.valueOf(rss.getDouble("DISC_AMT")));
                //tmp = rss.getDouble("PAIDAMT2") - (rss.getDouble("INV_AMT") - rss.getDouble("DISC_AMT"));
                //params.put("INV_CHANGEAMT", BigDecimal.valueOf(tmp));
                //params.put("ADD_CHAGE", BigDecimal.valueOf(rss.getDouble("ADD_CHARGE")));
                params.put("COMPANY_NAME", rss.getString("company_name"));
                params.put("COMPANY_SPEC", rss.getString("specification"));
                String drivername = "";

                if (rss.getString("SLSMN") != null && rss.getString("SLSMN").length() > 0) {
                    drivername = utils.getSqlValue("select name from salesp where no=" + rss.getString("SLSMN"), dbConnection);
                }
                params.put("DLV_DRIVERNAME", drivername);
                params.put("DLV_PHONE", rss.getString("CUST_REFERENCE"));
                params.put("DLV_CUSTOMERNAME", rss.getString("CUST_NAME"));
                params.put("DLV_OTHERTEL", rss.getString("addr_tel"));
                params.put("DLV_AREA", rss.getString("ADDR_AREA"));
                params.put("DLV_BLOCK", rss.getString("ADDR_BLOCK"));
                params.put("DLV_STREET", rss.getString("ADDR_STREET"));
                params.put("DLV_JEDDA", rss.getString("ADDR_JEDDA"));
                params.put("DLV_BUILDING", rss.getString("ADDR_BLDG"));
                params.put("DLV_FLOORNO", rss.getString("ADDR_FLOOR"));
                params.put("DLV_FLATNO", rss.getString("WORK_ADDRESS"));
                params.put("SPEC_COMMENTS", rss.getString("SPEC_COMMENTS"));
                params.put("TABLE_CODE", rss.getString("TABLE_CODE"));
            }
            pss.close();
            //JasperReport jasperReport =
//                    JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint
                    = JasperFillManager.fillReport(
                            reportCompiled, params, dbConnection);//new JREmptyDataSource());

            //JasperExportManager.exportReportToHtmlFile(
            //       jasperPrint, reportDest);
            //JasperViewer.viewReport(jasperPrint, false);
            JasperPrintManager.printReport(jasperPrint, false);
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }
    }

    public void print_data(double kf) {
        String reportSource = "reports/possale.jrxml";
        String reportCompiled = "reports/possale.jasper";
        String reportDest = "reports/output/possale.html";

        if (dataInvoiceCode == 20) {
            reportSource = "reports/possale_ret.jrxml";
            reportCompiled = "reports/possale_ret.jasper";
            reportDest = "reports/output/possale_ret.html";
        }

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("KEYFLD", BigDecimal.valueOf(kf));

        double tmp = 0;
        try {
            PreparedStatement pss = dbConnection.prepareStatement("select pospur1.* ,company.name company_name,company.specification from pospur1,company where company.crnt='X' and pospur1.keyfld=" + kf, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rss = pss.executeQuery();
            if (rss.first()) {
                PreparedStatement psx = dbConnection.prepareStatement("select pospayments.*,INITCAP(invoicetype.descr) DESCR from "
                        + "POSPUR1,pospayments,invoicetype where INVOICETYPE.NO=POSPAYMENTS.TYPE_NO AND "
                        + " INVOICETYPE.LOCATION_CODE=POSPUR1.LOCATION_CODE AND KEYFLD=VOU_KEYFLD AND vou_keyfld=" + kf + "",
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rsx = psx.executeQuery();
                rsx.beforeFirst();
                String t = parentJF.getMapVars().get("PAID_TEXT");
                int tt = 1;
                while (rsx.next()) {
                    params.put("INV_PAID_DESCR" + tt, t + " " + rsx.getString("DESCR"));
                    params.put("INV_PAIDAMT_" + tt, rsx.getBigDecimal("AMOUNT"));
                    tt++;
                }
                psx.close();
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

            //JasperReport jasperReport =
//                    JasperCompileManager.compileReport(reportSource);
            JasperPrint jasperPrint
                    = JasperFillManager.fillReport(
                            reportCompiled, params, dbConnection);//new JREmptyDataSource());

            //JasperExportManager.exportReportToHtmlFile(
            //       jasperPrint, reportDest);
            //JasperViewer.viewReport(jasperPrint, false);
            if (parentJF.getMapVars().containsKey("sale_print_copy")) {
                int n = Integer.valueOf(parentJF.getMapVars().get("sale_print_copy"));
                for (int i = 0; i < n; i++) {
                    JasperPrintManager.printReport(jasperPrint, false);
                }
            } else {
                JasperPrintManager.printReport(jasperPrint, false);
            }
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }
    }

    private void adrAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrAreaActionPerformed
}//GEN-LAST:event_adrAreaActionPerformed
    private double last_takeAway_disc_amt = 0;

    public void on_mode_change(String ac) {
        jTabbedPane1.removeAll();
        cmdDelivery.setContentAreaFilled(false);
        cmdTables.setContentAreaFilled(false);
        cmdTakeAway.setContentAreaFilled(false);
        cmdSave.setEnabled(true);
        ShowMessageFrame.closeAllWindows("");
        if (ac.equals("cmdTakeAway")) {
            onShowItems();
            jTable1.setModel(dataNull);
            jTable1.setModel(itemdetails);
            setColsProperties(itemdetails);
            jTable1.updateUI();
            cmdTakeAway.setContentAreaFilled(true);
            cmdSave.setEnabled(false);
            sumDiscountAmt = last_takeAway_disc_amt;
            sumAdvanceAmt = 0;
            sumTxtDiscAmt.setText(String.valueOf(last_takeAway_disc_amt));
            sumTxtDiscAmt.getInputVerifier().verify(sumTxtDiscAmt);
            itemdetails.getRowlistner().onCalc(0);

        }
        if (ac.equals("cmdDelivery")) {
            onShowDeliveries();
            cmdDelivery.setContentAreaFilled(true);
            itemdetails_dlv.getRowlistner().onCalc(0);
        }
        if (ac.equals("cmdTables")) {
            onShowTables();
            cmdTables.setContentAreaFilled(true);
            sumAdvanceAmt = 0;
            itemdetails_tbl.getRowlistner().onCalc(0);
        }
    }

    public void onShowItems() {
        if (cmdTables.isSelected() && ((RTablesCanvas) canvas).st == null) {
            JOptionPane.showMessageDialog(parentJF, "SELECT FIRST TABLE !");
            return;
        }
        ShowMessageFrame.closeAllWindows("");
        jTabbedPane1.removeAll();
        jTabbedPane1.addTab("", pnlItems);
        currentItem = "";
        start_showItems();
        pnlItems.updateUI();
        jTabbedPane1.updateUI();
        if (!cmdTables.isSelected() && !cmdDelivery.isSelected()) {
            lblSelection.setText("   ");
        }
        if (utils.noValue(parentJF.getMapVars().get("default_screen_search"), "false").toString().equals("true")) {
            Timer tmx = new Timer();
            tm.schedule(new TimerTask() {

                @Override
                public void run() {
                    chkShowSearchPanel.setSelected(true);
                    toggle_search_panel();

                }
            }, 500);

        }

    }

    public void onShowDeliveries() {
        jTabbedPane1.removeAll();
        jTabbedPane1.addTab("", pnlDeliveries);
        pnlDeliveries.updateUI();
        jTabbedPane1.updateUI();
        jTable1.setModel(dataNull);
        jTable1.setModel(itemdetails_dlv);
        setColsProperties(itemdetails_dlv);

        jTable1.updateUI();
        on_deliver_selection(false);
        if (tbl_delivery.getSelectedRow() > -1) {
            lblSelection.setText(
                    delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "CUST_NAME").toString());
        } else {
            lblSelection.setText("  ");
        }
        cmdEditDeliver.setEnabled(false);
        cmdRemoveDeliver.setEnabled(false);
        if (tbl_delivery.getSelectedRow() > -1) {
            cmdEditDeliver.setEnabled(true);
            cmdRemoveDeliver.setEnabled(true);
        }

    }

    public void onShowTables() {
        jTabbedPane1.removeAll();
        jTabbedPane1.addTab("", pnlTables);
        pnlTables.updateUI();
        jTabbedPane1.updateUI();
        jTable1.setModel(dataNull);
        jTable1.setModel(itemdetails_tbl);
        setColsProperties(itemdetails_tbl);
        jTable1.updateUI();
        lblSelection.setText("   ");
        if (((RTablesCanvas) canvas).st != null) {
            lblSelection.setText(((RTablesCanvas) canvas).st.Code);
        }
        refresh_active_tables();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void adrOtherTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrOtherTelActionPerformed
}//GEN-LAST:event_adrOtherTelActionPerformed

    private void adrFloorNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrFloorNoActionPerformed
}//GEN-LAST:event_adrFloorNoActionPerformed

    private void adrPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adrPhoneFocusLost
        if (adrPhone.getText() == null || adrPhone.getText().length() == 0) {
            return;
        }
        PreparedStatement ps2 = null;
        try {
            ps2 = dbConnection.prepareStatement("select *from POSCUSTOMER where CODE=?", ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
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
            payRows.setFieldValue(payTable.getSelectedRow(), "AMOUNT", Double.valueOf(sumNetAmt - sumAdvanceAmt));
        }

        sumPaidAmtThis = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumPaidAmt = sumPaidAmtThis + sumAdvanceAmt;
        sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmtThis));
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
            datasalesp
                    = ((dataCell) listDriver.getModel().getSelectedItem()).getValue().toString();
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
        //changeFromQuery=true;
        localTableModel t = (localTableModel) jTable1.getModel();
        txtItemCode.setText((String) t.getFieldValue(jTable1.getSelectedRow(), "REFER"));
        String ctg = (String) t.getFieldValue(jTable1.getSelectedRow(), "SIZE_OF_DESCR");
        txtItemQty.setText("1");
        try {
            addUpdate(txtItemCode.getText(), ctg);
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        if (!parentJF.usrlog.checkSecurity("SEC_SALE_DECREASE", "Decreasing on sale !")) {
            return;
        }

        if (jTable1.getSelectedRow() < 0) {
            return;
        }
        //changeFromQuery=true;
        localTableModel t = (localTableModel) jTable1.getModel();
        txtItemCode.setText((String) t.getFieldValue(jTable1.getSelectedRow(), "REFER"));
        String ctg = (String) t.getFieldValue(jTable1.getSelectedRow(), "SIZE_OF_DESCR");
        txtItemQty.setText("-1");
        //cmdItemAdd.doClick();
        try {
            addUpdate(txtItemCode.getText(), ctg);
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                sumTxtDiscP.setText(ld.getSlov().getLctb().getFieldValue(ld.getSelectedNo(), "DISC_PERCENT").toString());
                sumTxtDiscP.getInputVerifier().verify(sumTxtDiscP);
            }
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if (!parentJF.usrlog.checkSecurity("SEC_SALE_DECREASE", "Decrease on Sale")) {
            return;
        }
        remove_all();
    }//GEN-LAST:event_jButton6ActionPerformed
    public void remove_all() {
        try {
            if (!is_valid_entry()) {
                return;
            }
            localTableModel t = (localTableModel) jTable1.getModel();
            for (int i = t.getRowCount() - 1; i > -1; i--) {
                t.deleteRow(i);
                changeFromQuery = true;
            }
            jTable1.updateUI();
            update_sums();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        onShowItems();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void txtSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSectionActionPerformed
        onChangeTableSection();
    }//GEN-LAST:event_txtSectionActionPerformed

    private void cmdTakeAwayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTakeAwayActionPerformed
        on_mode_change(evt.getActionCommand());
    }//GEN-LAST:event_cmdTakeAwayActionPerformed

    private void cmdDeliveryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDeliveryActionPerformed
        on_mode_change(evt.getActionCommand());
    }//GEN-LAST:event_cmdDeliveryActionPerformed

    private void cmdTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdTablesActionPerformed
        on_mode_change(evt.getActionCommand());
    }//GEN-LAST:event_cmdTablesActionPerformed

    private void cmdCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCloseActionPerformed
        ShowMessageFrame.closeAllWindows("");
        if (sumAdvanceAmt > sumNetAmt) {
            JOptionPane.showMessageDialog(this, "Advance amount # " + decimalformat.format(sumAdvanceAmt) + " is lesser than invoiced amount");
            return;
        }
        try {

            if (jTable1.getModel().getRowCount() <= 0) {
                throw new Exception("No data found to close !");
            }
            if (cmdTables.isSelected()) {
                save_data_tbl(false);
            }
            if (cmdDelivery.isSelected() && changeFromQuery) {
                save_data_dlv(false);
            }
            if (dataInvoiceCode == 30) {
                CloseVouDlg cd = new CloseVouDlg(parentJF, true);
                cd.setVisible(true);
            } else {
                closeReturnDlg cd = new closeReturnDlg(parentJF, true);
                cd.setVisible(true);
            }
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            ShowMessageFrame sw = new ShowMessageFrame(ex, 14, 3000);
        }

    }//GEN-LAST:event_cmdCloseActionPerformed

    private void cmdSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSaveActionPerformed
        try {
            double kf = save_data();
            dbConnection.commit();
            if (cmdTables.isSelected() && ShowMessageFrame.closeAllWindows("") >= -1 && JOptionPane.showConfirmDialog(this,
                    "Do you want to print ?", "CHANNEL POS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                print_data_delivery(kf, "reports/postable");
            }

        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            try {
                dbConnection.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }//GEN-LAST:event_cmdSaveActionPerformed

    private void cmdCreateDeliverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreateDeliverActionPerformed
        parentJF.kf.setVisible(false);
        dataDlvDate.setTime(dataInv_date.getTime());
        dataDlvAdvanceDate.setTime(dataInv_date.getTime());
        frmDelivery fd = new frmDelivery(-1, parentJF, true);
        fd.setVisible(true);
}//GEN-LAST:event_cmdCreateDeliverActionPerformed

    private void cmdEditDeliverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdEditDeliverActionPerformed

        if (!parentJF.usrlog.checkSecurity("SEC_EDIT_DELIVERY", "Edit delivery data !")) {
            return;
        }

        if (tbl_delivery.getSelectedRow() > -1) {
            parentJF.kf.setVisible(false);
            on_deliver_selection(false);
            dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
            frmDelivery fd = new frmDelivery(dataDlKeyfld, parentJF, true);
            fd.setVisible(true);
        }
    }//GEN-LAST:event_cmdEditDeliverActionPerformed

    private void cmdRemoveDeliverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveDeliverActionPerformed

        remove_delivery();
    }//GEN-LAST:event_cmdRemoveDeliverActionPerformed

    private void cmdRemoveDeliver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdRemoveDeliver1ActionPerformed
        dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
        print_data_delivery(dataDlKeyfld, "reports/posdelivery");
    }//GEN-LAST:event_cmdRemoveDeliver1ActionPerformed

    private void tbl_deliveryMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_deliveryMouseReleased
        on_deliver_selection(true);
    }//GEN-LAST:event_tbl_deliveryMouseReleased

    private void adrEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adrEmailActionPerformed

    private void adrRPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrRPhoneActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrRPhoneActionPerformed

    private void adrRPhoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_adrRPhoneFocusLost
        // TODO add your handling code here:
}//GEN-LAST:event_adrRPhoneFocusLost

    private void adrRFloorNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrRFloorNoActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrRFloorNoActionPerformed

    private void adrROtherTelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrROtherTelActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_adrROtherTelActionPerformed

    private void adrRAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adrRAreaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_adrRAreaActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed

        onShowItems();
        if (chkShowSearchPanel.isSelected()) {
            toggle_search_panel();
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void chkShowSearchPanelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowSearchPanelActionPerformed
        toggle_search_panel();
    }//GEN-LAST:event_chkShowSearchPanelActionPerformed

    private void cmdItemAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAddActionPerformed
        try {
            addUpdate();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
}//GEN-LAST:event_cmdItemAddActionPerformed

    private void txtItemCodeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemCodeFocusGained
        ((JTextComponent) evt.getComponent()).selectAll();
}//GEN-LAST:event_txtItemCodeFocusGained

    private void txtItemCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtItemCodeActionPerformed
        cmdItemAdd.doClick();
}//GEN-LAST:event_txtItemCodeActionPerformed

    private void cmdItemAdd2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdItemAdd2ActionPerformed
        addUpdatePrice();
}//GEN-LAST:event_cmdItemAdd2ActionPerformed
    public void toggle_search_panel() {
        Rectangle r = new Rectangle(splitGroupItem.getBounds());
        if (chkShowSearchPanel.isSelected()) {
            pnlItems.remove(splitGroupItem);
            pnlItems.add(searchPanel);
            searchPanel.setBounds(r);
            searchPanel.setLayout(null);
            pnlS.setBounds(0, 0, searchPanel.getWidth(), searchPanel.getHeight());
            String sql = "select i.reference,i.descr,i.price1,pi.descr parent,i.barcode "
                    + " from items i,items pi where pi.reference(+)=i.reference and i.flag=1 "
                    + "  order by i.descr2 ";
            if (itemsRows == null) {
                if (!currentItem.isEmpty()) {
                    sql = "select i.reference,i.descr,i.price1,pi.descr parent,i.barcode "
                            + " from items i,items pi where pi.reference(+)=i.reference and i.flag=1 "
                            + " and i.descr2 like (select x.descr2||'%' from items x where x.reference='" + currentItem + "')"
                            + "  order by i.descr2 ";
                }
            } else {
                sql = "";
                fill_item_list(currentItem, ((frmLovSearchPanel) pnlS).getSlov());
            }
            ((frmLovSearchPanel) pnlS).childCountField = "CHILDCOUNT";
            ((frmLovSearchPanel) pnlS).levelNoField = "LEVELNO";
            ((frmLovSearchPanel) pnlS).levelno1_color = Color.lightGray;
            ((frmLovSearchPanel) pnlS).footerPanel = pnlFooter_search;
            ((frmLovSearchPanel) pnlS).show(sql, false, new lovFrame() {

                public void setSelectedNo(int no, String rfr) {
                    if (no > -1) {
                        try {
                            frmLovSearchPanel fs = (frmLovSearchPanel) pnlS;
                            String rf = fs.getSlov().getLctb().getFieldValue(no, "ITEM_CODE") + "";
                            txtItemCode.setText(rf);
                            addUpdate();
                        } catch (Exception ex) {
                            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                public DBClass getDbc() {
                    return dbc;
                }
            }, "REFERENCE", "DESCR", "PRICE1");
        } else {
            pnlItems.remove(searchPanel);
            pnlItems.add(splitGroupItem);
        }
        pnlItems.updateUI();
    }

    public void remove_delivery() {
        if (!parentJF.usrlog.checkSecurity("SEC_REMOVE_DELIVERY", "Remove delivery ! ")) {
            return;
        }
        if (tbl_delivery.getSelectedRow() <= -1) {
            return;
        }
        try {
            if (delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_DATE") != null
                    && !delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_DATE").toString().isEmpty()) {
                Date dt = new Date(((Timestamp) delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_DATE")).getTime());
                double ad = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_PAID").toString());
                double kfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
                if (ad > 0 && !frmDelivery.is_delivery_payment_posted(parentJF, dt, kfld)) {
                    throw new Exception("Date is posted , can not delete as advance payment is closed ! ");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
            return;
        }

        if (tbl_delivery.getSelectedRow() > -1
                && JOptionPane.showConfirmDialog(this, "Delete selected deliverey ?", "CHANNEL POS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
            try {
                QueryExe qe = new QueryExe("begin "
                        + " delete from pos_onpur1 where keyfld=:KEYFLD ; "
                        + " delete from pos_onpur2 where keyfld=:KEYFLD ;"
                        + " delete from pospur1 where INVOICE_CODE=10 AND ORDERNO= :KEYFLD ; "
                        + " end; ", dbConnection);
                qe.setParaValue("KEYFLD", dataDlKeyfld);
                qe.execute();
                dbConnection.commit();
                onChangeCashier(false);
            } catch (SQLException ex) {
                ex.printStackTrace();
                try {
                    dbConnection.rollback();
                } catch (SQLException ex1) {
                }
            }

        }
    }

    public void check_security() throws SQLException {
        if (parentJF.getMapVars().get("IS_ADMIN").equals("Y")) {
            return;
        }
        if (parentJF.getMapVars().get("DEFAULT_CASHIER").equals("-1")) {
            return;
        }

        if ((dataCell) txtCashier.getSelectedItem() == null
                || ((dataCell) txtCashier.getSelectedItem()).getValue().equals(parentJF.getMapVars().get("DEFAULT_CASHIER"))) {
            return;
        }
        if (!parentJF.usrlog.checkSecurity("CAN_CHANGE_CASHIER", "Changing Cashier !")) {
            txtCashier.setSelectedItem(utils.findByValue(txtCashier,
                    parentJF.getMapVars().get("DEFAULT_CASHIER")));
        }
    }

    public void onChangeCashier(boolean check_sec) {
        try {
            if (check_sec) {
                check_security();
            }

            if ((dataCell) txtCashier.getSelectedItem() == null) {
                return;
            }
            String d = ((dataCell) txtCashier.getSelectedItem()).getValue() + "";
            cmdCreateDeliver.setEnabled(true);
            if (d.isEmpty()) {
                cmdCreateDeliver.setEnabled(false);
            }
            delivery_data.executeQuery("select cust_name ,delivery_datetime DELIVERY_TIME,DISC_AMT, (INV_AMT-DISC_AMT) AMOUNT,KEYFLD,B_NO,"
                    + " AREA, TEL, HOME_ADDRESS, WORK_ADDRESS, EMAIL, BDETAIL, BCUST, TERMOFPAY, REMARK, "
                    + " REFERENCE, SPEC_COMMENTS, COMPLAINS, LAST_DRIVER, SLSMN,B_DATE, "
                    + " ADDR_AREA, ADDR_BLOCK, ADDR_JEDDA, ADDR_STREET, ADDR_BLDG, ADDR_TEL,ADDR_FLOOR ,PICK_UP,"
                    + " ADDR_R_AREA, ADDR_R_BLOCK, ADDR_R_JEDDA, ADDR_R_STREET, ADDR_R_BLDG, ADDR_R_TEL, ADDR_R_FLOOR, RECIPIENT_ADDRESS, "
                    + " ADVANCE_PAID , ADVANCE_DATE "
                    + " FROM POS_ONPUR1 "
                    + " WHERE  B_KIND='DELIVERY' AND FLAG!=2 AND location_code='" + dataLocationCode + "'"
                    + " and (SLSMN='" + d + "' or '" + d + "' is null)"
                    + " ORDER BY delivery_datetime", new String[]{"CUST_NAME", "DELIVERY_TIME", "AMOUNT", "ADVANCE_PAID"});
            tbl_delivery.setModel(delivery_data);
            tbl_delivery.getColumnModel().setColumnSelectionAllowed(false);
            tbl_delivery.getTableHeader().setReorderingAllowed(false);
            tbl_delivery.setRowHeight(30);
            delivery_data.setEditAllowed(false);
            for (int colx = 0; colx
                    < tbl_delivery.getModel().getColumnCount(); colx++) {
                tbl_delivery.getColumnModel().getColumn(colx).setCellRenderer(new ColorRenderer(true));
            }
            delivery_data.getColByName("CUST_NAME").setDisplaySize(100);
            delivery_data.getColByName("DELIVERY_TIME").setDisplaySize(60);
            delivery_data.getColByName("AMOUNT").setDisplaySize(60);
            delivery_data.getColByName("ADVANCE_PAID").setDisplaySize(60);

            utils.applyColumn(parentJF, delivery_data.getColByName("CUST_NAME"), tbl_delivery, 0);
            utils.applyColumn(parentJF, delivery_data.getColByName("DELIVERY_TIME"), tbl_delivery, 1);
            utils.applyColumn(parentJF, delivery_data.getColByName("AMOUNT"), tbl_delivery, 2);
            utils.applyColumn(parentJF, delivery_data.getColByName("ADVANCE_PAID"), tbl_delivery, 3);

            delivery_data.getColByName("AMOUNT").setNumberFormat(decimalformat.toPattern());
            delivery_data.getColByName("ADVANCE_PAID").setNumberFormat(decimalformat.toPattern());

            on_deliver_selection(false);
            tbl_delivery.revalidate();
            load_data();
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onChangeTableSection() {
        try {
            if (txtSection.getModel().getSelectedItem() == null) {
                return;
            }

            varSectionCode = ((dataCell) txtSection.getModel().getSelectedItem()).getValue().toString();
            PreparedStatement ps = dbConnection.prepareStatement("select *from pos_sections where code='" + varSectionCode + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();
            varSectionName = "";
            varFlag = 1;
            varDay1 = "Y";
            varDay2 = "Y";
            varDay3 = "Y";
            varDay4 = "Y";
            varDay5 = "Y";
            varDay6 = "Y";
            varDay7 = "Y";
            varHeight = 280;
            varWidth = 325;

            if (rs == null || !rs.first()) {
                fill_tables();
                canvas.repaint();
                return;
            }
            rs.first();
            varSectionName = rs.getString("section_name");
            varFlag = rs.getInt("flag");
            varDay1 = rs.getString("DAY_1");
            varDay2 = rs.getString("DAY_1");
            varDay3 = rs.getString("DAY_1");
            varDay4 = rs.getString("DAY_1");
            varDay5 = rs.getString("DAY_1");
            varDay6 = rs.getString("DAY_1");
            varDay7 = rs.getString("DAY_1");
            varHeight = rs.getInt("height");
            varWidth = rs.getInt("width");
            ps.close();
            fill_tables();
            canvas.setPreferredSize(new Dimension(varWidth, varHeight));
            canvas.requestFocus();
            canvas.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void fill_tables() {
        RTablesCanvas rtc = (RTablesCanvas) canvas;
        rtc.listTables.clear();
        rtc.st = null;
        rtc.setSize(varWidth, varHeight);
        rtc.setBackground(Color.BLACK);
        rtc.setOpaque(true);
        rtc.updateUI();
        rtc.repaint();
        rtc.setRTrigger(this);

        try {
            PreparedStatement ps = dbConnection.prepareStatement("select * from "
                    + " POS_TABLES where section_code='" + varSectionCode + "'"
                    + " order by keyfld", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ResultSet rs = ps.executeQuery();
            rs.beforeFirst();
            while (rs.next()) {
                RTables rt = new RTables(rtc);
                rt.keyfld = rs.getDouble("keyfld");
                rt.Code = rs.getString("table_code");
                rt.descr = rs.getString("descr");
                rt.TypeOfGraphics = rs.getString("TYPE_OF_GRAPHICS");
                rt.rect.width = rs.getInt("SIZE_RECT_LENGTH");
                rt.rect.height = rs.getInt("SIZE_RECT_HEIGHT");
                rt.rect.x = rs.getInt("POS_X");
                rt.rect.y = rs.getInt("POS_Y");
                rt.width = rs.getInt("SIZE_RECT_LENGTH");
                rt.height = rs.getInt("SIZE_RECT_HEIGHT");
                rt.pos_x = rs.getInt("POS_X");
                rt.pos_y = rs.getInt("POS_Y");
                rt.canSelect = rs.getString("CAN_SELECT");
                rt.strBackColor = rs.getString("back_color");
                rt.strTextColor = rs.getString("text_color");
                rt.strBorderColor = rs.getString("border_color");
                rt.backColor = ColorUtility.convertColor(rt.strBackColor, Color.blue);
                rt.textColor = ColorUtility.convertColor(rt.strTextColor, Color.white);
                rt.borderColor = ColorUtility.convertColor(rt.strBorderColor, Color.GRAY);
                rt.last_rect.setBounds(rt.rect);
                rtc.listTables.add(rt);
            }
            ps.close();
            rtc.isFirstTime = true;
            rtc.repaint();

        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
                if (cmdTakeAway.isSelected()) {
                    last_takeAway_disc_amt = sumDiscountAmt;
                }

                //sumDiscountAmt = (decimalformat.parse(txt.getText())).doubleValue();
            } catch (ParseException ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                txt.setText("");
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
        try {
            ((BlinkLabel) lblSelection).setBlinking(true);
            ((BlinkLabel) lbl_itemSel).setBlinking(true);
            this.parentJF = parentJF;
            RTablesCanvas rtc = (RTablesCanvas) canvas;
            rtc.setSystem_mode(rtc.MODE_USER);
            if (selItems == null) {
                no_of_item_cols = Integer.valueOf(parentJF.getMapVars().get("no_of_item_column"));
                selItems = new localTableModel(no_of_item_cols);
                setupItemCols();
                itemTable.addMouseListener(new JTableButtonMouseListener(itemTable));
                itemTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            }
            if (selGrpItems == null) {
                no_of_item_cols = Integer.valueOf(parentJF.getMapVars().get("no_of_item_column"));
                selGrpItems = new localTableModel(no_of_item_cols);
                setupGroupCols();
                groupTable.addMouseListener(new JTableButtonMouseListener(groupTable));
                groupTable.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            }

            if (dbc == null) {
                jSplitPane1.setDividerLocation(Integer.valueOf(parentJF.getMapVars().get("sales_screen_divider")));
                splitGroupItem.setDividerLocation(Integer.valueOf(parentJF.getMapVars().get("sale_items_split_divider")));
                dburl
                        = parentJF.getMapVars().get("dburl");
                username
                        = parentJF.getMapVars().get("username");
                password
                        = parentJF.getMapVars().get("password");
                sf.applyPattern(parentJF.getMapVars().get("ENGLISH_DATE_FORMAT"));
                dbc = new DBClass(dburl, username, password);
                dbConnection
                        = dbc.getDbConnection();
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
                itemdetails_dlv.createDBClassFromConnection(dbConnection);
                itemdetails_tbl.createDBClassFromConnection(dbConnection);
                delivery_data.createDBClassFromConnection(dbConnection);

                decimalformat = new DecimalFormat(parentJF.getMapVars().get("money_number"));
                dateformat
                        = new SimpleDateFormat(parentJF.getMapVars().get("short_date_format"));
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
                ((DefaultComboBoxModel) txtCashier.getModel()).removeAllElements();

                PreparedStatement ps = dbConnection.prepareStatement("select INVOICETYPE.*,C_YCUST.NAME ACNAME "
                        + " from invoicetype,C_YCUST where C_YCUST.CODE(+)=INVOICETYPE.ACCNO AND location_code=? order by no", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
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

                jComboBox1.setSelectedIndex(0);
                varCashType = Double.valueOf(((dataCell) jComboBox1.getSelectedItem()).getValue().toString());
                if (parentJF.getMapVars().get("CASH_TYPE") != null) {
                    varCashType = Integer.valueOf(parentJF.getMapVars().get("CASH_TYPE").toString());
                }
                dataCell dc1 = new dataCell("All", "");
                ((DefaultComboBoxModel) listDriver.getModel()).addElement(new dataCell("", ""));
                ((DefaultComboBoxModel) txtCashier.getModel()).addElement(dc1);
                ps = dbConnection.prepareStatement("select *from salesp where (type='S' or type='D') order by no", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rsx = ps.executeQuery();
                rsx.beforeFirst();
                while (rsx.next()) {
                    ((DefaultComboBoxModel) listDriver.getModel()).addElement(new dataCell(rsx.getString("name"), rsx.getString("NO")));
                    ((DefaultComboBoxModel) txtCashier.getModel()).addElement(new dataCell(rsx.getString("name"), rsx.getString("NO")));
                }
                ps.close();

                if (!parentJF.getMapVars().get("DEFAULT_CASHIER").equals("-1")) {
                    txtCashier.setSelectedItem(utils.findByValue(txtCashier,
                            parentJF.getMapVars().get("DEFAULT_CASHIER")));
                } else {
                    txtCashier.getModel().setSelectedItem(dc1);
                }
                onChangeCashier(false);
            }
            txtCashier.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    onChangeCashier(true);
                }
            });
            addAllSections();
            txtSection.setSelectedIndex(0);

            cmdTakeAway.setSelected(true);
            on_mode_change("cmdTakeAway");

        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }

    }
    public localTableModel payRows = new localTableModel(5);
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
    public double lastSelectedItemPrice = 0;

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
                if (itemsRows.get(i).lst.get(4).getValue() != null && itemsRows.get(i).lst.get(4).getValue().toString().length() > 0) {
                    return itemsRows.get(i).lst.get(4).getValue().toString();
                }
                return "DEFAULT";
            }

        }

        lastSelectedItemPrice = Double.valueOf(getValueFromItemsRow(itm, 2).toString());
        return "DEFAULT";
    }
    private int lastFoundItem = -1;
    private Row lastFoundRow = null;
    private boolean showing_itm_msg = false;

    public void show_item_balance_message(String refer, String ctg) {
        try {
            if (showing_itm_msg) {
                return;
            }
            showing_itm_msg = true;
            String qtyout = "";
            String onhand = "";
            String pkd = utils.getSqlValue("select packd from items where reference='" + refer + "'", dbConnection);
            String beforeCloseDate = parentJF.getMapVars().get("POS_CLOSE_DATE_" + parentJF.getMapVars().get("DEFAULT_LOCATION"));
            parentJF.getTxtMsg().setText("");
            PreparedStatement ps = dbConnection.prepareStatement("select sum(allqty/pack),max(packd) "
                    + " from pospur2 where dat>" + utils.getOraDateValue(beforeCloseDate) + " and "
                    + " refer= '" + refer + "' AND SIZE_OF_DESCR='" + ctg + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();
            qtyout = "0 " + pkd;
            if (rs != null && rs.first() && rs.getString(1) != null) {
                qtyout = rs.getString(1) + " " + rs.getString(2);
            }
            String descr = utils.getSqlValue("select descr from items where reference='" + refer + "'", dbConnection);
            String balance = utils.getSqlValue("select sum(qty-pos_out) from stori where strno=" + dataStore + " and refer='" + refer + "'", dbConnection);
            parentJF.getTxtMsg().setText(refer + " - " + descr + " ,Total Stock Out After # " + beforeCloseDate + " : " + qtyout + "   ,   Stock in Hand : " + balance + " " + pkd);
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        showing_itm_msg = false;
    }

    public Object getValueFromItemsRow(String rfr, int col) {
        // improving on quick finding items according previous call
        /*
         if (lastFoundItem != -1 && itemsRows.get(lastFoundItem).lst.get(0).getValue().toString().equals(rfr)) {
         return itemsRows.get(lastFoundItem).lst.get(col).getValue();
         }
        
         for (int i = 0; i < itemsRows.size(); i++) {
         if (itemsRows.get(i).lst.get(0).getValue().toString().equals(rfr)) {
         lastFoundItem = i;
         return itemsRows.get(i).lst.get(col).getValue();
         }
        
         }
        
         */
        if (lastFoundRow != null && lastFoundRow.lst.get(0).getValue().toString().equals(rfr)) {
            return lastFoundRow.lst.get(col).getValue();
        }
        lastFoundRow = itemMaps.get(rfr);
        lastFoundItem = -1;
        return lastFoundRow.lst.get(col).getValue();

    }

    public boolean is_valid_entry() throws Exception {
        boolean vld = true;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (parentJF.getMapVars().get("POS_CLOSE_DATE_" + parentJF.getMapVars().get("DEFAULT_LOCATION")) != null) {
            Date dt = sdf.parse(parentJF.getMapVars().get("POS_CLOSE_DATE_" + parentJF.getMapVars().get("DEFAULT_LOCATION")));
            Date dt2 = sdf.parse(sdf.format(dataInv_date));
            if (dt2.compareTo(dt) <= 0) {
                ShowMessageFrame sf = new ShowMessageFrame("Date should be great than " + sdf.format(dt), 18, 3000);
                throw new Exception("Date should be great than " + sdf.format(dt));
            }
        }
        if ((cmdDelivery.isSelected() && tbl_delivery.getSelectedRow() < 0)
                || (cmdDelivery.isSelected() && delivery_data.getRowCount() <= 0)) {
            ShowMessageFrame sf = new ShowMessageFrame("No delivery is selected !", 18, 3000);
            onShowDeliveries();
            throw new Exception("No delivery is selected !");
        }
        if (cmdTables.isSelected() && ((RTablesCanvas) canvas).st == null) {
            ShowMessageFrame sf = new ShowMessageFrame("No table is selected !", 18, 3000);
            onShowTables();
            throw new Exception("No table is selected !");
        }

        return vld;
    }

    public boolean is_valid_to_save() throws Exception {
        if (!is_valid_entry()) {
            return false;
        }
        if (cmdDelivery.isSelected() && tbl_delivery.getSelectedRow() >= 0 && delivery_data.getRowCount() > 0
                && delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "AMOUNT") != null) {
            double amt = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "AMOUNT").toString());
            if (amt > 0 && changeFromQuery && !parentJF.usrlog.checkSecurity("SEC_REQUIRE_ON_EDIT_DELIVERY", "Editing delivery ! ")) {
                throw new Exception("updating item has failed due to access. !");
            }
        }
        return true;
    }
    private boolean stopCalc = false;

    public void addUpdate() throws Exception {
        if (!is_valid_entry()) {
            return;
        }
        localTableModel t = (localTableModel) jTable1.getModel();
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
            ctg = getItemCategory(refer);
            txtCategory.setText(ctg);
            // utils.getSqlValue("select nvl(max(code),'DEFAULT')  from masterasm_ctg where baseitem='" + refer + "' and code!='DEFAULT'", dbConnection);
            if (ctg.length() == 0) {
                return;
            }
            int fnd = -1;//itemdetails.locate("REFER", refer, localTableModel.FIND_EXACT);
            for (int i = 0; i
                    < t.getRows().size(); i++) {
                if (t.getFieldValue(i, "REFER").equals(refer)
                        && t.getFieldValue(i, "SIZE_OF_DESCR").equals(ctg)) {
                    fnd = i;
                    break;
                }
            }
            try {
                stopCalc = true;
                if (fnd < 0) {
                    if (Double.valueOf(txtItemQty.getText()) > 0) {
                        fnd = t.addRecord();
                        changeFromQuery = true;
                        t.setFieldValue("ITEMPOS", BigDecimal.valueOf(t.getCursorNo() + 1));
                        t.setFieldValue("REFER", txtItemCode.getText());
                        t.setFieldValue("SIZE_OF_DESCR", ctg);
                        t.setFieldValue("PKQTY", BigDecimal.valueOf(Double.valueOf(txtItemQty.getText())));
                        if (itemsRows != null) {
                            t.setFieldValue("DESCR", getValueFromItemsRow(refer, 1));
                            t.setFieldValue("PRD_DATE", getValueFromItemsRow(refer, 7));
                            t.setFieldValue("EXP_DATE", getValueFromItemsRow(refer, 8));
                            t.setFieldValue("PACKD", getValueFromItemsRow(refer, 9));
                            t.setFieldValue("UNITD", getValueFromItemsRow(refer, 10));
                            t.setFieldValue("PACK", getValueFromItemsRow(refer, 11));
                        } else {
                            t.setFieldValue("DESCR", utils.getSqlValue(
                                    "select descr from items where reference='" + refer + "'", dbConnection));
                            t.setFieldValue("PRD_DATE", utils.getSqlValue("select PRD_DT from items "
                                    + " where reference='" + refer + "'", dbConnection));
                            t.setFieldValue("EXP_DATE", utils.getSqlValue("select EXP_DT from "
                                    + " items where reference='" + refer + "'", dbConnection));
                        }
                        t.setFieldValue("PRICE", String.valueOf(lastSelectedItemPrice));
                        jTable1.getSelectionModel().setSelectionInterval(fnd, fnd);
                    }

                } else {
                    BigDecimal b = BigDecimal.valueOf(Double.valueOf(t.getFieldValue(fnd,
                            "PKQTY").toString()) + Double.valueOf(txtItemQty.getText()));
                    if (b.doubleValue() <= 0) {
                        t.deleteRow(fnd);
                        t.getRowlistner().onCalc(0);
                    } else {
                        t.setFieldValue(fnd, "PKQTY", b);
                    }

                }

            } catch (Exception exx) {
                stopCalc = false;
                throw exx;
            }
            stopCalc = false;
            t.getRowlistner().onCalc(fnd);
            sumTxtDiscP.getInputVerifier().verify(sumTxtDiscP);
            update_sums();
            jTable1.updateUI();

        }
    }

    public void addUpdate(String refer, String ctg) throws Exception {
        addUpdate(refer, ctg, Double.valueOf(txtItemQty.getText()), -1.0);
    }

    public void addUpdate(String refer, String ctg, double qtyAdd, double price) throws Exception {
        if (!is_valid_entry()) {
            return;
        }
        localTableModel t = (localTableModel) jTable1.getModel();
        txtItemCode.setText(refer);
        if (((BigDecimal) (getValueFromItemsRow(refer, 6))).doubleValue() > 0) {
            txtItemCode.requestFocus();
            txtItemCode.selectAll();
            txtCategory.setText(ctg);
            JOptionPane.showMessageDialog(parentJF, "Parent Item can not select here");
            return;
        }
        if (refer.length() > 0) {
            txtItemCode.selectAll();
            if (ctg.length() == 0) {
                return;
            }
            lastSelectedItemPrice
                    = Double.valueOf(utils.getSqlValue("select nvl(sum(price1),0) "
                                    + " from masterasm_ctg where baseitem='" + refer + "'"
                                    + " and code='" + ctg + "'", dbConnection));
            if (lastSelectedItemPrice == 0) {
                lastSelectedItemPrice
                        = Double.valueOf(utils.getSqlValue("select price1 "
                                        + " from items where reference='" + refer + "'", dbConnection));
            }
            int fnd = -1;//itemdetails.locate("REFER", refer, localTableModel.FIND_EXACT);
            for (int i = 0; i
                    < t.getRows().size(); i++) {
                if (t.getFieldValue(i, "REFER").equals(refer)
                        && t.getFieldValue(i, "SIZE_OF_DESCR").equals(ctg)) {
                    fnd = i;
                    break;
                }
            }
            try {
                stopCalc = true;
                if (fnd < 0) {
                    if (qtyAdd > 0) {
                        fnd = t.addRecord();
                        changeFromQuery = true;
                        t.setFieldValue("ITEMPOS", BigDecimal.valueOf(t.getCursorNo() + 1));
                        t.setFieldValue("REFER", txtItemCode.getText());
                        t.setFieldValue("SIZE_OF_DESCR", ctg);
                        t.setFieldValue("PKQTY", qtyAdd);
                        if (itemsRows != null) {
                            t.setFieldValue("DESCR", getValueFromItemsRow(refer, 1));
                            t.setFieldValue("PRD_DATE", getValueFromItemsRow(refer, 7));
                            t.setFieldValue("EXP_DATE", getValueFromItemsRow(refer, 8));
                            t.setFieldValue("PACKD", getValueFromItemsRow(refer, 9));
                            t.setFieldValue("UNITD", getValueFromItemsRow(refer, 10));
                            t.setFieldValue("PACK", getValueFromItemsRow(refer, 11));
                        } else {
                            t.setFieldValue("DESCR", utils.getSqlValue("select descr from items where reference='" + refer + "'", dbConnection));
                            t.setFieldValue("PRD_DATE", utils.getSqlValue("select PRD_DT from items where reference='" + refer + "'", dbConnection));
                            t.setFieldValue("EXP_DATE", utils.getSqlValue("select EXP_DT from "
                                    + " items where reference='" + refer + "'", dbConnection));
                        }
                        if (price < 0) {
                            t.setFieldValue("PRICE", String.valueOf(lastSelectedItemPrice));
                        } else {
                            t.setFieldValue("PRICE", String.valueOf(price));
                        }

                        jTable1.getSelectionModel().setSelectionInterval(fnd, fnd);

                    }

                } else {
                    BigDecimal b = BigDecimal.valueOf(Double.valueOf(t.getFieldValue(fnd, "PKQTY").toString()) + qtyAdd);
                    if (b.doubleValue() <= 0) {
                        t.deleteRow(fnd);
                        changeFromQuery = true;
                        t.getRowlistner().onCalc(0);

                    } else {
                        t.setFieldValue(fnd, "PKQTY", b);
                        jTable1.getSelectionModel().setSelectionInterval(fnd, fnd);
                        changeFromQuery = true;
                    }

                }
                if (price >= 0) {
                    t.setFieldValue("PRICE", String.valueOf(price));
                }
            } catch (Exception exx) {
                stopCalc = false;
                throw exx;
            }
            stopCalc = false;
            t.getRowlistner().onCalc(fnd);
            sumTxtDiscP.getInputVerifier().verify(sumTxtDiscP);
            update_sums();
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
                    if (cmdTakeAway.isSelected()) {
                        last_takeAway_disc_amt = sumDiscountAmt;
                    }
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
                    if (sumDiscountP <= -1) {
                        sumDiscountP = 0;
                        txt.setText(String.valueOf(sumDiscountP));
                    }
                    if (sumGrossAmt > 0 && sumDiscountP > 0) {
                        sumDiscountAmt = (sumGrossAmt / 100) * sumDiscountP;
                    }
                    sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
                    if (cmdTakeAway.isSelected()) {
                        last_takeAway_disc_amt = sumDiscountAmt;
                    }

                }

                update_sums();
                ret
                        = true;
            } catch (ParseException ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
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

    private void calc_itemdetails(localTableModel t, int cursorNo) {

        if (stopCalc) {
            return;
        }
        sumGrossAmt = 0;
        sumPaidAmt = sumGrossAmt + sumAddAmt;

        if ((cursorNo < 0) || (cursorNo >= t.getRows().size())) {
            sumQty = t.getSummaryOf("PKQTY", localTableModel.SUMMARY_SUM);
            sumGrossAmt = t.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
            sumPaidAmt = sumGrossAmt + sumAddAmt;
            update_sums();
            return;
        }
        double p = 0, q = 0, pk = 1;

        if (t.getFieldValue(cursorNo, "PRICE") != null) {
            p = utils.noValue(Double.valueOf(t.getFieldValue(cursorNo, "PRICE").toString()), Double.valueOf(0));
        }
        if (t.getFieldValue(cursorNo, "PKQTY") != null && t.getFieldValue(cursorNo, "PACK") != null) {
            q = utils.noValue(Double.valueOf(t.getFieldValue(cursorNo, "PKQTY").toString()), Double.valueOf(0));
            pk = utils.noValue(Double.valueOf(t.getFieldValue(cursorNo, "PACK").toString()), Double.valueOf(0));
            t.setFieldValue(cursorNo, "ALLQTY", BigDecimal.valueOf(q * pk));
            sumQty = t.getSummaryOf("PKQTY", localTableModel.SUMMARY_SUM);
        }
        t.setFieldValue(cursorNo, "AMOUNT", BigDecimal.valueOf(p * q));
        t.setFieldValue(cursorNo, "QTY", 0);
        sumGrossAmt = t.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumPaidAmt = sumGrossAmt + sumAddAmt;
        update_sums();
    }

    public void setupItemDetails() throws Exception {
        if (cmdDelivery.isSelected() || cmdTables.isSelected()) {
            return;
        }
        itemdetails.setRowlistner(new rowTriggerListner() {

            public void onCalc(int cursorNo) {
                calc_itemdetails(itemdetails, cursorNo);

            }
        });

        itemdetails_dlv.setRowlistner(new rowTriggerListner() {

            public void onCalc(int cursorNo) {
                calc_itemdetails(itemdetails_dlv, cursorNo);

            }
        });

        itemdetails_tbl.setRowlistner(new rowTriggerListner() {

            public void onCalc(int cursorNo) {
                calc_itemdetails(itemdetails_tbl, cursorNo);
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

//        itemdetails.getColByName("DESCR").setDatabaseCol(false);
//        itemdetails.getColByName("SIZE_OF_DESCR").setTitle("CATEGORY");
//        itemdetails.getColByName("AMOUNT").setDatabaseCol(false);
//        itemdetails.getColByName("ITEMPOS").setAlignmnet(JLabel.CENTER);
//        itemdetails.getColByName("AMOUNT").setColor(Color.YELLOW);
//        itemdetails.getColByName("PKQTY").setTitle("Pack Qty");
//        itemdetails.getColByName("AMOUNT").setNumberFormat(decimalformat.toPattern());
//        itemdetails.getColByName("PRICE").setNumberFormat(decimalformat.toPattern());
        if (itemdetails_dlv.getColumnCount() == 0) {
            itemdetails_dlv.createDBClassFromConnection(itemdetails.getDbclass().getDbConnection());
        }
        itemdetails_tbl.executeQuery("SELECT P.ITEMPOS,P.CTG SIZE_OF_DESCR,P.REFER,I.DESCR,P.PRICE,P.PKQTY,P.PKQTY*P.PRICE AMOUNT  ,I.PACKD,I.PACK,I.UNITD ,P.ALLQTY ,P.QTY,I.PRD_DT PRD_DATE, I.EXP_DT EXP_DATE "
                + "FROM  POS_ONPUR2 P,ITEMS I WHERE I.REFERENCE=P.REFER AND P.KEYFLD IS NULL", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});

        itemdetails_dlv.executeQuery("SELECT P.ITEMPOS,P.CTG SIZE_OF_DESCR,P.REFER,I.DESCR,P.PRICE,P.PKQTY,P.PKQTY*P.PRICE AMOUNT  ,I.PACKD,I.PACK,I.UNITD ,P.ALLQTY ,P.QTY,I.PRD_DT PRD_DATE, I.EXP_DT EXP_DATE "
                + "FROM  POS_ONPUR2 P,ITEMS I WHERE I.REFERENCE=P.REFER AND P.KEYFLD is null", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});

        //copy_cols_to_all_models();
        localTableModel itemd = itemdetails;
        if (cmdTables.isSelected()) {
            itemd = itemdetails_tbl;
        }
        if (cmdDelivery.isSelected()) {
            itemd = itemdetails_dlv;
        }

        jTable1.setModel(dataNull);
        jTable1.setModel(itemd);
        setColsProperties(itemd);
        jTable1.setModel(dataNull);
        jTable1.setModel(itemd);

        jTable1.revalidate();

    }

    public void applyColumn(qryColumn qc, JTable table, int col) {
        int width = qc.getDisplaySize();
        if (parentJF.getMapVars().get("sale_col_" + col + "_width") != null) {
            width = Integer.valueOf(parentJF.getMapVars().get("sale_col_" + col + "_width"));
        }

    }
    private double fromk = 0;
    private double tok = 0;

    public void load_data(String qr) {
        this.QRYSTR = qr;
        load_data();
    }

    public void addAllSections() {
        ((DefaultComboBoxModel) txtSection.getModel()).removeAllElements();
        PreparedStatement ps;
        try {
            ps = dbConnection.prepareStatement("select code,section_name from pos_sections  order by code",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsx = ps.executeQuery();
            rsx.beforeFirst();

            while (rsx.next()) {
                ((DefaultComboBoxModel) txtSection.getModel()).addElement(new dataCell(rsx.getString("SECTION_NAME") + "-" + rsx.getString("CODE"), rsx.getString("CODE")));
            }
            ps.close();
            if (((DefaultComboBoxModel) txtSection.getModel()).getSize() <= 0) {
                JOptionPane.showMessageDialog(parentJF, "Must Create section", "", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load_data() {
        try {
            /*
             if (parentJF.getMapVars().get("default_page") != null) {
             jTabbedPane1.setSelectedIndex(Integer.valueOf(parentJF.getMapVars().get("default_page")));
             }

             */

            jTable1.getColumnModel().setColumnSelectionAllowed(false);
            jTable1.getTableHeader().setReorderingAllowed(false);
            jTable1.setRowSorter(null);
            lbl_itemSel.setText(" ");

            inCheckDiscount = false;
            varSectionCode = "";
            varSectionName = "";
            varSectionName = "";
            varSaleKeyfld = "";
            changeFromQuery = false;
            varFlag = 1;
            varDay1 = "Y";
            varDay2 = "Y";
            varDay3 = "Y";
            varDay4 = "Y";
            varDay5 = "Y";
            varDay6 = "Y";
            varDay7 = "Y";
            varHeight = 280;
            varWidth = 325;
            dataBkInvoiceDate.setTime(System.currentTimeMillis());
            dataBkKeyfld = -1;
            dataBkInvoiceNo = Double.valueOf(utils.getSqlValue("Select nvl(max(b_no),0)+1 from pos_onpur1", dbConnection));
            dataBkClosingTime.setTime(System.currentTimeMillis());
            dataBkCustCode = "";
            dataBkCustomerCount = 1;

            parentJF.displayDeliveryStatus(false);
            setupItemDetails();
            clearPayments();
            sumChangeAmt = 0;
            sumAddAmt = 0;
            sumDiscountAmt = 0;
            sumDiscountP = 0;
            sumGrossAmt = 0;
            sumNetAmt = 0;
            sumAdvanceAmt = 0;
            sumPaidAmt = 0;
            sumCashAmt = 0;
            sumQty = 0;
            dataInv_date.setTime(System.currentTimeMillis());
            update_sums();
            last_takeAway_disc_amt = sumDiscountAmt;
            sumTxtPaidAmt.setText(decimalformat.format(sumPaidAmt));
            sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
            sumTxtDiscP.setText(decimalformat.format(sumDiscountP));
            sumTxtCashAmt.setText(decimalformat.format(sumCashAmt));
            itemdetails.setEditAllowed(false);
            itemdetails_dlv.setEditAllowed(false);
            itemdetails_tbl.setEditAllowed(false);
            jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
            jTable1.setRowHeight(30);
            lblSelection.setText("   ");
            fromk = Double.valueOf(parentJF.getMapVars().get("FROM_KEYFLD"));
            tok = Double.valueOf(parentJF.getMapVars().get("TO_KEYFLD"));
            dataLocationCode
                    = parentJF.getMapVars().get("DEFAULT_LOCATION");
            dataLocationName
                    = parentJF.getMapVars().get("LOCATION_NAME");
            dataDlKeyfld = -1;
            dataDlvSales = -1;
            dataDlvDate.setTime(dataInv_date.getTime());
            dataDlvAdvanceDate.setTime(dataInv_date.getTime());
            txtLocation.setText(dataLocationName);
            txtKeyfld.setText(String.valueOf(dataKeyfld));
            txtInvoiceDate.setText(sf.format(dataInv_date));
            txtDate.setText(sf.format(dataInv_date));
            jComboBox1.setSelectedIndex(0);
            listDriver.setSelectedIndex(0);
            txtInvoiceNo.setText(utils.getSqlValue("select nvl(max(invoice_no),0)+1 from POSPUR1 WHERE LOCATION_CODE='" + dataLocationCode + "' and "
                    + "periodcode='" + parentJF.getMapVars().get("CURRENT_PERIOD") + "' and invoice_code=" + dataInvoiceCode, dbConnection));

            txtVoucherNo.setText(txtInvoiceNo.getText());
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
            adrHour.setText("0");
            adrMin.setText("0");
            txtDiscDescr.setText("");

            if (cmdTakeAway.isSelected() && QRYSTR.length() != 0) {
                ps_qry_1 = dbConnection.prepareStatement("select *from POSpur1 where keyfld=" + QRYSTR,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rss = ps_qry_1.executeQuery();
                if (rss.first()) {
                    txtKeyfld.setText(rss.getString("KEYFLD"));
                    dataKeyfld = rss.getDouble("keyfld");
                    txtInvoiceNo.setText(rss.getString("INVOICE_NO"));
                    txtVoucherNo.setText(txtInvoiceNo.getText());
                    txtInvoiceDate.setText(sf.format(new Date(rss.getTimestamp("INVOICE_DATE").getTime())));
                    txtDate.setText(txtInvoiceDate.getText());
                    txtDiscDescr.setText(rss.getString("MEMO"));
                    txtReasonCancel.setText(rss.getString("CTG"));
                    varSaleKeyfld = rss.getString("issue_keyfld");

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
                    adrRArea.setSelectedItem(rss.getString("ADDR_R_AREA"));
                    adrRBldg.setText(rss.getString("ADDR_R_BLDG"));
                    adrRBlock1.setText(rss.getString("ADDR_R_BLOCK"));
                    adrRFloorNo.setText(rss.getString("ADDR_R_FLOOR"));
                    adrRJedda.setText(rss.getString("ADDR_R_JEDDA"));
                    adrROtherTel.setText(rss.getString("ADDR_R_TEL"));
                    adrRStreet.setText(rss.getString("ADDR_R_STREET"));
                    adrRWorkAddress.setText(rss.getString("WORK_R_ADDRESS"));

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

            if (cmdTables.isSelected()) {
                RTablesCanvas rtc = ((RTablesCanvas) canvas);
                localTableModel itemd = itemdetails_tbl;
                if (rtc.st == null) {
                    itemdetails_tbl.getRows().clear();
                } else {
                    PreparedStatement ps = dbConnection.prepareStatement("select *from pos_onpur1 where flag=1 and  keyfld='" + rtc.st.active_keyfld + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet rst = ps.executeQuery();
                    if (rst != null && rst.first()) {
                        dataBkCustomerCount = rst.getInt("CUST_COUNTS");
                        dataBkInvoiceDate.setTime(rst.getDate("b_datetime").getTime());
                        dataBkInvoiceNo = rst.getDouble("b_no");
                        dataBkKeyfld = rst.getDouble("keyfld");
                        sumTxtDiscAmt.setText(decimalformat.format(rst.getDouble("DISC_AMT")));
                        sumDiscountAmt = rst.getDouble("DISC_AMT");
                        sumTxtDiscAmt.getInputVerifier().verify(sumTxtDiscAmt);

                        ps.close();
                        itemdetails_tbl.executeQuery("SELECT P.ITEMPOS,P.CTG SIZE_OF_DESCR,P.REFER,I.DESCR,P.PRICE,P.PKQTY,P.PKQTY*P.PRICE AMOUNT  ,I.PACKD,I.PACK,I.UNITD ,P.ALLQTY ,P.QTY,I.PRD_DT PRD_DATE, I.EXP_DT EXP_DATE "
                                + "FROM  POS_ONPUR2 P,ITEMS I WHERE I.REFERENCE=P.REFER AND P.KEYFLD='" + rtc.st.active_keyfld + "'", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});
                    } else {
                        itemdetails_tbl.getRows().clear();
                        refresh_active_tables();
                    }
                    update_sums();
                }
                jTable1.setModel(dataNull);
                jTable1.setModel(itemdetails_tbl);
                setColsProperties(itemd);
                jTable1.updateUI();

            }

            if (cmdDelivery.isSelected() && tbl_delivery.getSelectedRow() > -1 && delivery_data.getRowCount() > 0 && tbl_delivery.getSelectedRow() < delivery_data.getRowCount()) {
                dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
                dataDlvSales = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "SLSMN").toString());
                sumDiscountAmt = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "DISC_AMT").toString());
                sumAdvanceAmt = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "ADVANCE_PAID").toString());
                sumTxtDiscAmt.setText(decimalformat.format(sumDiscountAmt));
                sumTxtDiscAmt.getInputVerifier().verify(sumTxtDiscAmt);
                itemdetails_dlv.executeQuery("SELECT P.ITEMPOS,P.CTG SIZE_OF_DESCR,P.REFER,I.DESCR,P.PRICE,P.PKQTY,P.PKQTY*P.PRICE AMOUNT  ,I.PACKD,I.PACK,I.UNITD ,P.ALLQTY ,P.QTY,I.PRD_DT PRD_DATE, I.EXP_DT EXP_DATE "
                        + "FROM  POS_ONPUR2 P,ITEMS I WHERE I.REFERENCE=P.REFER AND P.KEYFLD='" + dataDlKeyfld + "'", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});
                lblSelection.setText(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "CUST_NAME").toString());
                localTableModel itemd = itemdetails_dlv;
                jTable1.setModel(dataNull);
                jTable1.setModel(itemd);
                setColsProperties(itemd);
                jTable1.updateUI();
                //sumAdvanceAmt = Double.valueOf(utils.getSqlValue("select nvl(max(advance_paid),0) from pos_onpur1 where KEYFLD='" + dataDlKeyfld + "'", dbConnection));
            }
            jTable1.invalidate();
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            repaint();
        }
    }
    PreparedStatement ps_titems = null;
    public List<Row> itemsRows = null;
    private Map<String, Row> itemMaps = new HashMap<String, Row>();
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
        lbl_itemSel.setText(" ");
        if (dataInvoiceCode == 20 && itemsRows == null) {
            if (parentJF.getSp().isShowing_items() == false && parentJF.getSp().getItemsRows() != null) {
                itemsRows = new ArrayList<Row>();
                itemMaps.clear();
                for (int i = 0; i < parentJF.getSp().getItemsRows().size(); i++) {
                    itemsRows.add(parentJF.getSp().getItemsRows().get(i));
                    itemMaps.put(parentJF.getSp().getItemsRows().get(i).lst.get(0).getValue().toString(), parentJF.getSp().getItemsRows().get(i));
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
                // 0 reference ,   1 descr ,  2 price, 3 parentitem ,
                // 4 ctg_code ,   5 barcode ,  6 childcounts, 7 prd_dt ,
                // 8 exp_dt ,   9 packd  ,  10 unitd, 11 pack ,
                // 12 levelno ,   9 packd  ,  10 unitd, 11 pack ,
                String par = parentJF.getMapVars().get("SHOW_ITEM_GROUP");
                sqlstr
                        = " select reference  ,descr,  NVL(masterasm_ctg.price1,ITEMS.PRICE1) PRICE1,"
                        + "  parentitem,  masterasm_ctg.CODE,  items.barcode,"
                        + "  childcounts,  items.prd_dt,   items.exp_dt,  ITEMS.packd,"
                        + "  ITEMS.unitd,  ITEMS.pack,     ITEMS.LEVELNO , items.descr2 "
                        + "  from items,masterasm_ctg where "
                        + "  (DESCR2 LIKE (SELECT NVL(MAX(DESCR2),'%')||'%' FROM ITEMS WHERE REFERENCE='" + par + "')) AND "
                        + "  BASEITEM(+)=REFERENCE  order by descr2";
                ps_titems
                        = dbConnection.prepareStatement(sqlstr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //ResultSet rs = ps_titems.executeQuery();
                itemMaps.clear();
                itemsRows
                        = utils.convertRows2(ps_titems, itemMaps, "", 0, 0);
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
                    itemTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionMouse, true, Color.pink));
                    ((ButtonRenderer) itemTable.getColumnModel().getColumn(colx).getCellRenderer()).setHorizontalAlignment(SwingConstants.RIGHT);
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
    public javax.swing.JComboBox adrArea;
    public javax.swing.JTextField adrBldg;
    public javax.swing.JTextField adrBlock;
    public javax.swing.JTextField adrCustomerName;
    public javax.swing.JTextField adrDeliveryDate;
    public javax.swing.JTextField adrEmail;
    public javax.swing.JTextField adrFloorNo;
    public javax.swing.JTextField adrHour;
    public javax.swing.JTextField adrJedda;
    public javax.swing.JTextField adrMin;
    public javax.swing.JTextArea adrNotes;
    public javax.swing.JTextField adrOtherTel;
    public javax.swing.JTextField adrPhone;
    public javax.swing.JComboBox adrRArea;
    public javax.swing.JTextField adrRBldg;
    public javax.swing.JTextField adrRBlock1;
    public javax.swing.JTextField adrRFloorNo;
    public javax.swing.JTextField adrRJedda;
    public javax.swing.JTextField adrROtherTel;
    public javax.swing.JTextField adrRPhone;
    public javax.swing.JTextField adrRStreet;
    public javax.swing.JTextField adrRWorkAddress;
    public javax.swing.JTextField adrStreet;
    public javax.swing.JTextField adrWorkAddress;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel canvas;
    public javax.swing.JCheckBox chkPickup;
    public javax.swing.JCheckBox chkRCopyClient;
    private javax.swing.JCheckBox chkShowSearchPanel;
    public javax.swing.JButton cmdClose;
    private javax.swing.JButton cmdCreateDeliver;
    public javax.swing.JToggleButton cmdDelivery;
    private javax.swing.JButton cmdEditDeliver;
    public javax.swing.JButton cmdItemAdd;
    public javax.swing.JButton cmdItemAdd2;
    private javax.swing.JButton cmdPayHereAll;
    private javax.swing.JButton cmdPayRestHere;
    private javax.swing.JButton cmdRemoveDeliver;
    private javax.swing.JButton cmdRemoveDeliver1;
    private javax.swing.JButton cmdSave;
    public javax.swing.JToggleButton cmdTables;
    public javax.swing.JToggleButton cmdTakeAway;
    private javax.swing.JTable groupTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    public javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
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
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
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
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblAmt;
    private javax.swing.JLabel lblChangeAmt;
    private javax.swing.JLabel lblSelection;
    private javax.swing.JLabel lbl_itemSel;
    private javax.swing.JComboBox listDriver;
    private javax.swing.JPanel panelCenter;
    public javax.swing.JTable payTable;
    private javax.swing.JPanel pnlAcc;
    private javax.swing.JPanel pnlBasicInfo;
    private javax.swing.JPanel pnlClosing;
    private javax.swing.JPanel pnlDeliverInfo;
    private javax.swing.JPanel pnlDeliveries;
    private javax.swing.JPanel pnlFooter_search;
    private javax.swing.JPanel pnlItems;
    private javax.swing.JPanel pnlS;
    private javax.swing.JPanel pnlTables;
    private javax.swing.JPanel pnl_reason_of_cancel;
    private javax.swing.JPanel searchPanel;
    private javax.swing.JSplitPane splitGroupItem;
    public javax.swing.JTextField sumTxtAddAmt;
    public javax.swing.JTextField sumTxtCashAmt;
    private javax.swing.JLabel sumTxtChangeAmt;
    public javax.swing.JTextField sumTxtDiscAmt;
    public javax.swing.JTextField sumTxtDiscP;
    private javax.swing.JLabel sumTxtGrossAmount;
    public javax.swing.JLabel sumTxtNetAmount;
    private javax.swing.JTextField sumTxtPaidAmt;
    private javax.swing.JLabel sumTxtTotQty;
    private javax.swing.JTable tbl_delivery;
    public javax.swing.JComboBox txtCashier;
    private javax.swing.JTextField txtCategory;
    private javax.swing.JLabel txtDate;
    private javax.swing.JTextField txtDiscDescr;
    private javax.swing.JLabel txtInvoiceDate;
    private javax.swing.JTextField txtInvoiceNo;
    public javax.swing.JTextField txtItemCode;
    public javax.swing.JTextField txtItemPrice;
    public javax.swing.JTextField txtItemQty;
    private javax.swing.JLabel txtKeyfld;
    private javax.swing.JLabel txtLocation;
    public javax.swing.JTextField txtReasonCancel;
    private javax.swing.JComboBox txtSection;
    private javax.swing.JLabel txtVoucherNo;
    // End of variables declaration//GEN-END:variables
    private MainFrame parentJF = null;
    public Connection dbConnection = null;
    private String dburl = "";
    private String username = "";
    private String password = "";
    private PreparedStatement ps_qry_1 = null;
    private PreparedStatement ps_qry_2 = null;
    private double dataKeyfld = -1;
    private String datasalesp = "";
    public double dataInvoiceNo = -1;
    public Date dataInv_date = new Date(System.currentTimeMillis());
    public String dataLocationCode = "";
    public String dataLocationName = "";
    private int posno = -1;
    public int strno = 0;
    public String QRYSTR = "";
    private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
    private localTableModel itemdetails = new localTableModel();
    private localTableModel itemdetails_dlv = new localTableModel();
    private localTableModel itemdetails_tbl = new localTableModel();
    //private ResultSetTableModelFactory factory;
    private DBClass dbc = null;
    private localTableModel selItems = null;
    private localTableModel selGrpItems = null;
    private localTableModel delivery_data = new localTableModel(2);
    private String currentItem = "";
    private String currentGroup = "";
    private int no_of_item_cols = 3;
    public double sumGrossAmt = 0;
    public double sumNetAmt = 0;
    public double sumAdvanceAmt = 0;
    public double sumCashAmt = 0;
    public double sumPaidAmt = 0;
    public double sumPaidAmtThis = 0;
    public double sumAddAmt = 0;
    public double sumChangeAmt = 0;
    public double sumDiscountAmt = 0;
    public double sumDiscountP = 0;
    public double sumQty = 0;
    private double dataBkInvoiceNo = -1;
    private double dataBkCustomerCount = 1;
    private String dataBkCustCode = "";
    private Date dataBkClosingTime = new Date(System.currentTimeMillis());
    private Date dataBkInvoiceDate = new Date(System.currentTimeMillis());
    private double dataBkKeyfld = -1;
    private double dataDlKeyfld = -1;
    public double dataDlvSales = -1;
    public Date dataDlvDate = new Date(System.currentTimeMillis());
    public Date dataDlvAdvanceDate = new Date(System.currentTimeMillis());
    private DecimalFormat decimalformat = null;
    private SimpleDateFormat dateformat = null;
    private boolean showing_items = false;
    private int dataInvoiceCode = 30;
    public int dataStore = 1;
    private double dataInvType = 1;
    private String varSectionCode = "##CREATE";
    private String varSectionName = "";
    private int varFlag = 1;
    private String varDay1 = "Y";
    private String varDay2 = "Y";
    private String varDay3 = "Y";
    private String varDay4 = "Y";
    private String varDay5 = "Y";
    private String varDay6 = "Y";
    private String varDay7 = "Y";
    private int varWidth = 300;
    private int varHeight = 200;
    private double varCashType = -1;
    public String varSaleKeyfld = "";
    public String varSale = "";
    public boolean changeFromQuery = false;
    private String[] strAlign = new String[]{"CENTER", "TOP", "LEFT", "BOTTOM", "RIGHT"};
    private localTableModel dataNull = new localTableModel(0);

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

    private MouseAdapter cmdActionMouse = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            String name = ((JLabel) e.getSource()).getName();
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

    public void onSelectionOfTable(RTables r) {
        RTablesCanvas rtc = ((RTablesCanvas) canvas);
        if ((rtc.st != rtc.last_st) || itemdetails_tbl.getRowCount() <= 0) {
            load_data();
        }
        lblSelection.setText("   ");
        if (((RTablesCanvas) canvas).st != null) {
            lblSelection.setText(rtc.st.Code);
        }

    }

    public void onMovingTable(RTables r, int pos_x, int pos_y) {
    }

    private void refresh_active_tables() {
        RTablesCanvas rtc = (RTablesCanvas) canvas;
        try {
            for (Iterator<RTables> it = rtc.listTables.iterator(); it.hasNext();) {
                RTables rt = (RTables) it.next();
                rt.isActive = false;
                rt.active_keyfld = -1;
            }
            PreparedStatement pst = dbConnection.prepareStatement("select keyfld,table_code "
                    + " from pos_onpur1 where flag=1 order by table_code", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = pst.executeQuery();
            if (rst != null && rst.first()) {
                rst.beforeFirst();
                while (rst.next()) {
                    RTables rt = findTableByCode(rst.getString(2), rtc.listTables);
                    if (rt != null) {
                        rt.active_keyfld = rst.getDouble(1);
                        rt.isActive = true;
                    }
                }
                rst.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
        }

    }

    public RTables findTableByCode(String cod, List<RTables> rtb) {
        for (Iterator<RTables> it = rtb.iterator(); it.hasNext();) {
            RTables rTables = it.next();
            if (rTables.Code.equals(cod)) {
                return rTables;
            }
        }
        return null;
    }

    private void copy_cols_to_all_models() {
        itemdetails_dlv.getQrycols().clear();
        itemdetails_dlv.getVisbleQrycols().clear();
        itemdetails_tbl.getQrycols().clear();
        itemdetails_tbl.getVisbleQrycols().clear();
        itemdetails_dlv.getQrycols().addAll(itemdetails.getVisbleQrycols());
        itemdetails_dlv.getVisbleQrycols().addAll(itemdetails.getVisbleQrycols());
        itemdetails_tbl.getQrycols().addAll(itemdetails.getVisbleQrycols());
        itemdetails_tbl.getVisbleQrycols().addAll(itemdetails.getVisbleQrycols());
        itemdetails_tbl.setCols(itemdetails.getQrycols().size());
        itemdetails_dlv.setCols(itemdetails.getQrycols().size());
        for (int i = 0; i < itemdetails_dlv.getQrycols().size(); i++) {
            itemdetails_dlv.getQrycols().get(i).setColpos(i);
            itemdetails_tbl.getQrycols().get(i).setColpos(i);
        }
    }

    public void close_voucher() throws Exception {
        if (cmdTakeAway.isSelected() == true) {
            try {
                double kf = save_data();
                if (kf > 0) {
                    print_data(kf);
                }
            } catch (Exception ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                ShowMessageFrame sf = new ShowMessageFrame(ex, 12, 4000);
            }

        }
        if (cmdTables.isSelected() == true) {
            try {
                double kf = close_table_data();
                if (kf > 0) {
                    print_data(kf);
                }
            } catch (Exception ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                ShowMessageFrame sf = new ShowMessageFrame(ex, 12, 4000);
            }

        }
        if (cmdDelivery.isSelected() == true) {
            try {
                double kf = close_delivery_data();
                if (kf > 0) {
                    print_data(kf);
                }
            } catch (Exception ex) {
                Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
                ShowMessageFrame sf = new ShowMessageFrame(ex, 12, 4000);
            }

        }

    }

    private double close_delivery_data() throws Exception {
        if (dataInvoiceCode == 20) {
            throw new SQLException("Unexpected return for tables !");
        }
        if (tbl_delivery.getSelectedRow() < 0) {
            throw new Exception("Must select delivery !");
        }
        if (delivery_data.getRowCount() <= 0) {
            throw new Exception("Must have records !");
        }

        try {
            dbConnection.setAutoCommit(false);
            QRYSTR = "";
            sumPaidAmtThis = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
            sumPaidAmt = sumPaidAmtThis + sumAdvanceAmt;
            dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
            dataDlvSales = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "SLSMN").toString());
            dataBkInvoiceNo = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "B_NO").toString());
            dataBkInvoiceDate = new Date(((Timestamp) delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "B_DATE")).getTime());

            double kf = save_data_ta(itemdetails_dlv, false);

            utils.execSql("update pos_onpur1 set flag=2 , SALEINV=" + kf + " WHERE keyfld='" + dataDlKeyfld + "'", dbConnection);
            utils.execSql("update pospur1 SET orderno='" + dataDlKeyfld + "', BOOKING_KIND='DELIVERY' WHERE KEYFLD='" + kf + "'", dbConnection);
            dbConnection.commit();
            onChangeCashier(false);
            return kf;
        } catch (Exception ex) {
            ex.printStackTrace();
            dbConnection.rollback();
        }
        return -1;
    }

    private double close_table_data() throws Exception {
        if (dataInvoiceCode == 20) {
            throw new SQLException("Unexpected return for tables !");
        }

        RTablesCanvas rtc = (RTablesCanvas) canvas;
        if (rtc.st == null) {
            throw new Exception("Table must select !");
        }

        if (!rtc.st.isActive) {
            throw new Exception("table is not active !");
        }
        try {
            dbConnection.setAutoCommit(false);
            QRYSTR = "";
            double kf = save_data_ta(itemdetails_tbl, false);
            utils.execSql("update pos_onpur1 set flag=2 , SALEINV=" + kf + " WHERE keyfld='" + rtc.st.active_keyfld + "'", dbConnection);
            utils.execSql("update pospur1 "
                    + " set inv_refnm='" + rtc.st.Code + "' , orderno='" + rtc.st.keyfld + "',"
                    + " BOOKING_KIND='TABLE' WHERE KEYFLD='" + kf + "'", dbConnection);
            dbConnection.commit();
            refresh_active_tables();
            rtc.updateUI();
            load_data();
            cmdTables.doClick();
            return kf;
        } catch (Exception ex) {
            ex.printStackTrace();
            dbConnection.rollback();
        }
        return -1;
        /*
         String posinout = "POS_IN_QTY";



         try {
         dbConnection.setAutoCommit(false);
         dataInvoiceNo = Double.valueOf(utils.getSqlValue("select nvl(max(invoice_no),0)+1 from pospur1 where invoice_code =" + dataInvoiceCode, dbConnection));
         dataKeyfld = Double.valueOf(utils.getSqlValue("select nvl(max(KEYFLD),0)+1 from pospur1 " + " ", dbConnection));
         QueryExe qe = new QueryExe("insert into pospur1("
         + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
         + "INVOICE_CODE, TYPE, INVOICE_DATE,"
         + "STRA, USERNAME, INV_AMT, "
         + "DISC_AMT, CREATDT, KEYFLD,YEAR,SLSMN, "
         + "INV_REF,INV_REFNM,ADDR_AREA,"
         + "ADDR_TEL ,HOME_ADDRESS,WORK_ADDRESS,SPEC_COMMENTS,"
         + "COMPLAINS,addr_jedda,addr_block,addr_street,"
         + "addr_bldg,reference_information,paidamt2,totqty,add_charge,MEMO,CTG"
         + ")values ( "
         + ":PERIODCODE, :LOCATION_CODE, :INVOICE_NO, "
         + ":INVOICE_CODE, :TYPE, :INVOICE_DATE,"
         + ":STRA, :USERNAME, :INV_AMT, "
         + ":DISC_AMT, :CREATDT, :KEYFLD,YEAR,:SLSMN, "
         + ":INV_REF,:INV_REFNM,:ADDR_AREA,"
         + ":ADDR_TEL ,:HOME_ADDRESS,:WORK_ADDRESS,:SPEC_COMMENTS,"
         + ":COMPLAINS,:addr_jedda,:addr_block,:addr_street,"
         + ":addr_bldg,:reference_information,:paidamt2,:totqty,:add_charge,:MEMO,:CTG)", dbConnection);

         qe.setParaValue("PERIODCODE", parentJF.getMapVars().get("CURRENT_PERIOD"));
         qe.setParaValue("LOCATION_CODE", dataLocationCode);
         qe.setParaValue("INVOICE_NO", dataInvoiceNo);
         qe.setParaValue("INVOICE_CODE", dataInvoiceCode);
         qe.setParaValue("TYPE", dataInvType);
         qe.setParaValue("INVOICE_DATE", new java.sql.Date(dataInv_date.getTime()));
         qe.setParaValue("STRA", dataStore);
         qe.setParaValue("USERNAME", parentJF.getLp().getLogon_user());
         qe.setParaValue("INV_AMT", sumGrossAmt);
         qe.setParaValue("DISC_AMT", sumDiscountAmt);
         qe.setParaValue("CREATDT", System.currentTimeMillis());
         qe.setParaValue("KEYFLD", dataKeyfld);
         qe.setParaValue("YEAR", "2003");
         qe.setParaValue("SLSMN", datasalesp);
         qe.setParaValue("INV_REF", adrPhone.getText());
         qe.setParaValue("INV_REFNM", adrCustomerName.getText());
         qe.setParaValue("ADDR_AREA", ((String) adrArea.getSelectedItem()));
         qe.setParaValue("ADDR_TEL", adrOtherTel.getText());
         qe.setParaValue("HOME_ADDRESS", "");
         qe.setParaValue("WORK_ADDRESS", adrWorkAddress.getText());
         qe.setParaValue("SPEC_COMMENTS", "");
         qe.setParaValue("COMPLAINS", "");
         qe.setParaValue("ADDR_JEDDA", adrJedda);
         qe.setParaValue("ADDR_BLOCK", adrBlock);
         qe.setParaValue("ADDR_STREET", adrStreet);
         qe.setParaValue("ADDR_BLDG", adrBldg);
         qe.setParaValue("REFERENCE_INFORMATION", adrFloorNo.getText());
         qe.setParaValue("PAIDAMT2", sumCashAmt);
         qe.setParaValue("TOTQTY", sumQty);
         qe.setParaValue("ADD_CHARGE", sumAddAmt);
         qe.setParaValue("MEMO", txtDiscDescr);
         qe.setParaValue("CTG", txtReasonCancel);
         qe.execute();

         QueryExe qe2 = new QueryExe("update_pos_customer( :KEYFLD );"
         + "delete from POSPAYMENTS where vou_keyfld= :KEYFLD ;"
         + "end; ", dbConnection);

         qe2.setParaValue("keyfld", dataKeyfld);
         qe2.execute();

         QueryExe qe3 = new QueryExe("BEGIN INSERT INTO POSPUR2("
         + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
         + "INVOICE_CODE, TYPE, ITEMPOS, REFER, STRA, "
         + "PRICE, PACK, PACKD, UNITD, DAT, QTY, "
         + "PKQTY, ALLQTY, PRD_DATE, EXP_DATE, YEAR, KEYFLD, QTYIN, QTYOUT,creatdt,DISC_AMT_GROSS,SIZE_OF_DESCR) VALUES ("
         + ":PERIODCODE, :LOCATION_CODE, :INVOICE_NO, "
         + ":INVOICE_CODE, :TYPE, :ITEMPOS, :REFER, :STRA, "
         + ":PRICE, :PACK, :PACKD, :UNITD, :DAT, :QTY, "
         + ":PKQTY, :ALLQTY, :PRD_DATE, :EXP_DATE, :YEAR, :KEYFLD, :QTYIN, :QTYOUT,:creatdt,:DISC_AMT_GROSS,:SIZE_OF_DESCR ); "
         + posinout + "(?,?,?);"
         + "update items set usecounts=usecounts+1 where reference=? ;"
         + "end;", dbConnection);
         qe.parse();
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

         qe.close();
         qe2.close();
         qe3.close;
         dbConnection.commit();
         refresh_active_tables();
         } catch (SQLException ex) {
         ex.printStackTrace();
         dbConnection.rollback();
         }
         */
    }

    private void setColsProperties(localTableModel itemd) {
        XTableColumnModel colmod = ((XTableColumnModel) jTable1.getColumnModel());
        for (int colx = 0; colx
                < colmod.getColumnCount(false); colx++) {
            String fn = (colx >= itemd.getVisbleQrycols().size() ? "" : itemd.getVisbleQrycols().get(colx).getColname());
            qryColumn qc = itemd.getVisbleQrycols().get(colx);
            int width = Integer.valueOf((parentJF.getMapVars().get("sale_col_" + fn + "_width") != null
                    ? parentJF.getMapVars().get("sale_col_" + fn + "_width") : "0"));
            String title = (parentJF.getMapVars().get("sale_col_" + fn + "_title") != null
                    ? parentJF.getMapVars().get("sale_col_" + fn + "_title") : itemd.getColByName(fn).getTitle());
            String t1 = (parentJF.getMapVars().get("sale_col_" + fn + "_align") != null
                    ? parentJF.getMapVars().get("sale_col_" + fn + "_align") : "CENTER");
            String fmt = (parentJF.getMapVars().get("sale_col_" + fn + "_number_format") != null
                    ? parentJF.getMapVars().get("sale_col_" + fn + "_number_format") : "");
            String dtfmt = (parentJF.getMapVars().get("sale_col_" + fn + "_date_format") != null
                    ? parentJF.getMapVars().get("sale_col_" + fn + "_date_format") : "");
            if (width > 0) {
                colmod.setColumnVisible(colmod.getColumn(colx, false), true);
                colmod.getColumn(colx, false).setCellRenderer(new ColorRenderer(true));
                colmod.getColumn(colx, false).setPreferredWidth(width);
                colmod.getColumn(colx, false).setWidth(width);
                qc.setDisplaySize(width);
                qc.setTitle(title);
                qc.setAlignmnet(utils.indexOf(strAlign, t1));
                qc.setNumberFormat(fmt);
                qc.setDateFormat(dtfmt);
            } else {
                colmod.setColumnVisible(colmod.getColumn(colx, false), false);
            }

        }
    }

    private void addUpdatePrice() {
        try {
            addUpdate(txtItemCode.getText(), txtCategory.getText(), 0, Double.valueOf(txtItemPrice.getText()));
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    public wzReturnDlg wr = new wzReturnDlg(parentJF, true);

    public void showWizard() {
        wr.setParentJf(parentJF);
    }

    public class JTableButtonMouseListener extends MouseAdapter {

        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mousePressed(e);
            mouseClickedX(e);
        }

        //@Override
        public void mouseClickedX(MouseEvent e) {
            super.mouseClicked(e);
            //int column = table.getColumnModel().getColumnIndexAtX(e.getX());
            //int row = e.getY() / table.getRowHeight();

            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof dataCell) {
                    ((dataCell) value).setDisplay("<font color=\"red\"><b>" + ((dataCell) value).getDisplay() + "</b></font");
                    actformed((dataCell) value);
                    //((dataCell) value).setDisplay("<font color=\"red\">" + ((dataCell) value).getDisplay() + "</font");
                    boolean fnd = false;
                    for (int i = 0; (i < selGrpItems.getRows().size() && fnd == false); i++) {
                        Row r = selGrpItems.getRows().get(i);
                        for (int j = 0; j < r.lst.size(); j++) {
                            if (r.lst.get(j).getValue().toString().equals(((dataCell) value).getValue().toString())) {
                                r.lst.get(j).setDisplay("<font color=\"red\"><b>" + r.lst.get(j).getDisplay() + "</b></font");
                                fnd = true;
                                break;
                            }
                        }
                    }
                    if (table == groupTable) {
                        lbl_itemSel.setText("<html>" + ((dataCell) value).getDisplay() + "</html>");
                    }
                    itemTable.updateUI();
                    groupTable.updateUI();
                }
            }
            ;
        }
    }

    public void updateDatas() throws Exception {
        dataInv_date = sf.parse(txtInvoiceDate.getText());
        dataInvoiceNo
                = Integer.valueOf(txtInvoiceNo.getText());
        dataLocationCode
                = parentJF.getMapVars().get("DEFAULT_LOCATION");
        dataStore
                = Integer.valueOf(parentJF.getMapVars().get("DEFAULT_STORE"));
        dataInvType
                = Double.valueOf(((dataCell) jComboBox1.getModel().getSelectedItem()).getValue().toString());
        datasalesp
                = ((dataCell) listDriver.getModel().getSelectedItem()).getValue().toString();
    }

    public double save_data() throws Exception {
        if (cmdTables.isSelected()) {
            RTablesCanvas rtc = (RTablesCanvas) canvas;
            if (rtc.st == null) {
                throw new Exception("Table not selected !");
            }
            if (itemdetails_tbl.getRowCount() <= 0) {
                throw new Exception("No Items found !");
            }
        }

        if (cmdDelivery.isSelected()) {
            if (itemdetails_dlv.getRowCount() <= 0) {
                throw new Exception("No Items found ! ");
            }
        }

        if (cmdTakeAway.isSelected()) {
            return save_data_ta(itemdetails, true);
        }
        if (cmdTables.isSelected()) {
            return save_data_tbl(true);
        }

        if (cmdDelivery.isSelected()) {
            return save_data_dlv(true);
        }

        return -1;
    }

    private double save_data_dlv(boolean ack) throws Exception {
        if (!is_valid_to_save()) {
            return -1;
        }

        if (tbl_delivery.getSelectedRow() < 0) {
            throw new Exception("Must select delivery !");
        }
        if (itemdetails_dlv.getRows().size() <= 0) {
            JOptionPane.showMessageDialog(parentJF, "No sales data found");
            throw new Exception("No items data found");
        }

        sumPaidAmtThis = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumPaidAmt = sumPaidAmtThis + sumAdvanceAmt;
        dataDlKeyfld = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "KEYFLD").toString());
        dataDlvSales = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "SLSMN").toString());
        dataBkInvoiceNo = Double.valueOf(delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "B_NO").toString());
        dataBkInvoiceDate = new Date(((Timestamp) delivery_data.getFieldValue(tbl_delivery.getSelectedRow(), "B_DATE")).getTime());

        try {
            dbConnection.setAutoCommit(false);
            utils.execSql("delete from pos_onpur2 where keyfld = '" + dataDlKeyfld + "'", dbConnection);
            QueryExe qe2 = new QueryExe("BEGIN  "
                    + "INSERT INTO POS_ONPUR2(KEYFLD, LOCATION_CODE, B_NO, B_KIND, "
                    + " B_DATE, REFER, PRICE, PACKD, UNITD, PACK, "
                    + " PRD_DATE, EXP_DATE, QTY, PKQTY, ALLQTY, FLAG, ITEMPOS, CTG) VALUES "
                    + " ( :KEYFLD, :LOCATION_CODE, :B_NO, :B_KIND, :B_DATE, :REFER,"
                    + " :PRICE, :PACKD, :UNITD, :PACK, "
                    + " :PRD_DATE, :EXP_DATE, :QTY, :PKQTY, :ALLQTY, :FLAG, :ITEMPOS, :CTG ); "
                    + " update pos_onpur1 set inv_amt=:GROSS_AMT , disc_amt=:DISC_AMT where keyfld= :KEYFLD ;  END; ", dbConnection);
            qe2.parse();
            for (int i = 0; i < itemdetails_dlv.getRows().size(); i++) {
                PreparedStatement ps_dt = dbConnection.prepareStatement("select prd_dt,exp_dt from items where reference='"
                        + itemdetails_dlv.getFieldValue(i, "REFER") + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_dt = ps_dt.executeQuery();
                rs_dt.first();

                qe2.setParaValue("KEYFLD", dataDlKeyfld);
                qe2.setParaValue("LOCATION_CODE", dataLocationCode);
                qe2.setParaValue("B_NO", dataBkInvoiceNo);
                qe2.setParaValue("B_KIND", "DELIVERY");
                qe2.setParaValue("B_DATE", dataBkInvoiceDate);
                qe2.setParaValue("REFER", itemdetails_dlv.getFieldValue(i, "REFER"));
                qe2.setParaValue("PRICE", itemdetails_dlv.getFieldValue(i, "PRICE"));
                qe2.setParaValue("PACKD", itemdetails_dlv.getFieldValue(i, "PACKD"));
                qe2.setParaValue("UNITD", itemdetails_dlv.getFieldValue(i, "UNITD"));
                qe2.setParaValue("PACK", itemdetails_dlv.getFieldValue(i, "PACK"));
                qe2.setParaValue("PRD_DATE", rs_dt.getTimestamp(1));
                qe2.setParaValue("EXP_DATE", rs_dt.getTimestamp(2));
                qe2.setParaValue("QTY", 0);
                qe2.setParaValue("PKQTY", itemdetails_dlv.getFieldValue(i, "PKQTY"));
                qe2.setParaValue("ALLQTY", itemdetails_dlv.getFieldValue(i, "ALLQTY"));
                qe2.setParaValue("FLAG", 1);
                qe2.setParaValue("ITEMPOS", i + 1);
                qe2.setParaValue("CTG", itemdetails_dlv.getFieldValue(i, "SIZE_OF_DESCR"));
                qe2.setParaValue("GROSS_AMT", sumGrossAmt);
                qe2.setParaValue("DISC_AMT", sumDiscountAmt);
                qe2.execute(false);
                rs_dt.close();

            }

            qe2.close();
            dbConnection.commit();
            changeFromQuery = false;
            if (ack) {
                ShowMessageFrame sw = new ShowMessageFrame("Saved Successfully", 18, 2000);
            }
            onChangeCashier(false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            ex.printStackTrace();
            dbConnection.rollback();
        }
        return -1;
    }

    public double save_data_tbl(boolean acknowledge) throws Exception {
        if (!is_valid_to_save()) {
            return -1;
        }

        RTablesCanvas rtc = (RTablesCanvas) canvas;
        if (rtc.st == null) {
            throw new Exception("Table must select !");
        }
        if (itemdetails_tbl.getRows().size() <= 0) {
            JOptionPane.showMessageDialog(parentJF, "No sales data found");
            throw new Exception("No items data found");
        }
        sumPaidAmt = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);

        try {
            dbConnection.setAutoCommit(false);
            QueryExe qe = new QueryExe(" begin "
                    + "delete from pos_onpur1 where keyfld= :KEYFLD ; "
                    + "delete FROM POS_ONPUR2 WHERE KEYFLD= :KEYFLD ;"
                    + " insert into pos_onpur1(KEYFLD, LOCATION_CODE, B_KIND, B_NO, B_DATE, B_DATETIME,"
                    + " DELIVERY_DATETIME, CUST_REFERENCE, TABLE_CODE,"
                    + " FLAG, CLOSING_TIME, INV_AMT, DISC_AMT, SLSMN, CUST_COUNTS) VALUES "
                    + "(:KEYFLD, :LOCATION_CODE, :B_KIND, :B_NO, :B_DATE, :B_DATETIME,"
                    + ":DELIVERY_DATETIME, :CUST_REFERENCE, :TABLE_CODE,"
                    + " :FLAG, :CLOSING_TIME, :INV_AMT, :DISC_AMT, :SLSMN, :CUST_COUNTS);end;", dbConnection);
            QueryExe qe2 = new QueryExe("INSERT INTO POS_ONPUR2(KEYFLD, LOCATION_CODE, B_NO, B_KIND, "
                    + " B_DATE, REFER, PRICE, PACKD, UNITD, PACK, "
                    + " PRD_DATE, EXP_DATE, QTY, PKQTY, ALLQTY, FLAG, ITEMPOS, CTG) VALUES "
                    + " (:KEYFLD, :LOCATION_CODE, :B_NO, :B_KIND, :B_DATE, :REFER,"
                    + " :PRICE, :PACKD, :UNITD, :PACK, "
                    + " :PRD_DATE, :EXP_DATE, :QTY, :PKQTY, :ALLQTY, :FLAG, :ITEMPOS, :CTG ) ", dbConnection);

            if (rtc.st.active_keyfld == -1) {
                PreparedStatement psk = dbConnection.prepareStatement("select nvl(max(keyfld),0)+1 keyfld ,nvl(max(b_no),0)+1 b_no from pos_onpur1",
                        ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rsk = psk.executeQuery();
                if (rsk != null && rsk.first()) {
                    rtc.st.active_keyfld = rsk.getDouble("keyfld");
                    dataBkInvoiceNo = rsk.getDouble("b_no");
                    rsk.close();
                }
            }

            dataBkKeyfld = rtc.st.active_keyfld;
            qe.setParaValue("KEYFLD", rtc.st.active_keyfld);
            qe.setParaValue("LOCATION_CODE", dataLocationCode);
            qe.setParaValue("B_KIND", "TABLE");
            qe.setParaValue("B_NO", dataBkInvoiceNo);
            qe.setParaValue("B_DATE", dataBkInvoiceDate);
            qe.setParaValue("B_DATETIME", dataBkInvoiceDate);
            qe.setParaValue("DELIVERY_DATETIME", dataBkInvoiceDate);
            qe.setParaValue("CUST_REFERENCE", null);
            qe.setParaValue("TABLE_CODE", rtc.st.Code);
            qe.setParaValue("FLAG", 1);
            qe.setParaValue("CLOSING_TIME", null);
            qe.setParaValue("INV_AMT", sumGrossAmt);
            qe.setParaValue("DISC_AMT", 0);
            qe.setParaValue("SLSMN", null);
            qe.setParaValue("CUST_COUNTS", 1);
            qe.execute();
            qe.close();

            qe2.parse();
            for (int i = 0; i < itemdetails_tbl.getRows().size(); i++) {
                PreparedStatement ps_dt = dbConnection.prepareStatement("select prd_dt,exp_dt from items where reference='" + itemdetails_tbl.getFieldValue(i, "REFER") + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs_dt = ps_dt.executeQuery();
                rs_dt.first();
                qe2.setParaValue("KEYFLD", dataBkKeyfld);
                qe2.setParaValue("LOCATION_CODE", dataLocationCode);
                qe2.setParaValue("B_NO", dataBkInvoiceNo);
                qe2.setParaValue("B_KIND", "TABLE");
                qe2.setParaValue("B_DATE", dataBkInvoiceDate);
                qe2.setParaValue("REFER", itemdetails_tbl.getFieldValue(i, "REFER"));
                qe2.setParaValue("PRICE", itemdetails_tbl.getFieldValue(i, "PRICE"));
                qe2.setParaValue("PACKD", itemdetails_tbl.getFieldValue(i, "PACKD"));
                qe2.setParaValue("UNITD", itemdetails_tbl.getFieldValue(i, "UNITD"));
                qe2.setParaValue("PACK", itemdetails_tbl.getFieldValue(i, "PACK"));
                qe2.setParaValue("PRD_DATE", rs_dt.getTimestamp(1));
                qe2.setParaValue("EXP_DATE", rs_dt.getTimestamp(2));
                qe2.setParaValue("QTY", 0);
                qe2.setParaValue("PKQTY", itemdetails_tbl.getFieldValue(i, "PKQTY"));
                qe2.setParaValue("ALLQTY", itemdetails_tbl.getFieldValue(i, "ALLQTY"));
                qe2.setParaValue("FLAG", 1);
                qe2.setParaValue("ITEMPOS", i + 1);
                qe2.setParaValue("CTG", itemdetails_tbl.getFieldValue(i, "SIZE_OF_DESCR"));
                qe2.execute(false);
                rs_dt.close();
            }
            qe2.close();
            dbConnection.commit();
            refresh_active_tables();
            changeFromQuery = false;
            rtc.updateUI();
            if (acknowledge) {
                ShowMessageFrame sw = new ShowMessageFrame("Saved successfully !", 20, 2000);
            }
            return dataBkKeyfld;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            ex.printStackTrace();
            dbConnection.rollback();

        }
        return 0;
    }

    public double save_data_ta(localTableModel t, boolean do_commit) throws Exception {
        if (!is_valid_to_save()) {
            return -1;
        }

        if (t.getRows().size() <= 0) {
            JOptionPane.showMessageDialog(parentJF, "No sales data found");
            throw new Exception("No items data found");
        }

        sumPaidAmtThis = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
        sumPaidAmt = sumPaidAmtThis + sumAdvanceAmt;
        if (sumPaidAmtThis <= 0) {
            int f = payRows.locate("NO",
                    ((dataCell) jComboBox1.getSelectedItem()).getValue().toString(), localTableModel.FIND_EXACT);
            if (f >= 0) {
                payTable.changeSelection(f, 2, false, false);
                cmdPayHereAll.doClick();
            }
            sumPaidAmtThis = payRows.getSummaryOf("AMOUNT", localTableModel.SUMMARY_SUM);
            sumPaidAmt = sumPaidAmtThis + sumAdvanceAmt;
        }
        /*
         if (utils.roundToDecimals(sumPaidAmt, 3) != utils.roundToDecimals(sumNetAmt, 3)) {
         payTable.changeSelection(0, 2, false, false);
         cmdPayHereAll.doClick();
         }

         if (utils.roundToDecimals(sumPaidAmt, 3) != utils.roundToDecimals(sumNetAmt, 3)) {
         JOptionPane.showMessageDialog(parentJF, "Oops ! paid amount is not valid, not equal to net amount");
         throw new Exception("Paid amount is not valid");
         }
         */
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
                ps_up
                        = dbConnection.prepareStatement("begin update stori set pos_out=pos_out-? where refer=? and strno=?; "
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
            /*
             if (datasalesp.length() == 0 && utils.roundToDecimals(sumCashAmt, 3) < utils.roundToDecimals(sumNetAmt, 3) && dataInvoiceCode == 30) {
             sumCashAmt = sumNetAmt;
             sumTxtCashAmt.setText(decimalformat.format(sumNetAmt));
             sumTxtCashAmt.requestFocus();
             throw new Exception("Cash given value is less than  " + decimalformat.format(sumNetAmt));
             }
             * 
             */
            /* PERIODCODE, LOCATION_CODE, INVOICE_NO,
             INVOICE_CODE, TYPE, INVOICE_DATE,
             STRA, USERNAME, INV_AMT,
             DISC_AMT, CREATDT, KEYFLD
             */

            ps_up
                    = dbConnection.prepareStatement("begin "
                            + "insert into pospur1("
                            + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
                            + "INVOICE_CODE, TYPE, INVOICE_DATE,"
                            + "STRA, USERNAME, INV_AMT, "
                            + "DISC_AMT, CREATDT, KEYFLD,YEAR,SLSMN, "
                            + "INV_REF,INV_REFNM,ADDR_AREA,"
                            + "ADDR_TEL ,HOME_ADDRESS,WORK_ADDRESS,SPEC_COMMENTS,"
                            + "COMPLAINS,addr_jedda,addr_block,addr_street,"
                            + "addr_bldg,reference_information,paidamt2,totqty,add_charge,MEMO,CTG,issue_keyfld"
                            + ",paidamt2_prior,paidamt2_this )values ("
                            + "?,?,?,?,?,?,?,?,?,?,sysdate,?,'2003',?,"
                            + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?"
                            + ",?,?);"
                            + ""
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
            ps_up.setString(30, txtReasonCancel.getText());
            ps_up.setString(31, varSaleKeyfld);
            ps_up.setDouble(32, sumAdvanceAmt);
            ps_up.setDouble(33, sumPaidAmtThis);
            ps_up.setDouble(34, dataKeyfld);
            ps_up.executeUpdate();
            ps_up.close();

            double itm_gros_disc = 0, prc = 0, allq = 0, pk = 0;
            ps_up = dbConnection.prepareStatement("BEGIN INSERT INTO POSPUR2("
                    + "PERIODCODE, LOCATION_CODE, INVOICE_NO, "
                    + "INVOICE_CODE, TYPE, ITEMPOS, REFER, STRA, "
                    + "PRICE, PACK, PACKD, UNITD, DAT, QTY, "
                    + "PKQTY, ALLQTY, PRD_DATE, EXP_DATE, YEAR, KEYFLD, QTYIN, QTYOUT,creatdt,"
                    + "DISC_AMT_GROSS,SIZE_OF_DESCR,RECIEVED_KEYFLD) VALUES ("
                    + "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?); "
                    + posinout + "(?,?,?);"
                    + "update items set usecounts=usecounts+1 where reference=? ;"
                    + "end;");

            for (int i = 0; i
                    < t.getRows().size(); i++) {
                Row row = t.getRows().get(i);
                t.setCursorNo(i);
                double qtin = 0, qtout = 0;
                if (dataInvoiceCode == 30) {
                    qtout = Double.valueOf(t.getFieldValue(i, "ALLQTY").toString());
                }

                if (dataInvoiceCode == 20) {
                    qtin = Double.valueOf(t.getFieldValue(i, "ALLQTY").toString());
                }
                itm_gros_disc = 0;
                if (sumGrossAmt > 0 && sumDiscountAmt > 0) {
                    prc = Double.valueOf(t.getFieldValue(i, "PRICE").toString());
                    allq = Double.valueOf(t.getFieldValue(i, "ALLQTY").toString());
                    pk = Double.valueOf(t.getFieldValue(i, "PACK").toString());
                    itm_gros_disc = ((sumDiscountAmt / sumGrossAmt) * (((prc) / pk) * allq)) / (allq / pk);
                }

                double tot_rtn = 0;
                double tot_sal = 0;

                if (!varSaleKeyfld.isEmpty() && dataInvoiceCode == 20) {
                    tot_rtn = Double.valueOf(utils.getSqlValue("select nvl(sum(allqty),0) from pospur2 "
                            + " where invoice_code=20 and RECIEVED_KEYFLD='" + varSaleKeyfld + "' and "
                            + " refer='" + t.getFieldValue(i, "REFER").toString() + "'", dbConnection));
                    tot_rtn = tot_rtn + Double.valueOf(t.getFieldValue(i, "ALLQTY").toString());
                    tot_sal = Double.valueOf(utils.getSqlValue(" select sum(allqty) from pospur2 "
                            + " where refer='" + t.getFieldValue(i, "REFER").toString()
                            + "' and keyfld='" + varSaleKeyfld + "'", dbConnection));
                    if (tot_rtn > tot_sal) {
                        throw new SQLException("  Ref # " + t.getFieldValue(i, "REFER").toString() + " - CAN NOT INCREASE RETURN QTY # " + tot_rtn);
                    }

                    utils.execSql("update pospur2 set has_recieved=" + tot_rtn + " where "
                            + " refer='" + t.getFieldValue(i, "REFER").toString() + "' and "
                            + " keyfld=" + varSaleKeyfld, dbConnection);
                }
                ps_up.setString(1, parentJF.getMapVars().get("CURRENT_PERIOD"));
                ps_up.setString(2, dataLocationCode);
                ps_up.setString(3, txtInvoiceNo.getText());
                ps_up.setInt(4, dataInvoiceCode);
                ps_up.setDouble(5, dataInvType);
                ps_up.setInt(6, Integer.valueOf(t.getFieldValue(i, "ITEMPOS").toString()));
                ps_up.setString(7, t.getFieldValue(i, "REFER").toString());
                ps_up.setInt(8, dataStore);
                ps_up.setDouble(9, Double.valueOf(t.getFieldValue(i, "PRICE").toString()));
                ps_up.setDouble(10, Double.valueOf(t.getFieldValue(i, "PACK").toString()));
                ps_up.setString(11, (t.getFieldValue(i, "PACKD").toString()));
                ps_up.setString(12, (t.getFieldValue(i, "UNITD").toString()));
                ps_up.setDate(13, new java.sql.Date(dataInv_date.getTime()));
                ps_up.setDouble(14, Double.valueOf(t.getFieldValue(i, "QTY").toString()));
                ps_up.setDouble(15, Double.valueOf(t.getFieldValue(i, "PKQTY").toString()));
                ps_up.setDouble(16, Double.valueOf(t.getFieldValue(i, "ALLQTY").toString()));
                ps_up.setTimestamp(17, (Timestamp) t.getFieldValue(i, "PRD_DATE"));
                ps_up.setTimestamp(18, (Timestamp) t.getFieldValue(i, "EXP_DATE"));
                ps_up.setString(19, "2003");
                ps_up.setDouble(20, dataKeyfld);
                ps_up.setDouble(21, qtin);
                ps_up.setDouble(22, qtout);
                ps_up.setDouble(23, itm_gros_disc);
                ps_up.setString(24, t.getFieldValue(i, "SIZE_OF_DESCR").toString());
                ps_up.setString(25, varSaleKeyfld);
                ps_up.setString(26, t.getFieldValue(i, "REFER").toString());
                ps_up.setInt(27, dataStore);
                ps_up.setDouble(28, Double.valueOf(t.getFieldValue(i, "ALLQTY").toString()));
                ps_up.setString(29, t.getFieldValue(i, "REFER").toString());
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
                        throw new Exception("Call Administrator for account  payment for KWD : "
                                + row.lst.get(2).getValue().toString() + " for " + row.lst.get(1).getValue());
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
            /*
             double selpay = (Double.valueOf(((dataCell) jComboBox1.getSelectedItem()).getValue().toString()));
             if (sumCashAmt > 0 && varCashType == selpay) {
             ShowMessageFrame sf = new ShowMessageFrame("Change amount : " + decimalformat.format(sumCashAmt - sumNetAmt), 20, 10000);
             }
             */
            QRYSTR = "";
            if (do_commit) {
                dbConnection.commit();
                load_data();
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJF, ex.getMessage());
            try {
                dbConnection.rollback();
            } catch (SQLException ex1) {
            }
            throw ex;
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
        qc
                = new qryColumn(1, "ITEM_NAME");
        ld.getSlov().getLctb().getQrycols().add(qc);
        qc.setDisplaySize(300);
        ld.getSlov().getLctb().getVisbleQrycols().add(qc);
        qc
                = new qryColumn(2, "PRICE");
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
                lastitem = row.lst.get(0).getValue().toString();
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

    public void fill_item_list(String ctg, selectListView slov) {
        slov.getLctb().clearALl();
        qryColumn qc = new qryColumn(0, "ITEM_CODE");
        slov.getLctb().getQrycols().add(qc);
        slov.getLctb().getVisbleQrycols().add(qc);
        qc
                = new qryColumn(1, "ITEM_NAME");
        slov.getLctb().getQrycols().add(qc);
        qc.setDisplaySize(300);
        slov.getLctb().getVisbleQrycols().add(qc);
        qc
                = new qryColumn(2, "PRICE");
        slov.getLctb().getQrycols().add(qc);
        slov.getLctb().getVisbleQrycols().add(qc);

        qc
                = new qryColumn(3, "BARCODE");
        slov.getLctb().getQrycols().add(qc);

        //slov.getLctb().getVisbleQrycols().add(qc);
        String lastitem = "";
        String ctgp = "";
        int fvl = utils.find_value_in_row(0, ctg, itemsRows);
        if (fvl > -1) {
            ctgp = itemsRows.get(fvl).lst.get(13).getValue().toString();
        }

        for (int i = 0; i
                < itemsRows.size(); i++) {
            Row row = itemsRows.get(i);
            if (!row.lst.get(0).getValue().toString().equals(lastitem)) {
                if (!ctg.isEmpty() && !row.lst.get(13).getValue().toString().startsWith(ctgp)) {
                    continue;
                }
                Row nw = new Row(4);
                nw.lst.set(0, row.lst.get(0));
                nw.lst.set(1, row.lst.get(1));
                nw.lst.set(2, row.lst.get(2));
                nw.lst.set(3, row.lst.get(5));
                slov.getLctb().getRows().add(nw);
                lastitem = row.lst.get(0).getValue().toString();
            }
        }
        slov.getLctb().setMasterRowsAsRows();

    }
}
