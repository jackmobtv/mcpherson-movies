package net.mcphersonmovies.mcphersonmovies.controller.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.User;
import net.mcphersonmovies.mcphersonmovies.model.DAO.UserDAO;
import net.mcphersonmovies.shared.Hashing;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet("/delete-account")
public class DeleteAccount extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");
        if(user == null) {
            session.setAttribute("flashMessageWarning", "You must be logged in.");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/login?redirect=delete-account"));
            return;
        } else if(user != null && !user.getStatus().equals("active")) {
            session.setAttribute("flashMessageDanger", "Your account is locked or inactive.");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
            return;
        }
        req.setAttribute("pageTitle", "Delete Account");
        req.getRequestDispatcher("WEB-INF/users/delete-account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        req.setAttribute("email", email);

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("activeUser");

        try {
            if(!user.getEmail().equals(email) || !UserDAO.delete(email, Hashing.hash(password).toCharArray())) {
                session.setAttribute("flashMessageWarning", "Invalid Email or Password.");
                req.setAttribute("pageTitle", "Delete Account");
                req.getRequestDispatcher("WEB-INF/users/delete-account.jsp").forward(req, resp);
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        session.invalidate();
        session = req.getSession();
        session.setAttribute("flashMessageDanger", "Your account has been Deleted.");

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/"));
    }
}
