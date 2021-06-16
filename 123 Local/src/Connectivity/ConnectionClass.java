package Connectivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {

    public Connection connection;
    public Connection getConnection(){
        String dbName = "inventarisation_system_db";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+dbName,username,password);
            System.out.println("jdbc:mysql://sql11.freemysqlhosting.net:3306"+dbName+username+password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error. DB fall");
        }
        return connection;
    }

}
