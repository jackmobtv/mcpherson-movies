package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.shared.AzureEmail;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.shared.Hashing;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/new-password")
public class NewPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        HttpSession session = req.getSession();
        if(UserDAO.getPasswordReset(token).isEmpty()) {
            session.setAttribute("flashMessageWarning", "Link has Expired");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }
        session.setAttribute("token", token);
        req.setAttribute("pageTitle", "New password");
        req.getRequestDispatcher("WEB-INF/users/new-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String token = session.getAttribute("token").toString();
        String password1 = req.getParameter("inputPassword1");
        String password2 = req.getParameter("inputPassword2");

        req.setAttribute("password1", password1);
        req.setAttribute("password2", password2);

        User user = new User();

        boolean errorFound = false;
        try {
            user.validatePassword(password1);
        } catch(IllegalArgumentException e) {
            errorFound = true;
            req.setAttribute("password1Error", e.getMessage());
        }
        if(password2 != null && password2.isEmpty()) {
            errorFound = true;
            req.setAttribute("password2Error", "Please confirm your password");
        }
        if(password1 != null && password2 != null && !password2.equals(password1)) {
            errorFound = true;
            req.setAttribute("password2Error", "Passwords don't match");
        }
        if(token == null || token.isEmpty()) {
            req.setAttribute("newPasswordFail", "Invalid or missing token");
        }

        if(!errorFound) {
            String email = UserDAO.getPasswordReset(token);
            if(email == null || email.isEmpty()) {
                req.setAttribute("newPasswordFail", "Token not found");
            } else {
                boolean success = false;
                try {
                    success = UserDAO.updatePassword(email, Hashing.hash(password1).toCharArray());
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }

                if(success) {
                    String subject = "Password has been reset";
                    String body = "<h2>Password has been reset</h2>";
                    body += "<p>Your password has changed. If you suspect that someone else changed your password, please contact customer support.</p>";

                    String appURL = "";
                    if(req.getServerName().equals("localhost")) {
                        appURL = req.getServletContext().getInitParameter("appURLLocal");
                    } else {
                        appURL = req.getServletContext().getInitParameter("appURLCloud");
                    }
                    String URL = String.format("%s/reset-password", appURL);
                    body += String.format("<p><a href=\"%s\" target=\"_blank\">%s</a></p>", URL, URL);

                    try{
                        AzureEmail.sendEmail(email, subject, body);
                    } catch(Exception ignored){}

//                session.removeAttribute("activeUser");
//                session.setAttribute("activeUser", user);
                    session.setAttribute("flashMessageSuccess", "Password has been reset");
                    resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login"));
                    return;
                } else {
                    session.setAttribute("newPasswordFail", "Reset password failed");
                }
            }
        }

        req.setAttribute("pageTitle", "New password");
        req.getRequestDispatcher("WEB-INF/users/new-password.jsp").forward(req, resp);
    }
}
