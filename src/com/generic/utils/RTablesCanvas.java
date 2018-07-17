/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.generic.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Yusuf
 */
public class RTablesCanvas extends javax.swing.JPanel implements MouseListener, MouseMotionListener, KeyListener {

    public List<RTables> listTables = new ArrayList<RTables>();
    public RTables st = null;
    public RTables last_st = null;
    private Graphics2D g2;
    public boolean isFirstTime = true;
    private Rectangle area;
    private boolean pressOut = false;
    private RTableTriggers rtrigger = null;
    public final static String MODE_USER = "MODE_USER";
    public final static String MODE_DESIGN = "MODE_DESIGN";
    private String system_mode = MODE_USER;
    public Color activeCol = Color.YELLOW;
    public Color activeColtEXT = Color.BLACK;

    public String getSystem_mode() {
        return system_mode;
    }

    public void setSystem_mode(String system_mode) {
        this.system_mode = system_mode;
    }

    public void setRTrigger(RTableTriggers r) {
        this.rtrigger = r;
    }

    public RTablesCanvas() {
        setBackground(Color.black);
        addMouseMotionListener(this);
        addMouseListener(this);
        addKeyListener(this);
        setRequestFocusEnabled(true);
    }

    public void mousePressed(MouseEvent e) {
        if (listTables.isEmpty()) {
            last_st = null;
            st = null;
            pressOut = true;
            return;
        }
        /*
        last_st=(st==null?last_st:st);
        st = null;
        if (rtrigger != null) {
        rtrigger.onSelectionOfTable(st);
        }
         */
        //st = null;

        for (int i = 0; i < listTables.size(); i++) {
            RTables rt = listTables.get(i);

            if (system_mode.equals(MODE_DESIGN)) {
                rt.preX = rt.rect.x - e.getX();
                rt.preY = rt.rect.y - e.getY();
            }
            if (system_mode.equals(MODE_USER) && !rt.canSelect.equals("Y")) {
                last_st = (st == null ? last_st : st);
                st = null;
                continue;
            }

            if (rt.rect.contains(e.getX(), e.getY())) {
                last_st = (st == null ? last_st : st);
                st = rt;
                pressOut = false;
                updateLocation(e);
                if (rtrigger != null) {
                    rtrigger.onSelectionOfTable(st);
                }
                return;
            }

        }
        last_st = (st == null ? last_st : st);
        st = null;
        if (rtrigger != null) {
            rtrigger.onSelectionOfTable(null);
        }        
        pressOut = true;
    }

    public void mouseDragged(MouseEvent e) {
        if (system_mode.equals(MODE_DESIGN) && !pressOut) {
            updateLocation(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (st == null) {
            pressOut = false;
            repaint();
            return;
        }

        if (st.rect.contains(e.getX(), e.getY())) {
            updateLocation(e);

        } else {
            pressOut = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        requestFocus();
        repaint();
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void updateLocation(MouseEvent e) {
        Dimension d = getSize();
        if (st == null) {
            return;
        }

        if (st.preX + e.getX() < 0 || st.preY + e.getY() < 0
                || st.preX + e.getX() > (d.width - st.rect.width)
                || st.preY + e.getY() > (d.height - st.rect.height)) {
            return;
        }
        if (system_mode.equals(MODE_DESIGN)) {
            st.rect.setLocation(st.preX + e.getX(), st.preY + e.getY());

            if (rtrigger != null) {
                rtrigger.onMovingTable(st, st.preX + e.getX(), st.preY + e.getY());
            }
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        update(g);
    }

    public void update(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Dimension dim = getSize();

        int w = (int) dim.getWidth();
        int h = (int) dim.getHeight();

        g2.setColor(Color.BLACK);
        g2.drawRect(0, 0, w, h);
        g2.drawRect(0, 0, w, h);

        for (int i = 0; i < listTables.size(); i++) {
            RTables rt = listTables.get(i);
            rt.draw(g2, 5, 5, null);
        }

        if (st != null) {
            st.draw(g2, 5, 5, Color.RED);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (st == null || system_mode.equals(MODE_USER)) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && e.isControlDown()) {
            int p = st.rect.getLocation().x;
            if ((p + 1) + st.rect.width > getSize().width) {
                return;
            }

            st.rect.setLocation(p + 1, st.rect.y);
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT && e.isControlDown()) {
            int p = st.rect.getLocation().x;
            if ((p - 1) < 0) {
                return;
            }
            st.rect.setLocation(p - 1, st.rect.y);
            repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN && e.isControlDown()) {
            int p = st.rect.getLocation().y;
            if ((p + 1) + st.rect.height > getSize().height) {
                return;
            }
            st.rect.setLocation(st.rect.x, p + 1);
            repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_UP && e.isControlDown()) {
            int p = st.rect.getLocation().y;
            if ((p - 1) < 0) {
                return;
            }
            st.rect.setLocation(st.rect.x, p - 1);
            repaint();
        }

        if (rtrigger != null) {
            rtrigger.onMovingTable(st, st.rect.x, st.rect.y);
        }

    }

    public void keyReleased(KeyEvent e) {
    }
}
