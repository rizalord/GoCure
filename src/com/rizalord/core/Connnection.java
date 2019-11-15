package com.rizalord.core;

import Data.UserData;
import com.mysql.jdbc.*;
import com.mysql.jdbc.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;
import org.mindrot.jbcrypt.BCrypt;

public class Connnection {
    
    protected Connection conn;
    protected Statement stmt;
    protected ResultSet rs;
    
//    make a constructor
    public Connnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/gocure","root","");
            stmt = (Statement) conn.createStatement();
        }catch(Exception e){
//            empty statement
            System.out.println("Koneksi error : " + e);
        }
    }
    
    public ArrayList<UserData> getAllUser() throws SQLException{
        
        ArrayList<UserData> list = new ArrayList<UserData>();
        
        
        try{
            rs = stmt.executeQuery("SELECT * FROM user WHERE role_id != 1");
            
            while(rs.next()){
                UserData data = new UserData();
                data.username = rs.getString("username");
                data.date = rs.getString("date_created");
                
                list.add(data);
            }
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return list;
    }
    
    public int getLoginData(String username , String pass){
        
        int status = 0;
        String query = "SELECT * FROM user WHERE username='" + username + "'";
        try{
            
            rs = stmt.executeQuery(query);
                
            if(rs.next()){
                    if(rs.getInt("role_id") == 0){
                        if(BCrypt.checkpw(pass,rs.getString("password"))){
                            status = 1; 
                        }
                    }else{
                        if(rs.getString("password").equals(pass)){
                            status = 2;
                        }
                    }
            }
            
            
        }catch(Exception e){
            System.out.println(e);
        }
        
        return status;
    }
    
    public boolean registerNewUser(String username , String password){
        
        try{
            String query = "SELECT * FROM user WHERE username='" + username + "'";
            rs = stmt.executeQuery(query);
            
            if(rs.next()){
                return false;
            }else{
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY");
                
                String date = sdf.format(cal.getTime());
                password = BCrypt.hashpw(password, BCrypt.gensalt());
                
                String queryInsert = "INSERT INTO user(username, password , date_created , role_id) VALUES(" + 
                        "'" + username + "'" + "," + 
                        "'" + password + "'" + "," + 
                        "'" + date + "'" + "," + 
                        "0)" ;
                if(stmt.executeUpdate(queryInsert) == 1){
                    return true;
                }else{
                    return false;
                }
                
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return true;
    }
    
    public boolean removeUser(String username){
        String query = "DELETE FROM user WHERE username='" + username + "'";
        try{
            if(stmt.executeUpdate(query) != 0 ){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return true;
    }
    
    public boolean removeAllUser(){
        String query = "DELETE FROM user WHERE role_id != 1";
        try{
            if(stmt.executeUpdate(query) != 0 ){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.println(e);
        }
        
        return true;
    }
    
    
}
