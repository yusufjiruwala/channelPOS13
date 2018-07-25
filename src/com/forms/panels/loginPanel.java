/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * loginPanel.java
 *
 * Created on 30/03/2010, 07:07:59 م
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.utils.utils;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author yusuf
 */
public class loginPanel extends javax.swing.JPanel {

    /** Creates new form loginPanel */
    public loginPanel() {
        initComponents();
        utils.setupFormTextBoxes(this);
        
    }

    public JPasswordField getTxtPwd() {
        return txtPwd;
    }

    public JTextField getTxtUsername() {
        return txtUsername;
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
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtPwd = new javax.swing.JPasswordField();
        butLogon = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtLocation = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jPanel1.setLayout(new java.awt.GridLayout(1, 2, 0, 2));

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("User Name");

        jLabel5.setText("Password");

        butLogon.setText("Login");
        butLogon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLogonActionPerformed(evt);
            }
        });

        jLabel6.setText("Location");

        txtLocation.setEditable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(241, 241, 241)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(butLogon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtPwd, txtUsername});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(butLogon)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {txtPwd, txtUsername});

        jPanel1.add(jPanel2);

        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        jPanel4.setLayout(new java.awt.GridLayout(2, 1));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel1);
        ImageIcon icon=new ImageIcon("company_logo.GIF");
        BufferedImage bi = new BufferedImage(150,
            150, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.drawImage(icon.getImage(), 0, 0, 150,
            150, null);
        g.dispose();
        ImageIcon newicon = new ImageIcon(bi);
        jLabel1.setIcon(newicon);

        jPanel3.add(jPanel4);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("  ");
        jPanel3.add(jLabel2);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" ");
        jPanel3.add(jLabel3);

        jPanel1.add(jPanel3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 668, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void butLogonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLogonActionPerformed
        // TODO add your handling code here:
        pos_login = false;
        if (parentJF.getMapVars().get("location_code") == null) {
            JOptionPane.showMessageDialog(parentJF, "Location not defined");
            return;
        }
        if (parentJF != null) {
            if (parentJF.login()) {
                parentJF.enableAllCommands();
                parentJF.getCmdSales().doClick();                
                parentJF.invalidate();
                
                pos_login = true;
                butLogon.setEnabled(!pos_login);
                setLogon_user(txtUsername.getText());
                setLogon_pwd(txtPwd.getPassword().toString());
                List<String> ls = new ArrayList<String>(parentJF.getMapVars().keySet());
                
                String s = "";
                for (int i = 0; i < ls.size(); i++) {
                    s = s + "\n" + ls.get(i) + " = " + parentJF.getMapVars().get(ls.get(i));
                }

                ShowMessageFrame sf = new ShowMessageFrame("Logon session created " + txtUsername.getText() + "\n" + s, 14, 2000);

            }
        }
    }//GEN-LAST:event_butLogonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butLogon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTextField txtLocation;
    private javax.swing.JPasswordField txtPwd;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
    private MainFrame parentJF = null;
    private boolean pos_login = false;
    private String logon_user = "";
    private String logon_pwd = "";


    public String getLogon_pwd() {
        return logon_pwd;
    }

    public void setLogon_pwd(String logon_pwd) {
        this.logon_pwd = logon_pwd;
    }

    public String getLogon_user() {
        return logon_user;
    }

    public void setLogon_user(String logon_user) {
        this.logon_user = logon_user;
    }

    public boolean isPos_login() {
        return pos_login;
    }

    public void setPos_login(boolean pos_login) {
        this.pos_login = pos_login;
    }

    public MainFrame getParentJF() {
        return parentJF;
    }

    public void setParentJF(MainFrame parentJF) {
        this.parentJF = parentJF;        
    }

    public JTextField getTxtLocation() {
        return txtLocation;
    }
}
