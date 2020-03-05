/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.my;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.sql.*;

/**
 *
 * @author Jaskaran
 */
public class Employee {
    private String empName;
    private int empSalary;
    private int empDeptId;
    
    public void readData(){
        try(Scanner input = new Scanner(new File("src/com/my/emp_data.txt"))){
            
            while(input.hasNextLine()){
                empName = "";
                String line;
                
                line = input.nextLine();
                
                //now process the line of textfor each data item
                try(Scanner data = new Scanner(line)){
                    while(!data.hasNextInt()){
                        empName += data.next()+" ";
                    }
                    
                    empName = empName.trim();
                    
                    //get Salary
                    if(data.hasNextInt()){
                        empSalary = data.nextInt();
                    }
                    
                    
                    //get dapartmant id
                    if(data.hasNextInt()){
                        empDeptId = data.nextInt();
                    }
                    
                }
                //System.out.println(empName+"\t"+empSalary+"\t"+empDeptId);
                saveData();
            }
            
        }catch(IOException e){
            System.out.println(e);
        }
    }
    
    //now save the data into database
    private void saveData(){
        try(Connection conn = connect();
                PreparedStatement patat = conn.prepareStatement("INSERT INTO employee VALUES(?, ?, ?)")){
        
            patat.setString(1, empName);
            patat.setInt(2, empSalary);
            patat.setInt(3, empDeptId);
            
            patat.executeUpdate();
            
        }catch(SQLException e){
            System.out.println(e);
        }
    }
    
    //create a connection to the database
    private Connection connect(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","");
        }catch(SQLException | ClassNotFoundException e){
            System.out.println(e);
            return null;
        }
    
    }
}

class FDemo{
    public static void main(String[] args){
        Employee emp = new Employee();
        try{
            emp.readData();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
