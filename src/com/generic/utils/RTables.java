/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Yusuf
 */
public class RTables {

    public final static String RECTANGLE = "RECTANGLE";
    public final static String CIRCLE = "CIRCLE";
    public Rectangle rect = new Rectangle(1, 1, 100, 25);
    public Rectangle last_rect = new Rectangle(1, 1, 100, 25);
    public String Code = "";
    public double keyfld = -1;
    public String section_code = "";
    public String descr = "";
    public String TypeOfGraphics = RECTANGLE;
    public int pos_x = 0;
    public int pos_y = 0;
    public int width = 100;
    public int height = 25;
    public int preX, preY = 0;
    public Color textColor = Color.BLACK;
    public String strTextColor = "BLUE";
    public String strBackColor = "BLACK";
    public String strBorderColor = "GRAY";
    public Color backColor = Color.BLUE;
    public Color borderColor = Color.GRAY;
    public String canSelect = "Y";
    public RTablesCanvas rtc = null;
    public boolean isActive = false;
    public double active_keyfld = -1;

    public RTables(RTablesCanvas rtc) {
        this.rtc = rtc;
    }

    public void draw(Graphics2D g2, float s1, float s2, Color colorsel) {

        if (TypeOfGraphics.equals(RECTANGLE)) {
            g2.setStroke(new BasicStroke(s1));
            g2.setPaint(Color.black);
            g2.draw(last_rect);
            g2.fill(last_rect);
            last_rect.setBounds(rect);
            g2.setStroke(new BasicStroke(s2));
            if (colorsel != null) {
                g2.setColor(colorsel);
                g2.draw(rect);
            } else {
                g2.setColor(borderColor);
                g2.draw(rect);
            }
            Color cl = (isActive ? rtc.activeCol : backColor);
            g2.setColor(cl);
            g2.fill(rect);
            g2.setPaint(textColor);
            print_text(g2, rect, g2.getFont(), descr);
        }

        if (TypeOfGraphics.equals(CIRCLE)) {
            g2.setStroke(new BasicStroke(s1));
            g2.setPaint(Color.black);
            g2.drawOval(last_rect.x, last_rect.y, last_rect.width, last_rect.height);
            g2.fillOval(last_rect.x, last_rect.y, last_rect.width, last_rect.height);
            last_rect.setBounds(rect);
            g2.setStroke(new BasicStroke(s2));
            if (colorsel != null) {
                g2.setColor(colorsel);
                g2.drawOval(rect.x, rect.y, rect.width, rect.height);
            } else {
                g2.setColor(borderColor);
                g2.drawOval(rect.x, rect.y, rect.width, rect.height);
            }

            Color cl = (isActive ? rtc.activeCol : backColor);
            g2.setColor(cl);
            g2.fillOval(rect.x, rect.y, rect.width, rect.height);
            cl = (isActive ? rtc.activeColtEXT : textColor);
            g2.setPaint(cl);
            print_text(g2, rect, g2.getFont(), descr);
        }
    }

    public void print_text(Graphics2D g, Rectangle r, Font f, String s) {
        FontMetrics fm = g.getFontMetrics(f);
        Rectangle2D txt_rect = fm.getStringBounds(s, g);

        int textHeight = (int) (txt_rect.getHeight());
        int textWidth = (int) (txt_rect.getWidth());
        int panelHeight = r.height;
        int panelWidth = r.width;

        int x = (panelWidth - textWidth) / 2;
        int y = (panelHeight - textHeight) / 2 + fm.getAscent();
        g.drawString(s, r.x + x, r.y + y);

    }
}
