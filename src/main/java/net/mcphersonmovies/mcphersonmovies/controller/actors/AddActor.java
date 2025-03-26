package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.ActorDAO;

import java.io.IOException;

@WebServlet(value="/add-actors")
public class AddActor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int id = Integer.parseInt(req.getParameter("id"));

        boolean success = false;
        success = ActorDAO.addActor(id, name);

        HttpSession session = req.getSession();

        if(success) {
            session.setAttribute("flashMessageSuccess", "Actor successfully Added");
        } else {
            session.setAttribute("flashMessageDanger", "Actor could not be Added");
        }

        resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/view-movies?id=" + id));
    }
}
