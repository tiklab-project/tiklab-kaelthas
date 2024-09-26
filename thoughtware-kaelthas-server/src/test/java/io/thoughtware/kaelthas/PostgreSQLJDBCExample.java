package io.thoughtware.kaelthas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class PostgreSQLJDBCExample {

    public static void main(String[] args) {
        // PostgreSQL JDBC URL
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "root";

        // Connection object
        Connection conn = null;

        try {
            // Load PostgreSQL JDBC Driver
            Class.forName("org.postgresql.Driver");

            // Establish connection
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to the PostgreSQL server successfully.");

            // Create a statement object to perform a query
            Statement stmt = conn.createStatement();
            String query = "SELECT version();";  // Example query

            // Execute the query
            ResultSet rs = stmt.executeQuery(query);

            // Process the result set
            while (rs.next()) {
                System.out.println("PostgreSQL version: " + rs.getString(1));
            }

            // Close the result set, statement, and connection
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Connection failure: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found: " + e.getMessage());
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
