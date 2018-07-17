/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * itemsSel.java
 *
 * Created on Mar 14, 2014, 1:18:11 PM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.model.Row;
import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import com.generic.model.qryColumn;
import com.generic.utils.utils;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Yusuf
 */
public class itemsSel extends javax.swing.JDialog {

    /** Creates new form itemsSel */
    public itemsSel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        parentJF = (MainFrame) parent;
        initComponents();
        utils.setupFormTextBoxes(this);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
                0, false);
        Action escapeAction = new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        setLocationRelativeTo(null);
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
        splitGroupItem = new javax.swing.JSplitPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        groupTable = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        itemTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        txtItem = new javax.swing.JTextField();
        txtCtg = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtQty = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtItem1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtPrice = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(splitGroupItem, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 872, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addComponent(splitGroupItem, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(-14336,true));

        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txtItem.setEditable(false);
        txtItem.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        txtCtg.setEditable(false);
        txtCtg.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel1.setText("Item");

        txtQty.setEditable(false);
        txtQty.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtQty.setText("1");

        jLabel2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel2.setText("Qty");

        jButton2.setText("Select");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtItem1.setEditable(false);
        txtItem1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtItem1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtItem1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(-16777216,true)));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel3.setText("Ctg");

        txtPrice.setEditable(false);
        txtPrice.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        txtPrice.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPrice.setText("0.000");

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel4.setText("Price");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4)
                        .addComponent(jLabel1))
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtPrice)
                    .addComponent(txtCtg, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtItem, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtQty, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1)
                        .addComponent(jButton3)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtItem1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtItem, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCtg, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int n = Integer.valueOf(txtQty.getText()) + 1;
        txtQty.setText(n + "");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int n = Integer.valueOf(txtQty.getText()) - 1;
        if (n > 1) {
            txtQty.setText(n + "");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public void customInitialize() {
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
        fetchAllItemsFromSales();
        show_items();
    }

    public void fetchAllItemsFromSales() {

        if (parentJF.getSp().isShowing_items() == false && parentJF.getSp().getItemsRows() != null) {
            itemsRows = new ArrayList<Row>();
            itemMaps.clear();
            for (int i = 0; i < parentJF.getSp().getItemsRows().size(); i++) {
                itemsRows.add(parentJF.getSp().getItemsRows().get(i));
                itemMaps.put(parentJF.getSp().getItemsRows().get(i).lst.get(0).getValue().toString(), parentJF.getSp().getItemsRows().get(i));
                ps_titems = parentJF.getSp().getPs_titems();
            }
        }
    }

    public int show_items() {
        int itmcount = 0;
        int itmcountg = 0;

        try {
            setupItemCols();
            setupGroupCols();
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

                    if (dc.getValue().toString().equals(currentGroup)
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
        } catch (Exception ex) {
            Logger.getLogger(salesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        parentJF.getTxtMsg().setText(itmcount + " item(s) displayed " + currentItem + ".");
        parentJF.getTxtMsg().setForeground(Color.BLUE);
        showing_items = false;
        return itmcount;

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
        }
        itemTable.setModel(selItems);
        for (int colx = 0; colx < itemTable.getModel().getColumnCount(); colx++) {
            itemTable.getColumnModel().getColumn(colx).setCellRenderer(new ButtonRenderer(cmdActionMouse, true, Color.pink));
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
        }

    }

    @Override
    protected void finalize() throws Throwable {
        getRootPane().getActionMap().remove("ESCAPE");
        super.finalize();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable groupTable;
    private javax.swing.JTable itemTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane splitGroupItem;
    private javax.swing.JTextField txtCtg;
    private javax.swing.JTextField txtItem;
    private javax.swing.JTextField txtItem1;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQty;
    // End of variables declaration//GEN-END:variables
    private MainFrame parentJF = null;
    private Map<String, Row> itemMaps = new HashMap<String, Row>();
    public List<Row> itemsRows = null;
    PreparedStatement ps_titems = null;
    public boolean showing_items = false;
    private localTableModel selGrpItems = null;
    private localTableModel selItems = null;
    private int no_of_item_cols = 3;
    private String currentGroup = "";
    private String currentItem = "";
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
        double chld = Double.valueOf(parentJF.getSp().getValueFromItemsRow(dc.getValue().toString(), 6).toString());

        if (chld <= 0) {
            txtItem.setText(dc.getValue().toString());
            txtItem.requestFocus();
            txtCtg.setText(parentJF.getSp().getItemCategory(txtItem.getText()));
            txtQty.setText("1");
            txtPrice.setText(parentJF.getSp().lastSelectedItemPrice + "");
            txtItem1.setText(parentJF.getSp().getValueFromItemsRow(dc.getValue().toString(), 1).toString());
        } else {
            currentItem = dc.getValue().toString();
            show_items();
            itemTable.updateUI();
        }
    }

    public class JTableButtonMouseListener extends MouseAdapter {

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
                    itemTable.updateUI();
                    groupTable.updateUI();
                }
            }
        }
    }
}
