package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;
import net.mcphersonmovies.shared.Hashing;
import net.mcphersonmovies.shared.Helpers;
import net.mcphersonmovies.shared.Validators;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/signup")
public class SignUp extends HomeServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Sign up for an Account");
        req.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String passwordConf = req.getParameter("password-conf");
        String response = req.getParameter("g-recaptcha-response");
        String[] terms = req.getParameterValues("terms");
        req.setAttribute("email", email);
        req.setAttribute("password", password);
        req.setAttribute("passwordConf", passwordConf);
        req.setAttribute("terms", terms != null && terms[0].equals("agree") ? "agree" : "");

        User user = new User();
        boolean errorFound = false;
        try {
            user.setEmail(email);
        } catch (IllegalArgumentException ex) {
            errorFound = true;
            req.setAttribute("emailError", ex.getMessage());
        }
        if(UserDAO.get(email) != null) {
            errorFound = true;
            req.setAttribute("emailError", "A user with that email already exists. Please login or reset your password.");
        }
        try {
            user.validatePassword(password);
            user.setPassword(Hashing.hash(password).toCharArray());
        } catch(IllegalArgumentException ex) {
            errorFound = true;
            req.setAttribute("password1Error", ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            errorFound = true;
            req.setAttribute("password1Error", ex.getMessage());
        }
        if(passwordConf != null && passwordConf.isEmpty()) {
            errorFound = true;
            req.setAttribute("password2Error", "Please confirm your password");
        }
        if(password != null && passwordConf != null && !passwordConf.equals(password)) {
            errorFound = true;
            req.setAttribute("password2Error", "Passwords don't match");
        }
        if(terms == null || !terms[0].equals("agree")) {
            errorFound = true;
            req.setAttribute("termsError", "You must agree to our terms of use");
        }
        if(!Validators.validateCaptcha(response)){
            errorFound = true;
            req.setAttribute("captchaError", "Captcha Failed");
        }

        if(!errorFound) {
            boolean userAdded = false;
            try {
                userAdded = UserDAO.add(user);
            } catch (RuntimeException ex) {
                req.setAttribute("userError", "User Could Not Be Added");
            }
            if(userAdded) {
                user.setPassword(null);
                HttpSession session = req.getSession();
                session.invalidate();
                session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("flashMessageSuccess", "User successfully added");
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            }
        }

        req.setAttribute("pageTitle", "Sign up for an Account");
        req.getRequestDispatcher("/WEB-INF/sign-up.jsp").forward(req, resp);
    }
}
