package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;

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

        req.setAttribute("pageTitle", "Edit Profile");
        req.getRequestDispatcher("WEB-INF/edit-profile.jsp").forward(req, resp);
    }
}