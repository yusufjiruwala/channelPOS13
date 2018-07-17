/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.utils;

import java.sql.*;
import java.util.Vector;
import javax.swing.table.*;
import javax.swing.event.*;

/**
 *
 * @author ibm pc
 */
public class ResultSetTableModel implements TableModel {

    ResultSet results;             // The ResultSet to interpret
    ResultSetMetaData metadata;    // Additional information about the results
    int numcols, numrows;          // How many rows and columns in the table
    static Vector balances = new Vector();

    /**
     * This constructor creates a TableModel from a ResultSet.  It is package
     * private because it is only intended to be used by 
     * ResultSetTableModelFactory, which is what you should use to obtain a
     * ResultSetTableModel
     **/
    ResultSetTableModel(ResultSet results) throws SQLException {
        this.results = results;                 // Save the results
        metadata = results.getMetaData();       // Get metadata on them
        numcols = metadata.getColumnCount();    // How many columns?
        results.last();                         // Move to last row
        numrows = results.getRow();             // How many rows?
//        if (balances.size()==0) {
//         st_accbal=results.getStatement().getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);        
//         rs_accbal=st_accbal.executeQuery("select accno,path,debit-credit balance from ACC_BALANCE_1 order by path"); 
//        while (rs_accbal.next())         
//            balances.addElement(new balx(rs_accbal.getString("path") , rs_accbal.getFloat("balance")));                          
//        }

    }

    /** 
     * Call this when done with the table model.  It closes the ResultSet and
     * the Statement object used to create it.
     **/
   
    public void close() {
        try {
            results.getStatement().close();
        } catch (SQLException e) {
        }
        ;
    }

    /** Automatically close when we're garbage collected */
    @Override
    protected void finalize() {
        close();
    }

    // These two TableModel methods return the size of the table
    public int getColumnCount() {
        return numcols;
    }

    public int getRowCount() {
        return numrows;
    }

    // This TableModel method returns columns names from the ResultSetMetaData
    public String getColumnName(int column) {
        try {
            return metadata.getColumnLabel(column + 1);
        } catch (SQLException e) {
            return e.toString();
        }
    }

    // This TableModel method specifies the data type for each column.  
    // We could map SQL types to Java types, but for this example, we'll just
    // convert all the returned data to strings.
    public Class getColumnClass(int column) {
        return String.class;

    }

    /**
     * This is the key method of TableModel: it returns the value at each cell
     * of the table.  We use strings in this case.  If anything goes wrong, we
     * return the exception as a string, so it will be displayed in the table.
     * Note that SQL row and column numbers start at 1, but TableModel column
     * numbers start at 0.
     **/
    public Object getValueAt(int row, int column) {
        try {
            results.absolute(row + 1);                // Go to the specified row
            Object o = results.getObject(column + 1); // Get value of the column
            if (o == null) {
                return null;
            } else {
                return o.toString();               // Convert it to a string
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Our table isn't editable
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    // Since its not editable, we don't need to implement these methods
    public void setValueAt(Object value, int row, int column) {
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public void removeTableModelListener(TableModelListener l) {
    }
}
