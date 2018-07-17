package com.generic.utils;

import com.generic.model.Row;
import com.generic.model.qryColumn;
import com.sun.crypto.provider.RSACipher;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

public class QueryExe {

    Map<String, Parameter> mapParameters = new HashMap<String, Parameter>();
    private int rowsAffected = 0;
    private String sqlStr = "";
    private Connection con = null;
    PreparedStatement ps_exe = null;
    ResultSet rs=null;

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Map<String, Parameter> getMapParas() {
        return mapParameters;
    }

    public void setParaValue(String paraname, Object val) throws SQLException {

        if (mapParameters.get(paraname.toUpperCase()) == null) {
            mapParameters.put(paraname.toUpperCase(), new Parameter(paraname,
                    val));
        } else {
            mapParameters.get(paraname.toUpperCase()).setValue(val);
        }

        if (val instanceof Number || val instanceof BigDecimal
                || val instanceof Float) {
            mapParameters.get(paraname).setValueType(Parameter.DATA_TYPE_NUMBER);
        }
        if (val instanceof java.sql.Date || val instanceof java.util.Date) {
            mapParameters.get(paraname).setValueType(Parameter.DATA_TYPE_DATE);
        }

        if (val instanceof java.sql.Timestamp) {
            mapParameters.get(paraname).setValueType(
                    Parameter.DATA_TYPE_DATETIME);
        }

    }

    public QueryExe() {
    }

    public QueryExe(String sql) {
        this.sqlStr = sql;
    }

    public QueryExe(Connection con) {
        this.con = con;
    }

    public QueryExe(String sql, Connection con) {
        this.con = con;
        this.sqlStr = sql;
    }

    public void execute() throws SQLException {
        execute(true);
    }

    public ResultSet executeRS() throws SQLException {
        return executeRS(true);
    }

    public void execute(String sql) {
        this.sqlStr = sql;
    }

    public void parse() throws SQLException {
        ps_exe = con.prepareStatement(utils.replaceParameters(sqlStr),
                ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    public void execute(boolean parse) throws SQLException {
        rowsAffected = -1;
        if (con == null) {
            return;
        }
        if (parse) {
            ps_exe = con.prepareStatement(utils.replaceParameters(sqlStr),
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        }
        utils.setParams(sqlStr, ps_exe, mapParameters);
        rowsAffected = ps_exe.executeUpdate();

    }

    public ResultSet executeRS(boolean parse) throws SQLException {
        ResultSet rsx = null;
        if (con == null) {
            return null;
        }
        if (parse) {
            ps_exe = con.prepareStatement(utils.replaceParameters(sqlStr),
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
        }
        utils.setParams(sqlStr, ps_exe, mapParameters);
        rsx = ps_exe.executeQuery();
        return rsx;

    }

    public void close() throws SQLException {
        if (ps_exe != null) {
            ps_exe.close();
        }
    }
        public List<qryColumn> getColumnsList() {
        List<qryColumn> lst = new ArrayList<qryColumn>();
        
        try {
            ResultSetMetaData rsm=rs.getMetaData();
            qryColumn q=null;
            for (int i = 0; i < rsm.getColumnCount(); i++) {
                q = new qryColumn(i + 1, rsm.getColumnName(i + 1));
                q.setWidth(rsm.getColumnDisplaySize(i + 1));
                q.setColpos(i);
                q.setColname(rsm.getColumnName(i + 1));
                q.setTitle(rsm.getColumnName(i + 1));
                q.setDatabaseCol(true);
                q.setDisplaySize(rsm.getColumnDisplaySize(i+1));
                q.setDatatype(rsm.getColumnType(i+1));

                if (rsm.getColumnType(i + 1) == 19 ||
                        rsm.getColumnType(i + 1) == 9 ||
                    rsm.getColumnType(i+1)==2) {

                    q.setAlignmnet(JLabel.RIGHT);
                }
                lst.add(q);
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryExe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lst;
    }


    public List<Row> convertRows() throws SQLException {
        return convertRows(true, "");
    }
    public List<Row> convertRows(boolean parse,
            String filterstring) throws SQLException {
        List<Row> lsr = new ArrayList<Row>();
        int tmp = 0;
        ResultSetMetaData rsm = null;
        boolean fnd = true;
        try {
            rs = executeRS(parse);
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
            Logger.getLogger(QueryExe.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
        return null;
    }
}
