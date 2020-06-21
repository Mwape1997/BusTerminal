package at.ac.fhcampuswien.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection dbConnection;

    private DBConnection(){
    }


    public Connection getConnection(){
        Connection connection = null;
        try {

            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/";
            String dbName = "busterminal";
            String userName = "root"; // needs to be changed corresponding to your username (usually 'root')
            String password = "";

            Class.forName(driver);
            connection = DriverManager.getConnection(url + dbName, userName, password);

        }catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static DBConnection getInstance(){
        if (dbConnection == null)
            dbConnection = new DBConnection();
        return dbConnection;
    }

    public static void main(String[] args) {
        DBConnection db_connection = getInstance();
        System.out.println(db_connection.getConnection());
    }

}
