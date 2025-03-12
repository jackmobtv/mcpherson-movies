package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;
import net.mcphersonmovies.shared.Hashing;
import net.mcphersonmovies.shared.Validators;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("redirect");
        String redirect = req.getParameter("redirect");
        if(redirect != null && !redirect.isEmpty()){
            session.setAttribute("redirect", redirect);
        }
        req.setAttribute("pageTitle", "Login");
        req.getRequestDispatcher("WEB-INF/users/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String redirect = session.getAttribute("redirect") == null ? "" : session.getAttribute("redirect").toString();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String response = req.getParameter("g-recaptcha-response");
        String[] rememberMe = req.getParameterValues("rememberMe");
        req.setAttribute("email", email);
        req.setAttribute("password", password);
        req.setAttribute("rememberMe", (rememberMe != null && rememberMe[0].equals("true")) ? "true" : "");

        if(!Validators.validateCaptcha(response)){
            req.setAttribute("captchaError", "Captcha Failed");
            req.setAttribute("pageTitle", "Login");
            req.getRequestDispatcher("WEB-INF/users/login.jsp").forward(req, resp);
            return;
        }

        User user = null;
        try {
            user = UserDAO.auth(email, Hashing.hash(password).toCharArray());
        } catch (RuntimeException | NoSuchAlgorithmException ex) {
            req.setAttribute("loginFail", "An error occurred.");
        }

        if (user == null) {
            Integer count = (Integer) session.getAttribute("wrongCount");
            if (count == null) {
                count = 0;
            }
            if(count == 5){
                UserDAO.lock(email);
                session.setAttribute("flashMessageDanger", "Account is locked due to too many failed attempts.");
                session.removeAttribute("wrongCount");
                resp.sendRedirect(req.getContextPath() + "/");
                return;
            } else {
                if (session.getAttribute("wrongEmail") != null && session.getAttribute("wrongEmail").equals(email)) {
                    session.setAttribute("wrongCount", count + 1);
                } else {
                    session.setAttribute("wrongEmail", email);
                    session.setAttribute("wrongCount", 1);
                }
            }
            req.setAttribute("loginFail", "Invalid Email or Password. <a href='signup'>Sign-up</a>");
        } else {
            if(!user.getStatus().equals("active")) {
                req.setAttribute("loginFail",  "Your account is locked or inactive. Please reset your password.");
                req.setAttribute("pageTitle", "Login");
                req.getRequestDispatcher("WEB-INF/users/login.jsp").forward(req, resp);
                return;
            }

            user.setPassword(null);

            session.invalidate();
            session = req.getSession();
            if(rememberMe != null && rememberMe[0].equals("true")) {
                session.setMaxInactiveInterval(30 * 60 * 24 * 60);
            }
            session.setAttribute("activeUser", user);
            session.setAttribute("flashMessageSuccess", String.format("Welcome back%s!", (user.getFirstName() != null && !user.getFirstName().isEmpty() ? " " + user.getFirstName() : "")));

            resp.sendRedirect(req.getContextPath() + "/" + redirect);
            return;
        }

        req.setAttribute("pageTitle", "Login");
        req.getRequestDispatcher("WEB-INF/users/login.jsp").forward(req, resp);
    }
}
