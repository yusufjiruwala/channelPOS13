/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.keyboard;

import com.forms.MainFrame;
import com.generic.utils.utils;
import com.toedter.calendar.JCalendar;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.KeyboardFocusManager;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

/**
 *
 * @author yusuf
 */
public class keyboardViewer {

    public static final String MODE_ALPHABET = "ALPHABETS";
    public static final String MODE_NUMBERS = "NUMBERS";
    public static final String MODE_SMALL_ALPHABETS = "SMALL_ALPHABETS";
    public static final String MODE_HIDE_SHOW = "HIDE";
    public static final String MODE_CALENDAR = "CALENDAR";
    public static final String MODE_CLEAR_FIELD = "CLEAR";
    public static final String MODE_NEXT_FIELD = "NEXT_FIELD";
    public static final String MODE_ENTER_FIELD = "ENTER_KEY";
    private JPanel parentPanel = null;
    private JPanel alphasPanel = null;
    private JPanel modesPanel = null;
    private JFrame parentjf = null;
    private Map mpChars = new HashMap();
    private String currentMode = MODE_ALPHABET;
    private boolean caps = false;
    private JSplitPane splitpane = null;
    private int keyboardHideValue = 10;
    public JCalendar jd = null;
    public boolean showModePanel = true;
    public boolean showNumbers = true;
    public boolean showAlpha = true;
    public boolean showClearField = true;
    public boolean showNextField = true;
    public boolean showEnterField = true;
    public boolean showCalendarPanel = true;
    public boolean showHideShowCmd = true;
    public KeyBoardSelectionListner kb_listner = null;

    public void setShowPanels(String allPanelCode) {
        showModePanel = (allPanelCode.indexOf("MODE_PANEL") < 0 ? false : true);
        showNumbers = (allPanelCode.indexOf(MODE_NUMBERS) < 0 ? false : true);
        showAlpha = (allPanelCode.indexOf(MODE_ALPHABET) < 0 ? false : true);
        showClearField = (allPanelCode.indexOf(MODE_CLEAR_FIELD) < 0 ? false : true);
        showNextField = (allPanelCode.indexOf(MODE_NEXT_FIELD) < 0 ? false : true);
        showEnterField = (allPanelCode.indexOf(MODE_ENTER_FIELD) < 0 ? false : true);
        showCalendarPanel = (allPanelCode.indexOf(MODE_CALENDAR) < 0 ? false : true);
        showHideShowCmd = (allPanelCode.indexOf(MODE_CALENDAR) < 0 ? false : true);
        createView();
    }
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

            if (parentjf != null && parentjf instanceof MainFrame
                    && ((JButton) e.getSource()).getName().equals(MODE_HIDE_SHOW)) {
                if (utils.getMainFrame().getMapVars().get("show_hide_keyboard") != null) {
                    keyboardHideValue = Integer.valueOf(utils.getMainFrame().getMapVars().get("show_hide_keyboard"));
                }

                if (parentPanel.getHeight() != keyboardHideValue) {
                    parentPanel.setPreferredSize(new Dimension(parentPanel.getWidth(), keyboardHideValue));
                } else {
                    int h = Integer.valueOf(utils.getMainFrame().getMapVars().get("keyboard_panel_height"));
                    parentPanel.setPreferredSize(
                            new Dimension(parentPanel.getWidth(), h));
                }
                parentPanel.updateUI();
                return;
            }
            if (parentjf != null && !(parentjf instanceof MainFrame)
                    && ((JButton) e.getSource()).getName().equals(MODE_HIDE_SHOW)) {
                (parentjf).setVisible(false);
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
        int h = Integer.valueOf(utils.getMainFrame().getMapVars().get("keyboard_panel_height"));
        parentPanel.setPreferredSize(
                new Dimension(parentPanel.getWidth(), h));
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

    public JFrame getParentjf() {
        return parentjf;
    }

    public void setParentjf(JFrame p) {
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
        but1.setMargin(new Insets(0, 0, 0, 0));
        but1.addActionListener(lst);
        but1.setFocusable(false);
        but1.setOpaque(true);
        //but1.setActionCommand(nm);
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
        rowPanel.add(addButton("ENTER", null, cmdActionControlLst));
        rowPanel.add(addButton("BACK", null, cmdActionControlLst));
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
        rowPanel.add(addButton("ENTER", null, cmdActionControlLst));
        rowPanel.add(addButton("BACK", null, cmdActionControlLst));
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
        rowPanel.add(addButton("ENTER", null, cmdActionControlLst));
        rowPanel.add(addButton("BACK", null, cmdActionControlLst));
        rowPanel.add(addButton("/", null, cmdActionLst));
        rowPanel.add(addButton(" ", null, cmdActionLst));


    }

    public void showModes(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }

        //((GridLayout) rowPanel.getLayout()).setColumns(6);
        //((GridLayout) rowPanel.getLayout()).setRows(1);

        //rowPanel.removeAll();
        JButton[] c = new JButton[6];

        c[0] = (showHideShowCmd ? addButton(MODE_HIDE_SHOW, MODE_HIDE_SHOW, cmdModeLst) : null);
        c[1] = (showClearField ? addButton("Clear Field", MODE_CLEAR_FIELD, cmdClearField) : null);
        c[2] = (showNumbers ? addButton(MODE_NUMBERS, MODE_NUMBERS, cmdModeLst) : null);
        c[3] = (showAlpha ? addButton(MODE_SMALL_ALPHABETS, MODE_SMALL_ALPHABETS, cmdModeLst) : null);
        c[4] = (showCalendarPanel ? addButton(MODE_CALENDAR, MODE_CALENDAR, cmdModeLst) : null);
        c[5] = (showAlpha ? addButton(MODE_ALPHABET, MODE_ALPHABET, cmdModeLst) : null);
        currentMode = "";
        for (int i = 0; i < c.length; i++) {
            Component cc = (c[i] != null ? rowPanel.add(c[i]) : null);
            if (c[i] != null && !c[i].getName().equals(MODE_CLEAR_FIELD) && !c[i].getName().equals(MODE_HIDE_SHOW)) {
                currentMode = c[i].getName();
            }
        }


    }

     private MouseListener calendarlst = new MouseListener() {
        public void mouseClicked(MouseEvent e) {
            
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
            actionPerformed(e);
        }

        public void mouseEntered(MouseEvent e) {
        }

        ;

        ;

        public void mouseExited(MouseEvent e) {
        }

        public void actionPerformed(MouseEvent e) {
            JTextComponent tb = null;
            if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != null
                    && KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() instanceof JTextComponent) {
                tb = (JTextComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
            }

            if (tb != null) {
                if (tb instanceof JTextField
                        && tb.isEditable() == true) {
                    try {
                        String txt = ((JButton) e.getSource()).getName();
                        JTextField j = (JTextField) tb;
                        Calendar cl = Calendar.getInstance();
                        cl.setTimeInMillis(jd.getCalendar().getTimeInMillis());
                        cl.set(Calendar.DAY_OF_MONTH, jd.getDayChooser().getDay());
                        SimpleDateFormat sdf = new SimpleDateFormat(utils.getMainFrame().getMapVars().get("short_date_format"));
                        j.setText(sdf.format(cl.getTime()));
                    } catch (Exception ex) {
                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            if (kb_listner != null) {
                SimpleDateFormat sdf = new SimpleDateFormat(utils.getMainFrame().getMapVars().get("short_date_format"));
                Calendar cl = Calendar.getInstance();
                cl.setTimeInMillis(jd.getCalendar().getTimeInMillis());
                kb_listner.OnKeyPress(sdf.format(cl.getTime()), getCurrentMode(), cl, true);
            }

        }
    };
    public JPanel pnl_calendar = new JPanel();
    public JButton cmdNextMonth = new JButton("Next Month");
    public JButton cmdPrevMonth = new JButton("Prev.Month");
    public JSplitPane pn_calendar = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    public JLabel lblMonth = new JLabel();
    public JLabel lblYear = new JLabel();

    public void showCalendar(JPanel rowPanel) {
        if (rowPanel == null) {
            return;
        }
        ((GridLayout) rowPanel.getLayout()).setColumns(1);
        ((GridLayout) rowPanel.getLayout()).setRows(1);
        Calendar cl = Calendar.getInstance();
        if (jd == null) {
            jd = new JCalendar(cl);
            cl.set(Calendar.DAY_OF_MONTH, 1);
            jd.getDayChooser().setYear(cl.get(Calendar.YEAR));
            jd.getDayChooser().setMonth(cl.get(Calendar.MONTH));
            jd.getDayChooser().setDay(cl.get(Calendar.DAY_OF_MONTH));
            jd.getDayChooser().setCalendar(cl);
            jd.getMonthChooser().setMonth(cl.get(Calendar.MONTH));
            jd.getYearChooser().setYear(cl.get(Calendar.YEAR));
            jd.getDayChooser().setFocusable(false);
            jd.getDayChooser().setMinSelectableDate(cl.getTime());
            lblMonth.setText(((JComboBox) jd.getMonthChooser().getComboBox()).getSelectedItem().toString());
            lblYear.setText(jd.getYearChooser().getYear() + "");
            cmdNextMonth.setFocusable(false);
            jd.getMonthChooser().setFocusable(false);
            jd.getYearChooser().setFocusable(false);
            cmdNextMonth.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Calendar cl = jd.getCalendar();
                    cl.add(Calendar.MONTH, 1);
                    jd.getDayChooser().setCalendar(cl);
                    jd.getMonthChooser().setMonth(cl.get(Calendar.MONTH));
                    jd.getYearChooser().setYear(cl.get(Calendar.YEAR));
                    lblMonth.setText(((JComboBox) jd.getMonthChooser().
                            getComboBox()).getSelectedItem().toString());
                    lblYear.setText(jd.getYearChooser().getYear() + "");
                }
            });
            pnl_calendar.add(cmdNextMonth);
            cmdPrevMonth.setFocusable(false);
            jd.getMonthChooser().setFocusable(false);
            jd.getYearChooser().setFocusable(false);
            cmdPrevMonth.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Calendar cl = jd.getCalendar();
                    cl.add(Calendar.MONTH, -1);
                    jd.getDayChooser().setCalendar(cl);
                    jd.getMonthChooser().setMonth(cl.get(Calendar.MONTH));
                    jd.getYearChooser().setYear(cl.get(Calendar.YEAR));
                    lblMonth.setText(((JComboBox) jd.getMonthChooser().
                            getComboBox()).getSelectedItem().toString());
                    lblYear.setText(jd.getYearChooser().getYear() + "");

                }
            });
            pnl_calendar.add(cmdPrevMonth);
            pn_calendar.setDividerLocation(100);
            pn_calendar.setLeftComponent(pnl_calendar);
            pnl_calendar.add(lblMonth);
            pnl_calendar.add(lblYear);
            pn_calendar.setRightComponent(jd.getDayChooser());
            for (int i = 0; i < jd.getDayChooser().getDayPanel().getComponentCount() - 1; i++) {
                if (jd.getDayChooser().getDayPanel().getComponent(i) instanceof JButton) {
                    ((JButton) jd.getDayChooser().getDayPanel().getComponent(i)).setFocusable(false);
                    ((JButton) jd.getDayChooser().getDayPanel().getComponent(i)).addMouseListener(calendarlst);
                }
            }
        }

        rowPanel.add(pn_calendar);
        //jd.setPreferredSize(new Dimension(rowPanel.getWidth(), rowPanel.getHeight()));
        jd.updateUI();
        pnl_calendar.updateUI();
    }

    public void createView() {
        if (parentjf == null && parentPanel == null) {
            return;
        }

        parentPanel.removeAll();

        if (alphasPanel == null) {
            alphasPanel = new JPanel(new GridLayout(3, 1));

        } else {
            alphasPanel.removeAll();
            ((GridLayout) alphasPanel.getLayout()).setRows(3);
            ((GridLayout) alphasPanel.getLayout()).setColumns(1);
            ((GridLayout) alphasPanel.getLayout()).setHgap(2);
            ((GridLayout) alphasPanel.getLayout()).setVgap(2);
        }

        if (modesPanel == null) {
            modesPanel = new JPanel(new GridLayout(1, 4));
            showModes(modesPanel);
        }

        /*
        if (showModePanel && modesPanel == null) {
        showModes(modesPanel);
        }
         * 
         */
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
            splitpane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, modesPanel, alphasPanel);
            splitpane.setDividerLocation(100);
            if (utils.getMainFrame().getMapVars().get("keyboard_split_divider") != null) {
                splitpane.setDividerLocation(Integer.valueOf(utils.getMainFrame().getMapVars().get("keyboard_split_divider")));
            }
        }

        splitpane.setBounds(0, 0, parentPanel.getWidth() - 1, parentPanel.getHeight() - 1);
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
        JTextComponent tb = null;
        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != null
                && KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() instanceof JTextComponent) {
            tb = (JTextComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        }

        if (tb != null) {
            if (tb instanceof JTextField
                    && tb.isEditable() == true) {
                try {
                    String txt = ((JButton) evt.getSource()).getName();
                    //JTextField j = (JTextField) parentjf.getFocusOwner();
                    JTextField j = (JTextField) tb;
                    j.requestFocus();
                    KeyEvent ke = new KeyEvent(j, KeyEvent.KEY_TYPED, System.currentTimeMillis(), 0, KeyEvent.VK_UNDEFINED, txt.charAt(0));
                    j.dispatchEvent(ke);
                    /*
                    String s1 = j.getText();
                    if (j.getSelectedText() != null && j.getSelectedText().length() > 0) {
                    int start = j.getSelectionStart(), end = j.getSelectionEnd();
                    s1 = s1.substring(0, start) + s1.substring(end, s1.length());
                    }

                    int cp = j.getCaretPosition();
                    s1 = insertString(s1, txt, cp);
                    j.setText(s1);
                    j.setCaretPosition(cp + 1);
                     */

                    /*
                    if (j.getInputVerifier() != null) {
                    j.getInputVerifier().verify(j);
                    }
                     * */
                    j.requestFocus();

                } catch (Exception ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (kb_listner != null) {
            String txt = ((JButton) evt.getSource()).getName();
            kb_listner.OnKeyPress(txt, getCurrentMode(), txt, false);
        }
    }

    private void cmdActionControl(java.awt.event.ActionEvent evt) {
        JTextComponent tb = null;
        if (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != null
                && KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() instanceof JTextComponent) {
            tb = (JTextComponent) KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner();
        }
        if (tb != null && tb instanceof JTextField
                && tb.isEditable() == true) {
            try {
                String txt = ((JButton) evt.getSource()).getName();
                //JTextField j = (JTextField) parentjf.getFocusOwner();
                JTextField j = (JTextField) tb;
                String s1 = j.getText();
                int cp = j.getCaretPosition();
                int start = j.getSelectionStart(), end = j.getSelectionEnd();
                if (txt.equals("DEL")) {
                    //s1 = s1.substring(0, start) + s1.substring(end, s1.length());
                    Date dt = new Date();
                    KeyEvent ke = new KeyEvent(j, KeyEvent.KEY_PRESSED, dt.getTime(), 0, KeyEvent.VK_DELETE, KeyEvent.CHAR_UNDEFINED);
                    KeyEvent ke2 = new KeyEvent(j, KeyEvent.KEY_RELEASED, dt.getTime() + 20, 0, KeyEvent.VK_DELETE, KeyEvent.CHAR_UNDEFINED);
                    j.dispatchEvent(ke);
                    j.dispatchEvent(ke2);
                    s1 = j.getText();
                }
                if (txt.equals(MODE_CLEAR_FIELD)) {
                    s1 = "";
                }
                if (txt.equals("ENTER")) {
                    KeyEvent ke = new KeyEvent(j, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
                    j.dispatchEvent(ke);
                }
                if (txt.equals("BACK")) {
                    //s1 = s1.substring(0, start) + s1.substring(end, s1.length());
                    Date dt = new Date();
                    KeyEvent ke = new KeyEvent(j, KeyEvent.KEY_PRESSED, dt.getTime(), 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
                    KeyEvent ke2 = new KeyEvent(j, KeyEvent.KEY_RELEASED, dt.getTime() + 20, 0, KeyEvent.VK_BACK_SPACE, KeyEvent.CHAR_UNDEFINED);
                    j.dispatchEvent(ke);
                    j.dispatchEvent(ke2);
                    s1 = j.getText();
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
                if (parentjf != null && !(parentjf instanceof MainFrame)) {
                    parentjf.setAlwaysOnTop(false);
                }
            } catch (Exception ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (kb_listner != null) {
            kb_listner.OnKeyPress(((JButton) evt.getSource()).getName(), getCurrentMode(), 0, true);
        }
    }

    private void showItems(JPanel rowPanel) {
    }
}
