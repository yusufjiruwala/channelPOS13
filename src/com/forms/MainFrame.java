/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainFrame.java
 *
 * Created on 30/03/2010, 11:07:44 ص
 */
package com.forms;

import com.forms.panels.DeliveryPanel;
import com.forms.panels.ShowMessageFrame;
import com.forms.panels.closeSalesPanel;
import com.forms.panels.dinePanel;
import com.forms.panels.frmUserLogon;
import com.forms.panels.loginPanel;
import com.forms.panels.reportPanel;
import com.forms.panels.salesPanel;
import com.generic.model.DBClass;
import com.generic.utils.utils;
import com.keyboard.KeyBoardFrame;
import com.keyboard.keyboardViewer;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

/**
 *
 * @author yusuf
 */
public class MainFrame extends javax.swing.JFrame {

    private static final GraphicsDevice gd = (GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())[0];
    /** Creates new form MainFrame */

    public MainFrame() {
        initComponents();
        utils.parentJf = this;
        utils.setupFormTextBoxes(getRootPane().getContentPane());
        setVisible(true);
        setExtendedState(MAXIMIZED_BOTH);
        custominitialze();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);


    }
    private Timer connection_timer = new Timer();
    
    private void do_full_screen() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.height = dim.height - 16;
        setBounds(0, 0, dim.width, dim.height);
        validate();
    }

    private void custominitialze() {
        utils.parentJf = this;
        addComponentListener(new ComponentAdapter() {

            public void componentResized(ComponentEvent e) {
                do_full_screen();
            }

            public void componentMoved(ComponentEvent e) {
                do_full_screen();
            }
        });
        addWindowListener(new WindowListener() {

            public void windowOpened(WindowEvent e) {
            }

            public void windowClosing(WindowEvent e) {
                cmdExit.doClick();
            }

            public void windowClosed(WindowEvent e) {
            }

            public void windowIconified(WindowEvent e) {
            }

            public void windowDeiconified(WindowEvent e) {
            }

            public void windowActivated(WindowEvent e) {
            }

            public void windowDeactivated(WindowEvent e) {
            }
        });
        try {
            readVars();
            Class.forName(mapVars.get("classdriver"));
            dbc = new DBClass(mapVars.get("dburl"), mapVars.get("username"), mapVars.get("password"));
            dbConneciton = dbc.getDbConnection();
            if (Integer.valueOf(getMapVars().get("connection_expire")) > 0) {
                connection_timer.scheduleAtFixedRate(new TimerTask() {

                    private boolean is_working = false;
                    PreparedStatement xxxpp = dbConneciton.prepareStatement("BEGIN NULL;END;", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

                    public void run() {
                        try {
                            if (is_working == false) {
                                is_working = true;
                                xxxpp.executeUpdate();
                                //xxrs.first();
                                //xxxpp.close();
                                is_working = false;
                                txtSeverStatus.setText("User: " + lp.getLogon_user() + "");
                                txtSeverStatus.setToolTipText("User Connect: " + lp.getLogon_user());
                                System.out.println("Mainframe -> sql exec..." + new Date(System.currentTimeMillis()));

                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                            txtSeverStatus.setText(ex.getMessage());
                            txtSeverStatus.setToolTipText(ex.getMessage());

                        }
                    }
                }, 5000, Integer.valueOf(getMapVars().get("connection_expire")));
            }

        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        getContentPane().add(lp, java.awt.BorderLayout.CENTER);
        lp.setParentJF(this);
        disableAllCommands(cmdLogon);
        cmdLogon.setSelected(true);
        if (mapVars.get("keyboard_panel_height") != null) {
            int h = Integer.valueOf(mapVars.get("keyboard_panel_height"));
            //jPanel2.setBounds(getHeight() - h, 0, getWidth(), h);
            jPanel2.setPreferredSize(new Dimension(getWidth(), h));
        }
        jPanel2.updateUI();
        kv.setParentjf(this);
        kv.setParentPanel(jPanel2);
        kv.createView();

        lp.getTxtUsername().requestFocus();

        cp = lp;

        if (mapVars.get("keyboard_initial_stat").equals("HIDE")) {
            kv.hideKeyboardPanel();
        }
        kf.setVisible(true);
        getLp().getTxtUsername().requestFocus();

    }

    public void disableAllCommands(JToggleButton cmd) {
        cmd.setEnabled(true);

        if (cmdLogon != cmd) {
            cmdLogon.setEnabled(false);
        }
        if (cmdExchangeData != cmd) {
            cmdExchangeData.setEnabled(false);
        }
        if (cmdReports != cmd) {
            cmdReports.setEnabled(false);
        }
        
        if (cmdReturn != cmd) {
            cmdReturn.setEnabled(false);
        }

        if (cmdSales != cmd) {
            cmdSales.setEnabled(false);
        }
        if (cmdDineIn != cmd) {
            cmdDineIn.setEnabled(false);
        }

    }

    public void enableAllCommands() {
        cmdLogon.setEnabled(true);
        cmdExchangeData.setEnabled(true);
        cmdReports.setEnabled(true);
        cmdReturn.setEnabled(true);
        cmdSales.setEnabled(true);
        cmdDineIn.setEnabled(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        cmdLogon = new javax.swing.JToggleButton();
        cmdSales = new javax.swing.JToggleButton();
        cmdReturn = new javax.swing.JToggleButton();
        cmdReports = new javax.swing.JToggleButton();
        cmdExchangeData = new javax.swing.JToggleButton();
        cmdDineIn = new javax.swing.JToggleButton();
        cmdExit = new javax.swing.JToggleButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        txtSeverStatus = new javax.swing.JLabel();
        txtDeliveryStatus = new javax.swing.JLabel();
        txtMsg = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(379, 50));

        jPanel4.setLayout(new java.awt.GridLayout(1, 8, 10, 0));

        buttonGroup1.add(cmdLogon);
        cmdLogon.setText("Log -On");
        cmdLogon.setMargin(new java.awt.Insets(3, 5, 3, 5));
        cmdLogon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdLogonActionPerformed(evt);
            }
        });
        jPanel4.add(cmdLogon);

        buttonGroup1.add(cmdSales);
        cmdSales.setText("Sales ");
        cmdSales.setMargin(new java.awt.Insets(3, 5, 3, 5));
        cmdSales.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdSalesActionPerformed(evt);
            }
        });
        jPanel4.add(cmdSales);

        buttonGroup1.add(cmdReturn);
        cmdReturn.setText("Return");
        cmdReturn.setMargin(new java.awt.Insets(3, 5, 3, 5));
        cmdReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReturnActionPerformed(evt);
            }
        });
        jPanel4.add(cmdReturn);

        buttonGroup1.add(cmdReports);
        cmdReports.setText("Reports");
        cmdReports.setMargin(new java.awt.Insets(3, 5, 3, 5));
        cmdReports.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReportsActionPerformed(evt);
            }
        });
        jPanel4.add(cmdReports);

        buttonGroup1.add(cmdExchangeData);
        cmdExchangeData.setText("Close Sales");
        cmdExchangeData.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdExchangeData.setMinimumSize(new java.awt.Dimension(120, 29));
        cmdExchangeData.setPreferredSize(new java.awt.Dimension(120, 29));
        cmdExchangeData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExchangeDataActionPerformed(evt);
            }
        });
        jPanel4.add(cmdExchangeData);

        buttonGroup1.add(cmdDineIn);
        cmdDineIn.setText("Dine-In");
        cmdDineIn.setMargin(new java.awt.Insets(0, 0, 0, 0));
        cmdDineIn.setMinimumSize(new java.awt.Dimension(120, 29));
        cmdDineIn.setPreferredSize(new java.awt.Dimension(120, 29));
        cmdDineIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdDineInActionPerformed(evt);
            }
        });
        jPanel4.add(cmdDineIn);

        cmdExit.setText("Exit");
        cmdExit.setMargin(new java.awt.Insets(3, 5, 3, 5));
        cmdExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdExitActionPerformed(evt);
            }
        });
        jPanel4.add(cmdExit);

        jPanel3.setMinimumSize(new java.awt.Dimension(861, 100));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 100));
        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel5.setLayout(new java.awt.GridLayout(1, 2));

        txtSeverStatus.setFont(new java.awt.Font("Tahoma", 0, 12));
        txtSeverStatus.setForeground(new java.awt.Color(102, 0, 0));
        jPanel5.add(txtSeverStatus);

        txtDeliveryStatus.setFont(new java.awt.Font("Tahoma", 0, 14));
        txtDeliveryStatus.setForeground(new java.awt.Color(153, 51, 0));
        txtDeliveryStatus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtDeliveryStatusMouseClicked(evt);
            }
        });
        jPanel5.add(txtDeliveryStatus);

        jPanel3.add(jPanel5);

        txtMsg.setBackground(new java.awt.Color(-16711681,true));
        txtMsg.setFont(new java.awt.Font("Courier New", 1, 12));
        txtMsg.setOpaque(true);
        jPanel3.add(txtMsg);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/keyboard.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(689, 200));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1017, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 196, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmdLogonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdLogonActionPerformed
        // TODO add your handling code here:
        getContentPane().remove(cp);
        getContentPane().add(lp, java.awt.BorderLayout.CENTER);
        lp.getTxtUsername().requestFocus();
        lp.getTxtLocation().setText(mapVars.get("LOCATION_NAME"));
        cp = lp;
        repaint();
}//GEN-LAST:event_cmdLogonActionPerformed

    private void cmdExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExitActionPerformed
        if (JOptionPane.showConfirmDialog(this, "Exit Application ?", "CHANNEL POS", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            dispose();
            System.exit(0);
        }
}//GEN-LAST:event_cmdExitActionPerformed

    private void cmdSalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdSalesActionPerformed
        getContentPane().remove(cp);
        getContentPane().add(sp, java.awt.BorderLayout.CENTER);
        sp.getJTable1().requestFocus();
        cp = sp;
        sp.setParentJF(this);

        if (sp.getItemsRows() == null) {
            sp.start_showItems();
        }
        sp.updateUI();
        
        //sp.updateUI();
    }//GEN-LAST:event_cmdSalesActionPerformed

    private void cmdReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReturnActionPerformed
        if (!usrlog.checkSecurity("SEC_SALES_RETURN", "Opening Return Form")) {
            return;
        }
        getContentPane().remove(cp);
        getContentPane().add(spr, java.awt.BorderLayout.CENTER);
        spr.getJTable1().requestFocus();
        cp = spr;
        spr.setParentJF(this);
        if (spr.getItemsRows() == null) {
            spr.start_showItems();
        }
        ShowMessageFrame.closeAllWindows("");
        spr.updateUI();
        spr.showWizard();


    }//GEN-LAST:event_cmdReturnActionPerformed

    private void cmdExchangeDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdExchangeDataActionPerformed
        if (!usrlog.checkSecurity("SEC_CLOSE_SALE", "Opening Return Form")) {
            return;
        }

        getContentPane().remove(cp);
        getContentPane().add(clsp, java.awt.BorderLayout.CENTER);
        cp = clsp;
        //clsp.setParentJF(this);
        clsp.customIntialize(this);
        clsp.updateUI();

    }//GEN-LAST:event_cmdExchangeDataActionPerformed

    private void cmdReportsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdReportsActionPerformed
        getContentPane().remove(cp); 
        getContentPane().add(rpnl, java.awt.BorderLayout.CENTER);
        cp = rpnl;
        rpnl.customIntialize(this);
        rpnl.updateUI();

    }//GEN-LAST:event_cmdReportsActionPerformed

    private void txtDeliveryStatusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtDeliveryStatusMouseClicked
        displayDeliveryStatus(true);
        if (txtDeliveryStatus.getText().length() > 0 && cp != dp) {
            getContentPane().remove(cp);
            getContentPane().add(dp, java.awt.BorderLayout.CENTER);
            cp = dp;
            dp.setParentJf(this);
            dp.updateUI();
        }
}//GEN-LAST:event_txtDeliveryStatusMouseClicked

    private void cmdDineInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdDineInActionPerformed
        if (!usrlog.checkSecurity("SEC_DINE_DESIGN", "Opening Return Form")) {
            return;
        }

        getContentPane().remove(cp);
        getContentPane().add(dine, java.awt.BorderLayout.CENTER);
        cp = dine;
        //clsp.setParentJF(this);
        dine.customIntialize(this);
        //rpnl.getDataStoreBalance();
        dine.updateUI();
    }//GEN-LAST:event_cmdDineInActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        kf.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
    public void update_keyboard() {
        kf.setVisible(true);
    }

    public void displayDeliveryStatus(boolean showmsg) {
        txtDeliveryStatus.setText("");
        int cnt = Integer.valueOf(utils.getSqlValue("select nvl(count(*),0) from POSPUR1 WHERE LOCATION_CODE='" + mapVars.get("DEFAULT_LOCATION") + "' and slsmn is not null and paidamt2=0 and (inv_amt-disc_amt)>0", dbConneciton));
        if (showmsg && cnt <= 0) {
            JOptionPane.showMessageDialog(this, "No outstanding Delivery found !");
        } else {
            String s = "<html>";
            for (int i = 0; i < cnt; i++) {
                s = s + "<img src=\"file:images/cart.png\">";
            }
            txtDeliveryStatus.setText(s);
        }
    }

    public String insertString(String source, String istr, int offset) {
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

    public void buildProifle() throws Exception {

        String s = utils.getSqlValue("select profileno from cp_users where username='" + lp.getTxtUsername().getText() + "'", dbConneciton);
        if (s == null || s.length() == 0) {
            throw new Exception("profile no for user is NULL");
        }
        profileno = Integer.valueOf(s);
        PreparedStatement ps_u = dbConneciton.prepareStatement("select variable,value "
                + " from CP_USER_PROFILES where (PROFILENO=0 OR PROFILENO=?) AND SETGRPNO IN ('POS','GNR') order by profileno,variable", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ps_u.setInt(1, profileno);
        ResultSet rs = ps_u.executeQuery();
        rs.beforeFirst();
        while (rs.next()) {
            mapVars.put(rs.getString("variable"), rs.getString("value"));
        }
        ps_u.close();
        rs.close();
        mapVars.put("DEFAULT_LOCATION", mapVars.get("location_code"));
        ps_u = dbConneciton.prepareStatement("select name,from_keyfld,to_keyfld from LOCATIONS where CODE='" + mapVars.get("DEFAULT_LOCATION") + "'", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
        rs = ps_u.executeQuery();
        rs.first();
        mapVars.put("LOCATION_NAME", rs.getString("name"));
        mapVars.put("FROM_KEYFLD", String.valueOf(rs.getFloat("from_keyfld")));
        mapVars.put("TO_KEYFLD", String.valueOf(rs.getFloat("to_keyfld")));
        if (getMapVars().get("POS_CLOSE_DATE_" + getMapVars().get("DEFAULT_LOCATION")) == null) {
            getMapVars().put("POS_CLOSE_DATE_" + getMapVars().get("DEFAULT_LOCATION"), "01/01/2000");
        }

        ps_u.close();
    }

    public void readVars() throws Exception {
        Scanner s = new Scanner(new BufferedReader(new FileReader("pos.ini")));
        Scanner ss = null;
        String var = "", val = "";
        mapVars.clear();
        while (s.hasNextLine()) {
            String a = s.nextLine();
            ss = new Scanner(a).useDelimiter("\\s*=\\s*");
            var = ss.next();
            val = ss.next();
            System.out.println("'" + var + "'");
            System.out.println("'" + val + "'");
            mapVars.put(var, val);
        }
    }

    public boolean login() {
        try {
            PreparedStatement ps_u = dbConneciton.prepareStatement("select username,COMPANY.NAME COMPANY_NAME,SPECIFICATION"
                    + " from cp_users,COMPANY where CRNT='X' AND username=? and password=?", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps_u.setString(1, lp.getTxtUsername().getText());
            ps_u.setString(2, String.valueOf(lp.getTxtPwd().getPassword()));
            ResultSet rs = ps_u.executeQuery();
            boolean ret = rs.first();
            if (ret == false) {
                ps_u.close();
                throw new Exception("User not found");
            }
            remove(cp);
            txtMsg.setText("Building profile user..");
            buildProifle();
            mapVars.put("COMPANY_NAME", rs.getString("company_name"));
            mapVars.put("IS_ADMIN", utils.getSqlValue("select IS_ADMIN from cp_users where username='" + lp.getTxtUsername().toString() + "'", dbConneciton));
            mapVars.put("DEFAULT_CASHIER", utils.getSqlValue("select nvl(max(no),'-1') from salesp where dlic='" + lp.getTxtUsername().getText() + "'", dbConneciton));
            mapVars.put("COMPANY_SPEC", rs.getString("SPECIFICATION"));
            ps_u.close();
            txtMsg.setText("Login....");
            txtSeverStatus.setText("User: " + lp.getLogon_user() + "");
            txtSeverStatus.setToolTipText("User Connect: " + lp.getLogon_user());
            txtMsg.setText("Login....");
            txtMsg.updateUI();

            this.setTitle("CHANNEL POS: " + lp.getLogon_user() + " Location: " + mapVars.get("LOCATION_NAME"));
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        return false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    public JToggleButton getCmdExchangeData() {
        return cmdExchangeData;
    }

    public JToggleButton getCmdExit() {
        return cmdExit;
    }

    public JToggleButton getCmdLogon() {
        return cmdLogon;
    }

    public JToggleButton getCmdReports() {
        return cmdReports;
    }

    public JToggleButton getCmdReturn() {
        return cmdReturn;
    }

    public JToggleButton getCmdSales() {
        return cmdSales;
    }

    public JToggleButton getCmdDineIn() {
        return cmdDineIn;
    }

    public JPanel getJPanel1() {
        return jPanel1;
    }

    public JPanel getJPanel2() {
        return jPanel2;
    }

    public keyboardViewer getKv() {
        return kv;
    }

    public Map<String, String> getMapVars() {
        return mapVars;
    }

    public JLabel getTxtMsg() {
        return txtMsg;
    }

    public void setTxtMsg(JLabel txtMsg) {
        this.txtMsg = txtMsg;
    }

    public loginPanel getLp() {
        return lp;
    }

    public salesPanel getSp() {
        return sp;
    }

    public reportPanel getRp() {
        return rpnl;
    }

    public salesPanel getSpr() {
        return spr;
    }

    public dinePanel getDine() {
        return dine;
    }

    public Timer getConnection_timer() {
        return connection_timer;
    }

    public Connection getDbConneciton() {
        return dbConneciton;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JToggleButton cmdDineIn;
    private javax.swing.JToggleButton cmdExchangeData;
    private javax.swing.JToggleButton cmdExit;
    private javax.swing.JToggleButton cmdLogon;
    private javax.swing.JToggleButton cmdReports;
    private javax.swing.JToggleButton cmdReturn;
    private javax.swing.JToggleButton cmdSales;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JLabel txtDeliveryStatus;
    private javax.swing.JLabel txtMsg;
    private javax.swing.JLabel txtSeverStatus;
    // End of variables declaration//GEN-END:variables
    public frmUserLogon usrlog = new frmUserLogon(this);
    private loginPanel lp = new loginPanel();
    private salesPanel sp = new salesPanel(30);
    private closeSalesPanel clsp = new closeSalesPanel();
    private reportPanel rpnl = new reportPanel();
    private DeliveryPanel dp = new DeliveryPanel();
    private salesPanel spr = new salesPanel(20);
    private dinePanel dine = new dinePanel();
    public JPanel cp = null;
    private keyboardViewer kv = new keyboardViewer();
    private Connection dbConneciton = null;
    private int profileno = -1;
    private Map<String, String> mapVars = new HashMap<String, String>();
    private DBClass dbc = null;
    public KeyBoardFrame kf = new KeyBoardFrame();
}
