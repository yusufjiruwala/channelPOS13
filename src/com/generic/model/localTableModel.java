/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.model;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author yusuf
 * local table model buffered data in client pc and synchronize with server data in Row
 * class.
 */
public class localTableModel implements TableModel {

    /**
     *
     * @author yusuf
     * ros data can be initialized with number of column.
     * class.
     */
    public static final int SUMMARY_SUM = 0;
    public static final int SUMMARY_COUNT = 1;
    public static final int SUMMARY_MAX = 2;
    public static final int SUMMARY_MIN = 3;
    private List<String> headerText = new ArrayList<String>();
    private List<qryColumn> qrycols = new ArrayList<qryColumn>();
    private List<qryColumn> visbleQrycols = new ArrayList<qryColumn>();
    private List<Row> rows = new ArrayList<Row>();
    private List<Row> masterRows = new ArrayList<Row>();
    private List<Row> deletedRecs = new ArrayList<Row>();
    private String fetchSql = "";
    private String tableName = "";
    private String schemaName = "";
    private DBClass dbclass = null;
    private boolean editAllowed = true;
    private int cursorNo = 0;
    private String modelStatus = "NORMAL";
    private int deletedRows = 0;
    private int fetchrecs = 0;
    private rowTriggerListner rowlistner = null;
    private boolean rowsTriggerExecute = true;
    private String filterStr = "";
    private String dynamicFilter = "";
    private boolean filtering = false;

    public String getDynamicFilter() {
        return dynamicFilter;
    }

    public void setMasterRowsAsRows() {
        masterRows.clear();
        masterRows.addAll(rows);
    }

    public void setDynamicFilter(String dynamicFilter) throws Exception {
        this.dynamicFilter = dynamicFilter;
        filtering = true;
        try {
            rows.clear();
            boolean fnd = false;
            Row r = null;
            for (int i = 0; i < masterRows.size(); i++) {
                r = masterRows.get(i);
                fnd = false;
                for (int j = 0; j < r.lst.size(); j++) {
                    if (dynamicFilter == null || dynamicFilter.length() == 0 ||
                            r.lst.get(j).getDisplay().toUpperCase().contains(dynamicFilter.toUpperCase())) {
                        fnd = true;
                        break;
                    }
                }
                if (fnd) {
                    rows.add(r);
                }
            }
        } catch (Exception ex) {
            filtering = false;
            throw ex;
        }
    }

    public void setFilterbyCol(String colname,String dynamicFilter) throws Exception {
        this.dynamicFilter = dynamicFilter;
        int colno=getColByName(colname).getColpos();
        filtering = true;
        try {
            rows.clear();
            boolean fnd = false;
            Row r = null;
            for (int i = 0; i < masterRows.size(); i++) {
                r = masterRows.get(i);
                fnd = false;

                    if (dynamicFilter == null || dynamicFilter.trim().length() == 0 ||
                            r.lst.get(colno).getDisplay().toUpperCase().equals(dynamicFilter.toUpperCase())) {
                        fnd = true;                        
                    }
                if (fnd) {
                    rows.add(r);
                }
            }
        } catch (Exception ex) {
            filtering = false;
            throw ex;
        }
    }

    
    public List<Row> getMasterRows() {
        return masterRows;
    }

    public double getSummaryOf(String colname, int st) {
        double sum = 0;
        if (st == SUMMARY_SUM /*&& getColByName(colname).isNumber()*/) {
            for (int i = 0; i < rows.size(); i++) {
                sum = sum + ((Number) getFieldValue(i, colname)).doubleValue();
            }
        }
        return sum;
    }

    public String getFilterStr() {
        return filterStr;
    }

    public void setFilterStr(String filterStr) {
        this.filterStr = filterStr;
    }

    public boolean isRowsTriggerExecute() {
        return rowsTriggerExecute;
    }

    public rowTriggerListner getRowlistner() {
        return rowlistner;
    }

    public void setRowlistner(rowTriggerListner rowlistner) {
        this.rowlistner = rowlistner;
    }

    public List<Row> getDeletedRecs() {
        return deletedRecs;
    }

    public List<qryColumn> getVisbleQrycols() {
        return visbleQrycols;
    }

    public int getFetchrecs() {
        return fetchrecs;
    }

    public void setFetchrecs(int fetchrecs) {
        this.fetchrecs = fetchrecs;
    }

    public int getDeletedRows() {
        return deletedRows;
    }

    public String getModelStatus() {
        return modelStatus;
    }

    public int getCursorNo() {
        return cursorNo;
    }

    public void setCursorNo(int cursorNo) {
        this.cursorNo = cursorNo;
    }

    public DBClass getDbclass() {
        return dbclass;
    }

    public void setDbclass(DBClass dbc) {
        this.dbclass = dbc;
    }

    public DBClass createDBClassFromConnection(Connection c) throws SQLException {
        dbclass = new DBClass(c);
        return dbclass;
    }

    public int addRecord() {
        return insertRecord(rows.size());

    }

    public String getFetchSql() {
        return fetchSql;
    }

    public void setFetchSql(String fetchSql) {
        this.fetchSql = fetchSql;
    }

    public List<String> getHeaderText() {
        return headerText;
    }

    public void setHeaderText(List<String> headerText) {
        this.headerText = headerText;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public boolean isEditAllowed() {
        return editAllowed;
    }

    public void setEditAllowed(boolean editAllowed) {
        this.editAllowed = editAllowed;
    }

    public List<qryColumn> getQrycols() {
        return qrycols;
    }

    public Row findRow(int colpos, String vl) {
        Row rw = null;
        dataCell dc = null;
        for (int i = 0; i < rows.size(); i++) {
            rw = rows.get(i);
            dc = rw.lst.get(colpos);
            if (dc.getValue().equals(vl)) {
                return rw;
            }
        }
        return null;
    }

    public List<Row> getRows() {
        return rows;
    }
    private int cols = 0;
    private String sqlString = "";

    public void setCols(int cols) {
        this.cols = cols;
    }

    public localTableModel() {
        this.cols = 0;
    }

    public localTableModel(int cols) {
        this.cols = cols;
    }

    public int getRealColPositionNo(int po) {
        int rp = 0, i = 0;
        for (i = 0; i < qrycols.size(); i++) {
            if (qrycols.get(i).isVisible()) {
                rp++;
            }
            if (rp == po) {
                return i;
            }
        }
        return i;
    }

    public qryColumn getColByPos(int posno) {
        return visbleQrycols.get(posno);
    }

    public qryColumn getColByName(String name) {
        for (int i = 0; i < qrycols.size(); i++) {
            if (qrycols.get(i).getColname().toUpperCase().equals(name.toUpperCase())) {
                return qrycols.get(i);
            }
        }
        return null;
    }

    public int setColumnVisible(qryColumn qc, boolean f) {
        visbleQrycols.remove(qc);
        if (f) {
            visbleQrycols.add(qc);
        }
        qc.setVisible(f);
        return 0;

    }

    public String getHeaderText(int no) {
        return visbleQrycols.get(no).getTitle();
    }

    public void addHeaderText(String tit) {
        headerText.add(tit);
    }

    public void setHeaderText(int no, String tit) {
        if (no >= headerText.size()) {
            headerText.add(tit);
        } else {
            headerText.set(no, tit);
        }
    }

    public int getHeaderCount() {
        return headerText.size();
    }

    public void fetchData(int nextNos) throws SQLException {
        int tmp = 0;
        if (fetchSql.length() > 0) {
            if (dbclass.getStatment() != null) {
                dbclass.getStatment().close();
            }
            dbclass.setSqlString(fetchSql);
            dbclass.parseStatment();
            dbclass.executeStatment();
            ResultSetMetaData rm = dbclass.getResultset().getMetaData();
            cols = rm.getColumnCount();
            qrycols.clear();
            qrycols.addAll(dbclass.getColumnsList());
            appendRows(dbclass.convertRows(filterStr));
            cursorNo = rows.size() - 1;
        }
    }

    public void fetchData() throws SQLException {
        fetchData(fetchrecs);
    }

    public void executeQuery(String sq, boolean visibleAll) throws SQLException {
        clearALl();
        fetchSql = sq;
        fetchData();
        setAllColVisible(visibleAll);
    }

    public void executeQuery(String sq, String[] visibleCol) throws SQLException {
        executeQuery(sq, false);
        for (int i = 0; i < visibleCol.length; i++) {
            setColumnVisible(getColByName(visibleCol[i]), true);
        }

    }

    public void appendRows(List<Row> rw) {
        if (rw == null) {
            return;
        }
        if (this.rows != null) {
            //this.rows.clear();
            for (int i = 0; i < rw.size(); i++) {
                Row r = rw.get(i);
                Row newr = new Row(0);
                newr.rowno = r.rowno;
                newr.cols = r.cols;
                if (newr.cols > this.cols) {
                    this.cols = newr.cols;
                }
                for (int j = 0; j < r.lst.size(); j++) {
                    newr.lst.add(r.lst.get(j));
                }
                this.rows.add(newr);
                this.masterRows.add(newr);
                cursorNo = rows.indexOf(newr);
                if (rowlistner != null && rowsTriggerExecute == true) {
                    try {
                        rowsTriggerExecute = false;
                        rowlistner.onCalc(rows.indexOf(newr));
                    } finally {
                        rowsTriggerExecute = true;
                    }
                }
            }
        }
    }

    public void setSqlString(String s) {
        this.fetchSql = s;
    }

    public String getSqlString() {
        return this.fetchSql;
    }

    public int getRowCount() {
        if (rows == null) {
            return 0;
        }
        return rows.size() - deletedRows;
    }

    public int getColumnCount() {
        return visbleQrycols.size();
    }

    public String getColumnName(int columnIndex) {

        return getHeaderText(columnIndex);
    }

    public Class<?> getColumnClass(int columnIndex) {
        return visbleQrycols.get(columnIndex).getColClass();
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (isEditAllowed() == false) {
            return false;
        }
        if (getColByPos(columnIndex).isCanEdit() == false) {
            return false;
        }

        return rows.get(rowIndex).lst.get(columnIndex).canEdit();
    }

    public Row getVisibleRow(int rowindex) {
        int vi = 0, i = 0;
        for (i = 0; i < rows.size(); i++) {
            if (!rows.get(i).getRowStatus().equals(Row.ROW_DELETED)) {
                vi++;
            }
            if ((vi - 1) == rowindex) {
                return rows.get(i);
            }
        }
        return null;
    }

    public void setAllColVisible(boolean f) {
        visbleQrycols.clear();
        for (int i = 0; i < qrycols.size(); i++) {
            qrycols.get(i).setVisible(f);
            if (f) {
                visbleQrycols.add(qrycols.get(i));
            }
        }
    }

    public Object visibleColValue(Row r, int colIndex) {
        if (colIndex > visbleQrycols.size() || visbleQrycols.get(colIndex) == null) {
            return null;
        }
        return r.lst.get(visbleQrycols.get(colIndex).getColpos());
    }

    public Object getValueAt(int rowIndex, int columnIndex) {

        if (rowIndex > getRows().size() || getRows().size() == 0) {
            return null;
        }

        Row r = getRows().get(rowIndex); //getVisibleRow(rowIndex);
        if (r == null) {
            return null;
        }

        Object s = visibleColValue(r, columnIndex);
        if (visbleQrycols.get(columnIndex).getNumberFormat().length() > 0) {
            if (s != null && ((dataCell) s).getValue() != null && ((dataCell) s).getValue().toString().length() > 0) {
                s = (new DecimalFormat(visbleQrycols.get(columnIndex).getNumberFormat()).format(Double.valueOf(((dataCell) s).getValue().toString())));
            }
        }

        if (visbleQrycols.get(columnIndex).getDateFormat().length() > 0) {
            if (s != null && ((dataCell) s).getValue() != null) {
                s = (new SimpleDateFormat(visbleQrycols.get(columnIndex).getDateFormat()).format((java.sql.Timestamp) ((dataCell) s).getValue()));
            }
        }

        return s;
    }

    public String getDisplayAt(int rowIndex, int columnIndex) {
        Row r = rows.get(rowIndex);
        String s = r.lst.get(columnIndex).getDisplay();
        return s;
    }

    public void setFieldValue(int rowno, String colname, Object value) {
        rows.get(rowno).lst.get(getColByName(colname).getColpos()).setValue(value.toString(), value);
        if (rowlistner != null && rowsTriggerExecute == true) {
            try {
                rowsTriggerExecute = false;
                rowlistner.onCalc(rowno);
            } finally {
                rowsTriggerExecute = true;
            }
        }

    }

    public void setFieldValue(String colname, Object value) {
        setFieldValue(cursorNo, colname, value);
    }

    public Object getFieldValue(int rowno, String colname) {
        return rows.get(rowno).lst.get(getColByName(colname).getColpos()).getValue();
    }

    public Object getFieldValue(String colname) {
        return getFieldValue(cursorNo, colname);
    }

    public void clearALl() {
        for (int i = 0; i < masterRows.size(); i++) {
            masterRows.get(i).lst.clear();
        }
        rows.clear();
        masterRows.clear();
        qrycols.clear();
        visbleQrycols.clear();
        headerText.clear();
        cols = 0;
        cursorNo = -1;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Row r = rows.get(rowIndex);
        dataCell s = r.lst.get(columnIndex);
        s.setValue(aValue, aValue);
    }

    public void setDisplayAt(String aValue, int rowIndex, int columnIndex) {
        Row r = rows.get(rowIndex);
        dataCell s = r.lst.get(columnIndex);
        s.setDisplay(aValue.toString());
    }

    public void addTableModelListener(itableModelListener l) {
    }

    public void removeTableModelListener(itableModelListener l) {
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public void removeTableModelListener(TableModelListener l) {
    }

    public int insertRecord(int index) {
        Row r = new Row(cols);
        r.setRowStatus(Row.ROW_INSERTED);
        for (int i = 0; i < cols; i++) {
            r.lst.get(i).setValue(qrycols.get(i).getDefaultValue(), qrycols.get(i).getDefaultDisplay());
        }
        if (index == rows.size()) {
            rows.add(r);
        } else {
            rows.add(index, r);
        }
        cursorNo = rows.indexOf(r);
        if (rowlistner != null && rowsTriggerExecute == true) {
            try {
                rowsTriggerExecute = false;
                rowlistner.onCalc(cursorNo);
            } finally {
                rowsTriggerExecute = true;
            }
        }
        return rows.indexOf(r);
    }

    public void moveFirst() {
        if (rows.size() > 0) {
            cursorNo = 0;
        } else {
            cursorNo = -1;
        }
    }

    public void moveNext() {
        if (rows.size() < cursorNo + 1) {
            cursorNo++;
        }

    }

    public void movePrior() {
        if (cursorNo - 1 > 0) {
            cursorNo--;
        }

    }
    public static final int FIND_LIKE = 0;
    public static final int FIND_EXACT = 1;

    public int locate(String col, String vl, int findOption) {
        for (int i = 0; i < rows.size(); i++) {
            if (findOption == FIND_EXACT && getFieldValue(i, col).toString().equals(vl)) {
                cursorNo = i;
                return i;
            }
            if (findOption == FIND_LIKE && getFieldValue(i, col).toString().toUpperCase().contains(vl)) {
                cursorNo = i;
                return i;
            }

        }
        return -1;
    }

    public int locate(String col, double v) {
        for (int i = 0; i < rows.size(); i++) {
            if (getFieldValue(i, col) == null) {
                continue;
            }
            double d = Double.valueOf(getFieldValue(i, col).toString());
            if (d == v) {
                cursorNo = i;
                return i;
            }
        }
        return -1;
    }

    public void deleteRow(int rowno) {
        Row r = rows.get(rowno);
//        if (r.getRowStatus().equals(Row.ROW_INSERTED)) {
        cursorNo = rows.indexOf(r);
        rows.remove(r);
        /*
        } else {
        deletedRecs.add(r);
        deletedRows++;
        r.setRowStatus(Row.ROW_DELETED);
        cursorNo = rows.indexOf(r);
        rows.remove(r);
        }
         * 
         */
        if (cursorNo >= rows.size()) {
            cursorNo = rows.size() - 1;
            if (cursorNo < 0 && rows.size() > 0) {
                cursorNo = 0;
            }
            if (rowlistner != null && rowsTriggerExecute == true) {
                try {
                    rowsTriggerExecute = false;
                    rowlistner.onCalc(cursorNo);
                } finally {
                    rowsTriggerExecute = true;
                }
            }
        }
        if (rows.size() == 0) {
            cursorNo = -1;
        }

    }

    public void deleteRow() {
        if (cursorNo < 0) {
            return;
        }
        deleteRow(cursorNo);
    }
}
