package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;
import net.mcphersonmovies.shared.Hashing;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Login");
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String[] rememberMe = req.getParameterValues("rememberMe");
        req.setAttribute("email", email);
        req.setAttribute("password", password);
        req.setAttribute("rememberMe", (rememberMe != null && rememberMe[0].equals("true")) ? "true" : "");

        User user = null;
        try {
            user = UserDAO.auth(email, Hashing.hash(password).toCharArray());
        } catch (RuntimeException | NoSuchAlgorithmException e) {
            req.setAttribute("loginFail", "An error occurred.");
        }

        if (user == null) {
            req.setAttribute("loginFail", "Invalid Email or Password. <a href='signup'>Sign-up</a>");
        } else {
            if(!user.getStatus().equals("active")) {
                req.setAttribute("loginFail",  "Your account is locked or inactive. Please reset your password.");
                req.setAttribute("pageTitle", "Login");
                req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
                return;
            }

            user.setPassword(null);

            HttpSession session = req.getSession();
            session.invalidate();
            session = req.getSession();
            if(rememberMe != null && rememberMe[0].equals("true")) {
                session.setMaxInactiveInterval(30 * 60 * 24 * 60);
            }
            session.setAttribute("activeUser", user);
            session.setAttribute("flashMessageSuccess", String.format("Welcome back%s!", (user.getFirstName() != null && !user.getFirstName().isEmpty() ? " " + user.getFirstName() : "")));

            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }

        req.setAttribute("pageTitle", "Login");
        req.getRequestDispatcher("WEB-INF/login.jsp").forward(req, resp);
    }
}
