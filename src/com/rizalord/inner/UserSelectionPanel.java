/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rizalord.inner;

import com.rizalord.models.Transaction_Model;
import com.rizalord.outer.Login;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.border.Border;

/**
 *
 * @author asus
 */
public class UserSelectionPanel extends javax.swing.JFrame {

    String username;
    
    public UserSelectionPanel(String uname) {
        initComponents();
        
        setTitle("GoCure");
        setVisible(true);
        setLocationRelativeTo(null);
        
        username = uname;
        
        
        labelCart.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/cart.png")).getImage().getScaledInstance(180, 130, Image.SCALE_SMOOTH)));
        labelCart.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,9,5,5)));
        labelCart.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                labelCart.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(200,200,200),2) , BorderFactory.createEmptyBorder(5,9,5,5)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelCart.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,9,5,5)));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelCart.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(200,200,255),2) , BorderFactory.createEmptyBorder(5,9,5,5)));
                buyText.setForeground(new Color(130,130,130));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelCart.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,9,5,5)));
                buyText.setForeground(Color.WHITE);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                
                try{
                    Transaction_Model mdl = new Transaction_Model();
                    if(mdl.checkTransaction(username)){
                        JOptionPane.showMessageDialog(null, "Transaction on Pending!");
                    }else{
                        setVisible(false);
                        UserPanel pn = new UserPanel(username);
                    }
                }catch(Exception ex){
                    
                }
            }
            
            
            
            
            
            
            
            
        });
        
        labelLogout.setIcon(new ImageIcon(
                new javax.swing.ImageIcon(getClass().getResource("/Assets/img/logout.png")).getImage().getScaledInstance(150, 130, Image.SCALE_SMOOTH)));
        
        labelLogout.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,40,5,5)));
        
         labelLogout.addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                labelLogout.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(200,200,200),2) , BorderFactory.createEmptyBorder(5,40,5,5)));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                labelLogout.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,40,5,5)));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                labelLogout.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(200,200,255),2) , BorderFactory.createEmptyBorder(5,40,5,5)));
                exitText.setForeground(new Color(130,130,130));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                labelLogout.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createLineBorder(new Color(150,150,150),2) , BorderFactory.createEmptyBorder(5,40,5,5)));
                exitText.setForeground(Color.WHITE);
            }
            
            
            

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                try {
                    Login log = new Login();
                } catch (SQLException ex) {
                    Logger.getLogger(UserSelectionPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            
            
            
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layerPanel = new javax.swing.JLayeredPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        labelCart = new javax.swing.JLabel();
        labelLogout = new javax.swing.JLabel();
        buyText = new javax.swing.JLabel();
        exitText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setOpaque(false);

        jLabel2.setBackground(new java.awt.Color(255, 255, 240));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/GoCure.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/userBg.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layerPanelLayout = new javax.swing.GroupLayout(layerPanel);
        layerPanel.setLayout(layerPanelLayout);
        layerPanelLayout.setHorizontalGroup(
            layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layerPanelLayout.createSequentialGroup()
                    .addGap(336, 336, 336)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(332, Short.MAX_VALUE)))
        );
        layerPanelLayout.setVerticalGroup(
            layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layerPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(17, Short.MAX_VALUE)))
        );
        layerPanel.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        layerPanel.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        jPanel1.setBackground(java.awt.Color.white);

        labelCart.setBackground(new java.awt.Color(244, 244, 244));
        labelCart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/cart.png"))); // NOI18N
        labelCart.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 9, 5, 5));
        labelCart.setOpaque(true);

        labelLogout.setBackground(new java.awt.Color(244, 244, 244));
        labelLogout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Assets/img/cart.png"))); // NOI18N
        labelLogout.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 9, 5, 5));
        labelLogout.setOpaque(true);

        buyText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buyText.setForeground(new java.awt.Color(255, 255, 255));
        buyText.setText("Buy");

        exitText.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        exitText.setForeground(new java.awt.Color(255, 255, 255));
        exitText.setText("Exit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(labelCart, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(labelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(265, 265, 265)
                        .addComponent(buyText)
                        .addGap(261, 261, 261)
                        .addComponent(exitText)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exitText, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(buyText, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelCart, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(layerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UserSelectionPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserSelectionPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserSelectionPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserSelectionPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UserSelectionPanel("test");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel buyText;
    private javax.swing.JLabel exitText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel labelCart;
    private javax.swing.JLabel labelLogout;
    private javax.swing.JLayeredPane layerPanel;
    // End of variables declaration//GEN-END:variables
}
