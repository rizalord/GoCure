package com.rizalord.models;

import Data.HistoryTransactionData;
import Data.ProductCart;
import Data.TransactionOnce;
import com.rizalord.core.Connnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Transaction_Model extends Connnection{
    
    public boolean addNewTransaction(ArrayList<ProductCart> cart , String username){
        
        for(int i = 0 ; i < cart.size() ; i++){
            String query = "INSERT INTO transaction (client_username , name , category , price , quantity , totalPrice) values('"+
                username + "','" +  cart.get(i).name + "','" + cart.get(i).category + "'," + cart.get(i).price + "," + cart.get(i).quantity + "," + 
                    cart.get(i).totalPrice + ")";
            
            try{
                stmt.executeUpdate(query);
            }catch(Exception e){
                System.out.println(e);
                return false;
            }
        }
        
        return true;
    }
    
    public boolean checkTransaction(String username){
    
        boolean status = true;
        String query = "SELECT * FROM transaction WHERE client_username='" + username + "'";
        
        try{
            rs = stmt.executeQuery(query);
            if(!rs.next()){
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
            status = false;
        }
        
        return status;
    }
    
    public ArrayList<TransactionOnce> getTransactionTable(){
        String query = "SELECT client_username , SUM(quantity) as totalItem , SUM(totalPrice) as totalPrice FROM transaction GROUP BY client_username";
        ArrayList<TransactionOnce> trans = new ArrayList<TransactionOnce>();
        
        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                TransactionOnce once = new TransactionOnce();
                once.username = rs.getString("client_username");
                once.totalQuantity = rs.getInt("totalItem");
                once.totalPrice = rs.getInt("totalPrice");
                
                trans.add(once);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return trans;
    }
    
    public ArrayList<ProductCart> getSingleTransaction(String username){
        String query = "SELECT * FROM transaction WHERE client_username='" + username + "'";
        ArrayList<ProductCart> trans = new ArrayList<ProductCart>();
        
        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                ProductCart once = new ProductCart();
                
                once.name = rs.getString("name");
                once.category = rs.getString("category");
                once.price = rs.getInt("price");
                once.quantity = rs.getInt("quantity");
                once.totalPrice = rs.getInt("totalPrice");
                
                trans.add(once);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return trans;
    }
    
    public boolean doTransaction(String username , int moneyBack , int moneyInput){
        ArrayList<ProductCart> list = this.getSingleTransaction(username);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
        String date = sdf.format(cal.getTime()).toString();
        
        try{
            for(int i = 0 ; i < list.size() ; i++){
                String query = "INSERT INTO history_transaction (client_username , name , category , price , quantity , totalPrice , amountMoney , date_created) values(" +
                        "'" + username + "','" + list.get(i).name + "','" + list.get(i).category + "'," + list.get(i).price + "," +
                        list.get(i).quantity + "," + list.get(i).totalPrice + "," + moneyInput + ",'" + date + "')";
                
                stmt.executeUpdate(query);
                
                String getData = "SELECT stocks FROM product WHERE name='" + list.get(i).name + "' AND category='" + list.get(i).category +"'";
                rs = stmt.executeQuery(getData);
                
                int stock = 0;
                if(rs.next()){
                    stock = rs.getInt("stocks");
                }
                
                stock -= list.get(i).quantity;
                
                String upQuery = "UPDATE product SET stocks=" + stock + " WHERE name='" + list.get(i).name + "' AND category='" + list.get(i).category +"'";
                stmt.executeUpdate(upQuery);
                
            }
            
            String delQuery = "DELETE FROM transaction WHERE client_username='" + username + "'";
            stmt.executeUpdate(delQuery);
            
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        
        
        
        return true;
    }
    
    public boolean deleteTransaction(String username , boolean all){
        boolean status = true;
        String delQuery;
        if(!all){
            delQuery = "DELETE FROM transaction WHERE client_username='" + username + "'";
        }else{
            delQuery = "DELETE FROM transaction";
        }
        
        
        try{
            
            stmt.executeUpdate(delQuery);
            
            
        }catch(Exception e){
            System.out.println(e);
            status = false;
        }
        
        return status;
    }
    
    public ArrayList<HistoryTransactionData> getHistoryData(){
        ArrayList<HistoryTransactionData> data = new ArrayList<HistoryTransactionData>();
        
        String query = "SELECT * FROM history_transaction ORDER BY id_transaction DESC";
        
        try{
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                HistoryTransactionData tmp = new HistoryTransactionData();
                tmp.client = rs.getString("client_username");
                tmp.name = rs.getString("name");
                tmp.category = rs.getString("category");
                tmp.price = rs.getInt("price");
                tmp.quantity = rs.getInt("quantity");
                tmp.totalPrice = rs.getInt("totalPrice");
                tmp.amountMoney = rs.getInt("amountMoney");
                tmp.date = rs.getString("date_created");
                
                data.add(tmp);
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return data;
        
    }
    
    public boolean checkHistoryTransaction(String username){
        return true;
    }
    public int checkRow (){
        String query = "SELECT * FROM history_transaction";
        int count = 0;
        
        try{
            rs = stmt.executeQuery(query);
            
            while(rs.next()){
                count+=1;
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return count;
    }
    
    public int getLastPrice(){
        String query = "SELECT amountMoney FROM history_transaction ORDER BY id_transaction DESC LIMIT 1";
        int amount = 0;
        
        try{
            rs = stmt.executeQuery(query);
            if(rs.next()){
                amount = rs.getInt("amountMoney");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return amount;
    }
    
    
}
