/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * dinePanel.java
 *
 * Created on Jan 24, 2014, 5:41:36 AM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.model.DBClass;
import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import com.generic.utils.ColorUtility;
import com.generic.utils.Parameter;
import com.generic.utils.RTableTriggers;
import com.generic.utils.RTables;
import com.generic.utils.RTablesCanvas;
import com.generic.utils.RTablesCanvas.*;
import com.generic.utils.XTableColumnModel;
import com.generic.utils.utils;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author Yusuf
 */
public class dinePanel extends javax.swing.JPanel implements RTableTriggers {

    public dinePanel() {
        initComponents();
        utils.setupFormTextBoxes(this);
        jTable2.setColumnModel(new XTableColumnModel());
        jTable2.setBackground(Color.GREEN);
        jTable2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTable2.setRowHeight(30);


    }

    public void setUpItemDetails() {
        try {
            jTable2.setModel(new javax.swing.table.DefaultTableModel());
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
                    + " from pospur2,items where items.reference=pospur2.refer and pospur2.keyfld='"
                    + QRYSTR + "' order by ITEMpos", new String[]{"ITEMPOS", "SIZE_OF_DESCR", "REFER", "DESCR", "PRICE", "PKQTY", "AMOUNT"});
            itemdetails.getColByName("DESCR").setDatabaseCol(false);
            itemdetails.getColByName("SIZE_OF_DESCR").setTitle("CATEGORY");
            itemdetails.getColByName("AMOUNT").setDatabaseCol(false);
            itemdetails.getColByName("ITEMPOS").setAlignmnet(JLabel.CENTER);
            itemdetails.getColByName("AMOUNT").setColor(Color.YELLOW);
            itemdetails.getColByName("PKQTY").setTitle("Pack Qty");
            jTable2.setModel(itemdetails);
            jTable2.getColumnModel().setColumnSelectionAllowed(false);
            jTable2.getTableHeader().setReorderingAllowed(false);
            
            for (int colx = 0; colx
                    < jTable2.getModel().getColumnCount(); colx++) {
                jTable2.getColumnModel().getColumn(colx).setCellRenderer(new ColorRenderer(true));
            }

            utils.applyColumn(parentJf, itemdetails.getColByName("ITEMPOS"), jTable2, 0);
            utils.applyColumn(parentJf, itemdetails.getColByName("SIZE_OF_DESCR"), jTable2, 1);
            utils.applyColumn(parentJf, itemdetails.getColByName("REFER"), jTable2, 2);
            utils.applyColumn(parentJf, itemdetails.getColByName("DESCR"), jTable2, 3);
            utils.applyColumn(parentJf, itemdetails.getColByName("PRICE"), jTable2, 4);
            utils.applyColumn(parentJf, itemdetails.getColByName("PKQTY"), jTable2, 5);
            utils.applyColumn(parentJf, itemdetails.getColByName("AMOUNT"), jTable2, 6);
            itemdetails.getColByName("AMOUNT").setNumberFormat(decf.toPattern());
            itemdetails.getColByName("PRICE").setNumberFormat(decf.toPattern());

            jTable2.revalidate();
            jTable2.updateUI();
            
        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fill_tables() {
        RTablesCanvas rtc = (RTablesCanvas) canvas1;
        rtc.listTables.clear();
        rtc.st = null;
        pnl_paras.removeAll();
        pnl_paras.updateUI();
        
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

    public void addAllSections() {
        ((DefaultComboBoxModel) txtSection.getModel()).removeAllElements();
        PreparedStatement ps;
        try {
            ps = dbConnection.prepareStatement("select code,section_name from pos_sections  order by code",
                    ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rsx = ps.executeQuery();
            rsx.beforeFirst();
            ((DefaultComboBoxModel) txtSection.getModel()).addElement(new dataCell("...", "##CREATE"));
            while (rsx.next()) {
                ((DefaultComboBoxModel) txtSection.getModel()).addElement(new dataCell(rsx.getString("SECTION_NAME") + "-" + rsx.getString("CODE"), rsx.getString("CODE")));
            }
            ps.close();
            if (((DefaultComboBoxModel) txtSection.getModel()).getSize() <= 0) {
                JOptionPane.showMessageDialog(parentJf, "Must Create section", "", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void customIntialize(MainFrame mf) {
        if (dbc == null) {
            try {
                parentJf = mf;
                dbc = tbModel.createDBClassFromConnection(parentJf.getDbConneciton());
                dbConnection = dbc.getDbConnection();
                itemdetails.createDBClassFromConnection(dbConnection);
                sdf = new SimpleDateFormat(parentJf.getMapVars().get("short_date_format"));
                decf = new DecimalFormat(parentJf.getMapVars().get("money_number"));


                if (parentJf.getSp().itemsRows == null) {
                    parentJf.getCmdSales().doClick();
                    parentJf.getCmdDineIn().doClick();
                    parentJf.getDine().updateUI();
                }
                if (parentJf.getSpr().itemsRows == null) {
                    parentJf.getCmdReturn().doClick();
                    parentJf.getCmdDineIn().doClick();
                    parentJf.getDine().updateUI();
                }

                addAllSections();
                varSectionCode = "##CREATE";
                txtSection.setSelectedIndex(0);
                fill_tables();
                on_change_mode();
                setUpItemDetails();

            } catch (SQLException ex) {
                Logger.getLogger(closeSalesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtSection = new javax.swing.JComboBox();
        cmdCreateSec = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtHeight = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtWidth = new javax.swing.JTextField();
        editTables = new javax.swing.JCheckBox();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        canvas1 = new RTablesCanvas();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnl_Prop = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        cmdSave1 = new javax.swing.JButton();
        cmdDelTable1 = new javax.swing.JButton();
        cmdCreateTable1 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        pnl_paras = new javax.swing.JPanel();
        pnl_items = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        cmdSave2 = new javax.swing.JButton();
        cmdDelTable2 = new javax.swing.JButton();
        cmdCreateTable2 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        splitGroupItem = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        groupTable = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();

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
        jScrollPane2.setViewportView(jTable1);

        txtSection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txtSection.setPreferredSize(new Dimension(100, 15));
        txtSection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSectionActionPerformed(evt);
            }
        });

        cmdCreateSec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/add.png"))); // NOI18N
        cmdCreateSec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreateSecActionPerformed(evt);
            }
        });

        jLabel2.setText("Height");

        txtHeight.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtHeight.setEnabled(false);

        jLabel3.setText("Width");

        txtWidth.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtWidth.setText("0");
        txtWidth.setEnabled(false);

        editTables.setText("DESIGN MODE");
        editTables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                editTablesMouseClicked(evt);
            }
        });
        editTables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editTablesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(txtSection, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmdCreateSec)
                .addGap(5, 5, 5)
                .addComponent(jLabel2)
                .addGap(5, 5, 5)
                .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(editTables, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdCreateSec, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSection, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel2))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editTables)
                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cmdCreateSec.getAccessibleContext().setAccessibleDescription("");

        jSplitPane2.setDividerLocation(550);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jSplitPane2.setRightComponent(jPanel4);

        canvas1.setBackground(new java.awt.Color(-16777216,true));
        canvas1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(-16777216,true)));

        javax.swing.GroupLayout canvas1Layout = new javax.swing.GroupLayout(canvas1);
        canvas1.setLayout(canvas1Layout);
        canvas1Layout.setHorizontalGroup(
            canvas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );
        canvas1Layout.setVerticalGroup(
            canvas1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 287, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(171, Short.MAX_VALUE))
        );

        canvas1.getAccessibleContext().setAccessibleParent(this);

        jScrollPane1.setViewportView(jPanel2);

        jSplitPane2.setLeftComponent(jScrollPane1);

        cmdSave1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/save.png"))); // NOI18N
        cmdSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSave1ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdSave1);

        cmdDelTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/remove.png"))); // NOI18N
        cmdDelTable1.setToolTipText("Delete Table");
        cmdDelTable1.setAlignmentY(0.0F);
        cmdDelTable1.setIconTextGap(2);
        cmdDelTable1.setMargin(null);
        cmdDelTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelTable1ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdDelTable1);

        cmdCreateTable1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/add.png"))); // NOI18N
        cmdCreateTable1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreateTable1ActionPerformed(evt);
            }
        });
        jPanel5.add(cmdCreateTable1);

        pnl_paras.setAutoscrolls(true);
        pnl_paras.setMinimumSize(new Dimension(200,200));
        pnl_paras.setLayout(new java.awt.GridBagLayout());
        jScrollPane3.setViewportView(pnl_paras);

        javax.swing.GroupLayout pnl_PropLayout = new javax.swing.GroupLayout(pnl_Prop);
        pnl_Prop.setLayout(pnl_PropLayout);
        pnl_PropLayout.setHorizontalGroup(
            pnl_PropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );
        pnl_PropLayout.setVerticalGroup(
            pnl_PropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_PropLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Properties", pnl_Prop);

        cmdSave2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/save.png"))); // NOI18N
        cmdSave2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSave2ActionPerformed(evt);
            }
        });
        jPanel6.add(cmdSave2);

        cmdDelTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/remove.png"))); // NOI18N
        cmdDelTable2.setToolTipText("Delete Table");
        cmdDelTable2.setAlignmentY(0.0F);
        cmdDelTable2.setIconTextGap(2);
        cmdDelTable2.setMargin(null);
        cmdDelTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDelTable2ActionPerformed(evt);
            }
        });
        jPanel6.add(cmdDelTable2);

        cmdCreateTable2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/add.png"))); // NOI18N
        cmdCreateTable2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdCreateTable2ActionPerformed(evt);
            }
        });
        jPanel6.add(cmdCreateTable2);

        jScrollPane4.setBackground(java.awt.Color.green);
        jScrollPane4.setMinimumSize(new java.awt.Dimension(0, 0));

        jTable2.setAutoCreateRowSorter(true);
        jTable2.setBackground(new java.awt.Color(204, 255, 204));
        jTable2.setFont(new java.awt.Font("Times New Roman", 0, 14));
        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable2.setMaximumSize(new java.awt.Dimension(32767, 32767));
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(jTable2);

        javax.swing.GroupLayout pnl_itemsLayout = new javax.swing.GroupLayout(pnl_items);
        pnl_items.setLayout(pnl_itemsLayout);
        pnl_itemsLayout.setHorizontalGroup(
            pnl_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
            .addGroup(pnl_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        );
        pnl_itemsLayout.setVerticalGroup(
            pnl_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_itemsLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(416, Short.MAX_VALUE))
            .addGroup(pnl_itemsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnl_itemsLayout.createSequentialGroup()
                    .addContainerGap(36, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jTabbedPane1.addTab("Items", pnl_items);

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
        jScrollPane5.setViewportView(groupTable);

        splitGroupItem.setTopComponent(jScrollPane5);

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
        jScrollPane6.setViewportView(itemTable);

        splitGroupItem.setRightComponent(jScrollPane6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("itemList", jPanel3);

        jSplitPane2.setRightComponent(jTabbedPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 884, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmdCreateSecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreateSecActionPerformed
        createSection c = null;
        if (txtSection.getSelectedIndex() == 0) {
            c = new createSection(parentJf, true, "");
        } else {
            c = new createSection(parentJf, true, ((dataCell) txtSection.getItemAt(txtSection.getSelectedIndex())).getValue().toString());
            //System.out.println(((dataCell) txtSection.getItemAt(txtSection.getSelectedIndex())).getValue().toString());
        }
        c.centerWindow();
        c.pack();
        c.setVisible(true);
        addAllSections();
        if (!c.QRYSES.isEmpty()) {
            txtSection.setSelectedItem(utils.findByValue(txtSection, c.last_code));
        }
    }//GEN-LAST:event_cmdCreateSecActionPerformed

    private void txtSectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSectionActionPerformed
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
                canvas1.repaint();
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
            canvas1.setPreferredSize(new Dimension(varWidth, varHeight));
            txtHeight.setText(varHeight + "");
            txtWidth.setText(varWidth + "");
            canvas1.requestFocus();
            canvas1.repaint();

            if (txtSection.getSelectedIndex() == 0) {
                cmdCreateSec.setText("Create Sec.");
            } else {
                cmdCreateSec.setText("Edit Sec.");
            }

        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSectionActionPerformed

    private void show_properties() {
        pnl_paras.removeAll();
        pnl_paras.updateUI();
        add_parameters();
        try {

            GridBagConstraints gc = new GridBagConstraints();
            int i = 0;
            for (Iterator<Parameter> it = listParas.iterator(); it.hasNext();) {
                final Parameter pm = it.next();
                String s = "";
                if (pm.getValue() != null) {
                    s = pm.getValue().toString();
                }
                final JLabel lb = new JLabel(pm.getDescription());

                gc.gridx = 0;
                gc.gridy = i;
                gc.insets = new Insets(5, 5, 5, 5);
                gc.gridwidth = 1;
                gc.weightx = 0.0;
                gc.fill = GridBagConstraints.HORIZONTAL;
                pnl_paras.add(lb, gc);

                if (!pm.getLovsql().isEmpty()) {
                    final JComboBox tf = new JComboBox();
                    pm.setUIObject(tf);
                    utils.fillComboBox(tf, null, pm.getLovsql(), dbc.getDbConnection());
                    gc.gridx = 1;
                    gc.gridy = i++;
                    gc.insets = new Insets(5, 5, 5, 5);
                    pnl_paras.add(tf, gc);
                    if (pm.getValue() != null) {
                        tf.setSelectedItem(utils.findByValue(tf, pm.getValue().toString()));
                    }
                    tf.addFocusListener(new FocusListener() {

                        public void focusGained(FocusEvent e) {
                        }

                        public void focusLost(FocusEvent e) {
                            pm.setValue(((dataCell) tf.getSelectedItem()).getValue().toString());
                            update_para_to_selected_table();
                        }
                    });
                    tf.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            pm.setValue(((dataCell) tf.getSelectedItem()).getValue().toString());
                            update_para_to_selected_table();
                        }
                    });

                } else {
                    final JTextField tf = new JTextField(s);
                    pm.setUIObject(tf);
                    tf.setColumns(20);
                    tf.addFocusListener(new FocusListener() {

                        public void focusGained(FocusEvent e) {
                        }

                        public void focusLost(FocusEvent e) {

                            if (pm.getValueType().equals(Parameter.DATA_TYPE_DATE)) {
                                SimpleDateFormat df = new SimpleDateFormat(utils.FORMAT_SHORT_DATE);

                                try {
                                    Date d = df.parse(tf.getText());
                                    tf.setText(df.format(d));
                                    pm.setValue(df.parse(tf.getText()));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(parentJf, ex);
                                    tf.requestFocus();
                                }
                            }
                            if (pm.getValueType().equals(Parameter.DATA_TYPE_NUMBER)) {
                                try {
                                    NumberFormat df = NumberFormat.getInstance();
                                    Number d = df.parse(tf.getText());
                                    tf.setText(df.format(d));
                                    pm.setValue(df.parse(tf.getText()));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(parentJf, ex);
                                    tf.requestFocus();
                                }
                            }
                            if (pm.getValueType().equals(Parameter.DATA_TYPE_STRING)) {
                                pm.setValue(tf.getText());
                            }
                            update_para_to_selected_table();
                        }
                    });


                    tf.addActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {

                            if (pm.getValueType().equals(Parameter.DATA_TYPE_DATE)) {
                                SimpleDateFormat df = new SimpleDateFormat(utils.FORMAT_SHORT_DATE);
                                try {
                                    Date d = df.parse(tf.getText());
                                    tf.setText(df.format(d));
                                    pm.setValue(df.parse(tf.getText()));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(parentJf, ex);
                                    tf.requestFocus();
                                }
                            }

                            if (pm.getValueType().equals(Parameter.DATA_TYPE_NUMBER)) {
                                try {
                                    NumberFormat df = NumberFormat.getInstance();
                                    Number d = df.parse(tf.getText());
                                    tf.setText(df.format(d));
                                    pm.setValue(df.parse(tf.getText()));
                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                    JOptionPane.showMessageDialog(parentJf, ex);
                                    tf.requestFocus();
                                }
                            }
                            if (pm.getValueType().equals(Parameter.DATA_TYPE_STRING)) {
                                pm.setValue(tf.getText());
                            }
                            update_para_to_selected_table();
                        }

                        ;
                    });

                    gc.gridx = 1;
                    gc.gridy = i++;
                    gc.insets = new Insets(5, 5, 5, 5);
                    pnl_paras.add(tf, gc);
                    if (pm.getValue() != null) {
                        if (pm.getValueType().equals(Parameter.DATA_TYPE_DATE)) {
                            SimpleDateFormat df = new SimpleDateFormat(utils.FORMAT_SHORT_DATE);
                            tf.setText(df.format((Date) pm.getValue()));
                        } else {
                            tf.setText(pm.getValue().toString());
                        }
                    }
                }
            }

            canvas1.repaint();
            pnl_paras.updateUI();
            if (!listParas.isEmpty()) {
                ((JTextField) findParameter("POS_X").getUIObject()).setEnabled(false);
                ((JTextField) findParameter("POS_Y").getUIObject()).setEnabled(false);
            }


        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void add_parameters() {
        listParas.clear();
        if (selectedTable == null) {
            return;
        }


        Parameter pm = new Parameter("TYPE_OF_GRAPHICS", "RECTANGLE");
        pm.setLovsql("select 'RECTANGLE','RECTANGLE' FROM DUAL UNION ALL SELECT 'CIRCLE','CIRCLE'  FROM DUAL ");
        pm.setDescription("Graphic");
        pm.setValue(selectedTable.TypeOfGraphics, true);
        listParas.add(pm);

        pm = new Parameter("CODE", "");
        pm.setDescription("Code");
        pm.setValue(selectedTable.Code, true);
        listParas.add(pm);

        pm = new Parameter("DESCR", "");
        pm.setDescription("Descr");
        pm.setValue(selectedTable.descr, true);
        listParas.add(pm);

        pm = new Parameter("BACK_COLOR", "BLUE");
        pm.setDescription("Back Color");
        pm.setValue(selectedTable.strBackColor, true);
        listParas.add(pm);

        pm = new Parameter("TEXT_COLOR", "BLACK");
        pm.setDescription("Text Color");
        pm.setValue(selectedTable.strTextColor.toString(), true);
        listParas.add(pm);

        pm = new Parameter("HEIGHT", "30");
        pm.setDescription("Height");
        pm.setValue(selectedTable.height, true);
        listParas.add(pm);

        pm = new Parameter("WIDTH", "30");
        pm.setDescription("Width");
        pm.setValue(selectedTable.width, true);
        listParas.add(pm);

        pm = new Parameter("POS_X", "0");
        pm.setDescription("Pos X");
        pm.setValue(selectedTable.pos_x, true);
        listParas.add(pm);

        pm = new Parameter("POS_Y", "0");
        pm.setDescription("Pos Y");
        pm.setValue(selectedTable.pos_y, true);
        listParas.add(pm);

        pm = new Parameter("BORDER_COLOR", "Gray");
        pm.setDescription("Border Color");
        pm.setValue(selectedTable.strBorderColor, true);
        listParas.add(pm);

        pm = new Parameter("CAN_SELECT", "Gray");
        pm.setLovsql("select 'Y','Y' FROM DUAL UNION ALL SELECT 'N','N'  FROM DUAL ");
        pm.setDescription("Selectable ");
        pm.setValue(selectedTable.canSelect, true);
        listParas.add(pm);

    }

    private void update_para_to_selected_table() {
        for (Iterator<Parameter> it = listParas.iterator(); it.hasNext();) {
            Parameter pm = it.next();
            if (pm.getName().equals("TYPE_OF_GRAPHICS")) {
                selectedTable.TypeOfGraphics = (pm.getValue()).toString();
            }
            if (pm.getName().equals("HEIGHT")) {
                selectedTable.height = Integer.valueOf(pm.getValue().toString());
                selectedTable.rect.height = Integer.valueOf(pm.getValue().toString());
            }
            if (pm.getName().equals("WIDTH")) {
                selectedTable.width = Integer.valueOf(pm.getValue().toString());
                selectedTable.rect.width = Integer.valueOf(pm.getValue().toString());
            }
            if (pm.getName().equals("POS_X")) {
                selectedTable.pos_x = Integer.valueOf(pm.getValue().toString());
                selectedTable.rect.x = Integer.valueOf(pm.getValue().toString());
            }
            if (pm.getName().equals("POS_Y")) {
                selectedTable.pos_y = Integer.valueOf(pm.getValue().toString());
                selectedTable.rect.y = Integer.valueOf(pm.getValue().toString());
            }

            if (pm.getName().equals("DESCR")) {
                selectedTable.descr = pm.getValue().toString();
            }
            if (pm.getName().equals("CAN_SELECT")) {
                selectedTable.canSelect = pm.getValue().toString();
            }


            if (pm.getName().equals("BACK_COLOR")) {
                selectedTable.backColor = ColorUtility.convertColor(pm.getValue().toString(),
                        Color.blue);
                selectedTable.strBackColor = pm.getValue().toString();
            }
            if (pm.getName().equals("TEXT_COLOR")) {
                selectedTable.textColor = ColorUtility.convertColor(pm.getValue().toString(),
                        Color.BLACK);
                selectedTable.strTextColor = pm.getValue().toString();
            }

            if (pm.getName().equals("BORDER_COLOR")) {
                selectedTable.borderColor = ColorUtility.convertColor(pm.getValue().toString(),
                        Color.GRAY);
                selectedTable.strBorderColor = pm.getValue().toString();
            }

            RTablesCanvas rtc = (RTablesCanvas) canvas1;
            rtc.repaint();
        }
    }

    public void onSelectionOfTable(RTables r) {
        selectedTable = r;
        show_properties();

    }

    private void save_table_data() {
        RTablesCanvas rtc = (RTablesCanvas) canvas1;
        try {
            dbConnection.setAutoCommit(false);
            utils.execSql("delete from pos_tables where section_code='" + varSectionCode + "' ", dbConnection);
            PreparedStatement ps = dbConnection.prepareStatement("insert into pos_tables (KEYFLD, TABLE_CODE, SECTION_CODE, DESCR"
                    + ", ORDER_POS, TYPE_OF_GRAPHICS, SIZE_RECT_HEIGHT, SIZE_RECT_LENGTH, "
                    + "SIZE_RADIUS, BACK_COLOR, TEXT_COLOR,POS_X,POS_Y,BORDER_COLOR,CAN_SELECT) values "
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            double kf = -1;

            for (Iterator<RTables> it = rtc.listTables.iterator(); it.hasNext();) {
                RTables rt = it.next();
                if (rt.keyfld < 0) {
                    rt.keyfld = Double.valueOf(utils.getSqlValue("select nvl(max(keyfld),0)+1 from pos_tables", dbConnection));
                }
                kf = rt.keyfld;
                ps.setDouble(1, kf);
                ps.setString(2, rt.Code);
                ps.setString(3, varSectionCode);
                ps.setString(4, rt.descr);
                ps.setDouble(5, kf);
                ps.setString(6, rt.TypeOfGraphics);
                ps.setInt(7, rt.height);
                ps.setInt(8, rt.width);
                ps.setInt(9, 0);
                ps.setString(10, rt.strBackColor);
                ps.setString(11, rt.strTextColor);
                ps.setDouble(12, rt.rect.x);
                ps.setDouble(13, rt.rect.y);
                ps.setString(14, rt.strBorderColor);
                ps.setString(15, rt.canSelect);
                ps.execute();
            }
            dbConnection.commit();
            JOptionPane.showMessageDialog(parentJf, "Saved data");
            String v = varSectionCode;
            addAllSections();
            txtSection.setSelectedItem(utils.findByValue(txtSection, v));
            rtc.mouseClicked(null);
            rtc.repaint();


        } catch (SQLException ex) {
            try {
                dbConnection.rollback();
            } catch (SQLException ex1) {
            }
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJf, ex.getMessage());
        }





    }
    private void cmdCreateTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreateTable1ActionPerformed
        create_table();
}//GEN-LAST:event_cmdCreateTable1ActionPerformed

    private void cmdSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSave1ActionPerformed
        save_table_data();
}//GEN-LAST:event_cmdSave1ActionPerformed
    public void create_table() {

        if (txtSection.getSelectedIndex() <= 0) {
            return;
        }
        RTablesCanvas rtc = (RTablesCanvas) canvas1;
        RTables rt = new RTables(rtc);
        Random rnd = new Random();
        rt.keyfld = -1;
        rt.Code = "T-" + (rtc.listTables.size() + 1);
        rt.descr = rt.Code;
        rt.TypeOfGraphics = RTables.RECTANGLE;
        rt.rect.width = 50;
        rt.rect.height = 50;
        rt.rect.x = rnd.nextInt(100);
        rt.rect.y = rnd.nextInt(100);
        rt.width = 50;
        rt.height = 50;
        rt.pos_x = rt.rect.x;
        rt.pos_y = rt.rect.y;
        rt.strBackColor = "BLUE";
        rt.strTextColor = "WHITE";
        rt.strBorderColor = "GRAY";
        rt.backColor = ColorUtility.convertColor(rt.strBackColor, Color.blue);
        rt.textColor = ColorUtility.convertColor(rt.strTextColor, Color.white);
        rt.borderColor = ColorUtility.convertColor(rt.strBorderColor, Color.gray);
        rt.last_rect.setBounds(rt.rect);

        rtc.listTables.add(rt);
        rtc.st = rt;
        onSelectionOfTable(rt);
        rtc.repaint();
    }

    private void cmdDelTable1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelTable1ActionPerformed
        delete_table();
}//GEN-LAST:event_cmdDelTable1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
}//GEN-LAST:event_jTable2MouseClicked

    private void editTablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editTablesActionPerformed
        on_change_mode();
    }//GEN-LAST:event_editTablesActionPerformed

    private void editTablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_editTablesMouseClicked
        on_change_mode();        // TODO add your handling code here:
    }//GEN-LAST:event_editTablesMouseClicked

    private void cmdSave2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSave2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdSave2ActionPerformed

    private void cmdDelTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDelTable2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmdDelTable2ActionPerformed

    private void cmdCreateTable2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdCreateTable2ActionPerformed
        add_item();
    }//GEN-LAST:event_cmdCreateTable2ActionPerformed
    private void add_item() {
        itemsSel itms = new itemsSel(parentJf, true);
        itms.customInitialize();
        itms.setVisible(true);
    }

    private void on_change_mode() {
        RTablesCanvas rtc = (RTablesCanvas) canvas1;
        jTabbedPane1.removeAll();
        if (editTables.isSelected()) {
            rtc.setSystem_mode(rtc.MODE_DESIGN);
            jTabbedPane1.addTab("Properties", pnl_Prop);
            rtc.repaint();
            jTabbedPane1.updateUI();
        } else {
            rtc.setSystem_mode(rtc.MODE_USER);
            jTabbedPane1.addTab("Items", pnl_items);
            jTabbedPane1.updateUI();
            rtc.repaint();
        }

    }

    private void delete_table() {
        RTablesCanvas rtc = (RTablesCanvas) canvas1;
        if (rtc.st == null) {
            JOptionPane.showMessageDialog(parentJf, "No table selected");
            return;
        }
        try {
            dbConnection.setAutoCommit(false);
            if (JOptionPane.showConfirmDialog(parentJf, "Do you want to remove table -" + rtc.st.Code + " ? ", "Confirm Delete",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                utils.execSql("delete from pos_tables where keyfld=" + rtc.st.keyfld, dbConnection);
                dbConnection.commit();
                String s = varSectionCode;
                rtc.st = null;
                fill_tables();
                rtc.repaint();
            }
        } catch (SQLException ex) {
            Logger.getLogger(dinePanel.class.getName()).log(Level.SEVERE, null, ex);
            try {
                dbConnection.rollback();
            } catch (SQLException ex1) {
            }
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel canvas1;
    private javax.swing.JButton cmdCreateSec;
    private javax.swing.JButton cmdCreateTable1;
    private javax.swing.JButton cmdCreateTable2;
    private javax.swing.JButton cmdDelTable1;
    private javax.swing.JButton cmdDelTable2;
    private javax.swing.JButton cmdSave1;
    private javax.swing.JButton cmdSave2;
    private javax.swing.JCheckBox editTables;
    private javax.swing.JTable groupTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel pnl_Prop;
    private javax.swing.JPanel pnl_items;
    private javax.swing.JPanel pnl_paras;
    private javax.swing.JSplitPane splitGroupItem;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JComboBox txtSection;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables
    private Connection dbConnection = null;
    private localTableModel tbModel = new localTableModel();
    private DBClass dbc = null;
    private SimpleDateFormat sdf = null;
    private DecimalFormat decf = null;
    private MainFrame parentJf = null;
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
    private List<Parameter> listParas = new ArrayList<Parameter>();
    private RTables selectedTable = null;
    private localTableModel itemdetails = new localTableModel();
    private String QRYSTR = "";

    public void onMovingTable(RTables r, int pos_x, int pos_y) {
        if (listParas.isEmpty()) {
            return;
        }
        Parameter pm = findParameter("POS_X");
        pm.setValue(pos_x);
        ((JTextField) pm.getUIObject()).setText(pos_x + "");
        pm = findParameter("POS_Y");
        pm.setValue(pos_y);
        ((JTextField) pm.getUIObject()).setText(pos_y + "");

    }

    public Parameter findParameter(String nm) {

        for (Iterator<Parameter> it = listParas.iterator(); it.hasNext();) {
            Parameter pm = it.next();
            if (pm.getName().equals(nm)) {
                return pm;
            }

        }
        return null;
    }
}
