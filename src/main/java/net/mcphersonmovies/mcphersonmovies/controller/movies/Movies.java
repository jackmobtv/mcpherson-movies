package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;

import java.io.IOException;
import java.util.List;

@WebServlet(value="/movies")
public class Movies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Movie> movies = MovieDAO.getAllMovies();
        req.setAttribute("movies", movies);
        req.setAttribute("pageTitle", "Movies");
        req.getRequestDispatcher("WEB-INF/movies/movies.jsp").forward(req, resp);
    }
}
