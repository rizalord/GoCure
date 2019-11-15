/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rizalord.inner;

import Data.AllProducts;
import Data.HistoryTransactionData;
import Data.TransactionOnce;
import Data.UserData;
import com.placeholder.PlaceHolder;
import com.rizalord.core.Connnection;
import com.rizalord.models.Product_Model;
import com.rizalord.models.Transaction_Model;
import com.rizalord.outer.Login;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author asus
 */
public class Admin_Dashboard extends javax.swing.JFrame {

    DefaultTableModel model = null;
    Product_Model tblModel = new Product_Model();
    int lastKey  ;
    Connnection conn = new Connnection();
    Transaction_Model trans = new Transaction_Model();
    DefaultTableModel transModel ;
    
    public Admin_Dashboard() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        initComponents();
        
        setTitle("GoCure");
        setVisible(true);
        setLocationRelativeTo(null);
        
        tableProduct.getTableHeader().setReorderingAllowed(false);
        tableUser.getTableHeader().setReorderingAllowed(false);
        tableTrans.getTableHeader().setReorderingAllowed(false);
        tableHistory.getTableHeader().setReorderingAllowed(false);
        
        _windowSet();
        _manageSideBar();
        
        MultiLayer.setVisible(true);
        firstPanel.setVisible(true);
        secondPanel.setVisible(false);
        thirdPanel.setVisible(false);
        fourthPanel.setVisible(false);
        model = (DefaultTableModel) tableProduct.getModel();
        transModel = (DefaultTableModel) tableTrans.getModel();
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
        
        
        dateField.setText(sdf.format(cal.getTime()));
        tableProduct.removeColumn(tableProduct.getColumnModel().getColumn(0));
        
        _getAllData();
        _selectRow();
        if(tableProduct.getRowCount() != 0){
            lastKey = Integer.parseInt(tableProduct.getModel().getValueAt(tableProduct.getRowCount() - 1 , 0).toString());
        }else{
            lastKey = 0;
        }
        
        
        
//        panel 2
        ArrayList<UserData> list = new ArrayList<UserData>(); 
        list = conn.getAllUser();
        DefaultTableModel mUser = (DefaultTableModel) tableUser.getModel();
        
        for(int i = 0 ; i < list.size() ; i++){
            mUser.addRow(new Object[]{list.get(i).username , list.get(i).date});
        }
        
        PlaceHolder search = new PlaceHolder(searchField2 , "Search user...");
        
        
//        panel ke 3
        _getTransData();
        
//        panel ke 4
        _getHistoryData();
    }
    
    protected void _getHistoryData(){
        ArrayList<HistoryTransactionData> data = trans.getHistoryData();
        DefaultTableModel tableHistoryModel =  (DefaultTableModel) tableHistory.getModel();
        
        for(int i = 0 ; i < data.size() ; i++){
            tableHistoryModel.addRow(new Object[]{
                data.get(i).client , data.get(i).name , data.get(i).category , data.get(i).price , data.get(i).quantity , 
                data.get(i).amountMoney , data.get(i).date
            });
        }
    }
    
    protected boolean _selectRowTransaction(){
        if(tableTrans.getSelectedRow() == -1){
            return false;
        }else{
            return true;
        }
    }
    
    protected void _getTransData(){
        ArrayList<TransactionOnce> tank = trans.getTransactionTable();
        for(int i = 0 ; i < tank.size(); i++){
            transModel.addRow(new Object[]{
                tank.get(i).username.toString() , tank.get(i).totalQuantity , tank.get(i).totalPrice
            });
        }
    }
    
    protected void _selectRow(){
        tableProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                
                if(!e.getValueIsAdjusting()){
                    try{
                    
                        String name = tableProduct.getValueAt(tableProduct.getSelectedRow(), 0).toString();
                        String category = tableProduct.getValueAt(tableProduct.getSelectedRow(),1).toString();
                        int price = Integer.parseInt(tableProduct.getValueAt(tableProduct.getSelectedRow(),2).toString());
                        int stocks = Integer.parseInt(tableProduct.getValueAt(tableProduct.getSelectedRow(),3).toString());

    //                    set value
                        nameField.setText(name);
                        categoryField.setSelectedItem(category);
                        priceField.setText(Integer.toString(price));
                        stocksField.setText(Integer.toString(stocks));
                    }catch(Exception error){
                        
                    }
                }
                
                
                
            }
        });
    }
    
    protected void _getAllData() throws SQLException{
        ArrayList<AllProducts> produk = new ArrayList<AllProducts>();
        produk = tblModel.getAllData();
        
        for(int i = 0 ; i < produk.size() ; i++){
            String name = produk.get(i).name;
            String category = produk.get(i).category;
            int price  = produk.get(i).price;
            int stocks = produk.get(i).stocks;
            int id = produk.get(i).id;
            String date = produk.get(i).date;
            model.addRow(new Object[]{id , name , category , price , stocks , date});
        }
    }
    
    protected void _manageSideBar(){
        
        logoutLbl.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                try {
                    Login log = new Login();
                } catch (Exception ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                logoutLbl.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                logoutLbl.setBackground(new Color(54,33,89));
            }
            
        });
        
        historyLbl.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                firstPanel.setVisible(false);
                secondPanel.setVisible(false);
                thirdPanel.setVisible(false);
                DefaultTableModel tableHistoryModel =  (DefaultTableModel) tableHistory.getModel();
                tableHistoryModel.setRowCount(0);
                _getHistoryData();
                fourthPanel.setVisible(true);
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                historyLbl.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                historyLbl.setBackground(new Color(54,33,89));
            }
            
        });
        
        manageMedLbl.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                firstPanel.setVisible(true);
                secondPanel.setVisible(false);
                thirdPanel.setVisible(false);
                fourthPanel.setVisible(false);
            }
            

            @Override
            public void mouseEntered(MouseEvent e) {
                manageMedLbl.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manageMedLbl.setBackground(new Color(54,33,89));
            }
            
        });
        
        manageUserLbl.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                firstPanel.setVisible(false);
                secondPanel.setVisible(true);
                thirdPanel.setVisible(false);
                fourthPanel.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                manageUserLbl.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manageUserLbl.setBackground(new Color(54,33,89));
            }
            
        });
        
        manageTransaction.addMouseListener(new MouseAdapter() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                firstPanel.setVisible(false);
                secondPanel.setVisible(false);
                transModel.setRowCount(0);
                _getTransData();
                thirdPanel.setVisible(true);
                fourthPanel.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                manageTransaction.setBackground(new Color(74,53,109));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                manageTransaction.setBackground(new Color(54,33,89));
            }
            
        });
    }
    
    protected void _windowSet(){
//        setLocationRelativeTo(null);
        
//        setLayer
//        MultiLayer.add(firstPanel , 0);
//        MultiLayer.add(secondPanel , 1);
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
        manageUserLbl = new javax.swing.JLabel();
        manageMedLbl = new javax.swing.JLabel();
        manageTransaction = new javax.swing.JLabel();
        logoutLbl = new javax.swing.JLabel();
        historyLbl = new javax.swing.JLabel();
        MultiLayer = new javax.swing.JLayeredPane();
        fourthPanel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableHistory = new javax.swing.JTable();
        firstPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableProduct = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        categoryField = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        priceField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        stocksField = new javax.swing.JTextField();
        btnSubmit = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        dateField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        searchField = new javax.swing.JTextField();
        searchBtn = new javax.swing.JButton();
        secondPanel = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        searchField2 = new javax.swing.JTextField();
        searchBtn2 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        btnDeleteUser = new javax.swing.JButton();
        btnResetUser = new javax.swing.JButton();
        thirdPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableTrans = new javax.swing.JTable();
        btnDetail = new javax.swing.JButton();
        btnSubmitTrans = new javax.swing.JButton();
        btnDelTrans = new javax.swing.JButton();
        btnDelAllTrans = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLayeredPane2.setBackground(new java.awt.Color(54, 33, 89));
        jLayeredPane2.setOpaque(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel1.setText("<html>\n\t<p style=\"\n\t\tborder-bottom : 1px solid white;\n\t\tpadding-bottom : 6px;\n\t\ttext-indent: 8px;\n\t\tdisplay: block;\n\t\twidth : 500px;\n\t\tcolor : white;\" onclick=\"return confirm('are you sure?');\">GoCure</p>\n\t\n\n</html>"); // NOI18N

        manageUserLbl.setBackground(new java.awt.Color(54, 33, 89));
        manageUserLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        manageUserLbl.setForeground(new java.awt.Color(230, 230, 230));
        manageUserLbl.setText("Manage Users");
        manageUserLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        manageUserLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageUserLbl.setMaximumSize(new java.awt.Dimension(639, 514));
        manageUserLbl.setMinimumSize(new java.awt.Dimension(639, 514));
        manageUserLbl.setOpaque(true);
        manageUserLbl.setPreferredSize(new java.awt.Dimension(639, 514));

        manageMedLbl.setBackground(new java.awt.Color(54, 33, 89));
        manageMedLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        manageMedLbl.setForeground(new java.awt.Color(230, 230, 230));
        manageMedLbl.setText("Manage Medicines");
        manageMedLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        manageMedLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageMedLbl.setMaximumSize(new java.awt.Dimension(639, 514));
        manageMedLbl.setMinimumSize(new java.awt.Dimension(639, 514));
        manageMedLbl.setOpaque(true);
        manageMedLbl.setPreferredSize(new java.awt.Dimension(639, 514));

        manageTransaction.setBackground(new java.awt.Color(54, 33, 89));
        manageTransaction.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        manageTransaction.setForeground(new java.awt.Color(230, 230, 230));
        manageTransaction.setText("Manage Transaction");
        manageTransaction.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        manageTransaction.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        manageTransaction.setMaximumSize(new java.awt.Dimension(639, 514));
        manageTransaction.setMinimumSize(new java.awt.Dimension(639, 514));
        manageTransaction.setOpaque(true);
        manageTransaction.setPreferredSize(new java.awt.Dimension(639, 514));
        manageTransaction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                manageTransactionMouseClicked(evt);
            }
        });

        logoutLbl.setBackground(new java.awt.Color(54, 33, 89));
        logoutLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        logoutLbl.setForeground(new java.awt.Color(230, 230, 230));
        logoutLbl.setText("Logout");
        logoutLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        logoutLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        logoutLbl.setMaximumSize(new java.awt.Dimension(639, 514));
        logoutLbl.setMinimumSize(new java.awt.Dimension(639, 514));
        logoutLbl.setOpaque(true);
        logoutLbl.setPreferredSize(new java.awt.Dimension(639, 514));
        logoutLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutLblMouseClicked(evt);
            }
        });

        historyLbl.setBackground(new java.awt.Color(54, 33, 89));
        historyLbl.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        historyLbl.setForeground(new java.awt.Color(230, 230, 230));
        historyLbl.setText("Transaction History");
        historyLbl.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        historyLbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        historyLbl.setMaximumSize(new java.awt.Dimension(639, 514));
        historyLbl.setMinimumSize(new java.awt.Dimension(639, 514));
        historyLbl.setOpaque(true);
        historyLbl.setPreferredSize(new java.awt.Dimension(639, 514));
        historyLbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                historyLblMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jLayeredPane2Layout = new javax.swing.GroupLayout(jLayeredPane2);
        jLayeredPane2.setLayout(jLayeredPane2Layout);
        jLayeredPane2Layout.setHorizontalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(manageUserLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(manageTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(logoutLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(historyLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jLayeredPane2Layout.createSequentialGroup()
                    .addComponent(manageMedLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jLayeredPane2Layout.setVerticalGroup(
            jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jLayeredPane2Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(manageUserLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(manageTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(historyLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(logoutLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jLayeredPane2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jLayeredPane2Layout.createSequentialGroup()
                    .addGap(138, 138, 138)
                    .addComponent(manageMedLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(398, Short.MAX_VALUE)))
        );
        jLayeredPane2.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(manageUserLbl, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(manageMedLbl, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(manageTransaction, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(logoutLbl, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane2.setLayer(historyLbl, javax.swing.JLayeredPane.DEFAULT_LAYER);

        fourthPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel12.setText("Transaction History");

        tableHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "client's username", "name", "category", "price", "quantity", "amount money", "transaction date"
            }
        ));
        tableHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableHistoryMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableHistory);

        javax.swing.GroupLayout fourthPanelLayout = new javax.swing.GroupLayout(fourthPanel);
        fourthPanel.setLayout(fourthPanelLayout);
        fourthPanelLayout.setHorizontalGroup(
            fourthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fourthPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(fourthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(fourthPanelLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE))
                .addContainerGap())
        );
        fourthPanelLayout.setVerticalGroup(
            fourthPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(fourthPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                .addContainerGap())
        );

        firstPanel.setBackground(new java.awt.Color(255, 255, 255));

        tableProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "Category", "Price/item (Rp)", "Stocks", "Date modified"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableProduct);

        jLabel2.setText("Manage Medicines");

        jLabel4.setText("Name");

        jLabel5.setText("Category");

        jLabel6.setText("Price");

        jLabel7.setText("Stocks");

        nameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameFieldActionPerformed(evt);
            }
        });

        categoryField.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pil", "Capsule", "Tablet", "Potion", "Obat Cair", "Bubuk" }));
        categoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryFieldActionPerformed(evt);
            }
        });

        jLabel8.setText("Rp.");

        priceField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceFieldActionPerformed(evt);
            }
        });

        jLabel9.setText("/item");

        stocksField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stocksFieldActionPerformed(evt);
            }
        });

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        jButton4.setText("Reset");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        dateField.setEditable(false);
        dateField.setText("10/04/2003");
        dateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateFieldActionPerformed(evt);
            }
        });

        jLabel10.setText("Search");

        searchBtn.setText("GO");
        searchBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout firstPanelLayout = new javax.swing.GroupLayout(firstPanel);
        firstPanel.setLayout(firstPanelLayout);
        firstPanelLayout.setHorizontalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(410, 410, 410)
                .addComponent(dateField, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                .addGap(18, 18, 18))
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(firstPanelLayout.createSequentialGroup()
                        .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(firstPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(priceField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addGap(61, 61, 61))
                            .addGroup(firstPanelLayout.createSequentialGroup()
                                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(stocksField, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(firstPanelLayout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchBtn))
                            .addGroup(firstPanelLayout.createSequentialGroup()
                                .addComponent(btnSubmit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(firstPanelLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, firstPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        firstPanelLayout.setVerticalGroup(
            firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(firstPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateField))
                .addGap(18, 18, 18)
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(firstPanelLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(priceField, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(firstPanelLayout.createSequentialGroup()
                        .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSubmit)
                            .addComponent(btnEdit)
                            .addComponent(btnDelete)
                            .addComponent(jButton4)
                            .addComponent(nameField))
                        .addGap(111, 111, 111)))
                .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stocksField))
                    .addGroup(firstPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(searchField)
                        .addComponent(searchBtn))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        secondPanel.setBackground(new java.awt.Color(255, 255, 255));
        secondPanel.setMaximumSize(new java.awt.Dimension(659, 606));
        secondPanel.setMinimumSize(new java.awt.Dimension(659, 606));
        secondPanel.setName(""); // NOI18N
        secondPanel.setPreferredSize(new java.awt.Dimension(659, 606));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Manage Users");

        searchBtn2.setText("GO");
        searchBtn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBtn2ActionPerformed(evt);
            }
        });

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Username", "Date of Created"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableUser);

        btnDeleteUser.setText("Delete");
        btnDeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteUserActionPerformed(evt);
            }
        });

        btnResetUser.setText("Delete All");
        btnResetUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetUserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout secondPanelLayout = new javax.swing.GroupLayout(secondPanel);
        secondPanel.setLayout(secondPanelLayout);
        secondPanelLayout.setHorizontalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnResetUser, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)))
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addComponent(searchField2, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchBtn2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        secondPanelLayout.setVerticalGroup(
            secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(secondPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchField2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchBtn2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(secondPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(secondPanelLayout.createSequentialGroup()
                        .addComponent(btnDeleteUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnResetUser))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        thirdPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Manage Transactions");

        tableTrans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Client Name", "Total Item", "Total Price (Rupiah)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tableTrans);

        btnDetail.setText("Detail");
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });

        btnSubmitTrans.setText("Submit");
        btnSubmitTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitTransActionPerformed(evt);
            }
        });

        btnDelTrans.setText("Delete");
        btnDelTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelTransActionPerformed(evt);
            }
        });

        btnDelAllTrans.setText("Delete All");
        btnDelAllTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelAllTransActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout thirdPanelLayout = new javax.swing.GroupLayout(thirdPanel);
        thirdPanel.setLayout(thirdPanelLayout);
        thirdPanelLayout.setHorizontalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(thirdPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 649, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thirdPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnDetail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSubmitTrans)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelTrans)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelAllTrans)))
                .addContainerGap())
        );
        thirdPanelLayout.setVerticalGroup(
            thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thirdPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel3)
                .addGap(27, 27, 27)
                .addGroup(thirdPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDetail)
                    .addComponent(btnSubmitTrans)
                    .addComponent(btnDelTrans)
                    .addComponent(btnDelAllTrans))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 492, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout MultiLayerLayout = new javax.swing.GroupLayout(MultiLayer);
        MultiLayer.setLayout(MultiLayerLayout);
        MultiLayerLayout.setHorizontalGroup(
            MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 669, Short.MAX_VALUE)
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(secondPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 669, Short.MAX_VALUE))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(thirdPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(firstPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fourthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MultiLayerLayout.setVerticalGroup(
            MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 617, Short.MAX_VALUE)
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MultiLayerLayout.createSequentialGroup()
                    .addComponent(secondPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 11, Short.MAX_VALUE)))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MultiLayerLayout.createSequentialGroup()
                    .addComponent(thirdPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(MultiLayerLayout.createSequentialGroup()
                    .addComponent(firstPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 14, Short.MAX_VALUE)))
            .addGroup(MultiLayerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(fourthPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MultiLayer.setLayer(fourthPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MultiLayer.setLayer(firstPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MultiLayer.setLayer(secondPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);
        MultiLayer.setLayer(thirdPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MultiLayer))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLayeredPane2)
            .addComponent(MultiLayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameFieldActionPerformed

    private void priceFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_priceFieldActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        if(nameField.getText().toString().isEmpty() || 
                priceField.getText().toString().isEmpty() ||
                stocksField.getText().toString().isEmpty()){
            JOptionPane.showMessageDialog(rootPane, "Field cannot be blank!");
        }else{
            
//            tambah data ke database
            String name = nameField.getText().toString();
            int price = Integer.parseInt( priceField.getText().toString() );
            String category = categoryField.getSelectedItem().toString();
            int stocks = Integer.parseInt( stocksField.getText().toString() );
            String date = dateField.getText().toString();
            try {
                if(tblModel.insertProduct(name, category, price, stocks, date)){
                    System.out.println("Berhasil tambah data");
                }else{
                    System.out.println("Gagal tambah data");
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
            
            int id = 0;
            if(tableProduct.getRowCount() != 0){
                id = lastKey+=1;
            }else{
                id = tblModel.getFirstId();
            }
            System.out.println(id);
            
            model.addRow(new Object[]{id ,nameField.getText().toString() , categoryField.getSelectedItem().toString() , priceField.getText().toString() ,stocksField.getText().toString() , dateField.getText().toString()});
            
            nameField.setText("");
            priceField.setText("");
            stocksField.setText("");
            try{
                tableProduct.clearSelection();
            }catch(Exception e){
//                make it empty
            }
            
            
        }
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void dateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dateFieldActionPerformed

    private void categoryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_categoryFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_categoryFieldActionPerformed

    private void stocksFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stocksFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stocksFieldActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if(tableProduct.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(rootPane, "Select row to edit!");
        }else{
            if(nameField.getText().toString().isEmpty() || 
                priceField.getText().toString().isEmpty() ||
                stocksField.getText().toString().isEmpty()){
                JOptionPane.showMessageDialog(rootPane, "Field cannot be blank!");
            }else{
               tableProduct.setValueAt(nameField.getText().toString(), tableProduct.getSelectedRow(),0);
               tableProduct.setValueAt(categoryField.getSelectedItem().toString(), tableProduct.getSelectedRow(),1);
               tableProduct.setValueAt(priceField.getText().toString(), tableProduct.getSelectedRow(),2);
               tableProduct.setValueAt(stocksField.getText().toString(), tableProduct.getSelectedRow(),3);
               tableProduct.setValueAt(dateField.getText().toString(), tableProduct.getSelectedRow(),4);
               
//               update into database
               ArrayList list = new ArrayList();
               list.add(nameField.getText().toString());
               list.add(categoryField.getSelectedItem());
               list.add(priceField.getText().toString());
               list.add(stocksField.getText().toString());
               list.add(dateField.getText().toString());
               
               int nameId = Integer.parseInt(tableProduct.getModel().getValueAt(tableProduct.getSelectedRow(), 0).toString());
               tblModel.updateProduct(nameId , list);
               
               try{
                   tableProduct.clearSelection();
               }catch(java.lang.ArrayIndexOutOfBoundsException e){
//                   make it empty
               }
               
                nameField.setText("");
                priceField.setText("");
                stocksField.setText("");
               

            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if(tableProduct.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(rootPane, "Select row to delete!");
        }else{
//            get id
            int id = Integer.parseInt(tableProduct.getModel().getValueAt(tableProduct.getSelectedRow(), 0).toString());
            try{
                model.removeRow(tableProduct.getSelectedRow());
            }catch(Exception e){
                System.out.println(e);
            }
            
            tableProduct.addNotify();
            tableProduct.clearSelection();
            tblModel.deleteProduct(id);
            
            nameField.setText("");
            priceField.setText("");
            stocksField.setText("");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        
        int row = model.getRowCount();
        if(row != 0 ){
            int opsi = JOptionPane.showConfirmDialog(rootPane, "Are u sure to delete All data?", "Reset Data", JOptionPane.YES_NO_OPTION);
            if(opsi == JOptionPane.YES_OPTION){
            
                model.setRowCount(0);
                
                tblModel.deleteAllProduct();
                
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Table is Empty!");
        }
        
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void searchBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtnActionPerformed
        TableRowSorter sorter = new TableRowSorter<DefaultTableModel>(model);
        tableProduct.setRowSorter(sorter);
        if(searchField.getText().trim().length() != 0){
            
            
            
            RowFilter<DefaultTableModel,Object> rf = null;
            
            
            try{
                rf = RowFilter.regexFilter("(?i)" + searchField.getText());
            }catch(java.util.regex.PatternSyntaxException e){
                System.out.println(e);
            }
            
            sorter.setRowFilter(rf);
        }else{
            sorter.setRowFilter(null);
        }
    }//GEN-LAST:event_searchBtnActionPerformed

    private void btnDeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteUserActionPerformed
        if(tableUser.getSelectedRow() == -1){
            JOptionPane.showMessageDialog(rootPane, "Select data first!");
        }else{
            _deleteUser();
        }
    }//GEN-LAST:event_btnDeleteUserActionPerformed

    private void btnResetUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetUserActionPerformed
        int select = JOptionPane.showConfirmDialog(rootPane, "Are u sure to delete All User", "Reset All", JOptionPane.YES_NO_OPTION);
        if(select == JOptionPane.YES_OPTION){
            conn.removeAllUser();
            int jumlahRow = tableUser.getRowCount();
            System.out.println(jumlahRow);
            
            
            DefaultTableModel model = (DefaultTableModel) tableUser.getModel();
            model.setRowCount(0);
        }    
    }//GEN-LAST:event_btnResetUserActionPerformed

    private void searchBtn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBtn2ActionPerformed
        DefaultTableModel modelGanteng = (DefaultTableModel) tableUser.getModel();
        TableRowSorter sorter = new TableRowSorter<DefaultTableModel>(modelGanteng);
        tableUser.setRowSorter(sorter);
        if(searchField2.getText().trim().length() != 0){
            
            
            
            RowFilter<DefaultTableModel,Object> rf = null;
            
            try{
                rf = RowFilter.regexFilter("(?i)" + searchField2.getText());
            }catch(java.util.regex.PatternSyntaxException e){
                System.out.println(e);
            }
            
            sorter.setRowFilter(rf);
        }else{
            sorter.setRowFilter(null);
        }
    }//GEN-LAST:event_searchBtn2ActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
//        detail transaction
        if(_selectRowTransaction()){
            if(trans.checkTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString())){
                try {
                    DetailTransaction detail = new DetailTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString());
                } catch (Exception ex) {
                    System.out.println(ex);
                } 
            }else{
                JOptionPane.showMessageDialog(rootPane, "No Transaction selected!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Select row first!");
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void btnSubmitTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitTransActionPerformed
        if(_selectRowTransaction()){
            if(trans.checkTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString())){
                try {
                    boolean status = true;
                    int money = 0 ;
                    try{
                        money = Integer.parseInt(JOptionPane.showInputDialog("Enter the amount of money :"));
                    }catch(Exception exc){
                        status = false;
                    }
                    
                    if(status){
                        if(money < Integer.parseInt(tableTrans.getValueAt(tableTrans.getSelectedRow(), 2).toString())){
                            JOptionPane.showMessageDialog(rootPane, "Money is less . Invalid!");
                        } else {
                            if(trans.doTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString(), (money - Integer.parseInt(tableTrans.getValueAt(tableTrans.getSelectedRow(), 2).toString())), money)){
                                transModel.removeRow(tableTrans.getSelectedRow());
                            }else{
                                JOptionPane.showMessageDialog(rootPane, "Transaction Failed!");
                            }
                        }                        
                    }else{
                        JOptionPane.showMessageDialog(rootPane, "Invalid input!");                        
                    }
                    

                } catch (Exception ex) {
                    System.out.println(ex);
                } 
            }else{
                JOptionPane.showMessageDialog(rootPane, "No Transaction selected!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Select row first!");
        }
    }//GEN-LAST:event_btnSubmitTransActionPerformed

    private void manageTransactionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_manageTransactionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_manageTransactionMouseClicked

    private void btnDelTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelTransActionPerformed
        if(_selectRowTransaction()){
            if(trans.checkTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString())){
                if(trans.deleteTransaction(tableTrans.getValueAt(tableTrans.getSelectedRow(), 0).toString() , false)){
                    transModel.removeRow(tableTrans.getSelectedRow());
                    try{
                        tableTrans.clearSelection();
                    }catch(Exception e){
//                        biarin kosong
                    }
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Delete Transaction Failed");
                }
            }else{
                JOptionPane.showMessageDialog(rootPane, "No Transaction selected!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Select row first!");
        }
    }//GEN-LAST:event_btnDelTransActionPerformed

    private void btnDelAllTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelAllTransActionPerformed
        if(tableTrans.getRowCount() == 0){
            JOptionPane.showMessageDialog(rootPane, "Table is Empty!");
        }else{
            int answer = JOptionPane.showConfirmDialog(null, "Are u sure to delete All row ?", "Delete All", JOptionPane.YES_NO_OPTION);
            
            if(answer == JOptionPane.YES_OPTION){
                if(trans.deleteTransaction(null , true)){
                    transModel.setRowCount(0);
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Delete Transaction Failed");
                }
            }
            
        }
    }//GEN-LAST:event_btnDelAllTransActionPerformed

    private void logoutLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_logoutLblMouseClicked

    private void historyLblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_historyLblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_historyLblMouseClicked

    private void tableHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableHistoryMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableHistoryMouseClicked

    protected void _deleteUser(){
        String username = tableUser.getValueAt(tableUser.getSelectedRow(), 0).toString();
        
        conn.removeUser(username);
        DefaultTableModel model = (DefaultTableModel) tableUser.getModel();
        model.removeRow(tableUser.getSelectedRow());
        
        tableUser.clearSelection();
    }
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
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Admin_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Admin_Dashboard().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Admin_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane MultiLayer;
    private javax.swing.JButton btnDelAllTrans;
    private javax.swing.JButton btnDelTrans;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDeleteUser;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnResetUser;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JButton btnSubmitTrans;
    private javax.swing.JComboBox categoryField;
    private javax.swing.JTextField dateField;
    private javax.swing.JPanel firstPanel;
    private javax.swing.JPanel fourthPanel;
    private javax.swing.JLabel historyLbl;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel logoutLbl;
    private javax.swing.JLabel manageMedLbl;
    private javax.swing.JLabel manageTransaction;
    private javax.swing.JLabel manageUserLbl;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField priceField;
    private javax.swing.JButton searchBtn;
    private javax.swing.JButton searchBtn2;
    private javax.swing.JTextField searchField;
    private javax.swing.JTextField searchField2;
    private javax.swing.JPanel secondPanel;
    private javax.swing.JTextField stocksField;
    private javax.swing.JTable tableHistory;
    private javax.swing.JTable tableProduct;
    private javax.swing.JTable tableTrans;
    private javax.swing.JTable tableUser;
    private javax.swing.JPanel thirdPanel;
    // End of variables declaration//GEN-END:variables
}
