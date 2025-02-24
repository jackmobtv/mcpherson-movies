package net.mcphersonmovies.mcphersonmovies.model;

import net.mcphersonmovies.shared.Hashing;
import net.mcphersonmovies.shared.Helpers;

import java.security.NoSuchAlgorithmException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.mcphersonmovies.shared.MySQL_Connect.getConnection;

public class UserDAO {
    public static void main(String[] args) {
//        add(new User("bro1234@redrum.org", "Password@123"));
//        getAll().forEach(System.out::println);
//        System.out.println(get("admin@company.com"));
        System.out.println(auth("admin@company.com", "9c9064c59f1ffa2e174ee754d2979be80dd30db552ec03e7e327e9b1a4bd594e".toCharArray()));
    }

    public static List<User> getAll() {
        List<User> list = new ArrayList<>();
        try (Connection connection = getConnection()) {
            CallableStatement cs = connection.prepareCall("{call sp_get_all_users(0,0)}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String language = rs.getString("language");
                String status = rs.getString("status");
                String privileges = rs.getString("role_name");
                Instant createdAt = rs.getTimestamp("created_at").toInstant();
                String timezone = rs.getString("timezone");
                Instant dateofbirth = rs.getTimestamp("dateofbirth").toInstant();
                String pronouns = rs.getString("pronouns");
                String description = rs.getString("description");
                User user = new User(userId, firstName, lastName, email, phone, language, status, privileges, createdAt, timezone, dateofbirth, pronouns, description);
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
                try (CallableStatement statement = connection.prepareCall("{CALL sp_get_user_by_email(?)}")) {
                    statement.setString(1, email);
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            int userId = rs.getInt("user_id");
                            String firstName = rs.getString("first_name");
                            String lastName = rs.getString("last_name");
                            String phone = rs.getString("phone");
                            String language = rs.getString("language");
                            String status = rs.getString("status");
                            String privileges = rs.getString("role_name");
                            Instant created_at = rs.getTimestamp("created_at").toInstant();
                            String timezone = rs.getString("timezone");
                            Instant dateofbirth = rs.getTimestamp("dateofbirth").toInstant();
                            String pronouns = rs.getString("pronouns");
                            String description = rs.getString("description");
                            user = new User(userId, firstName, lastName, email, phone, language, status, privileges, created_at, timezone, dateofbirth, pronouns, description);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return user;
    }

    public static boolean add(User user) {
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_new_user(?,?)}");
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, Helpers.CharToString(user.getPassword()));
            statement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    public static User auth(String email, char[] password) {
        User user = null;
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_authenticate_user(?,?)}")) {
                    statement.setString(1, email);
                    statement.setString(2, Helpers.CharToString(password));
                    try (ResultSet rs = statement.executeQuery()) {
                        if (rs.next()) {
                            int userId = rs.getInt("user_id");
                            String firstName = rs.getString("first_name");
                            String lastName = rs.getString("last_name");
                            String phone = rs.getString("phone");
                            String language = rs.getString("language");
                            String status = rs.getString("status");
                            String privileges = rs.getString("role_name");
                            Instant created_at = rs.getTimestamp("created_at").toInstant();
                            String timezone = rs.getString("timezone");
                            Instant dateofbirth = rs.getTimestamp("dateofbirth").toInstant();
                            String pronouns = rs.getString("pronouns");
                            String description = rs.getString("description");
                            user = new User(userId, firstName, lastName, email, phone, language, status, privileges, created_at, timezone, dateofbirth, pronouns, description);
                        } else {
                            return null;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return user;
    }

    public static void lock(String email) {
        try(Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_lock_user(?)}");
        ) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
