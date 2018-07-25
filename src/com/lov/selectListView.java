/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lov;

import com.forms.panels.ColorRenderer;
import com.generic.model.DBClass;
import com.generic.model.localTableModel;
import com.generic.model.qryColumn;
import com.generic.utils.XTableColumnModel;
import com.generic.utils.utils;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author yusuf
 */
public class selectListView {
    
    public static final int NO_CLICK_SELECTION = 0;
    public static final int DOUBLE_CLICK_SELECTION = 2;
    public static final int SINGLE_CLICK_SELECTION = 1;
    private localTableModel tbModel = new localTableModel();
    private lovFrame parentJF = null;
    private JPanel parentPanel = null;
    private JPanel headerPanel = null;
    public JPanel footerPanel = null;
    private JTable listtb = null;
    private DBClass dbc = null;
    private JScrollPane scrollpane = null;
    private int rowHeight = 30;
    private int rowno = -1;
    private JTextField txtFilter = null;

    public JTextField getTxtFilter() {
        return txtFilter;
    }
    private int selectionStyle = NO_CLICK_SELECTION;
    private localTableModel emptyTb = new localTableModel();
    private boolean auto_size_cols = true;
    public boolean show_footer_ok_cancel = true;
    private KeyListener kl = new KeyListener() {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                listtb.requestFocus();
            }
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (listtb.getRowCount()==1)
                    listtb.setRowSelectionInterval(0, 0);
                parentJF.setSelectedNo(listtb.getSelectedRow(),txtFilter.getText());
                txtFilter.selectAll();
            }
        }

        public void keyReleased(KeyEvent e) {
            try {
                /*
                if (tbModel != null) {
                try {
                tbModel.setFilterStr(txtFilter.getText());
                tbModel.executeQuery(tbModel.getSqlString(), true);
                listtb.updateUI();
                } catch (SQLException ex) {
                Logger.getLogger(selectListView.class.getName()).log(Level.SEVERE, null, ex);
                }
                }*/
                if (!utils.parentJf.getSp().chkOnlyBarcode.isSelected()) {
                    tbModel.setDynamicFilter(txtFilter.getText());
                } else
                {
                    tbModel.setFilterbyCol("BARCODE",txtFilter.getText());
                }
                
                listtb.updateUI();
            } catch (Exception ex) {
                Logger.getLogger(selectListView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    private MouseListener ml = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
            if (selectionStyle == SINGLE_CLICK_SELECTION && e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1) {
                parentJF.setSelectedNo(listtb.getSelectedRow(),"");
            }
            if (selectionStyle == DOUBLE_CLICK_SELECTION && e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                parentJF.setSelectedNo(listtb.getSelectedRow(),"");                
            }
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    };
    private KeyListener kbl = new KeyListener() {

        public void keyTyped(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                parentJF.setSelectedNo(listtb.getSelectedRow(),"");
                txtFilter.requestFocus();
            }

        }

        public void keyReleased(KeyEvent e) {
        }
    };

    public int getSelectionStyle() {
        return selectionStyle;
    }

    public void setSelectionStyle(int selectionStyle) {
        this.selectionStyle = selectionStyle;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void setTbModel(localTableModel tbModel) {
        this.tbModel = tbModel;
    }

    public JPanel getHeaderPanel() {
        return headerPanel;
    }

    public localTableModel getLctb() {
        return tbModel;
    }

    public JTable getListtb() {
        return listtb;
    }

    public lovFrame getParentJF() {
        return parentJF;
    }

    public void setParentJf(lovFrame lv) {
        this.parentJF = lv;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public selectListView() {
    }

    public void createView() {
        rowno = -1;

        if (parentPanel == null) {
            return;
        }

        if (headerPanel == null) {
            headerPanel = new JPanel();
            headerPanel.setLayout(null);
            txtFilter = new JTextField("");
            listtb = new JTable(tbModel);
            listtb.setShowHorizontalLines(false);
            listtb.setShowVerticalLines(false);
            txtFilter.addKeyListener(kl);
            listtb.addMouseListener(ml);
            listtb.addKeyListener(kbl);
            if (show_footer_ok_cancel && footerPanel == null) {
                footerPanel = new JPanel();
            }
        } else {
            parentPanel.removeAll();
            headerPanel.removeAll();
        }
        parentPanel.add(headerPanel);
        //headerPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 50));
        //headerPanel.setBounds(0, 0, parentPanel.getWidth(), 50);
        headerPanel.setBackground(Color.YELLOW);
        //txtFilter.setPreferredSize(new Dimension(headerPanel.getWidth(), headerPanel.getHeight()));
        txtFilter.setBackground(headerPanel.getBackground());
        txtFilter.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(txtFilter);
        listtb.setColumnModel(new XTableColumnModel());
        listtb.setModel(tbModel);
        setColsProperties();
        listtb.setModel(emptyTb);
        listtb.setModel(tbModel);
        listtb.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        listtb.setGridColor(Color.LIGHT_GRAY);

        if (auto_size_cols) {
            utils.resizeColumnWidth(listtb);
        } else {
            listtb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        }
        scrollpane = new JScrollPane(listtb);
        scrollpane.setViewportView(listtb);
        //scrollpane.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() - 74));
        //scrollpane.setBounds(0, 74, parentPanel.getWidth(), parentPanel.getHeight() - 74);
        parentPanel.add(scrollpane);
        listtb.setRowHeight(rowHeight);
        listtb.updateUI();

        if (footerPanel != null) {
            parentPanel.add(footerPanel);
            //footerPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 50));
            //footerPanel.setBounds(0, parentPanel.getHeight() - 75, parentPanel.getWidth(), parentPanel.getHeight());
        }
        if (show_footer_ok_cancel) {
            footerPanel.setBackground(Color.BLUE);
            footerPanel.removeAll();
            JButton but = new JButton("OK");
            but.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (parentJF != null) {
                        parentJF.setSelectedNo(listtb.getSelectedRow(),"");
                    }
                }
            });
            footerPanel.add(but);
            but = new JButton("Cancel");
            but.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (parentJF != null) {
                        parentJF.setSelectedNo(-1,"");

                    }
                }
            });
            footerPanel.add(but);
        }
        utils.arrangePanelComponentsVertically(parentPanel, true, 10, 80, 10);
    }
    
    private void setColsProperties() {
        localTableModel itemd = (localTableModel) listtb.getModel();
        XTableColumnModel colmod = ((XTableColumnModel) listtb.getColumnModel());
        for (int colx = 0; colx
                < colmod.getColumnCount(false); colx++) {
            qryColumn qc = itemd.getVisbleQrycols().get(colx);
            if (qc.getWidth() > 0) {
                colmod.setColumnVisible(colmod.getColumn(colx, false), true);
                colmod.getColumn(colx, false).setCellRenderer(new ColorRenderer(true));
                colmod.getColumn(colx, false).setPreferredWidth(qc.getWidth());
                colmod.getColumn(colx, false).setWidth(qc.getWidth());
            } else {
                colmod.setColumnVisible(colmod.getColumn(colx, false), false);
            }

        }
    }

    public void resize() {
        /*
        headerPanel.getPreferredSize().setSize(parentPanel.getWidth(), 50);
        headerPanel.setBounds(0, 0, parentPanel.getWidth(), 50);
        txtFilter.getPreferredSize().setSize(headerPanel.getWidth(), headerPanel.getHeight());
        txtFilter.getPreferredSize().setSize(new Dimension(headerPanel.getWidth(), headerPanel.getHeight()));
        scrollpane.getPreferredSize().setSize(parentPanel.getWidth(), parentPanel.getHeight() - 100);
        scrollpane.setBounds(0, 51, parentPanel.getWidth(), parentPanel.getHeight() - 100);
        if (show_footer_ok_cancel && footerPanel != null) {
        footerPanel.getPreferredSize().setSize(parentPanel.getWidth(), 50);
        footerPanel.setBounds(0, parentPanel.getHeight() - 50, parentPanel.getWidth(), parentPanel.getHeight());
        }
         *
         */
        utils.arrangePanelComponentsVertically(parentPanel, true, 10, 80, 10);
        parentPanel.updateUI();
    }

    public void setAutoSize(boolean auto_size_cols) {
        this.auto_size_cols = auto_size_cols;
    }

    public boolean get_auto_size_cols() {
        return this.auto_size_cols;
    }
}
