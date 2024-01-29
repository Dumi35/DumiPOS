/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utility_classes;

import IT_admin_dash.IT_admin_dash_Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;


/**
 *
 * @author dumid
 */
public class DatabaseConn {
    
    public static Connection connectDB(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pos_system", "root", "#BonBon214");
            return con;
     
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }
    
               
}
