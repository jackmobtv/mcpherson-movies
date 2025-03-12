package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;

import java.io.IOException;

@WebServlet("/reset-password")
public class ResetPassword extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/users/reset-password.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("inputEmail");
        req.setAttribute("email", email);

        String message = UserDAO.passwordReset(email, req);
        req.setAttribute("passwordResetMsg", message);

        req.setAttribute("pageTitle", "Reset your password");
        req.getRequestDispatcher("WEB-INF/users/reset-password.jsp").forward(req, resp);
    }
}
