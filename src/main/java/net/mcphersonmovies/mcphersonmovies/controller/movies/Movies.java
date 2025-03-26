package net.mcphersonmovies.mcphersonmovies.controller.movies;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.mcphersonmovies.mcphersonmovies.model.Movie;
import net.mcphersonmovies.mcphersonmovies.model.MovieDAO;
import net.mcphersonmovies.mcphersonmovies.model.MovieFormat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value="/movies")
public class Movies extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String limitStr = req.getParameter("limit");
        int limit = 0;
        try{
            limit = Integer.parseInt(limitStr);
        } catch(NumberFormatException ignored) {}
        int offset = 0;
        String[] formatsArr = req.getParameterValues("formats");
        String formatFilter = "";
        if(formatsArr != null && formatsArr.length > 0) {
            formatFilter = String.join(",", formatsArr);
        }
        req.setAttribute("formatFilter", formatFilter);
        req.setAttribute("limit", limit);
        List<Movie> movies = MovieDAO.getAllMoviesFiltered(offset, limit, formatFilter);
        List<MovieFormat> formats = MovieDAO.getAllFormats();
        req.setAttribute("movies", movies);
        req.setAttribute("formats", formats);
        req.setAttribute("pageTitle", "Movies");
        req.getRequestDispatcher("WEB-INF/movies/movies.jsp").forward(req, resp);
    }
}
