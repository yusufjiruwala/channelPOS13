/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.model;

import java.awt.Color;
import java.sql.Types;
import javax.swing.JLabel;

/**
 *
 * @author yusuf
 */
public class qryColumn {

    public final static String INPUT = "INPUT";
    public final static String OUTPUT = "OUTPUT";
    public final static String LIST = "LIST";
    public final static String SELECT_LIST = "SELECT_LIST";
    private int colpos = -1;
    private String colname = "";
    private String compClass = OUTPUT;
    private String style = "";
    private String styleclass = "";
    private Class<?> colClass = String.class;
    private String numberFormat = "";
    private String dateFormat = "";
    private boolean visible = true;
    private Color color = Color.WHITE;
    private String title = "Col";
    private Object defaultValue = null;
    private String defaultDisplay = null;
    private boolean databaseCol = true;
    private String tableColName = "";
    private int alignmnet = JLabel.LEADING;
    private int displaySize = 0;
    private int datatype = 0;
    private boolean canEdit=true;

    public boolean isCanEdit() {
        return canEdit;
    }

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    
    public int getDatatype() {
        return datatype;
    }

    public void setDatatype(int datatype) {
        this.datatype = datatype;
    }

    public boolean isNumber() {
        if (datatype == 19 ||
                datatype == 9 ||
                datatype == 2) {
            return true;
        }
        return false;

    }
    public boolean isDate() {
        if (datatype == Types.DATE ||
                datatype == Types.TIME ||
                datatype == Types.TIMESTAMP) {
            return true;
        }
        return false;

    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public int getAlignmnet() {
        return alignmnet;
    }

    public void setAlignmnet(int alignmnet) {
        this.alignmnet = alignmnet;
    }

    public String getTableColName() {
        return tableColName;
    }

    public void setTableColName(String tableColName) {
        this.tableColName = tableColName;
    }

    public boolean isDatabaseCol() {
        return databaseCol;
    }

    public void setDatabaseCol(boolean databaseCol) {
        this.databaseCol = databaseCol;
    }

    public String getDefaultDisplay() {
        return defaultDisplay;
    }

    public void setDefaultDisplay(String defaultDisplay) {
        this.defaultDisplay = defaultDisplay;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        this.defaultDisplay = defaultValue.toString();
    }

    public String getCompClass() {
        return this.compClass;
    }

    public void setCompClass(String compClass) {
        this.compClass = compClass;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleclass() {
        return styleclass;
    }

    public void setStyleclass(String styleclass) {
        this.styleclass = styleclass;
    }

    public String getColname() {
        return colname;
    }

    public void setColname(String colname) {
        this.colname = colname;
    }

    public int getColpos() {
        return colpos;
    }

    public void setColpos(int colpos) {
        this.colpos = colpos;
    }
    private int width = 30;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public qryColumn(qryColumn qq) {
        colClass = qq.colClass;
        colname = qq.colname;
        color = qq.color;
        colpos = qq.colpos;
        title = qq.title;
        width = qq.width;
        visible = qq.visible;
    }

    public qryColumn() {
    }

    public qryColumn(int no, String title) {
        setColpos(no);
        setColname(title);
        setTitle(title);
    }

    public Class<?> getColClass() {
        return colClass;
    }
}
