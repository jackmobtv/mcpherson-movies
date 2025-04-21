package net.mcphersonmovies.mcphersonmovies.model;

import jakarta.servlet.http.HttpServletRequest;
import net.mcphersonmovies.shared.AzureEmail;
import net.mcphersonmovies.shared.Helpers;

import java.sql.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public static boolean add(User user, Timestamp dob) {
        try(
            Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_new_user(?,?,?)}");
        ) {
            statement.setString(1, user.getEmail());
            statement.setString(2, Helpers.CharToString(user.getPassword()));
            statement.setTimestamp(3, dob);
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
        try(
            Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_lock_user(?)}");
        ) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String passwordReset(String email, HttpServletRequest req) {
        User user = get(email);
        if (user == null) {
            return "Sent Password Reset Link to Email if it Exists";
        } else {
            try(Connection connection = getConnection()) {
                String uuid = String.valueOf(UUID.randomUUID());
                CallableStatement statement = connection.prepareCall("{call sp_insert_password_reset(?,?,?)}");
                statement.setString(1, email);
                statement.setString(2, uuid);
                statement.registerOutParameter(3, java.sql.Types.INTEGER);
                statement.execute();
                int rowsAffected = statement.getInt(3);
                if (rowsAffected > 0) {
                    String subject = "Password Reset Link";
                    String body = "<h2>Reset Password</h2>";
                    body += "<p>Please click this link to reset your password. This link expires in 30 minutes.</p>";

                    String appURL = "";
                    if(req.getServerName().equals("localhost")) {
                        appURL = req.getServletContext().getInitParameter("appURLLocal");
                    } else {
                        appURL = req.getServletContext().getInitParameter("appURLCloud");
                    }
                    String URL = String.format("%s/new-password?token=%s", appURL, uuid);

                    body += "<a href='" + URL + "' target='_blank'>Reset Password</a>";
                    body += "<p>If you did not request a reset, you can ignore this message.</p>";

                    AzureEmail.sendEmail(email, subject, body);

                    return "Sent Password Reset Link to Email if it Exists";
                } else {
                    return "We couldn't process your password reset link, Try Again";
                }
            } catch (SQLException ex) {
                return ex.getMessage();
            }
        }
    }

    public static String getPasswordReset(String token) {
        String email = "";
        try (
            Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_select_password_reset(?)}");
        ) {
            statement.setString(1, token);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Instant now = Instant.now();
                Instant created_at = resultSet.getTimestamp("created_at").toInstant();
                Duration duration = Duration.between(created_at, now);
                long minutesElapsed = duration.toMinutes();
                if(minutesElapsed < 30) {
                    email = resultSet.getString("email");
                }
                int id = resultSet.getInt("id");
                CallableStatement statement2 = connection.prepareCall("{CALL sp_delete_password_reset(?)}");
                statement2.setInt(1, id);
                statement2.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return email;
    }

    public static boolean updatePassword(String email, char[] password) {
        try (Connection connection = getConnection()) {
            if (connection != null) {
                try (CallableStatement statement = connection.prepareCall("{CALL sp_update_user_password(?,?)}")) {
                    statement.setString(1, email);
                    statement.setString(2, Helpers.CharToString(password));
                    int rows = statement.executeUpdate();
                    return rows == 1;
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return false;
    }

    public static boolean update(User user) {
        try(
            Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_update_user_profile(?,?,?,?,?,?,?,?,?)}")
        ) {
            statement.setInt(1, user.getUserId());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhone());
            statement.setString(6, user.getLanguage());
            statement.setString(7, user.getTimezone());
            statement.setString(8, user.getPronouns());
            statement.setString(9, user.getDescription());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static boolean delete(String email, char[] password) {
        try(Connection connection = getConnection()) {
            CallableStatement statement = connection.prepareCall("{CALL sp_delete_user(?,?)}");
            statement.setString(1, email);
            statement.setString(2, Helpers.CharToString(password));
            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static boolean upgrade(int userId) {
        try(
            Connection connection = getConnection();
            CallableStatement statement = connection.prepareCall("{CALL sp_upgrade_user(?)}")
        ) {
            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
