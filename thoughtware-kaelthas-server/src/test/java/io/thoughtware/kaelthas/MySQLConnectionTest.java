package io.thoughtware.kaelthas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionTest {

    public static void main(String[] args) {
        // MySQL JDBC URL
        String url = "jdbc:mysql://172.11.1.28:3306/mysql";
        String username = "root";
        String password = "root";

        // Connection object
        Connection conn = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the MySQL server successfully.");
        } catch (SQLException e) {
            System.out.println("Connection failure: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException ex) {
                System.out.println("Error closing connection: " + ex.getMessage());
            }
        }
    }
}
