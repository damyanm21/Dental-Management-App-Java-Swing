/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;
import java.sql.*;
import java.util.*;
import java.util.logging.*;
import javax.management.Query;
/**
 *
 * @author Asus
 */
public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:DentalDB.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    
    
    public ArrayList<String> select(String[] columnsArray, String table){
        ArrayList<String> data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        String sql = "SELECT " + columns + " FROM " + table;
        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i]) + "---";
                }
                row = row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    
    
    public ArrayList<String> selectOrderBy(String[] columnsArray, String table, String uslovie, String order){
        ArrayList<String> data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);

        String sql = "SELECT " + columns + " FROM " + table + " order by " + uslovie + " " + order;
        //SELECT Title FROM customers order by Title ASC

        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i]) + "---";
                    
                }
                row = row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    
    
    public void insert(String table, String[] columnsArray, String[] valuesArray){
        String columns = String.join(", ", columnsArray);
        String values = String.join("', '", valuesArray);
        
        String sql = "INSERT INTO " + table + " (" + columns + ") VALUES ('" + values + "')";
        //INSERT INTO Patients (PatName, PatEgn, PatPhone, PatAddress, PatAlergies, PatIllness) VALUES ('Ivan Ivanov', '9001010101', '0888888899', 'bul. Treti Mart', 'none', 'none');
        
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void update(String table, String[] columnsArray, String[] valuesArray, String whereCol, String whereVal){
        //UPDATE Patients SET PatName = '...' PatEgn = '...', PatPhone = '...', PatAddress = '...', PatAlergies = '...', PatIllness = '...', WHERE PatientId LIKE '...'
        String sql = "UPDATE " + table + " SET ";
        for (int i = 0; i < columnsArray.length; i++) {
            sql = sql + columnsArray[i] + " = '" + valuesArray[i] + "', ";
        }
        sql = sql.substring(0, sql.length()-2);
        sql = sql + " WHERE " + whereCol + " LIKE '" + whereVal + "'";
        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void delete(String table, String column, String value){

        String sql = "DELETE FROM " + table + " WHERE "+column+" = "+value;

        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<String> selectWhere(String[] columnsArray, String table, String col1, String col2){
        ArrayList<String> data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        String sql = "SELECT " + columns + " FROM " + table + " WHERE " + col1 + " LIKE '%" + col2 + "%'" ;
        //SELECT PrescId FROM Prescriptions WHERE PrescId LIKE 3
        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i]) + "---";
                }
                row = row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ArrayList<String> selectWhere2(String[] columnsArray, String table, int[] whereCol, String[] usloviq){
        ArrayList<String> data = new ArrayList<String>();
        String columns = String.join(", ", columnsArray);
        //select firstname, lastname from employees
        //wherecol-> 0, 1
        //usloviq -> a, b
        String sql = "SELECT " + columns + " FROM "+table+" WHERE ";
        for (int i = 0; i < whereCol.length; i++) {
            sql = sql + columnsArray[whereCol[i]] + " like '%" + usloviq[i] + "%' or ";
        }
        //sled purva vrutka -> select fn, ln from empl where firstname like '%an%' or 
        //sled 2rata -> select fn, ln, from empl where fn like ... or lastname like ... or 
        sql = sql.substring(0, sql.length()-4);
        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
               String row ="";
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i]) + "---";
                }
                //row->Ime---Familiq---titla---
                row = row.substring(0, row.length()-3);
                //row->ime---familiq---titla
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
}

