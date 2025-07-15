package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.Image;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;

import java.io.IOException;

@WebServlet("/edit-profile")
public class EditProfile extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if (user == null) {
            session.setAttribute("flashMessageWarning", "You are not logged in");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=edit-profile"));
            return;
        } else if (!user.getStatus().equals("active")){
            session.setAttribute("flashMessageDanger", "Your Account is locked or inactive, Please reset your password");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }

        Image image = UserDAO.getImage(user.getUserId());
        if(image != null){
            image.EncodeImage();
        }
        req.setAttribute("image", image);

        req.setAttribute("user", UserDAO.get(user.getUserId()));

        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/users/edit-profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        boolean errorFound = false;

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String language = req.getParameter("language");
        String pronouns = req.getParameter("pronouns");
        String description = req.getParameter("description");

        HttpSession session = req.getSession();
        User currentUser = (User) session.getAttribute("activeUser");

        User user = new User(currentUser.getUserId(), firstName, lastName, email, phone, language, pronouns, description);

        user.setTimezone("America/Chicago");

        req.setAttribute("user", user);

        if(firstName != null && !firstName.equals(currentUser.getFirstName())) {
            user.setFirstName(firstName);
        }
        if(lastName != null && !lastName.equals(currentUser.getLastName())) {
            user.setLastName(lastName);
        }
        if(email != null && !email.isEmpty() && !email.equals(currentUser.getEmail()) && UserDAO.get(email) != null) {
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
            if(phone != null && !phone.equals(currentUser.getPhone())) {
                user.setPhone(phone);
            }
        } catch(IllegalArgumentException ex) {
            errorFound = true;
            req.setAttribute("phoneError", ex.getMessage());
        }

        try {
            if(!language.equals(currentUser.getLanguage())) {
                user.setLanguage(language);
            }
        } catch(IllegalArgumentException e) {
            errorFound = true;
            req.setAttribute("languageError", e.getMessage());
        }

        if(pronouns != null && !pronouns.equals(currentUser.getPronouns())) {
            user.setPronouns(pronouns);
        }

        if(description != null && !description.equals(currentUser.getDescription())) {
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
                session.setAttribute("activeUser", UserDAO.get(user.getUserId()));
                session.setAttribute("flashMessageSuccess", "Your profile was updated");
            } else {
                session.setAttribute("flashMessageWarning", "Your profile was not updated");
            }
        }

        Image image = UserDAO.getImage(user.getUserId());
        if(image != null){
            image.EncodeImage();
        }
        req.setAttribute("image", image);

        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/users/edit-profile.jsp").forward(req, resp);
    }
}