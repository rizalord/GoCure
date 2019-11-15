package com.rizalord.models;

import Data.AllProducts;
import com.rizalord.core.Connnection;
import java.sql.SQLException;
import java.util.ArrayList;

public class Product_Model extends Connnection {
    public boolean insertProduct(String name , String category , int price , int stocks , String date) throws SQLException{
        String query = "INSERT INTO product(name , category , price , stocks , date) VALUES('" +
                name + "','" + category +"',"+ price + ',' + stocks +",'" + date + "')";
        boolean result = false;
        try{
            
            result = stmt.executeUpdate(query) == 1 ? true : false;
            
        }catch(Exception e){
            System.out.println(e + query);
        }
        
        return result;
        
        //        if(stmt.executeUpdate(query) == 1){
//            return true;
//        }else{
//            return false;
//        }
        
        
    }
    
    public ArrayList<AllProducts> getAllData() throws SQLException{
        String query = "SELECT * FROM product";
        ArrayList<AllProducts> produk = new ArrayList<AllProducts>();
        
        try{
            rs = stmt.executeQuery(query);
            boolean row = false;

            while(rs.next()){
                if(row == false){
                    row = true;
                }
                
                AllProducts produkItem = new AllProducts();
                produkItem.id = rs.getInt("id_product");
                produkItem.name = rs.getString("name");
                produkItem.category = rs.getString("category");
                produkItem.price = rs.getInt("price");
                produkItem.stocks = rs.getInt("stocks");
                produkItem.date = rs.getString("date");
                        
                produk.add(produkItem);

            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return  produk;
        
    }
    
    public void updateProduct(int nameKey , ArrayList list){
        String name = list.get(0).toString();
        String category = list.get(1).toString();
        int price =  Integer.parseInt(list.get(2).toString());
        int stocks =  Integer.parseInt(list.get(3).toString());
        String date = list.get(4).toString();
        
        String query = "UPDATE product SET name='" + name + "',category='" + category + "',price=" + price + ",stocks=" + stocks + ",date='" + date + "' WHERE id_product=" + nameKey ;
        
        try {
            stmt.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e);
        }
        
        System.out.println(nameKey);
        
    }
    
    public void deleteProduct(int id){
        String query = "DELETE FROM product WHERE id_product=" + id ;
        
        try{
            stmt.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    public int getFirstId(){
        String query = "SELECT id_product FROM product";
        int res = 0;
        
        try{
            rs = stmt.executeQuery(query);
            while(rs.next()){
                res = rs.getInt("id_product");
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return res;
        
    }
    
    public void deleteAllProduct(){
        String query = "DELETE FROM product" ;
        
        try{
            stmt.executeUpdate(query);
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
