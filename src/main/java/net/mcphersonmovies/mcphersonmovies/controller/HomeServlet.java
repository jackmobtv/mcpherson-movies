package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;
import net.mcphersonmovies.shared.OMDB;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Movie movie = MovieDAO.getRandomMovie();

        req.setAttribute("title", movie.getTitle());
        req.setAttribute("plot", OMDB.getPlot(movie.getTitle()));
        req.setAttribute("posterURL", OMDB.getPoster(movie.getTitle()));
        req.setAttribute("id", movie.getMovie_id());

        req.setAttribute("pageTitle", "Home");
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
