/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.forms.panels;

import com.generic.model.localTableModel;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author yusuf
 */
public class ColorRenderer extends JLabel
        implements TableCellRenderer {

    Border unselectedBorder = null;
    Border selectedBorder = null;
    boolean isBordered = true;
//    static int cnt=1;

    public ColorRenderer(boolean isBordered) {
        this.isBordered = isBordered;
        setOpaque(false); //MUST do this for background to show up.
    }

    public Component getTableCellRendererComponent(
            JTable table, Object color,
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        //Color newColor = (Color)color;
        //setBackground(newColor);
//        System.out.println((cnt++)+" "+color);

        if (color != null) {
            setText(color.toString());
        } else {
            setText(null);
        }
        setFont(table.getFont());
        setBackground(table.getBackground());
        setForeground(table.getForeground());
        setOpaque(false);
        if (isBordered) {
            if (isSelected) {
                setOpaque(true);
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
                setBackground(Color.WHITE);
                localTableModel lctb = (localTableModel) (table.getModel());
                setHorizontalAlignment(lctb.getColByPos(column).getAlignmnet());
                setBackground(lctb.getColByPos(column).getColor());
            /*
            if (table.getModel().getValueAt(row, 3).equals("INVOICE_VOUCHER")) {
            setForeground(Color.BLUE);
            }
            if (table.getModel().getValueAt(row, 3).equals("REQUEST_MATERIALS")) {
            setForeground(Color.MAGENTA);
            }
            if (table.getModel().getValueAt(row, 3).equals("QUOTATION")) {
            setForeground(new Color(102, 0, 102));
            }
            if (table.getModel().getValueAt(row, 3).equals("PAYMENT")) {
            setForeground(new Color(0, 102, 105));
            }
            if (table.getColumnName(column).equals("KIND")) {
            setFont(new Font(table.getFont().getName(), 1, table.getFont().getSize()));
            setBackground(new Color(245, 245, 245));
            setHorizontalAlignment(CENTER);
            }
             **/
            }

        }

        /*
        setToolTipText("RGB value: " + newColor.getRed() + ", "
        + newColor.getGreen() + ", "
        + newColor.getBlue());
         */
        return this;
    }
}