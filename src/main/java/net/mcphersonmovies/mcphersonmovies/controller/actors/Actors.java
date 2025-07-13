package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.Actor;
import net.mcphersonmovies.mcphersonmovies.model.DAO.ActorDAO;
import net.mcphersonmovies.mcphersonmovies.model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/actors")
public class Actors extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user != null && user.getStatus().equals("active") && (user.getPrivileges().equals("Admin") || user.getPrivileges().equals("Premium"))) {
            List<Actor> actors = ActorDAO.getAllActors();
            req.setAttribute("actors", actors);
            req.setAttribute("pageTitle", "Actors");
            req.getRequestDispatcher("WEB-INF/actors/actors.jsp").forward(req, resp);
        } else {
            session.setAttribute("flashMessageWarning", "Page is Restricted to Premium Users");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/pricing"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name").toString();
        int id = Integer.parseInt(req.getParameter("id").toString());

        boolean success = false;
        success = ActorDAO.updateActor(new Actor(id, name));

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(success) {
            session.setAttribute("flashMessageSuccess", "Actor successfully Updated");
        } else {
            session.setAttribute("flashMessageDanger", "Actor could not be updated");
        }

        if(user != null && user.getStatus().equals("active") && (user.getPrivileges().equals("Admin") || user.getPrivileges().equals("Premium"))) {
            List<Actor> actors = ActorDAO.getAllActors();
            req.setAttribute("actors", actors);
            req.setAttribute("pageTitle", "Actors");
            req.getRequestDispatcher("WEB-INF/actors/actors.jsp").forward(req, resp);
        } else {
            session.setAttribute("flashMessageWarning", "Page is Restricted to Premium Users");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/pricing"));
        }
    }
}
