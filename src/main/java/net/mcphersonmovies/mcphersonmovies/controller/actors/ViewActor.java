package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Actor;
import net.mcphersonmovies.mcphersonmovies.model.ActorDAO;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/view-actors")
public class ViewActor extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actor_id = Integer.parseInt(req.getParameter("id"));
        List<Movie> movies = MovieDAO.getMoviesByActorId(actor_id);
        Actor actor = ActorDAO.getActorByActorId(actor_id);
        req.setAttribute("movies", movies);
        req.setAttribute("actor", actor);
        req.setAttribute("pageTitle", "View Movies");
        req.getRequestDispatcher("WEB-INF/actors/view-actors.jsp").forward(req, resp);
    }
}
