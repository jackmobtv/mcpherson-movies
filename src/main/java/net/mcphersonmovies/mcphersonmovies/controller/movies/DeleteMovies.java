package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.mcphersonmovies.mcphersonmovies.model.DAO.MovieDAO;

import java.io.IOException;

@WebServlet(value="/delete-movies")
public class DeleteMovies extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        String id = req.getParameter("id");

        boolean success = false;
        try{
            success = MovieDAO.deleteMovie(Integer.parseInt(id));
        } catch (Exception ex){
            session.setAttribute("flashMessageDanger", "Failed to delete movie.");
            resp.sendRedirect(req.getContextPath() + "/movies");
        }

        if(success){
            session.setAttribute("flashMessageSuccess", "Movie Deleted.");
            resp.sendRedirect(req.getContextPath() + "/movies");
        } else {
            session.setAttribute("flashMessageDanger", "Failed to delete movie.");
            resp.sendRedirect(req.getContextPath() + "/movies");
        }
    }
}
