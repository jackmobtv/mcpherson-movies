package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;

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
            req.getRequestDispatcher("WEB-INF/users/admin-users.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user != null && user.getStatus().equals("active") && user.getPrivileges().equals("Admin")) {
            int id = 0;

            try{
                id = Integer.parseInt(req.getParameter("id"));
            } catch(Exception ex) {
                session.setAttribute("flashMessageDanger", "User not found");
                resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/users"));
                return;
            }

            boolean success = false;
            User userCheck = UserDAO.get(id);
            String actionType = "";

            if(userCheck != null && userCheck.getStatus().equals("inactive")) {
                actionType = "Activate";
                success = UserDAO.activate(id);
            } else {
                actionType = "Deactivate";
                success = UserDAO.deactivate(id);
            }

            if(!success) {
                session.setAttribute("flashMessageDanger", actionType + " Failed");
                resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/users"));
                return;
            }

            session.setAttribute("flashMessageSuccess", "User " + actionType + "d");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/users"));
        }
    }
}