/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.utils;

import com.forms.MainFrame;
import com.generic.model.DBClass;
import com.generic.model.Row;
import com.generic.model.dataCell;
import com.generic.model.localTableModel;
import com.generic.model.qryColumn;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.MathContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author yusuf
 */
public class utils {

    private static Connection dbConn = null;
    private static DBClass dbClass = null;
    public static final String FORMAT_MONEY = "#,##0.000;(#,##0.000)";
    public static final String FORMAT_QTY = "#,##0.###;(#,##0.###)";
    public static final String FORMAT_SHORT_DATE = "dd/MM/yyyy";
    public static final String FORMAT_LONG_DATE = "dd/MM/yyyy hh:mm";
    public static MainFrame parentJf = null;
    public static JTextComponent last_textbox = null;
    public static JFrame last_frame = null;
    public static JTextComponent focus_textbox = null;

    public static int execSql(String sqlstr, Connection con) throws SQLException {
        PreparedStatement st = con.prepareStatement(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        int x = st.executeUpdate();
        st.close();
        return x;
    }
    public static FocusListener fc = new FocusListener() {

        public void focusGained(FocusEvent evt) {
            focus_textbox = (JTextComponent) evt.getSource();
            focus_textbox.selectAll();
            getMainFrame().kf.autoLocate((JComponent) evt.getSource());
        }

        public void focusLost(FocusEvent evt) {
            last_textbox = (JTextComponent) evt.getSource();
            focus_textbox = null;
        }
    };

    public static void setupFormTextBoxes(Container c) {

        for (int i = 0; i < c.getComponentCount(); i++) {
            Component co = c.getComponent(i);
            if (co instanceof Container) {
                setupFormTextBoxes((Container) co);
            }
            if (co instanceof JTextComponent) {
                for (int j = 0; j < co.getFocusListeners().length; j++) {
                    if (co.getFocusListeners()[j] == fc) {
                        break;
                    } else {
                        co.addFocusListener(fc);
                    }
                }
            }
        }

    }

    public static MainFrame getMainFrame() {
        return parentJf;
    }

    public static void applyColumn(MainFrame parentJF, qryColumn qc, JTable table, int col) {
        int width = qc.getDisplaySize();
        table.getColumnModel().getColumn(col).setPreferredWidth(width);
        table.getColumnModel().getColumn(col).setWidth(width);
    }

    public static String getSqlValue(
            String sqlstr, Connection con) {
        dbConn = con;
        ResultSet rs = null;
        String s = "";
        if (con == null) {
            return null;
        }
        PreparedStatement st = null;
        try {
            st = con.prepareStatement(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs =
                    st.executeQuery();
            if (rs.first()) {
                s = "";
                if (rs.getObject(1) != null) {
                    s = rs.getObject(1).toString();
                }
                rs.close();
                st.close();
                return s;
            } else {
                rs.close();
                st.close();
                return "";
            }


        } catch (Exception e) {
            Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                if (st != null) {
                    st.close();
                    rs.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    public static ResultSet getSqlRS(
            String sqlstr, Connection con) {
        dbConn = con;
        ResultSet rs = null;
        String s = "";
        try {
            PreparedStatement st = con.prepareStatement(sqlstr, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery();
            if (rs.first()) {
                s = "";
                return rs;
            } else {
                rs.close();
                st.close();
                return null;
            }


        } catch (Exception e) {
            Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            try {
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void buildTableSql(DBClass dbc, String sqlStr, localTableModel lctb) throws Exception {
        if (dbc != null && dbc.isConnected() == true) {
            List<Row> rw = null;
            if (sqlStr.length() > 0) {
                dbc.setSqlString(sqlStr);
                dbc.parseStatment();
                dbc.executeStatment();
            }
            rw = dbc.convertRows("");
            lctb.clearALl();
            lctb.appendRows(rw);
            lctb.getQrycols().clear();
            lctb.getQrycols().addAll(dbc.getColumnsList());
        }

    }

    public static Float noValue(Float f, Float a) {
        if (f == null) {
            return a;
        }
        return f;
    }

    public static Double noValue(Double f, Double a) {
        if (f == null) {
            return a;
        }
        return f;
    }

        public static Object noValue(Object f, Object a) {
        if (f == null || f.toString().equals("")) {
            return a;
        }
        return f;
    }

    public static List<Row> convertRows(PreparedStatement ps, String filterstring, int nextfetch) throws SQLException {
        List<Row> lsr = new ArrayList<Row>();
        ResultSet rs = null;
        ResultSetMetaData rsm = null;
        int tmp = 0;
        boolean fnd = true;
        if (ps == null) {
            return null;
        }
        //parseStatment();
        if (nextfetch <= 0) {
            rs = ps.executeQuery();
            rsm = rs.getMetaData();
        }
        Row r = null;
        rs.beforeFirst();
        while (rs.next() && (nextfetch == 0 || tmp <= nextfetch)) {
            fnd = false;
            r = new Row(rsm.getColumnCount());
            for (int i = 0; i < rsm.getColumnCount(); i++) {
                if ((filterstring == null || filterstring.length() == 0) || (rs.getObject(i + 1) != null && rs.getObject(i + 1).toString().contains(filterstring))) {
                    fnd = true;
                }
                if (rs.getObject(i + 1) != null) {
                    r.lst.get(i).setValue(rs.getObject(i + 1).toString(), rs.getObject(i + 1));
                } else {
                    r.lst.get(i).setValue("", "");
                }

            }
            if (fnd) {
                lsr.add(r);
            }
        }
        //trs.close();
        ps.close();
        return lsr;
    }

    public static List<Row> convertRows2(PreparedStatement ps, Map<String, Row> mp, String filterstring, int nextfetch, int mapcol) throws SQLException {
        List<Row> lsr = new ArrayList<Row>();
        ResultSet rs = null;
        ResultSetMetaData rsm = null;
        int tmp = 0;
        boolean fnd = true;
        if (ps == null) {
            return null;
        }
        //parseStatment();
        if (nextfetch <= 0) {
            rs = ps.executeQuery();
            rsm = rs.getMetaData();
        }
        Row r = null;
        rs.beforeFirst();
        while (rs.next() && (nextfetch == 0 || tmp <= nextfetch)) {
            fnd = false;
            r = new Row(rsm.getColumnCount());
            for (int i = 0; i < rsm.getColumnCount(); i++) {
                if ((filterstring == null || filterstring.length() == 0) || (rs.getObject(i + 1) != null && rs.getObject(i + 1).toString().contains(filterstring))) {
                    fnd = true;
                }
                if (rs.getObject(i + 1) != null) {
                    r.lst.get(i).setValue(rs.getObject(i + 1).toString(), rs.getObject(i + 1));
                } else {
                    r.lst.get(i).setValue("", "");
                }

            }
            if (fnd) {
                lsr.add(r);
                mp.put(r.lst.get(mapcol).getValue().toString(), r);
            }
        }
        //trs.close();
        ps.close();
        return lsr;
    }

    public static double roundToDecimals(double d, int c) {
        BigDecimal b = BigDecimal.valueOf(d);
        b = b.round(new MathContext(c));
        return b.doubleValue();
//        int temp = (int) ((d * Math.pow(10, c)));
        //      return (((double) temp) / Math.pow(10, c));
    }

    public static void setParams(String sq, PreparedStatement ps,
            Map<String, Parameter> mapParameters) throws SQLException {

        int parano = 1;
        String pnm = getParamName(sq, 1).toUpperCase();
        while (pnm.length() > 0) {
            pnm = getParamName(sq, parano).toUpperCase();
            if (pnm.length() > 0) {
                System.out.println("QueryExe, setParams function : para='" + pnm + "'");
                if (mapParameters.get(pnm).getValueType().equals(
                        Parameter.DATA_TYPE_STRING)) {
                    if (mapParameters.get(pnm).getValue() != null) {
                        ps.setString(parano, (String) mapParameters.get(pnm).getValue());
                    } else {
                        ps.setString(parano, "");
                    }
                }

                if (mapParameters.get(pnm).getValueType().equals(
                        Parameter.DATA_TYPE_DATE)) {
                    if (mapParameters.get(pnm).getValue() != null) {
                        java.sql.Date jdt = null;
                        if (mapParameters.get(pnm).getValue() instanceof java.util.Date) {
                            jdt = new java.sql.Date(
                                    ((java.util.Date) mapParameters.get(pnm).getValue()).getTime());
                        }
                        ps.setDate(parano, jdt);
                    } else {
                        ps.setDate(parano, null);
                    }
                }

                if (mapParameters.get(pnm).getValueType().equals(
                        Parameter.DATA_TYPE_DATETIME)) {
                    if (mapParameters.get(pnm).getValue() != null) {
                        java.sql.Timestamp jdt = null;
                        if (mapParameters.get(pnm).getValue() instanceof java.util.Date) {
                            jdt = new java.sql.Timestamp(
                                    ((java.util.Date) mapParameters.get(pnm).getValue()).getTime());
                        }
                        ps.setTimestamp(parano, jdt);
                    } else {
                        ps.setTimestamp(parano, null);
                    }
                }
                if (mapParameters.get(pnm).getValueType().equals(
                        Parameter.DATA_TYPE_NUMBER)) {
                    if (mapParameters.get(pnm).getValue() != null) {
                        if (mapParameters.get(pnm).getValue() instanceof Number) {
                            ps.setDouble(parano, ((Number) mapParameters.get(
                                    pnm).getValue()).doubleValue());
                        } else {
                            ps.setString(parano, (mapParameters.get(pnm).getValue()).toString());
                        }
                    } else {
                        ps.setString(parano, "");
                    }
                }
            }
            parano++;
        }
    }

    public static String getParamName(String s, int no) {
        String tmp = "";
        String tmp1 = "";
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            tmp = "";
            if (s.charAt(i) == ':' && !s.substring(i, i + 2).equals(":=")) {
                n++;
                i++;
                while (i < s.length() && s.charAt(i) != '\n'
                        && s.charAt(i) != ' ' && s.charAt(i) != '='
                        && s.charAt(i) != ')' && s.charAt(i) != '('
                        && s.charAt(i) != ';' && s.charAt(i) != ',') {
                    tmp = tmp + String.valueOf(s.charAt(i));
                    i++;
                }
            }
            if (n == no) {
                break;
            } else {
                tmp = "";
            }
        }
        return tmp.trim();
    }

    public static String replaceParameters(String s) {
        String tmp = "";
        String nexts = "?";
        for (int i = 0; i < s.length(); i++) {
            nexts = String.valueOf(s.charAt(i));
            if (s.charAt(i) == ':' && !s.substring(i, i + 2).equals(":=")) {
                nexts = "? ";
                while (i < s.length() && s.charAt(i) != '\n'
                        && s.charAt(i) != '\r' && s.charAt(i) != ' ') {
                    i++;
                    if (i == s.length()) {
                        break;
                    }
                    if (s.charAt(i) == '=') {
                        nexts = "?=";
                        break;
                    }
                    if (s.charAt(i) == ')') {
                        nexts = "?)";
                        break;
                    }
                    if (s.charAt(i) == '(') {
                        nexts = "?(";
                        break;
                    }
                    if (s.charAt(i) == ';') {
                        nexts = "?;";
                        break;
                    }
                    if (s.charAt(i) == ',') {
                        nexts = "?,";
                        break;
                    }

                }

            }
            tmp = tmp + nexts;
        }
        return tmp;

    }

    public static String getOraDateValue(String dt) {
        return "to_date('" + dt + "','" + FORMAT_SHORT_DATE + "')";
    }

    public static String getOraDateValue(Timestamp dt) {
        return "to_date('"
                + (new SimpleDateFormat(FORMAT_SHORT_DATE)).format(dt)
                + "','DD/MM/RRRR')";
    }

    public static String getOraDateValue(Date dt) {
        return "to_date('"
                + (new SimpleDateFormat(FORMAT_SHORT_DATE)).format(dt)
                + "','DD/MM/RRRR')";
    }

    public static void fillComboBox(JComboBox jc, List<dataCell> dclist, String sq, Connection con) throws SQLException {
        List<dataCell> dcl = dclist;
        if (dclist == null) {
            dcl = new ArrayList<dataCell>();
        }

        if (!sq.isEmpty()) {
            dcl.clear();
            PreparedStatement pst = con.prepareStatement(sq, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rst = pst.executeQuery();
            rst.beforeFirst();
            while (rst.next()) {
                dcl.add(new dataCell(rst.getString(2), rst.getString(1)));
            }
            pst.close();
        }
        ((DefaultComboBoxModel) jc.getModel()).removeAllElements();
        for (Iterator<dataCell> it = dcl.iterator(); it.hasNext();) {
            dataCell cell = it.next();
            ((DefaultComboBoxModel) jc.getModel()).addElement(cell);
        }
    }

    public static dataCell findByValue(JComboBox c, String val) {

        for (int i = 0; i < ((DefaultComboBoxModel) c.getModel()).getSize(); i++) {
            dataCell dx = (dataCell) ((DefaultComboBoxModel) c.getModel()).getElementAt(i);
            if (dx != null && dx.getValue() != null
                    && dx.getValue().toString().equals(val)) {
                return dx;
            }
        }
        return null;

    }

    public static List<Row> convertRows(PreparedStatement ps, ResultSet rsp,
            String filterstring) throws SQLException {
        List<Row> lsr = new ArrayList<Row>();
        int tmp = 0;
        ResultSetMetaData rsm = null;
        ResultSet rs = null;

        if (rsp == null) {
            rs = ps.executeQuery();
        } else {
            rs = rsp;
        }

        boolean fnd = true;
        try {
            if (ps == null) {
                return null;
            }
            rs = ps.executeQuery();
            rsm = rs.getMetaData();
            Row r = null;
            rs.beforeFirst();
            while (rs.next()) {
                fnd = false;
                r = new Row(rsm.getColumnCount());
                for (int i = 0; i < rsm.getColumnCount(); i++) {
                    if ((filterstring == null || filterstring.length() == 0)
                            || (rs.getObject(i + 1) != null && rs.getObject(
                            i + 1).toString().contains(filterstring))) {
                        fnd = true;
                    }
                    if (rs.getObject(i + 1) != null) {
                        r.lst.get(i).setValue(rs.getObject(i + 1).toString(),
                                rs.getObject(i + 1));
                    } else {
                        r.lst.get(i).setValue("", "");
                    }

                }
                if (fnd) {
                    lsr.add(r);
                }
            }
            // trs.close();
            // ps.close();
            return lsr;
        } catch (SQLException ex) {
            Logger.getLogger(DBClass.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return null;
    }

    public static int indexOf(String[] strAlign, String t1) {
        for (int i = 0; i < strAlign.length; i++) {
            String str = strAlign[i];
            if (str.equals(t1)) {
                return i;
            }

        }
        return -1;
    }

    public static void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 25, width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
            columnModel.getColumn(column).setWidth(width);

        }
    }

    public static void arrangePanelComponentsVertically(final Container pnl, boolean fitSub, double... exRatio) {
        if (pnl == null) {
            return;
        }
        int pnlHeight = pnl.getHeight();
        int location = 0;
        for (int i = 0; i < pnl.getComponentCount(); i++) {
            Component c = pnl.getComponent(i);
            if (i > exRatio.length) {
                break;
            }
            double rh1 = (((double) pnlHeight) / 100) * exRatio[i];
            int rh = (int) rh1;
            c.setSize(pnl.getWidth() - 3, rh - 1);
            c.getPreferredSize().setSize(pnl.getWidth() - 3, rh - 1);
            c.setBounds(0, location, pnl.getWidth() - 3, (rh - 1));

            if (fitSub && c instanceof JPanel && ((JPanel) c).getComponentCount() == 1) {
                ((JPanel) c).getComponent(0).setSize(c.getWidth() - 3, rh - 2);
                ((JPanel) c).getComponent(0).getPreferredSize().setSize(c.getWidth() - 3, rh - 2);
                ((JPanel) c).getComponent(0).setBounds(0, 0, c.getWidth() - 1, rh - 2);
            }

            location += rh;
        }
    }

    public static int find_value_in_row(int colno, String vl, List<Row> rows) {

        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            if (r.lst.get(colno).getValue() != null
                    && r.lst.get(colno).getValue().toString().equals(vl)) {
                return i;
            }
        }
        return -1;
    }

    public static int find_value_in_row(int colno, double vl, List<Row> rows) {

        for (int i = 0; i < rows.size(); i++) {
            Row r = rows.get(i);
            if (r.lst.get(colno).getValue() != null
                    && Double.valueOf(r.lst.get(colno).getValue().toString()) == vl) {
                return i;
            }
        }
        return -1;
    }
}
