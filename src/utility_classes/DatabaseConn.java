package utility_classes;

import IT_admin_dash.IT_admin_dash_Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



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
