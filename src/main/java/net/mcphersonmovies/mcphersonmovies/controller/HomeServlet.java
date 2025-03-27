package net.mcphersonmovies.mcphersonmovies.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;
import net.mcphersonmovies.mcphersonmovies.model.OMDB;
import net.mcphersonmovies.shared.Helpers;

import java.io.IOException;

@WebServlet("")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Movie movie = MovieDAO.getRandomMovie();

        req.setAttribute("title", movie.getTitle());
        String[] movieData = OMDB.getMovieData(movie.getTitle());
        req.setAttribute("plot", movieData[1]);
        req.setAttribute("posterURL", movieData[0]);
        req.setAttribute("id", movie.getMovie_id());

        req.setAttribute("mobile", Helpers.isMobile(req) ? 50 : 15);

        req.setAttribute("pageTitle", "Home");
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}
