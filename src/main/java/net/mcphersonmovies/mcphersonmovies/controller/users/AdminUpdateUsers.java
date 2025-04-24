package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;

import java.io.IOException;

import static net.mcphersonmovies.mcphersonmovies.model.UserDAO.get;

@WebServlet(value = "/edit-users")
public class AdminUpdateUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("activeUser");

        if (currentUser == null || !currentUser.getPrivileges().equals("Admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        int id = 0;
        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception ex) {
            session.setAttribute("flashMessageWarning", "User Does Not Exist");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/users"));
            return;
        }

        User user = UserDAO.get(id);

        if (user == null) {
            session.setAttribute("flashMessageWarning", "User Does Not Exist");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/users"));
            return;
        }

        req.setAttribute("user", user);

        req.setAttribute("pageTitle", "Edit User");
        req.getRequestDispatcher("WEB-INF/users/admin-edit-user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("activeUser");

        if (currentUser == null || !currentUser.getPrivileges().equals("Admin")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        boolean errorFound = false;

        int id = 0;

        try{
            id = Integer.parseInt(req.getParameter("id"));
        } catch (Exception ex) {
            errorFound = true;
            session.setAttribute("flashMessageWarning", "User does not Exist");
        }

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String language = req.getParameter("language");
        String pronouns = req.getParameter("pronouns");
        String description = req.getParameter("description");

        User user = new User(id, firstName, lastName, email, phone, language, pronouns, description);
        User originalUser = UserDAO.get(id);

        user.setTimezone("America/Chicago");

        req.setAttribute("user", user);

        if(firstName != null && !firstName.equals(originalUser.getFirstName())) {
            user.setFirstName(firstName);
        }
        if(lastName != null && !lastName.equals(originalUser.getLastName())) {
            user.setLastName(lastName);
        }
        if(email != null && !email.isEmpty() && !email.equals(originalUser.getEmail()) && UserDAO.get(email) != null) {
            errorFound = true;
            req.setAttribute("emailError", "A user with that email already exists.");
        } else {
            try {
                user.setEmail(email);
            } catch (IllegalArgumentException ex){
                errorFound = true;
                req.setAttribute("emailError", "Invalid email address");
            }
        }

        try {
            if(phone != null && !phone.equals(originalUser.getPhone())) {
                user.setPhone(phone);
            }
        } catch(IllegalArgumentException ex) {
            errorFound = true;
            req.setAttribute("phoneError", ex.getMessage());
        }

        try {
            if(!language.equals(originalUser.getLanguage())) {
                user.setLanguage(language);
            }
        } catch(IllegalArgumentException e) {
            errorFound = true;
            req.setAttribute("languageError", e.getMessage());
        }

        if(pronouns != null && !pronouns.equals(originalUser.getPronouns())) {
            user.setPronouns(pronouns);
        }

        if(description != null && !description.equals(originalUser.getDescription())) {
            user.setDescription(description);
        }

        if(!errorFound) {
            boolean userUpdated = false;
            try {
                userUpdated = UserDAO.update(user);
            } catch(RuntimeException ex) {
                session.setAttribute("flashMessageDanger", ex.getMessage()); // Change to a message like "Your profile was not updated"
            }
            if(userUpdated) {
                session.setAttribute("flashMessageSuccess", "The user was updated");
            } else {
                session.setAttribute("flashMessageWarning", "The user was not updated");
            }
        }

        req.setAttribute("pageTitle", "Edit User");
        req.getRequestDispatcher("WEB-INF/users/admin-edit-user.jsp").forward(req, resp);
    }
}
