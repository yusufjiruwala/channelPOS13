/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * frmUserLogon.java
 *
 * Created on Apr 21, 2014, 7:44:07 AM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.utils.BlinkLabel;
import com.keyboard.keyboardViewer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Yusuf
 */
public class frmUserLogon extends javax.swing.JDialog {

    /** Creates new form frmUserLogon */
    public frmUserLogon(java.awt.Frame parent) {
        super(parent, true);

        initComponents();
        this.parentJf = (MainFrame) parent;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        txtPass = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        lblTitle = new BlinkLabel("");
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnlKb = new javax.swing.JPanel();

        setBackground(new java.awt.Color(-14336,true));

        jPanel1.setBackground(new java.awt.Color(-14336,true));
        jPanel1.setAutoscrolls(true);

        jLabel1.setText("User Name");

        jLabel2.setText("Password");

        lblTitle.setFont(new java.awt.Font("Dialog", 1, 14));
        lblTitle.setForeground(new java.awt.Color(-16776961,true));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Serif", 2, 14));
        jLabel4.setText("Enter Granted user logon");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUser)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(20, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(103, 103, 103)
                    .addComponent(jLabel4)
                    .addContainerGap(135, Short.MAX_VALUE)))
        );

        jPanel2.setBackground(new java.awt.Color(-14336,true));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/ok.png"))); // NOI18N
        jButton1.setText("Logon");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/forms/panels/images/cancel.png"))); // NOI18N
        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlKbLayout = new javax.swing.GroupLayout(pnlKb);
        pnlKb.setLayout(pnlKbLayout);
        pnlKbLayout.setHorizontalGroup(
            pnlKbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 439, Short.MAX_VALUE)
        );
        pnlKbLayout.setVerticalGroup(
            pnlKbLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 322, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlKb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(pnlKb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        do_logon();
    }//GEN-LAST:event_jButton1ActionPerformed
    public void do_logon() {
        try {
            String usr = txtUser.getText();
            String sql = "select username,password from cp_users where  is_admin='Y' and (username ='"
                    + txtUser.getText() + "' and " + "password='" + String.valueOf(txtPass.getPassword()) + "') ";
            if (parentJf.getMapVars().get(sec + "_USERS") != null) {
                String gusr = parentJf.getMapVars().get(sec + "_USERS");
                PreparedStatement ps = con.prepareStatement("select *from cp_users where is_admin='Y' ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery();
                if (rs != null && rs.first()) {
                    rs.beforeFirst();
                    while (rs.next()) {
                        gusr += "\"" + rs.getString("USERNAME") + "\"";
                    }
                    ps.close();
                }
                if (gusr.indexOf("\"" + usr + "\"") < 0) {
                    throw new Exception("Invalid user access !");
                }
                sql = "select username,password from cp_users where  (is_admin='Y' and username ='"
                        + usr + "' and " + "password='" + String.valueOf(txtPass.getPassword()) + "') or "
                        + "(username ='"
                        + usr + "' and " + "password='" + String.valueOf(txtPass.getPassword()) + "')";
            }
            PreparedStatement ps = con.prepareStatement(sql,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = ps.executeQuery();
            if (rs == null || !rs.first()) {
                throw new SQLException("Invalid User Access ! ");
            }
            ps.close();
            hasLogonSuccess = true;
            setVisible(false);
        } catch (Exception ex) {
            hasLogonSuccess = false;
            Logger.getLogger(frmUserLogon.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        hasLogonSuccess = false;
        setVisible(false);
        ;
    }//GEN-LAST:event_jButton2ActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlKb;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
    public boolean hasLogonSuccess = false;
    private String last_user = "";
    private String last_password = "";
    private keyboardViewer kv = null;
    private Connection con = null;
    private MainFrame parentJf = null;
    private String sec = "";

    public void setParentJf(MainFrame parentJf, String msg) {
        setLocationRelativeTo(null);
        ((BlinkLabel) lblTitle).setBlinking(true);
        hasLogonSuccess = false;
        this.parentJf = parentJf;
        txtPass.setText("");
        con = this.parentJf.getDbConneciton();
        if (kv == null) {
            kv = new keyboardViewer(pnlKb);
            kv.setShowPanels(keyboardViewer.MODE_ALPHABET + "," + keyboardViewer.MODE_SMALL_ALPHABETS + "," + keyboardViewer.MODE_NUMBERS);
            kv.createView();
        }
        setVisible(true);
    }

    public boolean checkSecurity(String sec, String message) {
        boolean showDlg = true;
        this.sec = sec;
        lblTitle.setText(message);
        ShowMessageFrame.closeAllWindows("");
        if (parentJf.getMapVars().get(sec) != null && !parentJf.getMapVars().get(sec).equals("TRUE")) {
            showDlg = false;
        }
        if (showDlg) {
            setParentJf(parentJf, message);
            if (hasLogonSuccess) {
                return true;
            } else {
                return false;
            }
        } 
        return true;

    }
}
