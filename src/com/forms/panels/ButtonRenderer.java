/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms.panels;

import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets; 
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author yusuf
 */
public class ButtonRenderer extends JLabel implements TableCellRenderer {

    //private ActionListener listner = null;
    private MouseAdapter listner = null;
    private boolean isBordered = false;
    Border unselectedBorder = null;
    Border selectedBorder =getBorder();
    Color bk=null;
    public ButtonRenderer(MouseAdapter lst, boolean bordered,Color bk) {
        setOpaque(true);
        this.listner = lst;
        isBordered = bordered;
        this.bk=bk;        
        //addActionListener(listner);
        addMouseListener(lst);
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value==null) {return null;
        }
        setText("<html><div style='text-align: center;'>"+value.toString()+"</div></html>");
        //setText("<html><center>"+value.toString()+"</center></html>");
        //setText(value.toString());
        //setText(value.toString());
        setToolTipText("<html><u>"+((dataCell)value).getValue()+"</u><br>"+((dataCell)value).getDisplay()+"</html>");
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalTextPosition(SwingConstants.CENTER);
        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);
        //setMargin(new Insets(0, 0, 0, 0));       
        setName(((dataCell)value).getValue().toString());
        //setFont(table.getFont());
        setFont(new Font("Arial", Font.BOLD, 16));
        //setMargin(new Insets(0, 0, 0, 0));
        setOpaque(false);
        setBackground(table.getBackground());
        setForeground(table.getForeground());

        if (isBordered) {

            if (isSelected) {
                //setOpaque(true);
                if (selectedBorder == null) {
                    selectedBorder = BorderFactory.createMatteBorder(0, 0, 0, 0,
                            table.getSelectionBackground());
                }
                setBorder(selectedBorder);
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());

            } else {
                if (unselectedBorder == null) {
                    unselectedBorder = BorderFactory.createMatteBorder(0, 0, 0, 0,
                            table.getBackground());
                }
                setBorder(unselectedBorder);                
                localTableModel lctb = (localTableModel) (table.getModel());
                setHorizontalAlignment(lctb.getColByPos(column).getAlignmnet());
                setBackground(lctb.getColByPos(column).getColor());

            }
            
        }
        
           this.setHorizontalAlignment(SwingConstants.CENTER);

        return this;
    }
    
}

