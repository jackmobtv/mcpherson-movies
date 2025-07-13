package net.mcphersonmovies.mcphersonmovies.model;

import net.mcphersonmovies.shared.Validators;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.util.Date;

public class User implements Comparable<User> {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private char[] password;
    private String language;
    private String status;
    private String privileges;
    private Instant createdAt;
    private String timezone;
    private Instant dateofbirth;
    private String pronouns;
    private String description;

    public User(int userId, String firstName, String lastName, String email, String phone, String language, String status, String privileges, Instant createdAt, String timezone, Instant dateofbirth, String pronouns, String description) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.status = status;
        this.privileges = privileges;
        this.createdAt = createdAt;
        this.timezone = timezone;
        this.dateofbirth = dateofbirth;
        this.pronouns = pronouns;
        this.description = description;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password.toCharArray();
    }

    public User() {
    }

    public User(int id, String firstName, String lastName, String email, String phone, String language, String pronouns, String description) {
        this.userId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.language = language;
        this.pronouns = pronouns;
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(!Validators.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if(!Validators.isValidPhone(phone)) {
            throw new IllegalArgumentException("Invalid phone number");
        }
        this.phone = phone;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        if(!Validators.isValidLanguage(language)) {
            throw new IllegalArgumentException("Invalid language option");
        }
        this.language = language;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Date getCreatedAtDate() {
        return Date.from(createdAt);
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NotNull User o) {
        int result = this.lastName.compareTo(o.lastName);
        if (result == 0) {
            result = this.firstName.compareTo(o.firstName);
        }
        return result;
    }

    public Instant getDateofbirth() {
        return dateofbirth;
    }

    public Date getDateofbirthDate() {
        return Date.from(dateofbirth);
    }

    public void setDateofbirth(Instant dateofbirth) {
        this.dateofbirth = dateofbirth;
    }


    public String getPronouns() {
        return pronouns;
    }

    public void setPronouns(String pronouns) {
        this.pronouns = pronouns;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean validatePassword(String password) {
        if(password != null) {
            String passwordString = String.valueOf(password);
            if(!Validators.isStrongPassword(passwordString)) {
                throw new IllegalArgumentException("Password requires at least 8 characters, and at least 3 of 4: lowercase letter, uppercase letter, number, symbol");
            } else {
                return true;
            }
        }
        return false;
    }

    public String getFullName() {
        String fullName = "Anonymous";

        if(!firstName.isEmpty() || !lastName.isEmpty()) {
            fullName = firstName + " " + lastName;
        }

        return fullName.trim();
    }
}
