/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * createSection.java
 *
 * Created on Jan 24, 2014, 8:19:36 AM
 */
package com.forms.panels;

import com.forms.MainFrame;
import com.generic.utils.utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Yusuf
 */
public class createSection extends javax.swing.JDialog {

    /** Creates new form createSection */
    public createSection(java.awt.Frame parent, boolean modal, String QRYSES) {
        super(parent, modal);
        this.QRYSES = QRYSES;
        initComponents();
        con = ((MainFrame) parent).getDbConneciton();
        load_data();
    }

    public void centerWindow() {
        /*
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;        
        setLocation(screenWidth / 4, screenHeight / 4);
         */

        setLocationRelativeTo(null);
    }

    public void load_data() {
        chkAllDay.setEnabled(true);
        setAllWeek(true);
        txtCode.setEnabled(true);
        txtCode.setText("");
        txtName.setText("");
        txtWidth.setText("300");
        txtHeight.setText("300");
        chkFlag.setSelected(true);
        
        try {
            if (!QRYSES.isEmpty()) {

                PreparedStatement ps = con.prepareStatement("select *from POS_SECTIONS where code='"
                        + QRYSES + "'", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = ps.executeQuery();
                if (rs == null || !rs.first()) {
                    setVisible(false);
                    throw new SQLException("This record may have deleted by some user");
                }

                txtCode.setText(rs.getString("code"));
                txtName.setText(rs.getString("SECTION_name"));
                txtHeight.setText(rs.getString("height"));
                txtWidth.setText(rs.getString("width"));
                chkFlag.setSelected(((rs.getInt("flag") == 2)));
                chkSaturday.setSelected((rs.getString("DAY_1") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_2") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_3") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_4") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_5") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_6") == "Y"));
                chkSaturday.setSelected((rs.getString("DAY_7") == "Y"));

                chkFlag.setSelected(((rs.getInt("flag") == 1) ? false : true));
                txtCode.setEnabled(false);


            } else {
                String t = utils.getSqlValue("select nvl(max(code),0)+1 from POS_SECTIONS ", con);
                txtCode.setText(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(createSection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJf, ex.getMessage());
        }

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        chkFlag = new javax.swing.JCheckBox();
        chkAllDay = new javax.swing.JCheckBox();
        pnlWeek = new javax.swing.JPanel();
        chkSaturday = new javax.swing.JCheckBox();
        chkSunday = new javax.swing.JCheckBox();
        chkMonday = new javax.swing.JCheckBox();
        chkTuesday = new javax.swing.JCheckBox();
        chkWednesday = new javax.swing.JCheckBox();
        chkThursday = new javax.swing.JCheckBox();
        chkFriday = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtWidth = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtHeight = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Code");

        jLabel2.setText("Section Name");

        chkFlag.setSelected(true);
        chkFlag.setText("Block this section");
        chkFlag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFlagActionPerformed(evt);
            }
        });

        chkAllDay.setSelected(true);
        chkAllDay.setText("All Days in week");
        chkAllDay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAllDayActionPerformed(evt);
            }
        });

        chkSaturday.setSelected(true);
        chkSaturday.setText("Saturday");
        chkSaturday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSaturdayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkSaturday);

        chkSunday.setSelected(true);
        chkSunday.setText("Sunday");
        chkSunday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSundayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkSunday);

        chkMonday.setSelected(true);
        chkMonday.setText("Monday");
        chkMonday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMondayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkMonday);

        chkTuesday.setSelected(true);
        chkTuesday.setText("Tueday");
        chkTuesday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTuesdayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkTuesday);

        chkWednesday.setSelected(true);
        chkWednesday.setText("Wednesday");
        chkWednesday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkWednesdayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkWednesday);

        chkThursday.setSelected(true);
        chkThursday.setText("Thursday");
        chkThursday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkThursdayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkThursday);

        chkFriday.setSelected(true);
        chkFriday.setText("Friday");
        chkFriday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFridayActionPerformed(evt);
            }
        });
        pnlWeek.add(chkFriday);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jButton2.setText("Delete");
        jPanel1.add(jButton2);

        jButton3.setText("Close");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jLabel3.setText("Width");

        jLabel4.setText("Height");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(chkFlag))
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(121, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(428, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(pnlWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(81, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(chkAllDay)
                .addContainerGap(505, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(123, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkFlag))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtWidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAllDay)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlWeek, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void setAllWeek(boolean f) {
        for (int i = 0; i < pnlWeek.getComponentCount() - 1; i++) {
            if (pnlWeek.getComponent(i) instanceof JCheckBox) {
                ((JCheckBox) pnlWeek.getComponent(i)).setSelected(f);
            }
        }
        pnlWeek.setEnabled(true);
        if (f) {
            pnlWeek.setEnabled(false);
        }

    }

    private void delete_data() {
        if (QRYSES.isEmpty()) {
            setVisible(false);
        }
    }

    private void save_data() {
        try {
            con.setAutoCommit(false);
            validate_data();
            PreparedStatement ps = con.prepareStatement((QRYSES.isEmpty() ? "insert into POS_SECTIONS (CODE, SECTION_NAME, "
                    + "FLAG, DAY_1, DAY_2, DAY_3, DAY_4, DAY_5, DAY_6, DAY_7, HEIGHT, WIDTH) values "
                    + "(?, ?, "
                    + "?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    : "update  POS_SECTIONS "
                    + "SET CODE=?, SECTION_NAME=?, "
                    + "FLAG=?, DAY_1=?, DAY_2=?, "
                    + "DAY_3=?, DAY_4=?, DAY_5=?, DAY_6=?, DAY_7=?, HEIGHT=?, WIDTH=? "
                    + "where code=?"), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            ps.setString(1, txtCode.getText());
            ps.setString(2, txtName.getText());
            ps.setString(3, (chkFlag.isSelected() ? "1" : "2"));
            ps.setString(4, (chkSaturday.isSelected() ? "Y" : "N"));
            ps.setString(5, (chkSunday.isSelected() ? "Y" : "N"));
            ps.setString(6, (chkMonday.isSelected() ? "Y" : "N"));
            ps.setString(7, (chkTuesday.isSelected() ? "Y" : "N"));
            ps.setString(8, (chkWednesday.isSelected() ? "Y" : "N"));
            ps.setString(9, (chkThursday.isSelected() ? "Y" : "N"));
            ps.setString(10, (chkFriday.isSelected() ? "Y" : "N"));
            ps.setString(11, txtHeight.getText());
            ps.setString(12, txtWidth.getText());
            ps.setString((QRYSES.isEmpty() ? 1 : 13), txtCode.getText());
            ps.execute();
            con.commit();
            last_code = txtCode.getText();
            JOptionPane.showMessageDialog(parentJf, "Saved Successfully");
            setVisible(false);

        } catch (SQLException ex) {

            Logger.getLogger(createSection.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(parentJf, ex.getMessage());
            try {
                con.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(createSection.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private void validate_data() throws SQLException {
        if (txtCode.getText().isEmpty()) {
            throw new SQLException("Must have value to code");
        }

        if (txtName.getText().isEmpty()) {
            throw new SQLException("Must have description value ");
        }
        if (QRYSES.isEmpty()) {
            String fnd = utils.getSqlValue("select SECTION_NAME from POS_SECTIONS WHERE CODE='" + txtCode.getText() + "'", con);
            if (!fnd.isEmpty()) {
                throw new SQLException("Name found on this code !");
            }
        }

    }

    private void chkAllDayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAllDayActionPerformed
        setAllWeek(chkAllDay.isSelected());
    }//GEN-LAST:event_chkAllDayActionPerformed

    private void chkSundayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSundayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSundayActionPerformed

    private void chkMondayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMondayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkMondayActionPerformed

    private void chkTuesdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTuesdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTuesdayActionPerformed

    private void chkWednesdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkWednesdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkWednesdayActionPerformed

    private void chkThursdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkThursdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkThursdayActionPerformed

    private void chkFridayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFridayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkFridayActionPerformed

    private void chkSaturdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSaturdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkSaturdayActionPerformed

    private void chkFlagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFlagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkFlagActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        save_data();
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkAllDay;
    private javax.swing.JCheckBox chkFlag;
    private javax.swing.JCheckBox chkFriday;
    private javax.swing.JCheckBox chkMonday;
    private javax.swing.JCheckBox chkSaturday;
    private javax.swing.JCheckBox chkSunday;
    private javax.swing.JCheckBox chkThursday;
    private javax.swing.JCheckBox chkTuesday;
    private javax.swing.JCheckBox chkWednesday;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlWeek;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtWidth;
    // End of variables declaration//GEN-END:variables
    public String QRYSES = "";
    public String last_code = "";
    public Connection con = null;
    private MainFrame parentJf = null;
}
