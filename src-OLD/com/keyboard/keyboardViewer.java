/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keyboard;

import com.forms.MainFrame;
import com.toedter.calendar.JDayChooser;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;

/**
 *
 * @author yusuf
 */
public class keyboardViewer {

    public static final String MODE_ALPHABET = "ALPHABETS";
    public static final String MODE_NUMBERS = "NUMBERS";
    public static final String MODE_SMALL_ALPHABETS = "SMALL_ALPHABETS";
    public static final String MODE_HIDE_SHOW = "SHOW/HIDE";
    public static final String MODE_CALENDAR = "CALENDAR";
    public static final String MODE_CLEAR_FIELD = "Clear Field";
    private JPanel parentPanel = null;
    private JPanel alphasPanel = null;
    private JPanel modesPanel = null;
    private MainFrame parentjf = null;
    private Map mpChars = new HashMap();
    private String currentMode = MODE_ALPHABET;
    private boolean caps = false;
    private JSplitPane splitpane = null;
    private int keyboardHideValue = 10;
    private JDayChooser jd = null;
    private ActionListener cmdActionLst = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            cmdAction(e);
        }
    };
    private ActionListener calendarAct = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
        }
    };
    private ActionListener cmdActionControlLst = new ActionListener() {

        public void actionPerformed(ActionEvent e) {
            cmdActionControl(e);
        }
    };
    private ActionListener cmdClearField = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             cmdActionControl(e);
        }
    };
    private ActionListener cmdModeLst = new ActionListener() {

        public void actionPerformed(ActionEvent e) {

            if (((JButton) e.getSource()).getName().equals(MODE_HIDE_SHOW)) {
                if (parentjf.getMapVars().get("show_hide_keyboard") != null) {
                    keyboardHideValue = Integer.valueOf(parentjf.getMapVars().get("show_hide_keyboard"));
                }
                if (parentPanel.getHeight() != keyboardHideValue) {
                    parentPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), keyboardHideValue));
                } else {
                    int h = Integer.valueOf(parentjf.getMapVars().get("keyboard_panel_height"));
                    parentPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), h));
                }
                parentPanel.updateUI();
                return;
            }
            setCurrentMode(((JButton) e.getSource()).getName());
        }
    };

    public boolean isCaps() {
        return caps;
    }

    public void hideKeyboardPanel() {
        parentPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), keyboardHideValue));
        parentPanel.updateUI();
    }

    public void showKeyboardPanel() {
        int h = Integer.valueOf(parentjf.getMapVars().get("keyboard_panel_height"));
        parentPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), h));
        parentPanel.updateUI();

    }

    public void setCaps(boolean caps) {
        this.caps = caps;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(String currentMode) {
        this.currentMode = currentMode;
        createView();
    }

    public MainFrame getParentjf() {
        return parentjf;
    }

    public void setParentjf(MainFrame p) {
        this.parentjf = p;
    }

    public JPanel getAlphasPanel() {
        return alphasPanel;
    }

    public void setAlphasPanel(JPanel alphasPanel) {
        this.alphasPanel = alphasPanel;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public keyboardViewer() {
    }

    public keyboardViewer(MainFrame mf) {
        this.parentjf = mf;
    }

    public keyboardViewer(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public void createView(JPanel jpanel) {
        parentPanel = jpanel;
        createView();
    }

    JButton addButton(String caption, String nm, ActionListener lst) {
        JButton but1 = null;
        but1 = new JButton();
        if (nm == null) {
            but1.setName(caption);
        } else {
            but1.setName(nm);
        }
        but1.setText(caption);
        but1.setFocusable(false);
        but1.setMargin(new Insets(5, 10, 5, 10));
        but1.addActionListener(lst);
        return but1;
    }

    public void showAlphabets(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }

        rowPanel.removeAll();

        ((GridLayout) rowPanel.getLayout()).setRows(3);
        ((GridLayout) rowPanel.getLayout()).setColumns(9);

        rowPanel.add(addButton("A", null, cmdActionLst));
        rowPanel.add(addButton("B", null, cmdActionLst));
        rowPanel.add(addButton("C", null, cmdActionLst));
        rowPanel.add(addButton("D", null, cmdActionLst));
        rowPanel.add(addButton("E", null, cmdActionLst));
        rowPanel.add(addButton("F", null, cmdActionLst));
        rowPanel.add(addButton("G", null, cmdActionLst));
        rowPanel.add(addButton("H", null, cmdActionLst));
        rowPanel.add(addButton("I", null, cmdActionLst));
        rowPanel.add(addButton("J", null, cmdActionLst));
        rowPanel.add(addButton("K", null, cmdActionLst));
        rowPanel.add(addButton("L", null, cmdActionLst));
        rowPanel.add(addButton("M", null, cmdActionLst));
        rowPanel.add(addButton("N", null, cmdActionLst));
        rowPanel.add(addButton("O", null, cmdActionLst));
        rowPanel.add(addButton("P", null, cmdActionLst));
        rowPanel.add(addButton("Q", null, cmdActionLst));
        rowPanel.add(addButton("R", null, cmdActionLst));
        rowPanel.add(addButton("S", null, cmdActionLst));
        rowPanel.add(addButton("T", null, cmdActionLst));
        rowPanel.add(addButton("U", null, cmdActionLst));
        rowPanel.add(addButton("V", null, cmdActionLst));
        rowPanel.add(addButton("W", null, cmdActionLst));
        rowPanel.add(addButton("X", null, cmdActionLst));
        rowPanel.add(addButton("Y", null, cmdActionLst));
        rowPanel.add(addButton("Z", null, cmdActionLst));
        rowPanel.add(addButton("DEL", null, cmdActionControlLst));
        rowPanel.add(addButton(" ", null, cmdActionLst));
        rowPanel.add(addButton("/", null, cmdActionLst));
    }

    public void showAlphabetsSmall(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }

        rowPanel.removeAll();

        ((GridLayout) rowPanel.getLayout()).setRows(3);
        ((GridLayout) rowPanel.getLayout()).setColumns(9);

        rowPanel.add(addButton("a", null, cmdActionLst));
        rowPanel.add(addButton("b", null, cmdActionLst));
        rowPanel.add(addButton("c", null, cmdActionLst));
        rowPanel.add(addButton("d", null, cmdActionLst));
        rowPanel.add(addButton("e", null, cmdActionLst));
        rowPanel.add(addButton("f", null, cmdActionLst));
        rowPanel.add(addButton("g", null, cmdActionLst));
        rowPanel.add(addButton("h", null, cmdActionLst));
        rowPanel.add(addButton("i", null, cmdActionLst));
        rowPanel.add(addButton("j", null, cmdActionLst));
        rowPanel.add(addButton("k", null, cmdActionLst));
        rowPanel.add(addButton("l", null, cmdActionLst));
        rowPanel.add(addButton("m", null, cmdActionLst));
        rowPanel.add(addButton("n", null, cmdActionLst));
        rowPanel.add(addButton("o", null, cmdActionLst));
        rowPanel.add(addButton("p", null, cmdActionLst));
        rowPanel.add(addButton("q", null, cmdActionLst));
        rowPanel.add(addButton("r", null, cmdActionLst));
        rowPanel.add(addButton("s", null, cmdActionLst));
        rowPanel.add(addButton("t", null, cmdActionLst));
        rowPanel.add(addButton("u", null, cmdActionLst));
        rowPanel.add(addButton("v", null, cmdActionLst));
        rowPanel.add(addButton("w", null, cmdActionLst));
        rowPanel.add(addButton("x", null, cmdActionLst));
        rowPanel.add(addButton("y", null, cmdActionLst));
        rowPanel.add(addButton("z", null, cmdActionLst));
        rowPanel.add(addButton("DEL", null, cmdActionControlLst));
        rowPanel.add(addButton(" ", null, cmdActionLst));
        rowPanel.add(addButton("/", null, cmdActionLst));

    }

    public void showNumbers(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }

        rowPanel.removeAll();
        ((GridLayout) rowPanel.getLayout()).setRows(4);
        ((GridLayout) rowPanel.getLayout()).setColumns(3);

        rowPanel.add(addButton("1", "1", cmdActionLst));
        rowPanel.add(addButton("2", "2", cmdActionLst));
        rowPanel.add(addButton("3", "3", cmdActionLst));
        rowPanel.add(addButton("4", null, cmdActionLst));
        rowPanel.add(addButton("5", null, cmdActionLst));
        rowPanel.add(addButton("6", null, cmdActionLst));
        rowPanel.add(addButton("7", null, cmdActionLst));
        rowPanel.add(addButton("8", null, cmdActionLst));
        rowPanel.add(addButton("9", null, cmdActionLst));
        rowPanel.add(addButton("0", null, cmdActionLst));
        rowPanel.add(addButton(".", null, cmdActionLst));
        rowPanel.add(addButton("DEL", null, cmdActionControlLst));
        rowPanel.add(addButton("/", null, cmdActionLst));
        rowPanel.add(addButton(" ", null, cmdActionLst));


    }

    public void showModes(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }
        //rowPanel.setMaximumSize(new Dimension(50,100));
        //rowPanel.setPreferredSize(new Dimension(50,100));
        ((GridLayout) rowPanel.getLayout()).setColumns(2);
        ((GridLayout) rowPanel.getLayout()).setRows(3);

        rowPanel.removeAll();
        rowPanel.add(addButton(MODE_HIDE_SHOW, MODE_HIDE_SHOW, cmdModeLst));
        rowPanel.add(addButton(MODE_ALPHABET, MODE_ALPHABET, cmdModeLst));
        rowPanel.add(addButton(MODE_NUMBERS, MODE_NUMBERS, cmdModeLst));
        rowPanel.add(addButton(MODE_SMALL_ALPHABETS, MODE_SMALL_ALPHABETS, cmdModeLst));
        rowPanel.add(addButton(MODE_CALENDAR, MODE_CALENDAR, cmdModeLst));
        rowPanel.add(addButton("Clear Field", MODE_CLEAR_FIELD, cmdClearField));

    }
    private MouseListener calendarlst = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
            actionPerformed(e);
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        public void actionPerformed(MouseEvent e) {
            if (parentjf.getFocusOwner() != null) {
                if (parentjf.getFocusOwner() instanceof JTextField &&
                        ((JTextField)parentjf.getFocusOwner()).isEditable()==true ) {
                    try {
                        JTextField j = (JTextField) parentjf.getFocusOwner();
                        Calendar cl = Calendar.getInstance();
                        cl.setTimeInMillis(jd.getMinSelectableDate().getTime());
                        cl.set(Calendar.DAY_OF_MONTH, jd.getDay());
                        SimpleDateFormat sdf = new SimpleDateFormat(parentjf.getMapVars().get("short_date_format"));
                        j.setText(sdf.format(cl.getTime()));
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    };

    public void showCalendar(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }
        ((GridLayout) rowPanel.getLayout()).setColumns(1);
        ((GridLayout) rowPanel.getLayout()).setRows(1);
        if (jd == null) {
            jd = new JDayChooser(true);

        }
        Calendar cl = Calendar.getInstance();
        cl.set(Calendar.DAY_OF_MONTH, 1);
        jd.setYear(cl.get(Calendar.YEAR));
        jd.setMonth(cl.get(Calendar.MONTH));
        jd.setDay(cl.get(Calendar.DAY_OF_MONTH));
        jd.setCalendar(cl);
        jd.setMinSelectableDate(cl.getTime());

        for (int i = 0; i < jd.getDayPanel().getComponentCount() - 1; i++) {
            if (jd.getDayPanel().getComponent(i) instanceof JButton) {
                ((JButton) jd.getDayPanel().getComponent(i)).setFocusable(false);
                ((JButton) jd.getDayPanel().getComponent(i)).addMouseListener(calendarlst);
            }
        }
        rowPanel.add(jd);
        //jd.setPreferredSize(new Dimension(rowPanel.getWidth(), rowPanel.getHeight()));
        jd.updateUI();
    }

    public void createView() {
        if (parentjf == null || parentPanel == null) {
            return;
        }

        parentPanel.removeAll();

        if (alphasPanel == null) {
            alphasPanel = new JPanel(new GridLayout(3, 1));

        } else {
            alphasPanel.removeAll();
            ((GridLayout) alphasPanel.getLayout()).setRows(3);
            ((GridLayout) alphasPanel.getLayout()).setColumns(1);
        }

        if (modesPanel == null) {
            modesPanel = new JPanel(new GridLayout(4, 1));
        } else {
            modesPanel.removeAll();
            ((GridLayout) modesPanel.getLayout()).setRows(4);
            ((GridLayout) modesPanel.getLayout()).setColumns(2);
        }

        showModes(modesPanel);
        if (currentMode.equals(MODE_ALPHABET)) {
            showAlphabets(alphasPanel);
        }

        if (currentMode.equals(MODE_NUMBERS)) {
            showNumbers(alphasPanel);
        }
        if (currentMode.equals(MODE_SMALL_ALPHABETS)) {
            showAlphabetsSmall(alphasPanel);
        }
        if (currentMode.equals(MODE_CALENDAR)) {
            showCalendar(alphasPanel);
        }
        if (splitpane == null) {
            splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, alphasPanel, modesPanel);
            splitpane.setDividerLocation(1000);
            if (parentjf.getMapVars().get("keyboard_split_divider") != null) {
                splitpane.setDividerLocation(Integer.valueOf(parentjf.getMapVars().get("keyboard_split_divider")));
            }
        }

        splitpane.setBounds(0, 0, parentPanel.getWidth() - 1, parentPanel.getHeight() - 1);
        //       parentPanel.add(alphasPanel);
        //     parentPanel.add(modesPanel);

        parentPanel.add(splitpane);
        splitpane.updateUI();
        parentPanel.updateUI();

    }

    public String insertString(
            String source, String istr, int offset) {
        String s = source, tmp1 = "", tmp2 = "";
        if (offset >= source.length()) {
            s = source + istr;
        } else {
            tmp1 = s.substring(0, offset);
            tmp2 = s.substring(offset, source.length());
            s = tmp1 + istr + tmp2;
        }

        return s;
    }

    private void cmdAction(java.awt.event.ActionEvent evt) {
        if (parentjf.getFocusOwner() != null) {
            if (parentjf.getFocusOwner() instanceof JTextField &&
                    ((JTextField) parentjf.getFocusOwner()).isEditable() == true) {
                try {
                    String txt = ((JButton) evt.getSource()).getName();
                    JTextField j = (JTextField) parentjf.getFocusOwner();
                    String s1 = j.getText();
                    int cp = j.getCaretPosition();
                    s1 =
                            insertString(s1, txt, cp);
                    j.setText(s1);
                    j.setCaretPosition(cp + 1);
                    if (j.getInputVerifier() != null) {
                        j.getInputVerifier().verify(j);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    private void cmdActionControl(java.awt.event.ActionEvent evt) {
        if (parentjf.getFocusOwner() != null) {
            if (parentjf.getFocusOwner() instanceof JTextField &&
                    ((JTextField) parentjf.getFocusOwner()).isEditable() == true) {
                try {
                    String txt = ((JButton) evt.getSource()).getName();
                    JTextField j = (JTextField) parentjf.getFocusOwner();
                    String s1 = j.getText();
                    int cp = j.getCaretPosition();
                    int start = j.getSelectionStart(), end = j.getSelectionEnd();
                    if (txt.equals("DEL")) {
                        s1 = s1.substring(0, start) + s1.substring(end, s1.length());
                    }
                    if (txt.equals(MODE_CLEAR_FIELD)) {
                        s1 = "";
                    }
                    j.setText(s1);
                    if (end <= s1.length()) {
                        j.setCaretPosition(end);
                    } else {
                        j.setCaretPosition(s1.length());
                    }
                    if (j.getInputVerifier() != null) {
                        j.getInputVerifier().verify(j);
                    }
                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

    }

    private void showItems(JPanel rowPanel) {
    }
}
