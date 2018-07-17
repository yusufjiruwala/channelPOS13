/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lov;

import com.generic.model.DBClass;
import com.generic.model.localTableModel;
import java.awt.Color;
import java.awt.Dimension;
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
    public static final int NO_CLICK_SELECTION=0;
    public static final int DOUBLE_CLICK_SELECTION=2;
    public static final int SINGLE_CLICK_SELECTION=1;
    
    private localTableModel tbModel = new localTableModel();
    private lovFrame parentJF = null;
    private JPanel parentPanel = null;
    private JPanel headerPanel = null;
    private JPanel footerPanel = null;
    private JTable listtb = null;
    private DBClass dbc = null;
    private JScrollPane scrollpane = null;
    private int rowHeight = 30;
    private int rowno = -1;
    private JTextField txtFilter = null;
    private int selectionStyle=NO_CLICK_SELECTION;

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
            parentPanel.add(headerPanel);
            headerPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 50));
            headerPanel.setBounds(0, 0, parentPanel.getWidth(), 50);
            headerPanel.setBackground(Color.YELLOW);
            txtFilter = new JTextField("");
            txtFilter.setPreferredSize(new Dimension(headerPanel.getWidth(), headerPanel.getHeight()));
            txtFilter.setBackground(headerPanel.getBackground());
            headerPanel.add(txtFilter);
            txtFilter.addKeyListener(new KeyListener() {

                public void keyTyped(KeyEvent e) {
                }

                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        listtb.requestFocus();
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        parentJF.setSelectedNo(listtb.getSelectedRow());
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
                        tbModel.setDynamicFilter(txtFilter.getText());
                        listtb.updateUI();
                    } catch (Exception ex) {
                        Logger.getLogger(selectListView.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
        if (listtb == null) {
            listtb = new JTable(tbModel);
            listtb.setModel(tbModel);
            scrollpane = new JScrollPane(listtb);
            scrollpane.setViewportView(listtb);
            scrollpane.setPreferredSize(new Dimension(parentPanel.getWidth(), parentPanel.getHeight() - 100));
            scrollpane.setBounds(0, 51, parentPanel.getWidth(), parentPanel.getHeight() - 100);
            parentPanel.add(scrollpane);
            listtb.setRowHeight(rowHeight);
            listtb.updateUI();
            listtb.addKeyListener(new KeyListener() {

                public void keyTyped(KeyEvent e) {
                    
                }

                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        parentJF.setSelectedNo(listtb.getSelectedRow());
                    }

                }

                public void keyReleased(KeyEvent e) {
                    
                }
            });
            listtb.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    if (selectionStyle==SINGLE_CLICK_SELECTION && e.getClickCount()==1 && e.getButton()==MouseEvent.BUTTON1) {
                        parentJF.setSelectedNo(listtb.getSelectedRow());
                    }
                    if (selectionStyle==DOUBLE_CLICK_SELECTION && e.getClickCount()==2 && e.getButton()==MouseEvent.BUTTON1) {
                        parentJF.setSelectedNo(listtb.getSelectedRow());
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
            });
        }

        if (footerPanel == null) {
            footerPanel = new JPanel();
            parentPanel.add(footerPanel);
            footerPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), 50));
            footerPanel.setBounds(0, parentPanel.getHeight() - 50, parentPanel.getWidth(), parentPanel.getHeight());
            footerPanel.setBackground(Color.BLUE);
        }
        footerPanel.removeAll();
        JButton but = new JButton("OK");
        but.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (parentJF != null) {
                    parentJF.setSelectedNo(listtb.getSelectedRow());
                }
            }
        });
        footerPanel.add(but);
        but = new JButton("Cancel");
        but.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (parentJF != null) {
                    parentJF.setSelectedNo(-1);

                }
            }
        });
        footerPanel.add(but);
    }
}
