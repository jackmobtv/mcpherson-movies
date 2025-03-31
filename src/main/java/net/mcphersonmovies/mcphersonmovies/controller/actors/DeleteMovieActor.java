package net.mcphersonmovies.mcphersonmovies.controller.actors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.ActorDAO;

import java.io.IOException;

@WebServlet(value="/delete-movie-actors")
public class DeleteMovieActor extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int actor_id = 0;
        try {
            actor_id = Integer.parseInt(req.getParameter("actor_id"));
        } catch (NumberFormatException ignored) {}

        int movie_id = 0;
        try {
            movie_id = Integer.parseInt(req.getParameter("movie_id"));
        } catch (NumberFormatException ignored) {}

        boolean success = false;
        success = ActorDAO.deleteMovieActor(movie_id, actor_id);

        HttpSession session = req.getSession();

        if(success) {
            session.setAttribute("flashMessageSuccess", "Actor Successfully deleted");
        } else {
            session.setAttribute("flashMessageError", "Actor could not be deleted");
        }

        resp.sendRedirect(req.getContextPath() + "/update-movies?id=" + movie_id);
    }
}
