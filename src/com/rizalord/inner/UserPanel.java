/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rizalord.inner;

import Data.AllProducts;
import Data.ProductCart;
import com.placeholder.PlaceHolder;
import com.rizalord.core.Connnection;
import com.rizalord.models.Product_Model;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author asus
 */
public class UserPanel extends javax.swing.JFrame {

    
    Connnection conn = new Connnection();
    Product_Model pModel = new Product_Model();
    DefaultTableModel tableModel ;
    DefaultTableModel tableCartModel;
    String username;
    
    ArrayList<ProductCart> cart = new ArrayList<ProductCart>();
    
    public UserPanel(String uname) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        _windowsModel();
        initComponents();
        
        username = uname;
//        for window init
        setTitle("GoCure");
        setVisible(true);
        setLocationRelativeTo(null);
        
        
//        anyelse
        tableModel = (DefaultTableModel) tableOrder.getModel();
        tableCartModel = (DefaultTableModel) tableCart.getModel();
        _getData();
        _rowSelected();
        
//        placeHolder
        PlaceHolder quantity = new PlaceHolder(quantityField, "-");
        PlaceHolder totalOrd = new PlaceHolder(totalPriceField, "0");
        
        _manageSideBar();
        
        panelOrder.setVisible(true);
        panelCart.setVisible(false);
        
        tableOrder.getTableHeader().setReorderingAllowed(false);
        tableCart.getTableHeader().setReorderingAllowed(false);
        
//        manage tableCart
        _manageTableCart();

    }
    
    protected void _manageTableCart(){
        DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
        int finalP = 0;
        for(int i = 0 ; i < cart.size() ; i++){
            model.addRow(new Object[]{
                cart.get(i).name.toString() , cart.get(i).category.toString() , 
                cart.get(i).price , cart.get(i).quantity , cart.get(i).totalPrice
            });
            
            finalP += cart.get(i).totalPrice;
        }
        finalRiceField.setText(String.valueOf(finalP));
    }
    
    protected boolean validasiRow(){
        if(tableOrder.getSelectedRow() == -1 || quantityField.getText().trim().toString().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Select product first!");
        }else{
            return true;
        }
        return false;
    }
    
    protected boolean validasiRow2(){
        if(tableCart.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(rootPane, "Select product first!");
        }else{
            return true;
        }
        return false;
    }
    
    protected void _getData() throws SQLException{
        ArrayList<AllProducts> list =  new ArrayList<AllProducts>();
        list = pModel.getAllData();
        
        for(int i = 0 ; i < list.size() ; i++){
            tableModel.addRow(new Object[]{
                list.get(i).name , list.get(i).category , list.get(i).price , list.get(i).stocks
            });
        }
        
    }
    
    protected void _windowsModel() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
    }
    
    protected void _rowSelected(){
        tableOrder.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(!e.getValueIsAdjusting()){
                    try{
                    
                        String name = tableOrder.getValueAt(tableOrder.getSelectedRow(), 0).toString();
                        String category = tableOrder.getValueAt(tableOrder.getSelectedRow(),1).toString();
                        int price = Integer.parseInt(tableOrder.getValueAt(tableOrder.getSelectedRow(),2).toString());
                        int stocks = Integer.parseInt(tableOrder.getValueAt(tableOrder.getSelectedRow(),3).toString());

    //                    set value
                        nameField.setText(name);
                        categoryField.setText(category);
                        priceField.setText(Integer.toString(price));
                        stocksField.setText(Integer.toString(stocks));
                        totalPriceField.setText(String.valueOf(0));
                        quantityField.setText(String.valueOf(0));
                    }catch(Exception error){
                        
                    }
                }
                
                
                
            }
        });
    }
    
    protected void _manageSideBar(){
        medLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                panelOrder.setVisible(true);
                panelCart.setVisible(false);
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                medLabel.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                medLabel.setBackground(new Color(54,33,89));
            }
            
        });
        
        exitLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                UserSelectionPanel panel = new UserSelectionPanel(username);
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                exitLabel.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitLabel.setBackground(new Color(54,33,89));
            }
            
        });
        
        cartLabel.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                panelOrder.setVisible(false);
                panelCart.setVisible(true);
                DefaultTableModel model = (DefaultTableModel) tableCart.getModel();
                model.setRowCount(0);
                _manageTableCart();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                cartLabel.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cartLabel.setBackground(new Color(54,33,89));
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

        jLayeredPane2 = new javax.swing.JLayeredPane();
        jLabel1 = new javax.swing.JLabel();
        exitLabel = new javax.swing.JLabel();
        medLabel = new javax.swing.JLabel();
        cartLabel = new javax.swing.JLabel();
        multiLayer = new javax.swing.JLayeredPane();
        panelOrder = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableOrder = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        stocksField = new javax.swing.JTextField();
        categoryField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        totalPriceField = new javax.swing.JTextField();
        calBtn = new javax.swing.JButton();
        addCartBtn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        quantityField = new javax.swing.JTextField();
        panelCart = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        finalRiceField = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCart = new javax.swing.JTable();
        orderBtn = new javax.swing.JButton();
        delCartBtn = new javax.swing.JButton();
        rmAllBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLayeredPane2.setBackground(new java.awt.Color(54, 33, 89));
        jLayeredPane2.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("<html>\n\t<p style=\"\n\t\tborder-bottom : 1px solid white;\n\t\tpadding-bottom : 6px;\n\t\ttext-indent: 8px;\n\t\tdisplay: block;\n\t\twidth : 500px;\n\t\tcolor : white;\" onclick=\"return confirm('are you sure?');\">GoCure</p>\n\t\n\n</html>"); // NOI18N

        exitLabel.setBackground(new java.awt.Color(54, 33, 89));
        exitLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        exitLabel.setForeground(new java.awt.Color(230, 230, 230));
        exitLabel.setText("Exit");
        exitLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        exitLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitLabel.setMaximumSize(new java.awt.Dimension(639, 514));
        exitLabel.setMinimumSize(new java.awt.Dimension(639, 514));
        exitLabel.setOpaque(true);
        exitLabel.setPreferredSize(new java.awt.Dimension(639, 514));

        medLabel.setBackground(new java.awt.Color(54, 33, 89));
        medLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        medLabel.setForeground(new java.awt.Color(230, 230, 230));
        medLabel.setText("Order Medicine");
        medLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        medLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        medLabel.setMaximumSize(new java.awt.Dimension(639, 514));
        medLabel.setMinimumSize(new java.awt.Dimension(639, 514));
        medLabel.setOpaque(true);
        medLabel.setPreferredSize(new java.awt.Dimension(639, 514));

        cartLabel.setBackground(new java.awt.Color(54, 33, 89));
        cartLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cartLabel.setForeground(new java.awt.Color(230, 230, 230));
        cartLabel.setText("Cart");
        cartLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        cartLabel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cartLabel.setMaximumSize(new java.awt.Dimension(639, 514));
        cartLabel.setMinimumSize(new java.awt.Dimension(639, 514));
        cartLabel.setOpaque(true);
        cartLabel.setPreferredSize(new java.awt.Dimension(639, 514));

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(cartLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exitLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                    .addComponent(medLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(65, 65, 65)
                .addComponent(cartLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(exitLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(138, 138, 138)
                    .addComponent(medLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(398, Short.MAX_VALUE)))
        );
        jLayeredPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(exitLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(medLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(cartLabel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        panelOrder.setBackground(new java.awt.Color(255, 255, 255));

        tableOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Category", "Price/item (Rp)", "Stocks"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableOrder);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Order Medicines");

        jLabel4.setText("Medicine's name");

        nameField.setEditable(false);
        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        jLabel5.setText("Category");

        jLabel6.setText("Price");

        priceField.setEditable(false);
        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldActionPerformed(evt);
            }
        });

        jLabel8.setText("Rp.");

        jLabel9.setText("/item");

        jLabel7.setText("Stocks");

        stocksField.setEditable(false);
        stocksField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stocksFieldActionPerformed(evt);
            }
        });

        categoryField.setEditable(false);

        jLabel10.setText("Total Price : Rp.");

        totalPriceField.setEditable(false);
        totalPriceField.setText("0");
        totalPriceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                totalPriceFieldActionPerformed(evt);
            }
        });

        calBtn.setText("Calculate");
        calBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calBtnActionPerformed(evt);
            }
        });

        addCartBtn.setText("Add to Cart");
        addCartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCartBtnActionPerformed(evt);
            }
        });

        jLabel11.setText("Quantity");

        javax.swing.GroupLayout panelOrderLayout = new javax.swing.GroupLayout(panelOrder);
        panelOrder.setLayout(panelOrderLayout);
        panelOrderLayout.setHorizontalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(panelOrderLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelOrderLayout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(15, 15, 15)
                                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(priceField, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                                            .addComponent(stocksField))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel9))
                                    .addGroup(panelOrderLayout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(501, 501, 501))
                                    .addGroup(panelOrderLayout.createSequentialGroup()
                                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(panelOrderLayout.createSequentialGroup()
                                                .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(54, 54, 54)
                                                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(panelOrderLayout.createSequentialGroup()
                                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(totalPriceField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(panelOrderLayout.createSequentialGroup()
                                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(31, 31, 31)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(calBtn)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addCartBtn))
                                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        panelOrderLayout.setVerticalGroup(
            panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelOrderLayout.createSequentialGroup()
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalPriceField)
                            .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(categoryField)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(calBtn)
                                .addComponent(addCartBtn)))
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stocksField))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        panelCart.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Cart");

        jLabel15.setText("Final Price : Rp.");

        finalRiceField.setEditable(false);
        finalRiceField.setText("0");
        finalRiceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalRiceFieldActionPerformed(evt);
            }
        });

        tableCart.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Category", "Price/item (Rp)", "Quantity", "Total Price"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableCart);

        orderBtn.setText("Order");
        orderBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderBtnActionPerformed(evt);
            }
        });

        delCartBtn.setText("Delete");
        delCartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCartBtnActionPerformed(evt);
            }
        });

        rmAllBtn.setText("Remove All");
        rmAllBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rmAllBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCartLayout = new javax.swing.GroupLayout(panelCart);
        panelCart.setLayout(panelCartLayout);
        panelCartLayout.setHorizontalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCartLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 599, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCartLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(finalRiceField, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(orderBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delCartBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rmAllBtn)))
                .addContainerGap())
        );
        panelCartLayout.setVerticalGroup(
            panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(finalRiceField)
                    .addGroup(panelCartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(orderBtn)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(delCartBtn)
                        .addComponent(rmAllBtn)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout multiLayerLayout = new javax.swing.GroupLayout(multiLayer);
        multiLayer.setLayout(multiLayerLayout);
        multiLayerLayout.setHorizontalGroup(
            multiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelOrder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(multiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        multiLayerLayout.setVerticalGroup(
            multiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(multiLayerLayout.createSequentialGroup()
                .addComponent(panelOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
            .addGroup(multiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(multiLayerLayout.createSequentialGroup()
                    .addComponent(panelCart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        multiLayer.setLayer(panelOrder, javax.swing.JLayeredPane.DEFAULT_LAYER);
        multiLayer.setLayer(panelCart, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(multiLayer))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
            .addComponent(multiLayer)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void finalRiceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalRiceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_finalRiceFieldActionPerformed

    private void delCartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delCartBtnActionPerformed
        
        if(validasiRow2()){
            
            int stock = Integer.parseInt(tableCart.getValueAt(tableCart.getSelectedRow(), 3).toString());
            int row = cart.get(tableCart.getSelectedRow()).row;
            cart.remove(tableCart.getSelectedRow());
            
            int stockReal = Integer.parseInt(tableOrder.getValueAt(row, 3).toString());
            stockReal += stock;
            String temp = String.valueOf(stockReal);
            tableOrder.setValueAt(temp, row, 3);
            
            tableCartModel.removeRow(tableCart.getSelectedRow());
            tableCart.clearSelection();
            
            
            
        }
        
        
    }//GEN-LAST:event_delCartBtnActionPerformed

    private void rmAllBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rmAllBtnActionPerformed
        
        int rower = tableCart.getRowCount();
        
        if(tableCart.getRowCount() != 0 ){
            
            for(int i = 0 ; i < rower ; i++){
                int stock = Integer.parseInt(tableCart.getValueAt(0, 3).toString());
                int row = cart.get(0).row;
                cart.remove(0);

                int stockReal = Integer.parseInt(tableOrder.getValueAt(row, 3).toString());
                stockReal += stock;
                String temp = String.valueOf(stockReal);
                tableOrder.setValueAt(temp, row, 3);

                tableCartModel.removeRow(0);
                tableCart.clearSelection();
            }
            
           
             
            
        }else{
            JOptionPane.showMessageDialog(null, "Cart is Empty!");
        }
        
    }//GEN-LAST:event_rmAllBtnActionPerformed

    private void addCartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCartBtnActionPerformed
        //        add to cart
        if(validasiRow()){
            try {
                int jmlProduk = Integer.parseInt(stocksField.getText().toString());

                int jumlahOrder;
                if(quantityField.getText().toString().equals("-")){
                    jumlahOrder = 0;
                }else{
                    //                jumlahOrder = Integer.parseInt(quantityField.getText().toString());
                    jumlahOrder = Integer.parseInt(quantityField.getText().toString());
                }

                if(jumlahOrder > jmlProduk || jumlahOrder <= 0){
                    JOptionPane.showMessageDialog(rootPane, "Input valid quantity!");
                    totalPriceField.setText(String.valueOf(0));
                }else{
                    int hargaAwal = Integer.parseInt(priceField.getText().toString());
                    int hargaAkhir = jumlahOrder * hargaAwal;

                    ProductCart produk = new ProductCart();
                    produk.name = nameField.getText().toString();
                    produk.category = categoryField.getText().toString();
                    produk.price = Integer.parseInt(priceField.getText().toString());
                    produk.quantity = Integer.parseInt(quantityField.getText().toString());
                    produk.totalPrice = hargaAkhir;
                    produk.row = tableOrder.getSelectedRow();

                    cart.add(produk);

                    //                ambil stok
                    int stok = Integer.parseInt(tableOrder.getValueAt(tableOrder.getSelectedRow(), 3).toString());
                    int quantity = Integer.parseInt(quantityField.getText().toString());
                    int finalQuantity = stok - quantity ;

                    try{

                        tableOrder.setValueAt(finalQuantity, tableOrder.getSelectedRow(), 3);
                        tableOrder.clearSelection();
                    }catch(Exception e){
                        //                    System.out.println(e);
                    }

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Input valid quantity!");
            }
            
            
        }
    }//GEN-LAST:event_addCartBtnActionPerformed

    private void calBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calBtnActionPerformed
        if(validasiRow()){
            try {
                int jmlProduk = Integer.parseInt(stocksField.getText().toString());

                int jumlahOrder;
                if(quantityField.getText().toString().equals("-")){
                    jumlahOrder = 0;
                }else{
                    //                jumlahOrder = Integer.parseInt(quantityField.getText().toString());
                    jumlahOrder = Integer.parseInt(quantityField.getText().toString());
                }


                if(jumlahOrder > jmlProduk || jumlahOrder <= 0){
                    JOptionPane.showMessageDialog(rootPane, "Input valid quantity!");
                    totalPriceField.setText(String.valueOf(0));
                }else{
                    int hargaAwal = Integer.parseInt(priceField.getText().toString());
                    int harga = jumlahOrder * hargaAwal;

                    totalPriceField.setText(String.valueOf(harga));
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(rootPane, "Input valid quantity!");
            }
            
        }
    }//GEN-LAST:event_calBtnActionPerformed

    private void totalPriceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_totalPriceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_totalPriceFieldActionPerformed

    private void stocksFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stocksFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stocksFieldActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldActionPerformed

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void orderBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orderBtnActionPerformed
        
        
        if(tableCart.getRowCount() != 0 ){
            
            int select = JOptionPane.showConfirmDialog(panelCart, "Are you sure to complete the Order ?", "Complete Order", JOptionPane.YES_NO_OPTION);
        
            if(select == JOptionPane.YES_OPTION){
                OrderFrame frame  = new OrderFrame(cart , username , Integer.parseInt(finalRiceField.getText().toString()));
                setVisible(false);
            }
             
            
        }else{
            JOptionPane.showMessageDialog(null, "Cart is Empty!");
        }
        
        
        
    }//GEN-LAST:event_orderBtnActionPerformed

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
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new UserPanel("test");
                } catch (Exception ex) {
                    Logger.getLogger(UserPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCartBtn;
    private javax.swing.JButton calBtn;
    private javax.swing.JLabel cartLabel;
    private javax.swing.JTextField categoryField;
    private javax.swing.JButton delCartBtn;
    private javax.swing.JLabel exitLabel;
    private javax.swing.JTextField finalRiceField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel medLabel;
    private javax.swing.JLayeredPane multiLayer;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton orderBtn;
    private javax.swing.JPanel panelCart;
    private javax.swing.JPanel panelOrder;
    private javax.swing.JTextField priceField;
    private javax.swing.JTextField quantityField;
    private javax.swing.JButton rmAllBtn;
    private javax.swing.JTextField stocksField;
    private javax.swing.JTable tableCart;
    private javax.swing.JTable tableOrder;
    private javax.swing.JTextField totalPriceField;
    // End of variables declaration//GEN-END:variables

    
}
