package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.UserDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class AdminUsers extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user != null && user.getStatus().equals("active") && user.getPrivileges().equals("Admin")) {
            List<User> users = UserDAO.getAll();
            req.setAttribute("users", users);
            req.setAttribute("pageTitle", "All Users");
            req.getRequestDispatcher("WEB-INF/movies/admin-users.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
    }
}