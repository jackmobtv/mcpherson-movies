package net.mcphersonmovies.shared;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQL_Connect {
    public static Connection getConnection() throws SQLException {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/movie_database";
        String username = "postgres";
        String password = "password";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
            if (connection.isValid(2)) {
                return connection;
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            if(getConnection() != null) {
                System.out.println("Connection successful");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
