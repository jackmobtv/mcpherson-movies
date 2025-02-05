package net.mcphersonmovies.mcphersonmovies.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class UserDAO {
    public static void main(String[] args) {
        getAll().forEach(System.out::println);
        System.out.println(get("delete-me@example.com"));
    }

    public static List<User> getAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            CallableStatement cs = connection.prepareCall("{call sp_get_all_users()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                char[] password = rs.getString("password").toCharArray();
                String language = rs.getString("language");
                String status = rs.getString("status");
                String privileges = rs.getString("privileges");
                Instant createdAt = rs.getTimestamp("created_at").toInstant();
                String timezone = rs.getString("timezone");
                User user = new User(userId, firstName, lastName, email, phone, password, language, status, privileges, createdAt, timezone);
                list.add(user);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public static User get(String email) {
        User user = null;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_get_user(?)}")) {
                    statement.setString(1, email);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int userId = resultSet.getInt("user_id");
                            String firstName = resultSet.getString("first_name");
                            String lastName = resultSet.getString("last_name");
                            String phone = resultSet.getString("phone");
                            char[] password = resultSet.getString("password").toCharArray();
                            String language = resultSet.getString("language");
                            String status = resultSet.getString("status");
                            String privileges = resultSet.getString("privileges");
                            Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                            String timezone = resultSet.getString("timezone");
                            user = new User(userId, firstName, lastName, email, phone, password, language, status, privileges, created_at, timezone);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return user;
    }
}
