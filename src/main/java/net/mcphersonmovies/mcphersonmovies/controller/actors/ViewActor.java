package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.*;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/view-actors")
public class ViewActor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("activeUser");

        if(user != null && user.getStatus().equals("active") && (user.getPrivileges().equals("Admin") || user.getPrivileges().equals("Premium"))) {
            int actor_id = 0;
            try{
                actor_id = Integer.parseInt(req.getParameter("id"));
            } catch(NumberFormatException ignored) {}

            List<Movie> movies = MovieDAO.getMoviesByActorId(actor_id);
            Actor actor = ActorDAO.getActorByActorId(actor_id);
            req.setAttribute("movies", movies);
            req.setAttribute("actor", actor);
            req.setAttribute("pageTitle", "View Movies");
            req.getRequestDispatcher("WEB-INF/actors/view-actors.jsp").forward(req, resp);
        } else {
            session.setAttribute("flashMessageWarning", "Page is Restricted to Premium Users");
            resp.sendRedirect(resp.encodeRedirectURL(req.getContextPath() + "/pricing"));
        }
    }
}
