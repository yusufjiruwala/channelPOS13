/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.utils;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.Page;
import ar.com.fdvs.dj.domain.constants.Transparency;

public class DynamicReportSetting {

    public static final int PORTRAIT = 1;
    public static final int LANDSCAPE = 2;
    public static final int AUTO_ORIENTATION = 3;
    public static final int CUSTOM_ORIENTATION = 3;
    public static final int PORTRAIT_PAGE_HEIGHT = 842;
    public static final int PORTRAIT_PAGE_WIDTH = 595;
    private int defaultMaxColPerPortrait = 7;
    private int defaultMaxColPerLandscape = 12;
    private String title = "";
    private String subTitle = "";
    private int orientation = AUTO_ORIENTATION;
    private Style styleTitle = new Style();
    private Style styleFooter = new Style();
    private Style styleColumnHeader = new Style();
    private Style styleDetail = new Style();
    private Style styleNumber = new Style();
    private Style styleNumber2 = new Style();
    private Style styleHeader = new Style();
    private Style styleGroup = new Style();
    private Style styleOddRow = new Style();
    private Font titleFont = new Font(8, "DejaVu Sans", "Helvetica",
            Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing, true);
    private Font detailFont = new Font(8, "DejaVu Sans", "Helvetica",
            Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing, true);
    private Font groupFont = new Font(8, "DejaVu Sans", "Helvetica",
            Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing, true);
    private Font headerFont = new Font(8, "DejaVu Sans", "Helvetica",
            Font.PDF_ENCODING_Identity_H_Unicode_with_horizontal_writing, true);
    private boolean allowbackgroundoddrow = true;
    private Color oddBackGroundColor = new Color(230, 230, 230);
    private int pageHeight = PORTRAIT_PAGE_WIDTH;
    private int pageWidth = PORTRAIT_PAGE_HEIGHT;
    private int marginTop = 0;
    private int marginBottom = 0;
    private int marginLeft = 5;
    private int marginRight = 5;
    private Color titleForeColor = new Color(0, 64, 64);
    private Color titleBackColor = new Color(255, 255, 255);
    private boolean ignorePagination = false;

    public void setIgnorePagination(boolean b) {
        ignorePagination = b;
    }

    public boolean isIgnorePagination() {
        return ignorePagination;
    }

    public Style getStyleNumber() {
        return styleNumber;
    }

    public Style getStyleNumber2() {
        return styleNumber;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Font getDetailFont() {
        return detailFont;
    }

    public Font getGroupFont() {
        return groupFont;
    }

    public Font getHeaderFont() {
        return headerFont;
    }

    public Style getStyleHeader() {
        return styleHeader;
    }

    public void setStyleHeader(Style styleHeader) {
        this.styleHeader = styleHeader;
    }

    public Style getStyleGroup() {
        return styleGroup;
    }

    public void setStyleGroup(Style styleGroup) {
        this.styleGroup = styleGroup;
    }

    public Color getTitleForeColor() {
        return titleForeColor;
    }

    public void setTitleForeColor(Color titleForeColor) {
        this.titleForeColor = titleForeColor;
    }

    public Color getTitleBackColor() {
        return titleBackColor;
    }

    public void setTitleBackColor(Color titleBackColor) {
        this.titleBackColor = titleBackColor;
    }

    public int getMarginBottom() {
        return marginBottom;
    }

    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginRight() {
        return marginRight;
    }

    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }
    private List<Parameter> listParams = new ArrayList<Parameter>();

    public List<Parameter> getListParams() {
        return listParams;
    }

    public Color getOddBackGroundColor() {
        return oddBackGroundColor;
    }

    public void setOddBackGroundColor(Color oddBackGroundColor) {
        this.oddBackGroundColor = oddBackGroundColor;
    }

    public int getPageHeight() {
        return pageHeight;
    }

    public void setPageHeight(int pageHeight) {
        this.pageHeight = pageHeight;
    }

    public int getPageWidth() {
        return pageWidth;
    }

    public void setPageWidth(int pageWidth) {
        this.pageWidth = pageWidth;
    }

    public int getDefaultMaxColPerPortrait() {
        return defaultMaxColPerPortrait;
    }

    public void setDefaultMaxColPerPortrait(int defaultMaxColPerPortrait) {
        this.defaultMaxColPerPortrait = defaultMaxColPerPortrait;
    }

    public int getDefaultMaxColPerLandscape() {
        return defaultMaxColPerLandscape;
    }

    public void setDefaultMaxColPerLandscape(int defaultMaxColPerLandscape) {
        this.defaultMaxColPerLandscape = defaultMaxColPerLandscape;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public Style getStyleTitle() {
        return styleTitle;
    }

    public void setStyleTitle(Style styleTitle) {
        this.styleTitle = styleTitle;
    }

    public Style getStyleFooter() {
        return styleFooter;
    }

    public void setStyleFooter(Style styleFooter) {
        this.styleFooter = styleFooter;
    }

    public Style getStyleColumnHeader() {
        return styleColumnHeader;
    }

    public void setStyleColumnHeader(Style styleColumnHeader) {
        this.styleColumnHeader = styleColumnHeader;
    }

    public Style getStyleDetail() {
        return styleDetail;
    }

    public void setStyleDetail(Style styleDetail) {
        this.styleDetail = styleDetail;
    }

    public Style getStyleOddRow() {
        return styleOddRow;
    }

    public void setStyleOddRow(Style styleOddRow) {
        this.styleOddRow = styleOddRow;
    }

    public boolean isAllowbackgroundoddrow() {
        return allowbackgroundoddrow;
    }

    public void setAllowbackgroundoddrow(boolean allowbackgroundoddrow) {
        this.allowbackgroundoddrow = allowbackgroundoddrow;
    }

    public DynamicReportSetting() {
        styleOddRow.setTransparency(Transparency.OPAQUE);
    }

    public DynamicReportSetting(String title) {
        this.title = title;

    }

    public void doStandard() {
        styleHeader.setBackgroundColor(Color.lightGray);
        styleHeader.setFont(headerFont);

        styleColumnHeader.setBackgroundColor(Color.lightGray);
        styleColumnHeader.setFont(headerFont);
        styleColumnHeader.getFont().setUnderline(true);
        styleColumnHeader.setHorizontalAlign(HorizontalAlign.CENTER);

        styleNumber.setHorizontalAlign(HorizontalAlign.RIGHT);
        styleNumber.setFont(detailFont);

        styleNumber2.setHorizontalAlign(HorizontalAlign.RIGHT);
        styleNumber2.setFont(detailFont);
        styleNumber2.getFont().setBold(true);

        styleDetail.setFont(detailFont);

        styleGroup.setFont(groupFont);
        styleGroup.setBackgroundColor(Color.YELLOW);
        styleGroup.getFont().setBold(true);

        styleTitle.setBackgroundColor(titleBackColor);
        styleTitle.setTextColor(titleForeColor);
        styleTitle.setFont(titleFont);

        styleOddRow.setTransparency(Transparency.OPAQUE);
        styleOddRow.setBackgroundColor(oddBackGroundColor);
    }

    public void wrapReport(DynamicReportBuilder drb, boolean dostandard) {
        if (dostandard) {
            doStandard();
        }
        wrapReport(drb);
    }

    public void wrapReport(DynamicReportBuilder drb) {
        drb.setTitleStyle(styleTitle);
        drb.setDefaultStyles(styleTitle, styleTitle, styleColumnHeader,
                styleDetail);

        boolean portrait = ((orientation == PORTRAIT || orientation== CUSTOM_ORIENTATION ) ? true : false);
        if (!portrait) {
            if (orientation != CUSTOM_ORIENTATION) {
                pageHeight = PORTRAIT_PAGE_WIDTH;
                pageWidth = PORTRAIT_PAGE_HEIGHT;
            }
        }
        drb.setPageSizeAndOrientation(new Page(pageHeight, pageWidth,
                portrait));
        drb.setIgnorePagination(ignorePagination);
        // drb.setTitle(title);
        drb.addAutoText(title, AutoText.POSITION_HEADER,
                AutoText.ALIGMENT_RIGHT, pageWidth, styleTitle);
        drb.setMargins(marginTop, marginBottom, marginLeft, marginRight);
        for (Iterator iterator = listParams.iterator(); iterator.hasNext();) {
            Parameter pr = (Parameter) iterator.next();
            String vl = "";
            if (pr.getValue() != null) {
                vl = pr.getValue().toString();
            }
            if (pr.getValue() != null
                    && pr.getValueType().equals(Parameter.DATA_TYPE_DATE)) {
                vl = (new SimpleDateFormat(utils.FORMAT_SHORT_DATE)).format((Date) pr.getValue());
            }
            if (pr.getValueDescription() != null
                    && pr.getValueDescription().length() > 0) {
                vl = pr.getValueDescription();
            }
            drb.addAutoText(pr.getDescription() + " : " + vl,
                    AutoText.POSITION_HEADER, AutoText.ALIGMENT_LEFT,
                    pageWidth,styleHeader);
        }
        drb.setPrintBackgroundOnOddRows(true);
        drb.setOddRowBackgroundStyle(styleOddRow);
        drb.setUseFullPageWidth(true);
    }
}
