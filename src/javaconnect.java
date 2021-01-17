import java.sql.*;
import javax.swing.*;
public class javaconnect {
    Connection conn;
    public static Connection ConnectDb()
    {
        try {
           Class.forName("org.sqlite.JDBC");
            Connection conn =DriverManager.getConnection("jdbc:sqlite:C:\\Users\\HARDEXICO\\Documents\\NetBeansProjects\\Library Management Project\\lbm.db");
        return conn;
        }
            catch(Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
                return null;    
            }
   
    }
}
