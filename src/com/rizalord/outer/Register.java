/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rizalord.outer;

import com.placeholder.PlaceHolder;
import com.rizalord.core.Connnection;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.CompoundBorder;

/**
 *
 * @author asus
 */
public class Register extends javax.swing.JFrame {

    Connnection conn = new Connnection();
    
    public Register() {
        initComponents();
        
        setTitle("GoCure");
        setVisible(true);
        setLocationRelativeTo(null);
        
        jLayeredPane2.add(jLabel2,2);
        jLayeredPane2.add(jLabel5,1);
        jLayeredPane2.add(jLabel6,0);
        jLayeredPane2.add(jLabel7,0);
        jButton1.setFocusPainted(false);
//        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        
        
        jButton1.setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
               BorderFactory.createLineBorder(Color.WHITE, 4)));
        jButton1.setOpaque(true);
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                Login newFrame;
                try {
                    newFrame = new Login();
                    newFrame.setVisible(true);
                    newFrame.setLocationRelativeTo(null);
                } catch (SQLException ex) {
                    Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
            }

            
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton1.setForeground(new Color(0,0,0, (float) 0.9));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton1.setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
               BorderFactory.createLineBorder(Color.WHITE, 4)));
                jButton1.setBackground(Color.WHITE);
                jButton1.setForeground(Color.BLACK);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                jButton1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
                BorderFactory.createLineBorder(new Color(0,0,0,0) , 4)));
                jButton1.setBackground(new Color(0,0,0,(float)0.05));
                jButton1.setForeground(Color.BLACK);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                jButton1.setBorder(BorderFactory.createCompoundBorder(
               BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
               BorderFactory.createLineBorder(Color.WHITE, 4)));
                jButton1.setBackground(Color.WHITE);
                jButton1.setForeground(Color.BLACK);
            }
            
            
            
            
            
        });
        
        PlaceHolder holder1 = new PlaceHolder(usernameField , "Enter your username ");
        usernameField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                usernameField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLUE),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                RetypePassword.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
            }

            
            
        });
        
        passwordField.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLUE),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                usernameField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                RetypePassword.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
            }

            
            
        });
        
        RetypePassword.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                passwordField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                usernameField.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLACK),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
                
                RetypePassword.setBorder(new CompoundBorder(
                        BorderFactory.createLineBorder(Color.BLUE),
                        BorderFactory.createEmptyBorder(2, 7,2,5)
                ));
            }

            
            
        });
        
        btnRegister.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(usernameField.getText().toString().isEmpty() || 
                        passwordField.getText().toString().isEmpty() || 
                        RetypePassword.getText().toString().isEmpty()){
                    JOptionPane.showMessageDialog(rootPane, "Field cannot be blank!");
                }else{
                    if( !passwordField.getText().toString().equals(RetypePassword.getText().toString()) ){
                        JOptionPane.showMessageDialog(rootPane, "Password doesn't match!");
//                        JOptionPane.showMessageDialog(rootPane, passwordField.getText().toString() + " " + RetypePassword.getText().toString());
                    }else{
                        try {
                            _validasiRegister(usernameField.getText().toString() , passwordField.getText().toString());
                        } catch (SQLException ex) {
                            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        });
    }

    
    protected void _validasiRegister(String username , String password) throws SQLException{
        if(conn.registerNewUser(username, password)){
            JOptionPane.showMessageDialog(rootPane, "Congratulation! Your Account successfully been registered");
            setVisible(false);
            Login newFrame = new Login();
            
        }else{
            JOptionPane.showMessageDialog(rootPane, "Username already been registered");
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        center = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        btnRegister = new javax.swing.JLabel();
        passwordLabel1 = new javax.swing.JLabel();
        RetypePassword = new javax.swing.JPasswordField();
        jLabel10 = new javax.swing.JLabel();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(java.awt.Color.white);
        setResizable(false);

        center.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Already have an account ?");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setText("LOG IN");
        jButton1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setMargin(new java.awt.Insets(20, 20, 20, 20));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Meiryo UI", 1, 24)); // NOI18N
        jLabel3.setText("Register New Member");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(104, 104, 104));
        jLabel8.setText("Username");
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, 3, 1, 1));

        usernameField.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        usernameField.setForeground(new java.awt.Color(100, 100, 100));
        usernameField.setCaretColor(new java.awt.Color(50, 50, 50));
        usernameField.setMargin(new java.awt.Insets(2, 7, 2, 5));
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        passwordLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passwordLabel.setForeground(new java.awt.Color(104, 104, 104));
        passwordLabel.setText("Password");
        passwordLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, 3, 1, 1));

        btnRegister.setBackground(new java.awt.Color(40, 40, 40));
        btnRegister.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnRegister.setText("Sign Up");
        btnRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegister.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRegister.setOpaque(true);

        passwordLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        passwordLabel1.setForeground(new java.awt.Color(104, 104, 104));
        passwordLabel1.setText("Retype Password");
        passwordLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, 3, 1, 1));

        jLabel10.setForeground(new java.awt.Color(194, 194, 194));
        jLabel10.setText("Copyright ©  2019 - Rizalord");

        javax.swing.GroupLayout centerLayout = new javax.swing.GroupLayout(center);
        center.setLayout(centerLayout);
        centerLayout.setHorizontalGroup(
            centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(passwordLabel1)
                    .addComponent(passwordLabel)
                    .addComponent(jLabel8)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameField)
                    .addComponent(passwordField)
                    .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RetypePassword))
                .addContainerGap(280, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addGroup(centerLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addGap(16, 16, 16))
        );
        centerLayout.setVerticalGroup(
            centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(centerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(80, 80, 80)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(passwordLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(passwordLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RetypePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/GoCure.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/68735995-light-blue-paper-texture-useful-as-a-background-vertical.jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(31, 34, 37));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("<html><p align=\"center\" style=\"margin-bottom:7px\">A powerfull yet easy-to-use</p>\n<p align=\"center\" style=\"margin-bottom:7px\"> application for managing</p>\n<p align=\"center\" style=\"margin-bottom:7px\">any medicine</p></html>"); // NOI18N
        jLabel6.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel7.setForeground(new java.awt.Color(31, 34, 37));
        jLabel7.setText("Version 1.1");

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(jLabel5)
                    .addContainerGap(72, Short.MAX_VALUE)))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(25, 25, 25)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(54, Short.MAX_VALUE)))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(84, 84, 84)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(268, Short.MAX_VALUE)))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(267, 267, 267)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(152, Short.MAX_VALUE)))
        );
        jLayeredPane2.setLayer(jLabel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(jLabel7, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(center, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(center, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLayeredPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameFieldActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Register.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                Register frame = new Register();
//                frame.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPasswordField RetypePassword;
    private javax.swing.JLabel btnRegister;
    private javax.swing.JPanel center;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel passwordLabel1;
    private javax.swing.JTextField usernameField;
    // End of variables declaration//GEN-END:variables
}
